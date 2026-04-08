package tests;

import base.DriverFactory;
import org.testng.annotations.Test;
import pages.MenuPage;

public class DemoTest extends DriverFactory.BaseTest {



    @Test(priority = 1)
    public void upcomingBikes(){
        MenuPage menu = new MenuPage(driver);
        menu.clickUpcomingBikeOption();
        menu.clickHonda();
        menu.printBikeDetails();

    }
}
