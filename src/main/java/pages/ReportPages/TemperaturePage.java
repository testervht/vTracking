package pages.ReportPages;

//import hienntt.bases.BaseSetUp;
import bases.ExcelHelpers;
import bases.WebUIHelpers;
import org.testng.Assert;
import pages.History.RoutePage;
import pages.LoginPage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class TemperaturePage {
    WebDriver driver;
    private LoginPage loginPage;
    private WebUIHelpers uiHelpers;
    private ExcelHelpers excelHelpers;
    private RoutePage routePage;
    private AllReportPage allReportPage;
    private By tabBaocao = By.xpath("/html/body/div[1]/div[2]/div[10]/a/span");
    private By baoCaoLichSuNhietDo = By.xpath("/html/body/div[4]/div[1]/div[4]/div[10]/a/span");
    private By dropdownLocator = By.id("Nhietdo_plateNo");
    private By fullListLocator = By.xpath("//*[@id=\"device-ffe633ce-520e-4c99-a44a-3f8ec6cacee9\"]");
    //Calendar
    private By calendarFrom = By.id("Nhietdo_start-time");
    private By selectMonthFrom = By.xpath("//th[@title='Select Month']");
    private By calendarTo = By.id("Nhietdo_end-time");
    private By selectMonthTo = By.xpath("/html[1]/body[1]/div[4]/div[2]/div[1]/div[3]/div[1]/div[1]/ul[1]/li[1]/div[1]/div[1]/table[1]/thead[1]/tr[1]/th[2]");

    private By buttonTaoBaoCao = By.id("ht_createReportBtn");
    private By buttonXuatExcel = By.id("Fuel_Excel");
    private By notiPopup = By.cssSelector("span[data-notify='message']");
    private By textKetQua = By.id("Nhietdo_search-result");
    public TemperaturePage(WebDriver driver) throws Exception {
        this.driver = driver;
        uiHelpers = new WebUIHelpers(driver);
        routePage = new RoutePage(driver);
        allReportPage = new AllReportPage(driver);
        this.loginPage = new LoginPage(driver);
        this.excelHelpers = new ExcelHelpers();
        System.out.println("Bao Cao Lich su nhiet do Page browser:" + driver);
        loginPage.loginDefault();
        navigation();
    }
    public void navigation(){
        uiHelpers.clickElement(tabBaocao);
        uiHelpers.clickElement(baoCaoLichSuNhietDo);
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
    public void NotSelectLicensePlater(By element){
        boolean checkButtonClickable = isButtonClickable(element);
        if (checkButtonClickable) {
            driver.findElement(element).click();
            System.out.println("Button Tao bao cao clicked.");
        } else {
            System.out.println("Button Tao bao cao is disable.");
        }
    }
    public void enterPlate(String keyword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        dropdown.click();
        // Tìm ô tìm kiếm trong dropdown và nhập từ khóa tìm kiếm
        By searchBoxLocator = By.id("textSearchOrgAndDevice");
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchBoxLocator));
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.RETURN); // Nhan enter de tim kiem
        // Chờ kết quả tìm kiếm và chọn mục từ danh sách
        By searchResultLocator = By.id("listSearchOrgAndDevice");
        //WebElement searchResult = wait.until(ExpectedConditions.elementToBeClickable(searchResultLocator));
        By checkbox = By.xpath("//*[@id=\"device-ffe633ce-520e-4c99-a44a-3f8ec6cacee9\"]/div[28]/div[1]/label");
        By buttonXacNhan = By.id("Nhietdo_modalConfirmBtn");
        List<WebElement> searchResults = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(searchResultLocator));
        // Duyệt qua các kết quả và chọn mục phù hợp
        for (WebElement searchResult : searchResults) {
            if (searchResult.getText().contains(keyword)) {
                searchResult.click();
                //System.out.println("da vao dc den day");
                // Chờ danh sách đầy đủ xuất hiện
                List<WebElement> fullListItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(fullListLocator));
                System.out.println("da vao dc den day2");
                uiHelpers.clickElement(checkbox);
                System.out.println("Sau khi nhan checkbox");
                uiHelpers.clickElement(buttonXacNhan);
                return;
            }
        }
        System.out.println("No search results found for keyword: " + keyword);
    }
    public void EnterCalendar(String dateFrom, String monthFrom, String yearFrom, String dateTo, String monthTo, String yearTo){
        uiHelpers.clickElement(calendarFrom);
        uiHelpers.clickElement(selectMonthFrom);
        By _monthFrom = By.xpath ("//span[normalize-space()='" + monthFrom + "']");
        uiHelpers.clickElement(_monthFrom);
        By _dateFrom = By.xpath("//div[normalize-space()='" + dateFrom + "']");
        uiHelpers.clickElement(_dateFrom);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Sau khi chọn xong Tu ngay");

        uiHelpers.clickElement(calendarTo);
        //System.out.println("Sau khi nhan vao Calendar Ngay den");
        uiHelpers.clickElement(selectMonthTo);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Sau khi nhan vao Thang nam den");
        By _monthTo = By.xpath("//span[normalize-space()='"+ monthTo + "']");
        uiHelpers.clickElement(_monthTo);
        //System.out.println("Sau khi nhan vao chon Thang den");
        By _dateTo = By.xpath("//div[normalize-space()='" + dateTo + "']");
        uiHelpers.clickElement(_dateTo);
        //System.out.println("Sau khi chọn xong den ngay");

    }
    public boolean VerifyNoti(By element, String expectText){
        String actualAlert = uiHelpers.getText(element);
        System.out.println("Actual Alert:" + actualAlert);
        if(actualAlert.equals(expectText)){
            return true;
        }else{
            return false;
        }
    }
    public void ExportExcel(int IDPlateNo, String dateFrom, String monthFrom, String yearFrom, String dateTo, String monthTo, String yearTo) throws Exception {
        this.excelHelpers.setExcelFile("dataTestvTracking.xlsx", "RouteHistory");
        EnterCalendar(dateFrom, monthFrom, yearFrom, dateTo, monthTo, yearTo);
        String plate = routePage.getPlate(IDPlateNo);
        allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_Temperature, routePage.getId(IDPlateNo), allReportPage.btnSave_Temperature);
        allReportPage.createReport(allReportPage.btnCreate_Temperature);

        uiHelpers.clickElement(AllReportPage.btnExport_Temperature);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Assert.assertEquals(uiHelpers.getText(notiPopup), "Xuất báo cáo thành công", "Xuất báo cáo thành công");

        String fileDownloadPath = excelHelpers.getLatestFilePathFromDownloads();
        if(fileDownloadPath!=null){
            System.out.println("Tai file ve thanh cong");
        }else{
            System.out.println("Tai file ve khong thanh cong");
        }
        //tam cmt lai doan code check du lieu tai ve
