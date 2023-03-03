package webpages;

import miscclasses.User;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.apache.logging.log4j.LogManager.getLogger;

public class LoginPage extends BasePage {
    private static final Logger logger = getLogger(LoginPage.class.getName());
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
    private final By inputUsernameBy = By.xpath("//input[@id='username']");
    private final By inputPasswordBy = By.xpath("//input[@id='password']");
    private final By loginButtonBy = By.xpath("//input[@id='login_button']");
    private final By redErrorMessageBy = By.xpath("//div[@class='error_status card']//h2");
    private final By listErrMessagesBy = By.xpath("//div[@class='error_status card']//li");
    public LoginPage(WebDriver driver){
        super(driver);
    }
    public UserPage successfullyLogin(User someUser){
        logger.info("Typing valid username and password...");
        wait.until(ExpectedConditions.presenceOfElementLocated(inputUsernameBy));
        mapToElement(inputUsernameBy).sendKeys(someUser.getUsername());
        wait.until(ExpectedConditions.presenceOfElementLocated(inputPasswordBy));
        mapToElement(inputPasswordBy).sendKeys(someUser.getPassword());
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonBy));
        logger.info("Clicking on 'login' button...");
        mapToElement(loginButtonBy).click();
        return new UserPage(driver);
    }

    public LoginPage failedLogin(User someUser){
        logger.info("Typing valid username and invalid password...");
        wait.until(ExpectedConditions.presenceOfElementLocated(inputUsernameBy));
        mapToElement(inputUsernameBy).sendKeys(someUser.getUsername());
        wait.until(ExpectedConditions.presenceOfElementLocated(inputPasswordBy));
        mapToElement(inputPasswordBy).sendKeys(someUser.getPassword().repeat(2));
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonBy));
        logger.info("Clicking on 'login' button...");
        mapToElement(loginButtonBy).click();
        return this;
    }
    public boolean hasRedError(){
        wait.until(ExpectedConditions.presenceOfElementLocated(redErrorMessageBy));
        return driver.findElement(redErrorMessageBy).isDisplayed();
    }
    public boolean hasTwoErrors(){
        wait.until(ExpectedConditions.presenceOfElementLocated(listErrMessagesBy));
        return  driver.findElements(listErrMessagesBy).size() == 2;
    }
}
