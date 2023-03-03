package webpages;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.apache.logging.log4j.LogManager.getLogger;

public class SearchResultsPage extends BasePage{
    private static final Logger logger = getLogger(MainPage.class.getName());
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
    private final By movieTitlesBy = By.xpath("//a[@class='result']//h2");
    public SearchResultsPage(WebDriver driver){
        super(driver);
    }
    public String getFirstMovieTitle(){
        logger.info("Getting the first movie title");
        wait.until(ExpectedConditions.presenceOfElementLocated(movieTitlesBy));
        return mapToElements(movieTitlesBy).get(0).getText();
    }

}
