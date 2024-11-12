package pages;

import bases.WebUIHelpers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v123.network.Network;
import org.openqa.selenium.devtools.v123.network.model.RequestId;
import org.openqa.selenium.devtools.v123.network.model.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.ManagementPages.RolePage;
import pages.ManagementPages.VehiclePage;

import java.time.Duration;
import java.util.*;

public class MonitorPage {
    public MonitorPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
        this.logoutPage = new LogoutPage(driver);
    }
    private final WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private LoginPage loginPage;
    private LogoutPage logoutPage;

    //element in monitor page
    private By dropdownVehicleType = By.id("chooseSearchMonitorType");
    private By dropdownStatus = By.className("overSelect");
    private By tbSearch = By.id("textSearchOrgAndDevice");
    private By listResults = By.id("listSearchOrgAndDevice");
    public class PopupInfo {
        public static final By infoPlate = By.id("info-plate");
        public static final By infoTransport = By.id("info-transport");
        public static final By infoDrive = By.id("info-drive");
        public static final By infoPhone = By.id("info-phone");
        public static final By infoTimeTracking = By.id("info-timeTracking");
        public static final By infoSpeed = By.id("info-speed");
        public static final By infoGeocoding = By.id("info-geocoding");
        public static final By infoStatus = By.id("info-status");
        public static final By infoAcc = By.id("info-acc");
        public static final By infoWave = By.id("info-wave");
        public static final By infoSatellite = By.id("info-vetinh");
        public static final By infoDistance = By.id("info-distance");
        public static final By infoDistancePerDay = By.id("info-distancePerDay");
        public static final By infoEngine = By.id("info-engine");
        public static final By infoAir = By.id("info-air");
        public static final By infoDoor = By.id("info-door");
        public static final By infoWarning = By.id("info-canhbao");
        public static final By btnCam = By.id("iconInfoViewCamOnline");
        public static final By btnVid = By.id("iconInfoDirecHistoryVideo");
        public static final By btnImage = By.id("iconInfoDirecHistoryImage");
        public static final By btnRoute = By.id("iconInfoDirecHistoryTrack");
        public static final By btnDetail = By.cssSelector("div[data-target='#detailInfoVehicleModal']");
        public static final By btnConfig = By.cssSelector("div[onclick='setConfigDevice()']");


    }
    private By popupDetailInfo = By.id("detailInfoVehicleModal");
    private static final Map<String, String> valueMap = new HashMap<>();

    static {
        valueMap.put("Di chuyển", "run");
        valueMap.put("Dừng", "stop");
        valueMap.put("Đỗ", "park");
        valueMap.put("Quá tốc độ", "overspeed");
        valueMap.put("Mất kết nối", "offline");
        valueMap.put("Mất GPS", "badgps");
    }
    private static final Map<String, String> vehicleTpeMap = new HashMap<>();

    static {
        vehicleTpeMap.put("Máy kéo", "131");
        vehicleTpeMap.put("Máy lu", "134");
        vehicleTpeMap.put("Máy ủi", "133");
        vehicleTpeMap.put("Phương tiện khác", "8");
        vehicleTpeMap.put("Thuyền", "5");
        vehicleTpeMap.put("Xe buýt", "1");
    }

    //search function
    public void searchRelativeValue(){
        String searchKey = "Công ty test";
        String expectText = searchKey.toLowerCase();
        webUIHelpers.setText(tbSearch, searchKey);//type searchkey
        webUIHelpers.sleep(3000);

        //Checks returned value matches the searchkey
        WebElement ulElement = driver.findElement(listResults); //find ul in listResults
        List<WebElement> liElements = ulElement.findElements(By.tagName("li")); //find li in ul
        boolean isFound = false;
        for (WebElement li : liElements) {
            WebElement aElement = li.findElement(By.tagName("a"));//get text in a
            String actualText = aElement.getText().trim().toLowerCase();
            if (actualText.contains(expectText)) {
                isFound = true;
                break;
            }
        }
        Assert.assertTrue(isFound, "Chuỗi '" + searchKey + "' không xuất hiện trong danh sách.");
    }
    public void searchAbsoluteValue(String searchKey, By expectSpan){
        webUIHelpers.setText(tbSearch, searchKey);//type searchkey
        webUIHelpers.sleep(3000);

        //Checks returned value matches the searchkey
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

        //check ele value after click
        webUIHelpers.waitAndClick(listResults);//click result
        webUIHelpers.sleep(3000);
        WebElement spanElement = driver.findElement(expectSpan);
        String backgroundColorAfter = spanElement.getCssValue("background-color");//get background color after click
        Assert.assertEquals(backgroundColorAfter, "rgba(238, 0, 51, 0.45)", "Màu nền sau khi focus không đúng.");
    }

    public void searchCompany(){
        By spanElement = By.id("item-tree-c382f87d-c7a3-4901-8cef-aa9f75a2c125");
        searchAbsoluteValue("Công ty test 15/3", spanElement);
    }
    public void searchVehiclePlate(){
        By spanElement = By.id("item-tree-ad85c422-69d5-443f-8caf-02a3283ed4e2");
        searchAbsoluteValue("29A-17286", spanElement);
    }

    public static List<String> getVehicleTypes(String responseBody) {
        List<String> vehicleTypes = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(responseBody);

            JSONObject content = (JSONObject) jsonObject.get("content");

            JSONArray vehicles = (JSONArray) content.get("vehicles");

            for (Object obj : vehicles) {
                JSONObject vehicle = (JSONObject) obj;
                String vehicleType = (String) vehicle.get("vehicle_type");
                vehicleTypes.add(vehicleType);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return vehicleTypes;
    }
    public void searchByVehicleType(){
        By btnExpand = By.id("show-device-ffe633ce-520e-4c99-a44a-3f8ec6cacee9");
        webUIHelpers.selectElement(dropdownVehicleType, "Phương tiện khác");
        webUIHelpers.sleep(3000);

        DevTools devTools = ((ChromeDriver)driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

//        devTools.addListener(Network.requestWillBeSent(), requestComsumer -> {
//            Request request = requestComsumer.getRequest();
//        });

        final RequestId[] requestId = new RequestId[1];
        final List<String> vehicleTypes = new ArrayList<>();
        devTools.addListener(Network.responseReceived(), responseComsumer -> {
            Response response = responseComsumer.getResponse();
            requestId[0] = responseComsumer.getRequestId();
            if (response.getUrl().equals("https://vtracking.innoway.vn/portDataWithParamAndProjectId")){
                System.out.println(response.getStatus() + " " + response.getUrl());
                String responseBody = devTools.send(Network.getResponseBody(requestId[0])).getBody();
                System.out.println(responseBody);
                vehicleTypes.addAll(getVehicleTypes(responseBody));
                for (String type : vehicleTypes){
                    System.out.println("Vehicle type: " + type);
                }
            }
        });
        webUIHelpers.clickElement(btnExpand);
        webUIHelpers.sleep(500);
        Assert.assertTrue(vehicleTypes.contains("Phương tiện khác"));
    }
    public static List<String> getStatus(String responseBody) {
        List<String> vehicleStatus = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(responseBody);

            JSONObject content = (JSONObject) jsonObject.get("content");
            JSONArray vehicles = (JSONArray) content.get("vehicles");

            for (Object obj : vehicles) {
                JSONObject vehicle = (JSONObject) obj;
                JSONArray attributes = (JSONArray) vehicle.get("attributes");

                for (Object attrObj : attributes) {
                    JSONObject attribute = (JSONObject) attrObj;
                    if ("status".equals(attribute.get("attribute_key"))) {
                        String status = (String) attribute.get("value");
                        vehicleStatus.add(status);
                        break;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return vehicleStatus;
    }
    public void searchByStatus(String statusValue, String company) {
        String onclickValue = valueMap.get(statusValue);
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        final List<String> vehicleStatus = new ArrayList<>();
        final RequestId[] requestId = new RequestId[1];

        // Lắng nghe phản hồi
        devTools.addListener(Network.responseReceived(), responseConsumer -> {
            Response response = responseConsumer.getResponse();
            requestId[0] = responseConsumer.getRequestId();

            // Kiểm tra URL và xử lý phản hồi
            if ("https://vtracking.innoway.vn/portDataWithParamAndProjectId".equals(response.getUrl())) {
                System.out.println(response.getStatus() + " " + response.getUrl());

                // Đợi một chút để đảm bảo dữ liệu phản hồi đã được lưu trữ
                webUIHelpers.sleep(1000); // Tăng thời gian nếu cần

                try {
                    // Lấy dữ liệu phản hồi
                    String responseBody = devTools.send(Network.getResponseBody(requestId[0])).getBody();
                    System.out.println(responseBody);

                    // Phân tích dữ liệu JSON
                    vehicleStatus.addAll(getStatus(responseBody));
                    for (String status : vehicleStatus) {
                        System.out.println("Vehicle status: " + status);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Kích hoạt lọc và chờ phản hồi
        String xpathExpression = "//div[@onclick=\"checkedFilterStatusVehicle('" + onclickValue + "')\"]";
        webUIHelpers.clickElement(dropdownStatus);
        webUIHelpers.clickElement(By.xpath(xpathExpression));

        String xpathCompany = "//span[text()='" + company + "']/ancestor::div[@class='form-group']/descendant::label";
        webUIHelpers.clickElement(By.xpath(xpathCompany));

        // Đợi cho đến khi điều kiện được thỏa mãn
        webUIHelpers.waitFor(() -> !vehicleStatus.isEmpty(), 10000); // Tăng thời gian nếu cần

        Assert.assertTrue(vehicleStatus.contains(onclickValue));
    }

    public void  searchByMultiConditions(String statusValue, String vehicleTypeValue, String company){
        String onclickValue = valueMap.get(statusValue);
        String onclickVehicleTypeValue = vehicleTpeMap.get(vehicleTypeValue);
        webUIHelpers.selectElement(dropdownVehicleType, vehicleTypeValue);

        DevTools devTools = ((ChromeDriver)driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        final List<String> vehicleStatus = new ArrayList<>();
        final List<String> vehicleTypes = new ArrayList<>();
        final RequestId[] requestId = new RequestId[1];

        devTools.addListener(Network.responseReceived(), responseComsumer -> {
            Response response = responseComsumer.getResponse();
            requestId[0] = responseComsumer.getRequestId();
            if (response.getUrl().equals("https://vtracking.innoway.vn/portDataWithParamAndProjectId")){ // anhntl52 update getUrl tương thích với từng status khi truyền vào ở UpCodetest -> done
                System.out.println(response.getStatus() + " " + response.getUrl());
                String responseBody = devTools.send(Network.getResponseBody(requestId[0])).getBody();
//                System.out.println(responseBody);

                vehicleStatus.addAll(getStatus(responseBody));
                vehicleTypes.addAll(getVehicleTypes(responseBody));
                for (String type : vehicleTypes){
                    System.out.println("Vehicle type: " + type);
                }
                for (String status : vehicleStatus){
                    System.out.println("Vehicle status: " + status);
                }
            }
        });
        // Kích hoạt lọc và chờ phản hồi
        String xpathExpression = "//div[@onclick=\"checkedFilterStatusVehicle('" + onclickValue + "')\"]";
        webUIHelpers.clickElement(dropdownStatus);
        webUIHelpers.clickElement(By.xpath(xpathExpression));

        String xpathCompany = "//span[text()='" + company + "']/ancestor::div[@class='form-group']/descendant::label";
        webUIHelpers.clickElement(By.xpath(xpathCompany));

        // Đợi cho đến khi điều kiện được thỏa mãn
        webUIHelpers.sleep(9000);

        Assert.assertTrue(vehicleStatus.contains(onclickValue));
        Assert.assertTrue(vehicleTypes.contains(onclickVehicleTypeValue));
    }

    public void verifyInfoVehicleWithCam(String plate){
        webUIHelpers.setText(tbSearch, plate);
        webUIHelpers.sleep(5000);
        webUIHelpers.waitAndClick(listResults);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement btnLicense = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("item-tree-ad85c422-69d5-443f-8caf-02a3283ed4e2")));
        webUIHelpers.sleep(3000); //sleep 3s để cây công ty loading xong
        btnLicense.click();
        WebElement infoPlate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("info-plate"))); //lệnh chờ đến khi tìm được id info-plate
        WebElement infoTransport = driver.findElement(PopupInfo.infoTransport);
        WebElement infoDrive = driver.findElement(PopupInfo.infoDrive);
        WebElement infoPhone = driver.findElement(PopupInfo.infoPhone);
        WebElement infoTimeTracking = driver.findElement(PopupInfo.infoTimeTracking);
        WebElement infoSpeed = driver.findElement(PopupInfo.infoSpeed);
        WebElement infoGeocoding = driver.findElement(PopupInfo.infoGeocoding);
        WebElement infoStatus = driver.findElement(PopupInfo.infoStatus);
        WebElement infoAcc = driver.findElement(PopupInfo.infoAcc);
        WebElement infoWave = driver.findElement(PopupInfo.infoWave);
        WebElement infoSatellite = driver.findElement(PopupInfo.infoSatellite);
        WebElement infoDistance = driver.findElement(PopupInfo.infoDistance);
        WebElement infoDistancePerDay = driver.findElement(PopupInfo.infoDistancePerDay);
        WebElement infoEngine = driver.findElement(PopupInfo.infoEngine);
        WebElement infoAir = driver.findElement(PopupInfo.infoAir);
        WebElement infoDoor = driver.findElement(PopupInfo.infoDoor);
        WebElement infoWarning = driver.findElement(PopupInfo.infoWarning);
        WebElement btnCam = driver.findElement(PopupInfo.btnCam);
        WebElement btnVid = driver.findElement(PopupInfo.btnVid);
        WebElement btnImage = driver.findElement(PopupInfo.btnImage);
        WebElement btnRoute = driver.findElement(PopupInfo.btnRoute);
        WebElement btnDetail = driver.findElement(PopupInfo.btnDetail);
        WebElement btnConfig = driver.findElement(PopupInfo.btnConfig);
        assert infoPlate.isDisplayed() &&
                infoTransport.isDisplayed() &&
                infoDrive.isDisplayed() &&
                infoPhone.isDisplayed() &&
                infoTimeTracking.isDisplayed() &&
                infoSpeed.isDisplayed() &&
                infoGeocoding.isDisplayed() &&
                infoStatus.isDisplayed() &&
                infoAcc.isDisplayed() &&
                infoWave.isDisplayed() &&
                infoSatellite.isDisplayed() &&
                infoDistance.isDisplayed() &&
                infoDistancePerDay.isDisplayed() &&
                infoEngine.isDisplayed() &&
                infoAir.isDisplayed() &&
                infoDoor.isDisplayed() &&
                infoWarning.isDisplayed() &&
                btnCam.isDisplayed() &&
                btnDetail.isDisplayed() &&
                btnImage.isDisplayed() &&
                btnRoute.isDisplayed() &&
                btnVid.isDisplayed() &&
                btnConfig.isDisplayed();
    }

    public void verifyInfoVehicleWithoutCam(){
        webUIHelpers.setText(tbSearch, "26A-07118");
        webUIHelpers.sleep(3000);
        webUIHelpers.waitAndClick(listResults);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement btnLicense = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("item-tree-f1b845b2-31d2-4d58-b2fa-a06fe9841eb3")));
        webUIHelpers.sleep(3000); //sleep 3s để cây công ty loading xong
        btnLicense.click();
        WebElement infoPlate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("info-plate"))); //lệnh chờ đến khi tìm được id info-plate
        WebElement infoTransport = driver.findElement(PopupInfo.infoTransport);
        WebElement infoDrive = driver.findElement(PopupInfo.infoDrive);
        WebElement infoPhone = driver.findElement(PopupInfo.infoPhone);
        WebElement infoTimeTracking = driver.findElement(PopupInfo.infoTimeTracking);
        WebElement infoSpeed = driver.findElement(PopupInfo.infoSpeed);
        WebElement infoGeocoding = driver.findElement(PopupInfo.infoGeocoding);
        WebElement infoStatus = driver.findElement(PopupInfo.infoStatus);
        WebElement infoAcc = driver.findElement(PopupInfo.infoAcc);
        WebElement infoWave = driver.findElement(PopupInfo.infoWave);
        WebElement infoSatellite = driver.findElement(PopupInfo.infoSatellite);
        WebElement infoDistance = driver.findElement(PopupInfo.infoDistance);
        WebElement infoDistancePerDay = driver.findElement(PopupInfo.infoDistancePerDay);
        WebElement infoEngine = driver.findElement(PopupInfo.infoEngine);
        WebElement infoAir = driver.findElement(PopupInfo.infoAir);
        WebElement infoDoor = driver.findElement(PopupInfo.infoDoor);
        WebElement infoWarning = driver.findElement(PopupInfo.infoWarning);
        WebElement btnCam = driver.findElement(PopupInfo.btnCam);
        WebElement btnVid = driver.findElement(PopupInfo.btnVid);
        WebElement btnImage = driver.findElement(PopupInfo.btnImage);
        WebElement btnRoute = driver.findElement(PopupInfo.btnRoute);
        WebElement btnDetail = driver.findElement(PopupInfo.btnDetail);
        WebElement btnConfig = driver.findElement(PopupInfo.btnConfig);
        assert infoPlate.isDisplayed() &&
                infoTransport.isDisplayed() &&
                infoDrive.isDisplayed() &&
                infoPhone.isDisplayed() &&
                infoTimeTracking.isDisplayed() &&
                infoSpeed.isDisplayed() &&
                infoGeocoding.isDisplayed() &&
                infoStatus.isDisplayed() &&
                infoAcc.isDisplayed() &&
                infoWave.isDisplayed() &&
                infoSatellite.isDisplayed() &&
                infoDistance.isDisplayed() &&
                infoDistancePerDay.isDisplayed() &&
                infoEngine.isDisplayed() &&
                infoAir.isDisplayed() &&
                infoDoor.isDisplayed() &&
                infoWarning.isDisplayed() &&
                !btnCam.isDisplayed() &&
                btnDetail.isDisplayed() &&
                !btnImage.isDisplayed() &&
                btnRoute.isDisplayed() &&
                !btnVid.isDisplayed() &&
                btnConfig.isDisplayed();
    }
    public void verifyPopupDetail(String plate, String id) {
        webUIHelpers.setText(tbSearch, plate);
        webUIHelpers.sleep(3000);
        webUIHelpers.waitAndClick(listResults);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement btnLicense = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        webUIHelpers.sleep(3000); //sleep 3s chờ btn loading xong
        btnLicense.click();
        webUIHelpers.sleep(3000);
        webUIHelpers.clickElement(PopupInfo.btnDetail);
        webUIHelpers.sleep(2000);
        WebElement popupDetail = driver.findElement(popupDetailInfo);
        Assert.assertTrue(popupDetail.isDisplayed());
    }

    public void verifyHeaderMenu(List<String> expectedMenuItems) {
        List<WebElement> menuItems = driver.findElements(By.cssSelector(".header_tab a"));

        Set<String> actualMenuItems = new HashSet<>();
        Set<String> expectedMenuItemSet = new HashSet<>(expectedMenuItems);

        for (WebElement menuItem : menuItems) {
            String menuItemText = menuItem.findElement(By.tagName("span")).getText().trim();
            actualMenuItems.add(menuItemText);
        }

        Set<String> unexpectedMenuItems = new HashSet<>(actualMenuItems);
        unexpectedMenuItems.removeAll(expectedMenuItemSet);

        if (!unexpectedMenuItems.isEmpty()) {
            System.out.println("Unexpected menu item(s) found:");
            for (String menuItem : unexpectedMenuItems) {
                System.out.println(" - " + menuItem);
            }
        }

        Set<String> missingMenuItems = new HashSet<>(expectedMenuItemSet);
        missingMenuItems.removeAll(actualMenuItems);

        if (!missingMenuItems.isEmpty()) {
            System.out.println("Missing menu item(s):");
            for (String menuItem : missingMenuItems) {
                System.out.println(" - " + menuItem);
            }
        }

        if (!unexpectedMenuItems.isEmpty() || !missingMenuItems.isEmpty()) {
            throw new AssertionError("Mismatch between expected and actual menu items.");
        }
    }



}
