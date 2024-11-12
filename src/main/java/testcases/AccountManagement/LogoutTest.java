package testcases.AccountManagement;

import bases.BaseSetup;
import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.LogoutPage;

public class LogoutTest extends BaseSetup {
    private WebDriver driver;
    private LogoutPage logoutPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        logoutPage = new LogoutPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.loginDefault();
    }
    @Test
    public void logoutSuccessfully(){
        logoutPage.logoutSuccessfully();
    }
    @AfterMethod
    public void close(){
        quitDriver();
    }
//    @AfterSuite
//    public void cleanReport(){
//        extent.flush();
//    }
}
