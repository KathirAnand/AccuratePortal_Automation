package com.accurate.pages;

import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import com.accurate.pageActions.PageActions;
import com.accurate.utilities.ExtentReportUtility;

public class BasePage extends PageActions {

    protected WebDriver driver;
    protected PageActions action = new PageActions();
    public static  boolean frameExists = false;

    /**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	// Using @FindBy to locate the loading element with PageFactory
    @FindBy(css = "raDiv")
    private WebElement loadingSpinner;
    
    // You could also use a different loading indicator if needed
    @FindBy(id = "LoadingPanel1FormHeaderPane")
    private WebElement loadingCursor;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Waits for the loading cursor/spinner to become invisible
     * This method can be called from any page that extends BasePage
     */
    public void waitForLoadingCursorToInvisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            
            // Option 1: Using the PageFactory element
            wait.until(ExpectedConditions.invisibilityOf(loadingCursor));
            
            Thread.sleep(Duration.ofSeconds(15));
            // Option 2: Alternative approach if the loading element is dynamic
            // wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("your-loading-cursor-locator")));
            
        } catch (Exception e) {
           e.getMessage();
        }
    }
    
    public void backToDefaultFrame() {
    	try {
    		driver.switchTo().defaultContent();
    	}catch(Exception e) {
    		e.getMessage();
    	}
    }
    
    public void addStaticWaitAsSeconds(int secs) {
    	try {
    		Thread.sleep(Duration.ofSeconds(secs));
    	}catch(Exception e) {
    		e.getMessage();
    	}
    }
    
    public void toSideMenu() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        try {
	            // Wait for frame 'workarea' and switch to it
	            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("main"));
	            frameExists = true;
	        } catch (TimeoutException e) {
	            // Frame doesn't exist
	            ExtentReportUtility.info("Frame 'main' was not found, so no popup to handle");
	        }
    }
}