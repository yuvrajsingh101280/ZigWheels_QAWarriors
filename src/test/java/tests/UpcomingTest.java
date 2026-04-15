package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.MenuPage;

@Epic("Vehicle Information")
@Feature("Upcoming Bikes")

public class UpcomingTest extends BaseTest {


    @Test(priority = 1)
    @Story("View upcoming Honda bikes")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify upcoming Honda bike details are displayed")
    public void upcomingBikes(){
        MenuPage menu = new MenuPage();
        menu.clickUpcomingBikeOption();
        menu.clickBike();
        menu.printBikeDetails();
//        ScreenshotUtils.takeScreenshot(driver);

    }
}
