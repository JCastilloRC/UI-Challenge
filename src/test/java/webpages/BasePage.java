package webpages;


import drivermanager.DriverManager;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected DriverManager driverManager;
    public BasePage (DriverManager driverManager){
        this.driverManager = driverManager;
        PageFactory.initElements(driverManager.getDriver(), this);
    }
}
