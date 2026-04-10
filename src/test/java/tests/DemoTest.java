package tests;

import base.DriverFactory;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MenuPage;
import utilities.ScreenshotUtils;

public class DemoTest extends DriverFactory.BaseTest {



    @Test(priority = 1)
    public void upcomingBikes(){
        MenuPage menu = new MenuPage(driver);
        menu.clickUpcomingBikeOption();
        menu.clickHonda();
        menu.printBikeDetails();
//        ScreenshotUtils.takeScreenshot(driver);

    }
    @Test(priority = 3)
    public void loginTest(){
        LoginPage login = new LoginPage(driver);
        login.clickLoginIcon();
        login.switchToGoogleLogin();
        String data = login.loginData();
        System.out.println(data);


    }

}
