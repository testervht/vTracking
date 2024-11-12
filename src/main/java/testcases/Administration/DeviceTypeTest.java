package testcases.Administration;

import bases.BaseSetup;
import bases.DriverFactory;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AdministrationPage;
import pages.AdministrationPages.DeviceTypePage;
import pages.LoginPage;

public class DeviceTypeTest extends BaseSetup {
    private WebDriver driver;
    private DeviceTypePage deviceTypePage;
    private AdministrationPage administrationPage;
    private LoginPage loginPage;
    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        loginPage = new LoginPage(driver);
        deviceTypePage = new DeviceTypePage(driver);
        administrationPage = new AdministrationPage(driver);
        loginPage.loginDefault();
        administrationPage.navigateToAdminPage();
        administrationPage.navigateToDevice();
    }
    @Test(priority = 1)
    public void searchWithoutValue() {
        ExtentTest test = extent.createTest("Search Without Value");
        test.assignCategory("DeviceTypeAdministration");
        try {
            deviceTypePage.search("aksjdhjkldsf");
            deviceTypePage.verifySearchWithoutValue();
            test.log(Status.PASS, "Verified search without value successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Search without value encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 2)
    public void searchByText() {
        ExtentTest test = extent.createTest("Search by text search");
        test.assignCategory("DeviceTypeAdministration");
        try {
            deviceTypePage.search("DashCam");
            deviceTypePage.verifySearchByText("DashCam");
            test.log(Status.PASS, "Verified search by text search successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Search by text search encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 3)
    public void createWithoutName() {
        ExtentTest test = extent.createTest("Create without name");
        test.assignCategory("DeviceTypeAdministration");
        try {
            deviceTypePage.clickBtnCreate();
            deviceTypePage.fillInfoDeivceType("", "", "", "", "", "", "");
            deviceTypePage.clickBtnSave();
            deviceTypePage.verifyCreateWithoutName();
            test.log(Status.PASS, "Verified create without name successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create without name encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 3)
    public void createWithoutGPS() {
        ExtentTest test = extent.createTest("Create without GPS");
        test.assignCategory("DeviceTypeAdministration");
        try {
            deviceTypePage.clickBtnCreate();
            deviceTypePage.fillInfoDeivceType("AutoTest 1", "", "Có độ mạnh sóng", "", "", "", "");
            deviceTypePage.clickBtnSave();
            deviceTypePage.verifyCreateWithoutGPS();
            test.log(Status.PASS, "Verified create without GPS successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create without name encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 4)
    public void createWithDuplicateName() {
        ExtentTest test = extent.createTest("Create with duplicate name");
        test.assignCategory("DeviceTypeAdministration");
        try {
            deviceTypePage.clickBtnCreate();
            deviceTypePage.fillInfoDeivceType("DashCam", "ATC", "", "", "", "Giám sát hành trình", "");
            deviceTypePage.clickBtnSave();
            deviceTypePage.verifyWhenDuplicateName();
            test.log(Status.PASS, "Verified create with duplicate name successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create with duplicate name encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 5)
    public void createDeviceTypeHaveWave() {
        ExtentTest test = extent.createTest("Create device type have wave successfully");
        test.assignCategory("DeviceTypeAdministration");
        try {
            deviceTypePage.clickBtnCreate();
            deviceTypePage.fillInfoDeivceType("AutoTest 1", "AT1", "Có độ mạnh sóng", "100", "100", "Giám sát hành trình", "description");
            deviceTypePage.clickBtnSave();
            deviceTypePage.verifyCreateSuccessfully();
            deviceTypePage.search("AutoTest 1");
            deviceTypePage.verifySearchByText("AutoTest 1");
            test.log(Status.PASS, "Verified create device type have wave successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create successfully device type have wave encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
//    @Test(priority = 6)
//    public void createDeviceTypeNotWave() {
//        ExtentTest test = extent.createTest("Create device type not wave successfully");
//        test.assignCategory("DeviceTypeAdministration");
//        try {
//            deviceTypePage.clickBtnCreate();
//            deviceTypePage.fillInfoDeivceType("AutoTest 2", "AT2", "Không có độ mạnh sóng", "", "", "Giám sát hành trình", "description");
//            deviceTypePage.clickBtnSave();
//            deviceTypePage.verifyCreateSuccessfully();
//            deviceTypePage.search("AutoTest 2");
//            deviceTypePage.verifySearchByText("AutoTest 2");
//            test.log(Status.PASS, "Verified create device type not wave successfully.");
//        } catch (AssertionError e) {
//            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
//            throw e; // Rethrow the exception after logging it
//        } catch (Exception e) {
//            test.log(Status.FAIL, "Create successfully device type not wave encountered an error: " + e.getMessage());
//            throw e; // Rethrow the exception after logging it
//        }
//    }
    @Test(priority = 7)
    public void editWithoutRequiredFields() {
        ExtentTest test = extent.createTest("Edit without required fields");
        test.assignCategory("DeviceTypeAdministration");
        try {
            deviceTypePage.search("AutoTest 1");
            deviceTypePage.clickBtnEdit();
            deviceTypePage.editInfoDeviceType("", "", "", "", "", "Giám sát hành trình", "");
            deviceTypePage.clickBtnSaveEdit();
            deviceTypePage.verifyEditWithoutRequiredFields();
            test.log(Status.PASS, "Verified edit without name successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Edit without name an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 7)
    public void editWithDuplicateName() {
        ExtentTest test = extent.createTest("Edit with duplicate name");
        test.assignCategory("DeviceTypeAdministration");
        try {
            deviceTypePage.search("AutoTest 1");
            deviceTypePage.clickBtnEdit();
            deviceTypePage.editInfoDeviceType("DashCam", "", "", "100", "100", "", "");
            deviceTypePage.clickBtnSaveEdit();
            deviceTypePage.verifyWhenDuplicateName();
            test.log(Status.PASS, "Verified edit with duplicate name successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Edit with duplicate name encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 8)
    public void editSuccessfully() {
        ExtentTest test = extent.createTest("Edit successfully");
        test.assignCategory("RoleManage");
        try {
            deviceTypePage.search("AutoTest 1");
            deviceTypePage.clickBtnEdit();
            deviceTypePage.editInfoDeviceType("AutoTest edit", "ATE", "Có độ mạnh sóng", "120", "120", "Cam Nghị Định 10", "description edit");
            deviceTypePage.clickBtnSaveEdit();
            deviceTypePage.verifyEditSuccessfully();
            deviceTypePage.search("AutoTest edit");
            deviceTypePage.verifySearchByText("AutoTest edit");
            test.log(Status.PASS, "Verified edit successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Edit successfully encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @AfterMethod
    public void close(){
        quitDriver();
    }
}
