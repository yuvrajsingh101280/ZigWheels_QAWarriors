package hooks;

import base.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ConfigReader;
import utilities.ScreenshotUtils;

public class Hooks {
    public static Scenario scenario;
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    @Before
    public void setup(Scenario scenario){

        Hooks.scenario = scenario;


        logger.info("========== Scenario Started ==========");
        logger.info("Scenario Name: {}", scenario.getName());

        String browser = System.getProperty("browser");//run like mvn test -Dbrowser=chrome
        if(browser==null)
        {
            browser = ConfigReader.get("browser");
            logger.info("Browser picked from config.properties: {}", browser);

        }else {
            logger.info("Browser picked from system property: {}", browser);
        }

    String url = ConfigReader.get("base.url");

    if(browser==null||url==null)
    {
        throw  new RuntimeException("Browser or URL is undefined");
    }


        DriverFactory.createDriver(browser);
        DriverFactory.getDriver().get(url);

        logger.info("Browser launched: {}", browser);
        logger.info("Navigated to URL: {}", url);







    }







    @After
    public  void tearDown(Scenario scenario){

        logger.info("Scenario Status: {}", scenario.getStatus());

        if(scenario.isFailed())
        {

            logger.error("Scenario FAILED: {}", scenario.getName());
            byte[] screenshot = ScreenshotUtils.getScreenShotBytes(DriverFactory.getDriver());
            scenario.attach(screenshot,"image/png",scenario.getName());

            logger.error("Screenshot captured and attached to report");

        }

        DriverFactory.quitDriver();

        logger.info("Browser closed");

        logger.info("========== Scenario Finished ==========");


    }
}
