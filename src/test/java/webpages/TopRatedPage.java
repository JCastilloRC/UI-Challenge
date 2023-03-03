package webpages;

import miscclasses.Genre;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import static org.apache.logging.log4j.LogManager.getLogger;

public class TopRatedPage extends BasePage{
    private static final Logger logger = getLogger(MainPage.class.getName());
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
    private final By filtersBy = By.xpath("//div[@class='name' and @data-callback]");
    private final By sortBy = By.xpath("//span[@aria-owns='sort_by_listbox']");
    private final By dateAscendingBy = By.xpath("//li[text()='Release Date Ascending']");
    private final By datesBy = By.xpath("//div[@class='media_items results']//p");
    private final By searchBy = By.xpath("//a[text()='Search']");
    private final By getSearchBGBy = By.xpath("//div[contains(@class,'light_blue ')]");
    private final By movieTitlesBy = By.xpath("//h2/a[@title]");
    public TopRatedPage(WebDriver driver){
        super(driver);
    }

    public TopRatedPage openFilters(){
        logger.info("Opening filters...");
        wait.until(ExpectedConditions.elementToBeClickable(filtersBy));
        mapToElement(filtersBy).click();
        return this;
    }
    public TopRatedPage openSortBy(){
        logger.info("Opening sort by...");
        wait.until(ExpectedConditions.elementToBeClickable(sortBy));
        mapToElement(sortBy).click();
        return this;
    }
    public TopRatedPage filterByGenre(Genre genre){
        logger.info("Filtering by genre: "+genre.getName()+"...");
        By genreFilterBy = By.xpath("//li[@data-value='"+genre.getId()+"']/a");
        JavascriptExecutor js = (JavascriptExecutor)driver;
        wait.until(ExpectedConditions.elementToBeClickable(genreFilterBy));
        js.executeScript("arguments[0].click();", mapToElement(genreFilterBy));
        return this;
    }
    public TopRatedPage sortByDateAsc(){
        logger.info("Filtering by date in ascending order...");
        JavascriptExecutor js = (JavascriptExecutor)driver;
        wait.until(ExpectedConditions.elementToBeClickable(dateAscendingBy));
        js.executeScript("arguments[0].click();", mapToElement(dateAscendingBy));
        return this;
    }
    public TopRatedPage applyFilters(){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        wait.until(ExpectedConditions.elementToBeClickable(searchBy));
        logger.info("Clicking search...");
        js.executeScript("arguments[0].click();", mapToElement(searchBy));
        logger.info("Waiting for page to refresh...");
        wait.until(ExpectedConditions.attributeToBe(getSearchBGBy,
                "class",
                "apply small background_color light_blue disabled"));
        return this;
    }
    public Date[] getFirstDates(int numberDates) throws ParseException {
        logger.info("Returning first "+numberDates+" dates...");
        Date[] firstDates = new Date[numberDates];
        SimpleDateFormat parser=new SimpleDateFormat("MMM d, yyyy");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(datesBy));
        List <WebElement> dates = mapToElements(datesBy);
        for(int i=0; i<numberDates; i++){
            firstDates[i] = parser.parse(dates.get(i).getText());
        }
        return firstDates;
    }
    public MoviePage selectAnyMovie(int idx){
        logger.info("Selecting movie...");
        JavascriptExecutor js = (JavascriptExecutor)driver;
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(movieTitlesBy));
        WebElement randMovie = mapToElements(movieTitlesBy).get(idx);
        js.executeScript("arguments[0].scrollIntoView(true);",randMovie);
        randMovie.click();
        return new MoviePage(driver);
    }
}
