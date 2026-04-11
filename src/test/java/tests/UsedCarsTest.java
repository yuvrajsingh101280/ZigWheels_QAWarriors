package tests;

import base.BaseTest;
import base.DriverFactory;
import org.testng.annotations.Test;
import pages.UsedCars;

public class UsedCarsTest extends BaseTest {

    @Test
    public void usedCarsDetails() {
        UsedCars usedCars = new UsedCars(driver);
        usedCars.clickUsedCarsOption();
        usedCars.selectCity("Chennai");
        usedCars.printPopularUsedCarsModels();
    }
}
