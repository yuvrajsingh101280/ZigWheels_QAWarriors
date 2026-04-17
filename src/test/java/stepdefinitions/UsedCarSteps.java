package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.UsedCars;

import java.util.List;

public class UsedCarSteps {

    UsedCars usedCars = new UsedCars();
    List<String> models;
    String selectedCity;
    @Given("user opens Used Cars section")
    public void user_opens_Used_Cars_section(){
        usedCars.clickUsedCarsOption();
    }
    @When("user selects city {string}")
    public void selects_city(String city)
    {
        selectedCity = city;
        usedCars.selectCity(city);
    }

    @Then("popular used car models should be displayed")
    public void verify_popular_models(){
//print the car models and write to Excel and read from the Excel
        usedCars.printPopularUsedCarsModels(selectedCity);


        models = usedCars.getPopularModels();
        System.out.println("Total models:"+models.size());


        Assert.assertTrue(!models.isEmpty(),"No car models found!");


    }




}
