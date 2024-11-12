package testcases.Administration;

import bases.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AdministrationPages.TransportPage;
import pages.LoginPage;

public class TransportTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private TransportPage transportPage;
    @BeforeMethod
    public void setUpBrowser() throws Exception {
        driver = DriverFactory.getDriver("chrome");
        transportPage = new TransportPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.loginDefault();
        transportPage.Navigation();
        System.out.println("Add transport browser:" + driver);
    }
    @Test
    public void AddTransportSuccess(){
        transportPage.AddTransportSuccess();
    }
//    @Test
//    public void AddTransportMissName(){
//        addTransportPage.AddTransportMissName();
//    }
    @Test
    public void AddTransportMissCompulsory(){
        transportPage.AddTranSportMissCompulsory("", "1004");
    }
    @Test
    public void AddTransportDuplicateName(){
        transportPage.AddTransportDuplicateName();
    }
    @Test
    public void AddTransportDuplicateValue(){
        transportPage.AddTransportMissValue();
    }
    @Test
    public void SearchNoData(){
        transportPage.SearchNoData();
    }
    @Test
    public void SearchTextbox(){
        transportPage.SearchTextBox("Taxi");
    }
    @Test
    public void EditTransportMissCompulsory(){
        transportPage.EditTransportMissCompulsory("545465465");
    }
    @Test
    public void EditTransportDuplicateName(){
        transportPage.EditTransportDuplicateName("545465465", "Hien");
    }
    @Test
    public void EditTransportSuccess(){
        transportPage.EditTransportSuccess("545465465", "Hien4");
    }
    @Test
    public void DeleteTransport(){
        transportPage.DeleteTransport("TAXI");
    }

}
