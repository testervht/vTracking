package pages.ReportPages;
import bases.WebUIHelpers;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.History.RoutePage;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class AllReportPage {

    public AllReportPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.routePage = new RoutePage(driver);
    }
    private final WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private RoutePage routePage;

    //1. Elements in Journey Reports
    public static By btnCreate_Journey = By.id("journey_createReportBtn");
    public static By reportTable_Journey = By.id("journey_table");
    public static By btnExport_Journey = By.cssSelector("[onclick='journey_exportFormatExcel(0)']");
    public static By ddPlate_Journey = By.id("journey_plateNo");
    public static By btnSave_Journey = By.id("journey_modalConfirmBtn");

    //2. **********************Element in Continuous Driving Time Reports*******************
    public static By ddPlate_DTime = By.id("cdt_plateNo");
    public static By btnCreate_DTime = By.id("cdt_createReportBtn");
    public static By reportTable_DTime = By.id("cdt_table");
    public static By btnExport_DTime = By.cssSelector("[onclick='cdt_exportExcel(0)']");
    public static By results_DTime = By.id("cdt_search-result");
    public static By btnSave_DTime = By.id("cdt_modalConfirmBtn");
    public static By timepicker_start_DTime = By.id("cdt_start-time");
    public static By timepicker_end_DTime = By.id("cdt_end-time");

    //3. **********************Element in Speed Reports*******************
    public static By ddPlate_Speed = By.id("speed_plateNo");
    public static By btnCreate_Speed = By.id("speed_createReportBtn");
    public static By reportTable_Speed = By.id("speed_table");
    public static By btnExport_Speed = By.cssSelector("[onclick='speed_exportExcel(0)']");
    public static By results_Speed = By.id("speed_search-result");
    public static By timepicker_start_Speed = By.id("speed_start-time");
    public static By timepicker_end_Speed = By.id("speed_end-time");

    public static By btnSave_Speed = By.id("speed_modalConfirmBtn");

    //4. **********************Element in Over Speed Reports*******************
    public static By ddPlate_OS = By.id("os_plateNo");
    public static By btnCreate_OS = By.id("os_createReportBtn");
    public static By reportTable_OS = By.id("os_table");
    public static By btnExport_OS = By.cssSelector("[onclick='os_exportExcel(0)']");
    public static By results_OS = By.id("os_search-result");
    public static By timepicker_start_OS = By.id("os_start-time");
    public static By timepicker_end_OS = By.id("os_end-time");

    public static By btnSave_OS = By.id("os_modalConfirmBtn");

    //5. **********************Element in Park Reports*******************
    public static By ddPlate_Park = By.id("park_plateNo");
    public static By btnCreate_Park = By.id("park_createReportBtn");
    public static By reportTable_Park = By.id("park_table");
    public static By btnExport_Park = By.cssSelector("[onclick='park_exportExcel(0)']");
    public static By results_Park = By.id("park_search-result");
    public static By timepicker_start_Park = By.id("park_start-time");
    public static By timepicker_end_Park = By.id("park_end-time");

    public static By btnSave_Park = By.id("park_modalConfirmBtn");

    //6. **********************Element in Overview Reports*******************
    public static By ddPlate_O = By.id("o_plateNo");
    public static By btnCreate_O = By.id("o_createReportBtn");
    public static By reportTable_O = By.id("o_table");
    public static By btnExport_O = By.cssSelector("[onclick='o_genFormattedExcel(0)']");
    public static By results_O = By.id("o_search-result");
    public static By timepicker_start_O = By.id("o_start-time");
    public static By timepicker_end_O = By.id("o_end-time");

    public static By btnSave_O = By.id("o_modalConfirmBtn");

    //7. **********************Element in Activity Detail Reports*******************
    public static By ddPlate_Ad = By.id("ad_plateNo");
    public static By btnCreate_Ad = By.id("ad_createReportBtn");
    public static By reportTable_Ad = By.id("ad_table");
    public static By btnExport_Ad = By.cssSelector("[onclick='ad_genFormattedExcel(0)']");
    public static By results_Ad = By.id("ad_search-result");
    public static By timepicker_start_Ad = By.id("ad_start-time");
    public static By timepicker_end_Ad = By.id("ad_end-time");

    public static By btnSave_Ad = By.id("ad_modalConfirmBtn");

    //8. Báo cáo Lịch sử nhiên liệu
    public static By ddPlate_FuelHistory = By.id("ht_plateNo"); // Droplist bien so
    public static By btnCreate_FuelHistory = By.id("ht_createReportBtn");
    public static By reportTable_FuelHistory = By.id("ht_table");
    public static By btnExport_FuelHistory = By.id("Fuel_Excel");
    public static By btnSave_FuelHistory = By.id("ht_modalConfirmBtn"); // button Xac nhan o popup Chon ptien

    //9. Bao cao tieu hao nhien lieu theo quang duong
    public static By ddPlate_FuelDistance = By.id("FueDistance_plateNo"); // Droplist bien so
    public static By btnCreate_FuelDistance = By.id("FuelByDistance_createReportBtn");
    public static By reportTable_FuelDistance = By.id("FueDistance_table");
    public static By btnExport_FuelDistance = By.id("FueDistance_Excel");
    public static By btnSave_FuelDistance = By.id("FuelByDistance_modalConfirmBtn"); // button Xac nhan o popup Chon ptien


    //10. Bao cao Lich su nhiet do
    public static By ddPlate_Temperature = By.id("Nhietdo_plateNo"); // Droplist bien so
    public static By btnCreate_Temperature = By.id("Nhietdo_createReportBtn");
    public static By reportTable_Temperature = By.id("Nhietdo_table");
    public static By btnExport_Temperature = By.id("Nhietdo_Excel");
    public static By btnSave_Temperature = By.id("Nhietdo_modalConfirmBtn"); // button Xac nhan o popup Chon ptien

    //11. Bao cao Thoi gian lam viec
    public static By ddPlate_WorkTime = By.id("wt_plateNo"); // Droplist bien so
    public static By btnCreate_WorkTime = By.id("wt_createReportBtn");
    public static By reportTable_WorkTime = By.id("wt_table");
    public static By btnExport_WorkTime = By.cssSelector("[onclick='wt_exportFormattedExcel(0)']");;
    public static By btnSave_WorkTime = By.id("wt_modalConfirmBtn"); // button Xac nhan o popup Chon ptien

    //12. Bao cao Tong hop theo xe
    public static By ddPlate_Tc = By.id("tc_plateNo");
    public static By btnCreate_Tc = By.id("tc_createReportBtn");
    public static By reportTable_Tc = By.id("tc_table");
    public static By btnExport_Tc = By.cssSelector("[onclick='tc_exportExcel(0)']");
    public static By btnSave_Tc = By.id("tc_modalConfirmBtn");
    public static By monthpicker_Tc = By.id("tc_start-time");

    //**********************Element chung id******************************
    public static By alertPopup = By.cssSelector("span[data-notify='message']");
    public static By tbSearch = By.id("textSearchOrgAndDevice");
    public static By listResults = By.id("listSearchOrgAndDevice");

    public void verifyDefaultCreateBtn(By createButton){
        String pointerEvents = webUIHelpers.getCss(createButton, "pointer-events");
        String opacity = webUIHelpers.getCss(createButton, "opacity");
        boolean isDisabled = "none".equals(pointerEvents) || Float.parseFloat(opacity) < 1.0;
        Assert.assertTrue(isDisabled, "Test Failed: Button is not disabled.");
    }
    public void searchAbsoluteValue(String searchKey, By plateDD, String id, By saveBtn) {
        webUIHelpers.clickElement(plateDD);
        webUIHelpers.sleep(10000);
        webUIHelpers.setText(tbSearch, searchKey);//type searchkey
        webUIHelpers.sleep(10000);

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

        webUIHelpers.waitAndClick(listResults);//click result
        webUIHelpers.sleep(3000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); //routePage.getId(6)
        WebElement btnLicense = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("label[for='checkbox-" + id + "']")));
        btnLicense.click();
        webUIHelpers.clickElement(saveBtn);
        webUIHelpers.sleep(5000);
    }
    public void chooseDateTime(By timepicker_start, By timepicker_end, String startTime, String endTime){
        if (!startTime.isEmpty()) {
            webUIHelpers.clearAndSetText(timepicker_start, startTime);
        }
        webUIHelpers.clickElement(timepicker_end);
        if (!endTime.isEmpty()) {
            webUIHelpers.clearAndSetText(timepicker_end, endTime);
        }
        webUIHelpers.sleep(5000);
    }
    public void chooseMonth(String month){
        webUIHelpers.clickElement(monthpicker_Tc);
        By _monthFrom = By.xpath ("//span[normalize-space()='" + month + "']");
        webUIHelpers.clickElement(_monthFrom);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void createReport(By createBtn){
        webUIHelpers.clickElement(createBtn);
        webUIHelpers.sleep(15000);
    }

    public void verifyNoti(){
        String alertActual = webUIHelpers.getTextInAlert(alertPopup);
        String expectAlert = "Không có báo cáo nào trong khoảng thời gian được chọn";
        Assert.assertEquals(alertActual, expectAlert);
    }

    public void verifyCreateSuccessfully(By reportTable) {
        WebElement table = driver.findElement(reportTable);
        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        assert rows.size() > 0 : "Báo cáo không chứa dữ liệu.";
    }

    public void verifyExportFileExcel(By exportBtn) {
        String home = System.getProperty("user.home"); // anhntl52 xử lý downloadFilepath tương thích từng máy -> done
        String downloadFilepath = Paths.get(home, "Downloads").toString();
        long startTime = System.currentTimeMillis(); //// Ghi lại thời gian hiện tại

        webUIHelpers.clickElement(exportBtn);
        webUIHelpers.sleep(3000);

//        String actualResults = webUIHelpers.getText(numOfRecord); // Lấy số lượng bản ghi trên web
        boolean isDownloaded = false; // Cờ để kiểm tra xem tệp đã được tải về hay chưa
        File newestFile = null; // Biến để lưu tệp tải về

        // Vòng lặp kiểm tra thư mục đã được tải về chưa
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(1000);
                File[] files = new File(downloadFilepath).listFiles();

                // In debug thông tin về file trong thư mục
//                if (files != null && files.length > 0) {
//                    System.out.println("Found files:");
//                    for (File file : files) {
//                        System.out.println("File: " + file.getName() + ", Last Modified: " + file.lastModified());
//                    }
//                }

                if (files != null && files.length > 0) {
                    for (File file : files) {
                        // Kiểm tra xem file có phải là dạng excel và được tải sau startTime
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
            System.out.println("Downloaded file: " + newestFile.getName()); // Thêm log file tải về
            newestFile.delete(); // Xóa file sau khi kiểm tra xong
        }
    }



//    public void verifyExportFileExcel(String plate, By exportBtn, By numOfRecord, Map<Integer, String> headerExpectedValues) {
//        //String downloadFilepath = "/Users/ngolinh/Downloads/"; //"C:\\Users\\LAN ANH\\Downloads"; // đường dẫn ở thư mục mà file sẽ tải về
//        Path downloadPath = Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), "Downloads"); //update path tương thích từng máy
//        String downloadFilepath = downloadPath.toString();
//        long startTime = System.currentTimeMillis(); // Ghi lại thời gian hiện tại
//
//        webUIHelpers.clickElement(exportBtn);
//        webUIHelpers.sleep(3000);
//
//        String actualResults = webUIHelpers.getText(numOfRecord); // Lấy số lượng bản ghi trên web
//        boolean isDownloaded = false; // Cờ để kiểm tra xem tệp đã được tải về hay chưa
//        File newestFile = null; // Biến để lưu tệp tải về
//
//        // Vòng lặp kiểm tra thư mục đã được tải về chưa
//        for (int i = 0; i < 30; i++) {
//            try {
//                Thread.sleep(1000);
//                File[] files = new File(downloadFilepath).listFiles();
//                if (files != null && files.length > 0) {
//                    for (File file : files) {
//                        // Kiểm tra xem file có phải là dạng excel và được tải sau startTime
//                        if ((file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls")) && file.lastModified() > startTime) {
//                            if (newestFile == null || file.lastModified() > newestFile.lastModified()) {
//                                newestFile = file;
//                            }
//                        }
//                    }
//                    if (newestFile != null) {
//                        isDownloaded = true;
//                        break;
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (!isDownloaded) {
//            Assert.fail("Test Failed: Excel file was not downloaded.");
//        } else {
//            newestFile.delete(); // Xóa file sau khi kiểm tra xong
//        }
//
//        if (isDownloaded) {
//            try {
//                int excelRecordCount = checkExcelFile(newestFile.getAbsolutePath(), headerExpectedValues, plate);// Kiểm tra nội dung file excel
//                newestFile.delete(); // Xóa file sau khi kiểm tra xong
//
//                // Định dạng chuỗi số lượng bản ghi trong Excel giống với actualResults
//                String expectedResults = "KẾT QUẢ TÌM KIẾM: " + excelRecordCount;
//                Assert.assertEquals(actualResults, expectedResults, "Test Failed: Number of records in Excel file does not match number of records on the web page.");
//            } catch (Exception e) {
//                e.printStackTrace();
//                Assert.fail("Exception occurred while checking Excel file: " + e.getMessage());
//            }
//        }
//    }
//
////     kiểm tra nôi dung file
//    public int checkExcelFile(String filePath, Map<Integer, String> headerExpectedValues, String plate) {
//        int recordCount = 0;
//        try {
//            FileInputStream file = new FileInputStream(filePath);
//            Workbook workbook = new XSSFWorkbook(file);
//            Sheet sheet = workbook.getSheetAt(0);
//
//            // Kiểm tra số lượng cột
//            Row headerRow = sheet.getRow(0);
//            int expectedColumns = headerExpectedValues.size();
//            int actualColumns = headerRow.getPhysicalNumberOfCells();
//            if (actualColumns != expectedColumns) {
//                throw new AssertionError("Test Failed: Number of columns in Excel file (" + actualColumns + ") does not match expected number of columns (" + expectedColumns + ")");
//            }
//
//            // Kiểm tra các giá trị của hàng title
//            for (Map.Entry<Integer, String> entry : headerExpectedValues.entrySet()) {
//                int columnIndex = entry.getKey();
//                String expectedValue = entry.getValue();
//                Cell cell = headerRow.getCell(columnIndex);
//                if (cell == null) {
//                    throw new AssertionError("Test Failed: Cell is missing at column " + columnIndex + " in header row.");
//                }
//                String actualValue = cell.getStringCellValue();
//                Assert.assertEquals(actualValue, expectedValue, "Test Failed: Header content is incorrect at column " + columnIndex);
//            }
//
//            // Kiểm tra giá trị của ô biển số hàng đầu tiên
//            Row firstDataRow = sheet.getRow(1);
//            if (firstDataRow == null) {
//                throw new AssertionError("Test Failed: First data row is missing in Excel file.");
//            }
//            Cell plateCell = firstDataRow.getCell(1);
//            if (plateCell == null) {
//                throw new AssertionError("Test Failed: Cell for 'Biển số' is missing in first data row.");
//            }
//            String actualPlateValue = plateCell.getStringCellValue();
//            Assert.assertEquals(actualPlateValue, plate, "Test Failed: First row content for 'Biển số' is incorrect.");
//
//            // Đếm số lượng bản ghi trong file Excel
//            recordCount = sheet.getPhysicalNumberOfRows() - 1; // Trừ đi hàng title
//
//            workbook.close();
//            file.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Assert.fail("IOException occurred while checking Excel file: " + e.getMessage());
//        }
//        return recordCount;
//    }

    public void verifyExportWithoutData(By btnExport){
        webUIHelpers.clickElement(btnExport);
        verifyNoti();
    }

}
