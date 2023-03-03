package webpages;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.apache.logging.log4j.LogManager.getLogger;

public class MoviePage extends BasePage{
    private static final Logger logger = getLogger(MainPage.class.getName());
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
    private final By genresSpanBy = By.xpath("//span[@class='genres']");
    private final By movieTitleBy = By.xpath("//h2/a[contains(@href,'/movie/')]");
    private final By castBy = By.xpath("//div[@id='cast_scroller']//img");
    public MoviePage(WebDriver driver){
        super(driver);
    }
    public MoviePage wichPage(){
        wait.until(ExpectedConditions.presenceOfElementLocated(movieTitleBy));
        logger.info("The movie title is: " +mapToElement(movieTitleBy).getText());
        return this;
    }
    public String getGenres(){
        wait.until(ExpectedConditions.presenceOfElementLocated(genresSpanBy));
        return mapToElement(genresSpanBy).getText();
    }
    public ActorPage goToActorPage(int idx){
        wait.until(ExpectedConditions.elementToBeClickable(castBy));
        logger.info("Clicking on the actor card #" +(idx+1));
        mapToElements(castBy).get(idx).click();
        return new ActorPage(driver);
    }
}
