package listeners;

import base.DriverFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class AllureListeners implements ITestListener, IInvokedMethodListener {

    @Override
    public void afterInvocation(IInvokedMethod method , ITestResult iTestResult) {
        if(method.isTestMethod() && iTestResult.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test failed. Capturing screenshot in afterInvocation...");
            WebDriver driver = DriverFactory.getDriver();
            if(driver != null) {
                Allure.addAttachment(iTestResult.getMethod().getMethodName() + " - Failed Screenshot",
                        new ByteArrayInputStream(((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES)));
                System.out.println("Screenshot successfully attached to Allure!");
            }
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {

    }
}



