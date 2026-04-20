package runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import org.testng.annotations.*;
import utilities.AllureReportOpener;
@CucumberOptions(

        features = "src/test/resources/features",
        glue = {"stepdefinitions","hooks"},
        tags = "@smoke",
        plugin = {"pretty",
                "html:target/cucumber-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",

        },
        monochrome = true





)



public class TestRunner extends AbstractTestNGCucumberTests {


        @BeforeSuite
        public void beforeSuite() {
                System.out.println("--- Starting Test Suite: Allure Results Cleaned ---");
                AllureReportOpener.cleanAllureResults();
        }







        @AfterSuite
        public void afterSuite() {
                System.out.println("--- All Tests Finished. Launching Allure Report ---");
                AllureReportOpener.openAllureReport();
        }







}
