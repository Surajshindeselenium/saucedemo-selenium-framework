package testBase;

import org.testng.*;
import com.aventstack.extentreports.*;
import reports.ExtentManager;
import utilities.ScreenshotUtils;

public class TestListener implements ITestListener {

    ExtentReports extent = ExtentManager.getReport();
    ExtentTest test;

    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        test.pass("Test Passed");
    }

    public void onTestFailure(ITestResult result) {
        String path = ScreenshotUtils.takeScreenshot(result.getName());
        test.fail(result.getThrowable())
            .addScreenCaptureFromPath(path);
    }

    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
