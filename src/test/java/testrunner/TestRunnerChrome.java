package testrunner;

import hooks.Hooks;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import miscclasses.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import webpages.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import static helper.Helper.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestRunnerChrome extends Hooks{ //reports: allure serve C:\Users\jcastillo\Documents\Challenges\UI-Challenge\allure-results

    private static final String MOVIES_W_ACTORS = "testdata/movieswithactors.yaml";
    private static final Logger logger = LogManager.getLogger(TestRunnerChrome.class);
    @DataProvider(name = "moviesWithActorsProvider")
    public static Object[][] moviesWithActors() throws IOException {
        List<Movie> moviesWActors = parseMovieListYAML(MOVIES_W_ACTORS);
        Object[][] data = new Object[moviesWActors.size()][2];
        for (int i=0; i<moviesWActors.size(); i++){
            data[i][0] = i;
            data[i][1] = moviesWActors.get(i);
        }
        return data;
    }
    @Test(dataProvider = "moviesWithActorsProvider",priority = 1)
    @Description("Verify presence of actors in movies")
    @Feature("Search")
    @Severity(SeverityLevel.NORMAL)
    public void UICT05(int idx, Movie aMovie){
        logger.info("- - - - - - START TEST CASE UICT05-"+(idx+1)+" - - - - - -");
        MainPage mainPage = new MainPage(driverManager);
        boolean actedInMovie = mainPage.goDirectly2MoviePage(aMovie)
                .goToActorPage(idx)
                .actedInMovie(aMovie);
        logger.info("- - - - - - - - - TEST CASE END - - - - - - - - \n");
        assertThat("Movie doesn't appear in actor's acting timeline",
                actedInMovie);
    }
    @Test(priority = 2)
    @Description("Verify sorting by ascending order")
    @Feature("Filters")
    @Severity(SeverityLevel.NORMAL)
    public void UICT06() throws ParseException {
        logger.info("- - - - - - START TEST CASE UICT06 - - - - - -");
        MainPage mainPage = new MainPage(driverManager);
        Date[] first4Dates = mainPage
                .goToTopRated()
                .openSortBy()
                .sortByDateAsc()
                .applyFilters()
                .getFirstDates(4);
        assertThat("Dates aren't by ascending order",
                datesOrderedAscending(first4Dates)
        );
        logger.info("- - - - - - - - - TEST CASE END - - - - - - - - \n");
    }
}
