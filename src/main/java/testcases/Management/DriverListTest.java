package testcases.Management;

import bases.BaseSetup;
import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagePage;
import pages.ManagementPages.DriverListPage;

public class DriverListTest extends BaseSetup {
    private WebDriver driver;
    private DriverListPage driverListPage;
    private ManagePage managePage;
    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        driverListPage = new DriverListPage(driver);
        managePage = new ManagePage(driver);
        managePage.navigateToManagePage();
        managePage.navigateToDriverList();
    }
    @Test(priority = 1)
    public void searchWithoutValue(){
        driverListPage.search("test1246234test", "");
        driverListPage.verifySearchWithoutValue();
    }
    @Test(priority = 2)
    public void searchByTextsearch(){
        driverListPage.search("1111", "");
        driverListPage.verifySearchByText("1111");
    }
    @Test(priority = 3)
    public void createWithoutRequiredField(){
        driverListPage.createDriver("", "", "", "", "", "", "");
        driverListPage.verifyCreateWithoutRequiredField();
    }
    @Test(priority = 3)
    public void verifyCreateWithDuplicateCard(){
        driverListPage.createDriver("Lan Anh auto", "Công ty test 21/11/2024 new", "1276317263716312", "0987561234", "1121321111111111111", "CMT8", "1234767890");
        driverListPage.verifyCreateWithDuplicateCard();
    }
    @Test(priority = 4)
    public void verifyCreateWithDuplicateLicense(){
        driverListPage.createDriver("Lan Anh auto", "Công ty test 21/11/2024 new", "123advb", "0987561234", "111111111111111", "CMT8", "1234767890");
        driverListPage.verifyCreateWithDuplicateLicense();
    }
    @Test(priority = 5)
    public void verifyCreateWithDuplicateID(){
        driverListPage.createDriver("Lan Anh auto", "Công ty test 21/11/2024 new", "122344dvb", "0987561234", "111111112343411", "CMT8", "123432413222");
        driverListPage.verifyCreateWithDuplicateID();
    }
    @Test(priority = 6)
    public void verifyCreateSunccessfully(){
        driverListPage.createDriver("Lan Anh auto", "Công ty test 21/11/2024", "122344dvb", "0987561234", "111111112343411", "CMT8", "123432567222");
        driverListPage.verifyCreateSuccessfully();
        driverListPage.search("Lan Anh auto", "");
        driverListPage.verifySearchByText("Lan Anh auto");
    }
    @Test(priority = 7)
    public void verifyEditWithoutRequired(){
        driverListPage.search("Lan Anh auto", "");
        driverListPage.editDriver("", "", "", "", "", "", "");
        driverListPage.verifyEditWithoutRequiredField();
    }
    @Test(priority = 8)
    public void verifyEditWithDuplicateCard(){
        driverListPage.search("Lan Anh auto", "");
        driverListPage.editDriver("Lan Anh auto edit", "Công ty con của Hiền2", "180987", "0927062024", "270620241518", "HHT", "270620242020");
        driverListPage.verifyCreateWithDuplicateCard();
    }
    @Test(priority = 9)
    public void verifyEditWithDuplicateLicense(){
        driverListPage.search("Lan Anh auto", "");
        driverListPage.editDriver("Lan Anh auto edit", "Công ty con của Hiền2", "27062024", "0927062024", "010138003217", "HHT", "270620242020");
        driverListPage.verifyCreateWithDuplicateLicense();
    }
    @Test(priority = 10)
    public void verifyEditWithDuplicateID(){
        driverListPage.search("Lan Anh auto", "");
        driverListPage.editDriver("Lan Anh auto edit", "Công ty con của Hiền2", "27062024", "0927062024", "270620241518", "HHT", "040087019227");
        driverListPage.verifyCreateWithDuplicateID();
    }
    @Test(priority = 11)
    public void verifyEditSuccessfully(){
        driverListPage.search("Lan Anh auto", "");
        driverListPage.editDriver("Lan Anh auto edit", "Công ty con của Hiền2", "27062024", "0927062024", "270620241518", "HHT", "270620242020");
        driverListPage.verifyEditSuccessfully();
        driverListPage.search("Lan Anh auto edit", "");
        driverListPage.verifySearchByText("Lan Anh auto edit");
    }
    @Test(priority = 11)
    public void deleteUnusedDriver(){
        driverListPage.search("Lan Anh auto edit", "");
        driverListPage.deleteDriver();
        driverListPage.verifyDeleteUnusedDriver();
    }
    @Test(priority = 12)
    public void deleteUsedDriver(){
        driverListPage.search("Nguyễn Hiền 2202", "");
        driverListPage.deleteDriver();
        driverListPage.verifyDeleteUsedDriver();
    }

}
