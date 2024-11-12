package pages.ManagementPages;

import bases.WebUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class RolePage {
    public RolePage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
    }
    private final WebDriver driver;
    private WebUIHelpers webUIHelpers;

    //Các element trong search
    private By tbSearch = By.id("textSearchRole");
    private By dropdownCompany = By.id("nameOrgRoleTab");
    private By btnSearch = By.cssSelector("[onclick=\"searchRoleList()\"]");
    private By textTotalRole = By.id("totalRole");

    //Các button chức năng
    private By btnAdd = By.cssSelector("[onclick=\"resetForm('CompanyRoleAdd')\"]");
    private By btnExport = By.cssSelector(".btn.btn-export[onclick=\"exportRole(0)\"]");
    private By btnEdit = By.xpath("//*[@id=\"role_table\"]/tbody/tr[1]/td[2]/img[2]");
    private By btnDel = By.xpath("//*[@id=\"role_table\"]/tbody/tr[1]/td[2]/img[3]");
    private By btnConfirmDel = By.cssSelector("[onclick='processDeleteRole()']");

    //Các giá trị trong bảng danh sách
    private By textInTable = By.className("no_table_data");
    private By alertPopup = By.cssSelector("span[data-notify='message']");

    //element trong popup Thêm vai trò
    public class AddRoleLocators {
        public static final By tbName = By.id("nameRoleAdd");
        public static final By treeCompany = By.id("nameOrgRoleAdd");
        public static final By btnCancel = By.cssSelector("#addRoleModal .btn.btn-cancle");
        public static final By checkboxAll = By.id("checkAllRoleAdd");
        public static final By btnSave = By.cssSelector("[onclick=\"processAddRole()\"]");
        //noti
        public static final By notiName = By.id("nameRoleAddNoti");
        public static final By notiCompany = By.id("idOrgRoleAddNoti");
    }
    public class EditRoleLocators {
        public static final By tbName = By.id("nameRoleEdit");
        public static final By treeCompany = By.id("nameOrgRoleEdit");
        public static final By btnCancel = By.cssSelector("#editRoleModal .btn.btn-cancle");
        public static final By checkboxAll = By.id("checkAllRoleEdit");
        public static final By btnSave = By.cssSelector("[onclick=\"processEditRole()\"]");
        //noti
        public static final By notiName = By.id("nameRoleEditNoti");
    }

    private String filePath = "dataTestvTracking.xlsx"; // Anhntl52 cập nhật filepath tương đối
    private String sheetName = "MngRole";

    //Search
    public void search(String name, String company) {
        webUIHelpers.setText(tbSearch, name);
        if (!company.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeRoleTab') and contains(text(), '" + company + "')]";
            webUIHelpers.clickElementAndSelect(dropdownCompany, By.xpath(xpathExpression));
        }
        webUIHelpers.clickElement(btnSearch);
        webUIHelpers.sleep(5000);
    }
    public void verifySearchWithoutValue() {
        String actualTextTotal = webUIHelpers.getText(textTotalRole);
        String actualTextInTable = webUIHelpers.getText(textInTable);
        String expectTextTotal = "Tổng cộng: 0 vai trò";
        String expectTextInTable = "Không có dữ liệu vai trò";
        Assert.assertEquals(actualTextTotal, expectTextTotal);
        Assert.assertEquals(actualTextInTable, expectTextInTable);
    }
    public void verifySearchByText(String expect) {
        String expectText = expect.toLowerCase();
        List<WebElement> rows = driver.findElements(By.xpath("//div[@id='roleTab']//table[@id='role_table']//tbody//tr"));
        for (WebElement row : rows) {
            WebElement tdElement = row.findElement(By.xpath("./td[4]"));
            String actualText = tdElement.getText().toLowerCase();
            Assert.assertTrue(actualText.contains(expectText));
        }
    }
    public void verifySearchByCompany(String expectCompany) {
        String expectText = expectCompany.toLowerCase();
        List<WebElement> rows = driver.findElements(By.xpath("//div[@id='roleTab']//table[@id='role_table']//tbody//tr"));
        for (WebElement row : rows) {
            WebElement fourthTd = row.findElement(By.xpath("./td[4]"));
            String roleText = fourthTd.getText().toLowerCase();

            WebElement tdElement = row.findElement(By.xpath("./td[5]"));
            String actualText = tdElement.getText().toLowerCase();

            if (roleText.equals("default user")) {
                Assert.assertTrue(actualText.isEmpty() || actualText.contains(expectText),
                        "For 'Default User', company field is not empty or does not contain expected text for row: " + row.getText());
            } else {
                Assert.assertTrue(actualText.contains(expectText)," does not match for row: " + row.getText());
            }
        }
    }

    //create
    public void fillInfoRole(
            String name,
            String company,
            List<String> roles
    ) {
        webUIHelpers.sleep(3000);
        webUIHelpers.setText(AddRoleLocators.tbName, name);
        if (!company.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeRoleAdd') and contains(text(), '" + company + "')]";
            webUIHelpers.clickElementAndSelect(AddRoleLocators.treeCompany, By.xpath(xpathExpression));
        }
        webUIHelpers.clickElement(By.xpath("//*[@id=\"addRoleModal\"]/div/div/div[1]/h5"));
        for (String role : roles) {
            if (!role.isEmpty()) {
                String xpathRole = "//td[text()='" + role + "']/following-sibling::td/input[@type='checkbox']";
                webUIHelpers.clickElement(By.xpath(xpathRole));
            }
        }
    }
    public void createRole(
            String name,
            String company,
            List<String> roles
    ){
        webUIHelpers.clickElement(btnAdd);
        webUIHelpers.sleep(3000);
        fillInfoRole(name, company, roles);
        webUIHelpers.clickElement(AddRoleLocators.btnSave);
        webUIHelpers.sleep(3000);
    }
    public void verifyCreateWithoutName(){
        String notiActual = webUIHelpers.getText(AddRoleLocators.notiName);
        String expectText = "Vui lòng nhập tên vai trò";
        Assert.assertEquals(notiActual, expectText);
    }
    public void verifyCreateWithoutCompany(){
        String notiActual = webUIHelpers.getText(AddRoleLocators.notiCompany);
        String expectText = "Vui lòng chọn công ty";
        Assert.assertEquals(notiActual, expectText);
    }
    public void verifyCreateWithDuplicateName(){
        String actualAlert = webUIHelpers.getTextInAlert(alertPopup);
        String expectAlert = "Tên vai trò này đã tồn tại trong danh sách";
        Assert.assertEquals(actualAlert, expectAlert);
    }
    public void verifyCreateSuccessfully(){
        String actualAlert = webUIHelpers.getTextInAlert(alertPopup);
        String expectAlert = "Tạo thành công";
        Assert.assertEquals(actualAlert, expectAlert);
    }

    //edit
    public void editInfoRole(
            String name,
            List<String> untickRoles,
            List<String> roles
    ) {
        webUIHelpers.sleep(3000);
        webUIHelpers.clearAndSetText(EditRoleLocators.tbName, name);
        for (String untickRole : untickRoles) {
            String xpathRole = "//*[@id='CompanyRoleEdit']//td[text()='" + untickRole + "']/following-sibling::td/input[@type='checkbox']";
            webUIHelpers.clickElement(By.xpath(xpathRole));
        }
        for (String role : roles) {
            if (!role.isEmpty()) {
                String xpathRole = "//*[@id='CompanyRoleEdit']//td[text()='" + role + "']/following-sibling::td/input[@type='checkbox']";
                webUIHelpers.clickElement(By.xpath(xpathRole));
            }
        }
    }
    public void editRole(
            String name,
            List<String> untickRoles,
            List<String> roles
    ){
        webUIHelpers.clickElement(btnEdit);
        editInfoRole(name, untickRoles, roles);
        webUIHelpers.clickElement(EditRoleLocators.btnSave);
    }
    public void verifyEditWithoutName(){
        String notiActual = webUIHelpers.getText(EditRoleLocators.notiName);
        String expectText = "Vui lòng nhập tên vai trò";
        Assert.assertEquals(notiActual, expectText);
    }
    public void verifyEditSuccessfully(){
        String actualAlert = webUIHelpers.getTextInAlert(alertPopup);
        String expectAlert = "Cập nhật thành công";
        Assert.assertEquals(actualAlert, expectAlert);
    }
    //delete
    public void deleteRole(String deletedDevice){
        search(deletedDevice, "");
        webUIHelpers.clickElement(btnDel);
        webUIHelpers.sleep(3000);
        webUIHelpers.clickElement(btnConfirmDel);
    }
    public void verifyDeleteUnusedRole(){
        webUIHelpers.verifyAlert(webUIHelpers.getTextInAlert(alertPopup), "Xóa thành công");
    }
    public void verifyDeleteUsedRole(){
        webUIHelpers.verifyAlert(webUIHelpers.getTextInAlert(alertPopup), "Xóa không thành công");
    }



}
