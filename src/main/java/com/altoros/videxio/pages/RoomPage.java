package com.altoros.videxio.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.altoros.videxio.base.BasePageObject;

public class RoomPage extends BasePageObject<RoomPage>{
	
	@FindBy(id="launch")
	private WebElement joinRoom;
	
	@FindBy(id="urlClipboard")
	private WebElement copyButton;
	
	@FindBy(css="div#MenuWrapper span div div.info span a")
	private WebElement advancedRoomInfoLink;
	
	@FindBy(css="span#ToolBar button.Button")
	private WebElement lockButton;

	@FindBy(css="span#ToolBar button.IconButton")
	private WebElement roomListButton;
	
	@FindBy(css="div.participants-list button")
	private WebElement participantsListButton;
	
	@FindBy(css="div#root div div#MenuWrapper button.IconButton.size.toolbarButton.user")
	private WebElement moreItems;
	
	@FindBy(css="div#launch button")
	private WebElement joinThroughBrowserButton;
	
	@FindBy(css="li.logout a")
	private WebElement logout;
	
	public RoomPage(WebDriver driver) {
		super(driver);
	}
	
	public void waitForRoomPageToLoad() {
		waitForVisibilityOf(joinRoom);
		waitForVisibilityOf(copyButton);
		waitForVisibilityOf(advancedRoomInfoLink);
		waitForVisibilityOf(participantsListButton);
		waitForVisibilityOf(roomListButton);
		waitForVisibilityOf(lockButton);
		waitForVisibilityOf(joinThroughBrowserButton);
	}
	
	public PreflightPage clickJoinThroughBrowserButton() {
		click(joinThroughBrowserButton);
		// It clicks on Allow microphone button
		systemName = getOSName();
		switch (systemName) {
			case "mac os x": {
				break;
			}
			default: {
				try {
					System.out.println("click allow starts");
					robot = new Robot();
					robot.setAutoDelay(2000);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					driver.switchTo().defaultContent();
					System.out.println("click allow ends");
				} catch (AWTException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return new PreflightPage(driver);
	}
	
	public LoginPage logOut() {
		click(moreItems);
		click(logout);
		return new LoginPage(driver);
	}
	
}
