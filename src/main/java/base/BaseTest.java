package base;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utilities.ConfigReader;

public class BaseTest {


    protected  WebDriver driver;
    @Parameters({"browser"})
    @BeforeClass
    public void setup(String browser)
    {


        String url  = ConfigReader.get("base.url");
        if(browser==null||url==null)
        {
            throw new RuntimeException("Browser or url is undefined");
        }

        driver = DriverFactory.createDriver(browser);

        driver.get(url);



        System.out.println("[INFO] Browser launched and navigates to URL");



    }

@AfterClass
    public void tearDown(){


        if(driver!=null)
        {
            driver.quit();
            System.out.println("[INFO] Browser Closed");

        }
    }








}
