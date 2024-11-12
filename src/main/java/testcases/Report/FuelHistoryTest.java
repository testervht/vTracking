package testcases.Report;

import bases.DriverFactory;
import bases.ExcelHelpers;
import bases.WebUIHelpers;
import pages.History.RoutePage;
import pages.ReportPages.AllReportPage;
import pages.ReportPages.FuelHistoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FuelHistoryTest {
    private WebDriver driver;
    private ExcelHelpers excelHelpers;
    private WebUIHelpers webUIHelpers;
    private FuelHistoryPage fuelHistoryPage;
    private RoutePage routePage;
    private AllReportPage allReportPage;
    //private By buttonTaoBaoCao = By.id("ht_createReportBtn");
    //private By textKetQua = By.id("ht_search-result");
    private By notiPopup = By.cssSelector("span[data-notify='message']");
    //private By buttonXuatExcel = By.id("Fuel_Excel");

    @BeforeMethod
    public void setUpBrowser() throws Exception {
//        driver = new ChromeDriver();
        driver = DriverFactory.getDriver("chrome");
        allReportPage = new AllReportPage(driver);
        webUIHelpers = new WebUIHelpers(driver);
        routePage = new RoutePage(driver);
        fuelHistoryPage = new FuelHistoryPage(driver);
        this.excelHelpers = new ExcelHelpers();
        System.out.println("Bao cao Lich su nhien lieu browser:" + driver);
    }
    @Test
    //BC_51
    public void NotSelectLicensePlate() {
        fuelHistoryPage.NotSelectLicensePlater();
    }

    @Test
    //BC_52
    public void NoData() throws Exception {
        fuelHistoryPage.EnterCalendar("12", "Th8", "2024", "12", "Th8", "2024");
        String plate = routePage.getPlate(10);
        allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_FuelHistory, routePage.getId(10), allReportPage.btnSave_FuelHistory);
        allReportPage.createReport(allReportPage.btnCreate_FuelHistory);
        allReportPage.verifyNoti();
    }
    @Test
    //BC_53
    public void CreateReportSuccess() throws Exception {
        this.excelHelpers.setExcelFile("dataTestvTracking.xlsx", "RouteHistory");
        fuelHistoryPage.EnterCalendar("12", "Th7", "2024", "12", "Th7", "2024");
        String plate = routePage.getPlate(15);
        allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_FuelHistory, routePage.getId(15), allReportPage.btnSave_FuelHistory);
        allReportPage.createReport(allReportPage.btnCreate_FuelHistory);
        allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_FuelHistory);
    }

    @Test
    //BC_54
    public void ExportExcelNoDate() throws Exception {
        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        //fuelHistoryPage.enterPlate(this.excelHelpers.getCellData("PlateNo",3));
        fuelHistoryPage.EnterCalendar("15", "Th6", "2024", "16", "Th6", "2024");
        String plate = routePage.getPlate(15);
        allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_FuelHistory, routePage.getId(15), allReportPage.btnSave_FuelHistory);
        allReportPage.createReport(allReportPage.btnCreate_FuelHistory);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        allReportPage.verifyExportWithoutData(allReportPage.btnExport_FuelHistory);

    }
    @Test
    //BC_55
    public void ExportExcelSuccess() throws Exception {
        fuelHistoryPage.ExportExcel(15, "12", "Th7", "2024", "12", "Th7", "2024" );
    }




}
