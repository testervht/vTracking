package pages;


import bases.WebUIHelpers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v123.network.Network;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DashBoardPage {
    public DashBoardPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
    }

    private WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private LoginPage loginPage;
    // Cac element trong Tong quan
    private By tongSoPhuongTien = By.id("overview-total");
    private By dangHoatDong = By.id("overview-active");
    private By sapHetHan = By.id("overview-expiring");
    private By matTinHieu = By.id("overview-disconnect");
    private By viPhamTocDo = By.id("overview-speed");
    private By viPhamThoiGianLaiXe = By.id("overview-timeDriver");
    private By laiXe = By.id("overview-totalDriver");
    private By soKMTrungBinhNgay = By.id("overview-distanceInDay");

    //get tab Dashboard
    private By dashboardButton = By.cssSelector("a[href=\"/dashboard\"]");

    public void navigateToDashBoardPage() {
        webUIHelpers.clickElement(dashboardButton);
    }

    public void ThongTinTongQuan() throws Exception {
            // Lấy token tự động từ API
            String token = getToken("giangptt", "123456aA@", "CuUTtsmfjMBOKeMEpkAo", "Ga8lL0lMVFr6fJoudGgJR9upsXuIgGxz"); // Cập nhật thông tin đăng nhập

            // URL của API
            String apiUrl = "https://api.innoway.vn/api/attributes/vtracking/dashboard/stats?project_id=3207b5c0-96f8-46f2-9c38-9a08a825ab6c&startTime=1712682000000";

            // Tạo URL object
            URL url = new URL(apiUrl);

            // Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức yêu cầu
            connection.setRequestMethod("GET");

            // Thêm header xác thực với token tự động
            connection.setRequestProperty("Authorization", "Bearer " + token);

            // Lấy mã trạng thái HTTP
            int responseCode = connection.getResponseCode();
            System.out.println("Ma response:" + responseCode);

            // Đọc dữ liệu phản hồi
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null && responseCode == 200) {
                response.append(inputLine);
            }
            in.close();

            // Chuyển đổi dữ liệu phản hồi thành JSONObject
            JSONObject apiData = new JSONObject(response.toString());

            // In ra dữ liệu để kiểm tra
            System.out.println(apiData.toString(2)); // In ra JSON với định dạng đẹp

            // Lấy dữ liệu từ API
            String tongSoPhuongTienAPI = String.valueOf(apiData.getInt("total_vehicle"));
            String dangHoatDongAPI = String.valueOf(apiData.getInt("active_vehicle"));
            String sapHetHanAPI = String.valueOf(apiData.getInt("expiring_vehicles"));
            String matTinHieuAPI = String.valueOf(apiData.getInt("disconnection_count"));
            String viPhamTocDoAPI = String.valueOf(apiData.getInt("speed_violation_count"));
            String viPhamThoiGianLaiXeAPI = String.valueOf(apiData.getInt("driving_time_violation_count"));
            String laiXeAPI = String.valueOf(apiData.getInt("total_drivers"));
            String soKMTrungBinhAPI = String.valueOf(apiData.getInt("avg_km_per_day"));

            // Lấy dữ liệu từ giao diện web
            String tongSoPhuongTienWeb = webUIHelpers.getText(tongSoPhuongTien);
            String dangHoatDongWeb = webUIHelpers.getText(dangHoatDong);
            String sapHetHanWeb = webUIHelpers.getText(sapHetHan);
            String matTinHieuWeb = webUIHelpers.getText(matTinHieu);
            String viPhamTocDoWeb = webUIHelpers.getText(viPhamTocDo);
            String viPhamThoiGianLaiXeWeb = webUIHelpers.getText(viPhamThoiGianLaiXe);
            String laiXeWeb = webUIHelpers.getText(laiXe);
            String soKMTrungBinhWeb = webUIHelpers.getText(soKMTrungBinhNgay);

            // So sánh dữ liệu API và giao diện web
            Assert.assertTrue(Objects.equals(tongSoPhuongTienWeb, tongSoPhuongTienAPI) &&
                            Objects.equals(dangHoatDongWeb, dangHoatDongAPI) &&
                            Objects.equals(sapHetHanWeb, sapHetHanAPI) &&
                            Objects.equals(matTinHieuWeb, matTinHieuAPI) &&
                            Objects.equals(viPhamTocDoWeb, viPhamTocDoAPI) &&
                            Objects.equals(viPhamThoiGianLaiXeWeb, viPhamThoiGianLaiXeAPI) &&
                            Objects.equals(laiXeWeb, laiXeAPI) &&
                            Objects.equals(soKMTrungBinhWeb, soKMTrungBinhAPI),
                    "Du lieu khong trung khop voi API");
    }


    public void PhuongTienSapHetHanSung() throws Exception {
            // Lấy token tự động từ API
            String token = getToken("giangptt", "123456aA@", "CuUTtsmfjMBOKeMEpkAo", "Ga8lL0lMVFr6fJoudGgJR9upsXuIgGxz"); // Cập nhật thông tin đăng nhập

            // URL của API
            String apiUrl = "https://api.innoway.vn/api/devices/vtracking/dashboard/expiringVehicles?offset=0&limit=10";

            // Tạo URL object
            URL url = new URL(apiUrl);

            // Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức yêu cầu
            connection.setRequestMethod("GET");

            // Thêm header xác thực với token tự động
            connection.setRequestProperty("Authorization", "Bearer " + token);

            // Lấy mã trạng thái HTTP
            int responseCode = connection.getResponseCode();
            System.out.println("Ma response:" + responseCode);

            // Đọc dữ liệu phản hồi
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null && responseCode == 200) {
                response.append(inputLine);
            }
            in.close();

            // Chuyển đổi dữ liệu phản hồi thành JSONObject
            JSONObject apiData = new JSONObject(response.toString());

            // In ra dữ liệu để kiểm tra
            System.out.println("Danh sach phuong tien Sap het han su dung (Theo API):");
            System.out.println(apiData.toString(2)); // In ra JSON với định dạng đẹp

            // Lấy ra đối tượng "results" từ JSONObject gốc
            JSONArray results = apiData.getJSONArray("results");

            // Lấy ra các phương tiện theo API
            List<Vehicle> vehiclesAPI = VehicleAPI(results);

            // Lấy số bản ghi từ web
            int SoBanGhiWeb = ExpiringVehicleWeb();
            //System.out.println("So ban ghi tren Web cua phuong tien sap het han:" + SoBanGhiWeb);
            if (results.length() == (SoBanGhiWeb - 1)) {
                // Lấy thông tin các xe từ bảng trên giao diện web
                WebElement table = driver.findElement(By.id("vehicle_expiring_table"));
                List<WebElement> rows = table.findElements(By.tagName("tr"));

                // Duyệt qua các hàng, bắt đầu từ hàng thứ 2 (bỏ qua header)
                for (int i = 1; i < rows.size(); i++) {
                    WebElement row = rows.get(i);
                    List<WebElement> cols = row.findElements(By.tagName("td"));

                    String service_and_date = cols.get(5).getText();
                    String pattern = "dd/MM/yyyy HH:mm";
                    long service_and_date_web = convertToTimestamp(service_and_date, pattern);

                    Assert.assertTrue(cols.get(1).getText().equals(vehiclesAPI.get(i - 1).plate_no) &&
                                    cols.get(2).getText().equals(vehiclesAPI.get(i - 1).imei) &&
                                    cols.get(4).getText().equals(vehiclesAPI.get(i - 1).sim) &&
                                    service_and_date_web == vehiclesAPI.get(i - 1).service_end_date &&
                                    cols.get(6).getText().equals(vehiclesAPI.get(i - 1).days_remaining),
                            "Du lieu chua chinh xac voi API");
                }
            } else {
                Assert.assertTrue(results.length() == (SoBanGhiWeb - 1), "So ban ghi tren Web khong trung hop voi so ban ghi tra ve o API");
            }
    }


    //Tao mang Object thong tin phuong tien trong bang Phuong tien sap het han su dung
    static class Vehicle {
        String plate_no;
        String imei;
        String sim;
        long service_end_date;
        String days_remaining;
        String days_expired;

        public Vehicle(String plate_no, String imei, String sim, long service_end_date, String days_remaining) {
            this.plate_no = plate_no;
            this.imei = imei;
            this.sim = sim;
            this.service_end_date = service_end_date;
            this.days_remaining = days_remaining;
        }

        //lay ra gia tri cac truong
        // Getter
        public String getPlateNo() {
            return plate_no;
        }

        public String getImei() {
            return imei;
        }

        public String getSIM() {
            return sim;
        }

        public long getService_end_date() {
            System.out.println("Server_and_date o ham Vehicle:" + service_end_date);
            return service_end_date;
        }

        public String getDays_remaining() {
            return days_remaining;
        }

        // Override phương thức toString để hiển thị thông tin của object
        @Override
        public String toString() {
            return "Vehicle{" +
                    "plateNo='" + plate_no + '\'' +
                    ", imei='" + imei + '\'' +
                    ", sim='" + sim + '\'' +
                    //", serviceEndDate=" + service_end_date +
                    ", daysRemaining=" + days_remaining +
                    '}';
        }
    }

    public List<Vehicle> VehicleAPI(JSONArray results) {
        List<Vehicle> vehicles = new ArrayList<>();
        // Sử dụng vòng lặp để tạo các object Vehicle và thêm vào List
        for (int k = 0; k < results.length(); k++) {
            String plateNos = results.getJSONObject(k).getString("plate_no");
            String imeis = results.getJSONObject(k).getString("imei");
            String sim = results.getJSONObject(k).getString("sim");
            long service_end_date = results.getJSONObject(k).getLong("service_end_date");
            String days_remaining = String.valueOf(results.getJSONObject(k).getInt("days_remaining"));
            Vehicle vehicle = new Vehicle(
                    plateNos,
                    imeis,
                    sim,
                    service_end_date,
                    days_remaining
            );
            // Thêm object Vehicle vào List
            vehicles.add(vehicle);
        }
        return vehicles;
    }

    //tra ve so ban ghi hien thi tren web
    public int ExpiringVehicleWeb() {
        //Lấy ra thông tin của các xe trong bảng (Web)
        // Lấy phần tử bảng theo id
        WebElement table = driver.findElement(By.id("vehicle_expiring_table"));

        // Lấy tất cả các hàng trong bảng (dưới thẻ <tbody>)
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Duyệt qua các hàng
        //List<Vehicle> vehicles = null;
        for (int i = 1; i < rows.size(); i++) { // Bỏ qua hàng tiêu đề (header)
            WebElement row = rows.get(i);

            // Lấy tất cả các cột trong hàng hiện tại
            List<WebElement> cols = row.findElements(By.tagName("td"));
        }
        return rows.size();
    }

    //ham chuyen doi server_and_date sang timestamp
    // Hàm chuyển đổi chuỗi ngày tháng thành timestamp dạng milliseconds
    public static long convertToTimestamp(String server_and_date, String pattern) {
        // Tạo DateTimeFormatter theo định dạng chuỗi được cung cấp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        // Chuyển chuỗi thành LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse(server_and_date, formatter);

        // Chuyển LocalDateTime thành timestamp theo múi giờ địa phương, rồi trả về dạng milliseconds
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }

    public void PhuongTienDaHetHanSuDung() throws Exception {
        //try {
            // Lấy token tự động từ API
            String token = getToken("giangptt", "123456aA@", "CuUTtsmfjMBOKeMEpkAo", "Ga8lL0lMVFr6fJoudGgJR9upsXuIgGxz"); // Cập nhật thông tin đăng nhập

            // URL của API
            String apiUrl = "https://api.innoway.vn/api/devices/vtracking/dashboard/expiredVehicles?offset=0&limit=10";

            // Tạo URL object
            URL url = new URL(apiUrl);

            // Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức yêu cầu
            connection.setRequestMethod("GET");
            // Thêm header xác thực nếu cần (ví dụ: token Bearer)
            connection.setRequestProperty("Authorization", "Bearer " + token);

            // Lấy mã trạng thái HTTP
            int responseCode = connection.getResponseCode();
            System.out.println("Ma response:" + responseCode);

            // Đọc dữ liệu phản hồi
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null && responseCode == 200) {
                response.append(inputLine);
            }
            in.close();

            // Chuyển đổi dữ liệu phản hồi thành JSONObject
            JSONObject apiData = new JSONObject(response.toString());

            // In ra dữ liệu để kiểm tra
            System.out.println("Danh sach phuong tien Da het han su dung (Theo API):");
            System.out.println(apiData.toString(2)); // In ra JSON với định dạng đẹp

            // Lấy ra đối tượng "results" từ JSONObject gốc
            JSONArray results = apiData.getJSONArray("results");

            //Lay ra cac phuong tien theo API
            List<VehicleExpired> vehiclesExpiredAPI = VehicleExpiredAPI(results);
