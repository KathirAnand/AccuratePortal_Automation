package com.accurate.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.openqa.selenium.Dimension;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;

//MediaEntity related imports
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;

/**
 * Utility class to handle ExtentReport operations
 */
public class ExtentReportUtility implements ITestListener{

	
    private static ExtentReports extent;
    private static Map<Integer, ExtentTest> testMap = new HashMap<>();
    private static String reportDir;
    private static String reportFilePath;

    /**
     * Initialize the ExtentReport
     */
    public static void initReport() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        reportDir = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "reports";
        reportFilePath = reportDir + File.separator + "AccuratePortal_" + timeStamp + ".html";
        
        File dir = new File(reportDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
        sparkReporter.config().setDocumentTitle("Accurate Portal Automation Report");
        sparkReporter.config().setReportName("Accurate Portal Invoice Transfer");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
    }
    
    
    /**
     * Create a test in the report
     * @param testName The name of the test
     * @return ExtentTest object
     */
    public static synchronized ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        testMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }
    
    /**
     * Create a test with description in the report
     * @param testName The name of the test
     * @param description Description of the test
     * @return ExtentTest object
     */
    public static synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        testMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }
    
    /**
     * Get the current test
     * @return ExtentTest object for the current thread
     */
    public static synchronized ExtentTest getTest() {
        return testMap.get((int) Thread.currentThread().getId());
    }
    
    /**
     * Log a pass step
     * @param details Details to log
     */
    public static void pass(String details) {
        getTest().log(Status.PASS, details);
    }
    
    /**
     * Log a fail step
     * @param details Details to log
     */
    public static void fail(String details) {
        getTest().log(Status.FAIL, details);
    }
    
    /**
     * Log a fail step with exception
     * @param details Details to log
     * @param e Exception to log
     */
    public static void fail(String details, Throwable e) {
        getTest().log(Status.FAIL, details);
        getTest().log(Status.FAIL, e);
    }
    
    /**
     * Log an info step
     * @param details Details to log
     */
    public static void info(String details) {
        getTest().log(Status.INFO, details);
    }
    
    /**
     * Log a warning step
     * @param details Details to log
     */
    public static void warning(String details) {
        getTest().log(Status.WARNING, details);
    }
    
    /**
     * Log a skip step
     * @param details Details to log
     */
    public static void skip(String details) {
        getTest().log(Status.SKIP, details);
    }
    
    /**
     * Add a screenshot to the report
     * @param screenshotPath Path to the screenshot
     * @param title Title for the screenshot
     */
    public static void addScreenshot(String screenshotPath, String title) {
        try {
            getTest().addScreenCaptureFromPath(screenshotPath, title);
        } catch (Exception e) {
        	getTest().log(Status.FAIL, "Failed to add screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Flush the report (write to disk)
     */
    public static void flushReport() {
        extent.flush();
    }
    
    /**
     * Get the report file path
     * @return Path to the generated report
     */
    public static String getReportFilePath() {
        return reportFilePath;
    }
    
    /**
     * Captures screenshot and returns the path where it was saved
     * @param driver WebDriver instance
     * @param testName Name to be used for the screenshot file
     * @return Path to the saved screenshot
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            return null;
        }
        
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotName = testName + "_" + timestamp + ".png";
            String screenshotDir = System.getProperty("user.dir") + File.separator + "test-output" + 
                                   File.separator + "screenshots";
            
            // Create screenshots directory if it doesn't exist
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            String screenshotPath = screenshotDir + File.separator + screenshotName;
            
            // Take screenshot
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            // Save screenshot
            FileUtils.copyFile(source, new File(screenshotPath));
            
            // Return relative path for report
            return "screenshots" + File.separator + screenshotName;
        } catch (Exception e) {
            System.out.println("Error taking screenshot: " + e.getMessage());
            return null;
        }
    }
    
    public static String captureFullPageScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Create timestamp for unique filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = screenshotName + "_" + timestamp + ".png";
            
            // Define screenshot directory path
            String screenshotDir = System.getProperty("user.dir") + File.separator + "test-output" +
                                  File.separator + "screenshots";
            
            // Create directory if it doesn't exist
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Force the browser to maximize to ensure full width capture
            Dimension originalSize = driver.manage().window().getSize();
            driver.manage().window().maximize();
            
            // Use a more configurable strategy
            Screenshot screenshot = new AShot()
                .coordsProvider(new WebDriverCoordsProvider())
                .shootingStrategy(ShootingStrategies.viewportPasting(
                    ShootingStrategies.scaling(1.0f), 500))
                .takeScreenshot(driver);
            
            // Restore original window size
            driver.manage().window().setSize(originalSize);
            
            // Save the screenshot to file
            String fullPath = screenshotDir + File.separator + filename;
            ImageIO.write(screenshot.getImage(), "PNG", new File(fullPath));
            
            // Return relative path for report
            return "screenshots" + File.separator + filename;
            
        } catch (Exception e) {
            System.out.println("Error capturing full page screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public static String captureFullPageScreenshotByNative(WebDriver driver, String screenshotName) {
        try {
            // Create timestamp for unique filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = screenshotName + "_" + timestamp + ".png";
            
            // Define screenshot directory path
            String screenshotDir = System.getProperty("user.dir") + File.separator + "test-output" +
                                  File.separator + "screenshots";
            
            // Create directory if it doesn't exist
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Force browser to be exactly 1920x1080 (your screen resolution)
            driver.manage().window().setSize(new Dimension(1920, 1080));
            
            // Get page dimensions
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Long totalWidth = (Long) js.executeScript(
                "return Math.max(document.body.scrollWidth, document.documentElement.scrollWidth, " +
                "document.body.offsetWidth, document.documentElement.offsetWidth, " +
                "document.body.clientWidth, document.documentElement.clientWidth);"
            );
            
            Long totalHeight = (Long) js.executeScript(
                "return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight, " +
                "document.body.offsetHeight, document.documentElement.offsetHeight, " +
                "document.body.clientHeight, document.documentElement.clientHeight);"
            );
            
            // Set the browser window size to match the full content size
            driver.manage().window().setSize(new Dimension(totalWidth.intValue(), totalHeight.intValue()));
            
            // Now take the screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            // Save the screenshot
            String fullPath = screenshotDir + File.separator + filename;
            FileUtils.copyFile(screenshot, new File(fullPath));
            
            // Return relative path for report
            return "screenshots" + File.separator + filename;
            
        } catch (Exception e) {
            System.out.println("Error capturing full page screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            // Reset browser size to original dimensions
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }
    }
    
    public static void addScreenshotBase64(WebDriver driver, String stepDescription, Status status) {
        try {
            if (driver == null) {
                getTest().log(status, stepDescription + " (No screenshot: WebDriver is null)");
                return;
            }
            
            // Capture screenshot as Base64
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            
            // Attach to report
            Media  mediaModel = MediaEntityBuilder
                    .createScreenCaptureFromBase64String(base64Screenshot).build();
            
            getTest().log(status, stepDescription, mediaModel);
        } catch (Exception e) {
            getTest().log(status, stepDescription + " (Screenshot failed: " + e.getMessage() + ")");
        }
    }
}