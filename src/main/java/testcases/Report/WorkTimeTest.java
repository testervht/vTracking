package testcases.Report;

import bases.DriverFactory;
import bases.ExcelHelpers;
import bases.WebUIHelpers;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ReportPages.AllReportPage;
import pages.ReportPages.TemperaturePage;
import pages.ReportPages.WorkTimePage;

public class WorkTimeTest {
    private WebDriver driver;
    private ExcelHelpers excelHelpers;
    private WebUIHelpers webUIHelpers;
    private WorkTimePage workTimePage;
    private AllReportPage allReportPage;
    @BeforeMethod
    public void setUpBrowser() throws Exception {
//        driver = new ChromeDriver();
        driver = DriverFactory.getDriver("chrome");
        webUIHelpers = new WebUIHelpers(driver);
        workTimePage = new WorkTimePage(driver);
        this.excelHelpers = new ExcelHelpers();
        System.out.println("Bao cao Thoi gian lam viec browser:" + driver);
    }

}
