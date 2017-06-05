package com.altoros.videxio.base;

import java.awt.Robot;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject<T> {
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected String systemName;
	protected Robot robot;
	
	@FindBy(css="div#root div div#MenuWrapper button.IconButton.size.toolbarButton.user")
	private WebElement moreItems;
	
	@FindBy(css="li.logout a")
	
	private WebElement logout;
	
	protected BasePageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 40);
	}
	
	@SuppressWarnings("unchecked")
	protected T openPage(String url) {
		driver.get(url);
		return (T) this;
	}
	
	protected void type(WebElement element, String value) {
		waitForVisibilityOf(element);
		element.clear();
		element.sendKeys(value);
	}
	
	protected void click(WebElement element) {
		waitForVisibilityOf(element);
		waitForClickabilityOf(element);
		element.click();
	}
	
	protected void waitForClickabilityOf(WebElement element, Integer... timeout) {
		int attempts = 0;
		while (attempts < 2) {
			try {
				waitFor(ExpectedConditions.elementToBeClickable(element), (timeout.length > 0 ? timeout[0] : null));
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
	}
	
	protected void waitForVisibilityOf(WebElement element, Integer... timeout ) {
		int attempts = 0;
		while (attempts < 2) {
			try {
				waitFor(ExpectedConditions.visibilityOf(element), (timeout.length > 0 ? timeout[0] : null));
			} catch (StaleElementReferenceException e) {
				e.printStackTrace(); 
			}
			attempts++;
		}
	}
	
	private void waitFor(ExpectedCondition<WebElement> condition, Integer timeout) {
		timeout = timeout != null ? timeout : 30;
		wait = new WebDriverWait(driver, 40);
		wait.until(condition);
	}
	
	protected String getText(WebElement element) {
		waitForVisibilityOf(element);
		return element.getText();
	}
	
	protected String getBrowserName() {
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
		return browserName;
	}
	
	protected String getOSName() {
		systemName = System.getProperty("os.name").toLowerCase();
		return systemName;
	}
}