package base;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utilities.ConfigReader;

public class BaseTest {


    protected  WebDriver driver;
    @Parameters({"browser"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browser)
    {


        String url  = ConfigReader.get("base.url");
        if(browser==null||url==null)
        {
            throw new RuntimeException("Browser or url is undefined");
        }

        driver = DriverFactory.createDriver(browser);

        DriverFactory.getDriver().get(url);



        System.out.println("[INFO] Browser launched and navigates to URL");



    }

    @AfterMethod
    public void tearDown(){


        DriverFactory.quitDriver();
        System.out.println("[INFO] Browser Closed");
    }








}