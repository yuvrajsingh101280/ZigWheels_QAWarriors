package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.MenuPage;

public class UpcomingBikesSteps {

    MenuPage menuPage = new MenuPage();
    String selectedCompany;
    @Given("user opens Upcoming Bikes Section")
    public void user_opens_Upcoming_Bikes_Section(){

    menuPage.clickUpcomingBikeOption();

    }


    @When("user filters by bike company {string}")
    public void user_filters_by_bike_company(String company)
    {
        selectedCompany = company;
        menuPage.setBikeCompanyForScenario(selectedCompany);
        menuPage.clickBike();

    }

    @Then("upcoming bikes should be displayed and stored in Excel")
    public void extract_and_store_bikes(){
        menuPage.printBikeDetails(selectedCompany);
    }




}
