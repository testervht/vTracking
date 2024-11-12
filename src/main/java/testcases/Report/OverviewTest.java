package testcases.Report;

import bases.BaseSetup;
import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.History.RoutePage;
import pages.LoginPage;
import pages.ReportPage;
import pages.ReportPages.AllReportPage;

import java.util.HashMap;
import java.util.Map;

public class OverviewTest extends BaseSetup {
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
        reportPage.navigateToOverviewReports();
    }
    @Test(priority = 1)
    public void verifyDefaultCreateBtn(){
        allReportPage.verifyDefaultCreateBtn(AllReportPage.btnCreate_O);
    }
    @Test(priority = 2)
    public void createNoDataReport(){
        allReportPage.searchAbsoluteValue(
                routePage.getPlate(6),
                allReportPage.ddPlate_O,
                routePage.getId(6),
                allReportPage.btnSave_O);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_O,
                allReportPage.timepicker_end_O,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_O);
        allReportPage.verifyNoti();
    }
    @Test(priority = 3)
    public void createReportSuccessfully(){
        String plate = routePage.getPlate(7);
        allReportPage.searchAbsoluteValue(
                plate,
                allReportPage.ddPlate_O,
                routePage.getId(7),
                allReportPage.btnSave_O);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_O,
                allReportPage.timepicker_end_O,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_O);
        allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_O);
    }
    @Test(priority = 4)
    public void checkExcelFileWithoutData(){
        allReportPage.searchAbsoluteValue(
                routePage.getPlate(6),
                allReportPage.ddPlate_O,
                routePage.getId(6),
                allReportPage.btnSave_O);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_O,
                allReportPage.timepicker_end_O,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_O);
        allReportPage.verifyExportWithoutData(allReportPage.btnExport_O);
    }
    @Test(priority = 5)
    public void exportFileExcel(){
        String plate = routePage.getPlate(7);
        allReportPage.searchAbsoluteValue(
                plate,
                allReportPage.ddPlate_O,
                routePage.getId(7),
                allReportPage.btnSave_O);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_O,
                allReportPage.timepicker_end_O,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_O);
        Map<Integer, String> headerExpectedValues = new HashMap<>();
        headerExpectedValues.put(0, "STT");
        headerExpectedValues.put(1, "Biển số");
        headerExpectedValues.put(2, "Lái xe");
        headerExpectedValues.put(3, "Ngày");
        headerExpectedValues.put(4, "Quãng đường vận hành (Km)");
        headerExpectedValues.put(5, "Vận tốc trung bình  (Km/h)");
        headerExpectedValues.put(6, "Vận tốc lớn nhất (Km/h)");
        headerExpectedValues.put(7, "Số lần quá tốc độ");
        headerExpectedValues.put(8, "Số lần đóng/ mở cửa");
        headerExpectedValues.put(9, "Số lần dừng/đỗ");
        headerExpectedValues.put(10, "Tổng thời gian dừng/đỗ");
        headerExpectedValues.put(11, "Tổng nhiên liệu tiêu hao theo định mức (L)");
        headerExpectedValues.put(12, "Nhiên liệu tiêu hao thực tế (L)");
        allReportPage.verifyExportFileExcel(allReportPage.btnExport_O);
    }
//    @AfterMethod
//    public void close(){
//        quitDriver();
//    }
}
