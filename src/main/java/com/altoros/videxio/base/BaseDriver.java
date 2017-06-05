package com.altoros.videxio.base;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseDriver extends BaseReport {
	
	@Parameters({"browser", "extension"})
	@BeforeMethod
	protected void setUp(String browser, @Optional String extension) {
		System.out.println("You are testing on browser " + browser);
		
		osName = System.getProperty("os.name").toLowerCase();
		browser = browser.toLowerCase();
		driver = getDriver(browser, extension);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	protected void tearDown() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	protected WebDriver getDriver(String browser, @Optional String extension) {
		switch (osName) {
		case "mac os x":
			switch (browser) {
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
				driver = new FirefoxDriver();
				break;
			case "chrome":
				System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("user-data-dir=src/main/resources/allow-mic-chrome");
				if (!"noextension".equals(extension)) {
					options.addExtensions(new File("src/main/resources/My-Screen-Sharing_v1.0.2.crx"));
				}
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.popups", 1);
				prefs.put("hardware.audio_capture_enabled", 1);
				options.setExperimentalOption("prefs", prefs);
				driver = new ChromeDriver(options);
				break;
			case "safari":
				System.setProperty("webdriver.safari.driver", "src/main/resources/safari");
				driver = new SafariDriver();
				break;
			default: 
				System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
				driver = new FirefoxDriver();
				break;
			}
			break;
		default:
			switch (browser) {
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
				driver = new FirefoxDriver();
				break;
			case "chrome":
				System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
//				options.addArguments("user-data-dir=src/main/resources/allow-mic-chrome");
				if (!"noextension".equals(extension)) {
					options.addExtensions(new File("src/main/resources/My-Screen-Sharing_v1.0.2.crx"));
				}
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.popups", 1);
				options.setExperimentalOption("prefs", prefs);
				driver = new ChromeDriver(options);
				break;
			case "safari":
				System.setProperty("webdriver.safari.driver", "src/main/resources/safaridriver.exe");
				driver = new SafariDriver();
				break;
			case "edge":
				System.setProperty("webdriver.edge.driver", "src/main/resources/edgedriver.exe");
				driver = new EdgeDriver();
				break;
			case "iexplorer":
				System.setProperty("webdriver.ie.driver", "src/main/resources/iexplorerdriver.exe");
				driver = new InternetExplorerDriver();
				break;
			default:
				System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
				driver = new FirefoxDriver();
				break;
			}
			break;
		}
		return driver;
	}
}
