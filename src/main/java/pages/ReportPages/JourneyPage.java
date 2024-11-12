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
import java.util.*;

public class JourneyPage {
    WebDriver driver;
//    private BaseSetUp baseSetUp = new BaseSetUp();
    private LoginPage loginPage;
    private WebUIHelpers webUIHelpers;
    private ExcelHelpers excelHelpers;
    private AllReportPage allReportPage;
    private RoutePage routePage;
    private By tabBaocao = By.xpath("/html/body/div[1]/div[2]/div[10]/a/span");
    private By baoCaoHanhTrinh = By.xpath("/html/body/div[4]/div[1]/div[2]/div[1]/a/span");
    //Input
    private By dropdownLocator = By.xpath("//*[@id=\"journey_plateNo\"]");
    private By fullListLocator = By.xpath("//*[@id=\"device-ffe633ce-520e-4c99-a44a-3f8ec6cacee9\"]");
    //date from


    //Calendar
    private By calendarFrom = By.id("journey_start-time");
    private By selectMonthFrom = By.xpath("//th[@title='Select Month']");
    private By calendarTo = By.id("journey_end-time");
    private By selectMonthTo = By.xpath("/html[1]/body[1]/div[4]/div[2]/div[1]/div[3]/div[1]/div[1]/ul[1]/li[1]/div[1]/div[1]/table[1]/thead[1]/tr[1]/th[2]");


    private By notiPopup = By.cssSelector("span[data-notify='message']");

    private By textKetQua = By.id("journey_search-result");
    public JourneyPage(WebDriver driver) throws Exception {
        this.driver = driver;
        webUIHelpers = new WebUIHelpers(driver);
        routePage = new RoutePage(driver);
        allReportPage = new AllReportPage(driver);
        this.loginPage = new LoginPage(driver);
        this.excelHelpers = new ExcelHelpers();
        System.out.println("Bao Cao Hanh Trinh Page browser:" + driver);
        loginPage.loginDefault();
        navigation();
    }
    public void navigation(){
        webUIHelpers.clickElement(tabBaocao);
        webUIHelpers.clickElement(baoCaoHanhTrinh);
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

    public void NotSelectLicensePlate(){
        boolean checkButtonClickable = isButtonClickable(AllReportPage.btnCreate_Journey);
        if (checkButtonClickable) {
            driver.findElement(AllReportPage.btnCreate_Journey).click();
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
        By searchBoxLocator = By.xpath("//*[@id=\"textSearchOrgAndDevice\"]");

        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchBoxLocator));
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.RETURN); // Nhan enter de tim kiem
        // Chờ kết quả tìm kiếm và chọn mục từ danh sách
        By searchResultLocator = By.xpath("//*[@id=\"listSearchOrgAndDevice\"]");
        //WebElement searchResult = wait.until(ExpectedConditions.elementToBeClickable(searchResultLocator));
        By checkbox = By.xpath("//*[@id=\"div-item-646c95a4-b7c0-4896-a6e4-7ab133a2b644\"]/div/label");
        By buttonXacNhan = By.id("journey_modalConfirmBtn");
        List<WebElement> searchResults = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(searchResultLocator));
        // Duyệt qua các kết quả và chọn mục phù hợp
        for (WebElement searchResult : searchResults) {
            if (searchResult.getText().contains(keyword)) {
                searchResult.click();
                //System.out.println("da vao dc den day");
                // Chờ danh sách đầy đủ xuất hiện
                List<WebElement> fullListItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(fullListLocator));
                //System.out.println("da vao dc den day2");
                webUIHelpers.clickElement(checkbox);
                webUIHelpers.clickElement(buttonXacNhan);
                return;
            }
        }
        System.out.println("No search results found for keyword: " + keyword);
    }

    public void EnterCalendar(String dateFrom, String monthFrom, String yearFrom, String dateTo, String monthTo, String yearTo){
        webUIHelpers.clickElement(calendarFrom);
        webUIHelpers.clickElement(selectMonthFrom);
        By _monthFrom = By.xpath ("//span[normalize-space()='" + monthFrom + "']");
        webUIHelpers.clickElement(_monthFrom);
        By _dateFrom = By.xpath("//div[normalize-space()='" + dateFrom + "']");
        webUIHelpers.clickElement(_dateFrom);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        webUIHelpers.clickElement(calendarTo);
        webUIHelpers.clickElement(selectMonthTo);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        By _monthTo = By.xpath("//span[normalize-space()='"+ monthTo + "']");
        webUIHelpers.clickElement(_monthTo);
        By _dateTo = By.xpath("//div[normalize-space()='" + dateTo + "']");
        webUIHelpers.clickElement(_dateTo);


    }
    public boolean VerifyNoti(By element, String expectText){
        String actualAlert = webUIHelpers.getText(element);
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
        allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_Journey, routePage.getId(IDPlateNo), allReportPage.btnSave_Journey);
        allReportPage.createReport(allReportPage.btnCreate_Journey);

        webUIHelpers.clickElement(AllReportPage.btnExport_Journey);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Assert.assertEquals(webUIHelpers.getText(notiPopup), "Xuất báo cáo thành công", "Xuất báo cáo thành công");

        String fileDownloadPath = excelHelpers.getLatestFilePathFromDownloads();
        if(fileDownloadPath!=null){
            System.out.println("Tai file ve thanh cong");
        }else{
            System.out.println("Tai file ve khong thanh cong");
        }

        //tam cmt lai doan check du lieu file tai ve

//        String fileDownloadPath = excelHelpers.getLatestFilePathFromDownloads();
//
//        int rowDownload = GetNumberOfRows(fileDownloadPath, "Báo cáo hành trình phương tiện");
//        int actualRow = rowDownload - 1;
//
//        String actualTextKetqua = webUIHelpers.getText(textKetQua);
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
        this.excelHelpers.setExcelFile(fileDownloadPath, "Báo cáo hành trình phương tiện");

        String actualSTT = this.excelHelpers.getCellData("STT",0);
        //System.out.println("actualSTT:" + actualSTT);

        String actualBienSo = this.excelHelpers.getCellData("Biển số",0);
        //System.out.println("actualBienSo:" + actualBienSo);

        String actualThoiDiem = this.excelHelpers.getCellData("Thời điểm",0);
        //System.out.println("actualThoiDiem:" + actualThoiDiem);

        String actualTrangThai = this.excelHelpers.getCellData("Trạng thái",0);
        //System.out.println("actualTrangThai:" + actualTrangThai);

        String actualTocDo = this.excelHelpers.getCellData("Tốc độ",0);
        //System.out.println("actualTocDo:" + actualTocDo);

        String actualToaDo = this.excelHelpers.getCellData("Toạ độ",0);
        //System.out.println("actualToaDo:" + actualToaDo);

        String actualViTri = this.excelHelpers.getCellData("Vị trí",0);
        //System.out.println("actualViTri:" + actualViTri);

        if(actualSTT.equals("STT") && actualBienSo.equals("Biển số") && actualThoiDiem.equals("Thời điểm") && actualTrangThai.equals("Trạng thái")
                && actualTocDo.equals("Tốc độ") && actualToaDo.equals("Toạ độ") && actualViTri.equals("Vị trí")){
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