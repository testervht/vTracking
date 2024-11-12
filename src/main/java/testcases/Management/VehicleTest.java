package testcases.Management;

import bases.BaseSetup;
import bases.DriverFactory;
import bases.WebUIHelpers;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagePage;
import pages.ManagementPages.VehiclePage;

public class VehicleTest extends BaseSetup {
    private WebDriver driver;
    private VehiclePage vehiclePage;
    private ManagePage managePage;
    public WebUIHelpers helpers;
    private final static String KEYSEARCH = "89A12345";
    private final static String NOTIMISSEDNUMBERPLATE = "Vui lòng nhập biển số xe";
    private final static String NOTIDUPPLICATENUMBERPLATE = "Biển số đã tồn tại. Vui lòng thử lại";
    private final static String NOTIINVALIDNUMBERPLATE = "Biển số không đúng định dạng";
    private final static String ERRMSG = "Phương tiện chỉ có thể xóa khi phương tiện chưa được đấu nối với thiết bị";
    private final static String SUCCESSMSG = "Xóa thành công";
    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        vehiclePage = new VehiclePage(driver);
        managePage = new ManagePage(driver);
        managePage.navigateToManagePage();
        managePage.navigateToVehicle();
        helpers = new WebUIHelpers(driver);
    }
    @Test(priority = 1)
    public void searchWithoutValue(){
        vehiclePage.search("", "", "", "","","xwqeqwdasd");
        vehiclePage.verifySearchWithoutValue("Chưa có thông tin phương tiện", "Tổng cộng: 0 phương tiện");
    }
    @Test(priority = 2)
    public void searchByCompany(){
        vehiclePage.search("Công ty test 21/11/2024", "", "", "", "", "");
        vehiclePage.verifySearchByCompany();
    }
    @Test(priority = 3)
    public void searchByType(){
        vehiclePage.search("", "Phương tiện khác", "", "", "", "");
        vehiclePage.verifySearchByType();
    }
    @Test(priority = 4)
    public void searchByPlate(){
        vehiclePage.searchByPlate();
    }
    @Test(priority = 5)
    public void searchBySIM(){
        vehiclePage.searchBySIM();
    }
    @Test(priority = 6)
    public void searchByImei(){
        vehiclePage.searchByImei();
    }
    @Test(priority = 7)
    public void searchByDate(){
        vehiclePage.searchByDate();
    }
    @Test(priority = 8)
    public void searchByMultiConditions(){
        vehiclePage.searchByMultiConditions();
    }
    @Test(priority = 9)
    public void createWithoutRequiredField(){
        vehiclePage.createWithoutRequiredField();
    }
    @Test(priority = 10)
    public void createWithDuplicatePlate(){
        vehiclePage.createWithDuplicatePlate();
    }
    @Test(priority = 11)
    public void createVehicleNotTranmits(){
        vehiclePage.createVehicleNotTranmits();
    }
    @Test(priority = 12)
    public void createVehicleTranmits(){
        vehiclePage.createVehicleTranmits();
    }
    @Test(priority = 13)
    public void createVehicleConnectDevice(){
        vehiclePage.createVehicleConnectDevice();
    }
    @Test(priority = 14)
    public void createWithDuplicateSIM(){
        vehiclePage.createWithDuplicateSIM();
    }
    @Test(priority = 15)
    public void testNhapThieuThongTinPhuongTien() throws Exception {
        vehiclePage.search("", "", "", "", "Biển số xe", "1234");
        vehiclePage.suaBienSo("");
        String expectNoti ="Vui lòng nhập biển số xe";
        String actualNoti = vehiclePage.getErrBienSoRong();
        boolean notiBienSoTrong =vehiclePage.isElementEnabled(VehiclePage.EditVehicleLocators.notiBienSoRong);
        if(notiBienSoTrong){
            Assert.assertEquals(actualNoti, expectNoti);
        }
    }

    @Test(priority = 16)
    public void testNhapTrungBienSo() throws Exception {
        vehiclePage.searchVehicle(KEYSEARCH);
        vehiclePage.suaBienSo("89A-77712");
        String expectNoti = "Biển số đã tồn tại. Vui lòng thử lại";
        String actualNoti = vehiclePage.getErrMsg();
        Assert.assertEquals(actualNoti, expectNoti);
    }
    @Test(priority = 3)
    public void testSaiDinhDangBienSoCoTruyenTCDB() throws Exception {
        vehiclePage.searchVehicle(KEYSEARCH);
        vehiclePage.suaBienSo("89A-aaaaa");
        String expectNoti = "Biển số không hợp lệ";
        String actualNoti = vehiclePage.getErrMsg();
        Assert.assertEquals(actualNoti, expectNoti);
    }

    @Test(priority = 4)
    public void testDungDinhDangBienSoCoTruyenTCDB() throws Exception {
        vehiclePage.searchVehicle("89A-1234");
        vehiclePage.suaBienSo("89A-12340");
        String expectNoti = "Cập nhật thành công";
        String actualNoti = vehiclePage.getErrMsg();
        Assert.assertEquals(actualNoti, expectNoti);

    }

    @Test(priority = 5)
    public void testThietBiNhapTuBccs() throws Exception {
        vehiclePage.searchVehicle("29V7-9712");
        helpers.clickElement(VehiclePage.btnSua);
        helpers.sleep(1000);
        boolean kieuThietBi = vehiclePage.isElementEnabled(VehiclePage.EditVehicleLocators.ddKieuThietBi);
        boolean maThietBi = vehiclePage.isElementEnabled(VehiclePage.EditVehicleLocators.ddMaThietBi);
        boolean soSim = vehiclePage.isElementEnabled(VehiclePage.EditVehicleLocators.txtSIM);

        boolean ngayBatDau = vehiclePage.isElementEnabled(VehiclePage.EditVehicleLocators.dpkNgayBatDau);
        boolean ngayKetThuc = vehiclePage.isElementEnabled(VehiclePage.EditVehicleLocators.dpkNgayKetThuc);
        boolean trangThai = vehiclePage.isElementEnabled(VehiclePage.EditVehicleLocators.ddTrangThaiDV);

        Assert.assertTrue(kieuThietBi && kieuThietBi && kieuThietBi
                && !ngayBatDau && !ngayKetThuc && !trangThai);
        if (kieuThietBi && kieuThietBi && kieuThietBi && !ngayBatDau && !ngayKetThuc && !trangThai){
            vehiclePage.editInfoDevice("","","",
                    "16/06/2024", "16/07/2025", "Đang hoạt động");
            String alertActual = helpers.getTextInAlert(VehiclePage.alertPopup);
            String expectAlert = "Cập nhật thành công";
            Assert.assertEquals(alertActual,expectAlert);
        }
        else {
            System.out.println("Lỗi sửa thiết bị mBCCS");
        }
    }
    @Test(priority = 6)
    public void suaPtTruyenCDBThanhCong() throws Exception {
        vehiclePage.searchVehicle("89D-1234");
        helpers.clickElement(VehiclePage.btnSua);
        helpers.sleep(1000);
        vehiclePage.editInfoVehicle("Test 123","Taxi tải (truyền dữ liệu lên CĐB)","Rơ moóc (truyền dữ liệu lên CĐB)","89D-12346",
                "","","An Giang","Toyota", "Ô tô", "test auto");
        vehiclePage.editInfoDevice("","","","","","Tạm dừng");
        helpers.clickElement(VehiclePage.EditVehicleLocators.btnLuu);
        String alertActual = helpers.getTextInAlert(VehiclePage.alertPopup);
        String expectAlert = "Cập nhật thành công";
        Assert.assertEquals(alertActual,expectAlert);
    }
    @Test(priority = 7)
    public void suaPtKhongTruyenCDBThanhCong() throws Exception {
        vehiclePage.searchVehicle("89E1-706");
        helpers.clickElement(VehiclePage.btnSua);
        helpers.sleep(1000);
        vehiclePage.editInfoVehicle("Test 123","","Rơ moóc (truyền dữ liệu lên CĐB)","89E17067",
                "","","Hưng Yên","Toyota", "Ô tô", "test auto");
        vehiclePage.editInfoDevice("Giám sát hành trình","20062024","","16/06/2024","17/06/2025","Đang hoạt động");
        helpers.sleep(3000);
        helpers.clickElement(VehiclePage.EditVehicleLocators.btnLuu);
        String alertActual = helpers.getTextInAlert(VehiclePage.alertPopup);
        String expectAlert = "Cập nhật thành công";
        Assert.assertEquals(alertActual,expectAlert);
    }
    @Test(priority = 59, description = "Kiem tra dau noi thiet bi thanh cong")
    public void upCode_59(){
        vehiclePage.search("", "", "", "", "Biển số xe", "89E17067");
        vehiclePage.clickEditBtn();
        vehiclePage.editInfoDevice("Giám sát hành trình", "864180051812362", "", "09/09/2024", "30/12/2024", "Đang hoạt động");
        helpers.sleep(3000);
        helpers.clickElement(VehiclePage.EditVehicleLocators.btnLuu);
        vehiclePage.verifyEditSuccessfull();
    }
    @Test(priority = 60, description = "Kiem tra huy dau noi thiet bi thanh cong")
    public void upCode_60(){
        vehiclePage.search("", "", "", "", "Biển số xe", "89E17067");
        vehiclePage.clickEditBtn();
        vehiclePage.editInfoDevice("", "", "", "", "", "");
        helpers.sleep(3000);
        helpers.clickElement(VehiclePage.EditVehicleLocators.btnLuu);
        vehiclePage.verifyEditSuccessfull();
    }
    @Test(priority = 61, description = "Kiem tra dieu chuyen thiet bi thanh cong")
    public void upCode_61(){
        vehiclePage.search("", "", "", "", "Biển số xe", "89E17067");
        vehiclePage.clickEditBtn();
        vehiclePage.editCompany("Công ty con của Hiền232");
        helpers.sleep(3000);
        helpers.clickElement(VehiclePage.EditVehicleLocators.btnLuu);
        vehiclePage.verifyEditSuccessfull();
    }
    @Test(priority = 1)
    public void xoaPtChuaThietBi() throws Exception {
        vehiclePage.searchVehicle("89E1-706");
        vehiclePage.deleteVehicle(ERRMSG);
    }
    @Test(priority = 1)
    public void xoaPtKhongChuaThietBi() throws Exception {
        vehiclePage.searchVehicle("89F1-12236");
        helpers.sleep(3000);
        vehiclePage.deleteVehicle(SUCCESSMSG);
    }
    @Test(priority = 1)
    public void assignUser(){
        vehiclePage.searchVehicle("89F1-12236");
        vehiclePage.clickAssignBtn();
        vehiclePage.selectDDVehicleAssignedUserStatus("Người dùng chưa gán");
        vehiclePage.searchUserInPopupAssign("");
        vehiclePage.assignUser();
        vehiclePage.closePopupAssign();
        managePage.navigateToUser();
    }
//    @AfterMethod
//    public void close(){
//        quitDriver();
//    }
}
