package webpages;

import drivermanager.DriverManager;
import drivermanager.WaitConditions;
import miscclasses.User;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import static org.apache.logging.log4j.LogManager.getLogger;

public class LoginPage extends BasePage {
    private static final Logger logger = getLogger(LoginPage.class.getName());
    private final By inputUsernameBy = By.xpath("//input[@id='username']");
    private final By inputPasswordBy = By.xpath("//input[@id='password']");
    private final By loginButtonBy = By.xpath("//input[@id='login_button']");
    private final By redErrorMessageBy = By.xpath("//div[@class='error_status card']//h2");
    private final By listErrMessagesBy = By.xpath("//div[@class='error_status card']//li");
    public LoginPage(DriverManager driverManager){
        super(driverManager);
    }
    public UserPage successfullyLogin(User someUser){
        logger.info("Typing valid username and password...");
        driverManager.fluentWait(inputUsernameBy, WaitConditions.PRESENT);
        driverManager.sendKeysTo(inputUsernameBy, someUser.getUsername());
        driverManager.fluentWait(inputPasswordBy, WaitConditions.PRESENT);
        driverManager.sendKeysTo(inputPasswordBy, someUser.getPassword());
        driverManager.fluentWait(loginButtonBy, WaitConditions.CLICKABLE);
        logger.info("Clicking on 'login' button...");
        driverManager.clickElement(loginButtonBy);
        return new UserPage(driverManager);
    }
    public LoginPage failedLogin(User someUser){
        logger.info("Typing valid username and invalid password...");
        driverManager.fluentWait(inputUsernameBy, WaitConditions.PRESENT);
        driverManager.sendKeysTo(inputUsernameBy, someUser.getUsername());
        driverManager.fluentWait(inputPasswordBy, WaitConditions.PRESENT);
        driverManager.sendKeysTo(inputPasswordBy, someUser.getPassword().repeat(2));
        driverManager.fluentWait(loginButtonBy, WaitConditions.CLICKABLE);
        logger.info("Clicking on 'login' button...");
        driverManager.clickElement(loginButtonBy);
        return this;
    }
    public boolean hasRedError(){
        try{
            driverManager.fluentWait(redErrorMessageBy, WaitConditions.PRESENT);
            return true;
        }catch(Exception TimeoutException){
            return false;
        }
    }
    public boolean hasTwoErrors(){
        try{
            driverManager.fluentWait(listErrMessagesBy, WaitConditions.PRESENT);
            return driverManager.mapToElements(listErrMessagesBy).size() == 2;
        }catch(Exception TimeoutException){
            return false;
        }
    }
}
