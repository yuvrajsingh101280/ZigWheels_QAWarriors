package listeners;

import io.qameta.allure.Allure;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private static final int maxTry = 1; // 1 retry → total 2 runs

    @Override
    public boolean retry(ITestResult result) {

        if (!result.isSuccess() && count < maxTry) {
            count++;

            // Show retry attempt in Allure "Parameters"
            Allure.parameter("Retry Attempt", count + " of " + maxTry);

            // Show retry reason in Allure timeline/steps
            if (result.getThrowable() != null) {
                Allure.step(
                        "Retry triggered due to failure: "
                                + result.getThrowable().getClass().getSimpleName()
                                + " - "
                                + result.getThrowable().getMessage()
                );
            } else {
                Allure.step("Retry triggered due to test failure");
            }

            System.out.println(
                    "Retrying test: "
                            + result.getMethod().getMethodName()
                            + " | Retry #" + count
            );

            return true;
        }
        return false;
    }


    public int getCount() {
        return count;
    }
}