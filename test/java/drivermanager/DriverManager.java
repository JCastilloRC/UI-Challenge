package drivermanager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class DriverManager {
    protected WebDriver webDriver;
    protected WebDriverWait wait;
    protected void setDriver(WebDriver webDriver){
        this.webDriver = webDriver;
        wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(5));
    }
    public WebDriver getDriver() {
        return webDriver;
    }
    public void initTest(){
        webDriver.manage()
                .window()
                .maximize();
        webDriver.navigate()
                .to("http://themoviedb.org/");
    }
    public void deleteCookies() {
        webDriver.manage().deleteAllCookies();
    }
    
    public void quitDriver() {
        webDriver.quit();
    }

    public void fluentWait(By elementBy, WaitConditions condition) {
        switch (condition) {
            case PRESENT -> wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elementBy));
            case CLICKABLE -> wait.until(ExpectedConditions.elementToBeClickable(elementBy));
            case REFRESH -> {
                WebElement element = mapToElement(elementBy);
                wait.until(ExpectedConditions
                        .stalenessOf(element)
                );
            }
        }
    }
    public void goToURL(String url) {
        webDriver.navigate().to(url);
    }
    
    public void clickElement(By elementBy) {
        WebElement element = mapToElement(elementBy);
        scrollToView(element);
        element.click();
    }
    
    public void clickElement(By elementBy, int idx) {
        WebElement element = mapToElements(elementBy).get(idx);
        scrollToView(element);
        element.click();
    }
    protected void scrollToView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor)webDriver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    public void sendKeysTo(By elementBy, String input) {
        mapToElement(elementBy).sendKeys(input);
    }

    
    public WebElement mapToElement(By locator){
        return webDriver.findElement(locator);
    }
    
    public List<WebElement> mapToElements(By locator){
        return webDriver.findElements(locator);
    }
}
