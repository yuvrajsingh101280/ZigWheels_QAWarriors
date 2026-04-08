package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.MenuPage;

public class DemoTest extends BaseTest {


    @Test
    public void click(){
        MenuPage menu = new MenuPage(driver);
        menu.clickUpcomingBikeOption();
        menu.clickHonda();
        menu.printBikeDetails();

    }
}
