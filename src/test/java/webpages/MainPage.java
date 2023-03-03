package webpages;

import miscclasses.Movie;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.apache.logging.log4j.LogManager.getLogger;

public class MainPage extends BasePage {
    private static final Logger logger = getLogger(MainPage.class.getName());
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
    private final By loginButtonBy = By.xpath("//li/a[@href='/login']"); //didn't like this one
    private final By inputSearchBy = By.xpath("//input[@id='inner_search_v4']");
    private final By submitButtonBy = By.xpath("//input[@type='submit' and @value='Search']");
    private final By hoverMoviesMenuBy = By.xpath("//a[@href='/movie' and text()='Movies']");
    private final By hoverTopRatedBy = By.xpath("//a[@href='/movie/top-rated']");
    public MainPage(WebDriver driver){
        super(driver);
    }
    public LoginPage clickOnLoginButton(){
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonBy));
        mapToElement(loginButtonBy).click();
        logger.info("Clicking on 'login' button...");
        return new LoginPage(driver);
    }
    public SearchResultsPage  searchMovie(Movie movie){
        logger.info("Searching the movie: "+movie.getName()+"...");
        wait.until(ExpectedConditions.presenceOfElementLocated(inputSearchBy));
        mapToElement(inputSearchBy).sendKeys(movie.getName());
        wait.until(ExpectedConditions.elementToBeClickable(submitButtonBy));
        logger.info("Clicking on 'search' button...");
        mapToElement(submitButtonBy).click();
        return new SearchResultsPage(driver);
    }
    public TopRatedPage goToTopRated(){
        logger.info("Hover to 'Movies' menu...");
        wait.until(ExpectedConditions.presenceOfElementLocated(hoverMoviesMenuBy));
        mapToElement(hoverMoviesMenuBy).click();
        wait.until(ExpectedConditions.elementToBeClickable(hoverTopRatedBy));
        logger.info("Clicking on 'Top Rated'...");
        mapToElement(hoverTopRatedBy).click();
        return new TopRatedPage(driver);
    }
    public MoviePage goDirectly2MoviePage(Movie movie){
        logger.info("Redirecting to movie page of: "+movie.getName());
        driver.navigate()
                .to("https://www.themoviedb.org/movie/"+movie.getId());
        return new MoviePage(driver);
    }
}
