package pages;

import bases.WebUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ManagePage {
    public ManagePage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
    }
    private WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private  LoginPage loginPage;

    //get button Quản lý
    private By manageButton = By.cssSelector("a[href=\"/manageCompany\"]");


    //get các tab trong Quản lý
    private By vehicleButton = By.id("vehicleTabMenu");
    private By driverListButton = By.id("driverListTabMenu");
    private By userButton = By.id("userTabMenu");
    private By deviceButton = By.id("deviceTabMenu");
    private By roleButton = By.id("roleTabMenu");
    private By companyButton = By.id("companyTabMenu");
    public void navigateToManagePage(){
//        loginPage.loginDefault();
        webUIHelpers.clickElement(manageButton);
    }
    public void navigateToManage(){
        webUIHelpers.clickElement(manageButton);
    }
    public void navigateToCompany(){
        webUIHelpers.clickElement(companyButton);
    }
    public void navigateToDevice(){
        webUIHelpers.clickElement(deviceButton);
    }
    public void navigateToVehicle(){
        webUIHelpers.clickElement(vehicleButton);
    }
    public void navigateToRole(){
        webUIHelpers.clickElement(roleButton);
    }
    public void navigateToDriverList(){
        webUIHelpers.clickElement(driverListButton);
    }
    public void navigateToUser(){
        webUIHelpers.clickElement(userButton);
    }
    public void navigateToTabMenu(By by){
        webUIHelpers.clickElement(by);
    }
}
