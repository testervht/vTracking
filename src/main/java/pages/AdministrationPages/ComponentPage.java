package pages.AdministrationPages;

import bases.WebUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;

import java.time.Duration;
import java.util.List;

public class ComponentPage {
    WebDriver driver;
    private LoginPage loginPage;
    private WebUIHelpers uiHelpers;
    private By tabQuanTri = By.xpath("/html/body/div[1]/div[2]/div[12]/a/span");
    private By tabLoaiLinhKien = By.xpath("//*[@id=\"vehiclePartAdminTabMenu\"]/span");
    private By buttonAdd = By.xpath("//*[@id=\"vehiclePartAdminTab\"]/div[2]/div[2]/button");
    //Cac truong Trong Popup Them
    private By textBoxTenLinhKien = By.id("vehiclePartNameAdminAdd");
    private By textAlertTenLinhKien = By.id("vehiclePartNamAdminAddNoti");
    private By textBoxTenVietTat = By.id("vehiclePartAbbrAdminAdd");
    private By textBoxBaoTriTheoKM = By.id("vehiclePartWarrantyAdminAdd");
    private By textAlertBaoTriTheoKM = By.id("vehiclePartWarrantyAdminAddNoti");
    private By textBoxBaoTriTheoThang = By.id("vehiclePartMonthlyWarrantyAdminAdd");
    private By textAlertBaoTriTheoThang = By.id("vehiclePartMonthlyWarrantyAdminAddNoti");
    private By textBoxGia = By.id("vehiclePartUnitPriceAdminAdd");
    private By buttonLuu = By.xpath("/html/body/div[12]/div/div/div[3]/button[2]");
    private By notiPopup = By.cssSelector("span[data-notify='message']");
    private By searchBoxTenLoaiLinhKien = By.id("textSearch_VehiclePartAdmin");
    private By buttonSearch = By.xpath("/html/body/div[4]/div[2]/div[3]/div[1]/form/button/img");
    private By buttonSua = By.xpath("//*[@id=\"VehiclePartAdmin_Table\"]/tbody/tr/td[1]/img[1]");
    private By searchBox = By.id("textSearch_VehiclePartAdmin");
//    private By buttonSearch = By.xpath("//*[@id=\"vehiclePartAdminTab\"]/div[1]/form/button");
    private By textTotal = By.id("total_VehiclePartAdmin");
    private By textNoData = By.xpath("//*[@id=\"VehiclePartAdmin_Table\"]/tbody/td");
    //Element in popup edit
    private By textBoxTenLinhKienEdit = By.id("vehiclePartNameAdminEdit");
    private By textAlertTenLinhKienEdit = By.id("vehiclePartNameAdminEditNoti");
    private By textBoxTenVietTatEdit = By.id("vehiclePartAbbrAdminEdit");
    private By textBoxBaoTriTheoKMEdit = By.id("vehiclePartWarrantyAdminEdit");
    private By textAlertBaoTriTheoKMEdit = By.id("vehiclePartWarrantyAdminEditNoti");
    private By textBoxBaoTriTheoThangEdit = By.id("vehiclePartMonthlyWarrantyAdminEdit");
    private By textAlertBaoTriTheoThangEdit = By.id("vehiclePartMonthlyWarrantyAdminEditNoti");
    private By textBoxGiaEdit = By.id("vehiclePartUnitPriceAdminEdit");
    private By buttonLuuEdit = By.xpath("/html/body/div[13]/div/div/div[3]/button[2]");
    public ComponentPage(WebDriver driver) {
        this.driver = driver;
        uiHelpers = new WebUIHelpers(driver);
        System.out.println("Add Component Page:" + driver);
    }

    public void Navigation() {
        uiHelpers.clickElement(tabQuanTri);
        uiHelpers.clickElement(tabLoaiLinhKien);
    }

