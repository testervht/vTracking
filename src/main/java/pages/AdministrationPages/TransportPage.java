package pages.AdministrationPages;

import bases.WebUIHelpers;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TransportPage {
    WebDriver driver;
    private LoginPage loginPage;
    private WebUIHelpers uiHelpers;
    private By tabQuanTri = By.xpath("//span[contains(text(),'Quản trị')]");
    private By tabLoaiHinhVanTai = By.xpath("//*[@id=\"TransportTabMenu\"]/span");
    private By buttonAdd = By.xpath("//*[@id=\"TransportTab\"]/div[2]/div[2]/button");
    //Cac truong Trong Popup Them
    private By textBoxTenLoaiHinhVanTai = By.id("nameTypeTransportAdd");
    private By textAlertTenLoaiHinhVanTai = By.id("nameTypeTransportAddNoti");
    private By textBoxGiaTri = By.id("ValueTransportAdd");
    private By textAlertGiaTri = By.id("ValueTransportAddNoti");

    //Cac button trong popup Them
    private By buttonLuu = By.xpath("/html/body/div[11]/div/div/div[3]/button[2]");
    private By notiPopup = By.cssSelector("span[data-notify='message']");

    //Textbox Tim kiếm theo Tên loại hình vận tải
    private By searchBoxTenLoaiHinhVanTai = By.id("textSearch_Transport");
    private By buttonSearch = By.xpath("//*[@id=\"TransportTab\"]/div[1]/form/button");
    private By buttonXoa = By.xpath("//*[@id=\"Transport_Table\"]/tbody/tr/td[1]/img[2]");

    private By buttonSua = By.xpath("//*[@id=\"Transport_Table\"]/tbody/tr/td[1]/img[1]");
    private By buttonLuuEdit = By.xpath("/html/body/div[8]/div/div/div[3]/button[2]");
    //Cac truong trong Popup Sua
    private By textBoxTenLoaiHinhVanTaiEdit = By.id("imeiTransportEdit");
    private By textAlertTenLoaiHinhVanTaiEdit = By.id("imeiTransportEditNoti");
    //Tổng cộng: x loại hình vận tải
    private By textTotal = By.id("total_Transport");
    private By textNoData = By.xpath("//*[@id=\"Transport_Table\"]/tbody/td");
    public TransportPage(WebDriver driver){
        this.driver = driver;
        uiHelpers = new WebUIHelpers(driver);
        System.out.println("AddTransportPage: " + driver);
    }
    public void Navigation(){
        uiHelpers.clickElement(tabQuanTri);
        uiHelpers.clickElement(tabLoaiHinhVanTai);
    }
    public void AddTransport(String TenLoaiHinhVanTai, String GiaTri){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonAdd));
        addButton.click();
        uiHelpers.setText(textBoxTenLoaiHinhVanTai, TenLoaiHinhVanTai);
        uiHelpers.setText(textBoxGiaTri, GiaTri);
        uiHelpers.clickElement(buttonLuu);
    }
    public void AddTransportSuccess(){
        AddTransport("Loai hinh van tai autotest", "35443546");
        String actualNotiPopup = uiHelpers.getText(notiPopup);
        Assert.assertEquals(actualNotiPopup, "Tạo thành công");
    }
    public void AddTransportMissName(){
        AddTransport("", "1002");
        String actualAlert = uiHelpers.getText(textAlertTenLoaiHinhVanTai);
        if(actualAlert.equals("Tên loại hình vận tải là thông tin bắt buộc")){
            System.out.println("Thêm không thành công do thiếu trường Tên Loại hình vận tải");
        }else{
            System.out.println("Test case failed");
        }
    }
    public void AddTransportMissValue(){
        AddTransport("Hien2", "1002");
        String actualAlert = uiHelpers.getText(textAlertGiaTri);
        if(actualAlert.equals("Giá trị là thông tin bắt buộc")){
            System.out.println("Thêm không thành công do thiếu trường Gía trị");
        }else{
            System.out.println("Test case failed");
        }
    }
    public void AddTranSportMissCompulsory(String TenLoaiHinhVanTai, String GiaTri){
        AddTransport(TenLoaiHinhVanTai, GiaTri);
        if(!(TenLoaiHinhVanTai.isEmpty()) && !(GiaTri.isEmpty())){
            AddTransportSuccess();
        } else {
            if(TenLoaiHinhVanTai.isEmpty()){
                String actualAlert = uiHelpers.getText(textAlertTenLoaiHinhVanTai);
                System.out.println("Actual Alert:" + actualAlert);
                if(actualAlert.equals("Tên loại hình vận tải là thông tin bắt buộc")){
                    System.out.println("Thêm không thành công do thiếu trường Tên Loại hình vận tải");
                }else{
                    System.out.println("Test case failed");
                }
            } else {
                String actualAlert = uiHelpers.getText(textAlertGiaTri);
                if(actualAlert.equals("Giá trị là thông tin bắt buộc")){
                    System.out.println("Thêm không thành công do thiếu trường Gía trị");
                }else{
                    System.out.println("Test case failed");
                }
            }
        }


    }
    public void AddTransportDuplicateName(){
        AddTransport("Hien", "1004");
        String actualAlert = uiHelpers.getText(textAlertTenLoaiHinhVanTai);
        if(actualAlert.equals("Tên loại hình vận tải đã tồn tại. Vui lòng nhập lại")){
            System.out.println("Thêm không thành công do trùng Tên Loại hình vận tải");
        }else{
            System.out.println("Test case failed");
        }
    }
    public void AddTransportDuplicateValue(){
        AddTransport("Hien0", "1001");
        String actualAlert = uiHelpers.getText(textAlertGiaTri);
        if(actualAlert.equals("Giá trị đã tồn tại. Vui lòng nhập lại")){
            System.out.println("Thêm không thành công do trùng trường Gía trị");
        }else{
            System.out.println("Test case failed");
        }
    }
    public void DeleteTransport(String transportName){
        uiHelpers.setText(searchBoxTenLoaiHinhVanTai, transportName);
        uiHelpers.clickElement(buttonSearch);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
            // Xác định hàng chứa loai hình vận tải
            //WebElement transportRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@id='Vehiclekey' and text()='123']")));
            //WebElement transportRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZĂÂĐÊÔƠƯ', 'abcdefghijklmnopqrstuvwxyzăâđêôơư'), '" + transportName.toLowerCase() + "')]]")));
            //WebElement transportRow = //*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'taxi')]

            WebElement transportRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"+transportName.toLowerCase()+"')]")));
            System.out.println("TransportRow là: " + transportRow);

            // Kiểm tra sự hiện diện của nút "Xóa" trong hàng đó
            try {
                // Giả sử nút "Xóa" có class là "delete-icon"
                WebElement deleteButton = transportRow.findElement(By.xpath("//*[@id=\"Transport_Table\"]/tbody/tr[1]/td[1]/img[2]"));
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
                    WebElement deleteButtonPopup = driver.findElement(By.xpath("//*[@id=\"delTransportModal\"]/div/div/div[3]/button[2]"));
                    deleteButtonPopup.click();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    String actualText = uiHelpers.getText(notiPopup);
                    if(actualText.equals("Xóa thành công")){
                        System.out.println("Xóa loại hình vận tải thành công");
//                        uiHelpers.clearText(searchBoxTenLoaiHinhVanTai);
//                        uiHelpers.setText(searchBoxTenLoaiHinhVanTai, transportName);
//                        uiHelpers.clickElement(buttonSearch);
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                        WebElement textNoData = driver.findElement(By.xpath("//*[@id=\"Transport_Table\"]/tbody/td"));
//                        String actualTextNoData = textNoData.getText();
//                        if(actualTextNoData.equals("Không có dữ liệu loại hình vận tải")){
//                            System.out.println("Xóa loại hình vận tải thành công");
//                        }else {
//                            System.out.println("Xóa loại hình vận tải thất bại");
//                        }

                    }else if(actualText.equals("Xóa loại hình vận tải? Danh mục đã được sử dụng. Bạn không thể xóa")){
                        System.out.println("Xoa loại hình vận tải thất bại do đã được sử dụng trong hệ thống");
                    }else{
                        System.out.println("Sai nội dung thông báo");
                    }
                } else {
                    System.out.println("Nút 'Xóa' không thể click được.");
                }
            } catch (NoSuchElementException e) {
                System.out.println(e);
                System.out.println("Nút 'Xóa' không tồn tại.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Không tìm thấy hàng của loại hình vâ tải: " + transportName);
        }
    }
    public void EditTransportMissCompulsory(String oldTenLoaiHinhVanTai){
        uiHelpers.setText(searchBoxTenLoaiHinhVanTai, oldTenLoaiHinhVanTai);
        uiHelpers.clickElement(buttonSearch);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonSua));
        editButton.click();
        uiHelpers.clearText(textBoxTenLoaiHinhVanTaiEdit);
        uiHelpers.clickElement(buttonLuuEdit);
        String actualAlert = uiHelpers.getText(textAlertTenLoaiHinhVanTai);
        System.out.println("Actual Alert:" + actualAlert);
        if(actualAlert.equals("Tên loại hình vận tải là thông tin bắt buộc")){
            System.out.println("Sửa không thành công do thiếu trường Tên Loại hình vận tải");
        }else{
            System.out.println("Test case failed");
        }
    }
    public void EditTransportDuplicateName(String oldTenLoaiHinhVanTai, String duplicateName){
        uiHelpers.setText(searchBoxTenLoaiHinhVanTai, oldTenLoaiHinhVanTai);
        uiHelpers.clickElement(buttonSearch);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonSua));
        editButton.click();
        uiHelpers.clearText(textBoxTenLoaiHinhVanTaiEdit);
        uiHelpers.setText(textBoxTenLoaiHinhVanTaiEdit, duplicateName);
        uiHelpers.clickElement(buttonLuuEdit);
        String actualAlert = uiHelpers.getText(textAlertTenLoaiHinhVanTai);
        if(actualAlert.equals("Tên loại hình vận tải đã tồn tại. Vui lòng nhập lại")){
            System.out.println("Sửa không thành công do nhập trùng trường Tên Loại hình vận tải");
        }else{
            System.out.println("Test case failed");
        }
    }
    public void EditTransportSuccess(String oldTenLoaiHinhVanTai, String newName){
        uiHelpers.setText(searchBoxTenLoaiHinhVanTai, oldTenLoaiHinhVanTai);
        uiHelpers.clickElement(buttonSearch);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonSua));
        editButton.click();
        uiHelpers.clearText(textBoxTenLoaiHinhVanTaiEdit);
        uiHelpers.setText(textBoxTenLoaiHinhVanTaiEdit, newName);
        uiHelpers.clickElement(buttonLuuEdit);
        String actualAlert = uiHelpers.getText(notiPopup);
        if(actualAlert.equals("Cập nhật thành công")){
            System.out.println("Sửa looại hình vận tải thành công");
        }else{
            System.out.println("Test case failed");
        }
    }
    public void SearchTextBox(String keyword){
        uiHelpers.setText(searchBoxTenLoaiHinhVanTai, keyword);
        uiHelpers.clickElement(buttonSearch);

        WebElement table = driver.findElement(By.id("Transport_Table"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Tim tat ca cac hang trong bang
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        //Duyet qua tung hang
        for (WebElement row : rows) {
            // Tìm tất cả các cột trong hàng
            System.out.println("Tìm cac cột trong hàng");
            List<WebElement> columns = row.findElements(By.tagName("td"));

            // Duyệt qua từng cột
            for (WebElement column : columns) {
                System.out.println("Kiểm tra nếu trong cột chứa từ khóa");
                // Kiểm tra nếu text trong cột chứa từ khóa
                if(column.getText().equals("")){
                    System.out.println("Kiểm tra nếu trong cột chứa từ khóa 2");
                    continue;
                }else {
                    if (column.getText().contains(keyword)) {
                        System.out.println("Tim kiem tuong doi: " + row.getText());
                        break; // Dừng kiểm tra các cột còn lại trong hàng này
                    }else if(column.getText().equals(keyword)){
                        System.out.println("Tim kiem tuyet doi: " + row.getText());
                        break; // Dừng kiểm tra các cột còn lại trong hàng này
                    }
                }
                //System.out.println("Keyword not found in any row.");
//                if (column.getText().contains(keyword)) {
//                        System.out.println("Tim kiem tuong doi: " + row.getText());
//                        break; // Dừng kiểm tra các cột còn lại trong hàng này
//                    }else if(column.getText().equals(keyword)){
//                        System.out.println("Tim kiem tuyet doi: " + row.getText());
//                        break; // Dừng kiểm tra các cột còn lại trong hàng này
//                    }
            }
            System.out.println("Keyword not found in any row.");
        }
        //System.out.println("Keyword not found in any row.");
    }

    public void SearchNoData(){
        SearchTextBox("taxi");
        String actualTextTotal = uiHelpers.getText(textTotal);
        String actualTextNoData = uiHelpers.getText(textNoData);
        if(actualTextTotal.equals("Tổng cộng: 0 Loại hình vận tải") && actualTextNoData.equals("Không có dữ liệu loại hình vận tải")){
            System.out.println("Tìm kiếm không có dữ liệu");
        }else {
            System.out.println("Test case Fail");
        }
    }
    public void SearchRelativeValue(){

    }
}
