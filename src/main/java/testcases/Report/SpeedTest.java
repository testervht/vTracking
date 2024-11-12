package testcases.Report;

import bases.BaseSetup;
import bases.DriverFactory;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.History.RoutePage;
import pages.LoginPage;
import pages.ReportPage;
import pages.ReportPages.AllReportPage;

import java.util.Map;

public class SpeedTest extends BaseSetup {
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
        reportPage.navigateToSpeedReports();
    }
    @Test(priority = 1)
    public void verifyDefaultCreateBtn(){
        ExtentTest test = extent.createTest("Verify default create button");
        test.assignCategory("Speed report");
        try {
            allReportPage.verifyDefaultCreateBtn(AllReportPage.btnCreate_Speed);
            test.log(Status.PASS, "Create button disable");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Verify default create encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 2)
    public void createNoDataReport(){
        ExtentTest test = extent.createTest("Create no data report");
        test.assignCategory("Speed report");
        try {
            allReportPage.searchAbsoluteValue(
                    routePage.getPlate(6),
                    allReportPage.ddPlate_Speed,
                    routePage.getId(6),
                    allReportPage.btnSave_Speed);
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_Speed,
                    allReportPage.timepicker_end_Speed,
                    "01/08/2024 00:00",
                    "01/08/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_Speed);
            allReportPage.verifyNoti();
            test.log(Status.PASS, "Verified create no data report successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create no data report encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 3)
    public void createReportSuccessfully(){
        ExtentTest test = extent.createTest("Create report successfully");
        test.assignCategory("Speed report");
        try {
            String plate = routePage.getPlate(13);
            allReportPage.searchAbsoluteValue(
                    plate,
                    allReportPage.ddPlate_Speed,
                    routePage.getId(13),
                    allReportPage.btnSave_Speed);
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_Speed,
                    allReportPage.timepicker_end_Speed,
                    "01/08/2024 00:00",
                    "01/08/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_Speed);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_Speed);
            test.log(Status.PASS, "Verified create report successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create report successfully encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 4)
    public void checkExcelFileWithoutData(){
        ExtentTest test = extent.createTest("Check excel file without data");
        test.assignCategory("Speed report");
        try {
            allReportPage.searchAbsoluteValue(
                    routePage.getPlate(6),
                    allReportPage.ddPlate_Speed,
                    routePage.getId(6),
                    allReportPage.btnSave_Speed);
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_Speed,
                    allReportPage.timepicker_end_Speed,
                    "01/08/2024 00:00",
                    "01/08/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_Speed);
            allReportPage.verifyExportWithoutData(allReportPage.btnExport_Speed);
            test.log(Status.PASS, "Verified excel file without data successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Check excel file without data encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 5)
    public void exportFileExcel(){
        ExtentTest test = extent.createTest("Export file excel");
        test.assignCategory("Speed report");
        try {
            String plate = routePage.getPlate(13);
            allReportPage.searchAbsoluteValue(
                    plate,
                    allReportPage.ddPlate_Speed,
                    routePage.getId(13),
                    allReportPage.btnSave_Speed);
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_Speed,
                    allReportPage.timepicker_end_Speed,
                    "01/08/2024 00:00",
                    "01/08/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_Speed);
            Map<Integer, String> headerExpectedValues = Map.of(
                    0, "STT",
                    1, "Biển số",
                    2, "Thời điểm",
                    3, "Tốc độ(km/h)",
                    4, "Vị trí"
            );
            allReportPage.verifyExportFileExcel(allReportPage.btnExport_Speed);
            test.log(Status.PASS, "Verified export file excel successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Export file excel encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @AfterMethod
    public void close(){
        quitDriver();
    }
}

