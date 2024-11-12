package testcases.History;

import bases.BaseSetup;
import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.History.RoutePage;
import pages.HistoryPage;
import pages.LoginPage;

public class RouteTest extends BaseSetup {
    private WebDriver driver;
    private RoutePage routePage;
    private HistoryPage historyPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        routePage = new RoutePage(driver);
        historyPage = new HistoryPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.loginDefault();
        historyPage.navigateToRoutePage();
    }
//    @Test(priority = 1)
//    public void searchVehiclePlate(){
//        routePage.searchAbsoluteValue(routePage.getPlate(9));
//    }
//    @Test(priority = 2)
//    public void searchCompany(){
//        routePage.searchCompany(routePage.getCompany(1));
//    }
//    @Test(priority = 3)
//    public void routeHistory(){
//        routePage.searchAbsoluteValue(routePage.getPlate(21));
//        routePage.routeHistory(routePage.getId(21), "01/07/2024 00:00", "01/07/2024 23:59");
//    }
    @Test(priority = 3)
    public void routeHistory1(){
        routePage.searchAbsoluteValue(routePage.getPlate(21));
        routePage.routeHistory(routePage.getId(21), "15/07/2024 00:00", "15/07/2024 14:18");
    }
    @Test(priority = 3)
    public void routeHistory2(){
        routePage.searchAbsoluteValue(routePage.getPlate(21));
        routePage.routeHistory(routePage.getId(21), "02/07/2024 00:00", "02/07/2024 23:59");
    }
    @Test(priority = 3)
    public void routeHistory3(){
        routePage.searchAbsoluteValue(routePage.getPlate(21));
        routePage.routeHistory(routePage.getId(21), "03/07/2024 00:00", "03/07/2024 23:59");
    }
    @Test(priority = 3)
    public void routeHistory4(){
        routePage.searchAbsoluteValue(routePage.getPlate(21));
        routePage.routeHistory(routePage.getId(21), "04/07/2024 00:00", "04/07/2024 23:59");
    }
    @Test(priority = 3)
    public void routeHistory5(){
        routePage.searchAbsoluteValue(routePage.getPlate(21));
        routePage.routeHistory(routePage.getId(21), "05/07/2024 00:00", "05/07/2024 23:59");
    }
    @Test(priority = 3)
    public void routeHistory6(){
        routePage.searchAbsoluteValue(routePage.getPlate(21));
        routePage.routeHistory(routePage.getId(21), "06/07/2024 00:00", "06/07/2024 23:59");
    }
    @Test(priority = 3)
    public void routeHistory7(){
        routePage.searchAbsoluteValue(routePage.getPlate(21));
        routePage.routeHistory(routePage.getId(21), "07/07/2024 00:00", "07/07/2024 23:59");
    }
    @Test(priority = 3)
    public void routeHistory8(){
        routePage.searchAbsoluteValue(routePage.getPlate(21));
        routePage.routeHistory(routePage.getId(21), "08/07/2024 00:00", "08/07/2024 23:59");
    }
    @Test(priority = 4)
    public void exportExcelFile(){
        routePage.searchAbsoluteValue(routePage.getPlate(9));
        routePage.verifyExportFileExcel();
    }
    @AfterMethod
    public void close(){
        quitDriver();
    }
}
