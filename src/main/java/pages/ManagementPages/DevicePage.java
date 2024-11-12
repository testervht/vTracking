package pages.ManagementPages;

import bases.WebUIHelpers;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.LoginPage;
import pages.ManagePage;
import bases.ExcelHelpers;

import static bases.BaseSetup.extent;

public class DevicePage {
    public DevicePage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
        this.managePage = new ManagePage(driver);
    }
    private final WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private LoginPage loginPage;
    private ManagePage managePage;

    //Các element trong search
    private By tbSearch = By.id("search-device-imei");
    private By dropdownStatusSetup = By.id("search-device-setup");
    private By dropdownTypeDevice = By.id("search-device-model");
    private By dropdownCompany = By.id("nameOrgDeviceTab");
    private By btnSearch = By.cssSelector("[onclick=\"searchDeviceList()\"]");
    private By textTotalDevice = By.id("totalDevice");
    //Các button chức năng
    private By btnAdd = By.cssSelector(".btn.btn-add[onclick=\"document.getElementById('addDeviceForm').reset()\"]");
    private By btnExport = By.cssSelector(".btn.btn-export[onclick=\"exportDevice(0)\"]");
    private By btnEdit = By.xpath("//*[@id=\"device_table\"]/tbody/tr/td[2]/img[2]");
    private By btnDel = By.xpath("//*[@id=\"device_table\"]/tbody/tr/td[2]/img[3]");
    private By btnConfirmDel = By.cssSelector(".btn.btn-save[onclick='processDeleteDevice()']");


    //Các giá trị trong bảng danh sách
    private By textInTable = By.className("no_table_data");
    private By infoImeiDevice = By.cssSelector(".wrap-text.imeiDevice");
    private By infoKieuDeive = By.cssSelector(".wrap-text.kieuDevice");
    private By infoCompany = By.cssSelector(".wrap-text.companyDevice");
    private By infoStatusDevice = By.cssSelector(".wrap-text.statusDevice");
    private By alertPopup = By.cssSelector("span[data-notify='message']");

    //element trong popup Thêm thiết bị
    public class AddDeviceLocators {
        public static final By tbImeiDevice = By.id("imeiDeviceAdd");
        public static final By treeCompanyDevice = By.id("nameOrgDeviceAdd");
        public static final By dropdownKieuDeivce = By.id("modelDeviceAdd");
        public static final By dropdownLoaiDeivce = By.id("templateDeviceAdd");
        public static final By tbSIM = By.id("isdnAdd");
        public static final By btnCancel = By.cssSelector("#addDeviceModal .btn.btn-cancle");
        public static final By btnSave = By.cssSelector(".btn.btn-save[onclick=\"validateAddDevice()\"]");
        //noti
        public static final By notiImeiDevice = By.id("imeiDeviceAddNoti");
        public static final By notiCompany = By.id("nameOrgDeviceAddNoti");
        public static final By notiKieuDevice = By.id("modelDeviceAddNoti");
        public static final By notiLoaiDevice = By.id("templateDeviceAddNoti");
        //thông báo đẩy
        public static final By alertPopup = By.cssSelector("span[data-notify='message']");
    }
    public class EditDeviceLocators {
        public static final By tbImeiDevice = By.id("imeiDeviceEdit");
        public static final By treeCompanyDevice = By.id("nameOrgDeviceEdit");
        public static final By dropdownKieuDeivce = By.id("modelDeviceEdit");
        public static final By dropdownLoaiDeivce = By.id("templateDeviceEdit");
        public static final By tbSIM = By.id("isdnEdit");
        public static final By btnCancel = By.cssSelector("#editDeviceModal .btn.btn-cancle");
        public static final By btnSave = By.cssSelector(".btn.btn-save[onclick=\"validateEditDevice()\"]");
        //noti
        public static final By notiImeiDevice = By.id("imeiDeviceEditNoti");
        public static final By notiCompany = By.id("nameOrgDeviceEditNoti");
        public static final By notiKieuDevice = By.id("modelDeviceEditNoti");
        public static final By notiLoaiDevice = By.id("templateDeviceEditNoti");
        //thông báo đẩy
        public static final By alertPopup = By.cssSelector("span[data-notify='message']");
    }

    private String filePath = "dataTestvTracking.xlsx"; // Anhntl52 cập nhật filepath tương đối
    private String sheetName = "MngDevice";

    public String getImei(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"imei", index);
    }
    public String getCompany(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"company", index);
    }
    public String getKieu(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"kieu", index);
    }
    public String getLoai(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"loai", index);
    }
    public String getSIM(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"SIM", index);
    }

    //Search
    public void search(String imei, String status, String type, String company) {
        webUIHelpers.setText(tbSearch, imei);
        if (!status.isEmpty()) {
            webUIHelpers.selectElement(dropdownStatusSetup, status);
        }
        if (!type.isEmpty()) {
            webUIHelpers.selectElement(dropdownTypeDevice, type);
        }
        if (!company.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeDeviceTab') and contains(text(), '" + company + "')]";
            webUIHelpers.clickElementAndSelect(dropdownCompany, By.xpath(xpathExpression));
        }
        webUIHelpers.clickElement(btnSearch);
        webUIHelpers.sleep(3000);
    }
    public void searchWithoutValue() {
        search("0000000000000021", "", "", "");
        String actualTextTotal = webUIHelpers.getText(textTotalDevice);
        String actualTextInTable = webUIHelpers.getText(textInTable);
        String expectTextTotal = "Tổng cộng: 0 thiết bị";
        String expectTextInTable = "Không có dữ liệu thiết bị";
        ExtentTest test = extent.createTest("Verify search without value");
        if (expectTextTotal.equals(actualTextInTable) && actualTextTotal.equals(expectTextTotal)){
            test.log(Status.PASS, "Current text in table: " + actualTextInTable + " - Current text total: " + actualTextTotal);
        }
        else {
            test.log(Status.FAIL, "Current text in table: " + actualTextInTable + " - Current text total: " + actualTextTotal);
        }
        Assert.assertEquals(actualTextTotal, expectTextTotal);
        Assert.assertEquals(actualTextInTable, expectTextInTable);
    }
    public void searchByTextsearch(String expect) {
        String expectText = expect.toLowerCase();
        webUIHelpers.setText(tbSearch, expect);
        webUIHelpers.clickElement(btnSearch);
        webUIHelpers.sleep(3000);
        for (WebElement element : driver.findElements(infoImeiDevice)) {
            String actualText = element.getText().trim().toLowerCase();
            Assert.assertTrue(actualText.contains(expectText), "Element text doesn't contain '" + expectText + "'");
        }
    }
    public void searchByFilter(String imei, String status, String type, String company, String expect, By listByFilter) {
        String expectText = expect.toLowerCase();
        search(imei, status, type, company);
        for (WebElement element : driver.findElements(listByFilter)) {
            String actualText = element.getText().trim().toLowerCase();
            Assert.assertTrue(actualText.contains(expectText), "Element text doesn't contain '" + expectText + "'");
        }
    }
    public void searchRelativeValue(){
        searchByFilter("00000010","","","","00000010", infoImeiDevice);
    }
    public void searchAbsoluteValue() {
        searchByFilter("000000000000008","","","","000000000000008", infoImeiDevice);
        String actualTextTotal = webUIHelpers.getText(textTotalDevice);
        String expectTextTotal = "Tổng cộng: 1 thiết bị";
        Assert.assertEquals(actualTextTotal, expectTextTotal);
    }
    public void searchByStatus(){
        searchByFilter(" ","Đã cài đặt","","","Đã cài đặt", infoStatusDevice);
    }
    public void searchByType(){
        searchByFilter(" ","","Giám sát hành trình","","Giám sát hành trình", infoKieuDeive);
    }
    public void searchByCompany(){
        searchByFilter(" ","","","Công ty test 21/11","Công ty test 21/11", infoCompany);
    }
    public void searchByMultiConditions(){
        String type = "Giám sát hành trình";
        String status = "Đã cài đặt";
        String expectType = type.toLowerCase();
        String expectStatus = status.toLowerCase();
        searchByFilter(" ", status, type,"Công ty test 21/11","Công ty test 21/11", infoCompany);
        for (WebElement element : driver.findElements(infoKieuDeive)) {
            String actualText = element.getText().trim().toLowerCase();
            Assert.assertTrue(actualText.contains(expectType), "Element text doesn't contain '" + "Giám sát hành trình" + "'");
        }
        for (WebElement element : driver.findElements(infoStatusDevice)) {
            String actualText = element.getText().trim().toLowerCase();
            Assert.assertTrue(actualText.contains(expectStatus), "Element text doesn't contain '" + "Đã cài đặt" + "'");
        }
    }

    //create
    public void fillInfoCompany(
            String imei,
            String company,
            String kieu,
            String loai,
            String SIM
    ) {
        webUIHelpers.sleep(3000);
        webUIHelpers.setText(AddDeviceLocators.tbImeiDevice, imei);
        if (!company.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeDeviceAdd') and contains(text(), '" + company + "')]";
            webUIHelpers.clickElementAndSelect(AddDeviceLocators.treeCompanyDevice, By.xpath(xpathExpression));
        }
        webUIHelpers.clickElement(By.xpath("//*[@id=\"addDeviceModal\"]/div/div/div[1]/h5"));
        if (!kieu.isEmpty()) {
            webUIHelpers.selectElement(AddDeviceLocators.dropdownKieuDeivce, kieu);
        }
        webUIHelpers.sleep(3000);
        if (!loai.isEmpty()) {
            webUIHelpers.selectElement(AddDeviceLocators.dropdownLoaiDeivce, loai);
        }
        webUIHelpers.setText(AddDeviceLocators.tbSIM, SIM);
    }
    public void create(
            String imei,
            String company,
            String kieu,
            String loai,
            String SIM
    ){
        webUIHelpers.clickElement(btnAdd);
        fillInfoCompany(imei, company, kieu, loai, SIM);
        webUIHelpers.clickElement(AddDeviceLocators.btnSave);
        webUIHelpers.sleep(3000);
    }
    public void createWithoutRequiredField(){
        webUIHelpers.clickElement(btnAdd);
        webUIHelpers.sleep(3000);
        webUIHelpers.clickElement(AddDeviceLocators.btnSave);
        webUIHelpers.sleep(3000);
        String notiActual = webUIHelpers.getText(AddDeviceLocators.notiImeiDevice);
        String expectText = "Trường này là trường bắt buộc";
        ExtentTest test = extent.createTest("Verify create without required field");
        if (expectText.equals(notiActual)){
            test.log(Status.PASS, "Current noti: " + notiActual);
        }
        else {
            test.log(Status.FAIL, "Current noti: " + notiActual);
        }
        Assert.assertEquals(notiActual, expectText);
    }
    public void createWithoutCompany(){
        create(
                this.getImei(1),
                this.getCompany(1),
                this.getKieu(1),
                this.getLoai(1),
                this.getSIM(1)
        );
        String notiActual = webUIHelpers.getText(AddDeviceLocators.notiCompany);
        String expectText = "Trường này là trường bắt buộc";
        ExtentTest test = extent.createTest("Verify create device without comnpany");
        if (expectText.equals(notiActual)){
            test.log(Status.PASS, "Current noti: " + notiActual);
        }
        else {
            test.log(Status.FAIL, "Current noti: " + notiActual);
        }
        Assert.assertEquals(notiActual, expectText);
    }
    public void createWithDuplicateImei(){
        create(
                this.getImei(2),
                this.getCompany(2),
                this.getKieu(2),
                this.getLoai(2),
                this.getSIM(2)
        );
        String alertActual = webUIHelpers.getText(AddDeviceLocators.alertPopup);
        String expectAlert = "Mã thiết bị đã tồn tại. Vui lòng nhập lại";
        ExtentTest test = extent.createTest("Verify create device with duplicate imei");
        if (expectAlert.equals(alertActual)){
            test.log(Status.PASS, "Current alert: " + alertActual);
        }
        else {
            test.log(Status.FAIL, "Current alert: " + alertActual);
        }
        Assert.assertEquals(alertActual, expectAlert);
    }
    public void createDeviceSuccessfully(){
        create(
                this.getImei(3),
                this.getCompany(3),
                this.getKieu(3),
                this.getLoai(3),
                this.getSIM(3)
        );
        String alertActual = webUIHelpers.getText(AddDeviceLocators.alertPopup);
        String expectAlert = "Tạo thành công";
        searchByFilter("0000011042024","","","","0000011042024", infoImeiDevice);
        String device = webUIHelpers.getText(infoImeiDevice);
        Assert.assertEquals(device, "0000011042024");
        Assert.assertEquals(alertActual, expectAlert);
    }
    //edit function
    public void clickBtnEdit(){
        webUIHelpers.clickElement(btnEdit);
    }
    public void editInfoDevice(
            String imei,
            String company,
            String kieu,
            String loai,
            String SIM
    ) {
        webUIHelpers.sleep(3000);
        webUIHelpers.clearAndSetText(EditDeviceLocators.tbImeiDevice, imei);
        if (!company.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeDeviceEdit') and contains(text(), '" + company + "')]";
            webUIHelpers.clickElementAndSelect(EditDeviceLocators.treeCompanyDevice, By.xpath(xpathExpression));
        }
        webUIHelpers.clickElement(By.xpath("//*[@id=\"editDeviceModal\"]/div/div/div[1]/h5"));
        if (!kieu.isEmpty()) {
            webUIHelpers.selectElement(EditDeviceLocators.dropdownKieuDeivce, kieu);
        }
        webUIHelpers.sleep(3000);
        if (!loai.isEmpty()) {
            webUIHelpers.selectElement(EditDeviceLocators.dropdownLoaiDeivce, loai);
        }
        webUIHelpers.clearAndSetText(EditDeviceLocators.tbSIM, SIM);
    }

    public void editDevice(
            String imei,
            String company,
            String kieu,
            String loai,
            String SIM
    ){
        searchByFilter("0000011042024","","","","0000011042024", infoImeiDevice);
        webUIHelpers.clickElement(btnEdit);
        editInfoDevice(imei, company, kieu, loai, SIM);
        webUIHelpers.clickElement(EditDeviceLocators.btnSave);
    }

    public void editDeviceWithoutRequireField(){
        editDevice(" ", "", "", "", " ");
        String notiActual = webUIHelpers.getText(EditDeviceLocators.notiImeiDevice);
        String expectText = "Trường này là trường bắt buộc";
        Assert.assertEquals(notiActual, expectText);
    }

    public void editWithDuplicateImei(){
        editDevice(
                this.getImei(4),
                this.getCompany(4),
                this.getKieu(4),
                this.getLoai(4),
                this.getSIM(4)
        );
        String alertActual = webUIHelpers.getTextInAlert(EditDeviceLocators.alertPopup);
        String expectAlert = "Mã thiết bị đã tồn tại. Vui lòng nhập lại";
        ExtentTest test = extent.createTest("Verify create device with duplicate imei");
        if (expectAlert.equals(alertActual)){
            test.log(Status.PASS, "Current alert: " + alertActual);
        }
        else {
            test.log(Status.FAIL, "Current alert: " + alertActual);
        }
        Assert.assertEquals(alertActual, expectAlert);
    }

    public void editDeviceNotConnected(){
        editDevice(
                this.getImei(5),
                this.getCompany(5),
                this.getKieu(5),
                this.getLoai(5),
                this.getSIM(5)
        );
        String alertActual = webUIHelpers.getTextInAlert(EditDeviceLocators.alertPopup);
        String expectAlert = "Cập nhật thành công";
        webUIHelpers.clearText(tbSearch);
        searchByFilter("0000016042024", "", "", "", "0000016042024", infoImeiDevice);
        String device = webUIHelpers.getText(infoImeiDevice);
        Assert.assertEquals(device, "0000016042024");
        Assert.assertEquals(alertActual, expectAlert);
    }
    public void verifyEditSuccessfully(){
        String alertActual = webUIHelpers.getTextInAlert(EditDeviceLocators.alertPopup);
        String expectAlert = "Cập nhật thành công";
        Assert.assertEquals(alertActual, expectAlert);
    }

    public void editDeviceInstalled(){
        searchByFilter("013043794410","","","","013043794410", infoImeiDevice);
        webUIHelpers.clickElement(btnEdit);
        WebElement inputElement = driver.findElement(EditDeviceLocators.treeCompanyDevice);
        Assert.assertTrue(!inputElement.isEnabled());
    }

    //delete
    public void deleteDevice(String deletedDevice){
        searchByFilter(deletedDevice, "", "", "", deletedDevice, infoImeiDevice);
        webUIHelpers.clickElement(btnDel);//click button xóa
        webUIHelpers.sleep(3000);
        webUIHelpers.clickElement(btnConfirmDel);
    }
    public void deleteDeviceSuccessfully(){
        deleteDevice("0000016042024");
        webUIHelpers.verifyAlert(webUIHelpers.getTextInAlert(alertPopup), "Xóa thành công");
//        String actualTextTotal = webUIHelpers.getText(textTotalDevice);
//        Assert.assertEquals(actualTextTotal, "Tổng cộng: 0 thiết bị 123");
    }

    public void deleteDeviceUnsuccessfully(String imei){
        deleteDevice(imei);
        webUIHelpers.verifyAlert(webUIHelpers.getTextInAlert(alertPopup), "Thiết bị chỉ có thể xóa khi thiết bị chưa cài đặt và chưa đấu nối");
    }
}
