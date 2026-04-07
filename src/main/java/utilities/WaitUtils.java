package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {





    WebDriverWait wait;
    WebDriver driver;


    public WaitUtils(WebDriver driver){


        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //   wait for element to visible
    public void waitForVisibility(WebElement element)
    {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

//    wait for the element to be clickable
    public void waitForClickable(WebElement element)
    {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }




}
