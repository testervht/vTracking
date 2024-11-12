package testcases.Report;

import bases.BaseSetup;
import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.History.RoutePage;
import pages.ReportPage;
import pages.ReportPages.AllReportPage;

import java.util.HashMap;
import java.util.Map;

public class DrivingTimeTest extends BaseSetup {
    private WebDriver driver;
    private ReportPage reportPage;
    private AllReportPage allReportPage;
    private RoutePage routePage;

    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        allReportPage = new AllReportPage(driver);
        reportPage = new ReportPage(driver);
        routePage = new RoutePage(driver);
        reportPage.navigateToReportPage();
        reportPage.navigateToDrivingTime();
    }
    @Test(priority = 1)
    public void verifyDefaultCreateBtn(){
        allReportPage.verifyDefaultCreateBtn(AllReportPage.btnCreate_DTime);
    }
    @Test(priority = 2)
    public void createNoDataReport(){
        allReportPage.searchAbsoluteValue(routePage.getPlate(6), allReportPage.ddPlate_DTime, routePage.getId(6), allReportPage.btnSave_DTime);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_DTime,
                allReportPage.timepicker_end_DTime,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_DTime);
        allReportPage.verifyNoti();
    }
    @Test(priority = 3)
    public void createReportSuccessfully(){
        String plate = routePage.getPlate(15);
        allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_DTime, routePage.getId(15), allReportPage.btnSave_DTime);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_DTime,
                allReportPage.timepicker_end_DTime,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_DTime);
        allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_DTime);
    }
    @Test(priority = 4)
    public void checkExcelFileWithoutData(){
        allReportPage.searchAbsoluteValue(routePage.getPlate(6), allReportPage.ddPlate_DTime, routePage.getId(6), allReportPage.btnSave_DTime);
        allReportPage.createReport(allReportPage.btnCreate_DTime);
        allReportPage.verifyExportWithoutData(allReportPage.btnExport_DTime);
    }
    @Test(priority = 5)
    public void exportFileExcel(){
        String plate = routePage.getPlate(7);
        allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_DTime, routePage.getId(7), allReportPage.btnSave_DTime);
        allReportPage.createReport(allReportPage.btnCreate_DTime);
//        Map<Integer, String> headerExpectedValues = new HashMap<>();
//        headerExpectedValues.put(0, "STT");
//        headerExpectedValues.put(1, "Biển số");
//        headerExpectedValues.put(2, "Lái xe");
//        headerExpectedValues.put(3, "GPLX");
//        headerExpectedValues.put(4, "Thời điểm bắt đầu");
//        headerExpectedValues.put(5, "Toạ độ bắt đầu");
//        headerExpectedValues.put(6, "Vị trí bắt đầu");
//        headerExpectedValues.put(7, "Thời điểm kết thúc");
//        headerExpectedValues.put(8, "Toạ độ kết thúc");
//        headerExpectedValues.put(9, "Vị trí kết thúc");
//        headerExpectedValues.put(10, "Thời gian lái xe");
        allReportPage.verifyExportFileExcel(allReportPage.results_DTime);
    }
    @AfterMethod
    public void close(){
        quitDriver();
    }
}
