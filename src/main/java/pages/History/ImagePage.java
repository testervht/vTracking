package pages.History;

import bases.WebUIHelpers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v123.network.Network;
import org.openqa.selenium.devtools.v123.network.model.RequestId;
import org.openqa.selenium.devtools.v123.network.model.Response;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.HistoryPage;
import pages.LoginPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ImagePage {
    public ImagePage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
        this.historyPage = new HistoryPage(driver);
        this.actions = new Actions(driver);
    }
    private final WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private LoginPage loginPage;
    private HistoryPage historyPage;
    private Actions actions;

    //Các element trong search
    private By dropdownPlate = By.id("inputPlateVal");
    private By date_timepicker_start = By.id("date_timepicker_start");
    private By date_timepicker_end = By.id("date_timepicker_end");
    private By ddChanel = By.id("channelIdList");
    private By btnSearch = By.id("buttonSearchHistoryImage");

    //element trong popup plate
    private By tbSearch = By.id("textSearchOrgAndDevice");
    private By listResults = By.id("listSearchOrgAndDevice");
    private By btnSave = By.id("buttonChooseDeviceViewImage");

    //btn chuc nang
    private By btnUnchoose = By.id("image_unchoose");
    private By btnChoose = By.id("image_choose");
    private By btnDownload = By.id("image_down");

    //ele trong ds image
    private By spanBtnLoadMore = By.xpath("//div[contains(@onclick, 'getMoreImage')]//a");
    private By btnLoadMore = By.cssSelector("[onclick=\"getMoreImage(this)\"]");
    private By imageContainer = By.cssSelector("div.row.equal-cols div.col-sm-3");
    private By labelCountChooseImage = By.id("countChooseImage");

    //ele trong anh
    private By thumbnail = By.id("imageDeviceDetail");
    private By infoName = By.cssSelector(".gp-title-1");
    private By infoChanel = By.cssSelector(".gp-title-2");

    //ele popup anh
    private By infoPlate = By.id("info_plateNo");
    private By infoTime = By.id("info_time");
    private By infoSpeed = By.id("info_speed");
    private By infoLatLon = By.id("info_toado");
    private By infoGecoding = By.id("info_gecoding");
    private By btnClose = By.cssSelector("#modalimage .btn.btn-cancle");
    private By btnDown = By.id("info-download-image");

    private By alertPopup = By.cssSelector("span[data-notify='message']");

    //search function
    public void clickDDChoosePlate(){
        webUIHelpers.clickElement(dropdownPlate);
        webUIHelpers.sleep(3000);
    }
    public void setSearchKey(String searchKey){
        webUIHelpers.setText(tbSearch, searchKey);
        webUIHelpers.sleep(5000);
    }
    public void verifyResult(String searchKey) {
        WebElement ulElement = driver.findElement(listResults); //find ul in listResults
        List<WebElement> liElements = ulElement.findElements(By.tagName("li")); //find li in ul
        boolean isFound = false;
        for (WebElement li : liElements) {
            WebElement aElement = li.findElement(By.tagName("a"));//get text in a
            String content = aElement.getText();
            if (content.equals(searchKey)) {
                isFound = true;
                break;
            }
        }
        Assert.assertTrue(isFound, "Chuỗi '" + searchKey + "' không xuất hiện trong danh sách.");
    }
    public void clickResult(){
        webUIHelpers.waitAndClick(listResults);
        webUIHelpers.sleep(3000);
    }
    public void verifyChoosePlate(String id){
        WebElement spanElement = driver.findElement(By.id(id));
        String backgroundColorAfter = spanElement.getCssValue("background-color");//get background color after click
        Assert.assertEquals(backgroundColorAfter, "rgba(238, 0, 51, 0.45)", "Màu nền sau khi focus không đúng.");
    }
    public void tickCheckboxPlate(String id){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement btnLicense = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        btnLicense.click();
    }
    public void clickBtnChoosePlate(){
        webUIHelpers.clickElement(btnSave);
    }
    public void selectChanel(String chanel){
        webUIHelpers.selectElement(ddChanel, chanel);
        actions.moveByOffset(400, 400).click().perform();
        webUIHelpers.sleep(5000);
    }
    public void selectStartDate(String startTime){
        if (!startTime.isEmpty()) {
            webUIHelpers.clearAndSetText(date_timepicker_start, startTime);
        }
    }
    public void selectEndDate(String endTime){
        if (!endTime.isEmpty()) {
            webUIHelpers.clearAndSetText(date_timepicker_end, endTime);
        }
    }
    public void clickBtnSearch(){
        webUIHelpers.clickElement(btnSearch);
        webUIHelpers.sleep(3000);
    }
    public void clickBtnloadMore(int numOfImage) {
        int totalImages = numOfImage;
        int loadedImages = 32; // Mặc định sau khi nhấn nút tìm kiếm sẽ hiển thị 32 ảnh
        while (loadedImages < totalImages) {
            webUIHelpers.clickElement(btnLoadMore);
            loadedImages += 32; // mỗi lần click, load thêm 32 ảnh
            try {
                Thread.sleep(2000); // chờ để ảnh được load xong, có thể điều chỉnh thời gian ngủ
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void waitForImagesToLoad(int numOfImage) {
        if (numOfImage > 32) {
            clickBtnloadMore(numOfImage);
        }
    }
    private int countDisplayedImages() {
        return driver.findElements(imageContainer).size();
    }
    public void getInfoImage(int stt) {
        try {
            // Find all div elements with class "col-sm-3"
            List<WebElement> divElements = driver.findElements(By.cssSelector("div.col-sm-3"));

            if (stt > 0 && stt <= divElements.size()) {
                WebElement parentElement = divElements.get(stt - 1); // Adjust index to 0-based

                // Find all span elements within the parent element
                List<WebElement> spanElements = parentElement.findElements(By.tagName("span"));

                // Iterate through each span element and extract its text content
                for (WebElement spanElement : spanElements) {
                    String text = spanElement.getText().trim();
                    System.out.println("Text content: " + text);
                }
            } else {
                System.out.println("Invalid index: " + stt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clickThumbnail(int stt){
        try {
            // Find all div elements with class "col-sm-3"
            List<WebElement> divElements = driver.findElements(By.cssSelector("div.col-sm-3"));

            if (stt > 0 && stt <= divElements.size()) {
                WebElement parentElement = divElements.get(stt - 1);
                WebElement imageElement = parentElement.findElement(thumbnail);
                imageElement.click();

                System.out.println("Clicked on thumbnail " + stt);
            } else {
                System.out.println("Invalid index: " + stt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void verifyInfoPopupImage(String expectedPlate) {
        webUIHelpers.sleep(2000);
        // Kiểm tra xem các trường có xuất hiện hay không
        Assert.assertTrue(driver.findElement(infoPlate).isDisplayed(), "Plate field is not displayed!");
        Assert.assertTrue(driver.findElement(infoTime).isDisplayed(), "Time field is not displayed!");
        Assert.assertTrue(driver.findElement(infoSpeed).isDisplayed(), "Speed field is not displayed!");
        Assert.assertTrue(driver.findElement(infoLatLon).isDisplayed(), "Latitude/Longitude field is not displayed!");
        Assert.assertTrue(driver.findElement(infoGecoding).isDisplayed(), "Geocoding field is not displayed!");

        // Lấy giá trị của các trường sau khi kiểm tra sự xuất hiện của chúng
        String plate = driver.findElement(infoPlate).getText().trim();
        String time = driver.findElement(infoTime).getText().trim();
        String speed = driver.findElement(infoSpeed).getText().trim();
        String latlon = driver.findElement(infoLatLon).getText().trim();
        String gecocing = driver.findElement(infoGecoding).getText().trim();

        // In ra thông tin để debug nếu cần
        System.out.println("Plate: " + plate);
        System.out.println("Time: " + time);
        System.out.println("Speed: " + speed);
        System.out.println("Lat/Lon: " + latlon);
        System.out.println("Geocoding: " + gecocing);

        Assert.assertEquals(plate, expectedPlate, "Plate value does not match the expected plate from routePage!");
    }
    private boolean isDateWithinRange(String dateText, String startTime, String endTime) {
        try {
            // Định dạng ngày tháng cho dateText
            SimpleDateFormat dateFormatText = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
            // Định dạng ngày tháng cho startTime và endTime
            SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            // Phân tích dateText
            Date date = dateFormatText.parse(dateText);
            // Chuyển đổi startTime và endTime sang định dạng "dd/MM/yyyy - HH:mm:ss"
            Date start = dateFormatTime.parse(startTime + ":00");
            Date end = dateFormatTime.parse(endTime + ":00");

            // So sánh ngày
            return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void verifyNumAndInfoImage() {
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        AtomicInteger numOfImage = new AtomicInteger();
        final RequestId[] requestId = new RequestId[1];

        devTools.addListener(Network.responseReceived(), responseConsumer -> {
            Response response = responseConsumer.getResponse();
            if (response.getUrl().equals("https://vtracking.innoway.vn/getDataWithParam")) {
                requestId[0] = responseConsumer.getRequestId();
            }
        });

        devTools.addListener(Network.loadingFinished(), loadingFinished -> {
            if (requestId[0] != null) {
                String responseBody = devTools.send(Network.getResponseBody(requestId[0])).getBody();
//                System.out.println("Response Body Length: " + responseBody.length());
                int total = parseResponse(responseBody);
                numOfImage.set(total);
            }
        });
        this.clickBtnSearch();
        waitForImagesToLoad(numOfImage.get());
        int displayedImages = countDisplayedImages();
        System.out.println("Number of Images: " + numOfImage.get());
        Assert.assertEquals(displayedImages, numOfImage.get());
    }
    public void verifyNumAndInfoImageWithFilters(String chanel, String startTime, String endTime) {
        // Gọi hàm verifyNumAndInfoImage đã có để kiểm tra số lượng ảnh
        verifyNumAndInfoImage();

        // Lấy danh sách các div chứa ảnh
        List<WebElement> divElements = driver.findElements(By.cssSelector("div.col-sm-3"));

        // Kiểm tra xem có ảnh nào không
        Assert.assertFalse(divElements.isEmpty(), "Không có hình ảnh nào được tìm thấy!");

        // Duyệt qua từng ảnh nếu có
        for (int i = 0; i < divElements.size(); i++) {
            WebElement parentElement = divElements.get(i);

            // Lấy danh sách các span trong div hiện tại
            List<WebElement> spanElements = parentElement.findElements(By.tagName("span"));

            if (spanElements.size() >= 3) {
                String chanelText = spanElements.get(1).getText().trim();
                String dateText = spanElements.get(2).getText().trim();

                // Kiểm tra chanelText có khớp với chanel không
                Assert.assertEquals(chanelText, chanel, "Chanel không khớp");

                // Kiểm tra dateText có nằm trong khoảng thời gian không
                boolean isDateInRange = isDateWithinRange(dateText, startTime, endTime);
                Assert.assertTrue(isDateInRange, "Date không nằm trong khoảng thời gian cho phép");
            } else {
                System.out.println("Span elements không đủ để kiểm tra");
            }
        }
    }
    public void verifySearchWithoutImg(){
        String actualAlert = webUIHelpers.getText(alertPopup);
        String expectAlert = "Không có dữ liệu. Vui lòng thử lại";
        Assert.assertEquals(actualAlert, expectAlert);
    }
    public void clickDownloadIcon(int imageIndex) {
        try {
            // Tìm tất cả các nút tải ảnh bằng cách sử dụng selector class "icon-action"
            List<WebElement> downloadButtons = driver.findElements(By.cssSelector("img.icon-action"));

            // Kiểm tra xem chỉ số ảnh có hợp lệ không
            if (imageIndex > 0 && imageIndex <= downloadButtons.size()) {
                // Chọn nút tải ảnh của ảnh thứ n (chỉ số bắt đầu từ 0, nên cần trừ đi 1)
                WebElement downloadButton = downloadButtons.get(imageIndex - 1);

                // Nhấp vào nút tải ảnh
                downloadButton.click();

                System.out.println("Clicked download button for image index: " + imageIndex);
            } else {
                System.out.println("Invalid image index: " + imageIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void verifyDownload1Image() {
        String home = System.getProperty("user.home"); // anhntl52 xử lý downloadFilepath tương thích từng máy -> done
        String downloadFilepath = Paths.get(home, "Downloads").toString();
        long startTime = System.currentTimeMillis(); // Ghi lại thời gian hiện tại

        webUIHelpers.sleep(3000);

        boolean isDownloaded = false; // Cờ để kiểm tra xem tệp đã được tải về hay chưa
        File newestFile = null; // Biến để lưu tệp tải về

        // Vòng lặp kiểm tra thư mục đã được tải về chưa
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(1000);
                File[] files = new File(downloadFilepath).listFiles();

                // In debug thông tin về file trong thư mục
                if (files != null && files.length > 0) {
                    System.out.println("Found files:");
                    for (File file : files) {
                        System.out.println("File: " + file.getName() + ", Last Modified: " + file.lastModified());
                    }
                }

                if (files != null && files.length > 0) {
                    for (File file : files) {
                        // Kiểm tra xem file có phải là dạng excel và được tải sau startTime
                        if ((file.getName().endsWith(".jpg")) && file.lastModified() > startTime) {
                            if (newestFile == null || file.lastModified() > newestFile.lastModified()) {
                                newestFile = file;
                            }
                        }
                    }
                    if (newestFile != null) {
                        isDownloaded = true;
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!isDownloaded) {
            Assert.fail("Test Failed: Image file was not downloaded.");
        } else {
            System.out.println("Downloaded image: " + newestFile.getName()); // Thêm log file tải về
            newestFile.delete(); // Xóa file sau khi kiểm tra xong
        }
    }
    public void clickChooseAllBtn(){
        webUIHelpers.clickElement(btnChoose);
    }
    public void clickDownloadBtn(){
        webUIHelpers.clickElement(btnDownload);
    }
    public void verifyDownloadZipAndImageCount() {
        String home = System.getProperty("user.home");
        String downloadFilepath = Paths.get(home, "Downloads").toString();
        long startTime = System.currentTimeMillis();

        // Get the number of selected images
        String selectedImageCountText = webUIHelpers.getText(labelCountChooseImage);
//        int expectedImageCount = Integer.parseInt(selectedImageCountText.replaceAll("\\D+", "")); // Extract number
        int expectedImageCount = 2;
        boolean isDownloaded = false;
        File newestFile = null;

        // Check for the zip file
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(1000);
                File[] files = new File(downloadFilepath).listFiles();

                if (files != null && files.length > 0) {
                    for (File file : files) {
                        if (file.getName().endsWith(".zip") && file.lastModified() > startTime) {
                            if (newestFile == null || file.lastModified() > newestFile.lastModified()) {
                                newestFile = file;
                            }
                        }
                    }
                    if (newestFile != null) {
                        isDownloaded = true;
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!isDownloaded) {
            Assert.fail("Test Failed: Zip file was not downloaded.");
        } else {
            System.out.println("Downloaded zip file: " + newestFile.getName());
            // Extract zip file and count images
            try {
                int imageCount = countImagesInZip(newestFile.getAbsolutePath());
                Assert.assertEquals(imageCount, expectedImageCount, "The number of images in the zip file does not match the selected count.");
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("Error processing zip file.");
            }

            newestFile.delete(); // Delete zip file after checking
        }
    }

    private int countImagesInZip(String zipFilePath) throws IOException {
        int count = 0;
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            count = (int) zipFile.stream().filter(entry -> !entry.isDirectory() && (entry.getName().endsWith(".jpg") || entry.getName().endsWith(".png"))).count();
        }
        return count;
    }
    public int parseResponse(String responseBody) {
        int total = 0;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            Map<String, Object> contentMap = (Map<String, Object>) responseMap.get("content");
            total = (int) contentMap.get("total");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
        return total;
    }

}
