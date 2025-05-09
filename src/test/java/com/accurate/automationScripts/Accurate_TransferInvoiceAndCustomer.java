package com.accurate.automationScripts;

import java.time.Duration;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.accurate.automationBase.BaseClass;
import com.accurate.pages.AccurateSyncPage;
import com.accurate.pages.HomePage;
import com.accurate.pages.LoginPage;

@Listeners(com.accurate.utilities.ExtentReportListener.class)
public class Accurate_TransferInvoiceAndCustomer extends BaseClass {
	
	@BeforeMethod
	public void login() {
		new LoginPage(driver).setUserName("Ushadevi").setPassword("Ushadevi@13").clickSignInBtn();
		try {
			Thread.sleep(Duration.ofSeconds(5));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void accuratePortalInvoiceTransfer() {
		AccurateSyncPage accSynPage=new AccurateSyncPage(driver);
			new HomePage(driver)
			.handlePopupAfterLogin()
			.clickAccountingModuleBtn()
			.clickAccurateSyncBtn()
			.getItemsCount()
			.getPageSize()
			.takeScreenshot();
			if(accSynPage.isAllInvoiceHasCustomer()) {
				accSynPage.clickCheckAllCheckBox()
				.clickTransferInvoice();
			}
			else {
				System.out.println("transfer customer");
				accSynPage.clickTransferCustomer()
				.clickCheckAllCheckBox()
				.clickTransferInvoice();
			}
	}
	
}
