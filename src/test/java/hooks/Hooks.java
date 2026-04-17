package hooks;

import base.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.ConfigReader;
import utilities.ScreenshotUtils;

public class Hooks {
    public static Scenario scenario;

    @Before
    public void setup(Scenario scenario){

        Hooks.scenario = scenario;


        String browser = System.getProperty("browser");//run like mvn test -Dbrowser=chrome
        if(browser==null)
        {
            browser = ConfigReader.get("browser");

        }

    String url = ConfigReader.get("base.url");

    if(browser==null||url==null)
    {
        throw  new RuntimeException("Browser or URL is undefined");
    }


        DriverFactory.createDriver(browser);
        DriverFactory.getDriver().get(url);

        System.out.println("[CUCUMBER] Browser launched on: "+browser);





    }







    @After
    public  void tearDown(Scenario scenario){

        if(scenario.isFailed())
        {
            byte[] screenshot = ScreenshotUtils.getScreenShotBytes(DriverFactory.getDriver());
            scenario.attach(screenshot,"image/png",scenario.getName());

            System.out.println("[CUCUMBER] Screenshot attached in Allure");

        }

        DriverFactory.quitDriver();
        System.out.println("[CUCUMBER] Browser closed");

    }
}
