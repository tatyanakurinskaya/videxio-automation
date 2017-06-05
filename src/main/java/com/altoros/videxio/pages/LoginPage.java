package com.altoros.videxio.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.altoros.videxio.base.BasePageObject;

public class LoginPage extends BasePageObject<LoginPage> {

	@FindBy(id="username")
	private WebElement email;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(id="rememberMe")
	private WebElement rememberMe;
	
	@FindBy(css="p.forgot-password-link-wrapper a")
	private WebElement forgotPassword;
	
	@FindBy(css="button.Button")
	private WebElement submitButton;
	
	@FindBy(css="div.MessageBox")
	private WebElement errorMessage;
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public void waitForLoginPageToLoad() {
		waitForVisibilityOf(email);
		waitForVisibilityOf(submitButton, 10);
	}
	
	public void submitRememberMe() {
		click(rememberMe);
	}
	
	public void forgotPassword() {
		click(forgotPassword);
	}
	
	public String getError() {
		return getText(errorMessage);
	}
	
	public LoginPage incorrectEmailLogin(String value) {
		type(email, value);
		click(submitButton);
		return new LoginPage(driver);
	}
	
	public LoginPage incorrectPasswordLogin(String emailvalue, String passwordvalue) {
		type(email, emailvalue);
		click(submitButton);
		type(password, passwordvalue);
		click(submitButton);
		return new LoginPage(driver);
	}
	
	public RoomPage correctLogin(String emailvalue, String passwordvalue) {
		type(email, emailvalue);
		click(submitButton);
		type(password, passwordvalue);
		click(submitButton);
		return new RoomPage(driver);
	}
	
	public RecoverPage clickForgotPassword(String value) {
		type(email, value);
		click(submitButton);
		click(forgotPassword);
		return new RecoverPage(driver);
	}

}
