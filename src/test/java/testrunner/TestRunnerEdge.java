package testrunner;

import hooks.Hooks;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import miscclasses.Genre;
import miscclasses.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import webpages.MainPage;

import java.io.IOException;

import static helper.Helper.parseGenreYAML;
import static helper.Helper.parseMovieYAML;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.stringContainsInOrder;

public class TestRunnerEdge extends Hooks {

    private final String FC_MOVIE_PATH = "testdata/figthclubmovie.yaml";
    private static final String ACTION_GENRE_PATH = "testdata/actiongenre.yaml";
    private static final Logger logger = LogManager.getLogger(TestRunnerEdge.class);

    @Test(priority = 2)
    @Description("Verify search of 'Fight Club' movie")
    @Feature("Search")
    @Severity(SeverityLevel.NORMAL)
    public void UICT03() throws IOException {
        logger.info("- - - - - - START TEST CASE UICT03 - - - - - -");
        Movie someMovie = parseMovieYAML(FC_MOVIE_PATH);
        MainPage mainPage = new MainPage(driverManager);
        String firstResultName = mainPage.searchMovie(someMovie)
                .getFirstMovieTitle();
        logger.info("- - - - - - - - - TEST CASE END - - - - - - - - \n");
        assertThat("First movie name doesn't match",
                firstResultName,
                equalTo(someMovie.getName())
        );
    }
    @DataProvider(name = "fourIndexProvider")//javadoc
    public static Object[][] fourIndex(){
        Object[][] data = new Object[4][1];
        for (int i=0; i<4; i++){
            data[i][0] = i*3;
        }
        return data;
    }
    @Test(dataProvider = "fourIndexProvider", priority = 2)
    @Description("Verify filtering movies by 'Action' genre")
    @Feature("Filters")
    @Severity(SeverityLevel.NORMAL)
    public void UICT04(int idx) throws IOException{
        logger.info("- - - - - - START TEST CASE UICT04-"+(idx+1)+" - - - - - -");
        Genre actionGenre = parseGenreYAML(ACTION_GENRE_PATH);
        MainPage mainPage = new MainPage(driverManager);
        String genresObtained = mainPage.goToTopRated()
                .openFilters()
                .filterByGenre(actionGenre)
                .applyFilters()
                .selectAnyMovie(idx)
                .wichPage()
                .getGenres();
        logger.info("- - - - - - - - - TEST CASE END - - - - - - - - \n");
        assertThat("Genre filtered does not appear",
                genresObtained,
                stringContainsInOrder(actionGenre.getName())
        );
    }
}
