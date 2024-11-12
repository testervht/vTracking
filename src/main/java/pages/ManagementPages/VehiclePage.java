package pages.ManagementPages;

import bases.WebUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.LoginPage;
import pages.LogoutPage;
import pages.ManagePage;
import bases.ExcelHelpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VehiclePage {
    public VehiclePage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
        this.logoutPage = new LogoutPage(driver);
        this.managePage = new ManagePage(driver);
    }

    private final WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private LoginPage loginPage;
    private LogoutPage logoutPage;
    private ManagePage managePage;

    public static final By btnQuanLy = By.xpath("//span[contains(text(),'Quản lý')]");
    public static final By menuPhuongTien = By.id("vehicleTabMenu");
    //ele filter
    private By ddCompany = By.id("nameOrgVehicleTab");
    private By ddVehicleType = By.id("vt_filterVehicleTitleVal");
    private By timepickerStartDate = By.id("dateStartSearch");
    private By timepickerEndDate = By.id("dateEndSearch");
    private By ddFilter = By.id("vt_filterTypeTitleVal");
    private By searchbox = By.id("textSearchVehicle");
    private By btnSearch = By.cssSelector("[onclick='searchVehicleList()']");
    private By textTotalVehicle = By.id("totalVehicle");

    //Các button chức năng
    private By btnAdd = By.cssSelector("[onclick='handleAddVehicle()']");
    private By btnExport = By.cssSelector("[onclick='exportVehicle(0)']");
    private By btnConfig = By.cssSelector("[data-target='#configVehicleModal']");
    public static final By btnSua = By.xpath("//*[@id=\"vehicle_table\"]/tbody/tr/td[2]/img[3]");
    public static final By btnXoa = By.xpath("//*[@id=\"vehicle_table\"]/tbody/tr/td[2]/img[4]");
    private By btnAssign = By.xpath("//*[@id=\"vehicle_table\"]/tbody/tr[1]/td[2]/img[2]");

    //Các giá trị trong bảng danh sách
    private By textInTable = By.className("no_table_data");
    private By infoPlate = By.cssSelector(".wrap-text.vehiclePlate");
    private By infoType = By.cssSelector(".wrap-text.vehicleType");
    private By infoCompany = By.cssSelector(".wrap-text.vehicleCompany");
    private By infoImei = By.cssSelector(".wrap-text.vehicleImei");
    private By infoSim = By.cssSelector(".wrap-text.vehicleSim");
    private By infoCreateTime = By.cssSelector(".wrap-text.vehicleCreateTime");
    public static final By alertPopup = By.cssSelector("span[data-notify='message']");

    //ele in create popup
    public class AddVehicleLocators {
        public static final By tbVehicleName = By.id("nameVehicleAdd");
        public static final By ddTranportType = By.id("logisticAdd");
        public static final By ddVehicleType = By.id("typeAdd");
        public static final By tbPlate = By.id("plateAdd");
        public static final By treeCompany = By.id("nameOrgVehicleAdd");
        public static final By ddDriver = By.id("select2-driverAdd-container");
        public static final By ddRegisterPlace = By.id("registerPlaceAdd");
        public static final By tbBrand = By.id("brandAdd");
        public static final By tbVehicleLine = By.id("vehicleLineAdd");
        public static final By tbDesc = By.id("vehicleDescAdd");
        public static final By ddDeviceType = By.id("deviceTypeAdd");
        public static final By ddImei = By.id("deviceCodeAdd");
        public static final By tbSim = By.id("simAdd");
        public static final By datepickerStartDate = By.id("generalInfoStartAdd");
        public static final By datepickerEndDate = By.id("generalInfoEndAdd");
        public static final By ddServiceStatus = By.id("serviceStatusAdd");
        public static final By btnCancel = By.xpath("//*[@id=\"addVehicleModal\"]/div/div/div[3]/button[1]");
        public static final By btnSave = By.cssSelector("[onclick='validateAddVehicle()']");
        //noti
        public static final By notiLogistic = By.id("logisticAddNoti");
        public static final By notiCompanyName = By.id("nameCompanyAddNoti");
        public static final By notiUsername = By.id("usernameAddNoti");
        public static final By notiPhoneAcc = By.id("phoneAccountAddNoti");
        public static final By notiBusinessLicense = By.id("businessLicenseAddNoti");
        public static final By notiPassword = By.id("passwordAddNoti");
        public static final By notiRepassword = By.id("rePasswordAddNoti");
    }

    public class EditVehicleLocators {
        //Thong tin phuong tien
        public static final By txtTenPhuongtien = By.id("nameVehicleEdit");
        public static final By ddLoaiHinhVantai = By.id("logisticEdit");
        public static final By ddLoaiPhuongtien = By.id("typeEdit");
        public static final By txtBienSo = By.id("plateEdit");
        public static final By ddCongTy = By.id("nameOrgVehicleEdit");
        public static final By ddLaiXe = By.id("select2-driverEdit-container");
        public static final By ddNoiDangKiem = By.id("registerPlaceEdit");
        public static final By txtHang = By.id("brandEdit");
        public static final By txtDongPhuongTien = By.id("vehicleLineEdit");
        public static final By txtMoTa = By.id("vehicleDescEdit");

        //Thong tin thiet bi
        public static final By ddKieuThietBi = By.id("deviceTypeEdit");
        public static final By ddMaThietBi = By.id("deviceCodeEdit");

        public static final By txtSIM = By.id("simEdit");
        public static final By dpkNgayBatDau = By.id("generalInfoStartEdit");
        public static final By dpkNgayKetThuc = By.id("generalInfoEndEdit");
        public static final By ddTrangThaiDV = By.id("serviceStatusEdit");


        //Button chuc nang khi Sua
        public static final By btnHuy = By.xpath("//*[@id=\"editVehicleGeneralInfoForm\"]/div[3]/button[2]");
        public static final By btnLuu = By.xpath("//button[@onclick='validateEditVehicle()']");

        //noti inline
        public static final By notiBienSoRong = By.id("plateEditNoti");
    }
    //elm trong popup Gan
    private By ddAssignUserStatus = By.xpath("//*[@id=\"vehicleAssignUser\"]/div[1]/div[1]/div");
    private By searchboxUser = By.id("textSearchAssignedUser");
    private By btnSearchUser = By.cssSelector("[onclick='searchUserList()']");
    private By btnAssignUser = By.xpath("//*[@id=\"assignedUser_table\"]/tbody/tr/td[6]/div");
    private By btnConfirmAssign = By.cssSelector("[onclick='assignedUser()']");
    private By btnClose = By.xpath("//*[@id=\"assignUserVehicleModal\"]/div/div/div[3]/button[1]");

    //elm Xoa PT
    public static final By btnXacNhanXoa = By.cssSelector("button[onclick='processDeleteVehicle()']");
    public static final By btnXacNhanHuy = By.xpath("//div[@id='delVehicleModal']//button[@type='button'][contains(text(),'Hủy bỏ')]");

    private String filePath = "dataTestvTracking.xlsx"; // Anhntl52 cập nhật filepath tương đối
    private String sheetName = "MngVehicle";

    public String getVehicleName(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"name", index);
    }
    public String getTransportType(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"transportType", index);
    }
    public String getVehicleType(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"vehicleType", index);
    }
    public String getPlate(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"plate", index);
    }
    public String getCompany(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"company", index);
    }
    public String getDriver(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"driver", index);
    }
    public String getRegisterPlace(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"registerPlace", index);
    }
    public String getBrand(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"brand", index);
    }
    public String getVehicleLine(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"vehicleLine", index);
    }
    public String getDesc(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"desc", index);
    }
    public String getDeviceType(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"deviceType", index);
    }
    public String getImei(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"imei", index);
    }
    public String getSIM(int index)  {
        return ExcelHelpers.getDataXLSX(filePath,sheetName,"SIM", index);
    }

    //search function
    public void search(String company, String type, String startDate, String endDate, String filter, String searchText) {
        if (!company.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeVehicleTab') and contains(text(), '" + company + "')]";
            webUIHelpers.clickElementAndSelect(ddCompany, By.xpath(xpathExpression));
        }
        if (!type.isEmpty()) {
            webUIHelpers.selectElement(ddVehicleType, type);
        }
        if (!filter.isEmpty()) {
            webUIHelpers.selectElement(ddFilter, filter);
        }
        if (!startDate.isEmpty()) {
            webUIHelpers.clickElement(timepickerStartDate);
            webUIHelpers.clearAndSetText(timepickerStartDate, startDate);
        }
        webUIHelpers.sleep(3000);
        if (!endDate.isEmpty()) {
            webUIHelpers.clickElement(timepickerEndDate);
            webUIHelpers.clearAndSetText(timepickerEndDate, endDate);
        }
        webUIHelpers.setText(searchbox, searchText);
        webUIHelpers.clickElement(btnSearch);
        webUIHelpers.sleep(3000);
    }

    public void verifySearchWithoutValue(String expectText, String expectTextTotal){
        String actualTextTotal = webUIHelpers.getText(textTotalVehicle);
        String actualTextInTable = webUIHelpers.getText(textInTable);

        Assert.assertEquals(actualTextTotal, expectTextTotal);
        Assert.assertEquals(actualTextInTable, expectText);
    }

    public void searchByFilter(String company, String type, String startDate, String endDate, String filter, String searchText, String expect, By listByFilter){
        String expectText = expect.toLowerCase();
        search(company, type, startDate, endDate, filter, searchText);
        for (WebElement element : driver.findElements(listByFilter)) {
            String actualText = element.getText().trim().toLowerCase();
            Assert.assertTrue(actualText.contains(expectText), "Element text doesn't contain '" + expectText + "'");
        }
    }

    public void verifySearchByCompany(){
        searchByFilter("Công ty test 21/11/2024", "", "", "", "", "", "Công ty test 21/11/2024", infoCompany);
    }

    public void verifySearchByType(){
        searchByFilter("", "Phương tiện khác", "", "", "", "", "Phương tiện khác", infoType);
    }

    public void searchByPlate(){
        searchByFilter("", "", "", "", "Biển số xe", "1234", "1234", infoPlate);
    }

    public void searchBySIM(){
        searchByFilter("", "", "", "", "Số SIM", "123", "123", infoSim);
    }

    public void searchByImei(){
        searchByFilter("", "", "", "", "Mã thiết bị", "456", "456", infoImei);
    }

    public void verifyRangeDate(String startDate, String endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date start = dateFormat.parse(startDate + " 00:00:00");
        Date end = dateFormat.parse(endDate + " 23:59:59");

        List<WebElement> elements = driver.findElements(By.cssSelector(".wrap-text.vehicleCreateTime"));

        for (WebElement element : elements) {
            String dateText = element.getText().trim();
            Date date = dateFormat.parse(dateText);

            Assert.assertTrue(date.compareTo(start) >= 0 && date.compareTo(end) <= 0,
                    "Date " + dateText + " is not within the range " + startDate + " - " + endDate);
        }
    }

    public void searchByDate(){
        search("", "", "01/05/2024", "20/05/2024", "", "");
        webUIHelpers.sleep(3000);
        try {
            verifyRangeDate("01/05/2024", "20/05/2024");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void searchByMultiConditions(){
        search("", "", "01/09/2024", "20/09/2024", "Biển số xe", "99");
        List<WebElement> elements = driver.findElements(By.cssSelector(".wrap-text.vehicleCreateTime"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            Date start = dateFormat.parse("01/09/2024 00:00:00");
            Date end = dateFormat.parse("20/09/2024 23:59:59");

            for (WebElement element : elements) {
                // Kiểm tra ngày tháng nằm trong khoảng thời gian chỉ định
                String dateText = element.getText().trim();
                Date date = dateFormat.parse(dateText);

                Assert.assertTrue(date.compareTo(start) >= 0 && date.compareTo(end) <= 0,
                        "Date " + dateText + " is not within the range 01/09/2024 - 20/09/2024");
            }

            // Kiểm tra các điều kiện khác (công ty, loại phương tiện, biển số xe)
//            for (WebElement element : driver.findElements(infoCompany)) {
//                String actualCompanyText = element.getText().trim();
//                Assert.assertTrue(actualCompanyText.contains("Công ty test 21/11/2024"),
//                        "Company text doesn't contain 'Công ty test 21/11/2024'");
//            }

//            for (WebElement element : driver.findElements(infoType)) {
//                String actualTypeText = element.getText().trim();
//                Assert.assertTrue(actualTypeText.contains("Không truyền"),
//                        "Vehicle type doesn't contain 'Không truyền'");
//            }

            for (WebElement element : driver.findElements(infoPlate)) {
                String actualPlateText = element.getText().trim();
                Assert.assertTrue(actualPlateText.contains("99"),
                        "Plate number doesn't contain '99'");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Assert.fail("Failed to parse date: " + e.getMessage());
        }
    }

    public void searchVehicle(String keySearch){
        webUIHelpers.clickElement(btnQuanLy);
        webUIHelpers.clickElement(menuPhuongTien);
        webUIHelpers.setText(searchbox, keySearch);
        webUIHelpers.sleep(5000);
        webUIHelpers.clickElement(btnSearch);
        webUIHelpers.sleep(5000);
    }
    //create vehicle function
    public void fillInfoVehicle(
            String name,
            String transportType,
            String vehicleType,
            String plate,
            String company,
            String driver,
            String registerPlace,
            String brand,
            String vehicleLine,
            String desc,
            String deviceType,
            String imei,
            String SIM
    ){
        webUIHelpers.sleep(3000);
        webUIHelpers.setText(AddVehicleLocators.tbVehicleName, name);
        if (!transportType.isEmpty()) {
            webUIHelpers.selectElement(AddVehicleLocators.ddTranportType, transportType);
        }
        webUIHelpers.sleep(3000);
        if (!vehicleType.isEmpty()) {
            webUIHelpers.selectElement(AddVehicleLocators.ddVehicleType, vehicleType);
        }
        webUIHelpers.setText(AddVehicleLocators.tbPlate, plate);
        if (!company.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeVehicleAdd') and contains(text(), '" + company + "')]";
            webUIHelpers.clickElementAndSelect(AddVehicleLocators.treeCompany, By.xpath(xpathExpression));
        }
        webUIHelpers.clickElement(By.xpath("//*[@id=\"addVehicleModal\"]/div/div/div[1]/h5"));
        webUIHelpers.sleep(3000);
        if (!driver.isEmpty()) {
            String driverPath = "//li[text()='" + driver + "']";
            webUIHelpers.clickElementAndSelect(AddVehicleLocators.ddDriver, By.xpath(driverPath));
        }
        webUIHelpers.clickElement(By.xpath("//*[@id=\"addVehicleModal\"]/div/div/div[1]/h5"));

        if (!registerPlace.isEmpty()) {
            webUIHelpers.selectElement(AddVehicleLocators.ddRegisterPlace, registerPlace);
        }
        webUIHelpers.setText(AddVehicleLocators.tbBrand, brand);
        webUIHelpers.setText(AddVehicleLocators.tbVehicleLine, vehicleLine);
        webUIHelpers.setText(AddVehicleLocators.tbDesc, desc);
        if (!deviceType.isEmpty()) {
            webUIHelpers.selectElement(AddVehicleLocators.ddDeviceType, deviceType);
        }
        webUIHelpers.sleep(3000);
        if (!imei.isEmpty()) {
            String imeiPath = "//li[contains(text(), '" + imei + "')]";
            webUIHelpers.clickElementAndSelect(AddVehicleLocators.ddImei, By.xpath(imeiPath));
        }
        webUIHelpers.setText(AddVehicleLocators.tbSim, SIM);
    }
    public void createVehicle(
            String name,
            String transportType,
            String vehicleType,
            String plate,
            String company,
            String driver,
            String registerPlace,
            String brand,
            String vehicleLine,
            String desc,
            String deviceType,
            String imei,
            String SIM
    ){
        webUIHelpers.clickElement(btnAdd);
        fillInfoVehicle(name, transportType, vehicleType, plate, company, driver, registerPlace, brand, vehicleLine, desc, deviceType, imei, SIM);
        webUIHelpers.clickElement(AddVehicleLocators.btnSave);
        webUIHelpers.sleep(3000);
    }
    public void createWithoutRequiredField(){
        webUIHelpers.clickElement(btnAdd);
        webUIHelpers.sleep(3000);
        webUIHelpers.clickElement(AddVehicleLocators.btnSave);
        webUIHelpers.sleep(3000);
        String notiActual = webUIHelpers.getText(AddVehicleLocators.notiLogistic);
        String notiExpect = "Vui lòng chọn loại hình vận tải";
        Assert.assertEquals(notiActual, notiExpect);
    }

    public void createWithDuplicatePlate(){
        createVehicle(
                this.getVehicleName(1),
                this.getTransportType(1),
                this.getVehicleType(1),
                this.getPlate(1),
                this.getCompany(1),
                this.getDriver(1),
                this.getRegisterPlace(1),
                this.getBrand(1),
                this.getVehicleLine(1),
                this.getDesc(1),
                this.getDeviceType(1),
                this.getImei(1),
                this.getSIM(1));
        String actualAlert = webUIHelpers.getText(alertPopup);
        String expectAlert = "Biển số đã tồn tại. Vui lòng thử lại";
        Assert.assertEquals(actualAlert, expectAlert);
    }
    public void createWithDuplicateSIM(){
        createVehicle(
                this.getVehicleName(2),
                this.getTransportType(2),
                this.getVehicleType(2),
                this.getPlate(2),
                this.getCompany(2),
                this.getDriver(2),
                this.getRegisterPlace(2),
                this.getBrand(2),
                this.getVehicleLine(2),
                this.getDesc(2),
                this.getDeviceType(2),
                this.getImei(2),
                this.getSIM(2));
        String actualAlert = webUIHelpers.getText(alertPopup);
        String expectAlert = "Số SIM đã bị trùng. Vui lòng nhập lại";
        Assert.assertEquals(actualAlert, expectAlert);
    }
    public void createVehicleNotTranmits(){
        createVehicle(
                this.getVehicleName(3),
                this.getTransportType(3),
                this.getVehicleType(3),
                this.getPlate(3),
                this.getCompany(3),
                this.getDriver(3),
                this.getRegisterPlace(3),
                this.getBrand(3),
                this.getVehicleLine(3),
                this.getDesc(3),
                this.getDeviceType(3),
                this.getImei(3),
                this.getSIM(3));
        String actualAlert = webUIHelpers.getText(alertPopup);
        String expectAlert = "Tạo thành công";
        Assert.assertEquals(actualAlert, expectAlert);
    }
    public void createVehicleTranmits(){
        createVehicle(
                this.getVehicleName(4),
                this.getTransportType(4),
                this.getVehicleType(4),
                this.getPlate(4),
                this.getCompany(4),
                this.getDriver(4),
                this.getRegisterPlace(4),
                this.getBrand(4),
                this.getVehicleLine(4),
                this.getDesc(4),
                this.getDeviceType(4),
                this.getImei(4),
                this.getSIM(4));
        String actualAlert = webUIHelpers.getText(alertPopup);
        String expectAlert = "Tạo thành công";
        Assert.assertEquals(actualAlert, expectAlert);
    }
    public void createVehicleConnectDevice(){
        createVehicle(
                this.getVehicleName(5),
                this.getTransportType(5),
                this.getVehicleType(5),
                this.getPlate(5),
                this.getCompany(5),
                this.getDriver(5),
                this.getRegisterPlace(5),
                this.getBrand(5),
                this.getVehicleLine(5),
                this.getDesc(5),
                this.getDeviceType(5),
                this.getImei(5),
                this.getSIM(5));
        String actualAlert = webUIHelpers.getText(alertPopup);
        String expectAlert = "Tạo thành công";
        Assert.assertEquals(actualAlert, expectAlert);
    }

    public void suaBienSo(String bienso) throws Exception {
        webUIHelpers.clickElement(btnSua);
        webUIHelpers.sleep(1000);
        webUIHelpers.clearAndSetText(EditVehicleLocators.txtBienSo, bienso);
        webUIHelpers.sleep(300);
        webUIHelpers.clickElement(EditVehicleLocators.btnLuu);
        webUIHelpers.sleep(2000);
        // Assert.assertEquals(getErrMsg(), expectednoti);
    }

    public void editInfoVehicle(String tenphuongtien, String loaihinhvantai, String loaiphuongtien, String bienso,
                                String congty, String driver, String noidangkiem, String hang, String dongphuongtien,
                                String mota) {
        //helpers.sleep(3000);
        if (!tenphuongtien.isEmpty()) {
            webUIHelpers.clearAndSetText(EditVehicleLocators.txtTenPhuongtien, tenphuongtien);
        }
        if (!loaihinhvantai.isEmpty()) {
            webUIHelpers.selectElement(EditVehicleLocators.ddLoaiHinhVantai, loaihinhvantai);
        }
        webUIHelpers.sleep(3000);
        if (!loaiphuongtien.isEmpty()) {
            webUIHelpers.selectElement(EditVehicleLocators.ddLoaiPhuongtien, loaiphuongtien);
        }
        webUIHelpers.sleep(3000);
        webUIHelpers.clearAndSetText(EditVehicleLocators.txtBienSo, bienso);
        if (!congty.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeVehicleEdit') and contains(text(), '" + congty + "')]";
            webUIHelpers.clickElementAndSelect(EditVehicleLocators.ddCongTy, By.xpath(xpathExpression));
        }
        if (!noidangkiem.isEmpty()) {
            webUIHelpers.selectElement(EditVehicleLocators.ddNoiDangKiem, noidangkiem);
        }
        webUIHelpers.clearAndSetText(EditVehicleLocators.txtHang, hang);
        webUIHelpers.clearAndSetText(EditVehicleLocators.txtDongPhuongTien, dongphuongtien);
        webUIHelpers.clearAndSetText(EditVehicleLocators.txtMoTa, mota);
    }

    public void editCompany(String company){
        if (!company.isEmpty()) {
            String xpathExpression = "//a[contains(@id, 'jstreeVehicleEdit') and contains(text(), '" + company + "')]";
            webUIHelpers.clickElementAndSelect(EditVehicleLocators.ddCongTy, By.xpath(xpathExpression));
        }
    }

    public void editInfoDevice(String kieuthietbi, String mathietbi, String sim,
                               String ngaybatdau, String ngayketthuc, String trangthaidichvu) {
        if (!kieuthietbi.isEmpty()) {
            webUIHelpers.selectElement(EditVehicleLocators.ddKieuThietBi, kieuthietbi);
        }
        webUIHelpers.sleep(3000);
        if (!mathietbi.isEmpty()) {
            webUIHelpers.clearAndSetText(EditVehicleLocators.ddMaThietBi, mathietbi);
            String imeiPath = "//li[contains(text(), '" + mathietbi + "')]";
            webUIHelpers.clickElementAndSelect(EditVehicleLocators.ddMaThietBi, By.xpath(imeiPath));
        }
        webUIHelpers.sleep(3000);
        if (!sim.isEmpty()) {
            webUIHelpers.clearAndSetText(EditVehicleLocators.txtSIM, sim);
        }
        if (!ngaybatdau.isEmpty()) {
            webUIHelpers.clearAndSetText(EditVehicleLocators.dpkNgayBatDau, ngaybatdau);
        }
        if (!ngayketthuc.isEmpty()) {
            webUIHelpers.clearAndSetText(EditVehicleLocators.dpkNgayKetThuc, ngayketthuc);
        }
        if (!trangthaidichvu.isEmpty()) {
            webUIHelpers.selectElement(EditVehicleLocators.ddTrangThaiDV, trangthaidichvu);
        }
    }
    public void cancelConnect (String mathietbi){
        webUIHelpers.clearAndSetText(EditVehicleLocators.ddMaThietBi, mathietbi);
    }
    public void clickEditBtn(){
        webUIHelpers.clickElement(btnSua);
        webUIHelpers.sleep(3000);
    }

    public void verifyEditSuccessfull(){
        webUIHelpers.sleep(1000);
        String actualAlert = webUIHelpers.getTextInAlert(alertPopup);
        String expectAlert = "Cập nhật thành công";
        Assert.assertEquals(actualAlert, expectAlert);
    }
    public boolean isElementEnabled(By element) {
        return webUIHelpers.getElement(element).isEnabled();
    }
    public String getErrMsg() {
        return webUIHelpers.getText(alertPopup);
    }
    public String getErrBienSoRong() {
        return webUIHelpers.getText(EditVehicleLocators.notiBienSoRong);
    }

    public void deleteVehicle(String expectednoti){
        webUIHelpers.clickElement(btnXoa);
        webUIHelpers.sleep(2000);
        webUIHelpers.waitAndClick(btnXacNhanXoa);
        webUIHelpers.sleep(2000);
        Assert.assertEquals(getErrMsg(), expectednoti);

    }
    public void clickAssignBtn(){
        webUIHelpers.clickElement(btnAssign);
        webUIHelpers.sleep(3000);
    }
    public void selectDDVehicleAssignedUserStatus(String status){
        String xpathExpression = "//a[text()='" + status + "']/parent::li";
        webUIHelpers.clickElement(ddAssignUserStatus);
        webUIHelpers.clickElement(By.xpath(xpathExpression));
    }
    public void searchUserInPopupAssign(String username){
        webUIHelpers.setText(searchboxUser, username);
        webUIHelpers.sleep(5000);
        webUIHelpers.clickElement(btnSearchUser);
        webUIHelpers.sleep(5000);
    }
    public void assignUser(){
        webUIHelpers.clickElement(btnAssignUser);
        webUIHelpers.clickElement(btnConfirmAssign);
    }
    public void closePopupAssign(){
        webUIHelpers.clickElement(btnClose);
    }
}
