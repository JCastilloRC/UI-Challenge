package drivermanager;

import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager extends DriverManager{
    public ChromeDriverManager(){
        super();
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\jcastillo\\Documents\\Challenges\\UI-Challenge\\chromedriver.exe"
        );
        setDriver(new ChromeDriver());
    }
}
