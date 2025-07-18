package com.tusur;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.CONCURRENT)
@Tag("firefox")
public class NegativeTests extends BaseTest {

    @Test
    public void testLargeValues() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("125");
        getDriver().findElement(By.name("b")).sendKeys("125");
        getDriver().findElement(By.name("c")).sendKeys("125");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='text-danger' and contains(., 'Стороны могут иметь длину от 1 до 100')]")
        ));

        assertEquals("Стороны могут иметь длину от 1 до 100", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testRealValues() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("10");
        getDriver().findElement(By.name("b")).sendKeys("7");
        getDriver().findElement(By.name("c")).sendKeys("6,5");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Вещественные значения')]")
        ));

        assertEquals("Вещественные значения", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testBoundaryValues() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("1");
        getDriver().findElement(By.name("b")).sendKeys("1");
        getDriver().findElement(By.name("c")).sendKeys("1");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Граничные значения')]")
        ));

        assertEquals("Граничные значения", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testEmptyFields() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("");
        getDriver().findElement(By.name("b")).sendKeys("1");
        getDriver().findElement(By.name("c")).sendKeys("1");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='text-danger' and contains(., 'Не все обязательные поля для ввода были заполнены')]")
        ));

        assertEquals("Не все обязательные поля для ввода были заполнены", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testEmptyFields2() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("1");
        getDriver().findElement(By.name("b")).sendKeys("1");
        getDriver().findElement(By.name("c")).sendKeys("");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Одно поле пустое')]")
        ));

        assertEquals("Одно поле пустое", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testNotTriangle() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("1");
        getDriver().findElement(By.name("b")).sendKeys("2");
        getDriver().findElement(By.name("c")).sendKeys("5");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Не выполнилось условие треугольника')]")
        ));

        assertEquals("Не выполнилось условие треугольника", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testNonNumericValues() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("10");
        getDriver().findElement(By.name("b")).sendKeys("7");
        getDriver().findElement(By.name("c")).sendKeys("A");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Нечисловые значения')]")
        ));

        assertEquals("Нечисловые значения", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testNegativeValues() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("1");
        getDriver().findElement(By.name("b")).sendKeys("1");
        getDriver().findElement(By.name("c")).sendKeys("-1");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Отрицательные значения')]")
        ));

        assertEquals("Отрицательные значения", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testCleanValues() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("1");
        getDriver().findElement(By.name("b")).sendKeys("1");
        getDriver().findElement(By.name("c")).sendKeys("1");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();
        // Нажимаем кнопку очистки
        getDriver().findElement(By.name("reset")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Очистить поля')]")
        ));

        assertEquals("Очистить поля", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testZeroValues() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("0");
        getDriver().findElement(By.name("b")).sendKeys("1");
        getDriver().findElement(By.name("c")).sendKeys("1");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Проверка 0')]")
        ));

        assertEquals("Проверка 0", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testItsNotTriangle() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("1");
        getDriver().findElement(By.name("b")).sendKeys("2");
        getDriver().findElement(By.name("c")).sendKeys("5");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='text-danger' and contains(., 'Треугольник не возможно построить')]")
        ));

        assertEquals("Треугольник не возможно построить", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }
}