    public void AddComponent(String TenLinhKien, String TenVietTat, String BaoTriTheoKM, String BaoTriTheoThang, String Gia) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonAdd));
        addButton.click();
        uiHelpers.setText(textBoxTenLinhKien, TenLinhKien);
        uiHelpers.setText(textBoxTenVietTat, TenVietTat);
        uiHelpers.setText(textBoxBaoTriTheoKM, BaoTriTheoKM);
        uiHelpers.setText(textBoxBaoTriTheoThang, BaoTriTheoThang);
        uiHelpers.setText(textBoxGia, Gia);
        uiHelpers.clickElement(buttonLuu);
    }

    public void AddComponentSuccess() {
        AddComponent("Linh kien auto test", "auto", "1", "1", "10000");
        String actualNotiPopup = uiHelpers.getText(notiPopup);
        if (actualNotiPopup.equals("Tạo thành công")) {
            System.out.println("Thêm thành công Loại Linh kiện");
        } else {
            System.out.println("Test case failed");
        }
    }

    public void AddComponentMissCompulsory(String TenLinhKien, String TenVietTat, String BaoTriTheoKM, String BaoTriTheoThang, String Gia) {
        AddComponent(TenLinhKien, TenVietTat, BaoTriTheoKM, BaoTriTheoThang, Gia);
        if (!(TenLinhKien.isEmpty()) && !(BaoTriTheoKM.isEmpty()) && !(BaoTriTheoThang.isEmpty())) {
            AddComponentSuccess();
        } else {
            if (TenLinhKien.isEmpty()) {
                String actualAlert = uiHelpers.getText(textAlertTenLinhKien);
                System.out.println("Actual Alert:" + actualAlert);
                if (actualAlert.equals("Tên linh kiện là thông tin bắt buộc")) {
                    System.out.println("Thêm không thành công do thiếu trường Tên lINH KIỆN");
                } else {
                    System.out.println("Test case failed");
                }
            } else if (BaoTriTheoKM.isEmpty()) {
                String actualAlert = uiHelpers.getText(textAlertBaoTriTheoKM);
                System.out.println("Bao tri theo KM alert:" + actualAlert);
                if (actualAlert.equals("Bảo trì theo KM là thông tin bắt buộc")) {
                    System.out.println("Thêm không thành công do thiếu trường Bảo trì theo KM");
                } else {
                    System.out.println("Test case failed");
                }
            } else {
                String actualAlert = uiHelpers.getText(textAlertBaoTriTheoThang);
                if (actualAlert.equals("Bảo trì theo tháng là thông tin bắt buộc")) {
                    System.out.println("Thêm không thành công do thiếu trường Bảo trì theo KM");
                } else {
                    System.out.println("Test case failed");
                }

            }

        }
    }
    public void AddComponentDuplicateName(){
        AddComponent("Hien", "", "1", "1", "10000");
        String actualAlert = uiHelpers.getText(textAlertTenLinhKien);
        if(actualAlert.equals("Tên linh kiện đã tồn tại. Vui lòng nhập lại")){
            System.out.println("Thêm không thành công do trùng Tên Loại linh kiện");
        }else{
            System.out.println("Test case failed");
        }
    }
    public void SearchTextBox(String keyword){
        uiHelpers.setText(searchBox, keyword);
        uiHelpers.clickElement(buttonSearch);

        WebElement table = driver.findElement(By.id("VehiclePartAdmin_Table"));
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

            }
            System.out.println("Keyword not found in any row.");
        }
        //System.out.println("Keyword not found in any row.");
    }

    public void SearchNoData(){
        SearchTextBox("hien");
        String actualTextTotal = uiHelpers.getText(textTotal);
        String actualTextNoData = uiHelpers.getText(textNoData);
        if(actualTextTotal.equals("Tổng cộng: 0 Loại linh kiện") && actualTextNoData.equals("Chưa có loại linh kiện")){
            System.out.println("Tìm kiếm không có dữ liệu");
        }else {
            System.out.println("Test case Fail");
        }
    }
    public void DeleteComponent(String componentName){
        uiHelpers.setText(searchBoxTenLoaiLinhKien, componentName);
        uiHelpers.clickElement(buttonSearch);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
            // Xác định hàng chứa loai linh kiên
            WebElement componentRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td[contains(text(), '" + componentName + "')]]")));
            System.out.println("Component row là: " + componentRow);

            // Kiểm tra sự hiện diện của nút "Xóa" trong hàng đó
            try {
                // Giả sử nút "Xóa" có class là "delete-icon"
                WebElement deleteButton = componentRow.findElement(By.xpath("//*[@id=\"VehiclePartAdmin_Table\"]/tbody/tr/td[1]/img[2]"));
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
                    WebElement deleteButtonPopup = driver.findElement(By.xpath("//*[@id=\"delVehiclePartAdminModal\"]/div/div/div[3]/button[2]"));
                    deleteButtonPopup.click();
                    String actualText = uiHelpers.getText(notiPopup);
                    //System.out.println("Xóa linh kiện thành công");
                    if(actualText.equals("Xóa thành công")){
                        uiHelpers.clearText(searchBoxTenLoaiLinhKien);
                        uiHelpers.setText(searchBoxTenLoaiLinhKien, componentName);
                        uiHelpers.clickElement(buttonSearch);
                        WebElement textNoData = driver.findElement(By.xpath("//*[@id=\"VehiclePartAdmin_Table\"]/tbody/td"));
                        String actualTextNoData = textNoData.getText();
                        if(actualTextNoData.equals("Chưa có loại linh kiện")){
                            System.out.println("Xóa loại linh kiện thành công");
                        }else {
                            System.out.println("Xóa loại linh kiện thất bại");
                        }

                    }else if(actualText.equals("Danh mục đã được sử dụng. Bạn không thể xóa")){
                        System.out.println("Xoa loại linh kiện thất bại do đã được sử dụng trong hệ thống");
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
            System.out.println("Không tìm thấy hàng của loại hình vâ tải: " + componentName);
        }
    }
    public void EditComponent(String oldTenLoaiLinhKien, String newTenLoaiLinhKien, String newBaoTriTheoKM, String newBaoTriTheoThang){
        uiHelpers.setText(searchBoxTenLoaiLinhKien, oldTenLoaiLinhKien);
        uiHelpers.clickElement(buttonSearch);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement editButton = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonSua));
        editButton.click();
        //Khong sua Bao tri theo KM + Theo thang, chi Sua Ten linh kien
        if(newBaoTriTheoKM.isEmpty() && newBaoTriTheoThang.isEmpty()){
            uiHelpers.clearText(textBoxTenLinhKienEdit);
            uiHelpers.setText(textBoxTenLinhKienEdit, newTenLoaiLinhKien);
            // Khong sua Ten linh kien + Bao tri theo thang, chi sua Bao tri theo KM
        } else if (newTenLoaiLinhKien.isEmpty() && newBaoTriTheoThang.isEmpty()) {
            uiHelpers.clearText(textAlertBaoTriTheoKMEdit);
            uiHelpers.setText(textAlertBaoTriTheoKMEdit, newBaoTriTheoKM);
            // Khong sua Ten linh kien + Bao tri theo KM, chi sua Bao tri theo thang
        } else if (newTenLoaiLinhKien.isEmpty() && newBaoTriTheoKM.isEmpty()) {
            uiHelpers.clearText(textBoxBaoTriTheoThangEdit);
            uiHelpers.setText(textBoxBaoTriTheoThangEdit, newBaoTriTheoThang);
            // Sua het
        } else {
            uiHelpers.clearText(textBoxTenLinhKienEdit);
            uiHelpers.setText(textBoxTenLinhKienEdit, newTenLoaiLinhKien);
            uiHelpers.clearText(textBoxBaoTriTheoKMEdit);
            uiHelpers.setText(textBoxBaoTriTheoKMEdit, newBaoTriTheoKM);
            uiHelpers.clearText(textBoxBaoTriTheoThangEdit);
            uiHelpers.setText(textBoxBaoTriTheoThangEdit, newBaoTriTheoThang);
        }
    }
    public void EditComponentMissTenLinhKien(){
        EditComponent("123","", "1", "1");
        uiHelpers.clearText(textBoxTenLinhKienEdit);
        uiHelpers.clickElement(buttonLuuEdit);
        String actualAlert = uiHelpers.getText(textAlertTenLinhKienEdit);
        System.out.println("Actual Alert:" + actualAlert);
        if (actualAlert.equals("Tên linh kiện là thông tin bắt buộc")) {
            System.out.println("Sửa không thành công do thiếu trường Tên lINH KIỆN");
        } else {
            System.out.println("Test case failed");
        }
    }
    public void EditComponentMissBaoTriTheoKM(){
        EditComponent("123", "1234","","2");
        uiHelpers.clearText(textBoxBaoTriTheoKMEdit);
        uiHelpers.clickElement(buttonLuuEdit);
        String actualAlert = uiHelpers.getText(textAlertBaoTriTheoKMEdit);
        System.out.println("Actual Alert:" + actualAlert);
        if (actualAlert.equals("Bảo trì theo KM là thông tin bắt buộc")) {
            System.out.println("Sửa không thành công do thiếu trường bảo trì theo KM");
        } else {
            System.out.println("Test case failed");
        }
    }
    public void EditComponentMissBaoTriTheoThang(){
        EditComponent("123", "1234","1","");
        uiHelpers.clickElement(buttonLuuEdit);
        String actualAlert = uiHelpers.getText(textAlertBaoTriTheoThangEdit);
        System.out.println("Actual Alert:" + actualAlert);
        if (actualAlert.equals("Bảo trì theo tháng là thông tin bắt buộc")) {
            System.out.println("Sửa không thành công do thiếu trường bảo trì theo tháng");
        } else {
            System.out.println("Test case failed");
        }
    }
    public void EditComponentSuccess(){
        EditComponent("Linh kien auto test", "Linh kien auto test edit","2","2");
        uiHelpers.clickElement(buttonLuuEdit);
        String actualAlert = uiHelpers.getText(notiPopup);
        System.out.println("Actual Alert:" + actualAlert);
        if (actualAlert.equals("Cập nhật thành công")) {
            System.out.println("Sửa thành công Loại linh kiện");
        } else {
            System.out.println("Test case failed");
        }
    }
    public void EditComponentDuplicateName(){
        EditComponent("123", "hien","2","2");
        uiHelpers.clickElement(buttonLuuEdit);
        String actualAlert = uiHelpers.getText(textBoxTenLinhKien);
        System.out.println("Actual Alert:" + actualAlert);
        if (actualAlert.equals("Tên linh kiện đã tồn tại. Vui lòng nhập lại")) {
            System.out.println("Sửa Không thành công do trùng tên loại linh kiện");
        } else {
            System.out.println("Test case failed");
        }
    }
}



