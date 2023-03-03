package webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected final int WAIT_TIME = 5;
    public BasePage (WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    protected WebElement mapToElement(By locator){
        return driver.findElement(locator);
    }
    protected List<WebElement> mapToElements(By locator){
        return driver.findElements(locator);
    }
}
