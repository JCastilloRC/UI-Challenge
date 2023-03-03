package webpages;

import miscclasses.Movie;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


import static org.apache.logging.log4j.LogManager.getLogger;

public class ActorPage extends BasePage{
    private static final Logger logger = getLogger(MainPage.class.getName());
    private final By actingTimelineBy = By.xpath("//table[@class='credit_group']//bdi");
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
    public ActorPage(WebDriver driver){
        super(driver);
    }
    public boolean actedInMovie(Movie aMovie){
        wait.until(ExpectedConditions.presenceOfElementLocated(actingTimelineBy));
        By movieInActingTimeLineBy = By.xpath(
                "//table[@class='credit_group']//bdi[text()='"
                        +aMovie.getName()+"']");
        logger.info("Checking if movie appear in actor's time line...");
        return driver.findElements(movieInActingTimeLineBy).size() != 0;
    }
}
