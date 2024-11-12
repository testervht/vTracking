package pages;

import bases.WebUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HistoryPage {
    public HistoryPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
    }
    private WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private  LoginPage loginPage;
    private By historyBtn = By.cssSelector("a[href=\"/historyMapV2\"]");
    private By videoBtn = By.cssSelector("a[href=\"/PlayBackVideo\"]");
    private By imgBtn = By.cssSelector("a[href=\"/image\"]");
    public void navigateToRoutePage(){
        webUIHelpers.clickElement(historyBtn);
    }
    public void navigateToImagePage(){
        webUIHelpers.clickElement(imgBtn);
    }
    public void navigateToVideoPage(){
        loginPage.loginDefault();
        webUIHelpers.clickElement(historyBtn);
        webUIHelpers.clickElement(videoBtn);
    }
}
