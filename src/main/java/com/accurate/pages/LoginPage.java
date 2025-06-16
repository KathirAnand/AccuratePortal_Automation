package com.accurate.pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accurate.utilities.ExtentReportUtility;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public static boolean loginAlert=false;
	
	@FindBy(id = "txtUserName")
	protected WebElement userNameInputField;

	@FindBy(id = "txtPassword")
	protected WebElement passwordInputField;
	
	@FindBy(id = "btnLogin")
	protected WebElement signInButton;
	
	String invalidPasswordMessage="Invalid Password! Failed attempt count 1 out of 5";
	
	public LoginPage setUserName(String userName) {
		clearAndType(userNameInputField, userName);
		return this;
	}
	
	public LoginPage setPassword(String password) {
		clearAndType(passwordInputField, password);
		return this;
	}
	
	public LoginPage clickSignInBtn() {
		clickElement(signInButton);
		return this;
	}
	
	public HomePage validateCredentials() {
		if(isAlertPresent()) {
			Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if(alertText.equalsIgnoreCase(invalidPasswordMessage)) {
            	ExtentReportUtility.info("Alert displayed with text: " + alertText);
            	logger.info("Password mismatch");
            }
            alert.accept();
            ExtentReportUtility.fail(alertText);
		}
		return new HomePage(driver);
	}
	
	public boolean isAlertPresent() {
        try {
        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.alertIsPresent());
            loginAlert=true;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
