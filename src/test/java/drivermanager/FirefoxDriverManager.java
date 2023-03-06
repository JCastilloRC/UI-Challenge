package drivermanager;

import org.openqa.selenium.firefox.FirefoxDriver;
public class FirefoxDriverManager extends DriverManager{
    public FirefoxDriverManager(){
        super();
        System.setProperty("webdriver.gecko.driver",
                "C:\\Users\\jcastillo\\Documents\\Challenges\\UI-Challenge\\geckodriver.exe"
        );
        setDriver(new FirefoxDriver());
    }
}
