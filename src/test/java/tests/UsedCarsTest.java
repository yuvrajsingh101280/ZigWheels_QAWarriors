package tests;

import base.BaseTest;
import base.DriverFactory;
import org.testng.annotations.Test;
import pages.UsedCars;
import utilities.ConfigReader;

public class UsedCarsTest extends BaseTest {

    @Test(priority = 2)
    public void usedCarsDetails() {
        UsedCars usedCars = new UsedCars(driver);
        usedCars.clickUsedCarsOption();
        String city = ConfigReader.get("city");
        usedCars.selectCity(city);
        usedCars.printPopularUsedCarsModels();
    }
}
