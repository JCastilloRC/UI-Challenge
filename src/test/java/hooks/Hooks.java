package hooks;

import drivermanager.DriverManager;
import drivermanager.DriverManagerFactory;
import drivermanager.DriverType;
import org.slf4j.MDC;
import org.testng.annotations.*;

public class Hooks {
    protected DriverManager driverManager;
    @Parameters("DriverType")
    @BeforeTest()
    public void setUp(DriverType type){
        driverManager = DriverManagerFactory.getManager(type);
    }
    @BeforeMethod()
    public void testCaseInit(){
        MDC.put("threadName", Thread.currentThread().getName());
        driverManager.initTest();
    }
    @AfterMethod
    public void resetSession(){
        driverManager.deleteCookies();
    }
    @AfterTest
    public void tearDown(){
        driverManager.quitDriver();
    }
}
