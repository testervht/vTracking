package testcases.History;

import bases.BaseSetup;
import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.History.ImagePage;
import pages.History.RoutePage;
import pages.HistoryPage;
import pages.LoginPage;

public class ImageTest extends BaseSetup {
    private WebDriver driver;
    private HistoryPage historyPage;
    private LoginPage loginPage;
    private ImagePage imagePage;
    private RoutePage routePage;

    @BeforeMethod
    public void SetUp() throws InterruptedException {
        driver = DriverFactory.getDriver("chrome");
        historyPage = new HistoryPage(driver);
        loginPage = new LoginPage(driver);
        routePage = new RoutePage(driver);
        imagePage = new ImagePage(driver);

        loginPage.loginDefault();
        historyPage.navigateToRoutePage();
        historyPage.navigateToImagePage();
    }
    @Test(priority = 1)
    public void searchVehiclePlate(){
        imagePage.clickDDChoosePlate();
        imagePage.setSearchKey(routePage.getPlate(24));
        imagePage.verifyResult(routePage.getPlate(24));
        imagePage.clickResult();
        imagePage.verifyChoosePlate(routePage.getId(24));
        imagePage.tickCheckboxPlate(routePage.getId(24));
    }
    @Test(priority = 2)
    public void verifySearchWithoutImg(){
        imagePage.clickDDChoosePlate();
        imagePage.setSearchKey(routePage.getPlate(24));
        imagePage.verifyResult(routePage.getPlate(24));
        imagePage.clickResult();
        imagePage.tickCheckboxPlate(routePage.getId(24));
        imagePage.clickBtnChoosePlate();
        imagePage.selectStartDate("01/08/2024 00:00");
        imagePage.selectEndDate("01/08/2024 23:59");
        imagePage.selectChanel("Kênh 1");
        imagePage.clickBtnSearch();
        imagePage.verifySearchWithoutImg();
    }
    @Test(priority = 3)
    public void verifyNumOfImageHistory(){
        imagePage.clickDDChoosePlate();
        imagePage.setSearchKey(routePage.getPlate(24));
        imagePage.verifyResult(routePage.getPlate(24));
        imagePage.clickResult();
        imagePage.tickCheckboxPlate(routePage.getId(24));
        imagePage.clickBtnChoosePlate();
        imagePage.selectStartDate("10/09/2024 11:00");
        imagePage.selectEndDate("10/09/2024 12:00");
        imagePage.selectChanel("Kênh 1");
        imagePage.verifyNumAndInfoImageWithFilters("Kênh 1", "10/09/2024 11:00", "10/09/2024 12:00");
        imagePage.getInfoImage(10);
    }
    @Test(priority = 3)
    public void verifyPopupImage(){
        imagePage.clickDDChoosePlate();
        imagePage.setSearchKey(routePage.getPlate(24));
        imagePage.verifyResult(routePage.getPlate(24));
        imagePage.clickResult();
        imagePage.tickCheckboxPlate(routePage.getId(24));
        imagePage.clickBtnChoosePlate();
        imagePage.selectStartDate("10/09/2024 11:00");
        imagePage.selectEndDate("10/09/2024 12:00");
        imagePage.selectChanel("Kênh 1");
        imagePage.clickBtnSearch();
        imagePage.clickThumbnail(10);
        imagePage.verifyInfoPopupImage(routePage.getPlate(24));
    }
    @Test(priority = 4)
    public void verifyDownload1Image(){
        imagePage.clickDDChoosePlate();
        imagePage.setSearchKey(routePage.getPlate(24));
        imagePage.verifyResult(routePage.getPlate(24));
        imagePage.clickResult();
        imagePage.tickCheckboxPlate(routePage.getId(24));
        imagePage.clickBtnChoosePlate();
        imagePage.selectStartDate("10/09/2024 11:00");
        imagePage.selectEndDate("10/09/2024 12:00");
        imagePage.selectChanel("Kênh 1");
        imagePage.clickBtnSearch();
        imagePage.clickDownloadIcon(10);
        imagePage.verifyDownload1Image();
    }
    @Test(priority = 4)
    public void verifyDownloadAllImage(){
        imagePage.clickDDChoosePlate();
        imagePage.setSearchKey(routePage.getPlate(24));
        imagePage.verifyResult(routePage.getPlate(24));
        imagePage.clickResult();
        imagePage.tickCheckboxPlate(routePage.getId(24));
        imagePage.clickBtnChoosePlate();
        imagePage.selectStartDate("09/09/2024 11:00");
        imagePage.selectEndDate("10/09/2024 12:00");
        imagePage.selectChanel("Kênh 1");
        imagePage.clickBtnSearch();
        imagePage.clickChooseAllBtn();
        imagePage.clickDownloadBtn();
        imagePage.verifyDownloadZipAndImageCount();
    }
}
