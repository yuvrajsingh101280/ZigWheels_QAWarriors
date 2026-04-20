package stepdefinitions;

import base.DriverFactory;
import hooks.Hooks;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import utilities.ScreenshotUtils;

public class LoginSteps {

    LoginPage loginPage = new LoginPage();
    String errorMessage;
    private static final Logger logger = LogManager.getLogger(LoginSteps.class);


    @Given("user click on login and selects Google option")
    public  void click_login(){
        logger.info("User clicks login icon and selects Google option");
        loginPage.clickLoginIcon();

    }

    @When("user switches to Google login window")
    public void switch_window(){
        logger.info("User switches to Google login window");
        loginPage.switchToGoogleLogin();
    }
    @And("user enters {string} and submits")
    public void enter_email(String email)
    {
        logger.info("User enters email: {}", email);
        errorMessage = loginPage.loginDataWithFeatureFile(email);
        logger.info("Error message received: {}", errorMessage);

    }

    @Then("error message should be displayed")
    public void validate_error(){
        logger.info("Validating error message on login page");
        boolean isDisplayed = loginPage.isErrorDisplayed();
        if(isDisplayed)
        {
            SoftAssert softAssert = new SoftAssert();
            softAssert.fail("Failing intentionally for Allure Screenshot");

            byte[] screenshot = ScreenshotUtils.getScreenShotBytes(DriverFactory.getDriver());
            Hooks.scenario.attach(screenshot,"image/png","Failed login");

//fail here
            softAssert.assertAll();


            logger.info("Error message is displayed as expected");

        }
    }






}
