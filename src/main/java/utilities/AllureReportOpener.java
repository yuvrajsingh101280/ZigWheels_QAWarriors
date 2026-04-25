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

//open the terminal and open the allure report
public static void openAllureReport() {
    try {
        String resultsDir = "allure-results";

        logger.info("Opening Allure report from directory: {}", resultsDir);

        ProcessBuilder pb = new ProcessBuilder(
                "cmd.exe", "/c", "start", "cmd.exe", "/k",
                "allure serve " + resultsDir
        );

// DO NOT use inheritIO()
        pb.start();

        logger.info("Allure report started in a separate terminal");

    } catch (IOException e) {
        logger.error("Failed to open Allure report", e);
        e.printStackTrace();
    }
}

}