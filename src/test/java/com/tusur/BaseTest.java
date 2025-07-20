package com.tusur;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.qameta.allure.Allure;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class BaseTest {
    private static final String GRID_URL = "http://localhost:4444";
    private static final Lock testLock = new ReentrantLock();
    // Добавляем задержку в 3 секунды между тестами
    private static final int DELAY_BETWEEN_TESTS_MS = 3000;

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static int testClassCounter = 0;

    @BeforeAll
    public void setupDriver(TestInfo testInfo) throws MalformedURLException {
        testLock.lock();
        try {
            testClassCounter++;

            // Определяем, какой браузер использовать на основе тегов теста
            if (testInfo.getTags().contains("chrome")) {
                driver = createChromeDriver();
            } else if (testInfo.getTags().contains("firefox")) {
                driver = createFirefoxDriver();
            }
            wait = new WebDriverWait(driver, Duration.ofSeconds(2));

            // Открываем тестовую страницу
            driver.get("https://do.tusur.ru/qa-test2/");

            System.out.printf("[%s] Инициализирован драйвер для класса: %s тип браузера: %s%n",
                    Thread.currentThread().getName(),
                    this.getClass().getSimpleName(),
                    testInfo.getTags());

            // Начальная задержка после открытия браузера
            safeSleep(2000);
        } finally {
            testLock.unlock();
        }
    }

    @BeforeEach
    public void setupTest(TestInfo testInfo) {
        testLock.lock();
        try {
            // Ожидание готовности страницы
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

            System.out.printf("[%s] Начат тест: %s в новой вкладке%n",
                    Thread.currentThread().getName(),
                    testInfo.getDisplayName());

            // Задержка перед началом теста
            safeSleep(2000);
        } finally {
            testLock.unlock();
        }
    }

    private WebDriver createChromeDriver() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        return new RemoteWebDriver(new URL(GRID_URL), options);
    }

    private WebDriver createFirefoxDriver() throws MalformedURLException {
        FirefoxOptions options = new FirefoxOptions();
        return new RemoteWebDriver(new URL(GRID_URL), options);
    }

    @AfterEach
    public void afterTest(TestInfo testInfo) {
        testLock.lock();
        try {
            try {
                // Создаем скриншот
                takeScreenshot(testInfo);
                // Ожидание готовности страницы
                wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

                System.out.printf("[%s] Тест завершен: %s%n",
                        Thread.currentThread().getName(),
                        testInfo.getDisplayName());

                // Задержка между тестами
                safeSleep(DELAY_BETWEEN_TESTS_MS);
            } catch (Exception e) {
                System.out.println("Ошибка при завершении теста: " + e.getMessage());
            }
        } finally {
            testLock.lock();
        }
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
        testLock.lock();
        try {
            testClassCounter--;
            boolean isLastTestClass = (testClassCounter == 0);

            if (driver != null) {
                try {
                    // Добавляем задержку 3 секунды перед закрытием
                    Thread.sleep(3000);
                    if (isLastTestClass) {
                        System.out.println("Закрытие драйвера, так как это последний тестовый класс...");
                        driver.quit();
                        System.out.println("Драйвер успешно закрыт для класса: " + this.getClass().getSimpleName());
                    } else {
                        System.out.println("Драйвер остается открытым для следующих тестовых классов");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка при закрытии драйвера: " + e.getMessage());
                }
            }
        } finally {
            testLock.unlock();
        }
    }

    // Вспомогательный метод для безопасной задержки
    private void safeSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Задержка была прервана: " + e.getMessage());
        }
    }
}