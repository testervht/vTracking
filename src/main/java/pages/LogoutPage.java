package pages;

import bases.WebUIHelpers;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static bases.BaseSetup.extent;


public class LogoutPage {
    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
    }
    private WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private  LoginPage loginPage;
    private By userInfoButton = By.id("user-info");
    private By logoutButton = By.cssSelector("a[href='/logout']");
    public void logoutSuccessfully(){
        this.logout();
        String currentURL = driver.getCurrentUrl();
        String expectURL = "https://vtracking.innoway.vn/";
        Assert.assertEquals(currentURL,expectURL);
    }
    public void logout(){
        webUIHelpers.clickElement(userInfoButton);
        webUIHelpers.clickElement(logoutButton);
    }
}
