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
import bases.ExcelHelpers;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class RoutePage {
    public RoutePage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
        this.historyPage = new HistoryPage(driver);
        this.actions = new Actions(driver);
    }
    private Actions actions;
    private final WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private LoginPage loginPage;
    private HistoryPage historyPage;

    //Các element trong search
    private By dropdownPlate = By.id("inputPlateVal");
    private By date_timepicker_start = By.id("date_timepicker_start");
    private By date_timepicker_end = By.id("date_timepicker_end");
    private By historyBtn = By.id("viewHistoryDataBtn");

    //element trong popup plate
    private By tbSearch = By.id("textSearchOrgAndDevice");
    private By listResults = By.id("listSearchOrgAndDevice");
    private By btnSave = By.id("buttonChooseDeviceHistory");

    //element in route history
    private By seekBar = By.id("seek");
    private By dataTable = By.id("table-history-view");
    private By summaryInfo = By.className("hgm_summaryInfo");
    private By btnExport = By.cssSelector("[onclick='exportHistoryMap()']");

    //import data test
    private String filePath = "dataTestvTracking.xlsx";
    private String sheetName = "RouteHistory";

    public String getPlate(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"plate", index);
    }
    public String getCompany(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"company", index);
    }
    public String getId(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"id", index);
    }

    //search function
    public void searchAbsoluteValue(String searchKey) {
        webUIHelpers.clickElement(dropdownPlate);
        webUIHelpers.sleep(3000);
        webUIHelpers.setText(tbSearch, searchKey);//type searchkey
        webUIHelpers.sleep(5000);

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
//        WebElement spanElement = driver.findElement(expectSpan);
//        String backgroundColorAfter = spanElement.getCssValue("background-color");//get background color after click
//        Assert.assertEquals(backgroundColorAfter, "rgba(238, 0, 51, 0.45)", "Màu nền sau khi focus không đúng.");
    }

    public void searchCompany(String company) {
        webUIHelpers.clickElement(dropdownPlate);
        webUIHelpers.sleep(3000);
        webUIHelpers.setText(tbSearch, company);
        webUIHelpers.sleep(5000);

        //Checks returned value matches the searchkey
        WebElement ulElement = driver.findElement(listResults); //find ul in listResults
        List<WebElement> liElements = ulElement.findElements(By.tagName("li")); //find li in ul
        boolean isFound = false;
        for (WebElement li : liElements) {
            WebElement aElement = li.findElement(By.tagName("a"));//get text in a
            String content = aElement.getText();
            if (content.equals(company)) {
                isFound = true;
                break;
            }
        }
        Assert.assertTrue(isFound, "Chuỗi '" + this.getCompany(1) + "' không xuất hiện trong danh sách.");
    }

    public void searchVehiclePlate() {
        searchAbsoluteValue(this.getPlate(9));
    }
    //update 30/7
    private static final double SPEED_THRESHOLD = 150.0; // km/h

    public static double calculateDistance(List<Map<String, Object>> logs) {
        double totalDistance = 0;
        List<Map<String, Object>> validLogs = filterLogs(logs);

        for (int i = 0; i < validLogs.size() - 1; i++) {
            Map<String, Object> startLog = validLogs.get(i);
            Map<String, Object> endLog = validLogs.get(i + 1);

            double startLatitude = getDoubleValue(startLog.get("latitude"));
            double startLongitude = getDoubleValue(startLog.get("longitude"));
            double endLatitude = getDoubleValue(endLog.get("latitude"));
            double endLongitude = getDoubleValue(endLog.get("longitude"));

            double distance = haversine(startLatitude, startLongitude, endLatitude, endLongitude);
            totalDistance += distance;
        }

        return totalDistance; // Convert to kilometers
    }

    public static List<Map<String, Object>> filterLogs(List<Map<String, Object>> logs) {
        List<Map<String, Object>> filteredLogs = new ArrayList<>();
        boolean isStopped = false;
        boolean isParked = false;

        for (Map<String, Object> log : logs) {
            String status = log.get("status").toString().toLowerCase();

            if ("badgps".equals(status) || "offline".equals(status)) {
                continue; // Bỏ qua các bản ghi có trạng thái badGPS hoặc offline
            }

            if ("stop".equals(status)) {
                if (!isStopped) {
                    filteredLogs.add(log); // Thêm bản ghi vào danh sách lọc nếu trạng thái là "stop" và chưa có bản ghi "stop" trước đó
                    isStopped = true;
                    isParked = false;
                }
            } else if ("park".equals(status)) {
                if (!isParked) {
                    filteredLogs.add(log); // Thêm bản ghi vào danh sách lọc nếu trạng thái là "park" và chưa có bản ghi "park" trước đó
                    isStopped = false;
                    isParked = true;
                }
            } else {
                filteredLogs.add(log); // Thêm các bản ghi khác vào danh sách lọc
                isStopped = false;
                isParked = false;
            }
        }

        // Lọc khoảng cách và tốc độ không hợp lệ
        List<Map<String, Object>> finalFilteredLogs = new ArrayList<>();
        for (int i = 0; i < filteredLogs.size(); i++) {
            Map<String, Object> currentLog = filteredLogs.get(i);
            finalFilteredLogs.add(currentLog); // Thêm bản ghi hiện tại vào danh sách cuối cùng

            if (i < filteredLogs.size() - 1) {
                Map<String, Object> nextLog = filteredLogs.get(i + 1);
                double lat1 = getDoubleValue(currentLog.get("latitude"));
                double lon1 = getDoubleValue(currentLog.get("longitude"));
                double lat2 = getDoubleValue(nextLog.get("latitude"));
                double lon2 = getDoubleValue(nextLog.get("longitude"));

                if (Math.abs(lat2 - lat1) <= 0.1 && Math.abs(lon2 - lon1) <= 0.1) {
                    continue; // Nếu sự khác biệt về lat/lon nằm trong ngưỡng cho phép, thì tiếp tục với bản ghi tiếp theo
                } else {
                    double distance = haversine(lat1, lon1, lat2, lon2); // Tính khoảng cách giữa hai bản ghi
                    try {
                        String time1Str = currentLog.get("timestamp").toString();
                        String time2Str = nextLog.get("timestamp").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        Date time1 = sdf.parse(time1Str);
                        Date time2 = sdf.parse(time2Str);
                        double timeDiff = (time2.getTime() - time1.getTime()) / 3600000.0; // Tính khoảng thời gian giữa hai bản ghi (giờ)

                        double speed = distance / timeDiff; // Tính tốc độ
                        if (speed <= SPEED_THRESHOLD) {
                            continue; // Nếu tốc độ nằm trong ngưỡng cho phép, thì tiếp tục với bản ghi tiếp theo
                        }
                    } catch (Exception e) {
                        e.printStackTrace(); // Xử lý lỗi nếu có khi phân tích thời gian
                    }
                }

                finalFilteredLogs.add(nextLog); // Thêm bản ghi tiếp theo vào danh sách cuối cùng
                i++; // Bỏ qua bản ghi tiếp theo vì nó đã được xử lý
            }
        }

        return finalFilteredLogs; // Trả về danh sách các bản ghi đã lọc
    }

    private static double getDoubleValue(Object value) {
        if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value instanceof Double) {
            return (Double) value;
        } else {
            throw new IllegalArgumentException("Unsupported type for latitude/longitude");
        }
    }

    public static List<Map<String, Object>> filterLogs1(List<Map<String, Object>> logs) {
        List<Map<String, Object>> filteredLogs = new ArrayList<>();
        boolean isParked = false;
        boolean isStopped = false;

        for (Map<String, Object> log : logs) {
            String status = log.get("status").toString().toLowerCase();

            if ("badgps".equals(status) || "offline".equals(status)) {
                continue;
            }

            if ("stop".equals(status)) {
                if (!isStopped) {
                    filteredLogs.add(log);
                    isStopped = true;
                }
            } if ("park".equals(status)) {
                if (!isParked) {
                    filteredLogs.add(log);
                    isParked = true;
                }
            } else {
                filteredLogs.add(log);
                isParked = false;
                isStopped = false;
            }
        }

        List<Map<String, Object>> finalFilteredLogs = new ArrayList<>();
        for (int i = 0; i < filteredLogs.size(); i++) {
            Map<String, Object> currentLog = filteredLogs.get(i);
            finalFilteredLogs.add(currentLog);

            if (i < filteredLogs.size() - 1) {
                Map<String, Object> nextLog = filteredLogs.get(i + 1);
                double lat1 = (Double) currentLog.get("latitude");
                double lon1 = (Double) currentLog.get("longitude");
                double lat2 = (Double) nextLog.get("latitude");
                double lon2 = (Double) nextLog.get("longitude");

                if (Math.abs(lat2 - lat1) > 0.1 || Math.abs(lon2 - lon1) > 0.1) {
                    finalFilteredLogs.add(nextLog); // Ensure we keep both points if the segment between them is invalid
                    i++; // Skip the next point as it has already been added
                }
            }
        }

        return finalFilteredLogs;
    }

    public static double calculateDistance0(List<Map<String, Object>> logs) {
        double totalDistance = 0;
        boolean isStopped = false;
        boolean isParked = false;
        double lastStopLatitude = 0;
        double lastStopLongitude = 0;
        double lastParkLatitude = 0;
        double lastParkLongitude = 0;

        for (int i = 0; i < logs.size() - 1; i++) {
            Map<String, Object> start = logs.get(i);
            Map<String, Object> end = logs.get(i + 1);

            if (start == null || end == null) {
                continue;
            }

            if (!start.containsKey("latitude") || !start.containsKey("longitude") || !start.containsKey("status")) {
                continue;
            }

            String startStatus = (String) start.get("status");
            String endStatus = (String) end.get("status");

            // Skip logs with badGPS or offline status
            if (startStatus.equals("badGPS") || endStatus.equals("badGPS") || startStatus.equals("offline") || endStatus.equals("offline")) {
                continue;
            }

            double startLatitude = ((Number) start.get("latitude")).doubleValue();
            double startLongitude = ((Number) start.get("longitude")).doubleValue();
            double endLatitude = ((Number) end.get("latitude")).doubleValue();
            double endLongitude = ((Number) end.get("longitude")).doubleValue();

            // Skip logs where the difference in latitude or longitude is greater than 0.1
            if (Math.abs(endLatitude - startLatitude) > 0.1 || Math.abs(endLongitude - startLongitude) > 0.1) {
                continue;
            }

            // Handle stop status
            if (startStatus.equals("stop")) {
                if (!isStopped) {
                    lastStopLatitude = startLatitude;
                    lastStopLongitude = startLongitude;
                    isStopped = true;
                }
            } else {
                if (isStopped) {
                    double distance = haversine(lastStopLatitude, lastStopLongitude, startLatitude, startLongitude);
                    totalDistance += distance;
                    isStopped = false;
                }
            }

            // Handle park status
            if (startStatus.equals("park")) {
                if (!isParked) {
                    lastParkLatitude = startLatitude;
                    lastParkLongitude = startLongitude;
                    isParked = true;
                }
            } else {
                if (isParked) {
                    double distance = haversine(lastParkLatitude, lastParkLongitude, startLatitude, startLongitude);
                    totalDistance += distance;
                    isParked = false;
                }
            }

            // Calculate distance for normal travel
            if (!isStopped && !isParked) {
                double distance = haversine(startLatitude, startLongitude, endLatitude, endLongitude);
                totalDistance += distance;
            }
        }

        // Ensure the last point is handled if the loop exits while in stop/park state
        Map<String, Object> last = logs.get(logs.size() - 1);
        if (last != null && last.containsKey("latitude") && last.containsKey("longitude") && !last.get("status").equals("badGPS") && !last.get("status").equals("offline")) {
            double endLatitude = ((Number) last.get("latitude")).doubleValue();
            double endLongitude = ((Number) last.get("longitude")).doubleValue();
            if (isStopped) {
                double distance = haversine(lastStopLatitude, lastStopLongitude, endLatitude, endLongitude);
                totalDistance += distance;
            }
            if (isParked) {
                double distance = haversine(lastParkLatitude, lastParkLongitude, endLatitude, endLongitude);
                totalDistance += distance;
            }
        }

        return totalDistance;
    }

    public static double calculateDistance1(List<Map<String, Object>> logs) {
        double totalDistance = 0;
        boolean isStopped = false;
        double lastLatitude = 0;
        double lastLongitude = 0;

        for (int i = 0; i < logs.size() - 1; i++) {
            Map<String, Object> start = logs.get(i);
            Map<String, Object> end = logs.get(i + 1);

            if (start == null || end == null) {
                continue;
            }

            if (!start.containsKey("latitude") || !start.containsKey("longitude") || !start.containsKey("status")) {
                continue;
            }

            String startStatus = (String) start.get("status");
            String endStatus = (String) end.get("status");

            // Skip logs with badGPS or offline status
            if (startStatus.equals("badGPS") || endStatus.equals("badGPS") || startStatus.equals("offline") || endStatus.equals("offline")) {
                continue;
            }

            double startLatitude = ((Number) start.get("latitude")).doubleValue();
            double startLongitude = ((Number) start.get("longitude")).doubleValue();
            double endLatitude = ((Number) end.get("latitude")).doubleValue();
            double endLongitude = ((Number) end.get("longitude")).doubleValue();

            // Skip logs where the difference in latitude or longitude is greater than 0.1
            if (Math.abs(endLatitude - startLatitude) > 0.1 || Math.abs(endLongitude - startLongitude) > 0.1) {
                continue;
            }

            // Handle stop/park status
            if (startStatus.equals("stop") || startStatus.equals("park")) {
                if (!isStopped) {
                    lastLatitude = startLatitude;
                    lastLongitude = startLongitude;
                    isStopped = true;
                }
            } else {
                if (isStopped) {
                    double distance = haversine(lastLatitude, lastLongitude, startLatitude, startLongitude);
                    totalDistance += distance;
                    isStopped = false;
                }
                double distance = haversine(startLatitude, startLongitude, endLatitude, endLongitude);
                totalDistance += distance;
            }

            // Handle transition from stop/park to running state
            if (isStopped && !(endStatus.equals("stop") || endStatus.equals("park"))) {
                double distance = haversine(lastLatitude, lastLongitude, endLatitude, endLongitude);
                totalDistance += distance;
                isStopped = false;
            }
        }

        // Ensure the last point is handled if the loop exits while in stop/park state
        if (isStopped) {
            Map<String, Object> last = logs.get(logs.size() - 1);
            if (last != null && last.containsKey("latitude") && last.containsKey("longitude") && !last.get("status").equals("badGPS") && !last.get("status").equals("offline")) {
                double endLatitude = ((Number) last.get("latitude")).doubleValue();
                double endLongitude = ((Number) last.get("longitude")).doubleValue();
                double distance = haversine(lastLatitude, lastLongitude, endLatitude, endLongitude);
                totalDistance += distance;
            }
        }

        return totalDistance;
    }
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // Bán kính trái đất
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private int calculateStopCount(List<Map<String, Object>> logs) {
        int stopCount = 0;
        for (Map<String, Object> log : logs) {
            String status = (String) log.get("status");
            if ("stop".equals(status) || "park".equals(status)) {
                stopCount++;
            }
        }
        return stopCount;
    }
    private static String formatMilliseconds(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        return hours + "h " + minutes + "m " + seconds + "s";
    }

    private long calculateTime(List<Map<String, Object>> logs, String status) {
        long timestamps = 0;
        List<Integer> calculatedDataIndexes = new ArrayList<>();
        for (int i = 0; i < logs.size(); i++) {
            if (status.equals(logs.get(i).get("status"))) {
                calculatedDataIndexes.add(i);
            }
        }
        for (int i = 0; i < calculatedDataIndexes.size(); i++) {
            int currentIndex = calculatedDataIndexes.get(i);
            int nextIndex = i +  1 < calculatedDataIndexes.size() ? calculatedDataIndexes.get(i + 1) : -1;
            if(nextIndex - currentIndex != 1){
                if(nextIndex == -1){
                    nextIndex = currentIndex + 1 >= logs.size() ? currentIndex : currentIndex + 1;
                }
                else{
                    nextIndex = currentIndex + 1;
                }
            }
            long currentTimestamp = ((Number) logs.get(currentIndex).get("ts")).longValue();
            long nextTimestamp = ((Number) logs.get(nextIndex).get("ts")).longValue();
            timestamps += Math.abs(nextTimestamp - currentTimestamp);
        }
        return timestamps;
    }

    public void routeHistory(String id, String startTime, String endTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("tab-loading")));
        WebElement btnLicense = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        btnLicense.click();
        webUIHelpers.clickElement(btnSave);
        if (!startTime.isEmpty()) {
            webUIHelpers.clearAndSetText(date_timepicker_start, startTime);
        }
        webUIHelpers.clickElement(date_timepicker_start);
        if (!endTime.isEmpty()) {
            webUIHelpers.clearAndSetText(date_timepicker_end, endTime);
        }
        webUIHelpers.clickElement(date_timepicker_end);
        actions.moveByOffset(400, 400).click().perform();

        webUIHelpers.sleep(3000);
        webUIHelpers.clickElement(historyBtn);
        webUIHelpers.sleep(15000);
    }
    public void verifyRouteHistory(){
        WebElement seekBarEle = driver.findElement(seekBar);
        WebElement tableData = driver.findElement(dataTable);
        WebElement infoSummary = driver.findElement(summaryInfo);
        WebElement exportBtn = driver.findElement(btnExport);
        Assert.assertTrue(seekBarEle.isDisplayed());
        Assert.assertTrue(tableData.isDisplayed());
        Assert.assertTrue(infoSummary.isDisplayed());
        Assert.assertTrue(exportBtn.isDisplayed());
    }
    public void routeOverview(String id, String startTime, String endTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("tab-loading")));
        WebElement btnLicense = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        webUIHelpers.sleep(5000);
        btnLicense.click();
        webUIHelpers.clickElement(btnSave);
        if (!startTime.isEmpty()) {
            webUIHelpers.clearAndSetText(date_timepicker_start, startTime);
        }
        webUIHelpers.clickElement(date_timepicker_start);
        if (!endTime.isEmpty()) {
            webUIHelpers.clearAndSetText(date_timepicker_end, endTime);
        }
        webUIHelpers.clickElement(date_timepicker_end);
        actions.moveByOffset(400, 400).click().perform();

        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        AtomicReference<String> actualDistance = new AtomicReference<>();
        AtomicInteger actualStopCount = new AtomicInteger();
        AtomicReference<String> actualRunTime = new AtomicReference<>();
        AtomicReference<String> actualStopTime = new AtomicReference<>();
        final RequestId[] requestId = new RequestId[1];

        devTools.addListener(Network.responseReceived(), responseConsumer -> {
            Response response = responseConsumer.getResponse();
            if (response.getUrl().equals("https://vtracking.innoway.vn/getHistoryTracking")) {
                requestId[0] = responseConsumer.getRequestId();
                System.out.println(response.getStatus() + " " + response.getUrl());
            }
        });

        devTools.addListener(Network.loadingFinished(), loadingFinished -> {
            if (requestId[0] != null) {
                String responseBody = devTools.send(Network.getResponseBody(requestId[0])).getBody();
                System.out.println("Response Body Length: " + responseBody.length());
                List<Map<String, Object>> logs = parseResponse(responseBody);
                List<Map<String, Object>> logsWithTimestamps = extractTimestamps(responseBody);

                double totalDistance = calculateDistance(logs);
                actualDistance.set(String.format("%.3f", totalDistance) + " Km");
                int stopCount = calculateStopCount(logs);
                actualStopCount.set(stopCount);
                long runTime = calculateTime(logsWithTimestamps, "run") + calculateTime(logsWithTimestamps, "overspeed");
                actualRunTime.set(formatMilliseconds(runTime));
                long stopTime = calculateTime(logsWithTimestamps, "stop") + calculateTime(logsWithTimestamps, "park");
                actualStopTime.set(formatMilliseconds(stopTime));
            }
        });

        webUIHelpers.sleep(3000);
        webUIHelpers.clickElement(historyBtn);
        webUIHelpers.sleep(15000);

        System.out.println("Total Distance: " + actualDistance.get());
        System.out.println("Stop Count: " + actualStopCount.get());
        System.out.println("Run Time: " + actualRunTime.get());
        System.out.println("Stop Time: " + actualStopTime.get());

        String expectDistance = webUIHelpers.getText(By.id("id-sum-total"));
        String expectStopCountStr = webUIHelpers.getText(By.id("countParkStop"));
        int expectStopCount = Integer.parseInt(expectStopCountStr);
        String expectRunTime = webUIHelpers.getText(By.id("timeCarRun"));
        String expectStopTime = webUIHelpers.getText(By.id("timeParkStop"));

        Assert.assertEquals(actualDistance.get(), expectDistance);
        Assert.assertEquals(actualStopCount.get(), expectStopCount);
        Assert.assertEquals(actualRunTime.get(), expectRunTime);
        Assert.assertEquals(actualStopTime.get(), expectStopTime);
    }

    private List<Map<String, Object>> parseResponse(String responseBody) {
        List<Map<String, Object>> logs = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            Map<String, Object> contentMap = (Map<String, Object>) responseMap.get("content");
            List<Map<String, Map<String, Object>>> logsList = (List<Map<String, Map<String, Object>>>) contentMap.get("logs");
            for (Map<String, Map<String, Object>> log : logsList) {
                if (log.containsKey("value")) {
                    logs.add(log.get("value"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
        return logs;
    }

    private List<Map<String, Object>> extractTimestamps(String responseBody) {
        List<Map<String, Object>> logsWithTimestamps = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            Map<String, Object> contentMap = (Map<String, Object>) responseMap.get("content");
            List<Map<String, Object>> logsList = (List<Map<String, Object>>) contentMap.get("logs");
            for (Map<String, Object> log : logsList) {
                Map<String, Object> valueMap = new HashMap<>((Map<String, Object>) log.get("value"));
                valueMap.put("ts", log.get("ts"));
                logsWithTimestamps.add(valueMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
        return logsWithTimestamps;
    }
    public void clickBtnExport(){
        webUIHelpers.clickElement(btnExport);
        webUIHelpers.sleep(3000);
    }

    public void verifyExportFileExcel() {
        String home = System.getProperty("user.home"); //update path tương thích từng máy
        String downloadFilepath = Paths.get(home, "Downloads").toString(); // anhntl52 xử lý downloadFilepath tương thích từng máy -> done
        long startTime = System.currentTimeMillis();
        this.clickBtnExport();
        boolean isDownloaded = false;
        File newestFile = null;

        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(1000);
                File[] files = new File(downloadFilepath).listFiles();
                if (files != null && files.length > 0) {
                    for (File file : files) {
                        if ((file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls")) && file.lastModified() > startTime) {
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
            Assert.fail("Test Failed: Excel file was not downloaded.");
        } else {
            if (newestFile != null && newestFile.exists()) {
                newestFile.delete();
            }
        }
    }
}
