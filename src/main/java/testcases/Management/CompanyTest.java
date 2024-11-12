package testcases.Management;

import bases.BaseSetup;
import bases.DriverFactory;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagePage;
import pages.ManagementPages.CompanyPage;

public class CompanyTest extends BaseSetup {
    private WebDriver driver;
    private CompanyPage companyPage;
    private ManagePage managePage;
    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        companyPage = new CompanyPage(driver);
        managePage = new ManagePage(driver);
        managePage.navigateToManagePage();
        managePage.navigateToCompany();
    }

    @Test(priority = 1)
    public void searchWithoutValue(){
        ExtentTest test = extent.createTest("Search Without Value");
        test.assignCategory("CompanyManage");
        try {
            companyPage.searchWithoutValue();companyPage.searchWithoutValue();
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
    public void searchByCompanyName(){
        ExtentTest test = extent.createTest("Search by company name");
        test.assignCategory("CompanyManage");
        try {
            companyPage.searchByCompanyName();
            test.log(Status.PASS, "Verified search by company name successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Search by company name encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 3)
    public void searchByCompanyParent(){
        ExtentTest test = extent.createTest("Search by company parent");
        test.assignCategory("CompanyManage");
        try {
            companyPage.searchByCompanyParent();
            test.log(Status.PASS, "Verified by company parent successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Search by company parent encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 4)
    public void createWithoutUsername(){
        ExtentTest test = extent.createTest("Create without username");
        test.assignCategory("CompanyManage");
        try {
            companyPage.createWithoutUsername();
            test.log(Status.PASS, "Verified create without username successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create without username encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 5)
    public void createWithoutRequireField(){
        ExtentTest test = extent.createTest("Create without required fields");
        test.assignCategory("CompanyManage");
        try {
            companyPage.createWithoutRequireField();
            test.log(Status.PASS, "Verified create without required fields successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create without required fields encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 6)
    public void createWithDuplicateMail(){
        ExtentTest test = extent.createTest("Create with duplicate mail");
        test.assignCategory("CompanyManage");
        try {
            companyPage.createWithDuplicateEmail();
            test.log(Status.PASS, "Verified create with duplicate mail successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create with duplicate mail encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 7)
    public void createWithDuplicateUsername(){
        ExtentTest test = extent.createTest("Create with duplicate username");
        test.assignCategory("CompanyManage");
        try {
            companyPage.createWithDuplicateUsername();
            test.log(Status.PASS, "Verified create with duplicate username successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create with duplicate username encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 8)
    public void createWithDuplicatePhoneAcc(){
        ExtentTest test = extent.createTest("Create with duplicate phone acc");
        test.assignCategory("CompanyManage");
        try {
            companyPage.createWithDuplicatePhoneAcc();
            test.log(Status.PASS, "Verified create with duplicate phone acc successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create with duplicate phone acc encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
//    @Test(priority = 9)
//    public void createWhenClickCancel(){
//        companyPage.createWhenClickCancel();
//    }
    @Test(priority = 10)
    public void createSuccessfully(){
        ExtentTest test = extent.createTest("Create successfully");
        test.assignCategory("CompanyManage");
        try {
            companyPage.createSuccessfully();
            test.log(Status.PASS, "Verified create successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create successfully encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 11)
    public void loginAdminOrg(){
        ExtentTest test = extent.createTest("Login Admin org");
        test.assignCategory("CompanyManage");
        try {
            companyPage.loginAdminOrg();
            test.log(Status.PASS, "Verified login Admin org successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Login Admin org encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
//    @Test(priority = 12)
//    public void editWhenClickCancel(){
//        companyPage.editWhenClickCancel();
//    }
    @Test(priority = 13)
    public void editWithoutRequireField(){
        ExtentTest test = extent.createTest("Edit without required field");
        test.assignCategory("CompanyManage");
        try {
            companyPage.editWithoutRequireField();
            test.log(Status.PASS, "Verified Edit without required field.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Edit without required field encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 14)
    public void editWhenDuplicateEmail(){
        ExtentTest test = extent.createTest("Edit with duplicate email");
        test.assignCategory("CompanyManage");
        try {
            companyPage.editWhenDuplicateEmail();
            test.log(Status.PASS, "Verified Edit with duplicate email");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Edit with duplicate email encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 15)
    public void editWhenDuplicatePhoneAcc(){
        ExtentTest test = extent.createTest("Edit with duplicate phone acc");
        test.assignCategory("CompanyManage");
        try {
            companyPage.editWhenDuplicatePhoneAcc();
            test.log(Status.PASS, "Verified Edit with duplicate phone acc");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Edit with duplicate phone acc encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 16)
    public void editSuccessfully(){
        ExtentTest test = extent.createTest("Edit successfully");
        test.assignCategory("CompanyManage");
        try {
            companyPage.editSuccessfully();
            test.log(Status.PASS, "Verified Edit successfully");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Edit successfully encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
//    @Test(priority = 17)
//    public void loginAfterEditPhoneAcc(){
//        ExtentTest test = extent.createTest("Login after phone acc");
//        test.assignCategory("CompanyManage");
//        try {
//            companyPage.loginAfterEditPhoneAcc();
//            test.log(Status.PASS, "Verified login after phone acc successfully");
//        } catch (AssertionError e) {
//            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
//            throw e; // Rethrow the exception after logging it
//        } catch (Exception e) {
//            test.log(Status.FAIL, "Login after phone acc encountered an error: " + e.getMessage());
//            throw e; // Rethrow the exception after logging it
//        }
//    }
    @Test(priority = 18)
    public void deleteCompanySuccessfull(){
        ExtentTest test = extent.createTest("Delete successfully");
        test.assignCategory("CompanyManage");
        try {
            companyPage.deleteCompanySuccessfully();
            test.log(Status.PASS, "Verified delete successfully");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Delete successfully encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 19)
    public void deleteCompanyWithDevice(){
        ExtentTest test = extent.createTest("Delete company with device");
        test.assignCategory("CompanyManage");
        try {
            companyPage.deleteCompanyWithDevice();
            test.log(Status.PASS, "Verified Delete company with device successfully");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Delete company with device encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 20)
    public void deleteCompanyWithVehicle(){
        ExtentTest test = extent.createTest("Delete company with vehicle");
        test.assignCategory("CompanyManage");
        try {
            companyPage.deleteCompanyWithVehicle();
            test.log(Status.PASS, "Verified Delete company with vehicle successfully");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Delete company with vehicle encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 21)
    public void deleteCompanyWithDeviceAndCar(){
        ExtentTest test = extent.createTest("Delete company with vehicle and device");
        test.assignCategory("CompanyManage");
        try {
            companyPage.deleteCompanyWithDeviceAndCar();
            test.log(Status.PASS, "Verified Delete company with vehicle and device successfully");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Delete company with vehicle and device encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @AfterMethod
    public void close(){
        quitDriver();
    }

}
