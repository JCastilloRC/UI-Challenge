package webpages;

import drivermanager.DriverManager;
import drivermanager.WaitConditions;
import miscclasses.Movie;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import static org.apache.logging.log4j.LogManager.getLogger;
public class ActorPage extends BasePage{
    private static final Logger logger = getLogger(MainPage.class.getName());
    private final By actingTimelineBy = By.xpath("//table[@class='credit_group']//bdi");
    public ActorPage(DriverManager driverManager){
        super(driverManager);
    }
    public boolean actedInMovie(Movie aMovie){
        driverManager.fluentWait(actingTimelineBy, WaitConditions.PRESENT);
        By movieInActingTimeLineBy = By.xpath(
                "//table[@class='credit_group']//bdi[text()='"
                        +aMovie.getName()+"']");
        logger.info("Checking if movie appear in actor's time line...");
        return driverManager.mapToElements(movieInActingTimeLineBy).size()!= 0;
    }
}
