package pages.ManagementPages;

import bases.WebUIHelpers;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.LoginPage;
import pages.LogoutPage;
import pages.ManagePage;
import bases.ExcelHelpers;

import static bases.BaseSetup.extent;

public class CompanyPage {
    public CompanyPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
        this.logoutPage = new LogoutPage(driver);
        this.managePage = new ManagePage(driver);
    }

    private final WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private LoginPage loginPage;
    private LogoutPage logoutPage;
    private ManagePage managePage;

    //Các element trong search
    private By comboboxSearch = By.xpath("//*[@id=\"companyTab\"]/div[1]/div");
    private By tbSearch = By.id("textSearchCompany");
    private By btnSearch = By.xpath("//*[@id=\"companyTab\"]/div[1]/form/button");
    private By textTotalCompany = By.id("totalCompany");

    //Các button chức năng
    private By btnAdd = By.cssSelector(".btn.btn-add[onclick=\"setAddCompany()\"]");
    private By btnExport = By.cssSelector(".btn.btn-export[onclick=\"exportCompany(0)\"]");
    private By btnConfig = By.xpath("//*[@id=\"companyTab\"]/div[2]/div[2]/button[3]");

    //Các giá trị trong bảng danh sách
    private By textInTable = By.className("no_table_data");
    private By infoCompanyName = By.cssSelector(".wrap-text.companyName");
    private By infoCompanyParent = By.cssSelector(".wrap-text.companyParent");

    //element trong popup Thêm công ty
    public class AddCompanyLocators {
        public static final By tbCompanyName = By.id("nameCompanyAdd");
        public static final By treeParentCompany = By.id("nameOrgCompanyAdd");
        public static final By tbLicense = By.id("businessLicenseAdd");
        public static final By tbRepresentative = By.id("representativeAdd");
        public static final By tbEmail = By.id("emailAdd");
        public static final By tbPhoneCompany = By.id("phoneCompanyAdd");
        public static final By tbWebsite = By.id("websiteAdd");
        public static final By tbLocation = By.id("locationAdd");
        public static final By dropdownOperatingArea = By.id("operatingAreaAdd");
        public static final By tbUsername = By.id("usernameAdd");
        public static final By tbPhoneAccount = By.id("phoneAccountAdd");
        public static final By tbPassword = By.id("passwordAdd");
        public static final By tbRePassword = By.id("rePasswordAdd");
        public static final By datepickerFoundationDate = By.id("foundationDateAdd");
        public static final By selectBusinessType = By.id("businessTypeAdd");
        public static final By selectBusiness = By.id("businessAdd");
        public static final By btnOpenFile = By.xpath("//*[@id=\"addCompanyForm\"]/div[8]/div[2]/div/label/a");
        public static final By btnCancel = By.xpath("//*[@id=\"addCompanyModal\"]/div/div/div[3]/button[1]");
        public static final By btnSave = By.xpath("//*[@id=\"addCompanyModal\"]/div/div/div[3]/button[2]");
        //noti
        public static final By notiCompanyName = By.id("nameCompanyAddNoti");
        public static final By notiUsername = By.id("usernameAddNoti");
        public static final By notiPhoneAcc = By.id("phoneAccountAddNoti");
        public static final By notiBusinessLicense = By.id("businessLicenseAddNoti");
        public static final By notiPassword = By.id("passwordAddNoti");
        public static final By notiRepassword = By.id("rePasswordAddNoti");
        //thông báo đẩy
        public static final By alertPopup = By.cssSelector("span[data-notify='message']");
    }
    public class EditCompanyLocators {
        public static final By tbCompanyName = By.id("nameCompanyEdit");
        public static final By treeParentCompany = By.id("nameOrgCompanyEdit");
        public static final By tbLicense = By.id("businessLicenseEdit");
        public static final By tbRepresentative = By.id("representativeEdit");
        public static final By tbEmail = By.id("emailEdit");
        public static final By tbPhoneCompany = By.id("phoneCompanyEdit");
        public static final By tbWebsite = By.id("websiteEdit");
        public static final By tbLocation = By.id("locationEdit");
        public static final By dropdownOperatingArea = By.id("operatingAreaEdit");
        public static final By tbUsername = By.id("usernameEdit");
        public static final By tbPhoneAccount = By.id("phoneAccountEdit");
        public static final By datepickerFoundationDate = By.id("foundationDateEdit");
        public static final By selectBusinessType = By.id("businessTypeEdit");
        public static final By selectBusiness = By.id("businessEdit");
        public static final By btnOpenFile = By.xpath("//*[@id=\"editCompanyForm\"]/div[7]/div[2]/div/label/a");
        public static final By btnCancel = By.xpath("//*[@id=\"editCompanyModal\"]/div/div/div[3]/button[1]");
        public static final By btnSave = By.xpath("//*[@id=\"editCompanyModal\"]/div/div/div[3]/button[2]");
        //noti
        public static final By notiCompanyName = By.id("nameCompanyEditNoti");
        public static final By notiPhoneAcc = By.id("phoneCompanyEditNoti");
        public static final By notiBusinessLicense = By.id("businessLicenseEditNoti");
        //thông báo đẩy
        public static final By alertPopup = By.cssSelector("span[data-notify='message']");
    }
    public static final By alertDeletePopup = By.xpath("//*[@id=\"delconfigCompanyModal\"]/div/div/div[2]/h6");
    public static final By btnDel = By.xpath("//*[@id=\"company_table\"]/tbody/tr/td[2]/img[3]");
    public static final By btnConfirmDel = By.cssSelector(".btn.btn-save[onclick='processDeleteCompany()']");
    public static final By alertPopup = By.cssSelector("span[data-notify='message']");

    private String filePath = "dataTestvTracking.xlsx"; // Anhntl52 cập nhật filepath tương đối
    private String sheetName = "MngCompany";

    public String getCompanyName(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"companyName", index);
    }
    public String getParentName(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"parentName", index);
    }
    public String getLicense(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"license", index);
    }
    public String getRepresentative(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"representative", index);
    }
    public String getEmail(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"email", index);
    }
    public String getPhoneCompany(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"phoneCompany", index);
    }
    public String getWebsite(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"website", index);
    }
    public String getLocation(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"location", index);
    }
    public String getOperatingArea(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"operatingArea", index);
    }
    public String getUserName(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"userName", index);
    }
    public String getPhoneAcc(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"phoneAcc", index);
    }
    public String getPassword(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"password", index);
    }
    public String getRepassword(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"repassword", index);
    }
    public String getBusinessType(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"businessType", index);
    }
    public String getBusiness(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"business", index);
    }

    //Chức năng search
    public void search(String filter, String searchText) {
        String xpathExpression = "//ul[@class='dropdown-menu']/li[contains(@onclick, '" + filter + "')]/a";
        webUIHelpers.clickElementAndSelect(comboboxSearch, By.xpath(xpathExpression));
        webUIHelpers.setText(tbSearch, searchText);
        webUIHelpers.clickElement(btnSearch);
        webUIHelpers.sleep(3000);
    }

    public void searchWithoutValue() {
        search("Tất cả", "abcxyz123");
        String actualTextTotal = webUIHelpers.getText(textTotalCompany);
        String actualTextInTable = webUIHelpers.getText(textInTable);
        String expectText = "Không có dữ liệu công ty";
//        ExtentTest test = extent.createTest("Verify search without value");
//        if (expectText.equals(actualTextInTable) && actualTextTotal.equals("Tổng cộng: 0 công ty")){
//            test.log(Status.PASS, "Current text: " + actualTextInTable);
//        }
//        else {
//            test.log(Status.FAIL, "Current text: " + actualTextInTable);
//        }
        Assert.assertEquals(actualTextTotal, "Tổng cộng: 0 công ty");
        Assert.assertEquals(actualTextInTable, expectText);
    }

    public void searchWithFilter(String filterName, String expect, By listByFilter) {
        String expectText = expect.toLowerCase();
        search(filterName, expectText);
        for (WebElement element : driver.findElements(listByFilter)) {
            String actualText = element.getText().trim().toLowerCase();
            Assert.assertTrue(actualText.contains(expectText), "Element text doesn't contain '" + expectText + "'");
        }
    }

    public void searchByCompanyName() {
        searchWithFilter("Tên công ty", "test", infoCompanyName);
    }

    public void searchByCompanyParent() {
        searchWithFilter("Công ty mẹ", "Công ty test 21/11", infoCompanyParent);
    }

    //Thêm
    public void fillInfoCompany(
            String companyName,
            String parentName,
            String license,
            String representative,
            String email,
            String phoneCompany,
            String website,
            String location,
            String operatingArea,
            String userName,
            String phoneAcc,
            String password,
            String repassword,
//            String foundationDate,
            String businessType,
            String business
    ) {
        webUIHelpers.sleep(3000);
        webUIHelpers.setText(AddCompanyLocators.tbCompanyName, companyName);
        String xpathExpression = "//a[contains(@id, 'jstreeCompanyAdd') and contains(text(), '" + parentName + "')]";
        webUIHelpers.clickElementAndSelect(AddCompanyLocators.treeParentCompany, By.xpath(xpathExpression));
        webUIHelpers.clickElement(By.xpath("//*[@id=\"addCompanyModal\"]/div/div/div[1]/h5"));
        webUIHelpers.setText(AddCompanyLocators.tbLicense, license);
        webUIHelpers.setText(AddCompanyLocators.tbRepresentative, representative);
        webUIHelpers.setText(AddCompanyLocators.tbEmail, email);
        webUIHelpers.setText(AddCompanyLocators.tbPhoneCompany, phoneCompany);
        webUIHelpers.setText(AddCompanyLocators.tbWebsite, website);
        webUIHelpers.setText(AddCompanyLocators.tbLocation, location);
        String xpathLocation = "//div[@id='operatingAreaOptionAdd']//div[@id='bussinessArea' and text()='" + operatingArea + "']";
        webUIHelpers.clickElementAndSelect(AddCompanyLocators.dropdownOperatingArea, By.xpath(xpathLocation));
        webUIHelpers.setText(AddCompanyLocators.tbUsername, userName);
        webUIHelpers.setText(AddCompanyLocators.tbPhoneAccount, phoneAcc);
        webUIHelpers.setText(AddCompanyLocators.tbPassword, password);
        webUIHelpers.setText(AddCompanyLocators.tbRePassword, repassword);
        //ngày thành lập
        String xpathDate = "//td[@data-day=\"01/09/2024\"]";
        webUIHelpers.selectDate(AddCompanyLocators.datepickerFoundationDate, By.xpath(xpathDate));
        webUIHelpers.selectElement(AddCompanyLocators.selectBusinessType, businessType);
        webUIHelpers.selectElement(AddCompanyLocators.selectBusiness, business);
    }

    public void createCompany(
            String companyName,
            String parentName,
            String license,
            String representative,
            String email,
            String phoneCompany,
            String website,
            String location,
            String operatingArea,
            String userName,
            String phoneAcc,
            String password,
            String repassword,
//            String foundationDate,
            String businessType,
            String business
    ) {
        webUIHelpers.clickElement(btnAdd);
        fillInfoCompany(companyName, parentName, license, representative, email, phoneCompany, website, location, operatingArea, userName, phoneAcc, password, repassword, businessType, business);
        webUIHelpers.clickElement(AddCompanyLocators.btnSave);
    }
    public void createCompanyDefault() {
        createCompany(
                "Công ty test 25/3",
                "AM GIám sát",
                "Giấy tờ 123 test",
                "Lan Anh",
                "anhntl1112@gmail.com",
                "0912716223",
                "congty.com.vn",
                "285 Cách Mạng Tháng 8",
                "Hà Nội",
                "anhntl263",
                "0964002457",
                "123456aA@",
                "123456aA@",
                "Khác",
                "Tàu biển"
        );
        webUIHelpers.sleep(3000);
    }
    public void createWithoutUsername(){
        createCompany(
                this.getCompanyName(1),
                this.getParentName(1),
                this.getLicense(1),
                this.getRepresentative(1),
                this.getEmail(1),
                this.getPhoneCompany(1),
                this.getWebsite(1),
                this.getLocation(1),
                this.getOperatingArea(1),
                this.getUserName(1),
                this.getPhoneAcc(1),
                this.getPassword(1),
                this.getRepassword(1),
                this.getBusinessType(1),
                this.getBusiness(1)
        );
        String notiActual = webUIHelpers.getText(AddCompanyLocators.notiUsername);
        String expectText = "Trường này là trường bắt buộc";
        ExtentTest test = extent.createTest("Verify create without username");
        if (expectText.equals(notiActual)){
            test.log(Status.PASS, "Current noti: " + notiActual);
        }
        else {
            test.log(Status.FAIL, "Current noti: " + notiActual);
        }
        Assert.assertEquals(notiActual, expectText);
    }
    public void createWithoutRequireField(){
        webUIHelpers.clickElement(btnAdd);
        webUIHelpers.sleep(3000);
        webUIHelpers.clickElement(AddCompanyLocators.btnSave);
        String notiActual = webUIHelpers.getText(AddCompanyLocators.notiCompanyName);
//        String expectText = "Trường này là trường bắt buộc";
//        ExtentTest test = extent.createTest("Verify creater without required field");
//        if (expectText.equals(notiActual)){
//            test.log(Status.PASS, "Current noti: " + notiActual);
//        }
//        else {
//            test.log(Status.FAIL, "Current noti: " + notiActual);
//        }
        Assert.assertEquals(notiActual, "Trường này là trường bắt buộc");
    }

    public void createWithDuplicateEmail(){
        createCompany(
                this.getCompanyName(2),
                this.getParentName(2),
                this.getLicense(2),
                this.getRepresentative(2),
                this.getEmail(2),
                this.getPhoneCompany(2),
                this.getWebsite(2),
                this.getLocation(2),
                this.getOperatingArea(2),
                this.getUserName(2),
                this.getPhoneAcc(2),
                this.getPassword(2),
                this.getRepassword(2),
                this.getBusinessType(2),
                this.getBusiness(2)
        );
        String alertActual = webUIHelpers.getTextInAlert(alertPopup);
        String expectText = "Email đã được sử dụng để đăng ký";
//        ExtentTest test = extent.createTest("Verify create with duplicate email");
//        if (expectText.equals(alertActual)){
//            test.log(Status.PASS, "Current noti: " + alertActual);
//        }
//        else {
//            test.log(Status.FAIL, "Current noti: " + alertActual);
//        }
        Assert.assertEquals(alertActual, expectText);
    }
    public void createWithDuplicateUsername(){
        createCompany(
                this.getCompanyName(3),
                this.getParentName(3),
                this.getLicense(3),
                this.getRepresentative(3),
                this.getEmail(3),
                this.getPhoneCompany(3),
                this.getWebsite(3),
                this.getLocation(3),
                this.getOperatingArea(3),
                this.getUserName(3),
                this.getPhoneAcc(3),
                this.getPassword(3),
                this.getRepassword(3),
                this.getBusinessType(3),
                this.getBusiness(3)
        );
        String alertActual = webUIHelpers.getTextInAlert(AddCompanyLocators.alertPopup);
        String expectText = "Tài khoản chính đã được sử dụng. Hãy nhập một tên khác";
//        ExtentTest test = extent.createTest("Verify create with duplicate username");
//        if (expectText.equals(alertActual)){
//            test.log(Status.PASS, "Current noti: " + alertActual);
//        }
//        else {
//            test.log(Status.FAIL, "Current noti: " + alertActual);
//        }
        Assert.assertEquals(alertActual, expectText);
    }
    public void createWithDuplicatePhoneAcc(){
        createCompany(
                this.getCompanyName(4),
                this.getParentName(4),
                this.getLicense(4),
                this.getRepresentative(4),
                this.getEmail(4),
                this.getPhoneCompany(4),
                this.getWebsite(4),
                this.getLocation(4),
                this.getOperatingArea(4),
                this.getUserName(4),
                this.getPhoneAcc(4),
                this.getPassword(4),
                this.getRepassword(4),
                this.getBusinessType(4),
                this.getBusiness(4)
        );
        String alertActual = webUIHelpers.getTextInAlert(AddCompanyLocators.alertPopup);
        String expectText = "Số điện thoại đã được sử dụng để đăng ký";
//        ExtentTest test = extent.createTest("Verify create with duplicate username");
//        if (expectText.equals(alertActual)){
//            test.log(Status.PASS, "Current noti: " + alertActual);
//        }
//        else {
//            test.log(Status.FAIL, "Current noti: " + alertActual);
//        }
        Assert.assertEquals(alertActual, expectText);
    }

