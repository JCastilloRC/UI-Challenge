package testrunner;

import hooks.Hooks;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import miscclasses.Genre;
import miscclasses.Movie;
import miscclasses.User;
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

public class TestRunner extends Hooks { //reports: allure serve C:\Users\jcastillo\Documents\Challenges\UI-Challenge\allure-results
    private final String USER_PATH = "testdata/usercredentials.yaml";
    private final String FC_MOVIE_PATH = "testdata/figthclubmovie.yaml";
    private static final String ACTION_GENRE_PATH = "testdata/actiongenre.yaml";
    private static final String MOVIES_W_ACTORS = "testdata/movieswithactors.yaml";

    private static final Logger logger = LogManager.getLogger(TestRunner.class);

    @Test(priority = 3)
    @Description("Verify successful login with valid credentials")
    @Feature("Login")
    @Severity(SeverityLevel.CRITICAL)
    public void UICT01() throws IOException {
        logger.info("- - - - - - START TEST CASE UICT01 - - - - - -");
        User someUser = parseUserYAML(USER_PATH);
        MainPage mainPage = new MainPage(driver);
        String usernameObtained = mainPage.clickOnLoginButton()
                .successfullyLogin(someUser)
                .getUsernameHeader();
        logger.info("- - - - - - - - - TEST CASE END - - - - - - - - \n");
        assertThat("Username don't match test data",
                usernameObtained,
                equalTo(someUser.getUsername())
        );
    }
    @Test(priority = 2)
    @Description("Verify unsuccessful login errors with invalid credentials")
    @Feature("Login")
    @Severity(SeverityLevel.NORMAL)
    public void UICT02() throws IOException {
        logger.info("- - - - - - START TEST CASE UICT02 - - - - - -");
        User someUser = parseUserYAML(USER_PATH);
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickOnLoginButton()
                .failedLogin(someUser);
        logger.info("- - - - - - - - - TEST CASE END - - - - - - - - \n");
        assertThat("Red error message is not displayed",
                loginPage.hasRedError()
        );
        assertThat("Only two additional error messages are not displayed",
                loginPage.hasTwoErrors()
        );
    }
    @Test(priority = 2)
    @Description("Verify search of 'Fight Club' movie")
    @Feature("Search")
    @Severity(SeverityLevel.NORMAL)
    public void UICT03() throws IOException {
        logger.info("- - - - - - START TEST CASE UICT03 - - - - - -");
        Movie someMovie = parseMovieYAML(FC_MOVIE_PATH);
        MainPage mainPage = new MainPage(driver);
        String firstResultName = mainPage.searchMovie(someMovie)
                .getFirstMovieTitle();
        logger.info("- - - - - - - - - TEST CASE END - - - - - - - - \n");
        assertThat("First movie name doesn't match",
                firstResultName,
                equalTo(someMovie.getName())
        );
    }

    @DataProvider(name = "fourIndexProvider")
    public static Object[][] fourIndex(){
        Object[][] data = new Object[4][1];
        for (int i=0; i<4; i++){
            data[i][0] = i*2;
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
        MainPage mainPage = new MainPage(driver);
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
        MainPage mainPage = new MainPage(driver);
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
        MainPage mainPage = new MainPage(driver);
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
