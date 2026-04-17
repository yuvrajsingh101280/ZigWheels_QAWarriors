package tests;

import base.BaseTest;
import io.qameta.allure.*;
import listeners.AllureListeners;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.ConfigReader;


@Epic("Authentication")
@Feature("Login Feature")
@Listeners({AllureListeners.class})
public class LoginTest extends BaseTest {



    @Test

    @Story("Login using Google account")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can login using Google authentication")
    public void loginTest(){
        LoginPage login = new LoginPage();
        login.clickLoginIcon();
        login.switchToGoogleLogin();
        String email   = ConfigReader.get("email");
        String data = login.loginData(email);
        System.out.println(data);

        boolean isErrorDisplayed = login.isErrorDisplayed();
        System.out.println(" ------------- " + isErrorDisplayed);
//        Assert.assertTrue(isErrorDisplayed,);

        if (isErrorDisplayed) {
            Assert.fail("Failing test intentionally to trigger onTestFailure screenshot");
        }


    }
}
