package testcases.Report;

import bases.DriverFactory;
import bases.ExcelHelpers;
import bases.WebUIHelpers;
import org.openqa.selenium.By;
import pages.ReportPages.AllReportPage;
import pages.ReportPages.JourneyPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class JourneyTest {
    private WebDriver driver;
    private JourneyPage journeyPage;
    private ExcelHelpers excelHelpers;
    private WebUIHelpers webUIHelpers;
    private AllReportPage allReportPage;

    @BeforeMethod
    public void setUpBrowser() throws Exception {
//        driver = new ChromeDriver();
        driver = DriverFactory.getDriver("chrome");
        allReportPage = new AllReportPage(driver);
        webUIHelpers = new WebUIHelpers(driver);
        journeyPage = new JourneyPage(driver);
        this.excelHelpers = new ExcelHelpers();
        System.out.println("Bao cao hanh trinh browser:" + driver);
    }

    @Test //BC_1
    public void NotSelectLicensePlate() {
        journeyPage.NotSelectLicensePlate();
    }

    @Test  //BC_2
    public void NoData() throws Exception {
        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        journeyPage.enterPlate(this.excelHelpers.getCellData("PlateNo", 3));
        journeyPage.EnterCalendar("15", "Th6", "2024", "16", "Th6", "2024");
        webUIHelpers.clickElement(AllReportPage.btnCreate_Journey);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        allReportPage.verifyNoti();
    }

    @Test //BC_3
    public void CreateReportSuccess() throws Exception {
        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        journeyPage.enterPlate(this.excelHelpers.getCellData("PlateNo", 1));
        journeyPage.EnterCalendar("30", "Th7", "2024", "1", "Th8", "2024");
        webUIHelpers.clickElement(AllReportPage.btnCreate_Journey);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_Journey);
    }
    @Test
    //BC_04
    public void ExportExcelNoDate() throws Exception {
        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        journeyPage.enterPlate(this.excelHelpers.getCellData("PlateNo",3));
        journeyPage.EnterCalendar("15", "Th6", "2024", "16", "Th6", "2024");
        webUIHelpers.clickElement(AllReportPage.btnCreate_Journey);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        allReportPage.verifyExportWithoutData(allReportPage.btnExport_Journey);
    }
    @Test //BC_05
    public void ExportExcel() throws Exception {
        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        journeyPage.ExportExcel(15, "12", "Th7", "2024", "12", "Th7", "2024" );
    }
}



