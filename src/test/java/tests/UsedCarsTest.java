package tests;

import base.BaseTest;
import base.DriverFactory;
import io.qameta.allure.*;
import listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import pages.UsedCars;
import utilities.ConfigReader;


@Epic("Vehicle Marketplace")
@Feature("Used Cars")
public class UsedCarsTest extends BaseTest {

    @Test
    @Story("View popular used cars by city")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify popular used car models for selected city")
    public void usedCarsDetails() {
        UsedCars usedCars = new UsedCars();
        usedCars.clickUsedCarsOption();
        String city = ConfigReader.get("city");
        usedCars.selectCity(city);
        usedCars.printPopularUsedCarsModels(city);
    }
}
