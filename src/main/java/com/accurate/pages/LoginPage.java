package com.accurate.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id = "txtUserName")
	protected WebElement userNameInputField;

	@FindBy(id = "txtPassword")
	protected WebElement passwordInputField;
	
	@FindBy(id = "btnLogin")
	protected WebElement signInButton;
	
	public LoginPage setUserName(String userName) {
		clearAndType(userNameInputField, userName);
		return this;
	}
	
	public LoginPage setPassword(String password) {
		clearAndType(passwordInputField, password);
		return this;
	}
	
	public HomePage clickSignInBtn() {
		clickElement(signInButton);
		return new HomePage(driver);
	}
}
