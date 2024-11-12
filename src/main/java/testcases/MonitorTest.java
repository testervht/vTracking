package testcases;

import bases.BaseSetup;
import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MonitorPage;

public class MonitorTest extends BaseSetup {
    private WebDriver driver;
    private MonitorPage monitorPage;
    private LoginPage loginPage;
    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        monitorPage = new MonitorPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.loginDefault();
    }
    @Test(priority = 1)
    public void searchCompany(){
        monitorPage.searchCompany();
    }
    @Test(priority = 2)
    public void searchVehiclePlate(){
        monitorPage.searchVehiclePlate();
    }
    @Test(priority = 3)
    public void searchRelativeValue(){
        monitorPage.searchRelativeValue();
    }
    @Test(priority = 4)
    public void searchByVehicleType(){
        monitorPage.searchByVehicleType();
    }
    @Test(priority = 5)
    public void searchByStatus(){
        monitorPage.searchByStatus("Đỗ", "Công ty test 21/11/2024");
    }
    @Test(priority = 6)
    public void searchByMultiConditions(){
        monitorPage.searchByMultiConditions("Đỗ", "Máy lu", "Công ty test 21/11/2024");
    }
    @Test(priority = 7)
    public void verifyInfoVehicleWithCam(){
        monitorPage.verifyInfoVehicleWithCam("29A-17286");
    }
    @Test(priority = 8)
    public void verifyInfoVehicleWithoutCam(){
        monitorPage.verifyInfoVehicleWithoutCam();
    }
    @Test(priority = 9)
    public void verifyPopupDetail(){
        String plate = "26A-07116";
        String id = "item-tree-f1b845b2-31d2-4d58-b2fa-a06fe9841eb3";
        monitorPage.verifyPopupDetail(plate, id);
    }
}
