package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {



    @Test(priority = 3)
    public void loginTest(){
        LoginPage login = new LoginPage(driver);
        login.clickLoginIcon();
        login.switchToGoogleLogin();
        String data = login.loginData();
        System.out.println(data);


    }
}
