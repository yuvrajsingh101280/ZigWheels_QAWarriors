package pages;

import base.DriverFactory;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.ConfigReader;
import utilities.ExcelUtils;
import utilities.WaitUtils;

import java.util.ArrayList;
import java.util.List;

public class UsedCars {
    WebDriver driver;
    WaitUtils wait;
    Actions action;
    private static final Logger logger = LogManager.getLogger(UsedCars.class);
    private static final String FILE_PATH= System.getProperty("user.dir")+"/TestData/CarDetails.xlsx";
//    private static final String FILE_NAME= ConfigReader.get("carSheetName");




    String city = ConfigReader.get("city");
//    for excel write
        ExcelUtils excel;

    public UsedCars() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WaitUtils(driver);
        this.action = new Actions(driver);
        PageFactory.initElements(driver,this);

        logger.debug("UsedCars page initialized");

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
    @Step("Open Used Cars section from More menu")
    public void clickUsedCarsOption() {

        logger.info("Navigating to Used Cars section");

        wait.waitForVisibility(moreMenu);
        action.moveToElement(moreMenu).perform();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", usedCarsLink);

        logger.info("Used Cars page opened successfully");

    }

//    selecting city from dropdown
    @Step("Select city as {cityName}")
    public void selectCity(String cityName) {

        logger.info("Selecting city for used cars search: {}", cityName);

        cityInputBox.sendKeys(cityName);
        wait.waitForElementsVisibility(citySuggestionList);
        List<WebElement> suggestions = driver.findElements(citySuggestionList);
        for(WebElement city : suggestions) {
            if(city.getText().equalsIgnoreCase(cityName)) {
                city.click();
                break;
            }
        }

        logger.info("City selected successfully: {}", cityName);

    }





//for test ng
    @Step("Capture popular used car models and save to Excel")
    public void printPopularUsedCarsModels(String cityName) {

        logger.info("Fetching popular used car models for city: {}", cityName);

        excel = new ExcelUtils(FILE_PATH);
        excel.switchOrCreateSheetForCar(cityName);


        logger.info("Total popular used car models found: {}", popularModels.size());

        int excelRow=1;

//        writing data to Excel
        for (WebElement popularModel : popularModels) {
            String name = popularModel.getText();

            excel.setCellData(excelRow,0,name);
            excelRow++;
//            System.out.println(name);
        }
excel.close();

        logger.info("Popular used car models written to Excel");


//        reading data from Excel

      ExcelUtils readExcel  = new ExcelUtils(FILE_PATH,cityName);
        int rowCount = readExcel.getRowCount();

        logger.info("Reading {} popular car models from Excel for city: {}",
                rowCount - 1, cityName);



//        System.out.println("All popular Car Models in "+cityName+" :-");

        logger.info("All popular car Model in {}",cityName);
        for(int i = 1; i<rowCount;i++)
        {
            System.out.println(readExcel.getCellData(i,0));
        }


    }



//    for cucumber
    @Step("Get popular used car models list")
    public List<String> getPopularModels(){
        logger.info("Collecting popular used car models for validation");
        wait.waitForVisibility(popularModels.get(0));

        List<String>carList= new ArrayList<>();
        for(WebElement model:popularModels)
        {
            String name = model.getText();
            carList.add(name);
        }
        logger.info("Total popular models collected: {}", carList.size());
        return  carList;

    }
}