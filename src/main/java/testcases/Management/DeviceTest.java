package testcases.Management;

import bases.BaseSetup;
import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagePage;
import pages.ManagementPages.DevicePage;

public class DeviceTest extends BaseSetup {
    private WebDriver driver;
    private ManagePage managePage;
    private DevicePage devicePage;
    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        devicePage = new DevicePage(driver);
        managePage = new ManagePage(driver);
        managePage.navigateToManagePage();
        managePage.navigateToDevice();
    }
    @Test(priority = 1)
    public void searchWithoutValue(){
        devicePage.searchWithoutValue();
    }
    @Test(priority = 2)
    public void searchRelativeValue(){
        devicePage.searchRelativeValue();
    }
    @Test(priority = 3)
    public void searchAbsoluteValue(){
        devicePage.searchAbsoluteValue();
    }
    @Test(priority = 4)
    public void searchByStatus(){
        devicePage.searchByStatus();
    }
    @Test(priority = 5)
    public void searchByType(){
        devicePage.searchByType();
    }
    @Test(priority = 6)
    public void searchByCompany(){
        devicePage.searchByCompany();
    }
    @Test(priority = 7)
    public void searchByMultiConditions(){
        devicePage.searchByMultiConditions();
    }
    @Test(priority = 8)
    public void createWithoutRequiredField(){
        devicePage.createWithoutRequiredField();
    }
    @Test(priority = 9)
    public void createWithoutCompany(){
        devicePage.createWithoutCompany();
    }
    @Test(priority = 10)
    public void createWithDuplicateImei(){
        devicePage.createWithDuplicateImei();
    }
    @Test(priority = 11)
    public void createDeviceSuccessfully(){
        devicePage.createDeviceSuccessfully();
    }
    @Test(priority = 12)
    public void editDeviceWithoutRequireField(){
        devicePage.editDeviceWithoutRequireField();
    }
    @Test(priority = 13)
    public void editWithDuplicateImei(){
        devicePage.editWithDuplicateImei();
    }
    @Test(priority = 14)
    public void editDeviceNotConnected(){
        devicePage.editDeviceNotConnected();
    }
    @Test(priority = 15)
    public void deleteDeviceSuccessfully(){
        devicePage.deleteDeviceSuccessfully();
    }
    @Test(priority = 16)
    public void deleteDeviceInstalled(){
        devicePage.deleteDeviceUnsuccessfully("000000000000010");
    }
    @Test(priority = 17)
    public void editDeviceInstalled(){
        devicePage.editDeviceInstalled();
    }
    @AfterMethod
    public void close(){
        quitDriver();
    }
}
