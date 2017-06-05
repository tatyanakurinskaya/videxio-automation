package com.altoros.videxio.base;
 
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
 
 public class BaseReport {
 	
 	protected WebDriver driver;
 	
 	protected String osName;
 	protected String extentReportFile;
 	protected String extentReportImage;
 	protected String testDescription;
 	protected String testSuiteName;
 	protected String testName;
 
 	protected ExtentReports extent;
 	protected ExtentTest extentTest;
 	
 	@BeforeClass
 	public void startReport(ITestContext testContext) {
 		testSuiteName = testContext.getSuite().getName();
 		testName = testContext.getCurrentXmlTest().getName();
 		extentReportFile = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "report_" 
				+ (new SimpleDateFormat("dd-MM-yyyy")).format(new Date()) + ".html";
 		// Create object of extent report and specify the report file path.
 		extent = new ExtentReports(extentReportFile, false);
 		extentTest = extent.startTest(testSuiteName + "- " + testName, "Verify functionality: ");
 	}
 	
 	@AfterClass
 	public void endReport() {
 		extent.endTest(extentTest);
 		// Write everything to report.
 		extent.flush();
 		// Close report.
 		extent.close();
 	}
 	
 	@BeforeMethod
 	public void testResult(Method method) {
 		Test test = method.getAnnotation(Test.class);
 		testDescription = test.description();
 	}
 	
 	@AfterMethod
	public void getResult(ITestResult result) {
 		switch (result.getStatus()) {
 		case ITestResult.FAILURE:
 			extentReportImage = getExtentReportImageName();
	        getScreenshot(extentReportImage);
	        extentTest.log(LogStatus.FAIL, testDescription);
	     	extentTest.log(LogStatus.FAIL, result.getThrowable());
	     	extentTest.log(LogStatus.FAIL, "Error Snapshot: " + extentTest.addScreenCapture(extentReportImage));
 			break;
 		case ITestResult.SUCCESS:
 			extentTest.log(LogStatus.PASS, testDescription);
 			break;
 		case ITestResult.SKIP:
 			extentTest.log(LogStatus.SKIP, "SKIPPED: " + testDescription);
 			break;
 		default:
 			break;
 		}
	}
 	
 	protected void getScreenshot(String extentReportImageName) {
 		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
 		try {
			FileUtils.copyFile(scrFile, new File(extentReportImageName), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
 	}
 	
 	// Get name of the image to save
 	public String getExtentReportImageName() {
 		extentReportImage = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "failure_screenshots" 
				+ File.separator + (new SimpleDateFormat("dd-MM-yyyy")).format(new Date()) 
				+ File.separator + "image_" + (new SimpleDateFormat("dd-MM-yyyy HH-mm-ss")).format(new Date()) + ".png";
 		return extentReportImage;
 	}
 	
 	protected String getBrowserName() {
 		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
 		String browserName = caps.getBrowserName();
 		return browserName;
 	}
 	
	public void captureScreenshot(String title) {
		extentReportImage = getExtentReportImageName();
		getScreenshot(extentReportImage);
		extentTest.log(LogStatus.INFO, title + System.lineSeparator() + extentTest.addScreenCapture(extentReportImage));
	}
 }