package testcases.Report;

import bases.BaseSetup;
import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.History.RoutePage;
import pages.LoginPage;
import pages.ReportPage;
import pages.ReportPages.AllReportPage;

import java.util.Map;

public class OverSpeedTest extends BaseSetup {
    private WebDriver driver;
    private ReportPage reportPage;
    private AllReportPage allReportPage;
    private RoutePage routePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        allReportPage = new AllReportPage(driver);
        reportPage = new ReportPage(driver);
        routePage = new RoutePage(driver);
        loginPage = new LoginPage(driver);
        loginPage.loginDefault();
        reportPage.navigateToReportPage();
        reportPage.navigateToOverSpeedReports();
    }
    @Test(priority = 1)
    public void verifyDefaultCreateBtn(){
        allReportPage.verifyDefaultCreateBtn(AllReportPage.btnCreate_OS);
    }
    @Test(priority = 2)
    public void createNoDataReport(){
        allReportPage.searchAbsoluteValue(
                routePage.getPlate(6),
                allReportPage.ddPlate_OS,
                routePage.getId(6),
                allReportPage.btnSave_OS);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_OS,
                allReportPage.timepicker_end_OS,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_OS);
        allReportPage.verifyNoti();
    }
    @Test(priority = 3)
    public void createReportSuccessfully(){
        String plate = routePage.getPlate(7);
        allReportPage.searchAbsoluteValue(
                plate,
                allReportPage.ddPlate_OS,
                routePage.getId(7),
                allReportPage.btnSave_OS);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_OS,
                allReportPage.timepicker_end_OS,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_OS);
        allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_OS);
    }
    @Test(priority = 4)
    public void checkExcelFileWithoutData(){
        allReportPage.searchAbsoluteValue(
                routePage.getPlate(6),
                allReportPage.ddPlate_OS,
                routePage.getId(6),
                allReportPage.btnSave_OS);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_OS,
                allReportPage.timepicker_end_OS,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_OS);
        allReportPage.verifyExportWithoutData(allReportPage.btnExport_OS);
    }
    @Test(priority = 5)
    public void exportFileExcel(){
        String plate = routePage.getPlate(7);
        allReportPage.searchAbsoluteValue(
                plate,
                allReportPage.ddPlate_OS,
                routePage.getId(7),
                allReportPage.btnSave_OS);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_OS,
                allReportPage.timepicker_end_OS,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_OS);
        Map<Integer, String> headerExpectedValues = Map.of(
                0, "STT",
                1, "Biển số",
                2, "Lái xe",
                3, "Số GPLX",
                4, "Thời điểm quá tốc độ",
                5, "Tốc độ (km/h)",
                6, "Tốc độ giới hạn (km/h)",
                7, "Tọa độ",
                8, "Vị trí"
        );
        allReportPage.verifyExportFileExcel(allReportPage.btnExport_OS);
    }

//    @AfterMethod
//    public void close(){
//        quitDriver();
//    }
}
