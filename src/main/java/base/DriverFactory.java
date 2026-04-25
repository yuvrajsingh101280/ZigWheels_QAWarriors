package base;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {


//    creating driver

private static WebDriver driver;
    private static final Logger logger =
            LogManager.getLogger(DriverFactory.class);


    public static WebDriver createDriver(String browser)
    {

        logger.info("Creating WebDriver for browser: {}", browser);

        Map<String, Object> prefs = new HashMap<>();
        switch (browser.toLowerCase())
        {
            case "chrome":
                logger.info("Setting Chrome options");
                ChromeOptions options = new ChromeOptions();

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
                logger.info("Setting Edge options");
                EdgeOptions edgeOptions = new EdgeOptions();

                edgeOptions.setExperimentalOption("prefs", prefs);
                edgeOptions.setExperimentalOption(
                        "excludeSwitches",
                        new String[]{"enable-automation"}
                );
                edgeOptions.setExperimentalOption("useAutomationExtension", false);
                edgeOptions.addArguments("--disable-blink-features=AutomationControlled");
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                logger.error("Unsupported browser received: {}", browser);
                throw new RuntimeException("Unsupported browser :--- "+browser);
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        logger.info("WebDriver instance created");
        return driver;

    }

    public static WebDriver getDriver() {
//        return tdriver.get();
        return driver;
    }


    public static void quitDriver() {


        if (driver!= null) {
            logger.info("Quitting WebDriver");
            driver.quit();
            driver=null;
        }
    }

}
