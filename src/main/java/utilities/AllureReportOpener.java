package utilities;

import java.io.File;
import java.io.IOException;

public class AllureReportOpener {

    public static void cleanAllureResults() {
        File resultsDir = new File("allure-results");
        if (resultsDir.exists()) {
            // Only delete the files inside, not the folder itself,
            // to avoid permission issues during the next run
            File[] files = resultsDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }


    public static void openAllureReport() {
        try {
            String resultsDir = "allure-results";
            // Or "target/allure-results" if you've configured it that way

            System.out.println(">>> Opening Allure Report from: " + resultsDir);

            // Step 1: Using 'serve' is better than 'generate' + 'open' for local runs
            // It starts a local jetty server automatically.
            ProcessBuilder pb = new ProcessBuilder(
                    "cmd.exe", "/c", "allure serve " + resultsDir
            );

            pb.inheritIO();
            pb.start();

            // IMPORTANT: We do NOT use .waitFor() here.
            // If you do, your IDE will stay 'Running' forever because
            // the Allure server is waiting for you to look at the report.

        } catch (IOException e) {
            System.err.println("ERROR: Allure CLI not found. Did you install it and add to System PATH?");
            e.printStackTrace();
        }
    }
}