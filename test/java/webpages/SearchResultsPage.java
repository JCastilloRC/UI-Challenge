package webpages;
import drivermanager.DriverManager;
import drivermanager.WaitConditions;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static org.apache.logging.log4j.LogManager.getLogger;

public class SearchResultsPage extends BasePage{
    private static final Logger logger = getLogger(MainPage.class.getName());
    private final By movieTitlesBy = By.xpath("//a[@class='result']//h2");
    public SearchResultsPage(DriverManager driverManager){
        super(driverManager);
    }
    public String getFirstMovieTitle(){
        logger.info("Getting the first movie title");
        driverManager.fluentWait(movieTitlesBy, WaitConditions.PRESENT);
        return driverManager.mapToElements(movieTitlesBy).get(0).getText();
    }

}
