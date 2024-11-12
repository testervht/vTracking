package pages;

import bases.WebUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdministrationPage {
    public AdministrationPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
        //loginPage.loginDefault();
    }
    private WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private  LoginPage loginPage;

    //get button Quản tri
    private By adminButton = By.cssSelector("a[href=\"/Administration\"]");


    //get các tab trong Quản trị
    private By deviceTypeButton = By.id("deviceTabMenu");
    private By vehicleTypeButton = By.id("VehicleTabMenu");
    public void navigateToAdminPage(){
        webUIHelpers.clickElement(adminButton);
    }
    public void navigateToDevice(){
        webUIHelpers.clickElement(deviceTypeButton);
    }
    public void navigateToVehicle(){
        webUIHelpers.clickElement(vehicleTypeButton);
    }

}
