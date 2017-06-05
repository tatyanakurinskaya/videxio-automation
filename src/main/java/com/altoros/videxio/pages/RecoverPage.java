package com.altoros.videxio.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Optional;

import com.altoros.videxio.base.BasePageObject;

public class RecoverPage extends BasePageObject<RecoverPage>{
	
	@FindBy(id="resetEmail")
	private WebElement resetEmailField;
	
	@FindBy(css="button.Button.block")
	private WebElement sendEmailButton;
	
	@FindBy(css="div#RecoverUserAccountStart div div.Card")
	private WebElement pageText;
	
	@FindBy(css="div#RecoverUserAccountStart div div.Card span a")
	private WebElement tryAgainLink;
	
	public RecoverPage(WebDriver driver) {
		super(driver);
	}
	
	public void waitForRecoverPageToLoad() {
		waitForVisibilityOf(resetEmailField);
		waitForVisibilityOf(sendEmailButton);
	}
	
	public String getEmailFieldValue() {
		waitForVisibilityOf(resetEmailField);
		String value = resetEmailField.getText();
		return value;
	}
	
	
	public void sendEmail(@Optional String value) {
		if (value == null) {
			type(resetEmailField, value);
		}
		click(sendEmailButton);
	}
	
	public String getPageText() {
		String textValue = getText(pageText);
		return textValue;
	}
	
	public void waitForResultPage() {
		waitForVisibilityOf(pageText);
		waitForVisibilityOf(tryAgainLink);
	}
}
