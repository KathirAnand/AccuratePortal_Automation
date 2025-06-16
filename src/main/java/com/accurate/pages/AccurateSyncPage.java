package com.accurate.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accurate.automationBase.BaseClass;
import com.accurate.utilities.EmailUtility;
import com.accurate.utilities.ExtentReportUtility;

public class AccurateSyncPage extends BasePage {

	public AccurateSyncPage(WebDriver driver) {
		super(driver);
	}
//	#lblHeading  -- page heading

	public static String screenshotPath;
	public static int itemCount;
	public static int totalInvoices;
	public static int balanceInvoiceCount;

	@FindBy(xpath = "//div[@class='panel-body']/descendant::tbody/child::tr")
	List<WebElement> customerRow;

	@FindBy(xpath = "//span[text()='Transfer Customer']")
	WebElement transferCustomerBtn;

	@FindBy(xpath = "//span[text()='Transfer Invoice']")
	WebElement transferInvoiceBtn;

	@FindBy(css = "#RGInvoiceDetails_ctl00_ctl02_ctl01_GridClientSelectColumnSelectCheckBox")
	WebElement checkAllBtn;

	@FindBy(css = "#RGInvoiceDetails_ctl00_ctl03_ctl01_ChangePageSizeTextBox")
	WebElement pageSizeInputField;

	@FindBy(xpath = "//span[text()='Change']")
	WebElement changeBtn;

	@FindBy(xpath = "//div[@class='rgWrap rgInfoPart']")
	WebElement numberOfItemsTxt;

	@FindBy(xpath = "//div[text()='No records to display.']")
	WebElement noRecordsFoundMessage;

	public AccurateSyncPage getItemsCount() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			// Wait for frame 'workarea' and switch to it
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("workarea"));
		} catch (TimeoutException e) {
			// Frame doesn't exist
			ExtentReportUtility.info("Frame 'workarea' was not found, so no popup to handle");
		}
		String noOfItemCount = numberOfItemsTxt.getText();
		String[] parts = noOfItemCount.split("\\s+");
		itemCount = Integer.parseInt(parts[6]);
		ExtentReportUtility.info("Total Number of Invoices are " + itemCount);
		logger.info("Total Number of Invoices are " + itemCount);
