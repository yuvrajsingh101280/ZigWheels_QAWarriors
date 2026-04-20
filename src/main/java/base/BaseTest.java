package base;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utilities.AllureReportOpener;
import utilities.ConfigReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected  WebDriver driver;
    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("chrome") String browser)
    {

        logger.info("===== Test Setup Started =====");
        logger.info("Browser parameter received: {}", browser);
        String url  = ConfigReader.get("base.url");
        if(browser==null||url==null)
        {

            logger.error("Browser or URL is undefined. browser={}, url={}", browser, url);
            throw new RuntimeException("Browser or url is undefined");
        }

        driver = DriverFactory.createDriver(browser);

        logger.info("WebDriver initialized successfully");

        DriverFactory.getDriver().get(url);

        logger.info("Navigated to URL: {}", url);





    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){

        logger.info("===== Test Teardown Started =====");
        DriverFactory.quitDriver();
        logger.info("Browser closed successfully");
    }


    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        AllureReportOpener.cleanAllureResults();
    }

    @AfterSuite()
    public void afterSuite() {
        AllureReportOpener.openAllureReport();
    }







}