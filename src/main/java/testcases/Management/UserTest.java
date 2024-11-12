package testcases.Management;

import bases.DriverFactory;
import bases.WebUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import pages.History.RoutePage;
import pages.ManagementPages.DriverListPage;
import pages.ManagementPages.RolePage;
import pages.ManagementPages.UserPage;
import pages.ReportPages.AllReportPage;

public class UserTest {
    private WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private UserPage userPage;
    private LoginPage loginPage;
    private LogoutPage logoutPage;
    private ManagePage managePage;
    private AllReportPage allReportPage;
    private ReportPage reportPage;
    private RoutePage routePage;
    private MonitorPage monitorPage;

    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        userPage = new UserPage(driver);
        webUIHelpers = new WebUIHelpers(driver);
        loginPage = new LoginPage(driver);
        loginPage.loginDefault();
        logoutPage = new LogoutPage(driver);
        managePage = new ManagePage(driver);
        allReportPage = new AllReportPage(driver);
        reportPage = new ReportPage(driver);
        routePage = new RoutePage(driver);
        monitorPage = new MonitorPage(driver);
        managePage.navigateToManagePage();
        managePage.navigateToUser();
    }

    //Tinh nang Tim kiem
    @Test //QLND_0
    public void searchNoValue(){
        userPage.searchUser("45454g$%#$%");
        userPage.verifyText("Tổng cộng: 0 người dùng", "Không có dữ liệu người dùng");
    }
    @Test //QLND_1/2
    public void searchTextBox(){
        userPage.searchTextBox("ThuHien");
    }
    @Test //QLND_3
    public void searchOnlyCompany(){
        userPage.searchOnlyCompany(By.xpath("//a[@id='jstreeUserTab_ffe633ce-520e-4c99-a44a-3f8ec6cacee9_anchor']"));
    }
    @Test //QLND_4
    public void SearchOnlyRole(){
        userPage.searchOnlyRole("Default User");
    }

    @Test //QLND_5
    public void searchMultiConditions(){
        userPage.searchMultiConditions(By.xpath("//a[@id='jstreeUserTab_ffe633ce-520e-4c99-a44a-3f8ec6cacee9_anchor']"), "Default User", "thuhien");
    }

    //Tinh nang Them

    @Test //QLND_8
    public void addUserMissCompulsory(){
        userPage.addUserMissCompulsory("hien2507", "123456aA@", "123456aA@", "Default User", By.xpath("//*[@id=\"jstreeUserAdd_ffe633ce-520e-4c99-a44a-3f8ec6cacee9\"]"), "Nguyen Hien", "", "");
    }

    @Test //QLND_9
    public void addUserDuplicateEmail(){
        userPage.addUser("hien2507", "123456aA@", "123456aA@", "Default User", By.xpath("//*[@id=\"jstreeUserAdd_ffe633ce-520e-4c99-a44a-3f8ec6cacee9\"]"), "Nguyen Hien", "adsf@dsa.bg", "04968274819");
        userPage.verifyNoti("Email đã tồn tại. Vui lòng nhập lại");
    }

    @Test // QLND_10
    public void addUserDuplicateTenTaiKhoan(){
        userPage.addUser("giangptt", "123456aA@", "123456aA@", "Default User", By.xpath("//*[@id=\"jstreeUserAdd_ffe633ce-520e-4c99-a44a-3f8ec6cacee9\"]"), "Nguyen Hien", "adsf@dsda.bg", "04968274819");
        userPage.verifyNoti("Tài khoản đã tồn tại. Vui lòng nhập lại");
    }

    @Test // QLND_11
    public void addUserDuplicatePhone(){
        userPage.addUser("giangfptt", "123456aA@", "123456aA@", "Default User", By.xpath("//*[@id=\"jstreeUserAdd_ffe633ce-520e-4c99-a44a-3f8ec6cacee9\"]"), "Nguyen Hien", "adsf@dsda.bg", "0945784646");
        userPage.verifyNoti("Số điện thoại đã tồn tại. Vui lòng nhập lại");
    }

    @Test //QLND_12
    public void addUsersSuccess(){
        userPage.addUser("giangfptt2", "123456aA@", "123456aA@", "Default User", By.xpath("//*[@id=\"jstreeUserAdd_ffe633ce-520e-4c99-a44a-3f8ec6cacee9\"]"), "Nguyen Hien", "adsf2@dsda.bg", "0975784642");
        userPage.verifyNoti("Tạo thành công");
    }
    @Test //QLND_13
    public void loginWithAccountCreatedSuccess(){
        addUsersSuccess();
        logoutPage.logoutSuccessfully();
        loginPage.login("giangfptt", "123456aA@");
        userPage.verifyNoti("Mã OTP đã được gửi tới số điện thoại của bạn!");
    }

    //Tinh nang Sua
    @Test
    public void EditUserMissHoVaTen(){
        userPage.EditUserMissHoVaTen();
        userPage.verifyAlert("Trường này là trường bắt buộc", userPage.alertBoxHoVaTen_edit);
    }
    @Test
    public void EditUserMissSoDienThoai(){
        userPage.EditUserMissSoDienThoai();
    }
    @Test
    public void EditUserSuccess(){
        //userPage.EditUserSuccess("0637467843","Sua ten", "testauto1@gmail.com", "0312345212");
        userPage.editUser("0274817462","upcodetest edit", "upcodetestedit@gmail.com", "0911192920");
        userPage.verifyNoti("Cập nhật thành công");
    }
    @Test
    public void EditUserDuplicateEmail(){
        userPage.EditUserDuplicateEmail();
    }
    @Test
    public void EditUserDuplicateSDT(){
        userPage.EditUserDuplicateSDT();
    }

    //Tinh nang Xoa
    @Test
    public void DeleteUserWithoutButton(){
        userPage.DeleteUserWithoutButton("testxoa");
    }








}
