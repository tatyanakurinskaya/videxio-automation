package com.altoros.videxio.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTestListener extends TestListenerAdapter {
	
	protected WebDriver driver;
	
	protected String osName;
	protected String extentReportFile;
	protected String extentReportImage;
	protected String testSuiteName;
	protected String testName;
	protected String testDescription;

	protected ExtentReports extent;
	protected ExtentTest extentTest;
	protected ExtentReports extent2;
	protected ExtentTest extentTest2;
		
	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.log(LogStatus.PASS, result.getMethod().getDescription());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.log(LogStatus.FAIL, result.getMethod().getDescription());
		extentTest.log(LogStatus.FAIL, result.getThrowable());
		this.driver = ((BaseDriver)result.getInstance()).driver;
		extentReportImage = getExtentReportImageName();
		getScreenshot(extentReportImage);
		extentTest.log(LogStatus.FAIL, "Error Snapshot: " + System.lineSeparator() + extentTest.addScreenCapture(extentReportImage));
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.log(LogStatus.SKIP, result.getMethod().getDescription());
		extentTest.log(LogStatus.INFO, result.getThrowable());
	}
	  
	@Override
	public void onStart(ITestContext testContext) {
		testSuiteName = testContext.getSuite().getName();
		testName = testContext.getCurrentXmlTest().getName();
		extentReportFile = getExtentReportName();
		// Create object of extent report and specify the report file path.
		extent = new ExtentReports(extentReportFile, false);
		extentTest = extent.startTest(testSuiteName + " - " + testName, "Verify functionality:");
	}

	@Override
	public void onFinish(ITestContext testContext) {
		extent.endTest(extentTest);
		// Write everything to report.
		extent.flush();
		 // Close report.
		extent.close();
	}
	
	protected void getScreenshot(String extentReportImageName) {
		try {
			File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destination = new File(extentReportImageName);
			FileUtils.copyFile(source, destination, true);
		} catch (IOException e) {
			extentTest.log(LogStatus.FAIL, "Snapshot creation is failed due to " + e.getMessage());
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
	
	public String getExtentReportName() {
		String extentReportName = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "report_" 
				+ (new SimpleDateFormat("dd-MM-yyyy")).format(new Date()) + ".html";
		return extentReportName;
	}
}
