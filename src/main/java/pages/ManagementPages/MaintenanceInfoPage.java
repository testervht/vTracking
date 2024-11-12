package pages.ManagementPages;

import bases.WebUIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MaintenanceInfoPage {
    public MaintenanceInfoPage(WebDriver driver) {
        this.driver = driver;
        this.webUIHelpers = new WebUIHelpers(driver);
    }
    private final WebDriver driver;
    private WebUIHelpers webUIHelpers;

    //Các button chức năng
    private By btnAdd = By.cssSelector("[onclick=\"addVehicleMaintenance()\"]");
    private By btnEdit = By.xpath("//*[@id=\"vehicleMaintenanceEdit_table\"]/tbody/tr[1]/td[1]/img[1]");
    private By btnDel = By.xpath("//*[@id=\"vehicleMaintenanceEdit_table\"]/tbody/tr[1]/td[1]/img[2]");
    private By btnConfirmDel = By.cssSelector("[onclick='processDeleteVehicleMaintenance()']");
}
//push code
