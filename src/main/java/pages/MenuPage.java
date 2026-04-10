package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import utilities.WaitUtils;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuPage {

    WebDriver driver;
    WaitUtils wait;
    Actions action;
    public MenuPage(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        this.action = new Actions(driver);
        PageFactory.initElements(driver,this);//this initializes all the find By elements

    }

//    locators
@FindBy(xpath = "//li//span[contains(text(),'NEW BIKES')]")
public WebElement newBikesLink;

    @FindBy(xpath = "//li//span[contains(text(),'NEW BIKES')]/following-sibling::ul//a[contains(text(),'Upcoming Bikes')]")
    public WebElement upcomingBikesLink;



    @FindBy(xpath = "//a[normalize-space()='Honda']")
    WebElement honda;

    @FindBy(xpath="//a[contains(@title,'Honda')]/strong")
    List<WebElement>bikeNames;



    @FindBy(xpath = "//*[@id='modelList']/li/div/div[3]/div[1]")
    public List<WebElement> bikePrice;



    @FindBy(xpath = "//*[@id='modelList']/li/div/div[3]/div[2]")
    public List<WebElement> expectedLaunchDate;


    public void clickUpcomingBikeOption(){



        wait.waitForVisibility(newBikesLink);
        action.moveToElement(newBikesLink).perform();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();",upcomingBikesLink);
        action.moveByOffset(0, 600).perform();
    }

    public void clickHonda(){


        wait.waitForVisibility(honda);
        JavascriptExecutor js = (JavascriptExecutor)driver;


        js.executeScript("arguments[0].scrollIntoView(true)",honda);

        wait.waitForClickable(honda);

      js.executeScript("arguments[0].click();",honda);


    }


    public void printBikeDetails() {
        System.out.println("Total bikes found: " + bikeNames.size());
        System.out.println("------------------------------------------");


        for (int i = 0; i < bikeNames.size(); i++) {

            String name = bikeNames.get(i).getText();
            String rawPrice = bikePrice.get(i).getText();
            String launchDate = expectedLaunchDate.get(i).getText().split(":")[1].trim(); // Extract date after "Expected Launch Date:"

            // Use regex to find decimal numbers (e.g., 1.30)
            Pattern p = Pattern.compile("\\d+\\.\\d+");
            Matcher m = p.matcher(rawPrice);

            if (m.find()) {
                String cleanPrice = m.group();
                try {
                    double numericPrice = Double.parseDouble(cleanPrice);

                    // Requirement: Only print bikes under 4 Lakhs
                    if (numericPrice < 4.0) {





                        System.out.println("Bike Name: " + name);
                        System.out.println("Price: " + cleanPrice + " Lakh");
                        System.out.println("Expected Launch Date: " + launchDate);
                        System.out.println("-----------------------------");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing price for: " + name);
                }
            } else {
                // If no numeric price exists (e.g., "Price To Be Announced")
                System.out.println("Bike Name: " + name);
                System.out.println("Price Status: " + rawPrice);
                System.out.println("Expected Launch Date: " + launchDate);
                System.out.println("-----------------------------");
            }
        }
    }






}
