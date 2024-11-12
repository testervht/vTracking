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

import java.util.Map;

public class ActivityDetailTest extends BaseSetup {
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
        reportPage.navigateToReportPage();
        reportPage.navigateToActivityDetailReports();
    }
    @Test(priority = 1)
    public void verifyDefaultCreateBtn(){
        allReportPage.verifyDefaultCreateBtn(AllReportPage.btnCreate_Ad);
    }
    @Test(priority = 2)
    public void createNoDataReport(){
        allReportPage.searchAbsoluteValue(
                routePage.getPlate(6),
                allReportPage.ddPlate_Ad,
                routePage.getId(6),
                allReportPage.btnSave_Ad);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_Ad,
                allReportPage.timepicker_end_Ad,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_Ad);
        allReportPage.verifyNoti();
    }
    @Test(priority = 3)
    public void createReportSuccessfully(){
        allReportPage.searchAbsoluteValue(
                routePage.getPlate(7),
                allReportPage.ddPlate_Ad,
                routePage.getId(7),
                allReportPage.btnSave_Ad);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_Ad,
                allReportPage.timepicker_end_Ad,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_Ad);
        allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_Ad);
    }
    @Test(priority = 4)
    public void checkExcelFileWithoutData(){
        allReportPage.searchAbsoluteValue(
                routePage.getPlate(6),
                allReportPage.ddPlate_Ad,
                routePage.getId(6),
                allReportPage.btnSave_Ad);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_Ad,
                allReportPage.timepicker_end_Ad,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_Ad);
        allReportPage.verifyExportWithoutData(allReportPage.btnExport_Ad);
    }
    @Test(priority = 5)
    public void exportFileExcel(){
        String plate = routePage.getPlate(7);
        allReportPage.searchAbsoluteValue(
                plate,
                allReportPage.ddPlate_Ad,
                routePage.getId(7),
                allReportPage.btnSave_Ad);
        allReportPage.chooseDateTime(
                allReportPage.timepicker_start_Ad,
                allReportPage.timepicker_end_Ad,
                "01/08/2024 00:00",
                "01/08/2024 23:59");
        allReportPage.createReport(allReportPage.btnCreate_Ad);
//        Map<Integer, String> headerExpectedValues = Map.of(
//                0, "STT",
//                1, "Biển số",
//                2, "Lái xe",
//                3, "Ngày",
//                4, "Từ giờ",
//                5, "Đến giờ",
//                6, "Thời gian hoạt động",
//                7, "Số Km",
//                8, "Địa chỉ đi",
//                9, "Địa chỉ đến"
//        );
        allReportPage.verifyExportFileExcel(allReportPage.btnExport_Ad);
    }
//    @AfterMethod
//    public void close(){
//        quitDriver();
//    }
}
