package pages;

import java.util.Set;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;

public class LoginPage {

    WebDriver driver;
    WaitUtils wait;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
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

    public void clickLoginIcon() {
    wait.waitForVisibility(loginIcon);
    loginIcon.click();

    wait.waitForVisibility(GoogleBtn);
    GoogleBtn.click();
    }

    public void switchToGoogleLogin() {
        String mainWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();

        for(String win : windows) {
            if(!win.equals(mainWindow)) {
                driver.switchTo().window(win);
            }
        }
    }
    public String loginData() {
        wait.waitForVisibility(emailInput);
        emailInput.sendKeys("abc@gmail.com");
        wait.waitForClickable(nextBtn);
         nextBtn.click();
       wait.waitForVisibility(error);
       String errorMsg = error.getText();

      return errorMsg;
    }

}
