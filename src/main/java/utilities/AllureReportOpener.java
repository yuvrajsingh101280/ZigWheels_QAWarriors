package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class AllureReportOpener {

    private static final Logger logger =
            LogManager.getLogger(AllureReportOpener.class);

    public static void cleanAllureResults() {
        File resultsDir = new File("allure-results");
        if (resultsDir.exists()) {

            logger.info("Cleaning existing Allure results directory");

            // Only delete the files inside, not the folder itself,
            // to avoid permission issues during the next run
            File[] files = resultsDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    boolean    deleted = file.delete();
                    if(!deleted)
                    {

                        logger.warn("Unable to delete file: {}", file.getName());

                    }
//                    file.delete();
                }
            }
            logger.info("Allure results directory cleaned");
        }else{

            logger.info("No existing Allure results directory found");

        }



    }


    public static void openAllureReport() {
        try {
            String resultsDir = "allure-results";

            logger.info("Opening Allure report from directory: {}", resultsDir);

            // Or "target/allure-results" if you've configured it that way

//            System.out.println(">>> Opening Allure Report from: " + resultsDir);

            // Step 1: Using 'serve' is better than 'generate' + 'open' for local runs
            // It starts a local jetty server automatically.
            ProcessBuilder pb = new ProcessBuilder(
                    "cmd.exe", "/c", "allure serve " + resultsDir
            );

            pb.inheritIO();
            pb.start();

            logger.info("Allure report server started successfully");

            // IMPORTANT: We do NOT use .waitFor() here.
            // If you do, your IDE will stay 'Running' forever because
            // the Allure server is waiting for you to look at the report.

        } catch (IOException e) {

            logger.error("Failed to open Allure report. Ensure Allure CLI is installed and added to PATH.", e);
            e.printStackTrace();
        }
    }
}