package testcases;
//cmt fix bug commit
import bases.*;
import org.openqa.selenium.By;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import pages.AdministrationPages.ComponentPage;
import pages.AdministrationPages.DeviceTypePage;
import pages.AdministrationPages.TransportPage;
import pages.AdministrationPages.TypeOfVehiclePage;
import pages.History.RoutePage;
import pages.ManagementPages.*;
import pages.ReportPages.*;
import pages.ReportPages.AllReportPage;
//import pages.ReportPages.FuelHistoryPage;
import pages.ReportPages.FuelDistancePage;
import pages.ReportPages.FuelHistoryPage;
import pages.ReportPages.JourneyPage;
import pages.History.ImagePage;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;


public class UpCodeTest extends BaseSetup {
    private WebDriver driver;
    private ExcelHelpers excelHelpers;
    private WebUIHelpers webUIHelpers;
    private LoginPage loginPage;
    private LogoutPage logoutPage;
    private RoutePage routePage;
    private HistoryPage historyPage;
    private ManagePage managePage;
    private CompanyPage companyPage;
    private UserPage userPage;
    private DevicePage devicePage;
    private DriverListPage driverListPage;
    private RolePage rolePage;
    private VehiclePage vehiclePage;
    private AllReportPage allReportPage;
    private ReportPage reportPage;
    private JourneyPage journeyPage;
    private FuelHistoryPage fuelHistoryPage;
    private FuelDistancePage fuelDistancePage;
    private TemperaturePage temperaturePage;
    private WorkTimePage workTimePage;
    private AdministrationPage administrationPage;
    private MonitorPage monitorPage;
    private DeviceTypePage deviceTypePage;
    private ComponentPage componentPage;
    private TransportPage transportPage;
    private TypeOfVehiclePage typeOfVehiclePage;
    private ImagePage imagePage;
    private DashBoardPage dashBoardPage;

    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        loginPage = new LoginPage(driver);
        logoutPage = new LogoutPage(driver);
        loginPage = new LoginPage(driver);
        monitorPage = new MonitorPage(driver);
        routePage = new RoutePage(driver);
        historyPage = new HistoryPage(driver);
        reportPage = new ReportPage(driver);
        allReportPage = new AllReportPage(driver);
        managePage = new ManagePage(driver);
        rolePage = new RolePage(driver);
        userPage = new UserPage(driver);
        companyPage = new CompanyPage(driver);
        vehiclePage = new VehiclePage(driver);
        webUIHelpers = new WebUIHelpers(driver);
        devicePage = new DevicePage(driver);
        driverListPage = new DriverListPage(driver);
        deviceTypePage = new DeviceTypePage(driver);
        dashBoardPage = new DashBoardPage(driver);
        administrationPage = new AdministrationPage(driver);
        componentPage = new ComponentPage(driver);
        transportPage = new TransportPage(driver);
        typeOfVehiclePage = new TypeOfVehiclePage(driver);
        dashBoardPage = new DashBoardPage(driver);
        imagePage = new ImagePage(driver);
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));//adding wait for driver
    }
    // Dang nhap
    @Test(priority = 1, description = "Kiem tra dang nhap thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_1() {
        ExtentTest test = extent.createTest("Kiem tra dang nhap thanh cong");
        test.assignCategory("Upcode 1");
        try {
            loginPage.loginSuccessfully();
            test.log(Status.PASS, "Dang nhap thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Dang nhap khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Dang xuat
    @Test(priority = 2, description = "Kiem tra dang xuat thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_2() {
        ExtentTest test = extent.createTest("Kiem tra dang xuat thanh cong");
        test.assignCategory("Upcode 2");
        try {
            loginPage.loginDefault();
            logoutPage.logoutSuccessfully();
            test.log(Status.PASS, "Dang xuat thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Dang xuat khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Dashboard
    @Test(priority = 3, description = "Kiem tra thong tin tong quan cua phuong tien", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_3() throws Exception { //: Pick test case -->done
        ExtentTest test = extent.createTest("Kiem tra thong tin tong quan cua phuong tien");
        test.assignCategory("Upcode 3");
        try {
            loginPage.loginDefault();
            dashBoardPage.navigateToDashBoardPage();
            dashBoardPage.ThongTinTongQuan(); // Hiennt85 Xử lý trường hợp không get được API==> testcase failed
            test.log(Status.PASS, "Kiem tra thong tin tong quan cua phuong tien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra thong tin tong quan cua phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 4, description = "Kiem tra thong tin hoat dong cua phuong tien", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_4() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra thong tin hoat dong cua phuong tien");
        test.assignCategory("Upcode 4");
        try {
            loginPage.loginDefault();
            dashBoardPage.navigateToDashBoardPage();
            dashBoardPage.BieuDoTongSoKM();
            dashBoardPage.BieuDoThoiGianLaiXe(); // Hiennt85 Xử lý trường hợp không get được API==> testcase failed
            test.log(Status.PASS, "Kiem tra thong tin hoat dong cua phuong tien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra thong tin hoat dong cua phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 5, description = "Kiem tra hien thi thong tin canh bao cua phuong tien", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_5() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra hien thi thong tin canh bao cua phuong tien");
        test.assignCategory("Upcode 5");
        try {
            loginPage.loginDefault();
            dashBoardPage.navigateToDashBoardPage();
            dashBoardPage.PhuongTienSapHetHanSung();
            dashBoardPage.PhuongTienDaHetHanSuDung(); // Hiennt85 Xử lý trường hợp không get được API==> testcase failed
            test.log(Status.PASS, "Kiem tra hien thi thong tin canh bao cua phuong tien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra hien thi thong tin canh bao cua phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    // Giam sat truc tuyen
    @Test(priority = 6, description = "Kiem tra tim kiem phuong tien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_6() {
        ExtentTest test = extent.createTest("Kiem tra tim kiem phuong tien thanh cong");
        test.assignCategory("Upcode 6");
        try {
            loginPage.loginDefault();
            monitorPage.searchByMultiConditions("Đỗ", "Máy lu", "Công ty test 21/11/2024");
            test.log(Status.PASS, "Kiem tra tim kiem phuong tien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tim kiem phuong tien that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 7, description = "Kiem tra thong tin hien thị tren popup khi nhan vao 1 xe gan camera", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_7() {
        ExtentTest test = extent.createTest("Kiem tra thong tin hien thị tren popup khi nhan vao 1 xe gan camera");
        test.assignCategory("Upcode 7");
        try {
            loginPage.loginDefault();
            monitorPage.verifyInfoVehicleWithCam("29A-17286");
            test.log(Status.PASS, "Kiem tra thong tin hien thi tren popup thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra thong tin hien thi tren popup that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 8, description = "Kiem tra thong tin hien thị tren popup thong tin chi tiet", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_8() {
        ExtentTest test = extent.createTest("Kiem tra thong tin hien thị tren popup thong tin chi tiet");
        test.assignCategory("Upcode 8");
        try {
            String plate = "26A-07116";
            String id = "item-tree-f1b845b2-31d2-4d58-b2fa-a06fe9841eb3";
            loginPage.loginDefault();
            monitorPage.verifyPopupDetail(plate, id);
            test.log(Status.PASS, "Kiem tra thong tin hien thi tren popup chi tiet thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra thong tin hien thi tren popup chi tiet that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Lịch su hanh trinh (Lo trinh)
    @Test(priority = 9, description = "Kiem tra tim kiem phuong tien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_9() {
        ExtentTest test = extent.createTest("Kiem tra tim kiem phuong tien thanh cong");
        test.assignCategory("Upcode 9");
        try {
            loginPage.loginDefault();
            historyPage.navigateToRoutePage();
            routePage.searchAbsoluteValue(routePage.getPlate(9));
            test.log(Status.PASS, "Kiem tra tim kiem phuong tien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tim kiem phuong tien that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 10, description = "Kiem tra xem danh sach lo trinh thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_10() {
        ExtentTest test = extent.createTest("Kiem tra xem danh sach lo trinh thanh cong");
        test.assignCategory("Upcode 10");
        try {
            loginPage.loginDefault();
            historyPage.navigateToRoutePage();
            routePage.searchAbsoluteValue(routePage.getPlate(9));
            routePage.routeHistory(routePage.getId(9), "25/09/2024 00:00", "25/09/2024 12:00");
            routePage.verifyRouteHistory();
            test.log(Status.PASS, "Kiem tra xem danh sach lo trinh thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xem danh sach lo trinh that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 11, description = "Kiem tra tong quan lo trinh", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_11() {ExtentTest test = extent.createTest("Kiem tra tong quan lo trinh");
        test.assignCategory("Upcode 11");
        try {
            loginPage.loginDefault();
            historyPage.navigateToRoutePage();
            routePage.searchAbsoluteValue(routePage.getPlate(21));
            routePage.routeOverview(routePage.getId(21), "25/09/2024 00:00", "25/09/2024 09:00");
            test.log(Status.PASS, "Kiem tra tong quan lo trinh thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tong quan lo trinh that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 12, description = "Kiem tra khi click button xuat file lich su hanh trinh thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_12() {ExtentTest test = extent.createTest("Kiem tra khi click button xuat file lich su hanh trinh thanh cong");
        test.assignCategory("Upcode 12");
        try {
            loginPage.loginDefault();
            historyPage.navigateToRoutePage();
            routePage.searchAbsoluteValue(routePage.getPlate(9));
            routePage.routeHistory(routePage.getId(9), "25/09/2024 00:00", "25/09/2024 11:59");
            routePage.verifyExportFileExcel();
            test.log(Status.PASS, "Kiem tra khi click button xuat file lich su hanh trinh thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra khi click button xuat file lich su hanh trinh that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Lich su hinh anh
    @Test(priority = 13, description = "Kiem tra tim kiem hinh anh thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_13() {
        ExtentTest test = extent.createTest("Kiem tra tim kiem hinh anh thanh cong");
        test.assignCategory("Upcode 13");
        try {
            loginPage.loginDefault();
            historyPage.navigateToRoutePage();
            historyPage.navigateToImagePage();
            imagePage.clickDDChoosePlate();
            imagePage.setSearchKey(routePage.getPlate(24));
            imagePage.verifyResult(routePage.getPlate(24));
            imagePage.clickResult();
            imagePage.tickCheckboxPlate(routePage.getId(24));
            imagePage.clickBtnChoosePlate();
            imagePage.selectStartDate("25/09/2024 00:00");
            imagePage.selectEndDate("26/09/2024 23:59");
            imagePage.selectChanel("Kênh 1");
//            imagePage.clickBtnSearch();
            imagePage.verifyNumAndInfoImageWithFilters("Kênh 1", "25/09/2024 00:00", "26/09/2024 23:59");
            imagePage.getInfoImage(10);
            test.log(Status.PASS, "Kiem tra tim kiem hinh anh thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tim kiem hinh anh that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 14, description = "Kiem tra xem chi tiet 1 anh", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_14() {
        ExtentTest test = extent.createTest("Kiem tra xem chi tiet 1 anh");
        test.assignCategory("Upcode 14");
        try {
            loginPage.loginDefault();
            historyPage.navigateToRoutePage();
            historyPage.navigateToImagePage();
            imagePage.clickDDChoosePlate();
            imagePage.setSearchKey(routePage.getPlate(24));
            imagePage.verifyResult(routePage.getPlate(24));
            imagePage.clickResult();
            imagePage.tickCheckboxPlate(routePage.getId(24));
            imagePage.clickBtnChoosePlate();
            imagePage.selectStartDate("25/09/2024 00:00");
            imagePage.selectEndDate("26/09/2024 23:59");
            imagePage.selectChanel("Kênh 1");
            imagePage.clickBtnSearch();
            imagePage.clickThumbnail(10);
            imagePage.verifyInfoPopupImage(routePage.getPlate(24));
            test.log(Status.PASS, "Kiem tra xem chi tiet 1 anh thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xem chi tiet 1 anh that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Bao cao hanh trinh phuong tien
    @Test(priority = 15, description = "Kiem tra tao bao cao hanh trinh thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_15() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra tao bao cao hanh trinh thanh cong");
        test.assignCategory("Upcode 15");
        try {
            journeyPage = new JourneyPage(driver);
            this.excelHelpers = new ExcelHelpers();
            String plate = routePage.getPlate(26);

            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_Journey, routePage.getId(26), allReportPage.btnSave_Journey);
            this.excelHelpers.setExcelFile("dataTestvTracking.xlsx", "RouteHistory");
            journeyPage.EnterCalendar("25", "Th9", "2024", "26", "Th9", "2024");
            allReportPage.createReport(allReportPage.btnCreate_Journey);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_Journey);
            test.log(Status.PASS, "Kiem tra tao bao cao hanh trinh thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao hanh trinh that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 16, description = "Kiem tra xuat excel va noi dung file bao cao hanh trinh da tai", retryAnalyzer = RetryAnalyzer.class)
    //Upcode_16 - Kiểm tra xuất file excel và nội dung file báo cáo hành trình đã tải
    public void upCode_16() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra xuat excel va noi dung file bao cao hanh trinh da tai");
        test.assignCategory("Upcode 16");
        try {
            journeyPage = new JourneyPage(driver);
            this.excelHelpers = new ExcelHelpers();
            journeyPage.ExportExcel(26, "25", "Th9", "2024", "26", "Th9", "2024");
            test.log(Status.PASS, "Kiem tra xuat excel va noi dung file bao cao hanh trinh da tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat excel va noi dung file bao cao hanh trinh da tai that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Bao cao toc do
    @Test(priority = 17, description = "Kiem tra tao bao cao toc do thanh cong", retryAnalyzer = RetryAnalyzer.class)
    //Upcode_17 - Kiem tra tao bao cao toc do thanh cong
    public void upCode_17(){
        ExtentTest test = extent.createTest("Kiem tra tao bao cao toc do thanh cong");
        test.assignCategory("Upcode 17");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToSpeedReports();
            allReportPage.searchAbsoluteValue(
                    routePage.getPlate(27),
                    allReportPage.ddPlate_Speed,
                    routePage.getId(27),
                    allReportPage.btnSave_Speed);
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_Speed,
                    allReportPage.timepicker_end_Speed,
                    "25/09/2024 00:00",
                    "26/09/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_Speed);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_Speed);
            test.log(Status.PASS, "Kiem tra tao bao cao toc do thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao toc do that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 18, description = "Kiem tra xuat excel va noi dung file bao cao toc do da tai", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_18() {
        ExtentTest test = extent.createTest("Kiem tra xuat excel va noi dung file bao cao toc do da tai");
        test.assignCategory("Upcode 18");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToSpeedReports();
            String plate = routePage.getPlate(27);
            allReportPage.searchAbsoluteValue(
                    plate,
                    allReportPage.ddPlate_Speed,
                    routePage.getId(27),
                    allReportPage.btnSave_Speed);
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_Speed,
                    allReportPage.timepicker_end_Speed,
                    "25/09/2024 00:00",
                    "26/09/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_Speed);
            allReportPage.verifyExportFileExcel(allReportPage.btnExport_Speed);
            test.log(Status.PASS, "Kiem tra xuat excel va noi dung file bao cao toc do da tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat excel va noi dung file bao cao toc do da tai that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Bao cao qua toc do gioi han
    @Test(priority = 19, description = "Kiem tra tao bao cao qua toc do gioi han thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_19() {
        ExtentTest test = extent.createTest("Kiem tra tao bao cao qua toc do gioi han thanh cong");
        test.assignCategory("Upcode 19");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToOverSpeedReports();
            String plate = routePage.getPlate(29);
            allReportPage.searchAbsoluteValue(
                    plate,
                    allReportPage.ddPlate_OS,
                    routePage.getId(29),
                    allReportPage.btnSave_OS);
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_OS,
                    allReportPage.timepicker_end_OS,
                    "01/06/2024 00:00",
                    "30/06/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_OS);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_OS);
            test.log(Status.PASS, "Kiem tra tao bao cao qua toc do gioi han thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao qua toc do gioi han that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 20, description = "Kiem tra xuat excel va noi dung file bao cao qua toc do gioi han da tai")
    public void upCode_20() {
        ExtentTest test = extent.createTest("Kiem tra xuat excel va noi dung file bao cao qua toc do gioi han da tai thanh cong");
        test.assignCategory("Upcode 20");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToOverSpeedReports();
            String plate = routePage.getPlate(29);
            allReportPage.searchAbsoluteValue(
                    plate,
                    allReportPage.ddPlate_OS,
                    routePage.getId(29),
                    allReportPage.btnSave_OS);
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_OS,
                    allReportPage.timepicker_end_OS,
                    "01/06/2024 00:00",
                    "30/06/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_OS);
            allReportPage.verifyExportFileExcel(allReportPage.btnExport_OS);
            test.log(Status.PASS, "Kiem tra xuat excel va noi dung file bao cao qua toc do gioi han da tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat excel va noi dung file bao cao qua toc do gioi han da tai that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 21, description = "Kiem tra tao bao cao thoi gian lai xe lien tuc thanh cong")
    public void upCode_21() {
        ExtentTest test = extent.createTest("Kiem tra tao bao cao thoi gian lai xe lien tuc thanh cong");
        test.assignCategory("Upcode 21");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToDrivingTime();
            String plate = routePage.getPlate(30);
            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_DTime, routePage.getId(30), allReportPage.btnSave_DTime);
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_DTime,
                    allReportPage.timepicker_end_DTime,
                    "25/09/2024 00:00",
                    "26/09/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_DTime);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_DTime);
            test.log(Status.PASS, "Kiem tra tao bao cao thoi gian lai xe lien tuc thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao thoi gian lai xe lien tuc that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 22, description = "Kiem tra xuat excel va noi dung file bao cao thoi gian lai xe lien tuc da tai", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_22() {
        ExtentTest test = extent.createTest("Kiem tra xuat excel va noi dung file bao cao thoi gian lai xe lien tuc da tai thanh cong");
        test.assignCategory("Upcode 22");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToDrivingTime();
            String plate = routePage.getPlate(7);
            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_DTime, routePage.getId(7), allReportPage.btnSave_DTime);
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_DTime,
                    allReportPage.timepicker_end_DTime,
                    "01/08/2024 00:00",
                    "31/08/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_DTime);
            allReportPage.verifyExportFileExcel(allReportPage.btnExport_DTime);
            test.log(Status.PASS, "Kiem tra xuat excel va noi dung file bao cao thoi gian lai xe lien tuc da tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat excel va noi dung file bao cao thoi gian lai xe lien tuc da tai that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Bao cao dung do
    @Test(priority = 23, description = "Kiem tra tao bao cao Dung do thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_23() {
        ExtentTest test = extent.createTest("Kiem tra tao bao cao Dung do thanh cong");
        test.assignCategory("Upcode 23");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToOverParkReports();
            String plate = routePage.getPlate(28);
            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_Park, routePage.getId(28), allReportPage.btnSave_Park);// anhntl52 bổ sung chọn ngày cho báo cáo -> done
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_Park,
                    allReportPage.timepicker_end_Park,
                    "25/09/2024 00:00",
                    "26/09/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_Park);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_Park);

            test.log(Status.PASS, "Kiem tra tao bao cao Dung do thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao Dung do that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 24, description = "Kiem tra xuat file excel và noi dung file bao cao dung do da tai", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_24() {
        ExtentTest test = extent.createTest("Kiem tra xuat file excel và noi dung file bao cao dung do da tai");
        test.assignCategory("Upcode 24");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToOverParkReports();
            String plate = routePage.getPlate(28);
            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_Park, routePage.getId(28), allReportPage.btnSave_Park);// anhntl52 bổ sung chọn ngày cho báo cáo -> done
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_Park,
                    allReportPage.timepicker_end_Park,
                    "25/09/2024 00:00",
                    "26/09/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_Park);
            allReportPage.verifyExportFileExcel(allReportPage.btnExport_Park);
            test.log(Status.PASS, "Kiem tra xuat file excel và noi dung file bao cao dung do da tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat file excel và noi dung file bao cao dung do da tai that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //    //Bao cao tong hop
    @Test(priority = 25, description = "Kiem tra tao bao cao Tong hop thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_25() {
        ExtentTest test = extent.createTest("Kiem tra tao bao cao Tong hop thanh cong");
        test.assignCategory("Upcode 25");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToOverviewReports();
            String plate = routePage.getPlate(31);
            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_O, routePage.getId(31), allReportPage.btnSave_O);// anhntl52 bổ sung chọn ngày cho báo cáo -> done
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_O,
                    allReportPage.timepicker_end_O,
                    "20/09/2024 00:00",
                    "26/09/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_O);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_O);
            test.log(Status.PASS, "Kiem tra tao bao cao Tong hop thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao Tong hop that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 26, description = "Kiem tra xuat file excel và noi dung file bao cao tong hop da tai", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_26() {
        ExtentTest test = extent.createTest("Kiem tra xuat file excel và noi dung file bao cao tong hop da tai");
        test.assignCategory("Upcode 26");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToOverviewReports();
            String plate = routePage.getPlate(31);
            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_O, routePage.getId(31), allReportPage.btnSave_O);// anhntl52 bổ sung chọn ngày cho báo cáo -> done
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_O,
                    allReportPage.timepicker_end_O,
                    "20/09/2024 00:00",
                    "26/09/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_O);
            allReportPage.verifyExportFileExcel(allReportPage.btnExport_O);// anhntl52: cần sửa lại css của nút Export -> done
            test.log(Status.PASS, "Kiem tra xuat file excel và noi dung file bao cao tong hop da tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat file excel và noi dung file bao cao tong hop da tai that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }


    //    //Bao cao chi tiet hoat dong theo ngay
    @Test(priority = 27, description = "Kiem tra tao bao cao Chi tiet hoat dong theo ngay thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_27() {
        ExtentTest test = extent.createTest("Kiem tra tao bao cao Chi tiet hoat dong theo ngay thanh cong");
        test.assignCategory("Upcode 27");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToActivityDetailReports();
            String plate = routePage.getPlate(32);
            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_Ad, routePage.getId(32), allReportPage.btnSave_Ad);// anhntl52 bổ sung chọn ngày cho báo cáo -> done
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_Ad,
                    allReportPage.timepicker_end_Ad,
                    "25/09/2024 00:00",
                    "26/09/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_Ad);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_Ad);
            test.log(Status.PASS, "Kiem tra tao bao cao Chi tiet hoat dong theo ngay thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao Chi tiet hoat dong theo ngay that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }


    @Test(priority = 28, description = "Kiem tra xuat file excel va noi dung file chi tiet hoat đong theo ngay da tai", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_28() {
        ExtentTest test = extent.createTest("Kiem tra xuat file excel va noi dung file chi tiet hoat đong theo ngay da tai");
        test.assignCategory("Upcode 28");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToActivityDetailReports();
            String plate = routePage.getPlate(32);
            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_Ad, routePage.getId(32), allReportPage.btnSave_Ad);// anhntl52 bổ sung chọn ngày cho báo cáo -> done
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_Ad,
                    allReportPage.timepicker_end_Ad,
                    "25/09/2024 00:00",
                    "26/09/2024 23:59");
            allReportPage.createReport(allReportPage.btnCreate_Ad);
            allReportPage.verifyExportFileExcel(allReportPage.btnExport_Ad);// anhntl52 sửa lại css nút Export -> done
            test.log(Status.PASS, "Kiem tra xuat file excel va noi dung file chi tiet hoat đong theo ngay da tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat file excel va noi dung file chi tiet hoat đong theo ngay da tai that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Bao cao tong hop theo xe
    @Test(priority = 29, description = "Kiem tra tao bao cao tong hop theo xe thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_29() {
        ExtentTest test = extent.createTest("Kiem tra tao bao cao tong hop theo xe thanh cong");
        test.assignCategory("Upcode 29");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToTotalCarReports();
            String plate = routePage.getPlate(33);
            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_Tc, routePage.getId(33), allReportPage.btnSave_Tc);
            allReportPage.chooseMonth("Th9");
            allReportPage.createReport(allReportPage.btnCreate_Tc);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_Tc);
            test.log(Status.PASS, "Kiem tra tao bao cao tong hop theo xe thanh cong thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao tong hop theo xe thanh cong that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 30, description = "Kiem tra xuat bao cao tong hop theo xe thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_30() {
        ExtentTest test = extent.createTest("Kiem tra xuat bao cao tong hop theo xe thanh cong");
        test.assignCategory("Upcode 30");
        try {
            loginPage.loginDefault();
            reportPage.navigateToReportPage();
            reportPage.navigateToTotalCarReports();
            String plate = routePage.getPlate(33);
            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_Tc, routePage.getId(33), allReportPage.btnSave_Tc);
            allReportPage.chooseMonth("Th9");
            allReportPage.createReport(allReportPage.btnCreate_Tc);
            allReportPage.verifyExportFileExcel(allReportPage.btnExport_Tc);
            test.log(Status.PASS, "Kiem tra xuat bao cao tong hop theo xe thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat bao cao tong hop theo xe that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Bao cao thoi gian lam viec
    @Test(priority = 31, description = "Kiem tra tao bao cao Thoi gian lam viec thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_31() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra tao bao cao Thoi gian lam viec thanh cong");
        test.assignCategory("Upcode 31");
        try {
            workTimePage = new WorkTimePage(driver);
            this.excelHelpers = new ExcelHelpers();
            String plate = routePage.getPlate(34);

            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_WorkTime, routePage.getId(34), allReportPage.btnSave_WorkTime);
            this.excelHelpers.setExcelFile("dataTestvTracking.xlsx", "RouteHistory");
            workTimePage.EnterCalendar("25", "Th9", "2024", "26", "Th9", "2024");
            allReportPage.createReport(allReportPage.btnCreate_WorkTime);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_WorkTime);
            test.log(Status.PASS, "Kiem tra tao bao cao Thoi gian lam viec thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao Thoi gian lam viec that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 32, description = "Kiem tra xuat bao cao Thoi gian lam viec thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_32() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra xuat bao cao Thoi gian lam viec thanh cong");
        test.assignCategory("Upcode 32");
        try {
            workTimePage = new WorkTimePage(driver);
            this.excelHelpers = new ExcelHelpers();
            workTimePage.ExportExcel(34, "25", "Th9", "2024", "26", "Th9", "2024");
            test.log(Status.PASS, "Kiem tra xuat bao cao Thoi gian lam viec thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat bao cao Thoi gian lam viec that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }


    //Bao cao Lich Su nhien lieu
    @Test(priority = 33, description = "Kiem tra tao bao cao lich su nhien lieu thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_33() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra tao bao cao lich su nhien lieu thanh cong");
        test.assignCategory("Upcode 33");
        try {
            fuelHistoryPage = new FuelHistoryPage(driver);
            this.excelHelpers = new ExcelHelpers();

            String plate = routePage.getPlate(35);
            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_FuelHistory, routePage.getId(35), allReportPage.btnSave_FuelHistory);
            this.excelHelpers.setExcelFile("dataTestvTracking.xlsx", "RouteHistory");
            fuelHistoryPage.EnterCalendar("20", "Th8", "2024", "25", "Th8", "2024");
            allReportPage.createReport(allReportPage.btnCreate_FuelHistory);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_FuelHistory);
            test.log(Status.PASS, "Kiem tra tao bao cao lich su nhien lieu thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao lich su nhien lieu that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 34, description = "Kiem tra xuat excel va noi dung file bao cao lich su nhien lieu da tai", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_34() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra xuat excel va noi dung file bao cao lich su nhien lieu da tai thanh cong");
        test.assignCategory("Upcode 34");
        try {
            fuelHistoryPage = new FuelHistoryPage(driver);
            this.excelHelpers = new ExcelHelpers();
            fuelHistoryPage.ExportExcel(35, "20", "Th8", "2024", "25", "Th8", "2024");
            test.log(Status.PASS, "Kiem tra xuat excel va noi dung file bao cao lich su nhien lieu da tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat excel va noi dung file bao cao lich su nhien lieu da tai that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Bao cao tieu hao nhien lieu theo quang duong
    @Test(priority = 35, description = "Kiem tra tao bao cao Tieu hao nhien lieu theo quang duong thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_35() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra tao bao cao Tieu hao nhien lieu theo quang duong thanh cong");
        test.assignCategory("Upcode 35");
        try {
            fuelDistancePage = new FuelDistancePage(driver);
            this.excelHelpers = new ExcelHelpers();
            String plate = routePage.getPlate(36);

            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_FuelDistance, routePage.getId(36), allReportPage.btnSave_FuelDistance);
            this.excelHelpers.setExcelFile("dataTestvTracking.xlsx", "RouteHistory");
            fuelDistancePage.EnterCalendar("20", "Th8", "2024", "25", "Th8", "2024");
            allReportPage.createReport(allReportPage.btnCreate_FuelDistance);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_FuelDistance);
            test.log(Status.PASS, "Kiem tra tao bao cao Tieu hao nhien lieu theo quang duong thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao Tieu hao nhien lieu theo quang duong that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 36, description = "Kiem tra xuat bao cao tieu hao nhien lieu theo quang duong thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_36() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra xuat bao cao tieu hao nhien lieu theo quang duong thanh cong");
        test.assignCategory("Upcode 36");
        try {
            fuelDistancePage = new FuelDistancePage(driver);
            this.excelHelpers = new ExcelHelpers();

            fuelDistancePage.ExportExcel(36, "20", "Th8", "2024", "25", "Th8", "2024");
            test.log(Status.PASS, "Kiem tra xuat bao cao tieu hao nhien lieu theo quang duong thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat bao cao tieu hao nhien lieu theo quang duong that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Bao cao lich su nhiet do
    @Test(priority = 37, description = "Kiem tra tao bao cao lich su nhiet do thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_37() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra tao bao cao lich su nhiet do thanh cong");
        test.assignCategory("Upcode 37");
        try {
            temperaturePage = new TemperaturePage(driver);
            this.excelHelpers = new ExcelHelpers();
            String plate = routePage.getPlate(23);

            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_Temperature, routePage.getId(23), allReportPage.btnSave_Temperature);
            this.excelHelpers.setExcelFile("dataTestvTracking.xlsx", "RouteHistory");
            temperaturePage.EnterCalendar("25", "Th9", "2024", "26", "Th9", "2024");
            allReportPage.createReport(allReportPage.btnCreate_Temperature);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_Temperature);
            test.log(Status.PASS, "Kiem tra tao bao cao lich su nhiet do thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra tao bao cao lich su nhiet do that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }

    }

    @Test(priority = 38, description = "Kiem tra xuat bao cao lich su nhiet do thanh cong", retryAnalyzer = RetryAnalyzer.class)
//Upcode_38 - Kiem tra xuat bao cao lich su nhiet do thanh cong
    public void upCode_38() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra xuat bao cao lich su nhiet do thanh cong");
        test.assignCategory("Upcode 38");
        try {
            temperaturePage = new TemperaturePage(driver);
            this.excelHelpers = new ExcelHelpers();
            String plate = routePage.getPlate(23);

            allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_Temperature, routePage.getId(23), allReportPage.btnSave_Temperature);
            this.excelHelpers.setExcelFile("dataTestvTracking.xlsx", "RouteHistory");
            temperaturePage.EnterCalendar("25", "Th9", "2024", "26", "Th9", "2024");
            allReportPage.createReport(allReportPage.btnCreate_Temperature);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_Temperature);
            test.log(Status.PASS, "Kiem tra xuat bao cao lich su nhiet do thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Kiem tra xuat bao cao lich su nhiet do that bai. Thong tin chi tiet:" + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    //Quan ly cong ty
    @Test(priority = 39, description = "Kiem tra them cong ty thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_39(){
        ExtentTest test = extent.createTest("Kiem tra them cong ty thanh cong");
        test.assignCategory("Upcode 39");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToCompany();
            companyPage.createSuccessfully();
            test.log(Status.PASS, "Them cong ty thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Them cong ty khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 40, description = "Kiem tra login voi tai khoan admin cong ty cua cong ty vua tao", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_40(){
        ExtentTest test = extent.createTest("Kiem tra login voi tai khoan admin cong ty cua cong ty vua tao");
        test.assignCategory("Upcode 40");
        try {
            loginPage.loginFirstTime("anhntl279","123456aA@");
            test.log(Status.PASS, "Login voi tai khoan admin cong ty cua cong ty vua tao thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Login voi tai khoan admin cong ty cua cong ty vua tao khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 41, description = "Kiem tra khi tim kiem cong ty thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_41(){
        ExtentTest test = extent.createTest("Kiem tra khi tim kiem cong ty thanh cong");
        test.assignCategory("Upcode 41");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToCompany();
            companyPage.searchByCompanyName();
            test.log(Status.PASS, "Tim kiem cong ty thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Tim kiem cong ty khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 42, description = "Kiem tra chinh sua cong ty thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_42(){
        ExtentTest test = extent.createTest("Kiem tra chinh sua cong ty thanh cong");
        test.assignCategory("Upcode 42");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToCompany();
            companyPage.editSuccessfully();
            test.log(Status.PASS, "Chinh sua cong ty thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Chinh sua cong ty khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    //    //Quan ly vai tro
    @Test(priority = 43, description = "Kiem tra thêm vai tro thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_43(){
        ExtentTest test = extent.createTest("Kiem tra thêm vai tro thanh cong");
        test.assignCategory("Upcode 43");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToRole();
            List<String> roles = Arrays.asList("Cấu hình đơn lẻ", "Quản lý công ty");
            rolePage.createRole("Quy luat auto", "Công ty test 21/11/2024", roles);
            rolePage.verifyCreateSuccessfully();
            rolePage.search("Quy luat auto", "");
            rolePage.verifySearchByText("Quy luat auto");
            test.log(Status.PASS, "Thêm vai tro thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Thêm vai tro khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    //    @Test(priority = 38) //Upcode_38 - Kiem tra dang nhap voi tai khoan cong ty vua tao thanh cong
    @Test(priority = 44, description = "Kiem tra tim kiem vai tro thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_44(){
        ExtentTest test = extent.createTest("Kiem tra tim kiem vai tro thanh cong");
        test.assignCategory("Upcode 44");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToRole();
            rolePage.search("quy", "Công ty test 21/11/2024");
            rolePage.verifySearchByText("quy");
            rolePage.verifySearchByCompany("Công ty test 21/11/2024");
            test.log(Status.PASS, "Tim kiem vai tro thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Tim kiem vai tro khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 45, description = "Kiem tra chinh sua vai tro thanh cong", retryAnalyzer = RetryAnalyzer.class) //Upcode_40 - Kiem tra sua cong ty thanh cong
    public void upCode_45(){
        ExtentTest test = extent.createTest("Kiem tra chinh sua vai tro thanh cong");
        test.assignCategory("Upcode 45");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToRole();
            List<String> untickRoles = Arrays.asList("Cấu hình đơn lẻ", "Quản lý công ty");
            List<String> roles = Arrays.asList("Tạo tài khoản người dùng");
            rolePage.search("Quy luat auto", "");
            rolePage.editRole("Quy luat test auto", untickRoles, roles);
            rolePage.verifyEditSuccessfully();
            test.log(Status.PASS, "Chinh sua vai tro thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Chinh sua vai tro khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 46, description = "Kiem tra tinh nang cua tai khoan khi được gan quyen Bao cao", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_46(){
        ExtentTest test = extent.createTest("Kiem tra tinh nang cua tai khoan khi được gan quyen Bao cao");
        test.assignCategory("Upcode 46");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToRole();
            List<String> expectedMenuItems = Arrays.asList(
                    "Giám sát trực tuyến",
                    "Báo cáo",
                    "Trợ giúp"
            );
            List<String> expectedReports = Arrays.asList(
                    "Hành trình phương tiện",
                    "Tốc độ",
                    "Dừng đỗ",
                    "Quá tốc độ giới hạn",
                    "Thời gian lái xe liên tục",
                    "Tổng hợp theo xe",
                    "Tổng hợp theo lái xe",
                    "Vi phạm thời gian lái xe liên tục",
                    "Truyền dữ liệu",
                    "Chi tiết vi phạm tốc độ",
                    "Tổng hợp",
                    "Chi tiết hoạt động theo ngày",
                    "Thời gian làm việc",
                    "Mất GPS",
                    "Mất kết nối",
                    "Quá tốc độ",
                    "Chi tiết sử dụng dịch vụ",
                    "Lịch sử nhiên liệu",
                    "Tiêu hao nhiên liệu theo quãng đường",
                    "Lịch sử nhiệt độ"
            );
            //gan tk voi role full quyen ql danh sach lai xe
            managePage.navigateToUser();
            userPage.editRoleUser("duclong", "Full Báo Cáo");

            //login tk vua duoc gan
            logoutPage.logout();
            loginPage.login("duclong", "123456aA@");
            reportPage.navigateToReport();
            reportPage.navigateToSpeedReports();

            //kt tinh nang tao bao cao toc do
            String plate = routePage.getPlate(25);
            allReportPage.searchAbsoluteValue(
                    plate,
                    allReportPage.ddPlate_Speed,
                    routePage.getId(25),
                    allReportPage.btnSave_Speed);
            allReportPage.chooseDateTime(
                    allReportPage.timepicker_start_Speed,
                    allReportPage.timepicker_end_Speed,
                    "25/09/2024 00:00",
                    "26/09/2024 12:00");
            allReportPage.createReport(allReportPage.btnCreate_Speed);
            allReportPage.verifyCreateSuccessfully(allReportPage.reportTable_Speed);

            //verify menu header
            monitorPage.verifyHeaderMenu(expectedMenuItems);

            //verify so bao cao hien thi
            reportPage.verifyReportTypes(expectedReports);
            test.log(Status.PASS, "Tai khoan thuc hien quyen duoc gan thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Tai khoan thuc hien quyen duoc gan thanh cong khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 47, description = "Kiem tra xoa vai tro thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_47() {
        ExtentTest test = extent.createTest("Kiem tra xoa vai tro thanh cong");
        test.assignCategory("Upcode 47");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToRole();
            rolePage.deleteRole("Quy luat test auto");
            rolePage.verifyDeleteUnusedRole();
            test.log(Status.PASS, "Xoa vai tro thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Xoa vai tro khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Quan ly nguoi dung
    @Test(priority = 48, description = "Kiem tra them nguoi dung thanh cong", retryAnalyzer = RetryAnalyzer.class) //Upcode_48: Kiem tra them nguoi dung thanh cong
    public void upCode_48(){
        ExtentTest test = extent.createTest("Kiem tra them nguoi dung thanh cong");
        test.assignCategory("Upcode 48");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToUser();
            userPage.addUser("upcodetest", "123456aA@", "123456aA@", "Default User", By.xpath("//*[@id=\"jstreeUserAdd_ffe633ce-520e-4c99-a44a-3f8ec6cacee9\"]"), "upcode test", "upcodetest@gmail.com", "0274817462");
            userPage.verifyNoti("Tạo thành công");
            test.log(Status.PASS, "Them nguoi dung thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Them nguoi dung khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    } // Hiennt85 cập nhật email người dùng. Hiện tại email truyền vào đang tổn tại -->done
    @Test(priority = 49, description = "Kiem tra dang nhap vao hẹ thong voi nguoi dung vua duoc them thanh cong", retryAnalyzer = RetryAnalyzer.class) // Upcode_49: Kiểm tra đăng nhập vào hệ thống với người dùng được thêm mới thành công
    public void upCode_49(){
        ExtentTest test = extent.createTest("Kiem tra dang nhap vao hẹ thong voi nguoi dung vua duoc them thanh cong");
        test.assignCategory("Upcode 49");
        try {
            loginPage.login("upcodetest", "123456aA@");
            userPage.verifyNoti("Mã OTP đã được gửi tới số điện thoại của bạn!");
            test.log(Status.PASS, "Dang nhap vao hẹ thong voi nguoi dung vua duoc them thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Dang nhap vao hẹ thong voi nguoi dung vua duoc them khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 50, description = "Kiem tra Tim kiem nguoi dung thanh cong", retryAnalyzer = RetryAnalyzer.class) // Upcode_50: Kiểm tra tìm kiếm người dùng thành công
    public void upCode_50(){
        ExtentTest test = extent.createTest("Kiem tra Tim kiem nguoi dung thanh cong");
        test.assignCategory("Upcode 50");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToUser();
            userPage.searchTextBox("upcodetest");
            test.log(Status.PASS, "Tim kiem nguoi dung thanh cong thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Tim kiem nguoi dung khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 51, description = "Kiem tra chinh sua nguoi dung thanh cong", retryAnalyzer = RetryAnalyzer.class) // Upcode_51: Kiểm tra chỉnh sửa người dùng thành công
    public void upCode_51(){
        ExtentTest test = extent.createTest("Kiem tra chinh sua nguoi dung thanh cong");
        test.assignCategory("Upcode 51");
        try {
            loginPage.loginDefault();
            managePage.navigateToManage();
            managePage.navigateToUser();
            userPage.editUser("0274817462","upcodetest edit", "upcodetestedit@gmail.com", "0911192920");
            userPage.verifyNoti("Cập nhật thành công");
            test.log(Status.PASS, "Chinh sua nguoi dung thanh cong thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Chinh sua nguoi dung khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }// Hiennt85 chưa thực hiện lưu sau khi thay đổi thông tin user -->done
    //  Hiennt85 kiểm tra lại data truyền vào để eddit user -->done

//    //Quan ly phuong tien
    @Test(priority = 52, description = "Kiem tra them phuong tien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_52(){
        ExtentTest test = extent.createTest("Kiem tra them phuong tien thanh cong");
        test.assignCategory("Upcode 52");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToVehicle();
            vehiclePage.createVehicleNotTranmits();
            test.log(Status.PASS, "Them phuong tien thanh cong thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Them phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 53, description = "Kiem tra tim kiem phuong tien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_53(){
        ExtentTest test = extent.createTest("Kiem tra tim kiem phuong tien thanh cong");
        test.assignCategory("Upcode 53");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToVehicle();
            vehiclePage.searchByMultiConditions();
            test.log(Status.PASS, "Tim kiem phuong tien thanh cong thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Tim kiem phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 54, description = "Kiem tra sua phuong tien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_54(){
        ExtentTest test = extent.createTest("Kiem tra sua phuong tien thanh cong");
        test.assignCategory("Upcode 54");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToVehicle();
            vehiclePage.searchVehicle("12345.0");
            webUIHelpers.clickElement(VehiclePage.btnSua);
            webUIHelpers.sleep(5000);
            vehiclePage.editInfoVehicle("Xe autotest edit","","Rơ moóc (truyền dữ liệu lên CĐB)","89E17067",
                    "","","Hưng Yên","Toyota", "Ô tô", "test auto");
//        vehiclePage.editInfoDevice("Giám sát hành trình","20062024","","16/06/2024","17/06/2025","Đang hoạt động");
            webUIHelpers.sleep(5000);
            webUIHelpers.clickElement(VehiclePage.EditVehicleLocators.btnLuu);
            String alertActual = webUIHelpers.getTextInAlert(VehiclePage.alertPopup);
            String expectAlert = "Cập nhật thành công";
            Assert.assertEquals(alertActual,expectAlert);
            test.log(Status.PASS, "Sua phuong tien thanh cong thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Sua phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 55, description = "Kiem tra phan quyen giam sat phuong tien", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_55(){
        ExtentTest test = extent.createTest("Kiem tra phan quyen giam sat phuong tien");
        test.assignCategory("Upcode 55");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToVehicle();
            vehiclePage.searchVehicle("89E17067");
            vehiclePage.clickAssignBtn();
            vehiclePage.selectDDVehicleAssignedUserStatus("Người dùng chưa gán");
            vehiclePage.searchUserInPopupAssign("upcodetest");
            vehiclePage.assignUser();
            vehiclePage.closePopupAssign();
            managePage.navigateToUser();
            userPage.searchUser("upcodetest");
            userPage.clickBtnAssign();
            userPage.searchVehicleInPopupAssign("89E17067");
            userPage.verifySearchByVehicle("89E17067");
            test.log(Status.PASS, "Phan quyen giam sat phuong tien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Phan quyen giam sat phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
//    //Quan ly thiet bi
    @Test(priority = 56, description = "Kiem tra them thiet bi thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_56(){
        ExtentTest test = extent.createTest("Kiem tra them thiet bi thanh cong");
        test.assignCategory("Upcode 56");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToDevice();
            devicePage.createDeviceSuccessfully();
            test.log(Status.PASS, "Them thiet bi thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Them thiet bi khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 57, description = "Kiem tra tim kiem thiet bi thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_57(){
        ExtentTest test = extent.createTest("Kiem tra tim kiem thiet bi thanh cong");
        test.assignCategory("Upcode 57");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToDevice();
            devicePage.searchByTextsearch("0000011042024");
            test.log(Status.PASS, "Tim kiem thiet bi thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Tim kiem thiet bi khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 58, description = "Kiem tra sua thiet bi thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_58(){
        ExtentTest test = extent.createTest("Kiem tra sua thiet bi thanh cong");
        test.assignCategory("Upcode 58");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToDevice();
            devicePage.editDeviceNotConnected();
            test.log(Status.PASS, "Sua thiet bi thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Sua thiet bi khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 59, description = "Kiem tra dau noi thiet bi thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_59(){
        ExtentTest test = extent.createTest("Kiem tra dau noi thiet bi thanh cong");
        test.assignCategory("Upcode 59");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToVehicle();
            vehiclePage.search("", "", "", "", "Biển số xe", "89E17067");
            vehiclePage.clickEditBtn();
            vehiclePage.editInfoDevice("Cam Nghị Định 10", "0000016042024", "", "09/09/2024", "30/12/2024", "Đang hoạt động");
            webUIHelpers.sleep(3000);
            webUIHelpers.clickElement(VehiclePage.EditVehicleLocators.btnLuu);
            vehiclePage.verifyEditSuccessfull();
            test.log(Status.PASS, "Dau noi thiet bi thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Dau noi thiet bi khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 60, description = "Kiem tra huy dau noi thiet bi thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_60(){
        ExtentTest test = extent.createTest("Kiem tra huy dau noi thiet bi thanh cong");
        test.assignCategory("Upcode 60");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToVehicle();
            vehiclePage.search("", "", "", "", "Biển số xe", "89E17067");
            vehiclePage.clickEditBtn();
            vehiclePage.cancelConnect("");
            webUIHelpers.sleep(3000);
            webUIHelpers.clickElement(VehiclePage.EditVehicleLocators.btnLuu);
            vehiclePage.verifyEditSuccessfull();
            test.log(Status.PASS, "Huy dau noi thiet bi thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Huy dau noi thiet bi khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 61, description = "Kiem tra dieu chuyen thiet bi thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_61(){
        ExtentTest test = extent.createTest("Kiem tra dieu chuyen thiet bi thanh cong");
        test.assignCategory("Upcode 61");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToDevice();
            devicePage.search("0000016042024","","","");
            devicePage.clickBtnEdit();
            devicePage.editInfoDevice("0000016042024", "Công ty con của Hiền232", "Cam Nghị Định 10", "DashCam", "");
            webUIHelpers.clickElement(DevicePage.EditDeviceLocators.btnSave);
            devicePage.verifyEditSuccessfully();
            test.log(Status.PASS, "Dieu chuyen thiet bi thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Dieu chuyen thiet bi khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    //Quan ly danh sach lai xe
    @Test(priority = 62, description = "Kiem tra them lai xe thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_62(){
        ExtentTest test = extent.createTest("Kiem tra them lai xe thanh cong");
        test.assignCategory("Upcode 62");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToDriverList();
            driverListPage.createDriver("Lai xe autotest", "Công ty test 21/11/2024", "122344dvb", "0987561234", "111111112343411", "CMT8", "123432567222");
            driverListPage.verifyCreateSuccessfully();
            test.log(Status.PASS, "Them lai xe thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Them lai xe khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 63, description = "Kiem tra tim kiem lai xe thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_63(){
        ExtentTest test = extent.createTest("Kiem tra tim kiem lai xe thanh cong");
        test.assignCategory("Upcode 63");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToDriverList();
            driverListPage.search("Lai xe autotest", "");
            driverListPage.verifySearchByText("Lai xe autotest");
            test.log(Status.PASS, "Tim kiem lai xe thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Tim kiem lai xe khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 64, description = "Kiem tra sua lai xe thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_64(){
        ExtentTest test = extent.createTest("Kiem tra sua lai xe thanh cong");
        test.assignCategory("Upcode 64");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToDriverList();
            driverListPage.search("Lai xe autotest", "");
            driverListPage.editDriver("Lai xe autotest edit", "Công ty con của Hiền2", "27062024", "0927062024", "270620241518", "HHT", "270620242024");
            driverListPage.verifyEditSuccessfully();
            driverListPage.search("Lai xe autotest edit", "");
            driverListPage.verifySearchByText("Lai xe autotest edit");
            test.log(Status.PASS, "Sua lai xe thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Sua lai xe khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 65, description = "Kiem tra xoa lai xe thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_65(){
        ExtentTest test = extent.createTest("Kiem tra xoa lai xe thanh cong");
        test.assignCategory("Upcode 65");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToDriverList();
            driverListPage.search("Lai xe autotest edit", "");
            webUIHelpers.sleep(3000);
            driverListPage.deleteDriver();
            driverListPage.verifyDeleteUnusedDriver();
            test.log(Status.PASS, "Xoa lai xe thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Xoa lai xe khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Quan ly loai linh kien
    @Test(priority = 66, description = "Kiem tra them loai linh kien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_66(){
        ExtentTest test = extent.createTest("Kiem tra them loai linh kien thanh cong");
        test.assignCategory("Upcode 66");
        try {
            loginPage.loginDefault();
            componentPage.Navigation();
            componentPage.AddComponentSuccess();
            test.log(Status.PASS, "Them loai linh kien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Them loai linh kien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 67, description = "Kiem tra tim kiem loai linh kien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_67(){
        ExtentTest test = extent.createTest("Kiem tra tim kiem loai linh kien thanh cong");
        test.assignCategory("Upcode 67");
        try {
            loginPage.loginDefault();
            componentPage.Navigation();
            componentPage.SearchTextBox("Linh kien auto test");
            test.log(Status.PASS, "Tim kiem loai linh kien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Tim kiem loai linh kien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 68, description = "Kiem tra sua loai linh kien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_68(){
        ExtentTest test = extent.createTest("Kiem tra sua loai linh kien thanh cong");
        test.assignCategory("Upcode 68");
        try {
            loginPage.loginDefault();
            componentPage.Navigation();
            componentPage.EditComponentSuccess();
            test.log(Status.PASS, "Sua loai linh kien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Sua loai linh kien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 69, description = "Kiem tra xoa loai linh kien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_69(){
        ExtentTest test = extent.createTest("Kiem tra xoa loai linh kien thanh cong");
        test.assignCategory("Upcode 69");
        try {
            loginPage.loginDefault();
            componentPage.Navigation();
            componentPage.DeleteComponent("Linh kien auto test edit");
            test.log(Status.PASS, "Xoa loai linh kien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Xoa loai linh kien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    //Quan ly loai phuong tien
    @Test(priority = 70, description = "Kiem tra them loai phuong tien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_70(){
        ExtentTest test = extent.createTest("Kiem tra them loai phuong tien thanh cong");
        test.assignCategory("Upcode 70");
        try {
            loginPage.loginDefault();
            administrationPage.navigateToAdminPage();
            administrationPage.navigateToVehicle();
            typeOfVehiclePage.AddTypeOfVehicle("Loai phuong tien autotest", "09062024", "Ô tô");
            typeOfVehiclePage.VerifyNoti("Tạo thành công");
            test.log(Status.PASS, "Them loai phuong tien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Them loai phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 71, description = "Kiem tra tim kiem loai phuong tien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_71(){
        ExtentTest test = extent.createTest("Kiem tra tim kiem loai phuong tien thanh cong");
        test.assignCategory("Upcode 71");
        try {
            loginPage.loginDefault();
            administrationPage.navigateToAdminPage();
            administrationPage.navigateToVehicle();
            typeOfVehiclePage.SearchTextBox("Loai phuong tien autotest");
            test.log(Status.PASS, "Tim kiem loai phuong tien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Tim kiem loai phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 72, description = "Kiem tra sua loai phuong tien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_72(){
        ExtentTest test = extent.createTest("Kiem tra sua loai phuong tien thanh cong");
        test.assignCategory("Upcode 72");
        try {
            loginPage.loginDefault();
            administrationPage.navigateToAdminPage();
            administrationPage.navigateToVehicle();
            typeOfVehiclePage.SearchTextBox("Loai phuong tien autotest");
            typeOfVehiclePage.EditTypeOfVehicleSuccess("Loai phuong tien autotest","Loai phuong tien autotest edit","Ô tô");
            typeOfVehiclePage.VerifyNoti("Cập nhật thành công");
            test.log(Status.PASS, "Sua loai phuong tien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Sua loai phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 73, description = "Kiem tra xoa loai phuong tien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_73(){
        ExtentTest test = extent.createTest("Kiem tra xoa loai phuong tien thanh cong");
        test.assignCategory("Upcode 73");
        try {
            loginPage.loginDefault();
            administrationPage.navigateToAdminPage();
            administrationPage.navigateToVehicle();
            typeOfVehiclePage.DeleteTypeOfVehicle("Loai phuong tien autotest edit");
            test.log(Status.PASS, "Xoa loai phuong tien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Xoa loai phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Quan ly loai hinh van tai
    @Test(priority = 74, description = "Kiem tra them loai hinh van tai thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_74(){
        ExtentTest test = extent.createTest("Kiem tra them loai hinh van tai thanh cong");
        test.assignCategory("Upcode 74");
        try {
            loginPage.loginDefault();
            transportPage.Navigation();
            transportPage.AddTransportSuccess();
            test.log(Status.PASS, "Them loai hinh van tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Them loai hinh van tai khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 75, description = "Kiem tra tim kiem loai hinh van tai thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_75(){
        ExtentTest test = extent.createTest("Kiem tra tim kiem loai hinh van tai thanh cong");
        test.assignCategory("Upcode 75");
        try {
            loginPage.loginDefault();
            transportPage.Navigation();
            transportPage.SearchTextBox("Loai hinh van tai autotest");
            test.log(Status.PASS, "Tim kiem loai hinh van tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Tim kiem loai hinh van tai khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 76, description = "Kiem tra sua loai hinh van tai thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_76(){
        ExtentTest test = extent.createTest("Kiem tra sua loai hinh van tai thanh cong");
        test.assignCategory("Upcode 76");
        try {
            loginPage.loginDefault();
            transportPage.Navigation();
            transportPage.EditTransportSuccess("Loai hinh van tai autotest", "Loai hinh van tai autotest edit");
            test.log(Status.PASS, "Sua loai hinh van tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Sua loai hinh van tai khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    // hienntt85 xem lai ham DeleteTransport
    @Test(priority = 77, description = "Kiem tra xoa loai hinh van tai thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_77(){
        ExtentTest test = extent.createTest("Kiem tra xoa loai hinh van tai thanh cong");
        test.assignCategory("Upcode 77");
        try {
            loginPage.loginDefault();
            transportPage.Navigation();
            transportPage.DeleteTransport("Loai hinh van tai autotest edit");
            test.log(Status.PASS, "Xoa loai hinh van tai thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Xoa loai hinh van tai khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Quan ly loai thiet bi
    @Test(priority = 78, description = "Kiem tra them loai thiet bi thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_78(){
        ExtentTest test = extent.createTest("Kiem tra them loai thiet bi thanh cong");
        test.assignCategory("Upcode 78");
        try {
            loginPage.loginDefault();
            administrationPage.navigateToAdminPage();
            administrationPage.navigateToDevice();
            deviceTypePage.clickBtnCreate();
            deviceTypePage.fillInfoDeivceType("Loai thiet bi autoTest", "AT1", "Có độ mạnh sóng", "100", "100", "Giám sát hành trình", "description");
            deviceTypePage.clickBtnSave();
            deviceTypePage.verifyCreateSuccessfully();
            test.log(Status.PASS, "Them loai thiet bi thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Them loai thiet bi khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 79, description = "Kiem tra tim kiem loai thiet bi thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_79(){
        ExtentTest test = extent.createTest("Kiem tra tim kiem loai thiet bi thanh cong");
        test.assignCategory("Upcode 79");
        try {
            loginPage.loginDefault();
            administrationPage.navigateToAdminPage();
            administrationPage.navigateToDevice();
            deviceTypePage.search("Loai thiet bi autoTest");
            deviceTypePage.verifySearchByText("Loai thiet bi autoTest");
            test.log(Status.PASS, "Tim kiem loai thiet bi thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Tim kiem loai thiet bi khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 80, description = "Kiem tra sua loai thiet bi thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_80() throws Exception {
        ExtentTest test = extent.createTest("Kiem tra sua loai thiet bi thanh cong");
        test.assignCategory("Upcode 80");
        try {
            loginPage.loginDefault();
            administrationPage.navigateToAdminPage();
            administrationPage.navigateToDevice();
            deviceTypePage.search("Loai thiet bi autoTest");
            deviceTypePage.clickBtnEdit();
            deviceTypePage.editInfoDeviceType("Loai thiet bi autoTest edit", "ATE", "Có độ mạnh sóng", "120", "120", "Cam Nghị Định 10", "description edit");
            deviceTypePage.clickBtnSaveEdit();
            deviceTypePage.verifyEditSuccessfully();
            deviceTypePage.search("Loai thiet bi autoTest edit");
            deviceTypePage.deleteDeviceTemplate();
            test.log(Status.PASS, "Sua loai thiet bi thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Sua loai thiet bi khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    //Xoa nguoi dung, phuong tien, cong ty
    @Test(priority = 81, description = "Kiem tra xoa nguoi dung thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_81(){
        ExtentTest test = extent.createTest("Kiem tra xoa nguoi dung thanh cong");
        test.assignCategory("Upcode 81");
        try {
            loginPage.loginDefault();
            managePage.navigateToManage();
            managePage.navigateToUser();
            userPage.DeleteUserWithoutButton("0911192920");
            test.log(Status.PASS, "Xoa nguoi dung thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Xoa nguoi dung khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 82, description = "Kiem tra xoa phuong tien thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_82(){
        ExtentTest test = extent.createTest("Kiem tra xoa phuong tien thanh cong");
        test.assignCategory("Upcode 82");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToVehicle();
            vehiclePage.searchVehicle("89E17067");
            webUIHelpers.sleep(3000);
            vehiclePage.deleteVehicle("Xóa thành công");
            test.log(Status.PASS, "Xoa phuong tien thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Xoa phuong tien khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 83, description = "Kiem tra xoa thiet bi thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_83(){
        ExtentTest test = extent.createTest("Kiem tra xoa thiet bi thanh cong");
        test.assignCategory("Upcode 83");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToDevice();
            devicePage.deleteDeviceSuccessfully();
            test.log(Status.PASS, "Xoa thiet bi thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Xoa thiet bi khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 84, description = "Kiem tra xoa cong ty thanh cong", retryAnalyzer = RetryAnalyzer.class)
    public void upCode_84(){
        ExtentTest test = extent.createTest("Kiem tra xoa cong ty thanh cong");
        test.assignCategory("Upcode 84");
        try {
            loginPage.loginDefault();
            managePage.navigateToManagePage();
            managePage.navigateToCompany();
            companyPage.deleteCompanySuccessfully();
            test.log(Status.PASS, "Xoa cong ty thanh cong");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Xoa cong ty khong thanh cong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Loi he thong. Thong tin chi tiet: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }


    @AfterMethod
    public void close(){
        driver.quit();
    }
}
