package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import pages.UsedCars;

import java.util.List;

public class UsedCarSteps {
    private static final Logger logger = LogManager.getLogger(UsedCarSteps.class);
    UsedCars usedCars = new UsedCars();
    List<String> models;
    String selectedCity;
    @Given("user opens Used Cars section")
    public void user_opens_Used_Cars_section(){

        logger.info("Opening Used Cars section");

        usedCars.clickUsedCarsOption();
    }
    @When("user selects city {string}")
    public void selects_city(String city)
    {

        logger.info("Selecting city for used cars: {}", city);

        usedCars.selectCity(city);
    }

    @Then("popular used car models should be displayed")
    public void verify_popular_models(){
//print the car models and write to Excel and read from the Excel

        logger.info("Selecting city for used cars: {}", selectedCity);

        usedCars.printPopularUsedCarsModels(selectedCity);


        models = usedCars.getPopularModels();

        logger.info("Total popular used car models found: {}", models.size());

        System.out.println("Total models:"+models.size());


        Assert.assertTrue(!models.isEmpty(),"No car models found!");

        logger.info("Popular used car models validation passed");



    }




}
