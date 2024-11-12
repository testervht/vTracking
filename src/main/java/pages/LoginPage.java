package pages;

import bases.WebUIHelpers;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import bases.ExcelHelpers;

import java.time.Duration;

import static bases.BaseSetup.extent;

public class LoginPage {
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
    }
    private WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private By inputUsername = By.xpath("//input[@id='inputUsername']");
    public static By inputPassword = By.xpath("//input[@id='inputChoosePassword']");
    private By buttonLogin = By.xpath("//button[@id=\"loginButton\"]");
    private By notiUsername = By.id("inputUsernameNoti");
    private By notiPassword = By.id("inputChoosePasswordNoti");
    private By notiPopup = By.cssSelector("span[data-notify='message']");
    public By getInputUsername() {
        return inputUsername;
    }
    private String filePath = "dataTestvTracking.xlsx";
    private String sheetName = "Login";

    public String getUserName(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"username", index);
    }
    public String getPassword(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"password", index);
    }

    public void login(String username, String password){
        webUIHelpers.setText(inputUsername,username);
        webUIHelpers.setText(inputPassword,password);
        webUIHelpers.clickElement(buttonLogin);

        webUIHelpers.sleep(3000);
    }
    public void loginDefault(){
        login(this.getUserName(1),this.getPassword(1));
    }
    public void loginSuccessfully(){
        login(this.getUserName(1),this.getPassword(1));
        String currentURL = driver.getCurrentUrl();
        String expectURL = "https://vtracking.innoway.vn/monitorMapV2";
//        if (expectURL.equals(currentURL)){
//            test.log(Status.PASS, "Dieu huong thanh cong den man Giam sat: " + currentURL); // anhntl52 suggest update text result thành tiếng Việt -> done
//        }
//        else {
//            test.log(Status.FAIL, "URL hien tai: " + currentURL);
//        }
        Assert.assertEquals(currentURL, expectURL);
    }
    public void loginWithoutUsername() {
        login(this.getUserName(2),this.getPassword(2));
        String actualText = webUIHelpers.getText(notiUsername);
        String expectText = "Vui lòng nhập tên đăng nhập hoặc số điện thoại";
        ExtentTest test = extent.createTest("Verify login without username");
        if (expectText.equals(actualText)){
            test.log(Status.PASS, "Noi dung thong bao: " + actualText);
        }
        else {
            test.log(Status.FAIL, "Noi dung thong bao: " + actualText);
        }
        Assert.assertEquals(actualText, expectText);
    }
    public void loginWithoutPassword(){
        login(this.getUserName(3),this.getPassword(3));
        String actualText = webUIHelpers.getText(notiPassword);
        String expectText = "Vui lòng nhập mật khẩu";
        ExtentTest test = extent.createTest("Verify login without password");
        if (expectText.equals(actualText)){
            test.log(Status.PASS, "Noi dung thong bao: " + actualText);
        }
        else {
            test.log(Status.FAIL, "Noi dung thong bao: " + actualText);
        }
        Assert.assertEquals(actualText, expectText);
    }
    public void loginWrongPass(){
        login(this.getUserName(4),this.getPassword(4));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(notiPopup));
        String alertText = alert.getText();
        String expectText = "Tài khoản đăng nhập hoặc mật khẩu không đúng";
        ExtentTest test = extent.createTest("Verify login with wrong password");
        if (expectText.equals(alertText)){
            test.log(Status.PASS, "Noi dung thong bao: " + alertText);
        }
        else {
            test.log(Status.FAIL, "Noi dung thong bao: " + alertText);
        }
        Assert.assertEquals(alertText, expectText);
    }
    public void loginWrongUsername(){
        login(this.getUserName(5),this.getPassword(5));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(notiPopup));
        String alertText = alert.getText();
        String expectText = "Tài khoản đăng nhập hoặc mật khẩu không đúng";
        ExtentTest test = extent.createTest("Verify login with wrong username");
        if (expectText.equals(alertText)){
            test.log(Status.PASS, "Noi dung thong bao: " + alertText);
        }
        else {
            test.log(Status.FAIL, "Noi dung thong bao: " + alertText);
        }
        Assert.assertEquals(alertText, expectText);
    }
    public void loginWrongPassword6times(){
        for (int i = 0; i < 6; i++){
            login(this.getUserName(4),this.getPassword(4));
            webUIHelpers.clearText(inputUsername);
            webUIHelpers.clearText(inputPassword);
        }
        WebElement captcha = driver.findElement(By.id("captcha"));
        ExtentTest test = extent.createTest("Verify login wrong pass 6 times");
        if (captcha.isDisplayed()){
            test.log(Status.PASS, "Hien thi truong nhap captcha");
        }
        else {
            test.log(Status.FAIL, "Khong hien thi truong nhap captcha");
        }
        Assert.assertTrue(captcha.isDisplayed());
    }
    public void loginFirstTime(String username, String password){
        login(username, password);
        webUIHelpers.sleep(5000);
        WebElement tbOTP = driver.findElement(By.id("optPhoneLogin"));
        Assert.assertTrue(tbOTP.isDisplayed());
    }
}
