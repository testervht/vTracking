package testcases.Report;

import bases.DriverFactory;
import bases.ExcelHelpers;
import bases.WebUIHelpers;
import pages.ReportPages.TemperaturePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TemperatureTest {
    private WebDriver driver;
    private ExcelHelpers excelHelpers;
    private WebUIHelpers uiHelpers;
    private TemperaturePage temperaturePagePage;
    private By buttonTaoBaoCao = By.id("Nhietdo_createReportBtn");
    private By textKetQua = By.id("Nhietdo_search-result");
    private By notiPopup = By.cssSelector("span[data-notify='message']");
    private By buttonXuatExcel = By.id("Fuel_Excel");
    @BeforeMethod
    public void setUpBrowser() throws Exception {
//        driver = new ChromeDriver();
        driver = DriverFactory.getDriver("chrome");
        uiHelpers = new WebUIHelpers(driver);
        temperaturePagePage = new TemperaturePage(driver);
        this.excelHelpers = new ExcelHelpers();
        System.out.println("Bao cao lich su nhiet do browser:" + driver);
    }
    @Test
    //BC_61
    public void NotSelectLicensePlate() {
        temperaturePagePage.NotSelectLicensePlater(buttonTaoBaoCao);
    }
    @Test
    //BC_62
    public void NoData() throws Exception {
        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        temperaturePagePage.enterPlate(this.excelHelpers.getCellData("PlateNo",3));
        temperaturePagePage.EnterCalendar("15", "Th6", "2024", "16", "Th6", "2024");
        uiHelpers.clickElement(buttonTaoBaoCao);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(temperaturePagePage.VerifyNoti(textKetQua, "KẾT QUẢ TÌM KIẾM: 0") && temperaturePagePage.VerifyNoti(notiPopup, "Không có báo cáo nào trong khoảng thời gian được chọn")){
            System.out.println("Không có báo cáo trong thời gian được chọn");
        }else{
            System.out.println("Test case fail");
        }
    }
    @Test
    //BC_63
    public void CreateReportSuccess() throws Exception {
        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        temperaturePagePage.enterPlate(this.excelHelpers.getCellData("PlateNo",3));
        temperaturePagePage.EnterCalendar("15", "Th6", "2024", "16", "Th6", "2024");
        uiHelpers.clickElement(buttonTaoBaoCao);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(!temperaturePagePage.VerifyNoti(textKetQua, "KẾT QUẢ TÌM KIẾM: 0")){
            System.out.println("Xuat du lieu bao cao thanh cong");
        }else{
            System.out.println("Test case fail");
        }
    }
//    @Test
//    public void EnterPlate() throws Exception {
//        this.excelHelpers.setExcelFile("src/test/java/hienntt/datas/PlateNo.xlsx", "Sheet1");
//        baoCaoLichSuNhietDoPagePage.enterPlate(this.excelHelpers.getCellData("PlateNo",3));
//    }
    @Test
    //BC_64
    public void ExportExcelNoDate() throws Exception {
        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        temperaturePagePage.enterPlate(this.excelHelpers.getCellData("PlateNo",3));
        temperaturePagePage.EnterCalendar("15", "Th6", "2024", "16", "Th6", "2024");
        uiHelpers.clickElement(buttonTaoBaoCao);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(temperaturePagePage.isButtonClickable(buttonXuatExcel)){
            uiHelpers.clickElement(buttonXuatExcel);
            if(temperaturePagePage.VerifyNoti(notiPopup, "Không có báo cáo nào trong khoảng thời gian được chọn")){
                System.out.println("Không có báo cáo trong thời gian được chọn nên không xuất được excel");
            }else{
                System.out.println("Test case fail");
            }
        }else {
            System.out.println("Test case fail");
        }

    }
    @Test
    public void ExportExcel() throws Exception {
        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        temperaturePagePage.enterPlate(this.excelHelpers.getCellData("PlateNo",3));
        temperaturePagePage.EnterCalendar("15", "Th6", "2024", "16", "Th6", "2024");
        uiHelpers.clickElement(buttonTaoBaoCao);
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
        temperaturePagePage.VerifyNoti(notiPopup, "Không có dữ liệu báo cáo");
    }
}
