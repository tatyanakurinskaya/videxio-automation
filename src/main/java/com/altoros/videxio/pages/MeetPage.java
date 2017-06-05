package com.altoros.videxio.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.altoros.videxio.base.BasePageObject;

public class MeetPage extends BasePageObject<MeetPage> {
	
	private static final String URL = "https://my.videxio.com/meet";

	@FindBy(css="div#MenuWrapper span.logged-out button")
	private WebElement loginMenu;
	
	@FindBy(xpath="//div[@class='ListMenuWrapper']/ul[@class='ListMenu']/li[2]/a")
	private WebElement loginLink;
	
	@FindBy(css="div#MeetIndex div.Wrapper--medium form input")
	private WebElement conferenceCodeField;
	
	@FindBy(css="div#MeetIndex div.Wrapper--medium form button")
	private WebElement joinConferenceButton;
	
	public MeetPage(WebDriver driver) {
		super(driver);
	}
	
	public void openMeetPage() {
		openPage(URL);
	}
		
	public void waitForMeetPageToLoad() {
		waitForVisibilityOf(loginMenu);
		waitForVisibilityOf(conferenceCodeField);
		waitForVisibilityOf(joinConferenceButton);
	}
	
	public LoginPage goToLoginPage() {
		click(loginMenu);
		click(loginLink);
		return new LoginPage(driver);
	}

}
