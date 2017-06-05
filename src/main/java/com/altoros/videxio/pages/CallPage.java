package com.altoros.videxio.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.altoros.videxio.base.BasePageObject;

public class CallPage extends BasePageObject<CallPage>{
	
	// Top menu
	@FindBy(css="div.Call div.SecondaryButtonBar div.topLeft button")
	private WebElement startPresentation;

	@FindBy(xpath="//div[@id='Call']/div[@class='SecondaryButtonBar']/div/button[@class='RoundButton active']")
	private WebElement stopPresentation;
	
	@FindBy(xpath="//div[@id='Call']/div[@class='SecondaryButtonBar']/div/button[2]")
	private WebElement showDTMFButton;
	
	@FindBy(xpath="//div[@id='Call']/div[@class='SecondaryButtonBar']/div/button[2]")
	private WebElement hideDTMFButton;
	
	@FindBy(xpath="//div[@id='Call']/div[@class='SecondaryButtonBar']/div/button[3]")
	private WebElement enterFullscreenButton;
	
	@FindBy(xpath="//div[@id='Call']/div[@class='SecondaryButtonBar']/div/button[3]")
	private WebElement disableFullscreenButton;
	
	@FindBy(css="div#Call div.SecondaryButtonBar div.topRight button.Button")
	private WebElement lockButton;
	
	@FindBy(css="div#Call div.SecondaryButtonBar div.topRight button.Button")
	private WebElement unlockButton;
	
	@FindBy(css="div#Call div.SecondaryButtonBar div.topRight button.RoundButton")
	private WebElement inviteButton;
	
	@FindBy(css="div#Call div.SecondaryButtonBar div.topRight button.RoundButton.manageRoom")
	private WebElement showParticipantsButton;
	
	@FindBy(css="div#Call div.SecondaryButtonBar div.topRight button.RoundButton.active.manageRoom")
	private WebElement hideParticipantsButton;

	// Bottom menu
	@FindBy(css="button.RoundButton.red")
	private WebElement disconnectFromCall;
		
	// Common elements
	@FindBy(css="div.Overlay div.Overlay__content div button")
	private WebElement installBrowserExtensionButton;
	
	@FindBy(css="div.Overlay div.Overlay__content a.close")
	private WebElement closeInstallExtensionButton;

	public CallPage(WebDriver driver) {
		super(driver);
	}
	
	public void waitForCallPageToLoad() {
		waitForVisibilityOf(startPresentation);
		waitForVisibilityOf(showDTMFButton);
		waitForVisibilityOf(enterFullscreenButton);
		waitForVisibilityOf(lockButton);
		waitForVisibilityOf(inviteButton);
		waitForVisibilityOf(showParticipantsButton);
		waitForVisibilityOf(disconnectFromCall);
	}

	public RoomPage disconnectFromCall() {
		systemName = getOSName();
		switch (systemName) {
		case "mac os x":
			break;
		default:
			// Move mouse to show buttons
			try {
				robot = new Robot();
				robot.setAutoDelay(1000);
				robot.mouseWheel(10);
				driver.switchTo().defaultContent();
			} catch (AWTException e) { e.printStackTrace(); }
			break;
		}
		click(disconnectFromCall);
		System.out.println("disconnect from call");
		return new RoomPage(driver);
	}
	
	public void startPresentationAndShare() {
		click(startPresentation);
		try {
			Thread.sleep(1000);
			System.out.println("sleep after starting");
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		}
		if (isInstallBrowserExtensionDisplayed()) {
			return;
		}
		systemName = getOSName();
		System.out.println("presentation starts");
		switch (systemName) {
		case "mac os x":
			break;
		default:
			switch (getBrowserName()) {
			case "chrome":
				try {
					robot = new Robot();
					robot.setAutoDelay(3000);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					robot.setAutoDelay(500);
					robot.mouseWheel(5);
					driver.switchTo().defaultContent();
				} catch (AWTException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
			break;
		}
		System.out.println("presentation ends");
	}
	
	public void stopPresentation() {
		systemName = getOSName();
		switch (systemName) {
		case "mac os x":
			break;
		default:
			try {
				// Move mouse to show buttons
				robot = new Robot();
				robot.delay(2000);
				robot.mouseMove(500, 500);
				robot.mouseWheel(5);
				driver.switchTo().defaultContent();
				System.out.print(stopPresentation.isDisplayed());
				click(stopPresentation);
			} catch (AWTException e) {
				e.printStackTrace(); 
			}
			System.out.println("stop presentation");
			break;
		}
	}
	
	public void closeInstallExtensionModal(){
		click(closeInstallExtensionButton);
	}
	
	public Boolean isInstallBrowserExtensionDisplayed() {
		try {
			installBrowserExtensionButton.isDisplayed();
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

}
