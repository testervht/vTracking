package pages.ManagementPages;

import bases.WebUIHelpers;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class UserPage {

    private WebDriver driver;
    private WebUIHelpers webUIHelpers;

    //cac element loc du lieu
    private By congTyRoot = By.id("nameOrgUserTab");

    //Các element trong search
    private By tbSearch = By.id("textSearchUser");
    private By btnSearch = By.cssSelector("[onclick=\"searchUserTabList()\"]");

    private By btnAdd = By.cssSelector(".btn.btn-add[onclick=\"handleAdduser()\"]");
    private By btnAssign = By.xpath("//*[@id=\"user_table\"]/tbody/tr/td[2]/img[3]");

    //cac truong trong popup Gan
    private By searchboxVehicle = By.id("textSearchAssignedVehicle");
    private By btnSearchVehicle = By.cssSelector("[onclick=\"searchVehicleListAssigned()\"]");

    // cac truong trong Popup Them
    public By title = By.cssSelector("div[id='addUserModal'] h5[class='modal-title']");
    public static By textBoxTenTaiKhoan_add = By.id("nameUserAdd");
    public static By alertTenTaiKhoan_add = By.id("nameUserAddNoti");
    public static By textBoxMatKhau_add = By.id("passwordUserAdd");
    public static By alertMatKhau_add = By.id("passwordUserAddNoti");
    public static By textBoxXacNhanMatKhau_add = By.id("rePasswordUserAdd");
    public static By alertXacNhanMatKhau_add = By.id("rePasswordUserAddNoti");
    public static By droplistCongTyRoot_add = By.xpath("//input[@id='nameOrgUserAdd']");
    public static By droplistCongTy_add = By.id("jstreeUserAdd_5536830d-d0c8-41c3-87e2-1811f06dd637_anchor");
    public static By textBoxHoVaTen_add = By.id("fullnameUserAdd");
    public static By alertHoVaTen_add = By.id("fullnameUserAddNoti");
    public static By textBoxEmail_add = By.id("emailUserAdd");
    public static By textBoxSDT_add = By.id("phoneUserAdd");
    public static By alertSDT_add = By.id("phoneUserAddNoti");
    public static By btnSave_add = By.xpath("//button[@id='uta_save-btn']");

    //private By btnEdit = By.xpath("//*[@id=\"user_table\"]/tbody/tr/td[2]/img[2]");
    // Tìm phần tử bằng CSS Selector
    //WebElement editIcon = driver.findElement(By.cssSelector("img.icon-action[onclick*=\"setEditUser('9f7d8d15-e466-4ae1-be65-72678db36081')\"]"));

    private By btnEdit = By.cssSelector("img.icon-action[onclick*='setEditUser']");

    // Cac truong trong Man Sua
    public static By textBoxHoVaTen_edit = By.id("fullnameUserEdit");
    public static By alertBoxHoVaTen_edit = By.id("fullnameUserEditNoti");
    public static By textBoxSDT_edit = By.id("phoneUserEdit");
    public static By alertSDT_edit = By.id("phoneUserEditNoti");
    public static By textBoxEmail_edit = By.id("emailUserEdit");
    public static By alertEmail_edit = By.id("emailUserEditNoti");


    //ele in edit popup
    private By ddRole = By.id("roleUserEdit");
    private By btnSave_edit = By.cssSelector("[onclick=\"validateEditUser()\"]");
    //*[@id="editUserModal"]/div/div/div[3]/button[3]
    //Ket qua search
    private By textTotalResult = By.id("totalUser");
    private By textNoData = By.className("no_table_data");
    private By notiPopup = By.cssSelector("span[data-notify='message']");

    public UserPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
    }
    public void searchUser(String textSearch){
        webUIHelpers.setText(tbSearch, textSearch);
        webUIHelpers.sleep(1000);
        webUIHelpers.clickElement(btnSearch);
        webUIHelpers.sleep(3000);
    }
    public void verifyText(String expectTotal, String expectNoData){
        String actualText = webUIHelpers.getText(textTotalResult);
        String actualNoData = webUIHelpers.getText(textNoData);
        Assert.assertEquals(actualText, expectTotal);
        Assert.assertEquals(actualNoData, expectNoData);
    }
    //verify alert thong bao
    public void verifyAlert(String expect, By element){
        String actualAlert = webUIHelpers.getText(element);
        Assert.assertEquals(actualAlert, expect);
    }
    public void verifyNoti(String expect){
        // Thiết lập thời gian chờ là 20 giây
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(notiPopup));
        String actualText = webUIHelpers.getText(notiPopup);
        Assert.assertEquals(actualText, expect);
    }


    // Tinh nang Search
    public void searchOnlyCompany(By congTyLocator){
        webUIHelpers.clickElementAndSelect(congTyRoot, congTyLocator);
        webUIHelpers.clickElement(btnSearch);
        webUIHelpers.sleep(1000);
        //By tenCongTy = By.id("jstreeUserTab_ffe633ce-520e-4c99-a44a-3f8ec6cacee9_anchor");
        //WebElement tenCongTy = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[4]/div[1]/div[1]/div/div/div/ul/li/ul/li[4]/a"));

        WebElement tenCongTy = driver.findElement(By.id("jstreeUserTab_ffe633ce-520e-4c99-a44a-3f8ec6cacee9_anchor"));
        String tenCongTyStr = tenCongTy.getAttribute("innerText");

        System.out.println("Ten cong ty:" + tenCongTyStr);

        List<WebElement> elements = driver.findElements(By.className("userCompany"));
        for (int i = 1; i < elements.size(); i++){
            WebElement element = elements.get(i);
            String actualText = element.getText();
            if (actualText.equals("Công ty test 21/11/2024")){
                System.out.println("Tim kiem thanh cong");
            }else{
                System.out.println("Tim kiem that bai");
            }
        }
    }
    public void searchOnlyRole(String roleName){
        WebElement quyenRoot = driver.findElement(By.id("search-user-role"));
        Select dropdown = new Select(quyenRoot);
        quyenRoot.click();
        dropdown.selectByVisibleText(roleName);
        webUIHelpers.clickElement(btnSearch);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<WebElement> elements = driver.findElements(By.className("userRole"));
        for (int i = 1; i < elements.size(); i++){
            WebElement element = elements.get(i);
            String actualText = element.getText();
            Assert.assertEquals(actualText, roleName, "Tim kiem thanh cong");
        }
    }
    public void searchTextBox(String keyword){
        searchUser(keyword);
        WebElement table = driver.findElement(By.id("user_table"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Tim tat ca cac hang trong bang
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        //Duyet qua tung hang
        for (WebElement row : rows) {
            // Tìm tất cả các cột trong hàng
            List<WebElement> columns = row.findElements(By.tagName("td"));

            // Duyệt qua từng cột
            for (WebElement column : columns) {
                // Kiểm tra nếu text trong cột chứa từ khóa
                //System.out.println("Tren dong 147:" + column.getText());
                if(column.getText().equals("")){
                    continue;
                }else {
                    if (column.getText().contains(keyword)) {
                        System.out.println("Tim kiem tuong doi: " + row.getText());
                        break; // Dừng kiểm tra các cột còn lại trong hàng này
//                    }else if(column.getText().equals(keyword)){
//                        System.out.println("Tim kiem tuyet doi: " + row.getText());
//                        break; // Dừng kiểm tra các cột còn lại trong hàng này
                    }
                }
            }
        }
        System.out.println("Keyword not found in any row.");
    }
    public void searchMultiConditions(By congTyLocator, String roleName, String keyword){
        webUIHelpers.clickElementAndSelect(congTyRoot, congTyLocator);
        WebElement quyenRoot = driver.findElement(By.id("search-user-role"));
        Select dropdown = new Select(quyenRoot);
        quyenRoot.click();
        dropdown.selectByVisibleText(roleName);
        searchTextBox(keyword);

    }

    //Tinh nang Them
    public void addUser(String TenTaiKhoan, String MatKhau, String XacNhanMatKhau, String RoleName, By TenCongTy, String HoVaTen, String Email, String SoDienThoai){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(btnAdd));
        addButton.click();
        webUIHelpers.setText(textBoxTenTaiKhoan_add, TenTaiKhoan);
        // Delay giữa các trường
        delay(1000); // Delay 1 giây giữa các trường
        webUIHelpers.setText(textBoxMatKhau_add, MatKhau);
        // Delay giữa các trường
        delay(1000); // Delay 1 giây giữa các trường
        webUIHelpers.setText(textBoxXacNhanMatKhau_add, XacNhanMatKhau);
        // Delay giữa các trường
        delay(1000); // Delay 1 giây giữa các trường
        webUIHelpers.clickElementAndSelect(droplistCongTyRoot_add, TenCongTy);
        // Delay giữa các trường
        delay(1000); // Delay 1 giây giữa các trường
        WebElement quyenRoot = driver.findElement(By.xpath("//select[@id='roleUserAdd']"));
        // Delay giữa các trường
        delay(1000); // Delay 1 giây giữa các trường
        Select dropdown = new Select(quyenRoot);
        quyenRoot.click();
        // Delay giữa các trường
        delay(1000); // Delay 1 giây giữa các trường
        dropdown.selectByVisibleText(RoleName);
        webUIHelpers.setText(textBoxHoVaTen_add, HoVaTen);
        // Delay giữa các trường
        delay(1000); // Delay 1 giây giữa các trường
        webUIHelpers.setText(textBoxEmail_add, Email);
        // Delay giữa các trường
        delay(1000); // Delay 1 giây giữa các trường
        webUIHelpers.setText(textBoxSDT_add, SoDienThoai);
        // Delay giữa các trường
        delay(1000); // Delay 1 giây giữa các trường
        webUIHelpers.clickElement(btnSave_add);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void addUserMissCompulsory(String TenTaiKhoan, String MatKhau, String XacNhanMatKhau, String RoleName, By TenCongTy, String HoVaTen, String Email, String SoDienThoai){
        addUser(TenTaiKhoan, MatKhau, XacNhanMatKhau, RoleName, TenCongTy, HoVaTen, Email, SoDienThoai );
        if(!(TenTaiKhoan.isEmpty()) && !(MatKhau.isEmpty()) && !(XacNhanMatKhau.isEmpty()) && !(HoVaTen.isEmpty()) && !(Email.isEmpty()) && !(SoDienThoai.isEmpty())){
            addUser(TenTaiKhoan, MatKhau, XacNhanMatKhau, RoleName, TenCongTy, HoVaTen, Email, SoDienThoai );
        } else {
            if(TenTaiKhoan.isEmpty()){
                Assert.assertEquals(webUIHelpers.getText(alertTenTaiKhoan_add),"Trường này là trường bắt buộc", "Thêm nguoi dung that bai do thieu truong Ten tai khoan");
            }else if (MatKhau.isEmpty()){
                Assert.assertEquals(webUIHelpers.getText(alertMatKhau_add), "Trường này là trường bắt buộc", "Thêm nguoi dung that bai do thieu truong Mat khau");
            }else if (XacNhanMatKhau.isEmpty()){
                Assert.assertEquals(webUIHelpers.getText(alertXacNhanMatKhau_add), "Trường này là trường bắt buộc", "Thêm nguoi dung that bai do thieu truong Mat khau");
            }else if (HoVaTen.isEmpty()){
                Assert.assertEquals(webUIHelpers.getText(alertHoVaTen_add), "Trường này là trường bắt buộc", "Thêm nguoi dung that bai do thieu truong Mat khau");
            } else if (SoDienThoai.isEmpty()) {
                Assert.assertEquals(webUIHelpers.getText(alertSDT_add), "Trường này là trường bắt buộc", "Thêm nguoi dung that bai do thieu truong So dien thoai");
            }
        }
    }

    //Tinh nang Sua
    public void editUser(String oldSDT, String newHoVaTen, String newEmail, String newSoDienThoai){
        searchUser(oldSDT);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(btnEdit));
        editButton.click();
        if(newHoVaTen.isEmpty() && newEmail.isEmpty()){
            webUIHelpers.clearText(textBoxSDT_edit);
            webUIHelpers.setText(textBoxSDT_edit, newSoDienThoai);
            delay(1000); // Delay 1 giây giữa các trường
        } else if (newHoVaTen.isEmpty() && newSoDienThoai.isEmpty()) {
            webUIHelpers.clearText(textBoxEmail_edit);
            webUIHelpers.setText(textBoxEmail_edit, newEmail);
            delay(1000); // Delay 1 giây giữa các trường
        } else if (newEmail.isEmpty() && newSoDienThoai.isEmpty()) {
            webUIHelpers.clearText(textBoxHoVaTen_edit);
            webUIHelpers.setText(textBoxHoVaTen_edit, newHoVaTen);
            delay(1000); // Delay 1 giây giữa các trường
        } else if (newHoVaTen.isEmpty()) {
            webUIHelpers.clearText(textBoxEmail_edit);
            webUIHelpers.setText(textBoxEmail_edit, newEmail);
            delay(1000); // Delay 1 giây giữa các trường
            webUIHelpers.clearText(textBoxSDT_edit);
            webUIHelpers.setText(textBoxSDT_edit, newSoDienThoai);
            delay(1000); // Delay 1 giây giữa các trường
        } else if (newEmail.isEmpty()) {
            webUIHelpers.clearText(textBoxHoVaTen_edit);
            webUIHelpers.setText(textBoxHoVaTen_edit, newHoVaTen);
            delay(1000); // Delay 1 giây giữa các trường
            webUIHelpers.clearText(textBoxSDT_edit);
            webUIHelpers.setText(textBoxSDT_edit, newSoDienThoai);
            delay(1000); // Delay 1 giây giữa các trường
        } else if (newSoDienThoai.isEmpty()) {
            webUIHelpers.clearText(textBoxHoVaTen_edit);
            webUIHelpers.setText(textBoxHoVaTen_edit, newHoVaTen);
            delay(1000); // Delay 1 giây giữa các trường
            webUIHelpers.clearText(textBoxEmail_edit);
            webUIHelpers.setText(textBoxEmail_edit, newEmail);
            delay(1000); // Delay 1 giây giữa các trường
        } else {
            webUIHelpers.clearText(textBoxHoVaTen_edit);
            webUIHelpers.setText(textBoxHoVaTen_edit, newHoVaTen);
            delay(1000); // Delay 1 giây giữa các trường
            webUIHelpers.clearText(textBoxSDT_edit);
            webUIHelpers.setText(textBoxSDT_edit, newSoDienThoai);
            delay(1000); // Delay 1 giây giữa các trường
            webUIHelpers.clearText(textBoxEmail_edit);
            webUIHelpers.setText(textBoxEmail_edit, newEmail);
            delay(1000); // Delay 1 giây giữa các trường
        }

        webUIHelpers.clickElement(btnSave_edit);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void EditUserMissHoVaTen(){
        editUser("0637467843","Hiền", "", "");
        webUIHelpers.clearText(textBoxHoVaTen_edit);
        webUIHelpers.clickElement(btnSave_edit);
        Assert.assertEquals(webUIHelpers.getText(alertBoxHoVaTen_edit), "Trường này là trường bắt buộc", "Sưa nguoi dung that bai do thieu truong Ho Va Ten");
    }
    public void EditUserMissSoDienThoai(){
        editUser("0637467843","", "", "0123453567");
        webUIHelpers.clearText(textBoxSDT_edit);
        webUIHelpers.clickElement(btnSave_edit);
        Assert.assertEquals(webUIHelpers.getText(alertSDT_edit), "Trường này là trường bắt buộc", "Sưa nguoi dung that bai do thieu truong SDT");
    }
    public void EditUserDuplicateEmail(){
        editUser("0637467843","Sua ten", "testauto1@gmail.com", "0312345212");
        webUIHelpers.clickElement(btnSave_edit);
        Assert.assertEquals(webUIHelpers.getText(notiPopup), "Email đang được sử dụng", "Sưa nguoi dung that bai");
    }
    public void EditUserDuplicateSDT(){
        editUser("0637467843","Sua ten", "testauto1@gmail.com", "0312345212");
        webUIHelpers.clickElement(btnSave_edit);
        Assert.assertEquals(webUIHelpers.getText(notiPopup), "Số điện thoại đang được sử dụng", "Sưa nguoi dung that bai");
    }

    //Tinh nang Xoa

    //xoa tk chinh minh + admin cong ty
    public void DeleteUserWithoutButton(String username){
        //webUIHelpers.clickElementAndSelect(droplistCongTyRoot, droplistCongTy);
        webUIHelpers.setText(tbSearch,username);
        delay(2000); // Delay 1 giây giữa các trường
        webUIHelpers.clickElement(btnSearch);
        delay(1500); // Delay 1 giây giữa các trường

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
            // Xác định hàng chứa tài khoản của bạn trong danh sách người dùng
            WebElement userRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td[contains(text(), '" + username + "')]]")));
            System.out.println("UserRow là: " + userRow);

            // Kiểm tra sự hiện diện của nút "Xóa" trong hàng đó
            try {
                // Giả sử nút "Xóa" có class là "delete-icon"
                WebElement deleteButton = userRow.findElement(By.xpath("//*[@id=\"user_table\"]/tbody/tr/td[2]/img[4]"));
                if (deleteButton.isDisplayed() && deleteButton.isEnabled()) {
                    System.out.println("Nút 'Xóa' hiển thị và có thể click được.");
                    // Thực hiện hành động click nếu cần
                    deleteButton.click();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // Thêm các bước xử lý sau khi click nút "Xóa"
                    WebElement deleteButtonPopup = driver.findElement(By.xpath("//*[@id=\"delUserModal\"]/div/div/div[3]/button[2]"));
                    deleteButtonPopup.click();
                    String actualText = webUIHelpers.getText(notiPopup);
                    if(actualText.equals("Xóa thành công")){
                        webUIHelpers.clearText(tbSearch);
                        webUIHelpers.setText(tbSearch,username);
                        webUIHelpers.clickElement(btnSearch);

                        String actualTextNoData = webUIHelpers.getText(textNoData);
                        System.out.println("TextNodata:" + actualTextNoData);
                        if(actualTextNoData.equals("Không có dữ liệu người dùng")){
                            System.out.println("Xóa người dùng thành công");
                        }else {
                            System.out.println("Xóa người dùng thất bại");
                        }
                    }else {
                        System.out.println("Xoa người dùng thất bại");
                    }
                } else {
                    System.out.println("Nút 'Xóa' không thể click được.");
                }
            } catch (NoSuchElementException e) {
                System.out.println(e);
                System.out.println("Nút 'Xóa' không tồn tại.");
            }
        } catch (TimeoutException e) {
            // Bắt ngoại lệ nếu không tìm thấy người dùng
            System.out.println("Không tìm thấy người dùng '" + username + "' để xóa.");
        } catch (NoSuchElementException e) {
            System.out.println("Không tìm thấy hàng cho tài khoản: " + username);
        }

    }
    public void clickBtnEdit(){
        webUIHelpers.clickElement(btnEdit);
        webUIHelpers.sleep(3000);
    }
    public void selectRole(String role){
        webUIHelpers.selectElement(ddRole, role);
    }
    public void clickBtnSaveEdit(){
        webUIHelpers.clickElement(btnSave_edit);
    }
    public void editRoleUser(String textSearch, String updateRole){
        this.searchUser(textSearch);
        this.clickBtnEdit();
        this.selectRole(updateRole);
        this.clickBtnSaveEdit();
    }
    public void clickBtnAssign(){
        webUIHelpers.clickElement(btnAssign);
    }
    public void searchVehicleInPopupAssign(String vehicle){
        webUIHelpers.setText(searchboxVehicle, vehicle);
        webUIHelpers.clickElement(btnSearchVehicle);
        webUIHelpers.sleep(3000);
    }
    public void verifySearchByVehicle(String expect){
        String expectText = expect.toLowerCase();
        List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"assigned_table\"]/tbody/tr"));
        for (WebElement row : rows) {
            WebElement tdElement = row.findElement(By.xpath("./td[4]"));
            String actualText = tdElement.getText().toLowerCase();
            Assert.assertTrue(actualText.contains(expectText));
        }
    }

    // Hàm delay giữa các trường
    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds); // delay giữa các trường
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
