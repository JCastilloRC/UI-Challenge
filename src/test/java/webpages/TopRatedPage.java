package webpages;

import drivermanager.DriverManager;
import drivermanager.WaitConditions;
import miscclasses.Genre;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.apache.logging.log4j.LogManager.getLogger;

public class TopRatedPage extends BasePage{
    private static final Logger logger = getLogger(MainPage.class.getName());
    private final By filtersBy = By.xpath("//div[@data-callback='filterCallback()']");
    private final By sortBy = By.xpath("//span[@aria-owns='sort_by_listbox']");
    private final By dateAscendingBy = By.xpath("//li[text()='Release Date Ascending']");
    private final By datesBy = By.xpath("//div[@class='media_items results']//p");
    private final By searchBy = By.xpath("//a[text()='Search']");
    private final By movieTitlesBy = By.xpath("//h2/a[@title]");
    public TopRatedPage(DriverManager driverManager){
        super(driverManager);
    }

    public TopRatedPage openFilters(){
        logger.info("Opening filters...");
        driverManager.fluentWait(filtersBy, WaitConditions.CLICKABLE);
        driverManager.clickElement(filtersBy);
        return this;
    }
    public TopRatedPage openSortBy(){
        logger.info("Opening sort by...");
        driverManager.fluentWait(sortBy, WaitConditions.CLICKABLE);
        driverManager.clickElement(sortBy);
        return this;
    }
    public TopRatedPage filterByGenre(Genre genre){
        logger.info("Filtering by genre: "+genre.getName()+"...");
        By genreFilterBy = By.xpath("//li[@data-value='"+genre.getId()+"']/a");
        driverManager.fluentWait(genreFilterBy, WaitConditions.CLICKABLE);
        driverManager.clickElement(genreFilterBy);
        return this;
    }
    public TopRatedPage sortByDateAsc(){
        logger.info("Filtering by date in ascending order...");
        driverManager.fluentWait(dateAscendingBy, WaitConditions.CLICKABLE);
        driverManager.clickElement(dateAscendingBy);
        return this;
    }
    public TopRatedPage applyFilters(){
        driverManager.fluentWait(searchBy, WaitConditions.CLICKABLE);
        logger.info("Clicking search...");
        driverManager.clickElement(searchBy);
        logger.info("Waiting for page to refresh...");
        driverManager.fluentWait(movieTitlesBy, WaitConditions.REFRESH);
        return this;
    }
    public Date[] getFirstDates(int numberDates) throws ParseException {
        logger.info("Returning first "+numberDates+" dates...");
        Date[] firstDates = new Date[numberDates];
        SimpleDateFormat parser=new SimpleDateFormat("MMM d, yyyy");
        driverManager.fluentWait(datesBy, WaitConditions.PRESENT);
        List <WebElement> dates = driverManager.mapToElements(datesBy);
        for(int i=0; i<numberDates; i++){
            firstDates[i] = parser.parse(dates.get(i).getText());
        }
        return firstDates;
    }
    public MoviePage selectAnyMovie(int idx){
        logger.info("Selecting movie...");
        driverManager.fluentWait(movieTitlesBy, WaitConditions.PRESENT);
        driverManager.clickElement(movieTitlesBy, idx);
        return new MoviePage(driverManager);
    }
}
