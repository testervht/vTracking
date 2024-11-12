package testcases.Report;

import bases.DriverFactory;
import bases.WebUIHelpers;
import pages.ReportPages.ServiceUsageDetailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ServiceUsageDetailTest {
    private WebDriver driver;
    private WebUIHelpers uiHelpers;
    private ServiceUsageDetailPage serviceUsageDetailPage;
    By notiPopup = By.cssSelector("span[data-notify='message']");
    private By textKetQua = By.id("sud_search-result");
    private By buttonXuatExcel = By.xpath("//button[@class='btn btn-export']");

    @BeforeMethod
    public void setUpBrowser() throws Exception {
//        driver = new ChromeDriver();
        driver = DriverFactory.getDriver("chrome");
        uiHelpers = new WebUIHelpers(driver);
        serviceUsageDetailPage = new ServiceUsageDetailPage(driver);
        System.out.println("Bao cao chi tiet su dung dich vu browser:" + driver);
    }
    @Test
    //BC_46
    public void NotSelectCompany(){
        serviceUsageDetailPage.NotSelectCompany();
    }
    @Test
    //BC_47
    public void NoData(){
        serviceUsageDetailPage.MultiFilters("2", "Hết hạn");
        serviceUsageDetailPage.VerifyNoti(notiPopup, "Không có dữ liệu báo cáo");
    }
    @Test
    //BC_48
    public void CreateReportSuccess(){
        serviceUsageDetailPage.MultiFilters("1","Hết hạn");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String actualTextKetqua = uiHelpers.getText(textKetQua);
        System.out.println("actualKetqua:" + actualTextKetqua);
        if(!actualTextKetqua.equals("KẾT QUẢ TÌM KIẾM: 0")){
            System.out.println("Tạo báo cáo thành công!");
        }else{
            System.out.println("Test case Fail!");
        }
    }
    @Test
    //BC_49
    public void ExportExcelNoData(){
        serviceUsageDetailPage.MultiFilters("2", "Hết hạn");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        uiHelpers.clickElement(buttonXuatExcel);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        By notiPopupAfterExport = By.xpath("/html/body/div[7]");
        serviceUsageDetailPage.VerifyNoti(notiPopup, "Không có dữ liệu báo cáo");
    }
    @Test
    //BC_50
    public void ExportExcelSuccess() throws Exception {
        serviceUsageDetailPage.ExportExcel("1","Hết hạn",buttonXuatExcel,textKetQua);
    }
}
