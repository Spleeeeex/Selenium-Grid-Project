package com.tusur;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import io.qameta.allure.Allure;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
    public class BaseTest {
    private static final String GRID_URL = "http://localhost:4444";

    // ThreadLocal для хранения драйверов и ожиданий
    protected ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    protected ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    protected WebDriverWait getWait() {
        return waitThreadLocal.get();
    }

    @BeforeEach
    public void setup(TestInfo testInfo) throws MalformedURLException {

        // Определяем, какой браузер использовать на основе тегов теста
        if (testInfo.getTags().contains("chrome")) {
            driverThreadLocal.set(createChromeDriver());
        } else if (testInfo.getTags().contains("firefox")) {
            driverThreadLocal.set(createFirefoxDriver());
        }
        waitThreadLocal.set(new WebDriverWait(getDriver(), Duration.ofSeconds(5)));

        System.out.printf("[%s] Начат тест: %s тип браузера: %s%n",
                Thread.currentThread().getName(),
                testInfo.getDisplayName(),
                testInfo.getTags());
    }

    private WebDriver createChromeDriver() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        return new RemoteWebDriver(new URL(GRID_URL), options);
    }

    private WebDriver createFirefoxDriver() throws MalformedURLException {
        FirefoxOptions options = new FirefoxOptions();
        return new RemoteWebDriver(new URL(GRID_URL), options);
    }

    @BeforeEach
    public void slowDown() throws InterruptedException {
        // Добавляем задержку перед каждым тестом (2 секунды)
        Thread.sleep(2000);
    }

    @AfterEach
    public void afterTest(TestInfo testInfo) {
        try {
            // Создаем уникальное имя файла
            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            String browserType = testInfo.getTags().contains("chrome") ? "chrome" : "firefox";

            // Правильный путь с вложенными папками
            String screenshotsDir = "target" + File.separator + "screenshots" + File.separator + browserType + File.separator;
            String screenshotPath = screenshotsDir + File.separator + "test_" + timestamp + ".png";

            // Создаем все необходимые папки
            new File(screenshotsDir);

            // Создаем и сохраняем скриншоты
            File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(screenshotPath));

            // Прикрепляем скриншоты к Allure отчету
            try (FileInputStream fis = new FileInputStream(screenshot)) {
                Allure.addAttachment("Скриншот после теста", "image/png", fis, ".png");
            }

            // Пауза 2 секунды
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Не удалось сделать скриншот: " + e.getMessage());
        }
    }

    @AfterEach
    public void logTestEnd(TestInfo testInfo) {
        System.out.printf("[%s] Тест завершен: %s%n",
                Thread.currentThread().getName(),
                testInfo.getDisplayName());
    }

    @AfterEach
    public void tearDownDriver() {
        if (getDriver() != null) {
            try {
                // Добавляем задержку 5 секунд перед закрытием
                Thread.sleep(5000);
                getDriver().quit();
                System.out.println("Драйвер успешно закрыт для потока: " + Thread.currentThread().getName());
            } catch (Exception e) {
                System.out.println("Ошибка при закрытии драйвера: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
                waitThreadLocal.remove();
            }
        }
    }
}