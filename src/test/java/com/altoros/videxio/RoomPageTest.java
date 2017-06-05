package com.altoros.videxio;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.altoros.videxio.base.BaseDriver;
import com.altoros.videxio.pages.CallPage;
import com.altoros.videxio.pages.LoginPage;
import com.altoros.videxio.pages.MeetPage;
import com.altoros.videxio.pages.PreflightPage;
import com.altoros.videxio.pages.RoomPage;

public class RoomPageTest extends BaseDriver {
	
	@Test(description = "Should be asked for install browser extension when sharing presentation", groups = {"noextension", "broken	"})
	public void testAskingForBrowserExtensionInstallation() {
		MeetPage meetPage = new MeetPage(driver);
		meetPage.openMeetPage();
		meetPage.waitForMeetPageToLoad();
		
		LoginPage loginPage = meetPage.goToLoginPage();
		RoomPage roomPage = loginPage.correctLogin("alfafa@videxio.com", "");
		roomPage.waitForRoomPageToLoad();
		
		PreflightPage preflightPage = roomPage.clickJoinThroughBrowserButton();
		preflightPage.waitForPreflightPageToLoad();

		CallPage callPage = preflightPage.clickJoinNow();
		callPage.waitForCallPageToLoad();

		callPage.startPresentationAndShare();
		Assert.assertTrue(callPage.isInstallBrowserExtensionDisplayed());

		callPage.closeInstallExtensionModal();
		roomPage = callPage.disconnectFromCall();
		roomPage.waitForRoomPageToLoad();
		
		loginPage = roomPage.logOut();
		loginPage.waitForLoginPageToLoad();
	}
	
	@Test(description = "Should be able to share presentation", groups = {"extension"})
	public void testSharingPresentation() {
		MeetPage meetPage = new MeetPage(driver);
		meetPage.openMeetPage();
		meetPage.waitForMeetPageToLoad();
		
		LoginPage loginPage = meetPage.goToLoginPage();
		RoomPage roomPage = loginPage.correctLogin("alfafa@videxio.com", "");
		roomPage.waitForRoomPageToLoad();
		
		PreflightPage preflightPage = roomPage.clickJoinThroughBrowserButton();
		preflightPage.waitForPreflightPageToLoad();
		
		CallPage callPage = preflightPage.clickJoinNow();
		callPage.waitForCallPageToLoad();

		callPage.startPresentationAndShare();

		captureScreenshot("Capture Screen Sharing...");
		
		callPage.stopPresentation();
		roomPage = callPage.disconnectFromCall();
		roomPage.waitForRoomPageToLoad();
		
		loginPage = roomPage.logOut();
		loginPage.waitForLoginPageToLoad();
	}
}
