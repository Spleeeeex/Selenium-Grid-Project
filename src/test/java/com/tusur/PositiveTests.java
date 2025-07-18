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
@Tag("chrome")
public class PositiveTests extends BaseTest {

    @Test
    public void testRightAngledTriangle() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("3");
        getDriver().findElement(By.name("b")).sendKeys("4");
        getDriver().findElement(By.name("c")).sendKeys("5");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
         WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                 By.xpath("//li[contains(., 'Прямоугольный треугольник')]")
         ));

         assertEquals("Прямоугольный треугольник", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testEquilateralTriangle() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("5");
        getDriver().findElement(By.name("b")).sendKeys("5");
        getDriver().findElement(By.name("c")).sendKeys("5");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Равносторонний треугольник')]")
        ));

        assertEquals("Равносторонний треугольник", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testIsoscelesTriangle() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("3");
        getDriver().findElement(By.name("b")).sendKeys("3");
        getDriver().findElement(By.name("c")).sendKeys("5");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Равнобедренный треугольник')]")
        ));

        assertEquals("Равнобедренный треугольник", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testAcuteTriangle() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("6");
        getDriver().findElement(By.name("b")).sendKeys("7");
        getDriver().findElement(By.name("c")).sendKeys("5");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Остроугольный треугольник')]")
        ));

        assertEquals("Остроугольный треугольник", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testObtuseTriangle() {
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
        getDriver().findElement(By.name("c")).sendKeys("5");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Тупоугольный треугольник')]")
        ));

        assertEquals("Тупоугольный треугольник", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }
}