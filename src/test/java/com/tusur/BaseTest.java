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

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String mainWindowHandle;

    @BeforeAll
    public void setupDriver(TestInfo testInfo) throws MalformedURLException {
        // Определяем, какой браузер использовать на основе тегов теста
        if (testInfo.getTags().contains("chrome")) {
            driver = createChromeDriver();
        } else if (testInfo.getTags().contains("firefox")) {
            driver = createFirefoxDriver();
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        System.out.printf("[%s] Инициализирован драйвер для класса: %s тип браузера: %s%n",
                Thread.currentThread().getName(),
                this.getClass().getSimpleName(),
                testInfo.getTags());
    }

    @BeforeEach
    public void setupTest(TestInfo testInfo) {
        // Создаем новую вкладку для теста
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://do.tusur.ru/qa-test2/");

        System.out.printf("[%s] Начат тест: %s в новой вкладке%n",
                Thread.currentThread().getName(),
                testInfo.getDisplayName());
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
        // Добавляем задержку перед каждым тестом (5 секунд)
        Thread.sleep(5000);
    }

    @AfterEach
    public void afterTest(TestInfo testInfo) {
        try {
            // Создаем скриншот
            takeScreenshot(testInfo);

            // Закрываем текущую вкладку (тестовую)
            driver.close();

            // Возвращаемся к оригинальному окну
            driver.switchTo().window(mainWindowHandle);
        } catch (Exception e) {
            System.out.println("Ошибка при завершении теста: " + e.getMessage());
        }

        System.out.printf("[%s] Тест завершен: %s%n",
                Thread.currentThread().getName(),
                testInfo.getDisplayName());
    }

    private void takeScreenshot(TestInfo testInfo) {
        try {
            // Создаем уникальное имя файла
            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            String browserType = testInfo.getTags().contains("chrome") ? "chrome" : "firefox";
            String screenshotsDir = "target" + File.separator + "screenshots" + File.separator + browserType;

            // Правильный путь с вложенными папками
            File screenshotsFolder = new File(screenshotsDir);
            if (!screenshotsFolder.exists() && !screenshotsFolder.mkdirs()) {
                System.out.println("Не удалось создать директорию для скриншотов: " + screenshotsDir);
            }

            // Создаем скриншот перед закрытием вкладки
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = screenshotsDir + File.separator + "test_" + timestamp + ".png";
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            // Прикрепляем скриншоты к Allure отчету
            try (FileInputStream fis = new FileInputStream(screenshot)) {
                Allure.addAttachment("Скриншот после теста " + testInfo.getDisplayName(), "image/png", fis, ".png");
            }
        } catch (Exception e) {
            System.out.println("Не удалось сделать скриншот - окно уже закрыто: " + e.getMessage());
        }
    }

    @AfterAll
    public void tearDownDriver() {
        if (driver != null) {
            try {
                // Добавляем задержку 15 секунд перед закрытием
                Thread.sleep(15000);
                driver.quit();
                System.out.println("Драйвер успешно закрыт для класса: " + this.getClass().getSimpleName());
            } catch (Exception e) {
                System.out.println("Ошибка при закрытии драйвера: " + e.getMessage());
            }
        }
    }
}