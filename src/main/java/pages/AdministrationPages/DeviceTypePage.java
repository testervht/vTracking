package pages.AdministrationPages;

import bases.WebUIHelpers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DeviceTypePage {
    public DeviceTypePage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
    }
    private final WebDriver driver;
    private WebUIHelpers webUIHelpers;

    //Các element trong search
    private By tbSearch = By.id("textSearch_Device");
    private By btnSearch = By.cssSelector("[onclick=\"searchDeviceType()\"]");
    private By textTotalRole = By.id("total_Device");

    //Các button chức năng
    private By btnAdd = By.cssSelector("[onclick=\"document.getElementById('addTypeDeviceForm').reset()\"]");
    private By btnEdit = By.xpath("//*[@id=\"deivce_Table\"]/tbody/tr[1]/td[1]/img");

    //Các giá trị trong bảng danh sách
    private By textInTable = By.className("no_table_data");
    private By alertPopup = By.cssSelector("span[data-notify='message']");
    private By infoDeviceName = By.cssSelector(".wrap-text.deviceName");

    //element trong popup Thêm loai thiet bi
    public class AddDeivceTypeLocators {
        public static final By tbName = By.id("imeiTypeDeviceAdd");
        public static final By tbNameAbb = By.id("nameTypeDeviceAdd");
        public static final By ddWave = By.id("domanhsong");
        public static final By tbGPS = By.id("Gps");
        public static final By tbGMS = By.id("Gsm");
        public static final By ddDeviceType = By.id("typedeviceModalAdd");
        public static final By tbDecs = By.id("textcontentAdd");
        public static final By btnCancel = By.cssSelector("#addDeviceModalType .btn.btn-cancle");
        public static final By btnSave = By.cssSelector("[onclick=\"validateAddDeviceType()\"]");
        //noti
        public static final By notiName = By.id("imeiTypeDeviceAddNoti");
        public static final By notiGPS = By.id("GpsNoti");
        public static final By notiGMS = By.id("GsmNoti");
        public static final By notiDeviceType = By.id("typedeviceModalAddNoti");
    }
    //element trong popup Edit loai thiet bi
    public class EditDeivceTypeLocators {
        public static final By tbName = By.id("imeiTypeDeviceEdit");
        public static final By tbNameAbb = By.id("nameTypeDeviceEdit");
        public static final By ddWave = By.id("domanhsongEdit");
        public static final By tbGPS = By.id("GpsEdit");
        public static final By tbGMS = By.id("GsmEdit");
        public static final By ddDeviceType = By.id("typedeviceModalEdit");
        public static final By tbDecs = By.id("textcontentEdit");
        public static final By btnCancel = By.cssSelector("#editDeviceModalType .btn.btn-cancle");
        public static final By btnSave = By.cssSelector("[onclick=\"validateEditDeviceType()\"]");
        //noti
        public static final By notiName = By.id("imeiTypeDeviceEditNoti");
        public static final By notiGPS = By.id("GpsEditNoti");
        public static final By notiGMS = By.id("GsmEditNoti");
    }

    //Search
    public void search(String name) {
        webUIHelpers.clearAndSetText(tbSearch, name);
        webUIHelpers.clickElement(btnSearch);
        webUIHelpers.sleep(5000);
    }
    public void verifySearchWithoutValue() {
        String actualTextTotal = webUIHelpers.getText(textTotalRole);
        String actualTextInTable = webUIHelpers.getText(textInTable);
        String expectTextTotal = "Tổng cộng: 0 Loại thiết bị";
        String expectTextInTable = "Không có dữ liệu loại thiết bị";
        Assert.assertEquals(actualTextTotal, expectTextTotal);
        Assert.assertEquals(actualTextInTable, expectTextInTable);
    }
    public void verifySearchByText(String expect) {
        String expectText = expect.toLowerCase();
        for (WebElement element : driver.findElements(infoDeviceName)) {
            String actualText = element.getText().trim().toLowerCase();
            Assert.assertTrue(actualText.contains(expectText), "Element text doesn't contain '" + expectText + "'");
        }
    }

    //create
    public void clickBtnCreate(){
        webUIHelpers.clickElement(btnAdd);
    }
    public void fillInfoDeivceType(
            String name,
            String nameAbb,
            String wave,
            String GPS,
            String GMS,
            String deviceType,
            String decs
    ) {
        webUIHelpers.sleep(3000);
        webUIHelpers.setText(AddDeivceTypeLocators.tbName, name);
        webUIHelpers.setText(AddDeivceTypeLocators.tbNameAbb, nameAbb);
        if (!wave.isEmpty()) {
            webUIHelpers.selectElement(AddDeivceTypeLocators.ddWave, wave);
        }
        webUIHelpers.setText(AddDeivceTypeLocators.tbGPS, GPS);
        webUIHelpers.setText(AddDeivceTypeLocators.tbGMS, GMS);
        if (!deviceType.isEmpty()) {
            webUIHelpers.selectElement(AddDeivceTypeLocators.ddDeviceType, deviceType);
        }
        webUIHelpers.setText(AddDeivceTypeLocators.tbDecs, decs);
    }
    public void clickBtnSave(){
        webUIHelpers.clickElement(AddDeivceTypeLocators.btnSave);
    }
    public void verifyCreateWithoutName(){
        String notiActual = webUIHelpers.getText(AddDeivceTypeLocators.notiName);
        String expectText = "Trường này là trường bắt buộc";
        Assert.assertEquals(notiActual, expectText);
    }
    public void verifyCreateWithoutGPS(){
        String notiActual = webUIHelpers.getText(AddDeivceTypeLocators.notiGPS);
        String expectText = "Trường này là trường bắt buộc";
        Assert.assertEquals(notiActual, expectText);
    }
    public void verifyWhenDuplicateName(){
        String notiActual = webUIHelpers.getTextInAlert(alertPopup);
        String expectText = "Tên loại thiết bị đã tồn tại. Vui lòng nhập lại";
        Assert.assertEquals(notiActual, expectText);
    }
    public void verifyCreateSuccessfully(){
        String actualAlert = webUIHelpers.getTextInAlert(alertPopup);
        String expectText = "Tạo thành công";
        Assert.assertEquals(actualAlert, expectText);
    }
    public void clickBtnEdit(){
        webUIHelpers.clickElement(btnEdit);
    }
    public void editInfoDeviceType(
            String name,
            String nameAbb,
            String wave,
            String GPS,
            String GMS,
            String deviceType,
            String decs
    ) {
        webUIHelpers.sleep(3000);
        webUIHelpers.clearAndSetText(EditDeivceTypeLocators.tbName, name);
        webUIHelpers.clearAndSetText(EditDeivceTypeLocators.tbNameAbb, nameAbb);
        if (!wave.isEmpty()) {
            webUIHelpers.selectElement(EditDeivceTypeLocators.ddWave, wave);
        }
        webUIHelpers.clearAndSetText(EditDeivceTypeLocators.tbGPS, GPS);
        webUIHelpers.clearAndSetText(EditDeivceTypeLocators.tbGMS, GMS);
        if (!wave.isEmpty()) {
            webUIHelpers.selectElement(EditDeivceTypeLocators.ddDeviceType, deviceType);
        }
        webUIHelpers.clearAndSetText(EditDeivceTypeLocators.tbDecs, decs);
    }
    public void clickBtnSaveEdit(){
        webUIHelpers.clickElement(EditDeivceTypeLocators.btnSave);
    }
    public void verifyEditWithoutRequiredFields(){
        String actualText = webUIHelpers.getText(EditDeivceTypeLocators.notiName);
        String expectText = "Trường này là trường bắt buộc";
        Assert.assertEquals(actualText, expectText);
    }
    public void verifyEditSuccessfully(){
        String actualAlert = webUIHelpers.getTextInAlert(alertPopup);
        String expectText = "Cập nhật thành công";
        Assert.assertEquals(actualAlert, expectText);
        webUIHelpers.clearText(tbSearch);
    }
    public String getToken(String identifier, String password, String appKey, String appSecret) throws Exception {
        // Tạo HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Chuẩn bị body yêu cầu dưới dạng JSON
        String requestBody = """
                {
                    "identifier": "%s",
                    "password": "%s"
                }
                """.formatted(identifier, password);

        // Tạo HttpRequest để gửi POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.innoway.vn/api/app/vtracking/login"))
                .header("Content-Type", "application/json")
                .header("AppKey", appKey)
                .header("AppSecret", appSecret)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Gửi yêu cầu và nhận phản hồi
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Kiểm tra mã trạng thái phản hồi
        if (response.statusCode() == 200) {
            // Chuyển phản hồi thành JSON object
            JSONObject jsonResponse = new JSONObject(response.body());

            // Lấy token từ phản hồi
            return jsonResponse.getString("token");
        } else {
            throw new Exception("Failed to get token. Response code: " + response.statusCode());
        }
    }

    public String extractDeviceId() {
        // Tìm phần tử bằng cách sử dụng biến btnEdit đã khai báo
        WebElement iconAction = driver.findElement(btnEdit);

        // Lấy giá trị của thuộc tính onclick
        String onclickValue = iconAction.getAttribute("onclick");

        // Sử dụng biểu thức chính quy để trích xuất ID
        String id = onclickValue.replaceAll("setEditDeviceType\\('(.+?)'\\)", "$1");

        return id;
    }

    public void deleteDeviceTemplate() throws Exception {
        // Lấy token tự động từ API
        String token = getToken("giangptt", "123456aA@", "CuUTtsmfjMBOKeMEpkAo", "Ga8lL0lMVFr6fJoudGgJR9upsXuIgGxz"); // Cập nhật thông tin đăng nhập

        // Lấy ID thiết bị từ phương thức extractDeviceId
        String deviceId = extractDeviceId();

        // Tạo URL cho yêu cầu DELETE
        String webUrl = "https://api.innoway.vn/api/templates/" + deviceId;

        // Tạo URL object
        URL url = new URL(webUrl);

        // Mở kết nối
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Thiết lập phương thức yêu cầu
        connection.setRequestMethod("DELETE");

        // Thêm header Authorization với token lấy được
        connection.setRequestProperty("Authorization", "Bearer " + token);

        // Gửi yêu cầu và kiểm tra mã trạng thái
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            System.out.println("Successfully deleted template with ID: " + deviceId);
        } else {
            throw new Exception("Failed to delete template. Response code: " + responseCode);
        }
    }
}
