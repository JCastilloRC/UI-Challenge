package webpages;

import drivermanager.DriverManager;
import drivermanager.WaitConditions;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import static org.apache.logging.log4j.LogManager.getLogger;

public class UserPage extends BasePage {
    private static final Logger logger = getLogger(UserPage.class.getName());
    private final By usernameBy = By.xpath("//h2/a[contains(@href, '/u/')]");
    public UserPage(DriverManager driverManager){
        super(driverManager);
    }
    public String getUsernameHeader(){
        logger.info("Obtaining username header...");
        driverManager.fluentWait(usernameBy, WaitConditions.PRESENT);
        return driverManager.mapToElement(usernameBy).getText();
    }
}
