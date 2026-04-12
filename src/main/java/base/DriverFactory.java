package base;
//import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utilities.ConfigReader;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {


//    creating driver


    public static WebDriver createDriver(String browser)
    {

        WebDriver driver;

        switch (browser.toLowerCase())
        {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("profile.default_content_setting_values.notifications", 2);// 3. Set the preference to disable notifications (1 = Allow, 2 = Block)

                options.setExperimentalOption("prefs", prefs);// 4. Add the preferences to options

                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});//to hide the "Chrome is being controlled by automated test software" infobar

                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                options.setExperimentalOption("useAutomationExtension", false);
                options.addArguments("--disable-blink-features=AutomationControlled");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                throw new RuntimeException("Unsupported browser :--- "+browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        return driver;

    }


    public static class BaseTest {


        protected  WebDriver driver;
        @Parameters({"browser"})
        @BeforeMethod
        public void setup(String browser)
        {


            String url  = ConfigReader.get("base.url");
            if(browser==null||url==null)
            {
                throw new RuntimeException("Browser or url is undefined");
            }

            driver = createDriver(browser);

            driver.get(url);



            System.out.println("[INFO] Browser launched and navigates to URL");



        }

    @AfterMethod
        public void tearDown(){


            if(driver!=null)
            {
                driver.quit();
                System.out.println("[INFO] Browser Closed");

            }
        }








    }
}
