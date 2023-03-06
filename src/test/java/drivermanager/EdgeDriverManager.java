package drivermanager;
import org.openqa.selenium.edge.EdgeDriver;
public class EdgeDriverManager extends DriverManager{
    public EdgeDriverManager(){
        super();
        System.setProperty("webdriver.edge.driver",
                "C:\\Users\\jcastillo\\Documents\\Challenges\\UI-Challenge\\msedgedriver.exe"
        );
        setDriver(new EdgeDriver());
    }
}
