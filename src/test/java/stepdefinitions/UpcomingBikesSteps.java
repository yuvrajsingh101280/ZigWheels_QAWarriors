package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.MenuPage;

public class UpcomingBikesSteps {
    private static final Logger logger = LogManager.getLogger(UpcomingBikesSteps.class);
    MenuPage menuPage = new MenuPage();
    String selectedCompany;
    @Given("user opens Upcoming Bikes Section")
    public void user_opens_Upcoming_Bikes_Section(){
        logger.info("Opening Upcoming Bikes section");
    menuPage.clickUpcomingBikeOption();

    }


    @When("user filters by bike company {string}")
    public void user_filters_by_bike_company(String company)
    {
        logger.info("Filtering upcoming bikes by company: {}", company);
        selectedCompany = company;
        menuPage.setBikeCompanyForScenario(selectedCompany);
        menuPage.clickBike();

    }

    @Then("upcoming bikes should be displayed and stored in Excel")
    public void extract_and_store_bikes(){
        logger.info("Extracting and storing upcoming bikes for company: {}", selectedCompany);
        menuPage.printBikeDetails(selectedCompany);
    }




}
