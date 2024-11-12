package testcases.Report;

import bases.DriverFactory;
import bases.ExcelHelpers;
import bases.WebUIHelpers;
import pages.History.RoutePage;
import pages.ReportPages.AllReportPage;
import pages.ReportPages.FuelDistancePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FuelDistanceTest {
    private WebDriver driver;
    private ExcelHelpers excelHelpers;
    private WebUIHelpers uiHelpers;
    private FuelDistancePage fuelDistancePage;
    private AllReportPage allReportPage;
    private RoutePage routePage;
    private By buttonTaoBaoCao = By.id("FuelByDistance_createReportBtn");
    private By textKetQua = By.id("FueDistance_search-result");
    private By notiPopup = By.cssSelector("span[data-notify='message']");
    private By buttonXuatExcel = By.id("FueDistance_Excel");

    @BeforeMethod
    public void setUpBrowser() throws Exception {
//        driver = new ChromeDriver();
        driver = DriverFactory.getDriver("chrome");
        uiHelpers = new WebUIHelpers(driver);
        fuelDistancePage = new FuelDistancePage(driver);
        allReportPage = new AllReportPage(driver);
        this.excelHelpers = new ExcelHelpers();
        System.out.println("Bao cao tieu hao nhien lieu browser:" + driver);
    }
    @Test
    //BC_56
    public void NotSelectLicensePlate() {
        fuelDistancePage.NotSelectLicensePlater(buttonTaoBaoCao);
    }
    @Test
    //BC_57
    public void NoData() throws Exception {

        fuelDistancePage.EnterCalendar("12", "Th8", "2024", "12", "Th8", "2024");
        String plate = routePage.getPlate(10);
        allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_FuelHistory, routePage.getId(10), allReportPage.btnSave_FuelHistory);
        allReportPage.createReport(allReportPage.btnCreate_FuelHistory);
        allReportPage.verifyNoti();


        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        fuelDistancePage.enterPlate(this.excelHelpers.getCellData("PlateNo",3));
        fuelDistancePage.EnterCalendar("15", "Th6", "2024", "16", "Th6", "2024");
        uiHelpers.clickElement(buttonTaoBaoCao);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(fuelDistancePage.VerifyNoti(textKetQua, "KẾT QUẢ TÌM KIẾM: 0") && fuelDistancePage.VerifyNoti(notiPopup, "Không có báo cáo nào trong khoảng thời gian được chọn")){
            System.out.println("Không có báo cáo trong thời gian được chọn");
        }else{
            System.out.println("Test case fail");
        }
    }
    @Test
    //BC_58
    public void CreateReportSuccess() throws Exception {
        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        fuelDistancePage.enterPlate(this.excelHelpers.getCellData("PlateNo",3));
        fuelDistancePage.EnterCalendar("15", "Th6", "2024", "16", "Th6", "2024");
        uiHelpers.clickElement(buttonTaoBaoCao);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(!fuelDistancePage.VerifyNoti(textKetQua, "KẾT QUẢ TÌM KIẾM: 0")){
            System.out.println("Xuat du lieu bao cao thanh cong");
        }else{
            System.out.println("Test case fail");
        }
    }
    @Test
    //BC_59
    public void ExportExcelNoDate() throws Exception {
        this.excelHelpers.setExcelFile("PlateNo.xlsx", "Sheet1");
        fuelDistancePage.enterPlate(this.excelHelpers.getCellData("PlateNo",3));
        fuelDistancePage.EnterCalendar("15", "Th6", "2024", "16", "Th6", "2024");
        uiHelpers.clickElement(buttonTaoBaoCao);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(fuelDistancePage.isButtonClickable(buttonXuatExcel)){
            uiHelpers.clickElement(buttonXuatExcel);
            if(fuelDistancePage.VerifyNoti(notiPopup, "Không có báo cáo nào trong khoảng thời gian được chọn")){
                System.out.println("Không có báo cáo trong thời gian được chọn nên không xuất được excel");
            }else{
                System.out.println("Test case fail");
            }
        }else {
            System.out.println("Test case fail");
        }

    }


//    @Test
//    public void EnterPlate() throws Exception {
//        this.excelHelpers.setExcelFile("src/test/java/hienntt/datas/PlateNo.xlsx", "Sheet1");
//        baoCaoTieuHaoNhienLieuPage.enterPlate(this.excelHelpers.getCellData("PlateNo",1));
//        baoCaoTieuHaoNhienLieuPage.EnterCalendar("15", "Th6", "2024", "16", "Th6", "2024");
//    }
//    @Test
//    public void EnterCalendar(){
//    }


}
