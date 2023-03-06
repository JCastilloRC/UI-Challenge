package drivermanager;
public class DriverManagerFactory {
    public static DriverManager getManager(DriverType type){
        return switch (type) {
            case CHROME -> new ChromeDriverManager();
            case EDGE -> new EdgeDriverManager();
            case FIREFOX -> new FirefoxDriverManager();
        };
    }
}

