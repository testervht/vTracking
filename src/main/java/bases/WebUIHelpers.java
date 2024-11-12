package bases;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.function.Supplier;

public class WebUIHelpers {
    private WebDriver driver;

    public WebUIHelpers(WebDriver driver) {
        this.driver = driver;
    }
    public void waitFor(Supplier<Boolean> condition, long timeoutMillis) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            if (condition.get()) {
                return; // Điều kiện đã được thỏa mãn
            }
            try {
                Thread.sleep(100); // Chờ 100ms trước khi kiểm tra lại
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new TimeoutException("Condition not met within timeout period");
    }
    public WebElement getElement(By by) {
        return driver.findElement(by);
    }

    public void setText(By by, String value) {
        WebElement textInput = driver.findElement(by);
        textInput.sendKeys(value);
    }

    public String getText(By by) {
        WebElement element = driver.findElement(by);
        String textActual = element.getText();
        return textActual;
    }

    public void clickElement(By by) {
        WebElement clickElement = driver.findElement(by);
        clickElement.click();
    }

    public void selectElement(By by, String optionValue) {
        WebElement dropdownElement = driver.findElement(by);
        dropdownElement.click();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(optionValue);
    }

    public void clickElementAndSelect(By by, By optionBY) {
        WebElement dropdownElement = driver.findElement(by);
        dropdownElement.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(optionBY));
        option.click();
    }
    public String getTextInAlert(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        String textActual = alert.getText();
        return textActual;
    }

    public void waitAndClick(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        option.click();
    }

    public void sleep(long miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearText(By by) {
        WebElement element = driver.findElement(by);
        element.clear();
    }

    public void clearAndSetText(By by, String value) {
        WebElement element = driver.findElement(by);
        element.clear();
        element.sendKeys(value);
    }
    public String getCss(By by, String value) {
        WebElement element = driver.findElement(by);
        String cssValue = element.getCssValue(value);
        return cssValue;
    }

    public void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
    public void selectDate(By datepickerBy, By dateSelectedBy){
        WebElement datepicker = driver.findElement(datepickerBy);
        datepicker.click();
        WebElement date = driver.findElement(dateSelectedBy);
        date.click();
    }
    public void verifyAlert(String actualAlert, String expectAlert){
        Assert.assertEquals(actualAlert, expectAlert);
    }
}
