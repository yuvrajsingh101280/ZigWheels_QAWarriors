package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;

import java.util.List;

public class UsedCars {
    WebDriver driver;
    WaitUtils wait;
    Actions action;

    public UsedCars(WebDriver driver) {
        this.driver =driver;
        this.wait = new WaitUtils(driver);
        this.action = new Actions(driver);
        PageFactory.initElements(driver,this);
    }

//    locators
    @FindBy(xpath = "//li//span[contains(text(),'MORE')]")
    public WebElement moreMenu;

    @FindBy(xpath = "//a[normalize-space()='Used Cars']")
    public WebElement usedCarsLink;

    @FindBy(xpath = "//input[@id='gs_input5']")
    public WebElement cityInputBox;

    @FindBy(xpath = "//ul[contains(@class,'usedCarMakeModelList')]//li//label")
    public List<WebElement> popularModels;

    By citySuggestionList = By.xpath("//li[@class='ui-menu-item']//a");

//    Actions

//    clicking on Used Cars link
    public void clickUsedCarsOption() {
        wait.waitForVisibility(moreMenu);
        action.moveToElement(moreMenu).perform();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", usedCarsLink);
    }

//    selecting city from dropdown
    public void selectCity(String cityName) {
        cityInputBox.sendKeys(cityName);
        wait.waitForElementsVisibility(citySuggestionList);
        List<WebElement> suggestions = driver.findElements(citySuggestionList);
        for(WebElement city : suggestions) {
            if(city.getText().equalsIgnoreCase(cityName)) {
                city.click();
                break;
            }
        }
    }


    public void printPopularUsedCarsModels() {
        System.out.println("Total popular used cars model found: " + popularModels.size());
        System.out.println("---------------------------------------------------------------");

        for (WebElement popularModel : popularModels) {
            String name = popularModel.getText();
            System.out.println(name);
        }
    }

}