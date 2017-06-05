package com.altoros.videxio.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.altoros.videxio.base.BasePageObject;

public class PreflightPage  extends BasePageObject<PreflightPage>{
	
	@FindBy(css="div.controls div.clear button.Button.join-button")
	private WebElement joinNowButton;
	
	@FindBy(css="div.controls div.clear div.Button.block.PreflightButton.PreflightButton--hasCheckbox")
	private WebElement joinWithAudioOnly;
	
	@FindBy(css="div.PreflightMenu button")
	private WebElement preflightSettings;
	
	@FindBy(css="div.controls div button.Button.Icon-parent.PreflightButton.PreflightButton--back")
	private WebElement backButton;
	
	@FindBy(xpath="//div[@class='buttonTooltipBar']/div[@class='buttonBar']/button")
	private WebElement muteMicrophoneButton;
	
	@FindBy(xpath="//div[@class='buttonTooltipBar']/div[@class='buttonBar']/button[2]")
	private WebElement disableCameraButton;
	
	@FindBy(css="div.controls div button.Button.Icon-parent.PreflightButton.PreflightButton--test-speakers")
	private WebElement testSpeakersButton;

	public PreflightPage(WebDriver driver) {
		super(driver);
	}
	
	public void waitForPreflightPageToLoad() {
		waitForVisibilityOf(joinNowButton);
		waitForVisibilityOf(joinWithAudioOnly);
		waitForVisibilityOf(preflightSettings);
		waitForVisibilityOf(backButton);
		waitForVisibilityOf(testSpeakersButton);
		waitForVisibilityOf(muteMicrophoneButton);
		waitForVisibilityOf(disableCameraButton);
	}
	
	public CallPage clickJoinNow() {
		System.out.println("click joinNow starts");
		click(joinNowButton);
		System.out.println("click joinNow ends");
		return new CallPage(driver);
	}
	
}
