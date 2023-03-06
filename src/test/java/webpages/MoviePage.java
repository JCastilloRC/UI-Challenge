package webpages;
import drivermanager.DriverManager;
import drivermanager.WaitConditions;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import static org.apache.logging.log4j.LogManager.getLogger;

public class MoviePage extends BasePage{
    private static final Logger logger = getLogger(MainPage.class.getName());
    private final By genresSpanBy = By.xpath("//span[@class='genres']");
    private final By movieTitleBy = By.xpath("//h2/a[contains(@href,'/movie/')]");
    private final By castBy = By.xpath("//div[@id='cast_scroller']//img");
    public MoviePage(DriverManager driverManager){
        super(driverManager);
    }
    public MoviePage wichPage(){
        driverManager.fluentWait(movieTitleBy, WaitConditions.PRESENT);
        logger.info("The movie title is: " +driverManager.mapToElement(movieTitleBy).getText());
        return this;
    }
    public String getGenres(){
        driverManager.fluentWait(genresSpanBy, WaitConditions.PRESENT);
        return driverManager.mapToElement(genresSpanBy).getText();
    }
    public ActorPage goToActorPage(int idx){
        driverManager.fluentWait(castBy, WaitConditions.CLICKABLE);
        logger.info("Clicking on the actor card #" +(idx+1));
        driverManager.clickElement(castBy, idx);
        return new ActorPage(driverManager);
    }
}