//		System.out.println(noOfItemCount);
		return this;
	}

	public AccurateSyncPage setInvoiceCount() {
		totalInvoices = itemCount;
		return this;
	}

	public boolean isInvoicesToTransfer() {
		boolean isAllInvoiceToTransfer = true;
		if (itemCount == 0) {
			isAllInvoiceToTransfer = false;
		}
		return isAllInvoiceToTransfer;
	}

	public AccurateSyncPage getPageSize() {
		String pageSize = pageSizeInputField.getDomAttribute("value");
//		System.out.println(pageSize);
		if (itemCount > Integer.parseInt(pageSize)) {
			clearAndType(pageSizeInputField, itemCount);
			clickElement(changeBtn);
			try {
				Thread.sleep(Duration.ofSeconds(10));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this;
	}

	public boolean isAllInvoiceHasCustomer() {
		boolean isAllInvoiceHasCustomer = true;
		System.out.println(customerRow.size());
		for (WebElement customer : customerRow) {
			List<WebElement> customerCol = customer.findElements(By.tagName("td"));
//			System.out.println(customerCol.size());
			WebElement accurateCustomer = customerCol.get(12);
			WebElement customerCheckBox = customerCol.get(0);
			if (accurateCustomer.getText().equals(" ")) {
				try {
					Thread.sleep(Duration.ofSeconds(1));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				customerCheckBox.click();
				try {
					Thread.sleep(Duration.ofSeconds(1));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isAllInvoiceHasCustomer = false;
				ExtentReportUtility.info(customerRow.indexOf(customer) + " Invoice does not have the customer");
			}
		}
		if (isAllInvoiceHasCustomer) {
			ExtentReportUtility.info("All Invoices have the customer");
			logger.info("All Invoices have the customer");
		}
		return isAllInvoiceHasCustomer;
	}

	public AccurateSyncPage clickTransferCustomer() {

		clickElement(transferCustomerBtn);

		// Wait for loading cursor to disappear (using method from BasePage)
		waitForLoadingCursorToInvisible();

		// Wait for alert to be present and accept it
		waitForAlertAndAccept();

		ExtentReportUtility.info("Completed full Transfer Customer process");
		logger.info("Completed full Transfer Customer process");

		return this;
	}

	public AccurateSyncPage clickCheckAllCheckBox() {
		clickElement(checkAllBtn);
		ExtentReportUtility.info("Check All Invoice Checkbox clicked sucessfully");
		logger.info("Check All Invoice Checkbox clicked sucessfully");
//		takeScreenshot();
		return this;
	}

	public AccurateSyncPage clickTransferInvoice() {

		clickElement(transferInvoiceBtn);
		ExtentReportUtility.info("Transfer Invoice button clicked sucessfully");
		logger.info("Transfer Invoice button clicked sucessfully");
		int retryCount = 0;
		if (itemCount > 30) {
			if (retryCount == 0) {
				try {
					Thread.sleep(Duration.ofMinutes(2));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				driver.navigate().refresh();
//				backToDefaultFrame();
//				HomePage home=new HomePage(driver);
//				home.clickAccountingModuleBtn();
//				home.clickAccurateSyncBtn();
//				isAllInvoiceHasCustomer();
//				clickCheckAllCheckBox();
//				clickTransferInvoice();
//				++retryCount;
//				if(itemCount>0) {
//					getBalanceInvoiceDetails();
//				}
			}
		} else {
			if (itemCount < 15) {
				// Wait for loading cursor to disappear (using method from BasePage)
				waitForLoadingCursorToInvisible();

				// Wait for alert to be present and accept it
				waitForAlertAndAccept();

			} else {
				addStaticWaitAsSeconds(40);
				// Wait for loading cursor to disappear (using method from BasePage)
				waitForLoadingCursorToInvisible();

				// Wait for alert to be present and accept it
				waitForAlertAndAccept();
			}
			if (itemCount > 0) {
				getBalanceInvoiceDetails();
			}

			ExtentReportUtility.info("Completed full Transfer Invoice process with alert handling");
			logger.info("Completed full Transfer Invoice process with alert handling");
		}
		return this;
	}

	/**
	 * Waits for alert to be present and accepts it
	 */
	private void waitForAlertAndAccept() {
		try {
			ExtentReportUtility.info("Waiting for alert to be present");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			// Wait for alert to be present
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());

			// Log alert text
			String alertText = alert.getText();
			ExtentReportUtility.info("Alert displayed with text: " + alertText);

			// Accept the alert
			alert.accept();
			ExtentReportUtility.info("Alert accepted successfully");
			logger.info("Alert accepted successfully");

		} catch (Exception e) {
			ExtentReportUtility.info("Error while waiting for or accepting alert: " + e.getMessage());
			logger.info(e.getMessage());
		}
	}

	/**
	 * Verifies the Accurate Sync page is loaded and captures a screenshot
	 * 
	 * @return this page object for method chaining
	 */
	public AccurateSyncPage takeScreenshot() {
		try {

			// Capture screenshot using your existing utility method
			screenshotPath = ExtentReportUtility.captureScreenshot(driver, "AccurateSyncPage");

			// Add screenshot to the report
//			if (screenshotPath != null) {
//				ExtentReportUtility.addScreenshot(screenshotPath, "Accurate Sync Page Loaded");
//				logger.info("Screenshot captured for Accurate Sync Page");
//			}

			// Add screenshot to the report
			if (ExtentReportUtility.getTest() != null) {
				try {
					ExtentReportUtility.getTest().addScreenCaptureFromPath(screenshotPath);
					logger.info("Screenshot captured for Accurate Sync Page");
				} catch (Exception e) {
					System.err.println("Error attaching screenshot to report: " + e.getMessage());
					logger.info(e.getMessage());
				}
			}
			return this;
		} catch (Exception e) {
			logger.error("Error verifying Accurate Sync Page load: " + e.getMessage());
			logger.info(e.getMessage());
			throw e; // Or handle as appropriate for your framework
		}
	}

	public void getBalanceInvoiceDetails() {
		try {
			Thread.sleep(Duration.ofSeconds(3));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String numberOfItems = driver.findElement(By.xpath("//div[@class='rgWrap rgInfoPart']")).getText();

		String[] parts = numberOfItems.split("\\s+");
		int itemCount = Integer.parseInt(parts[6]);
		if (itemCount > 0) {
			balanceInvoiceCount = driver
					.findElements(By.xpath("//div[@class='panel-body']/descendant::tbody/child::tr")).size();
		}

	}

}
