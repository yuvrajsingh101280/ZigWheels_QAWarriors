package pages;

import org.openqa.selenium.WebDriver;
import utilities.WaitUtils;
import org.openqa.selenium.support.PageFactory;
public class MenuPage {

    WebDriver driver;
    WaitUtils utils;
    public MenuPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver,this);//this initializes all the find By elements

    }






}
