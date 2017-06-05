package com.altoros.videxio;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.altoros.videxio.base.BaseDriver;
import com.altoros.videxio.pages.LoginPage;
import com.altoros.videxio.pages.MeetPage;
import com.altoros.videxio.pages.RecoverPage;
import com.altoros.videxio.pages.RoomPage;

public class LoginPageTest extends BaseDriver {
	
	@Test(description = "Should fail to login with incorrect Email value")
	public void testLoginWithIncorrectEmailValue() {
		MeetPage meetPage = new MeetPage(driver);
		meetPage.openMeetPage();
		meetPage.waitForMeetPageToLoad();
		
		LoginPage loginPage = meetPage.goToLoginPage();
		loginPage.waitForLoginPageToLoad();
		loginPage.incorrectEmailLogin("test@test.com");
		Assert.assertEquals("Failed to log in. Try again later", loginPage.getError());
	}
	
	@Test(description = "Should fail to login with incorrect Email format")
	public void testLoginWithIncorrectEmailFormat() {
		MeetPage meetPage = new MeetPage(driver);
		meetPage.openMeetPage();
		meetPage.waitForMeetPageToLoad();
		
		LoginPage loginPage = meetPage.goToLoginPage();
		loginPage.waitForLoginPageToLoad();
		loginPage.incorrectEmailLogin("test@test2");
		Assert.assertEquals("Failed to log in. Try again later", loginPage.getError());
	}
	
	@Test(description = "Should fail to login with incorrect Password value")
	public void testLoginWithIncorrectPassword() {
		MeetPage meetPage = new MeetPage(driver);
		meetPage.openMeetPage();
		meetPage.waitForMeetPageToLoad();
		
		LoginPage loginPage = meetPage.goToLoginPage();
		loginPage.waitForLoginPageToLoad();
		loginPage.incorrectPasswordLogin("alfafa@videxio.com", "test");
		Assert.assertEquals("Wrong username or password", loginPage.getError());
	}
	
	@Test(description = "Should be able to login with correct credentials and Logout")
	public void testCorrectLogin() {
		MeetPage meetPage = new MeetPage(driver);
		meetPage.openMeetPage();
		meetPage.waitForMeetPageToLoad();
		
		LoginPage loginPage = meetPage.goToLoginPage();
		RoomPage roomPage = loginPage.correctLogin("", "");
		roomPage.waitForRoomPageToLoad();
		loginPage = roomPage.logOut();
		loginPage.waitForLoginPageToLoad();
	}
	
	@Test(description = "Should be possible to select Remember Me")
	public void testRememberMe(){
	}
	
	@Test(description = "Should fail to send email via Forgot Password with Incorrect Email address format", groups = {"broken"} )
	public void testForgotPasswordWithIncorrectEmailFormat() {
		MeetPage meetPage = new MeetPage(driver);
		meetPage.openMeetPage();
		meetPage.waitForMeetPageToLoad();
		
		LoginPage loginPage = meetPage.goToLoginPage();
		loginPage.waitForLoginPageToLoad();
		
		RecoverPage recoverPage = loginPage.clickForgotPassword("alfafa@videxio.com");
		recoverPage.waitForRecoverPageToLoad();
		
		recoverPage.sendEmail("test@test2");
		recoverPage.waitForResultPage();
		
		String recoverText1 = "A reset password link has been sent by email to the owner of the test@test2 account, please click on the link in the email to complete this process.";
		Assert.assertFalse(recoverPage.getPageText().contains(recoverText1));
		String recoverText2 = "If for some reason the email did not arrive, please make sure that you entered the correct email or username. You could also check the spam or junk folder in your e-mail client.";
		Assert.assertFalse(recoverPage.getPageText().contains(recoverText2));
	}

	@Test(description = "Should be able to send email via Forgot Password")
	public void testForgotPassword() {
		MeetPage meetPage = new MeetPage(driver);
		meetPage.openMeetPage();
		meetPage.waitForMeetPageToLoad();
		
		LoginPage loginPage = meetPage.goToLoginPage();
		loginPage.waitForLoginPageToLoad();
		
		RecoverPage recoverPage = loginPage.clickForgotPassword("alfafa@videxio.com");
		recoverPage.waitForRecoverPageToLoad();
		
		recoverPage.sendEmail("alfafa@videxio.com");
		recoverPage.waitForResultPage();

		String recoverText1 = "A reset password link has been sent by email to the owner of the alfafa@videxio.com account, please click on the link in the email to complete this process.";
		Assert.assertTrue(recoverPage.getPageText().contains(recoverText1));
		String recoverText2 = "If for some reason the email did not arrive, please make sure that you entered the correct email or username. You could also check the spam or junk folder in your e-mail client.";
		Assert.assertTrue(recoverPage.getPageText().contains(recoverText2));
	}

}
