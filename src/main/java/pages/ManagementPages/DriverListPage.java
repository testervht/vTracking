package pages.ManagementPages;

import bases.WebUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class DriverListPage {
    public DriverListPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
    }
    private final WebDriver driver;
    private WebUIHelpers webUIHelpers;

    //Các element trong search
    private By tbSearch = By.id("textSearchDriver");
    private By dropdownCompany = By.id("nameOrgDriverTab");
    private By btnSearch = By.cssSelector("[onclick=\"searchDriverList()\"]");
    private By textTotalDriver = By.id("totalDriver");

    //Các button chức năng
    private By btnAdd = By.cssSelector("[onclick=\"resetForm('addDriverForm')\"]");
    private By btnExport = By.cssSelector("[onclick=\"exportDriver(0)\"]");
    private By btnEdit = By.xpath("//*[@id=\"driver_table\"]/tbody/tr/td[2]/img[2]");
    private By btnDel = By.xpath("//*[@id=\"driver_table\"]/tbody/tr/td[2]/img[3]");
    private By btnConfirmDel = By.cssSelector("[onclick='processDeleteDriver()']");

    //Các giá trị trong bảng danh sách
    private By textInTable = By.className("no_table_data");

    //alert
    private By alertPopup = By.cssSelector("span[data-notify='message']");

    //element trong popup Thêm loai tb
    public class AddDriverLocators {
        public static final By tbName = By.id("dt_add-name");
        public static final By treeCompany = By.id("nameOrgDriverAdd");
        public static final By tbCard = By.id("dt_add-card");
        public static final By tbPhone = By.id("dt_add-phone");
        public static final By tbLicense = By.id("dt_add-license");
        public static final By tbAddress = By.id("dt_add-address");
        public static final By timepickerExprired = By.id("dt_add-license-expired");
        public static final By timepickerBirthday = By.id("dt_add-date");
        public static final By tbID = By.id("dt_add-gov-id");
        public static final By btnAvatar = By.id("dt_add-profile-wizard-picture");
        public static final By btnFrontImg = By.id("dt_add-gov-front-wizard-picture");
        public static final By btnBackImg = By.id("dt_add-gov-back-wizard-picture");
        public static final By btnCancel = By.cssSelector("#addDriverModal .btn.btn-cancle");
        public static final By btnSave = By.cssSelector("[onclick=\"validateAddDriver()\"]");
        //noti
        public static final By notiName = By.id("dt_add-name-validation");
        public static final By notiCompany = By.id("nameOrgDriverAdd-validation");
        public static final By notiCard = By.id("dt_add-card-validation");
        public static final By notiLicense = By.id("dt_add-license-validation");
        public static final By notiExpired = By.id("dt_add-license-expired-validation");
        public static final By notiID = By.id("dt_add-gov-id-validation");
    }
    public class EditDriverLocators {
        public static final By tbName = By.id("dt_edit-name");
        public static final By treeCompany = By.id("nameOrgDriverEdit");
        public static final By tbCard = By.id("dt_edit-card");
        public static final By tbPhone = By.id("dt_edit-phone");
        public static final By tbLicense = By.id("dt_edit-license");
        public static final By tbAddress = By.id("dt_edit-address");
        public static final By timepickerExprired = By.id("dt_edit-license-expired");
        public static final By timepickerBirthday = By.id("dt_edit-date");
        public static final By tbID = By.id("dt_edit-gov-id");
        public static final By btnAvatar = By.id("dt_edit-profile-wizard-picture");
        public static final By btnFrontImg = By.id("dt_edit-gov-front-wizard-picture");
        public static final By btnBackImg = By.id("dt_edit-gov-back-wizard-picture");
        public static final By btnCancel = By.cssSelector("#editDriverModal .btn.btn-cancle");
        public static final By btnSave = By.cssSelector("[onclick=\"validateEditDriver()\"]");
        //noti
        public static final By notiName = By.id("dt_edit-name-validation");
        public static final By notiCompany = By.id("nameOrgDriverEdit-validation");
        public static final By notiCard = By.id("dt_edit-card-validation");
        public static final By notiLicense = By.id("dt_edit-license-validation");
        public static final By notiExpired = By.id("dt_edit-license-expired-validation");
        public static final By notiID = By.id("dt_edit-gov-id-validation");
    }

    //Search
    public void search(String searchKey, String company) {
        webUIHelpers.setText(tbSearch, searchKey);
        webUIHelpers.sleep(5000);
        if (!company.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeDriverTab') and contains(text(), '" + company + "')]";
            webUIHelpers.clickElementAndSelect(dropdownCompany, By.xpath(xpathExpression));
        }
        webUIHelpers.clickElement(btnSearch);
        webUIHelpers.sleep(5000);
    }
    public void verifySearchWithoutValue() {
        String actualTextTotal = webUIHelpers.getText(textTotalDriver);
        String actualTextInTable = webUIHelpers.getText(textInTable);
        String expectTextTotal = "Tổng cộng: 0 Lái xe";
        String expectTextInTable = "Không có dữ liệu lái xe";
        Assert.assertEquals(actualTextTotal, expectTextTotal);
        Assert.assertEquals(actualTextInTable, expectTextInTable);
    }
    public void verifySearchByText(String expect) {
        String expectText = expect.toLowerCase();
        List<WebElement> rows = driver.findElements(By.xpath("//*[@id='driver_table']//tbody//tr"));
        for (WebElement row : rows) {
            WebElement tdElement5 = row.findElement(By.xpath("./td[5]"));
            WebElement tdElement6 = row.findElement(By.xpath("./td[7]"));
            String actualText5 = tdElement5.getText().toLowerCase();
            String actualText7 = tdElement6.getText().toLowerCase();
            Assert.assertTrue(actualText5.contains(expectText) || actualText7.contains(expectText));
        }
    }
    //create
    public void fillInfoDriver(
            String name,
            String company,
            String card,
            String phone,
            String license,
            String address,
//            String exprired,
//            String birthday,
            String ID
    ) {
        webUIHelpers.sleep(3000);
        webUIHelpers.setText(AddDriverLocators.tbName, name);
        if (!company.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeDriverAdd') and contains(text(), '" + company + "')]";
            webUIHelpers.clickElementAndSelect(AddDriverLocators.treeCompany, By.xpath(xpathExpression));
            webUIHelpers.clickElement(By.xpath(xpathExpression));
        }
        webUIHelpers.setText(AddDriverLocators.tbCard, card);
        webUIHelpers.setText(AddDriverLocators.tbPhone, phone);
        webUIHelpers.setText(AddDriverLocators.tbLicense, license);
        webUIHelpers.setText(AddDriverLocators.tbAddress, address);
        webUIHelpers.clickElement(AddDriverLocators.timepickerExprired);
        webUIHelpers.clickElement(AddDriverLocators.timepickerBirthday);
        webUIHelpers.setText(AddDriverLocators.tbID, ID);
    }
    public void createDriver(
            String name,
            String company,
            String card,
            String phone,
            String license,
            String address,
//            String exprired,
//            String birthday,
            String ID
    ){
        webUIHelpers.clickElement(btnAdd);
        webUIHelpers.sleep(3000);
        fillInfoDriver(name, company, card, phone, license, address, ID);
        webUIHelpers.clickElement(AddDriverLocators.btnSave);
        webUIHelpers.sleep(3000);
    }
    public void verifyCreateWithoutRequiredField(){
        String notiNameActual = webUIHelpers.getText(AddDriverLocators.notiName);
        String notiCompanyActual = webUIHelpers.getText(AddDriverLocators.notiCompany);
        String notiCardActual = webUIHelpers.getText(AddDriverLocators.notiCard);
        String notiLicenseActual = webUIHelpers.getText(AddDriverLocators.notiLicense);
        String notiIDActual = webUIHelpers.getText(AddDriverLocators.notiID);
        String expectNameText = "Vui lòng nhập tên lái xe";
        String expectCompanyText = "Vui lòng chọn công ty";
        String expectCardText = "Vui lòng nhập mã thẻ";
        String expectLicenseText = "Vui lòng nhập số GPLX";
        String expectIDText = "Vui lòng nhập CCCD/CMND";
        Assert.assertEquals(notiNameActual, expectNameText);
        Assert.assertEquals(notiCompanyActual, expectCompanyText);
        Assert.assertEquals(notiCardActual, expectCardText);
        Assert.assertEquals(notiLicenseActual, expectLicenseText);
        Assert.assertEquals(notiIDActual, expectIDText);
    }
    public void verifyCreateWithDuplicateCard() {
        webUIHelpers.verifyAlert(webUIHelpers.getTextInAlert(alertPopup), "Mã thẻ đã bị trùng, vui lòng kiểm tra lại");
    }
    public void verifyCreateWithDuplicateLicense(){
        webUIHelpers.verifyAlert(webUIHelpers.getTextInAlert(alertPopup), "Số GPLX đã bị trùng, vui lòng kiểm tra lại");
    }
    public void verifyCreateWithDuplicateID(){
        webUIHelpers.verifyAlert(webUIHelpers.getTextInAlert(alertPopup), "Số CCCD/CMND đã bị trùng, vui lòng kiểm tra lại");
    }
    public void verifyCreateSuccessfully(){
        webUIHelpers.verifyAlert(webUIHelpers.getTextInAlert(alertPopup), "Tạo thành công");
    }
    public void editInfoDriver(
            String name,
            String company,
            String card,
            String phone,
            String license,
            String address,
//            String exprired,
//            String birthday,
            String ID
    ) {
        webUIHelpers.sleep(3000);
        webUIHelpers.clearAndSetText(EditDriverLocators.tbName, name);
        if (!company.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeDriverEdit') and contains(text(), '" + company + "')]";
            webUIHelpers.clickElementAndSelect(EditDriverLocators.treeCompany, By.xpath(xpathExpression));
            webUIHelpers.clickElement(By.xpath(xpathExpression));
        }
        webUIHelpers.clearAndSetText(EditDriverLocators.tbCard, card);
        webUIHelpers.clearAndSetText(EditDriverLocators.tbPhone, phone);
        webUIHelpers.clearAndSetText(EditDriverLocators.tbLicense, license);
        webUIHelpers.clearAndSetText(EditDriverLocators.tbAddress, address);
        webUIHelpers.clickElement(EditDriverLocators.timepickerExprired);
        webUIHelpers.clickElement(EditDriverLocators.timepickerBirthday);
        webUIHelpers.clearAndSetText(EditDriverLocators.tbID, ID);
    }
    public void editDriver(
            String name,
            String company,
            String card,
            String phone,
            String license,
            String address,
//            String exprired,
//            String birthday,
            String ID
    ){
        webUIHelpers.clickElement(btnEdit);
        editInfoDriver(name, company, card, phone, license, address, ID);
        webUIHelpers.clickElement(EditDriverLocators.btnSave);
        webUIHelpers.clearText(tbSearch);
    }
    public void verifyEditWithoutRequiredField(){
        String notiNameActual = webUIHelpers.getText(EditDriverLocators.notiName);
        String notiCardActual = webUIHelpers.getText(EditDriverLocators.notiCard);
        String notiLicenseActual = webUIHelpers.getText(EditDriverLocators.notiLicense);
        String notiIDActual = webUIHelpers.getText(EditDriverLocators.notiID);
        String expectNameText = "Vui lòng nhập tên lái xe";
        String expectCardText = "Vui lòng nhập mã thẻ";
        String expectLicenseText = "Vui lòng nhập số GPLX";
        String expectIDText = "Vui lòng nhập CCCD/CMND";
        Assert.assertEquals(notiNameActual, expectNameText);
        Assert.assertEquals(notiCardActual, expectCardText);
        Assert.assertEquals(notiLicenseActual, expectLicenseText);
        Assert.assertEquals(notiIDActual, expectIDText);
    }
    public void verifyEditSuccessfully(){
        webUIHelpers.verifyAlert(webUIHelpers.getTextInAlert(alertPopup), "Cập nhập thành công");
    }
    public void deleteDriver(){
        webUIHelpers.clickElement(btnDel);
        webUIHelpers.sleep(3000);
        webUIHelpers.clickElement(btnConfirmDel);
    }
    public void verifyDeleteUnusedDriver(){
        webUIHelpers.verifyAlert(webUIHelpers.getTextInAlert(alertPopup), "Xóa thành công");
//        String actualTextTotal = webUIHelpers.getText(textTotalDriver);
//        Assert.assertEquals(actualTextTotal, "Tổng cộng: 0 Lái xe");
    }
    public void verifyDeleteUsedDriver(){
        webUIHelpers.verifyAlert(webUIHelpers.getTextInAlert(alertPopup), "Thông tin lái xe đã có trong hệ thống. Bạn không thể xóa");
    }
    public void verifyBtnEditInUserAcc(){
        List<WebElement> editButtons = driver.findElements(By.cssSelector("img[src='/assets/images/icon/edit.svg']"));
        Assert.assertTrue(editButtons.isEmpty());
    }
    public void verifyBtnDeleteInUserAcc(){
        List<WebElement> deleteButtons = driver.findElements(By.cssSelector("img[src='/assets/images/icon/trash.svg']"));
        Assert.assertTrue(deleteButtons.isEmpty());
    }
    public void verifyBtnAddInUserAcc(){
    }

}
