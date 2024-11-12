package testcases.Management;

import bases.BaseSetup;
import bases.DriverFactory;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.History.RoutePage;
import pages.*;
import pages.ManagementPages.DriverListPage;
import pages.ManagementPages.RolePage;
import pages.ManagementPages.UserPage;
import pages.ReportPages.AllReportPage;

import java.util.Arrays;
import java.util.List;

public class RoleTest extends BaseSetup {
    private WebDriver driver;
    private RolePage rolePage;
    private ManagePage managePage;
    private UserPage userPage;
    private DriverListPage driverListPage;
    private AllReportPage allReportPage;
    private ReportPage reportPage;
    private RoutePage routePage;
    private LogoutPage logoutPage;
    private LoginPage loginPage;
    private MonitorPage monitorPage;
    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        rolePage = new RolePage(driver);
        userPage = new UserPage(driver);
        driverListPage = new DriverListPage(driver);
        loginPage = new LoginPage(driver);
        logoutPage = new LogoutPage(driver);
        managePage = new ManagePage(driver);
        allReportPage = new AllReportPage(driver);
        reportPage = new ReportPage(driver);
        routePage = new RoutePage(driver);
        monitorPage = new MonitorPage(driver);
        managePage.navigateToManagePage();
        managePage.navigateToRole();
    }
    @Test(priority = 1)
    public void searchWithoutValue() {
        ExtentTest test = extent.createTest("Search Without Value");
        test.assignCategory("RoleManage");
        try {
            rolePage.search("test1246234test", "");
            rolePage.verifySearchWithoutValue();
            test.log(Status.PASS, "Verified search without value successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Search without value encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }

    @Test(priority = 2)
    public void searchByTextsearch(){
        ExtentTest test = extent.createTest("Search by text search");
        test.assignCategory("RoleManage");
        try {
            rolePage.search("teSt", "");
            rolePage.verifySearchByText("TEST");
            test.log(Status.PASS, "Verified search by text search successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Search by text search encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 3)
    public void searchByCompany(){
        ExtentTest test = extent.createTest("Search by company");
        test.assignCategory("RoleManage");
        try {
            rolePage.search("", "Công ty test 21/11/2024 new");
            rolePage.verifySearchByCompany("Công ty test 21/11/2024 new");
            test.log(Status.PASS, "Verified by company successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Search by company encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 4)
    public void searchByMultiConditions(){
        ExtentTest test = extent.createTest("Search by multiple conditions");
        test.assignCategory("RoleManage");
        try {
            rolePage.search("full", "Công ty test 21/11/2024 new");
            rolePage.verifySearchByText("FULL");
            rolePage.verifySearchByCompany("Công ty test 21/11/2024 new");
            test.log(Status.PASS, "Verified search by multiple conditions successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Search by multiple conditions encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 5)
    public void createWithoutName(){
        ExtentTest test = extent.createTest("Create without name");
        test.assignCategory("RoleManage");
        try {
            List<String> roles = Arrays.asList("Cấu hình đơn lẻ", "Quản lý công ty");
            rolePage.createRole("", "Công ty test 21/11/2024 new", roles);
            rolePage.verifyCreateWithoutName();
            test.log(Status.PASS, "Verified create without name successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create without name encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 6)
    public void createWithoutRequiredFields(){
        ExtentTest test = extent.createTest("Create without required fields");
        test.assignCategory("RoleManage");
        try {
            List<String> roles = Arrays.asList("Cấu hình đơn lẻ", "Quản lý công ty");
            rolePage.createRole("test 123", "", roles);
            rolePage.verifyCreateWithoutCompany();
            test.log(Status.PASS, "Verified create without required fields successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create without required fields encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 7)
    public void createWithDuplicateName(){
        ExtentTest test = extent.createTest("Create with duplicate name");
        test.assignCategory("RoleManage");
        try {
            List<String> roles = Arrays.asList("Cấu hình đơn lẻ", "Quản lý công ty");
            rolePage.createRole("Full Quy Luật Hàng Rào", "Công ty test 21/11/2024 new", roles);
            rolePage.verifyCreateWithDuplicateName();
            test.log(Status.PASS, "Verified create with duplicate name successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create with duplicate name encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 8)
    public void createSuccessfully(){
        ExtentTest test = extent.createTest("Create successfully");
        test.assignCategory("RoleManage");
        try {
            List<String> roles = Arrays.asList("Cấu hình đơn lẻ", "Quản lý công ty");
            rolePage.createRole("Quy luat auto", "Công ty test 21/11/2024 new", roles);
            rolePage.verifyCreateSuccessfully();
            rolePage.search("Quy luat auto test", "");
            rolePage.verifySearchByText("Quy luat auto test");
            test.log(Status.PASS, "Verified create successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Create successfully encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 9)
    public void editWithoutName(){
        ExtentTest test = extent.createTest("Edit without name");
        test.assignCategory("RoleManage");
        try {
            List<String> untickRoles = Arrays.asList("Cấu hình đơn lẻ", "Quản lý công ty");
            List<String> roles = Arrays.asList("Tạo tài khoản người dùng");
            rolePage.search("Quy luat auto", "");
            rolePage.editRole("", untickRoles, roles);
            rolePage.verifyEditWithoutName();
            test.log(Status.PASS, "Verified edit without name successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Edit without name an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 10)
    public void editWithDuplicateName(){
        ExtentTest test = extent.createTest("Edit with duplicate name");
        test.assignCategory("RoleManage");
        try {
            List<String> untickRoles = Arrays.asList("Cấu hình đơn lẻ", "Quản lý công ty");
            List<String> roles = Arrays.asList("Tạo tài khoản người dùng");
            rolePage.search("test", "Công ty test 21/11/2024 new");
            rolePage.editRole("Full Quy Luật Hàng Rào", untickRoles, roles);
            rolePage.verifyCreateWithDuplicateName();
            test.log(Status.PASS, "Verified edit with duplicate name successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Edit with duplicate name encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 11)
    public void editSuccessfully(){
        ExtentTest test = extent.createTest("Edit successfully");
        test.assignCategory("RoleManage");
        try {
            List<String> untickRoles = Arrays.asList("Cấu hình đơn lẻ", "Quản lý công ty");
            List<String> roles = Arrays.asList("Tạo tài khoản người dùng");
            rolePage.search("Quy luat auto", "");
            rolePage.editRole("Quy luat test 123 auto", untickRoles, roles);
            rolePage.verifyEditSuccessfully();
            test.log(Status.PASS, "Verified edit successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Edit successfully encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 12)
    public void deleteUnusedRole(){
        ExtentTest test = extent.createTest("Delete unused role");
        test.assignCategory("RoleManage");
        try {
            rolePage.deleteRole("Quy luat test 123 auto");
            rolePage.verifyDeleteUnusedRole();
            test.log(Status.PASS, "Verified delete unused role successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Delete unused role encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 13)
    public void deleteUsedRole(){
        ExtentTest test = extent.createTest("Delete used role");
        test.assignCategory("RoleManage");
        try {
            rolePage.deleteRole("Qlhđ Mix");
            rolePage.verifyDeleteUsedRole();
            test.log(Status.PASS, "Verified delete used role successfully.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Verification failed: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        } catch (Exception e) {
            test.log(Status.FAIL, "Delete used role encountered an error: " + e.getMessage());
            throw e; // Rethrow the exception after logging it
        }
    }
    @Test(priority = 14)
    public void verifyUserWithMngDriverRole(){
        List<String> expectedMenuItems = Arrays.asList(
                "Giám sát trực tuyến",
                "Quản lý",
                "Trợ giúp"
        );
        //gan tk voi role full quyen ql danh sach lai xe
        managePage.navigateToUser();
        userPage.editRoleUser("duclong", "Chỉ Quản lý danh sách lái xe");

        //login tk vua duoc gan
        logoutPage.logout();
        loginPage.login("duclong", "123456aA@");
        managePage.navigateToManage();
        managePage.navigateToDriverList();

        //kt tinh nang them
        driverListPage.createDriver("Lan Anh auto", "Công ty test 21/11/2024 new", "122344dvb", "0987561234", "111111112343411", "CMT8", "123432567222");
        driverListPage.verifyCreateSuccessfully();

        //kt tinh nang sua
        driverListPage.search("Lan Anh auto", "");
        driverListPage.editDriver("Lan Anh auto edit", "", "27062024", "0927062024", "270620241518", "HHT", "270620242020");
        driverListPage.verifyEditSuccessfully();

        //kt tinh nang xoa
        driverListPage.search("Lan Anh auto edit", "");
        driverListPage.deleteDriver();
        driverListPage.verifyDeleteUnusedDriver();

        //verify menu header
        monitorPage.verifyHeaderMenu(expectedMenuItems);
    }
    @Test(priority = 14)
    public void verifyUserWithAddDriverRole(){
        List<String> expectedMenuItems = Arrays.asList(
                "Giám sát trực tuyến",
                "Quản lý",
                "Trợ giúp"
        );
        //gan tk voi role full quyen ql danh sach lai xe
        managePage.navigateToUser();
        userPage.editRoleUser("duclong", "Chỉ Thêm Xóa Lái Xe");

        //login tk vua duoc gan
        logoutPage.logout();
        loginPage.login("duclong", "123456aA@");
        managePage.navigateToManage();
        managePage.navigateToDriverList();

        //kt tinh nang them
        driverListPage.createDriver("Lan Anh auto", "Công ty test 21/11/2024 new", "122344dvb", "0987561234", "111111112343411", "CMT8", "123432567222");
        driverListPage.verifyCreateSuccessfully();

        //kt tinh nang xoa
        driverListPage.search("Lan Anh auto", "");
        driverListPage.deleteDriver();
        driverListPage.verifyDeleteUnusedDriver();

        //kt hien thi tinh nang sua, xoa
        driverListPage.verifyBtnEditInUserAcc();

        //verify menu header
        monitorPage.verifyHeaderMenu(expectedMenuItems);
    }
    @Test(priority = 14)
    public void verifyUserWithReportRole() {
        List<String> expectedMenuItems = Arrays.asList(
                "Tổng quan",
                "Giám sát trực tuyến",
                "Lịch sử",
                "Quản lý",
                "Báo cáo",
                "Quản trị",
                "Thống kê",
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

        //kt tinh nang tao bao cao toc do
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

        //verify menu header
        monitorPage.verifyHeaderMenu(expectedMenuItems);

        //verify so bao cao hien thi
        reportPage.verifyReportTypes(expectedReports);
    }
    @Test(priority = 14)
    public void verifyUserWithSpeedReportRole() {
        List<String> expectedMenuItems = Arrays.asList(
                "Giám sát trực tuyến",
                "Báo cáo",
                "Trợ giúp"
        );
        List<String> expectedReports = Arrays.asList(
                "Tốc độ"
        );
        //gan tk voi role full quyen ql danh sach lai xe
        managePage.navigateToUser();
        userPage.editRoleUser("duclong", "Chỉ Báo Cáo Tốc Độ");

        //login tk vua duoc gan
        logoutPage.logout();
        loginPage.login("duclong", "123456aA@");
        reportPage.navigateToSpeedReports();

        //kt tinh nang tao bao cao toc do
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

        //verify menu header
        monitorPage.verifyHeaderMenu(expectedMenuItems);

        //verify so bao cao hien thi
        reportPage.verifyReportTypes(expectedReports);
    }
//    @AfterMethod
//    public void close(){
//        quitDriver();
//    }
}