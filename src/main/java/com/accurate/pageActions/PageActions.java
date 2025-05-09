package com.accurate.pageActions;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.accurate.automationBase.BaseClass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;


public class PageActions extends BaseClass {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public WebDriverWait wait;
	public static Actions action;
	private String text = "";
	private boolean status;

	
	/**
	 * 
	 * @param element
	 */
	protected void clickElement(WebElement element) {
		try {
			element.click();
		} catch (ElementClickInterceptedException ex) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
//			ex.printStackTrace();
		}catch(ElementNotInteractableException ex) {
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		}catch (NoSuchElementException ex) {
			logger.info("WebElement could not be able to find in the webpage");
			ex.printStackTrace();
			throw new RuntimeException();

		}
	}

	/**
	 * @param element
	 * 
	 */
	public void switchToFrame(WebElement element) {
		try {
			driver.switchTo().frame(element);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}

	}

	public void switchToFrameByIndex(int index) {
		try {
//			List<WebElement> f = driver.findElements(By.tagName("iframe"));
//		    System.out.println("Total number " + f.size());
			driver.switchTo().frame(index);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void switchToParentFrame() {
		try {
			driver.switchTo().parentFrame();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}
	}

	protected void waitAndClick(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}
	}

	protected void visibleAndClick(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));
			element.click();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}
	}

	protected void waitAndJsClick(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	protected void scrollAndJsClick(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	protected void actionClick(WebElement ele) {

		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			action = new Actions(driver);
			setText(ele.getText());
			action.moveToElement(ele).click().build().perform();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	protected void moveAndClick(WebElement parentEle, WebElement childEle) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(parentEle));
			action = new Actions(driver);
			action.moveToElement(parentEle).perform();
			action.click(childEle).perform();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 
	 * @param element
	 * @param value
	 */
	protected void clearAndType(WebElement element, String value) {
		try {
			element.clear();
			element.sendKeys(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void jsSendkey(WebElement ele, String value) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('value', '" + value + "')", ele);
		} catch (NoSuchElementException ex) {
			Assert.fail();
			ex.printStackTrace();
		}
	}

	protected void clearAndType(WebElement element, int value) {
		try {
			element.clear();
			element.sendKeys(Integer.toString(value));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void scrollAndType(WebElement element, String value) {
		try {
			action = new Actions(driver);
			action.moveToElement(element).build().perform();
			element.clear();
			element.sendKeys(value);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}

	}

	protected boolean isDisplayedInUI(WebElement element) {
		return element.isDisplayed();
	}

	protected boolean validationIsDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException ex) {
			return false;
		}
	}

	protected boolean isSelected(WebElement element) {
		status = element.isSelected();
		return status;
	}

	protected void findAndClick(List<WebElement> elements, String value) {
		String text;
		for (WebElement element : elements) {
			text = element.getText();
			if (text.equals(value)) {
				element.click();
//				logger.info(value + " is clicked");
				break;
			}
		}
	}

	protected void selectByValue(WebElement element, String value) {
		Select dropdown = new Select(element);
		dropdown.selectByValue(value);
	}

	protected void selectByIndex(WebElement element, int index) {
		Select dropdown = new Select(element);
		dropdown.selectByIndex(index);
	}

	protected String getTextOfElement(WebElement element) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.getText();
	}

	protected void scrollByaction(WebElement element) {
		action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	protected void scrollToElebyJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	protected void sendKeysUseKeyboard(WebElement element, String value) {
		element.sendKeys(value);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}