//            for (VehicleExpired vehicle : vehiclesExpiredAPI) {
//                System.out.println("Danh sach cac phuong tien theo API:");
//                System.out.println("Plate Number: " + vehicle.getPlateNo());
//                System.out.println("IMEI: " + vehicle.getImei());
//                System.out.println("SIM: " + vehicle.getSIM());
//                System.out.println("Service End Date: " + vehicle.getService_end_date());
//                System.out.println("Days Expired: " + vehicle.getDays_expired());
//            }

            int SoBanGhiWeb = ExpiredVehicleWeb();
            if (results.length() == (SoBanGhiWeb - 1)) {
                //Lấy ra thông tin của các xe trong bảng (Web)
                // Lấy phần tử bảng theo id
                WebElement table = driver.findElement(By.id("vehicle_expired_table"));

                // Lấy tất cả các hàng trong bảng (dưới thẻ <tbody>)
                List<WebElement> rows = table.findElements(By.tagName("tr"));

                // Duyệt qua các hàng
                //List<Vehicle> vehicles = null;
                for (int i = 1; i < rows.size(); i++) { // Bỏ qua hàng tiêu đề (header)
                    WebElement row = rows.get(i);

                    // Lấy tất cả các cột trong hàng hiện tại
                    List<WebElement> cols = row.findElements(By.tagName("td"));

                    String service_and_date = cols.get(5).getText();
                    String pattern = "dd/MM/yyyy HH:mm"; // Định dạng chuỗi
                    long service_and_date_web = convertToTimestamp(service_and_date, pattern);

                    Assert.assertTrue(cols.get(1).getText().equals(vehiclesExpiredAPI.get(i - 1).plate_no) && cols.get(2).getText().equals(vehiclesExpiredAPI.get(i - 1).imei)
                            && cols.get(4).getText().equals(vehiclesExpiredAPI.get(i - 1).sim) && service_and_date_web == vehiclesExpiredAPI.get(i - 1).service_end_date
                            && cols.get(6).getText().equals(vehiclesExpiredAPI.get(i - 1).days_expired), "Du lieu chua chinh xac voi API");
                }
            } else {
                Assert.assertTrue(results.length() == (SoBanGhiWeb - 1), "So ban ghi tren Web khong trung hop voi so ban ghi tra ve o API");
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    //Tao mang Object thong tin phuong tien trong bang Phuong tien da het han su dung
    static class VehicleExpired {
        String plate_no;
        String imei;
        String sim;
        long service_end_date;
        String days_expired;

        public VehicleExpired(String plate_no, String imei, String sim, long service_end_date, String days_expired) {
            this.plate_no = plate_no;
            this.imei = imei;
            this.sim = sim;
            this.service_end_date = service_end_date;
            this.days_expired = days_expired;
        }

        //lay ra gia tri cac truong
        // Getter
        public String getPlateNo() {
            return plate_no;
        }

        public String getImei() {
            return imei;
        }

        public String getSIM() {
            return sim;
        }

        public long getService_end_date() {
            return service_end_date;
        }

        public String getDays_expired() {
            return days_expired;
        }

        // Override phương thức toString để hiển thị thông tin của object
        @Override
        public String toString() {
            return "Vehicle{" +
                    "plateNo='" + plate_no + '\'' +
                    ", imei='" + imei + '\'' +
                    ", sim='" + sim + '\'' +
                    //", serviceEndDate=" + service_end_date +
                    ", daysExpired=" + days_expired +
                    '}';
        }
    }

    public List<VehicleExpired> VehicleExpiredAPI(JSONArray results) {
        List<VehicleExpired> vehicles = new ArrayList<>();
        // Sử dụng vòng lặp để tạo các object Vehicle và thêm vào List
        for (int k = 0; k < results.length(); k++) {
            String plateNos = results.getJSONObject(k).getString("plate_no");
            String imeis = results.getJSONObject(k).getString("imei");
            String sim = results.getJSONObject(k).getString("sim");
            long service_end_date = results.getJSONObject(k).getLong("service_end_date");
            String days_expired = String.valueOf(results.getJSONObject(k).getInt("days_expired"));
            VehicleExpired vehicle = new VehicleExpired(
                    plateNos,
                    imeis,
                    sim,
                    service_end_date,
                    days_expired
            );
            // Thêm object Vehicle vào List
            vehicles.add(vehicle);
        }
        return vehicles;
    }
    public int ExpiredVehicleWeb() {
        //Lấy ra thông tin của các xe trong bảng (Web)
        // Lấy phần tử bảng theo id
        WebElement table = driver.findElement(By.id("vehicle_expired_table"));

        // Lấy tất cả các hàng trong bảng (dưới thẻ <tbody>)
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Duyệt qua các hàng
        //List<Vehicle> vehicles = null;
        for (int i = 1; i < rows.size(); i++) { // Bỏ qua hàng tiêu đề (header)
            WebElement row = rows.get(i);

            // Lấy tất cả các cột trong hàng hiện tại
            List<WebElement> cols = row.findElements(By.tagName("td"));
        }
        return rows.size();
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

    public void BieuDoTongSoKM() throws Exception {
        //try {
            // Lấy token tự động từ API
        String token = getToken("giangptt", "123456aA@", "CuUTtsmfjMBOKeMEpkAo", "Ga8lL0lMVFr6fJoudGgJR9upsXuIgGxz"); // Cập nhật thông tin đăng nhập

            // URL của API
            String webUrl = "https://api.innoway.vn/api/attributes/vtracking/dashboard/distanceKm?orderBy=DESC&startTime=1723827600000&endTime=1726481807467";

            // Tạo URL object
            URL url = new URL(webUrl);

            // Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức yêu cầu
            connection.setRequestMethod("GET");

            // Thêm header Authorization với token lấy được
            connection.setRequestProperty("Authorization", "Bearer " + token);

            // Lấy mã trạng thái HTTP
            int responseCode = connection.getResponseCode();
            System.out.println("Ma response: " + responseCode);

            // Đọc dữ liệu phản hồi
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null && responseCode == 200) {
                response.append(inputLine);
            }
            in.close();

            // Chuyển đổi dữ liệu phản hồi thành JSONObject
            JSONObject apiData = new JSONObject(response.toString());
            JSONArray results = apiData.getJSONArray("results");

            // In ra dữ liệu để kiểm tra
            System.out.println("Danh sach phuong tien theo bieu do tong so KM:" + results);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void BieuDoThoiGianLaiXe() throws Exception {
        //try {
            // Lấy token tự động từ API
        String token = getToken("giangptt", "123456aA@", "CuUTtsmfjMBOKeMEpkAo", "Ga8lL0lMVFr6fJoudGgJR9upsXuIgGxz"); // Cập nhật thông tin đăng nhập

            // URL của API
            String webUrl = "https://api.innoway.vn/api/attributes/vtracking/dashboard/drivingTime?orderBy=DESC&startTime=1723914000000&endTime=1726539609487";

            // Tạo URL object
            URL url = new URL(webUrl);

            // Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức yêu cầu
            connection.setRequestMethod("GET");

            // Thêm header Authorization với token lấy được
            connection.setRequestProperty("Authorization", "Bearer " + token);

            // Lấy mã trạng thái HTTP
            int responseCode = connection.getResponseCode();
            System.out.println("Ma response: " + responseCode);

            // Đọc dữ liệu phản hồi
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null && responseCode == 200) {
                response.append(inputLine);
            }
            in.close();

            // Chuyển đổi dữ liệu phản hồi thành JSONObject
            JSONObject apiData = new JSONObject(response.toString());
            JSONArray results = apiData.getJSONArray("results");

            // In ra dữ liệu để kiểm tra
            System.out.println("Danh sach phuong tien theo bieu do Thoi gian lai xe: " + results);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
