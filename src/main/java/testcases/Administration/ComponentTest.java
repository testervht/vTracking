package testcases.Administration;

import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AdministrationPages.ComponentPage;
import pages.LoginPage;

public class ComponentTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ComponentPage componentPage;
    @BeforeMethod
    public void setUpBrowser() throws Exception {
        driver = DriverFactory.getDriver("chrome");
        componentPage = new ComponentPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.loginDefault();
        componentPage.Navigation();
        System.out.println("Add Component browser:" + driver);
    }
    @Test
    public void AddComponentSuccess(){
        componentPage.AddComponentSuccess();
    }
    @Test
    public void AddComponentMissCompulsory(){
        componentPage.AddComponentMissCompulsory("hien", "", "", "1", "10000");
    }
    @Test
    public void AddComponentDuplicateName(){
        componentPage.AddComponentDuplicateName();
    }
    @Test
    public void SearchNoData(){
        componentPage.SearchNoData();
    }
    @Test
    public void SearchTextBox(){
        componentPage.SearchTextBox("mabe");
    }
    @Test
    public void EditComponentMissTenLinhKien(){
        componentPage.EditComponentMissTenLinhKien();
    }
    @Test
    public void EditComponentMissBaoTriTheoKM(){
        componentPage.EditComponentMissBaoTriTheoKM();
    }
    @Test
    public void EditComponentMissBaoTriTheoThang(){
        componentPage.EditComponentMissBaoTriTheoThang();
    }
    @Test
    public void EditComponentSuccess(){
        componentPage.EditComponentSuccess();
    }
    @Test
    public void EditComponentDuplicateName(){
        componentPage.EditComponentDuplicateName();
    }
    @Test
    public void DeleteComponent(){
        componentPage.DeleteComponent("123");
    }
//    @AfterMethod
//    public void close(){
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
