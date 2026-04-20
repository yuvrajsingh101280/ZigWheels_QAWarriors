package listeners;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utilities.ConfigReader;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;

    private static final int maxTry = Integer.parseInt(ConfigReader.get("maxTry")); // 1 retry → total 2 runs

    private static final Logger logger =
            LogManager.getLogger(RetryAnalyzer.class);

    @Override
    public boolean retry(ITestResult result) {

        if (!result.isSuccess() && count < maxTry) {
            count++;

            // Show retry attempt in Allure "Parameters"
            Allure.parameter("Retry Attempt", count + " of " + maxTry);

            // Show retry reason in Allure timeline/steps
            if (result.getThrowable() != null) {


                logger.error(
                        "Retry triggered due to exception: {} - {}",
                        result.getThrowable().getClass().getSimpleName(),
                        result.getThrowable().getMessage()
                );


                Allure.step(
                        "Retry triggered due to failure: "
                                + result.getThrowable().getClass().getSimpleName()
                                + " - "
                                + result.getThrowable().getMessage()
                );
            } else {

                logger.error("Retry triggered due to test failure without exception");

                Allure.step("Retry triggered due to test failure");
            }




            logger.info("Retrying test: {} | Retry # {}",result.getMethod().getMethodName(),count);

            return true;
        }

        logger.info(
                "No retry required / max retry reached for test: {}",
                result.getMethod().getMethodName()
        );

        return false;
    }


    public int getCount() {
        return count;
    }
}