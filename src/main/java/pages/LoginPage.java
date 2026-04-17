package pages;

import java.util.Set;

import base.DriverFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.ConfigReader;
import utilities.WaitUtils;

public class LoginPage {

    WebDriver driver;
    WaitUtils wait;
    public LoginPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WaitUtils(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div[@id='forum_non_login_lg']/div[1]")
    WebElement loginIcon;


    @FindBy(xpath = "//div[@data-track-label='Popup_Login/Register_with_Google']")
    WebElement GoogleBtn;
    @FindBy(xpath = "//input[@type='email'][@id='identifierId']")
    WebElement emailInput;

    @FindBy(xpath = "//span[text()='Next']")
    WebElement nextBtn;

    @FindBy(xpath = "//div[@id='i8']/div")
    WebElement error;

    @Step("Click login icon and select Google login option")
    public void clickLoginIcon() {
    wait.waitForVisibility(loginIcon);
    loginIcon.click();

    wait.waitForVisibility(GoogleBtn);
    GoogleBtn.click();
    }


    @Step("Switch control to Google login window")
    public void switchToGoogleLogin() {
        String mainWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();

        for(String win : windows) {
            if(!win.equals(mainWindow)) {
                driver.switchTo().window(win);
            }
        }
    }


    @Step("Enter email and submit login form")
    public String loginData(String email) {
        wait.waitForVisibility(emailInput);
        emailInput.sendKeys(email);
        wait.waitForClickable(nextBtn);
         nextBtn.click();
       wait.waitForVisibility(error);
       String errorMsg = error.getText();

      return errorMsg;
    }
//for cucumber
    @Step("Enter email{0} and submit the login form")
    public String loginDataWithFeatureFile(String email)
    {
        wait.waitForVisibility(emailInput);
        emailInput.sendKeys(email);
        wait.waitForClickable(nextBtn);
        nextBtn.click();
        wait.waitForVisibility(error);
        String errorMsg = error.getText();
        return errorMsg;
    }



    @Step("Check if login error message is displayed")
    public boolean isErrorDisplayed() {
        try {
            wait.waitForVisibility(error);
            return error.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

}
