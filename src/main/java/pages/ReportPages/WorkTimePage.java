package pages.ReportPages;

import bases.BaseSetup;
import bases.ExcelHelpers;
import bases.WebUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.History.RoutePage;
import pages.LoginPage;

import java.time.Duration;
import java.util.List;

public class WorkTimePage extends BaseSetup {
    WebDriver driver;
    private LoginPage loginPage;
    private WebUIHelpers webUIHelpers;
    private ExcelHelpers excelHelpers;
    private AllReportPage allReportPage;
    private RoutePage routePage;
    private By tabBaocao = By.xpath("/html/body/div[1]/div[2]/div[10]/a/span");
    private By baoCaoThoiGianLamViec = By.xpath("//span[contains(text(),'Thời gian làm việc')]");
    private By fullListLocator = By.xpath("//*[@id=\"device-ffe633ce-520e-4c99-a44a-3f8ec6cacee9\"]");
    //Calendar
    private By calendarFrom = By.id("wt_start-time");
    private By selectMonthFrom = By.xpath("//th[@title='Select Month']");

    private By calendarTo = By.id("wt_end-time");
    private By selectMonthTo = By.xpath("//th[@title='Select Month']");

    public WorkTimePage(WebDriver driver) throws Exception {
        this.driver = driver;
        webUIHelpers = new WebUIHelpers(driver);
        routePage = new RoutePage(driver);
        allReportPage = new AllReportPage(driver);
        this.loginPage = new LoginPage(driver);
        this.excelHelpers = new ExcelHelpers();
        System.out.println("Bao cao Thoi gian lam viec Page browser:" + driver);
        loginPage.loginDefault();
        navigation();
    }
    public void navigation(){
        webUIHelpers.clickElement(tabBaocao);
        webUIHelpers.clickElement(baoCaoThoiGianLamViec);
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

    //nhap bien so vao popup Chon Bien so
    public void enterPlate(String keyword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(allReportPage.ddPlate_WorkTime));
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
                webUIHelpers.clickElement(checkbox);
                System.out.println("Sau khi nhan checkbox");
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Sau khi chọn xong Tu ngay");

        webUIHelpers.clickElement(calendarTo);
        //System.out.println("Sau khi nhan vao Calendar Ngay den");
        webUIHelpers.clickElement(selectMonthTo);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Sau khi nhan vao Thang nam den");
        By _monthTo = By.xpath("//span[normalize-space()='"+ monthTo + "']");
        webUIHelpers.clickElement(_monthTo);
        //System.out.println("Sau khi nhan vao chon Thang den");
        By _dateTo = By.xpath("//div[normalize-space()='" + dateTo + "']");
        webUIHelpers.clickElement(_dateTo);
        //System.out.println("Sau khi chọn xong den ngay");

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
        allReportPage.searchAbsoluteValue(plate, allReportPage.ddPlate_WorkTime, routePage.getId(IDPlateNo), allReportPage.btnSave_WorkTime);
        allReportPage.createReport(allReportPage.btnCreate_WorkTime);
        webUIHelpers.clickElement(AllReportPage.btnExport_WorkTime);

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
    }
}
