package testrunner;

import hooks.Hooks;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import miscclasses.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import webpages.LoginPage;
import webpages.MainPage;

import java.io.IOException;

import static helper.Helper.parseUserYAML;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestRunnerFirefox extends Hooks {
    private final String USER_PATH = "testdata/usercredentials.yaml";
    private static final Logger logger = LogManager.getLogger(TestRunnerFirefox.class);
    @Test(priority = 3)
    @Description("Verify successful login with valid credentials")
    @Feature("Login")
    @Severity(SeverityLevel.CRITICAL)
    public void UICT01() throws IOException {
        logger.info("- - - - - - START TEST CASE UICT01 - - - - - -");
        User someUser = parseUserYAML(USER_PATH);
        MainPage mainPage = new MainPage(driverManager);
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
        MainPage mainPage = new MainPage(driverManager);
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
}
