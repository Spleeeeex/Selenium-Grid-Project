package com.tusur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.CONCURRENT)
@Tag("chrome")
@Epic("Проверка функционала треугольника")
@Feature("Баг-репорты")
@Story("Проверка известных проблем")
public class BugsTests extends BaseTest {

    @BeforeEach
    void openTestPage() {
        driver.get("https://do.tusur.ru/qa-test2/");
        System.out.println("Открыта вкладка для теста: " + driver.getWindowHandle());
    }

    @Test
    @Description("Тест проверяет двойное нажатие по кнопке 'Очистить'")
    @Severity(SeverityLevel.MINOR)
    public void testDoubleClickToClean() {
        try {
        // Очищаем поле (для надежности)
        driver.findElement(By.name("a")).clear();
        driver.findElement(By.name("b")).clear();
        driver.findElement(By.name("c")).clear();

        // Нажимаем кнопку очистки
        driver.findElement(By.name("reset")).click();
        // Дублируем нажатие
        driver.findElement(By.name("reset")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Кнопка «Очистить поля» при всех пустых полях заполняет нулями')]")
        ));

        assertEquals("Кнопка «Очистить поля» при всех пустых полях заполняет нулями", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест проверяет (не)принятие полем 'BC' вещественных чисел")
    @Severity(SeverityLevel.MINOR)
    public void testBCFieldRealNumbers() {
        try{

        // Открываем страницу
        driver.get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        driver.findElement(By.name("a")).clear();
        driver.findElement(By.name("b")).clear();
        driver.findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        driver.findElement(By.name("a")).sendKeys("10");
        driver.findElement(By.name("b")).sendKeys("6,5");
        driver.findElement(By.name("c")).sendKeys("7");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Поле ВC принимает вещественные числа')]")
        ));

        assertEquals("Поле ВC принимает вещественные числа", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест проверяет диапазон поля 'AC'")
    @Severity(SeverityLevel.MINOR)
    public void testACFieldRange1To99() {
        try {

        // Открываем страницу
        driver.get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        driver.findElement(By.name("a")).clear();
        driver.findElement(By.name("b")).clear();
        driver.findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        driver.findElement(By.name("a")).sendKeys("1");
        driver.findElement(By.name("b")).sendKeys("2");
        driver.findElement(By.name("c")).sendKeys("99");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Поле AС имеет диапазон от 1 до 99')]")
        ));

        assertEquals("Поле AС имеет диапазон от 1 до 99", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест проверяет поле 'AC' на заполненность")
    @Severity(SeverityLevel.MINOR)
    public void testACFieldNotCheckedForCompleteness() {
        try {

        // Открываем страницу
        driver.get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        driver.findElement(By.name("a")).clear();
        driver.findElement(By.name("b")).clear();
        driver.findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        driver.findElement(By.name("a")).sendKeys("1");
        driver.findElement(By.name("b")).sendKeys("1");
        driver.findElement(By.name("c")).sendKeys("");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Поле AС не проверяется на заполненность')]")
        ));

        assertEquals("Поле AС не проверяется на заполненность", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }
}