package testcases.Administration;

import bases.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import pages.AdministrationPages.TypeOfVehiclePage;
import pages.History.RoutePage;
import pages.ManagementPages.UserPage;
import pages.ReportPages.AllReportPage;
import pages.AdministrationPage;

public class TypeOfVehicleTest {
    private WebDriver driver;
    private TypeOfVehiclePage typeOfVehiclePage;
    private LoginPage loginPage;
    private LogoutPage logoutPage;
    private ManagePage managePage;
    private AllReportPage allReportPage;
    private ReportPage reportPage;
    private RoutePage routePage;
    private MonitorPage monitorPage;
    private AdministrationPage administrationPage;
    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        typeOfVehiclePage = new TypeOfVehiclePage(driver);
        loginPage = new LoginPage(driver);
        logoutPage = new LogoutPage(driver);
        managePage = new ManagePage(driver);
        allReportPage = new AllReportPage(driver);
        reportPage = new ReportPage(driver);
        routePage = new RoutePage(driver);
        monitorPage = new MonitorPage(driver);
        administrationPage = new AdministrationPage(driver);
        administrationPage.navigateToAdminPage();
        administrationPage.navigateToVehicle();
    }
    //Tinh nang Tim kiem
    @Test //LPT_0
    public void SearchNoData(){
        typeOfVehiclePage.SearchTextBox("8975hh");
        typeOfVehiclePage.VerifyText("Tổng cộng: 0 Loại phương tiện", "Không có dữ liệu loại phương tiện");
    }
    @Test //LPT_1/2
    public void SearchTypeOfVehicle(){
        typeOfVehiclePage.SearchTextBox("456547647");
    }

    //Tinh nang Them
    @Test //LPT_7
    public void AddTypeOfVehicleSuccess(){
        typeOfVehiclePage.AddTypeOfVehicle("loại xe 123", "09062024", "Ô tô");
        typeOfVehiclePage.VerifyNoti("Tạo thành công");
    }
    @Test //LPT_4
    public void AddTypeOfVehicleMissCompulsory(){
        Select icon = new Select(driver.findElement(By.id("IconVehicleAdd")));
        typeOfVehiclePage.AddTypeOfVehicleMissCompulsory("", "1005", "");
    }
    @Test //LPT_5
    public void AddTypeOfVehicleDuplicateName(){
        typeOfVehiclePage.AddTypeOfVehicleDuplicateName("Hien", "3423", "Thuyền");
    }


    //Tinh nang Sua
//    @Test  //LPT_8
//    public void EditTypeOfVehicleMissCompulsory(){
//        typeOfVehiclePage.EditTypeOfVehicleMissCompulsory("124");
//        typeOfVehiclePage.VerifyText("Trường này là trường bắt buộc");
//    }
    @Test  //LPT_10
    public void EditTypeOfVehicleSuccess(){
        typeOfVehiclePage.EditTypeOfVehicleSuccess("123","124","Ô tô");
        typeOfVehiclePage.VerifyNoti("Cập nhật thành công");
    }
//    @Test  //LPT_9
//    public void EditTypeOfVehicleDuplicateName(){
//        typeOfVehiclePage.EditTypeOfVehicleSuccess("124","123","Ô tô");
//        typeOfVehiclePage.VerifyText("Tên loại phương tiện đã tồn tại. Vui lòng nhập lại");
//    }

    //Tinh nang Xoa
    @Test  //LPT_11/12
    public void DeleteTypeOfVehicle(){
        typeOfVehiclePage.DeleteTypeOfVehicle("123");
    }



}