//        String fileDownloadPath = excelHelpers.getLatestFilePathFromDownloads();
//
//        int rowDownload = GetNumberOfRows(fileDownloadPath, "Sheet 1");
//        int actualRow = rowDownload - 1;
//
//        String actualTextKetqua = uiHelpers.getText(textKetQua);
//
//
//        String ketQuaTaiVe = "KẾT QUẢ TÌM KIẾM: " + actualRow;
//
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

        String actualBienSo = this.excelHelpers.getCellData("Biển số xe",0);
        System.out.println("actualBienSo:" + actualBienSo);

        String actualNhietDoC = this.excelHelpers.getCellData("Nhiệt độ(C)",0);
        System.out.println("actualNhietDoC:" + actualNhietDoC);

        String actualNhietDoF = this.excelHelpers.getCellData("Nhiệt độ (F)",0);
        System.out.println("actualNhietDoF:" + actualNhietDoF);

        String actualThoiGian = this.excelHelpers.getCellData("Thời gian",0);
        System.out.println("actualThoiGian: " + actualThoiGian);

        String actualToaDo = this.excelHelpers.getCellData("Tọa độ",0);
        System.out.println("actualToaDo:" + actualToaDo);

        String actualViTri = this.excelHelpers.getCellData("Vị trí",0);
        System.out.println("actualViTri:" + actualViTri);



        if(actualSTT.equals("STT") && actualBienSo.equals("Biển số") && actualCongTy.equals("Công ty")
                && actualNhietDoC.equals("Nhiệt độ(C)") && actualNhietDoF.equals("Nhiệt độ(F)") && actualThoiGian.equals("Thời gian") &&
                actualToaDo.equals("Tọa độ") && actualViTri.equals("Vị trí") ){
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
