package testcases.AccountManagement;

import bases.BaseSetup;
import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseSetup {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        loginPage = new LoginPage(driver);
    }
    @Test
    public void loginSuccessfully(){
        loginPage.loginSuccessfully();
    }
    @Test
    public void loginWithoutUsername() {
        loginPage.loginWithoutUsername();
    }
    @Test
    public void loginWithoutPassword(){
        loginPage.loginWithoutPassword();
    }
    @Test
    public void loginWrongUsername(){
        loginPage.loginWrongUsername();
    }
    @Test
    public void loginWrongPass(){
        loginPage.loginWrongPass();
    }
    @Test
    public void loginWrongPassword6times(){
        loginPage.loginWrongPassword6times();
    }
//    @Test
//    public void loginFirstTime(){
//        loginPage.loginFirstTime("giangptt", "123");
//    }
    @AfterMethod
    public void close(){
        quitDriver();
    }
}
