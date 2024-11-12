package testcases;

import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AdministrationPages.TransportPage;
import pages.DashBoardPage;
import pages.LoginPage;

import java.io.IOException;

public class DashBoardTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private DashBoardPage dashBoardPage;
    @BeforeMethod
    public void setUpBrowser() throws Exception {
        driver = DriverFactory.getDriver("chrome");
        dashBoardPage = new DashBoardPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.loginDefault();
        dashBoardPage.navigateToDashBoardPage();
        System.out.println("Dashboard browser:" + driver);
    }
    @Test
    public void ThongTinTongQuan() throws Exception {
        dashBoardPage.ThongTinTongQuan();
    }
    @Test
    public void BieuDoTongSoKM() throws Exception {
        dashBoardPage.BieuDoTongSoKM();
    }
    @Test
    public void BieuDoThoiGianLaiXe() throws Exception {
        dashBoardPage.BieuDoThoiGianLaiXe();
    }
    @Test void ThongTinHoatDong() throws Exception {
        dashBoardPage.BieuDoTongSoKM();
        dashBoardPage.BieuDoThoiGianLaiXe();

    }

    @Test
    public void ThongTinCanhBao() throws Exception {
        dashBoardPage.PhuongTienSapHetHanSung();
        dashBoardPage.PhuongTienDaHetHanSuDung();
    }

    @Test
    public void PhuongTienDaHetHanSD() throws Exception {
        dashBoardPage.PhuongTienDaHetHanSuDung();
    }

}
