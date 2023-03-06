package webpages;

import drivermanager.DriverManager;
import drivermanager.WaitConditions;
import miscclasses.Movie;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import static org.apache.logging.log4j.LogManager.getLogger;

public class MainPage extends BasePage {
    private static final Logger logger = getLogger(MainPage.class.getName());
    private final By loginButtonBy = By.xpath("//li/a[@href='/login']"); //didn't like this one
    private final By inputSearchBy = By.xpath("//input[@id='inner_search_v4']");
    private final By submitButtonBy = By.xpath("//input[@type='submit' and @value='Search']");
    private final By hoverMoviesMenuBy = By.xpath("//a[@href='/movie' and text()='Movies']");
    private final By hoverTopRatedBy = By.xpath("//a[@href='/movie/top-rated']");
    public MainPage(DriverManager driverManager){
        super(driverManager);
    }
    public LoginPage clickOnLoginButton(){
        driverManager.fluentWait(loginButtonBy, WaitConditions.CLICKABLE);
        driverManager.clickElement(loginButtonBy);
        logger.info("Clicking on 'login' button...");
        return new LoginPage(driverManager);
    }
    public SearchResultsPage  searchMovie(Movie movie){
        logger.info("Searching the movie: "+movie.getName()+"...");
        driverManager.fluentWait(inputSearchBy,WaitConditions.PRESENT);
        driverManager.sendKeysTo(inputSearchBy, movie.getName());
        driverManager.fluentWait(submitButtonBy, WaitConditions.CLICKABLE);
        logger.info("Clicking on 'search' button...");
        driverManager.clickElement(submitButtonBy);
        return new SearchResultsPage(driverManager);
    }
    public TopRatedPage goToTopRated(){
        logger.info("Hover to 'Movies' menu...");
        driverManager.fluentWait(hoverMoviesMenuBy, WaitConditions.PRESENT);
        driverManager.clickElement(hoverMoviesMenuBy);
        driverManager.fluentWait(hoverTopRatedBy, WaitConditions.CLICKABLE);
        logger.info("Clicking on 'Top Rated'...");
        driverManager.clickElement(hoverTopRatedBy);
        return new TopRatedPage(driverManager);
    }
    public MoviePage goDirectly2MoviePage(Movie movie){
        logger.info("Redirecting to movie page of: "+movie.getName());
        driverManager.goToURL("https://www.themoviedb.org/movie/"+movie.getId());
        return new MoviePage(driverManager);
    }
}
