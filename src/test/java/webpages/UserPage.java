package webpages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.apache.logging.log4j.LogManager.getLogger;

public class UserPage extends BasePage {
    private static final Logger logger = getLogger(UserPage.class.getName());
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
    private final By usernameBy = By.xpath("//h2/a[contains(@href, '/u/')]");
    public UserPage(WebDriver driver){
        super(driver);
    }
    public String getUsernameHeader(){
        logger.info("Obtaining username header...");
        wait.until(ExpectedConditions.presenceOfElementLocated(usernameBy));
        return mapToElement(usernameBy).getText();
    }
}
