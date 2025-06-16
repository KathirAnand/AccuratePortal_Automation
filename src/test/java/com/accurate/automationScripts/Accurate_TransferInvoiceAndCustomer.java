package com.accurate.automationScripts;

import java.time.Duration;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.accurate.automationBase.BaseClass;
import com.accurate.pages.AccurateSyncPage;
import com.accurate.pages.HomePage;
import com.accurate.pages.LoginPage;

@Listeners(com.accurate.utilities.ExtentReportListener.class)
public class Accurate_TransferInvoiceAndCustomer extends BaseClass {

	AccurateSyncPage accSynPage;
	HomePage homePage;
	
	
	@Test
	public void login() {
		new LoginPage(driver)
		.setUserName(props.getProperty("userName"))
		.setPassword(props.getProperty("password"))
		.clickSignInBtn()
		.validateCredentials();
		try {
			Thread.sleep(Duration.ofSeconds(5));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
//	@Test(dependsOnMethods = {"login"})
	public void accuratePortalInvoiceTransfer() {
		int retryCount;
		accSynPage = new AccurateSyncPage(driver);
		homePage = new HomePage(driver);
		homePage.handlePopupAfterLogin()
		.clickAccountingModuleBtn()
		.clickAccurateSyncBtn()
		.getItemsCount()
		.setInvoiceCount();
		
		retryCount=(int) Math.ceil(AccurateSyncPage.totalInvoices / 30.0);
		
		if(AccurateSyncPage.totalInvoices>30) {
			for(int i=0;i<=retryCount;i++) {
				if(i==0) {
					if (accSynPage.isInvoicesToTransfer()) {
//						accSynPage.getPageSize();
						if (accSynPage.isAllInvoiceHasCustomer()) {
							accSynPage.clickCheckAllCheckBox()
							.clickTransferInvoice();
						} else {
							System.out.println("transfer customer");
							accSynPage.clickTransferCustomer()
							.clickCheckAllCheckBox()
							.clickTransferInvoice();
						}
					}
				}
				else {
					driver.navigate().refresh();
					homePage.backToDefaultFrame();
					homePage.clickAccountingModuleBtn()
					.clickAccurateSyncBtn()
					.getItemsCount();
					if (accSynPage.isInvoicesToTransfer()) {
//						accSynPage.getPageSize();
						if (accSynPage.isAllInvoiceHasCustomer()) {
							accSynPage.clickCheckAllCheckBox()
							.clickTransferInvoice();
						} else {
							System.out.println("transfer customer");
							accSynPage.clickTransferCustomer()
							.clickCheckAllCheckBox()
							.clickTransferInvoice();
						}
					}
				}
			}
		}
		else {
			if (accSynPage.isInvoicesToTransfer()) {
				try {
					accurateTransfer();
				}catch(Exception e) {
					driver.navigate().refresh();
					homePage.backToDefaultFrame();
					homePage.clickAccountingModuleBtn()
					.clickAccurateSyncBtn()
					.getItemsCount();
					accurateTransfer();				
				}
			}
		}
	}
	
	public void accurateTransfer() {
		if (accSynPage.isAllInvoiceHasCustomer()) {
			accSynPage.clickCheckAllCheckBox()
			.clickTransferInvoice();
//			.takeScreenshot();
		} else {
			System.out.println("transfer customer");
			accSynPage.clickTransferCustomer()
			.clickCheckAllCheckBox()
			.clickTransferInvoice();
//			.takeScreenshot();
		}
	}
	
}
