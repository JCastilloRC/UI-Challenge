package hooks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

public class Hooks {
    protected WebDriver driver = new ChromeDriver();
    @BeforeMethod
    public void setUp(){
        driver.manage()
                .window()
                .maximize();
        driver.navigate()
                .to("https://www.themoviedb.org/");
    }
    @AfterMethod
    public void resetSession(){
        driver.manage().deleteAllCookies();
    }
    @AfterTest
    public void tearDown(){
        driver.close();
    }
}
