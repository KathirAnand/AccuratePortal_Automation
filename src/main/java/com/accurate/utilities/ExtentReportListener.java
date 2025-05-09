package com.accurate.utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;

public class ExtentReportListener implements ITestListener {
    
    @Override
    public void onStart(ITestContext context) {
        ExtentReportUtility.initReport();
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportUtility.createTest(result.getMethod().getMethodName(), 
                                      result.getMethod().getDescription());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportUtility.pass("Test passed successfully");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        // Get WebDriver instance from your test class
        Object testInstance = result.getInstance();
        WebDriver driver = null;
        
        try {
            // This assumes your test classes have a getDriver() method
            // or extend BaseClass which has this method
            driver = (WebDriver) testInstance.getClass().getMethod("getDriver").invoke(testInstance);
        } catch (Exception e) {
            ExtentReportUtility.fail("Could not get WebDriver: " + e.getMessage());
        }
        
        if (driver != null) {
            String screenshotPath = ExtentReportUtility.captureScreenshot(driver, result.getName());
            ExtentReportUtility.fail("Test failed: " + result.getThrowable().getMessage());
            ExtentReportUtility.addScreenshot(screenshotPath, "Failure Screenshot");
        } else {
            ExtentReportUtility.fail("Test failed: " + result.getThrowable().getMessage());
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportUtility.skip("Test skipped: " + result.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        ExtentReportUtility.flushReport();
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }
}