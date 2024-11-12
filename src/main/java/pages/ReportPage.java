package pages;

import bases.WebUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReportPage {
    public ReportPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
        this.loginPage = new LoginPage(driver);
    }
    private WebDriver driver;
    private WebUIHelpers webUIHelpers;
    private LoginPage loginPage;
    private By reportButton = By.cssSelector("a[href=\"/JourneyReports\"]");
    private By drivingTimeButton = By.cssSelector("a[href=\"/ContinuousDrivingTimeReports\"]");
    private By speedButton = By.cssSelector("a[href=\"/SpeedReports\"]");
    private By overSpeedButton = By.cssSelector("a[href=\"/OverSpeedReports\"]");
    private By parkButton = By.cssSelector("a[href=\"/ParkReports\"]");
    private By overviewButton = By.cssSelector("a[href=\"/OverviewReports\"]");
    private By activityDetailButton = By.cssSelector("a[href=\"/ActivityDetailReports\"]");
    private By totalCarButton = By.xpath("//span[contains(text(),'Tổng hợp theo xe')]");

    public void navigateToReportPage(){
        webUIHelpers.clickElement(reportButton);
    }
    public void navigateToReport(){
        webUIHelpers.clickElement(reportButton);
    }
    public void navigateToDrivingTime(){
        webUIHelpers.clickElement(drivingTimeButton);
    }
    public void navigateToSpeedReports(){
        webUIHelpers.clickElement(speedButton);
    }
    public void navigateToOverSpeedReports(){
        webUIHelpers.clickElement(overSpeedButton);
    }
    public void navigateToOverParkReports(){
        webUIHelpers.clickElement(parkButton);
    }
    public void navigateToOverviewReports(){
        webUIHelpers.clickElement(overviewButton);
    }
    public void navigateToActivityDetailReports(){
        webUIHelpers.clickElement(activityDetailButton);
    }
    public void navigateToTotalCarReports(){
        webUIHelpers.clickElement(totalCarButton);
    }
    public void verifyReportTypes(List<String> expectedReports) {
        List<WebElement> reportLinks = driver.findElements(By.cssSelector(".side-bar .side-bar-option a"));

        Set<String> actualReports = new HashSet<>();
        Set<String> expectedReportSet = new HashSet<>(expectedReports);

        for (WebElement link : reportLinks) {
            String linkText = link.findElement(By.tagName("span")).getText().trim();
            actualReports.add(linkText);
        }

        Set<String> unexpectedReports = new HashSet<>(actualReports);
        unexpectedReports.removeAll(expectedReportSet);

        if (!unexpectedReports.isEmpty()) {
            System.out.println("Unexpected report(s) found:");
            for (String report : unexpectedReports) {
                System.out.println(" - " + report);
            }
        }

        Set<String> missingReports = new HashSet<>(expectedReportSet);
        missingReports.removeAll(actualReports);

        if (!missingReports.isEmpty()) {
            System.out.println("Missing report(s):");
            for (String report : missingReports) {
                System.out.println(" - " + report);
            }
        }

        if (!unexpectedReports.isEmpty() || !missingReports.isEmpty()) {
            throw new AssertionError("Mismatch between expected and actual reports.");
        }
    }
}
