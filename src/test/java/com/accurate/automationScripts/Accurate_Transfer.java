package com.accurate.automationScripts;

import java.time.Duration;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.accurate.automationBase.BaseClass;
import com.accurate.pages.AccurateSyncPage;
import com.accurate.pages.HomePage;
import com.accurate.pages.LoginPage;
import com.accurate.utilities.ExtentReportUtility;

@Listeners(com.accurate.utilities.ExtentReportListener.class)
public class Accurate_Transfer extends BaseClass {
    
    private AccurateSyncPage accSynPage;
    private HomePage homePage;
    private static final int SLEEP_DURATION = 5;
    private static final int INVOICES_PER_PAGE = 30;
    
    @Test
    public void login() {
        new LoginPage(driver)
            .setUserName(props.getProperty("userName"))
            .setPassword(props.getProperty("password"))
            .clickSignInBtn()
            .validateCredentials();
        
        waitForSeconds(SLEEP_DURATION);
    }
    
    @Test(dependsOnMethods = {"login"})
    public void accuratePortalInvoiceTransfer() {
        initializePageObjects();
        navigateToAccurateSync();
        
        int retryCount = calculateRetryCount();
        
        if (AccurateSyncPage.totalInvoices > INVOICES_PER_PAGE) {
            processMultiplePages(retryCount);
        } else {
            processSinglePage();
            refreshAndNavigateToAccurateSync();
            if (accSynPage.isInvoicesToTransfer()) {
                performAccurateTransfer();
            }
        }
    }
    
    private void initializePageObjects() {
        accSynPage = new AccurateSyncPage(driver);
        homePage = new HomePage(driver);
    }
    
    private void navigateToAccurateSync() {
        homePage.handlePopupAfterLogin()
            .clickAccountingModuleBtn()
            .clickAccurateSyncBtn()
            .getItemsCount()
            .setInvoiceCount();
    }
    
    private int calculateRetryCount() {
        return (int) Math.ceil(AccurateSyncPage.totalInvoices / (double) INVOICES_PER_PAGE);
    }
    
    private void processMultiplePages(int retryCount) {
        for (int i = 0; i <= retryCount; i++) {
            if (i == 0) {
                processFirstPage();
                ExtentReportUtility.info("Page 1");
    			logger.info("Page 1");
            } else {
                processSubsequentPage();
                ExtentReportUtility.info("Page "+i+1);
    			logger.info("Page "+i+1);
            }
            if(i==retryCount) {
            	accSynPage.takeScreenshot();
            }
        }
    }
    
    private void processFirstPage() {
        if (accSynPage.isInvoicesToTransfer()) {
            performAccurateTransfer();
        }
    }
    
    private void processSubsequentPage() {
        refreshAndNavigateToAccurateSync();
        if (accSynPage.isInvoicesToTransfer()) {
            performAccurateTransfer();
        }
    }
    
    private void processSinglePage() {
        if (accSynPage.isInvoicesToTransfer()) {
            try {
                accurateTransfer();
            } catch (Exception e) {
                handleExceptionAndRetry();
            }
        }
    }
    
    private void handleExceptionAndRetry() {
        refreshAndNavigateToAccurateSync();
        accurateTransfer();
    }
    
    private void refreshAndNavigateToAccurateSync() {
        driver.navigate().refresh();
        homePage.backToDefaultFrame();
        homePage.clickAccountingModuleBtn()
            .clickAccurateSyncBtn()
            .getItemsCount();
    }
    
    private void performAccurateTransfer() {
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
    
    public void accurateTransfer() {
        if (accSynPage.isAllInvoiceHasCustomer()) {
            accSynPage.clickCheckAllCheckBox()
                .clickTransferInvoice()
                .takeScreenshot();
        } else {
            System.out.println("transfer customer");
            accSynPage.clickTransferCustomer()
                .clickCheckAllCheckBox()
                .clickTransferInvoice()
                .takeScreenshot();
        }
    }
    
    private void waitForSeconds(int seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}