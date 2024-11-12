package pages.AdministrationPages;

import bases.WebUIHelpers;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.LoginPage;

import java.time.Duration;
import java.util.List;

public class TypeOfVehiclePage {
    WebDriver driver;
    private LoginPage loginPage;
    private WebUIHelpers webUIHelpers;
    private By searchBox = By.id("textSearch_Vehicle");
    private By buttonSearch = By.xpath("//*[@id=\"VehicleTab\"]/div[1]/form/button");
    private By textTotal = By.xpath("//*[@id=\"total_Vehicle\"]");
    private By textNoData = By.xpath("//*[@id=\"Vehicle_Table\"]/tbody/td");
    private By buttonAdd = By.xpath("//*[@id=\"VehicleTab\"]/div[2]/div[2]/button");
    //Cac truong Trong Popup Them
    private By textBoxTenLoaiPhuongTien = By.id("nameVehicleAdd");
    private By textAlertTenLoaiPhuongTien = By.id("nameVehicleAddNoti");
    private By textBoxGiaTri = By.id("ValueVehicleAdd");
    private By textAlertGiaTri = By.id("ValueVehicleAddNoti");
    private By textAlertIcon = By.id("IconVehicleAddNoti");

    // Cac truong trong Popup Sua
    private By textBoxTenLoaiPhuongTien_Sua = By.id("nameVehicleEdit");
    private By textAlertTenLoaiPhuongTien_Sua = By.id("nameVehicleEditNoti");
    private By buttonLuu_Sua = By.xpath("/html/body/div[17]/div/div/div[3]/button[2]");
    private By notiPopup = By.cssSelector("span[data-notify='message']");

    private By buttonLuu = By.xpath("/html/body/div[16]/div/div/div[3]/button[2]");

