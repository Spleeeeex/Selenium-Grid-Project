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
public class BugsTests extends BaseTest {

    @Test
    public void testDoubleClickToClean() {
        try {

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Нажимаем кнопку очистки
        getDriver().findElement(By.name("reset")).click();
        // Дублируем нажатие
        getDriver().findElement(By.name("reset")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Кнопка «Очистить поля» при всех пустых полях заполняет нулями')]")
        ));

        assertEquals("Кнопка «Очистить поля» при всех пустых полях заполняет нулями", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testBCFieldRealNumbers() {
        try{

        // Открываем страницу
        getDriver().get("https://do.tusur.ru/qa-test2/");

        // Очищаем поле (для надежности)
        getDriver().findElement(By.name("a")).clear();
        getDriver().findElement(By.name("b")).clear();
        getDriver().findElement(By.name("c")).clear();

        // Вводим значения сторон треугольника
        getDriver().findElement(By.name("a")).sendKeys("10");
        getDriver().findElement(By.name("b")).sendKeys("6,5");
        getDriver().findElement(By.name("c")).sendKeys("7");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Поле ВC принимает вещественные числа')]")
        ));

        assertEquals("Поле ВC принимает вещественные числа", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testACFieldRange1To99() {
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
        getDriver().findElement(By.name("c")).sendKeys("99");

        // Нажимаем кнопку расчета
        getDriver().findElement(By.name("calc")).click();

        // Ожидаем появления результата и проверяем его
        WebElement result = getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(., 'Поле AС имеет диапазон от 1 до 99')]")
        ));

        assertEquals("Поле AС имеет диапазон от 1 до 99", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testACFieldNotCheckedForCompleteness() {
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
                By.xpath("//li[contains(., 'Поле AС не проверяется на заполненность')]")
        ));

        assertEquals("Поле AС не проверяется на заполненность", result.getText());

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }
}