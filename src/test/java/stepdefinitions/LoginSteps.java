package stepdefinitions;

import base.DriverFactory;
import hooks.Hooks;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import utilities.ScreenshotUtils;

public class LoginSteps {

    LoginPage loginPage = new LoginPage();
    String errorMessage;



    @Given("user click on login and selects Google option")
    public  void click_login(){
        loginPage.clickLoginIcon();

    }

    @When("user switches to Google login window")
    public void switch_window(){
        loginPage.switchToGoogleLogin();
    }
    @And("user enters {string} and submits")
    public void enter_email(String email)
    {
        errorMessage = loginPage.loginDataWithFeatureFile(email);
        System.out.println(errorMessage);

    }

    @Then("error message should be displayed")
    public void validate_error(){
        boolean isDisplayed = loginPage.isErrorDisplayed();
        if(isDisplayed)
        {
            SoftAssert softAssert = new SoftAssert();
            softAssert.fail("Failing intentionally for Allure Screenshot");

            byte[] screenshot = ScreenshotUtils.getScreenShotBytes(DriverFactory.getDriver());
            Hooks.scenario.attach(screenshot,"image/png","Failed login");

//fail here
            softAssert.assertAll();


        }
    }






}