    public TypeOfVehiclePage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
    }
    // Tinh nang Search
    public void SearchTextBox(String keyword){
        webUIHelpers.setText(searchBox, keyword);
        webUIHelpers.clickElement(buttonSearch);
        WebElement table = driver.findElement(By.id("Vehicle_Table"));
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
            //System.out.println("Tìm cac cột trong hàng");
            List<WebElement> columns = row.findElements(By.tagName("td"));

            // Duyệt qua từng cột
            for (WebElement column : columns) {
                //System.out.println("Kiểm tra nếu trong cột chứa từ khóa");
                // Kiểm tra nếu text trong cột chứa từ khóa
                if(column.getText().equals("")){
                    //System.out.println("Kiểm tra nếu trong cột chứa từ khóa 2");
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

    // Tinh nang Add
    public void AddTypeOfVehicle(String TenLoaiPhuongTien, String GiaTri, String iconName){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonAdd));
        addButton.click();
        webUIHelpers.setText(textBoxTenLoaiPhuongTien, TenLoaiPhuongTien);
        webUIHelpers.setText(textBoxGiaTri, GiaTri);
        Select iconDropdown = new Select(driver.findElement(By.id("IconVehicleAdd")));

        if (iconName != null && !iconName.isEmpty()) {
            iconDropdown.selectByVisibleText(iconName);
            webUIHelpers.clickElement(buttonLuu);
        } else {
            // Code tiếp theo nếu iconName rỗng
            webUIHelpers.clickElement(buttonLuu);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Truyen vao element dang WebElement va tra ve kieu Select
    public Select getIconDropdown(WebElement icon) {
        WebElement iconDropdownElement = driver.findElement(By.id("IconVehicleAdd"));
        return new Select(iconDropdownElement);
    }
    public void AddTypeOfVehicleSuccess(){
        WebElement iconDropdown = driver.findElement(By.id("IconVehicleAdd"));
        AddTypeOfVehicle("loại xe 123", "09062024", "Ô tô" );
        String actualNotiPopup = webUIHelpers.getText(notiPopup);
        if(actualNotiPopup.equals("Tạo thành công")){
            System.out.println("Thêm thành công Loại phương tiện");
        }else{
            System.out.println("Test case failed");
        }
    }

    public void AddTypeOfVehicleMissCompulsory(String TenLoaiPhuongTien, String GiaTri, String iconName){
        //WebElement iconDropdown = driver.findElement(By.id("IconVehicleAdd"));
        AddTypeOfVehicle(TenLoaiPhuongTien, GiaTri, iconName );
        if(!(TenLoaiPhuongTien.isEmpty()) && !(GiaTri.isEmpty()) && (!iconName.isEmpty())){
            AddTypeOfVehicleSuccess();
        } else {
            if(TenLoaiPhuongTien.isEmpty()){
                String actualAlert = webUIHelpers.getText(textAlertTenLoaiPhuongTien);
                System.out.println("Actual Alert:" + actualAlert);
                if(actualAlert.equals("Trường này là trường bắt buộc")){
                    System.out.println("Thêm không thành công do thiếu trường Tên Loại phương tiện");
                }else{
                    System.out.println("Test case failed");
                }
            } else if(GiaTri.isEmpty()) {
                String actualAlert = webUIHelpers.getText(textAlertGiaTri);
                if(actualAlert.equals("Trường này là trường bắt buộc")){
                    System.out.println("Thêm không thành công do thiếu trường Gía trị");
                }else{
                    System.out.println("Test case failed");
                }
            }else if(iconName.isEmpty()){
                webUIHelpers.clickElement(buttonLuu);
                String actualAlert = webUIHelpers.getText(textAlertIcon);
                if(actualAlert.equals("Trường này là trường bắt buộc")){
                    System.out.println("Thêm không thành công do thiếu trường Iconnn");
                }else{
                    System.out.println("Test case failed");
                }
            }
        }
    }
    public void AddTypeOfVehicleDuplicateName(String TenLoaiPhuongTien, String GiaTri, String iconName){
        AddTypeOfVehicle(TenLoaiPhuongTien, GiaTri, iconName);
        String actualNotiPopup = webUIHelpers.getText(textAlertTenLoaiPhuongTien);
        if(actualNotiPopup.equals("Tên loại phương tiện đã tồn tại. Vui lòng nhập lại")){
            System.out.println("Thêm KHÔNG thành công Loại phương tiện do trùng tên");
        }else{
            System.out.println("Test case failed");
        }
    }

    //Tinh nang Sua
    public void EditTypeOfVehicle(String oldTenLoaiPhuongTien){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td[contains(text(), '" + oldTenLoaiPhuongTien + "')]]")));
        // Tìm nút "Sửa" trong hàng đó và click
        WebElement editButton = row.findElement(By.xpath(".//img[@class='icon-action' and @src='/assets/images/icon/edit.svg']"));
        editButton.click();
    }
    public void EditTypeOfVehicleMissCompulsory(String oldTenLoaiPhuongTien){
        EditTypeOfVehicle(oldTenLoaiPhuongTien);
        webUIHelpers.clearText(textBoxTenLoaiPhuongTien_Sua);
        boolean staleElement = true;
        while(staleElement){
            try{
                //WebElement element = driver.findElement(By.xpath("/html/body/div[17]/div/div/div[3]/button[2]"));
                webUIHelpers.clickElement(buttonLuu_Sua);
                //System.out.println("Sau khi nhan button lUU");
                staleElement = false;
            } catch(StaleElementReferenceException e){
                staleElement = true;
            }
        }
    }
    public void EditTypeOfVehicleSuccess(String oldTenLoaiPhuongTien, String newTenLoaiPhuongTien, String newIconName){
        EditTypeOfVehicle(oldTenLoaiPhuongTien);
        webUIHelpers.clearText(textBoxTenLoaiPhuongTien_Sua);
        webUIHelpers.setText(textBoxTenLoaiPhuongTien_Sua, newTenLoaiPhuongTien);
        Select iconDropdown = new Select(driver.findElement(By.id("IconVehicleEdit")));
        iconDropdown.selectByVisibleText(newIconName);
        webUIHelpers.clickElement(buttonLuu_Sua);
    }
    public void VerifyNoti(String expectText){
        String actualAlert = webUIHelpers.getText(notiPopup);
        Assert.assertEquals(actualAlert, expectText, "Test case fail");
    }
    public void VerifyText(String expectTotal, String expectNoData){
        String actualText = webUIHelpers.getText(textTotal);
        String actualNoData = webUIHelpers.getText(textNoData);
        Assert.assertEquals(actualText, expectTotal);
        Assert.assertEquals(actualNoData, expectNoData);
    }


    //Tinh nang Xoa
    public void DeleteTypeOfVehicle(String typeOfVehicleName){
        webUIHelpers.setText(searchBox, typeOfVehicleName);
        webUIHelpers.clickElement(buttonSearch);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
            // Xác định hàng chứa loai phương tiện
            System.out.println("Ten loai phuong tien la:" + typeOfVehicleName);
            WebElement typeOfVehicleRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td[contains(text(), '" + typeOfVehicleName + "')]]")));
            //WebElement typeOfVehicleRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@id='Vehiclekey' and text()='123']")));
            //System.out.println("Text xong hang:" + typeOfVehicleRow.getText());
            //WebElement typeOfVehicleRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), '123')]")));
            //System.out.println("TypeOfVehicleRow là: " + typeOfVehicleRow);

            // Kiểm tra sự hiện diện của nút "Xóa" trong hàng đó
            try {
                // Giả sử nút "Xóa" có class là "delete-icon"
                WebElement deleteButton = typeOfVehicleRow.findElement(By.xpath("//*[@id=\"Vehicle_Table\"]/tbody/tr/td[1]/img[2]"));
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
                    WebElement deleteButtonPopup = driver.findElement(By.xpath("//*[@id=\"delVehicleModal\"]/div/div/div[3]/button[2]"));

                    deleteButtonPopup.click();
                    String actualText = webUIHelpers.getText(notiPopup);
                    if(actualText.equals("Xóa thành công")){
                        webUIHelpers.setText(searchBox, typeOfVehicleName);
                        webUIHelpers.clickElement(buttonSearch);
                        WebElement textNoData = driver.findElement(By.xpath("//*[@id=\"Vehicle_Table\"]/tbody/td"));
                        String actualTextNoData = textNoData.getText();
                        if(actualTextNoData.equals("Không có dữ liệu loại phương tiện")){
                            System.out.println("Xóa loại phương tiện thành công");
                        }else {
                            System.out.println("Xóa loại phương tiện thất bại");
                        }

                    }else if(actualText.equals("Không thể xóa loại phương tiện đã được sử dụng trong hệ thống")){
                        System.out.println("Xoa loại phương tiện thất bại do đã được sử dụng trong hệ thống");
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
            System.out.println("Không tìm thấy hàng của loại hình vâ tải: " + typeOfVehicleName);
        }

    }

}
