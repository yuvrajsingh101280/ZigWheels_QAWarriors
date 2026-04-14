package base;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {


//    creating driver
    private static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();


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
        tdriver.set(driver);
        return driver;

    }

    public static WebDriver getDriver() {
        return tdriver.get();
    }


    public static void quitDriver() {
        if (tdriver.get() != null) {
            tdriver.get().quit();
            tdriver.remove();
        }
    }

}
