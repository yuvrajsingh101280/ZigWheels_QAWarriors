package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.MenuPage;

public class UpcomingTest extends BaseTest {


    @Test(priority = 1)
    public void upcomingBikes(){
        MenuPage menu = new MenuPage(driver);
        menu.clickUpcomingBikeOption();
        menu.clickHonda();
        menu.printBikeDetails();
//        ScreenshotUtils.takeScreenshot(driver);

    }
}
