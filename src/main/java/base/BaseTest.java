package base;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.ConfigReader;

public class BaseTest {


    @Parameters({"browser"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browser)
    {


        String url  = ConfigReader.get("base.url");
        if(browser==null||url==null)
        {
            throw new RuntimeException("Browser or url is undefined");
        }

        DriverFactory.createDriver(browser);

        DriverFactory.getDriver().get(url);



        System.out.println("[INFO] Browser launched and navigates to URL");



    }

    @AfterMethod
    public void tearDown(ITestResult iTestResult){
        if(DriverFactory.getDriver() != null) {
            System.out.println("[INFO] Browser Closed");
            DriverFactory.quitDriver();
        }
    }








}