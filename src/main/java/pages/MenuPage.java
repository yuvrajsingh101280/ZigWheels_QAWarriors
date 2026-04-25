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
import utilities.ConfigReader;
import utilities.ExcelUtils;
import utilities.WaitUtils;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuPage {

    WebDriver driver;
    WaitUtils wait;
    Actions action;
    private static final Logger logger= LogManager.getLogger(MenuPage.class);
    private static final String FILE_PATH= System.getProperty("user.dir")+"/TestData/BikeDetails.xlsx";
//    private static final String FILE_NAME= ConfigReader.get("BikesheetName");
    private String bikeName = ConfigReader.get("bikeCompany");
//    for Excel write

    ExcelUtils excel;


    public MenuPage()
    {
        this.driver = DriverFactory.getDriver();
        this.wait = new WaitUtils(driver);
        this.action = new Actions(driver);
        PageFactory.initElements(driver,this);//this initializes all the find By elements
        logger.debug("MenuPage initialized");

    }


//    setter method for cucumber
    public void setBikeCompanyForScenario(String company)
    {


        logger.info("Bike company set for scenario: {}", company);

        this.bikeName = company;
    }






//    locators
    @FindBy(xpath = "//li//span[contains(text(),'NEW BIKES')]")
    public WebElement newBikesLink;

    @FindBy(xpath = "//li//span[contains(text(),'NEW BIKES')]/following-sibling::ul//a[contains(text(),'Upcoming Bikes')]")
    public WebElement upcomingBikesLink;



//    @FindBy(xpath = "//a[normalize-space()='Honda']")
//    WebElement honda;
//
//    @FindBy(xpath="//a[contains(@title,'Honda')]/strong")
//    List<WebElement>bikeNames;

    //get bike web element
    private WebElement bikeCompanyElement(){


        return driver.findElement(By.xpath("//a[normalize-space()='"+bikeName+"']"));
    }

    //    get the bike name list
    private List<WebElement>bikeNameList(){
        return  driver.findElements(By.xpath("//a[contains(@title,'"+bikeName+"')]/strong"));


    }



    @FindBy(xpath = "//*[@id='modelList']/li/div/div[3]/div[1]")
    public List<WebElement> bikePrice;



    @FindBy(xpath = "//*[@id='modelList']/li/div/div[3]/div[2]")
    public List<WebElement> expectedLaunchDate;


    @Step("Navigate to Upcoming Bikes section")
    public void clickUpcomingBikeOption(){


        logger.info("Navigating to Upcoming Bikes section");


        wait.waitForVisibility(newBikesLink);
        action.moveToElement(newBikesLink).perform();
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        js.executeScript("arguments[0].click();",upcomingBikesLink);
//        action.moveByOffset(0, 600).perform();
        wait.waitForVisibility(upcomingBikesLink);
        upcomingBikesLink.click();

        logger.info("User is on Upcoming Bikes page");

    }

    @Step("Filter upcoming bikes by Honda brand")
    public void clickBike(){

        logger.info("Filtering upcoming bikes for brand: {}", bikeName);


        WebElement bikeCompany = bikeCompanyElement();
        wait.waitForVisibility(bikeCompany);
        JavascriptExecutor js = (JavascriptExecutor)driver;


        js.executeScript("arguments[0].scrollIntoView(true)",bikeCompany);

        wait.waitForClickable(bikeCompany);

      js.executeScript("arguments[0].click();",bikeCompany);

        logger.info("Bike brand filter applied successfully");


    }


    @Step("Extract upcoming bike details and store them in Excel")
    public void printBikeDetails(String companyName) {

        logger.info("Extracting bike details for company: {}", companyName);

        excel = new ExcelUtils(FILE_PATH);
        excel.switchOrCreateSheetForUpcomingBikes(companyName);


        List<WebElement>bikeNames = bikeNameList();



        logger.info("Total bikes found: {}", bikeNames.size());

        //            stating from second row
        int excelRow = 1;

//        writing data into the Excel

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
                    double maxBikePrice = Double.parseDouble(ConfigReader.get("maxBikePrice"));

                    // Requirement: Only print bikes under 4 Lakhs
                    if (numericPrice < maxBikePrice) {
                            excel.setCellData(excelRow,0,name);
                            excel.setCellData(excelRow,1,cleanPrice+" Lakh");
                            excel.setCellData(excelRow,2,launchDate);
                        excelRow++;
                    }
                } catch (NumberFormatException e) {

                    logger.warn("Failed to parse price for bike: {}", name);
                }
            } else {
                // If no numeric price exists (e.g., "Price To Be Announced")
                excel.setCellData(excelRow,0,name);
                excel.setCellData(excelRow,1,rawPrice);
                excel.setCellData(excelRow,2,launchDate);
                excelRow++;
            }
        }

excel.close();




        logger.info("Bike details written to Excel successfully");






    // Reading from the Excel and print
//    for reading excel
        ExcelUtils readExcel = new ExcelUtils(FILE_PATH,companyName);
        int rowCount = readExcel.getRowCount();

        logger.info("Reading back {} rows from Excel", rowCount - 1);


        for (int i = 1; i <rowCount; i++) {
            System.out.println("=================================================");
            System.out.println("Bike Name:---- "+readExcel.getCellData(i,0));
            System.out.println("Price:---- "+readExcel.getCellData(i,1));
            System.out.println("Expected Launch Date:---"+readExcel.getCellData(i,2));
            System.out.println("=================================================");
        }

readExcel.close();

    }






}
