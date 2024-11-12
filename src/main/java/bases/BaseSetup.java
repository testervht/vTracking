package bases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseSetup extends DriverFactory{
    public static ExtentReports extent = new ExtentReports();
    @BeforeSuite
    public void generateReports() throws  InterruptedException {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        extent.attachReporter(spark);
    }
    //test thử xem sao
    @AfterSuite
    public void cleanReport(){
        extent.flush();
    }
}
