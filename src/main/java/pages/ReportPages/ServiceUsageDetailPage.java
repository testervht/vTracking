package pages.ReportPages;

import bases.ExcelHelpers;
import bases.WebUIHelpers;
import org.testng.Assert;
import pages.LoginPage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ServiceUsageDetailPage {
    WebDriver driver;
    private LoginPage loginPage;
    private WebUIHelpers uiHelpers;
    private ExcelHelpers excelHelpers;
    private By tabBaocao = By.xpath("/html/body/div[1]/div[2]/div[10]/a/span");
    private By baoCaoChiTietSuDungDichVu = By.xpath("//span[contains(text(),'Chi tiết sử dụng dịch vụ')]");
    private By notiPopup = By.cssSelector("span[data-notify='message']");
    // Cac droplist

    private By buttonTaoBaoCao = By.id("sud_createReportBtn");
    public ServiceUsageDetailPage(WebDriver driver) throws Exception {
        this.driver = driver;
        uiHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
        this.excelHelpers = new ExcelHelpers();
        System.out.println("Bao Cao Chi Tiet Su Dung Dich Vu Page browser:" + driver);
        loginPage.loginDefault();
        navigation();
    }
    public void navigation(){
        uiHelpers.clickElement(tabBaocao);
        uiHelpers.clickElement(baoCaoChiTietSuDungDichVu);
    }
    public boolean isButtonClickable(By locator) {
        WebElement button = driver.findElement(locator);
        String pointerEvents = button.getCssValue("pointer-events");

        // Kiểm tra giá trị của thuộc tính "pointer-events"
        if (pointerEvents.equals("none")) {
            System.out.println("Button Tao bao cao is not clickable.");
            return false;
        } else {
            System.out.println("Button Tao bao cao is clickable.");
            return true;
        }
    }
    public void NotSelectCompany(){
        boolean checkButtonClickable = isButtonClickable(buttonTaoBaoCao);
        if (checkButtonClickable) {
            driver.findElement(buttonTaoBaoCao).click();
            System.out.println("Button Tao bao cao clicked.");
        } else {
            System.out.println("Button Tao bao cao is disable.");
        }
    }
    public void MultiFilters(String filterCompany, String filterStatus){
        By dropListCompany = By.id("UsageDetail_company");
        uiHelpers.clickElement(dropListCompany);
        By company = By.xpath("//*[@id=\"org-root\"]/div["+filterCompany+"]/div[1]/label");
        //uiHelpers.clickElementAndSelect(dropListCompany,company);
        uiHelpers.clickElement(company);
        By buttonXacNhan = By.id("btn_service");
        uiHelpers.clickElement(buttonXacNhan);

        By dropListStatus = By.id("sud_filterStatusTitle");
        By status = By.xpath("//div[@id='sud_filterStatusModal']/ul[@class='dropdown-menu']//a[.='"+filterStatus+"']");
        uiHelpers.clickElementAndSelect(dropListStatus,status);
        uiHelpers.clickElement(buttonTaoBaoCao);
    }
    public void VerifyNoti(By element, String expectText){
        String actualAlert = uiHelpers.getText(element);
        System.out.println("Actual Alert:" + actualAlert);
        if(actualAlert.equals(expectText)){
            System.out.println(expectText);
        }else{
            System.out.println("Test case failed");
        }
    }
    public void ExportExcel(String filterCompany, String filterStatus, By buttonXuatExcel, By textKetQua) throws Exception {
        MultiFilters(filterCompany, filterStatus);
        uiHelpers.clickElement(buttonXuatExcel);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertEquals(uiHelpers.getText(notiPopup), "Xuất báo cáo thành công", "Xuất báo cáo thành công");

        //cmt tam doan code check du lieu tai ve
//        String fileDownloadPath = excelHelpers.getLatestFilePathFromDownloads();
//
//        int rowDownload = GetNumberOfRows(fileDownloadPath, "Sheet 1");
//        int actualRow = rowDownload - 1;
//
//        String actualTextKetqua = uiHelpers.getText(textKetQua);
//        System.out.println("Text ket qua:" + actualTextKetqua);
//
//        String ketQuaTaiVe = "KẾT QUẢ TÌM KIẾM: " + actualRow;
//        System.out.println("kET QUA TAI VE:" + ketQuaTaiVe);
//
//        if (GetDataExcelFile(fileDownloadPath) && (ketQuaTaiVe.equals(actualTextKetqua))) {
//            System.out.println("File tai ve da dung noi dung");
//        }else {
//            System.out.println("File tai ve khong hop le");
//        }

    }
    public boolean GetDataExcelFile(String fileDownloadPath) throws Exception {

        this.excelHelpers.setExcelFile(fileDownloadPath, "Sheet 1");

        String actualSTT = this.excelHelpers.getCellData("STT",0);
        System.out.println("actualSTT:" + actualSTT);

        String actualCongTy = this.excelHelpers.getCellData("Công ty",0);
        System.out.println("actualCongty:" + actualCongTy);

        String actualBienSo = this.excelHelpers.getCellData("Biển số",0);
        System.out.println("actualBienSo:" + actualBienSo);

        String actualTrangThai = this.excelHelpers.getCellData("Trạng thái",0);
        System.out.println("actualTrangThai:" + actualTrangThai);

        String actualTenGoiCuoc = this.excelHelpers.getCellData("Tên gói cước",0);
        System.out.println("actualTenGoiCuoc:" + actualTenGoiCuoc);

        String actualThoiGianBatDau = this.excelHelpers.getCellData("Thời gian bắt đầu",0);
        System.out.println("actualThoiGianBatDau:" + actualThoiGianBatDau);

        String actualThoiGianKetThuc = this.excelHelpers.getCellData("Thời gian kết thúc",0);
        System.out.println("actualThoiGianKetThuc: " + actualThoiGianKetThuc);

        String actualHanhDong = this.excelHelpers.getCellData("Hành động",0);
        System.out.println("actualHanhDong:" + actualHanhDong);

        String actualKenhDauNoi = this.excelHelpers.getCellData("Kênh đấu nối",0);
        System.out.println("actualKenhDauNoi:" + actualKenhDauNoi);

        String actualThoiGianTacDong= this.excelHelpers.getCellData("Thời gian tác động",0);
        System.out.println("actualThoiGianTacDong:" + actualThoiGianTacDong);

        if(actualSTT.equals("STT") && actualBienSo.equals("Biển số") && actualCongTy.equals("Công ty") && actualTrangThai.equals("Trạng thái")
                && actualTenGoiCuoc.equals("Tên gói cước") && actualThoiGianBatDau.equals("Thời gian bắt đầu") && actualThoiGianKetThuc.equals("Thời gian kết thúc")
                && actualHanhDong.equals("Hành động") && actualKenhDauNoi.equals("Kênh đấu nối") && actualThoiGianTacDong.equals("Thời gian tác động")){
            System.out.println("Các header chính xác");
            return true;
        } else {
            System.out.println("Các header không chính xác");
        }
        return false;
    }
    public int GetNumberOfRows(String excelFilePath, String sheetName){
        int rowCount = 0;
        try (FileInputStream fis = new FileInputStream(new File(excelFilePath))) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet != null) {
                rowCount = sheet.getPhysicalNumberOfRows();
            } else {
                System.err.println("Sheet " + sheetName + " does not exist in the workbook.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rowCount;
    }
}