//    public void createWhenClickCancel() {
//        webUIHelpers.clickElement(btnAdd);
//        fillInfoCompany(
//                "Công ty test 25/3",
//                "AM GIám sát",
//                "Giấy tờ 123 test",
//                "Lan Anh",
//                "anhntl1112@gmail.com",
//                "0912716223",
//                "congty.com.vn",
//                "285 Cách Mạng Tháng 8",
//                "Hà Nội",
//                "anhntl263",
//                "0964002456",
//                "123456aA@",
//                "123456aA@",
//                "Khác",
//                "Tàu biển"
//        );
//        webUIHelpers.clickElement(AddCompanyLocators.btnCancel);
//    }
    public void createSuccessfully() {
        createCompany(
                this.getCompanyName(5),
                this.getParentName(5),
                this.getLicense(5),
                this.getRepresentative(5),
                this.getEmail(5),
                this.getPhoneCompany(5),
                this.getWebsite(5),
                this.getLocation(5),
                this.getOperatingArea(5),
                this.getUserName(5),
                this.getPhoneAcc(5),
                this.getPassword(5),
                this.getRepassword(5),
                this.getBusinessType(5),
                this.getBusiness(5)
        );
        webUIHelpers.sleep(5000);
        search("Tên công ty", "Công ty test 25/3");
        String company = webUIHelpers.getText(infoCompanyName);
        Assert.assertEquals(company, "Công ty test 25/3");
//        webUIHelpers.clearText(tbSearch);
//        deleteCompany("0964002457");
    }
    public void loginAdminOrg() {
        logoutPage.logout();
        //login tài khoản admin công ty của cty vừa tạo
        loginPage.loginFirstTime(loginPage.getUserName(6),loginPage.getPassword(6));
        webUIHelpers.clickElement(By.xpath("//*[@id=\"theme16\"]/div[1]/button"));
    }

    //Sửa
    public void editInfoCompany(
            String companyName,
            String parentName,
            String license,
            String representative,
            String email,
            String phoneCompany,
            String website,
            String location,
            String operatingArea,
            String phoneAcc,
//            String foundationDate,
            String businessType,
            String business
    ) {
        webUIHelpers.sleep(3000);
        webUIHelpers.clearAndSetText(EditCompanyLocators.tbCompanyName, companyName);
        String xpathExpression = "//a[contains(@id, 'jstreeCompanyEdit') and contains(text(), '" + parentName + "')]";
        webUIHelpers.clickElementAndSelect(EditCompanyLocators.treeParentCompany, By.xpath(xpathExpression));
        webUIHelpers.clickElement(By.xpath("//*[@id=\"editCompanyModal\"]/div/div/div[1]/h5"));//click vào vị trí bất kì trong popup để đóng dropdown tree
        webUIHelpers.clearAndSetText(EditCompanyLocators.tbLicense, license);
        webUIHelpers.clearAndSetText(EditCompanyLocators.tbRepresentative, representative);
        webUIHelpers.clearAndSetText(EditCompanyLocators.tbEmail, email);
        webUIHelpers.clearAndSetText(EditCompanyLocators.tbPhoneCompany, phoneCompany);
        webUIHelpers.clearAndSetText(EditCompanyLocators.tbWebsite, website);
        webUIHelpers.clearAndSetText(EditCompanyLocators.tbLocation, location);
        String xpathLocation = "//div[@id='operatingAreaOptionEdit']//div[@id='bussinessArea' and text()='" + operatingArea + "']";
        webUIHelpers.clickElementAndSelect(EditCompanyLocators.dropdownOperatingArea, By.xpath(xpathLocation));
        webUIHelpers.clearAndSetText(EditCompanyLocators.tbPhoneAccount, phoneAcc);
        //ngày thành lập
        String xpathDate = "//td[@data-day=\"01/09/2024\"]";
        webUIHelpers.selectDate(EditCompanyLocators.datepickerFoundationDate, By.xpath(xpathDate));
        webUIHelpers.selectElement(EditCompanyLocators.selectBusinessType, businessType);
        webUIHelpers.selectElement(EditCompanyLocators.selectBusiness, business);
    }
    public void editCompany(
            String companyName,
            String parentName,
            String license,
            String representative,
            String email,
            String phoneCompany,
            String website,
            String location,
            String operatingArea,
            String phoneAcc,
//            String foundationDate,
            String businessType,
            String business
    ) {
//        createCompanyDefault();
        search("Tất cả", "Công ty test 25/3");
        webUIHelpers.clickElement(By.xpath("//*[@id=\"company_table\"]/tbody/tr/td[2]/img[2]"));//click button edit
        editInfoCompany(companyName, parentName, license, representative, email, phoneCompany, website, location, operatingArea, phoneAcc, businessType, business);
        webUIHelpers.sleep(3000);
        webUIHelpers.clickElement(EditCompanyLocators.btnSave);
    }
    public void editWhenClickCancel() {
//        createCompanyDefault();
        search("Tất cả", "Công ty test 25/3");
        webUIHelpers.clickElement(By.xpath("//*[@id=\"company_table\"]/tbody/tr/td[2]/img[2]"));//click button edit
        editInfoCompany(
                "Công ty test 27/3",
                "Công ty test 21/11",
                "Giấy tờ 456 test",
                "Nguyễn Anh",
                "anhntl112000@gmail.com",
                "0914567223",
                "congty123.com.vn",
                "300 Lạc Long Quân",
                "An Giang",
                "0961234456",
                "Công ty TNHH",
                "Khác"
        );
        webUIHelpers.clickElement(EditCompanyLocators.btnCancel);
        search("Tất cả", "Công ty test 25/3");
    }
    public void editWithoutRequireField() {
        editCompany(
                this.getCompanyName(6),
                this.getParentName(6),
                this.getLicense(6),
                this.getRepresentative(6),
                this.getEmail(6),
                this.getPhoneCompany(6),
                this.getWebsite(6),
                this.getLocation(6),
                this.getOperatingArea(6),
                this.getPhoneAcc(6),
                this.getBusinessType(6),
                this.getBusiness(6)
        );
        String notiActual = webUIHelpers.getText(EditCompanyLocators.notiCompanyName);
//        String company = webUIHelpers.getText(infoCompanyName);
//        ExtentTest test = extent.createTest("Verify create with duplicate username");
//        if (company.equals("Công ty test 25/3")){
//            test.log(Status.PASS, "Create successfully: " + company);
//        }
//        else {
//            test.log(Status.FAIL, "Create unsuccessfully");
//        }
        Assert.assertEquals(notiActual, "Trường này là trường bắt buộc");
//        webUIHelpers.clickElement(EditCompanyLocators.btnCancel);
//        webUIHelpers.clearText(tbSearch);
//        deleteCompany("Công ty test 25/3");
    }
    public void editWhenDuplicateEmail() {
        editCompany(
                this.getCompanyName(7),
                this.getParentName(7),
                this.getLicense(7),
                this.getRepresentative(7),
                this.getEmail(7),
                this.getPhoneCompany(7),
                this.getWebsite(7),
                this.getLocation(7),
                this.getOperatingArea(7),
                this.getPhoneAcc(7),
                this.getBusinessType(7),
                this.getBusiness(7)
        );
        String alertActual = webUIHelpers.getTextInAlert(alertPopup);
        Assert.assertEquals(alertActual, "Email đã được sử dụng để đăng ký");
//        webUIHelpers.clickElement(EditCompanyLocators.btnCancel);
//        webUIHelpers.clearText(tbSearch);
//        deleteCompany("Công ty test 25/3");
    }
    public void editWhenDuplicatePhoneAcc() {
        editCompany(
                this.getCompanyName(8),
                this.getParentName(8),
                this.getLicense(8),
                this.getRepresentative(8),
                this.getEmail(8),
                this.getPhoneCompany(8),
                this.getWebsite(8),
                this.getLocation(8),
                this.getOperatingArea(8),
                this.getPhoneAcc(8),
                this.getBusinessType(8),
                this.getBusiness(8)
        );
        String alertActual = webUIHelpers.getTextInAlert(alertPopup);
        Assert.assertEquals(alertActual, "Số điện thoại đã được sử dụng để đăng ký");
//        webUIHelpers.clickElement(EditCompanyLocators.btnCancel);
//        webUIHelpers.clearText(tbSearch);
//        deleteCompany("Công ty test 25/3");
    }
    public void editSuccessfully() {
        editCompany(
                this.getCompanyName(9),
                this.getParentName(9),
                this.getLicense(9),
                this.getRepresentative(9),
                this.getEmail(9),
                this.getPhoneCompany(9),
                this.getWebsite(9),
                this.getLocation(9),
                this.getOperatingArea(9),
                this.getPhoneAcc(9),
                this.getBusinessType(9),
                this.getBusiness(9)
        );
        String alertActual = webUIHelpers.getTextInAlert(alertPopup);
        Assert.assertEquals(alertActual, "Cập nhật thành công");
//        webUIHelpers.clearText(tbSearch);
//        deleteCompany("Công ty test 27/3");
    }
    public void loginAfterEditPhoneAcc() {
        //đăng xuât
        logoutPage.logoutSuccessfully();
        //login tài khoản admin công ty của cty vừa sửa bằng số điện thoại
        loginPage.loginFirstTime(loginPage.getUserName(7),loginPage.getPassword(7));
//        webUIHelpers.clickElement(By.xpath("//*[@id=\"theme16\"]/div[1]/button"));
        WebElement tbOTP = driver.findElement(By.id("optPhoneLogin"));
        Assert.assertTrue(tbOTP.isDisplayed());
        //xóa thông tin đăng nhập
//        webUIHelpers.clearText(loginPage.getInputUsername());
//        webUIHelpers.clearText(LoginPage.inputPassword);
        //đăng nhập lại tk giangptt để xóa cty
//        managePage.navigateToManagePage();
//        managePage.navigateToCompany();
//        deleteCompany("0961234456");
    }

    //Delete
    public void deleteCompany(String deletedCompany){
        search("Tên công ty", deletedCompany);
        webUIHelpers.clickElement(btnDel);//click button xóa
        webUIHelpers.sleep(3000);
        webUIHelpers.clickElement(btnConfirmDel);
        webUIHelpers.sleep(3000);
    }
    public void deleteCompanySuccessfully(){
        //tạo mới -> tìm -> xóa
//        createCompanyDefault();
        deleteCompany("Công ty test 27/3");
        //kiểm tra sau khi xóa
        String actualAlert = webUIHelpers.getTextInAlert(alertPopup);
        Assert.assertEquals(actualAlert, "Xóa thành công");
//        String actualTextTotal = webUIHelpers.getText(textTotalCompany);
//        Assert.assertEquals(actualTextTotal, "Tổng cộng: 0 công ty");
    }
    public void deleteCompanyUnsuccessfully(String companyName){
        deleteCompany(companyName);
        //kiểm tra sau khi xóa không thành công
        String actualAlert = webUIHelpers.getTextInAlert(alertDeletePopup);
        Assert.assertEquals(actualAlert, "Bạn chỉ có thể xóa công ty khi trong công ty không có thiết bị hoặc xe.");
    }
    public void deleteCompanyWithDevice(){
        deleteCompanyUnsuccessfully("Công ty có thiết bị");
    }
    public void deleteCompanyWithVehicle(){
        deleteCompanyUnsuccessfully("Công ty có xe");
    }
    public void deleteCompanyWithDeviceAndCar(){
        deleteCompanyUnsuccessfully("Công ty có xe với thiết bị");
    }
}
