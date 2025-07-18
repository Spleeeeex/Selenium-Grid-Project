package com.tusur;

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
@Tag("firefox")
@Epic("Проверка функционала треугольника")
@Feature("Негативные тесты")
@Story("Проверка обработки некорректных данных")
public class NegativeTests extends BaseTest {

    @Test
    @Description("Тест больших значений")
    @Severity(SeverityLevel.MINOR)
    public void testLargeValues() {
        try {

        // Открываем страницу
        driver.get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        driver.findElement(By.name("a")).clear();
        driver.findElement(By.name("b")).clear();
        driver.findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        driver.findElement(By.name("a")).sendKeys("125");
        driver.findElement(By.name("b")).sendKeys("125");
        driver.findElement(By.name("c")).sendKeys("125");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='text-danger' and contains(., 'Стороны могут иметь длину от 1 до 100')]")
        ));

        assertEquals("Стороны могут иметь длину от 1 до 100", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест вещественных значений")
    @Severity(SeverityLevel.MINOR)
    public void testRealValues() {
        try {

        // Открываем страницу
        driver.get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        driver.findElement(By.name("a")).clear();
        driver.findElement(By.name("b")).clear();
        driver.findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        driver.findElement(By.name("a")).sendKeys("10");
        driver.findElement(By.name("b")).sendKeys("7");
        driver.findElement(By.name("c")).sendKeys("6,5");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Вещественные значения')]")
        ));

        assertEquals("Вещественные значения", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест граничных значений")
    @Severity(SeverityLevel.MINOR)
    public void testBoundaryValues() {
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
        driver.findElement(By.name("c")).sendKeys("1");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Граничные значения')]")
        ));

        assertEquals("Граничные значения", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест проверки наличия пустого поля")
    @Severity(SeverityLevel.MINOR)
    public void testEmptyFields() {
        try {

        // Открываем страницу
        driver.get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        driver.findElement(By.name("a")).clear();
        driver.findElement(By.name("b")).clear();
        driver.findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        driver.findElement(By.name("a")).sendKeys("");
        driver.findElement(By.name("b")).sendKeys("1");
        driver.findElement(By.name("c")).sendKeys("1");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='text-danger' and contains(., 'Не все обязательные поля для ввода были заполнены')]")
        ));

        assertEquals("Не все обязательные поля для ввода были заполнены", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест пустого поля 'AC'")
    @Severity(SeverityLevel.MINOR)
    public void testEmptyFields2() {
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
                By.xpath("//li[contains(., 'Одно поле пустое')]")
        ));

        assertEquals("Одно поле пустое", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест не выполнения условия треугольника")
    @Severity(SeverityLevel.MINOR)
    public void testNotTriangle() {
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
        driver.findElement(By.name("c")).sendKeys("5");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Не выполнилось условие треугольника')]")
        ));

        assertEquals("Не выполнилось условие треугольника", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест нечисловых значений")
    @Severity(SeverityLevel.MINOR)
    public void testNonNumericValues() {
        try {

        // Открываем страницу
        driver.get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        driver.findElement(By.name("a")).clear();
        driver.findElement(By.name("b")).clear();
        driver.findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        driver.findElement(By.name("a")).sendKeys("10");
        driver.findElement(By.name("b")).sendKeys("7");
        driver.findElement(By.name("c")).sendKeys("A");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Нечисловые значения')]")
        ));

        assertEquals("Нечисловые значения", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест отрицательных значений")
    @Severity(SeverityLevel.MINOR)
    public void testNegativeValues() {
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
        driver.findElement(By.name("c")).sendKeys("-1");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Отрицательные значения')]")
        ));

        assertEquals("Отрицательные значения", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест очистки поля")
    @Severity(SeverityLevel.MINOR)
    public void testCleanValues() {
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
        driver.findElement(By.name("c")).sendKeys("1");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();
        // Нажимаем кнопку очистки
        driver.findElement(By.name("reset")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Очистить поля')]")
        ));

        assertEquals("Очистить поля", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест нулевого значения")
    @Severity(SeverityLevel.MINOR)
    public void testZeroValues() {
        try {

        // Открываем страницу
        driver.get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        driver.findElement(By.name("a")).clear();
        driver.findElement(By.name("b")).clear();
        driver.findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        driver.findElement(By.name("a")).sendKeys("0");
        driver.findElement(By.name("b")).sendKeys("1");
        driver.findElement(By.name("c")).sendKeys("1");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Проверка 0')]")
        ));

        assertEquals("Проверка 0", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Description("Тест невозможности построения треугольника")
    @Severity(SeverityLevel.MINOR)
    public void testItsNotTriangle() {
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
        driver.findElement(By.name("c")).sendKeys("5");

        // Нажимаем кнопку расчета
        driver.findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='text-danger' and contains(., 'Треугольник не возможно построить')]")
        ));

        assertEquals("Треугольник не возможно построить", result.getText());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }
}