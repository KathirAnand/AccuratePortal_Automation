package com.accurate.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;


import com.accurate.utilities.ExtentReportUtility;

public class HomePage extends BasePage{

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(css = "a[title='Close']")
	protected WebElement popupCloseIcon;
	
	@FindBy(xpath = "//table[@class='rwTable rwShadow']")
	protected WebElement popup;
	
	@FindBy(xpath = "(//td[text()='Accounting Module'])[2]")
	protected WebElement accountingModuleBtn;
	
	@FindBy(xpath = "//a[text()='Accurate Sync']")
	protected WebElement accurateSyncBtn;
	
//	#ASPxRoundPanel1_HTC_lblHeading  -- launch page
//	  
	
	public HomePage clickAccountingModuleBtn() {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		 boolean frameExists = false;

	        try {
	            // Wait for frame 'workarea' and switch to it
	            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("main"));
	            frameExists = true;
	        } catch (TimeoutException e) {
	            // Frame doesn't exist
	            ExtentReportUtility.info("Frame 'workarea' was not found, so no popup to handle");
	        }
	        if(frameExists) {
	        	WebElement ee=driver.findElement(By.xpath("(//td[text()='Accounting Module'])[2]"));
	    		clickElement(ee);
	        }
		return this;
	}
	
	public AccurateSyncPage clickAccurateSyncBtn() {
		WebElement ee=driver.findElement(By.xpath("//a[text()='Accurate Sync']"));
		clickElement(ee);
		try {
			Thread.sleep(Duration.ofSeconds(10));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
	            driver.switchTo().defaultContent();
	        } catch (Exception ex) {
	            // Ignore
	        }
		return new AccurateSyncPage(driver);
	}
	
	public HomePage handlePopupAfterLogin() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        boolean frameExists = false;

	        try {
	            // Wait for frame 'workarea' and switch to it
	            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("workarea"));
	            frameExists = true;
	        } catch (TimeoutException e) {
	            // Frame doesn't exist
	            ExtentReportUtility.info("Frame 'workarea' was not found, so no popup to handle");
	        }

	        if (frameExists) {
	            try {
	                // Check if popup table is present
	                boolean isPopupDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//table[@class='rwTable rwShadow']"))).isDisplayed();

	                if (isPopupDisplayed) {
	                    WebElement closeButton = driver.findElement(By.cssSelector("a[title='Close']"));
	                    closeButton.click();
	                    ExtentReportUtility.info("Popup was displayed and closed successfully");
	                }
	            } catch (TimeoutException e) {
	                // Popup not found
	                ExtentReportUtility.info("Frame exists but no popup was displayed after login");
	            } finally {
	                driver.switchTo().defaultContent();
	            }
	        }
	    } catch (Exception e) {
	        ExtentReportUtility.warning("Error while handling popup: " + e.getMessage());
	        try {
	            driver.switchTo().defaultContent();
	        } catch (Exception ex) {
	            // Ignore
	        }
	    }

	    return this; // Always return the HomePage object
	}

}
