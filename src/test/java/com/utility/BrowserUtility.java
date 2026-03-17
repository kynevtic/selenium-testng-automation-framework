package com.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.constants.Browser;

public abstract class BrowserUtility {
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	Logger logger = LoggerUtility.getLogger(this.getClass());
	
	public WebDriver getDriver() {
		return driver.get();
	}

	public BrowserUtility(WebDriver driver) {
		super();
		BrowserUtility.driver.set(driver);
	}
	
	public BrowserUtility(Browser browserName) {
		logger.info("Launching " + browserName + " browser");
		if (browserName == Browser.CHROME) {
			driver.set(new ChromeDriver());
		}
		else if (browserName == Browser.EDGE) {
			driver.set(new EdgeDriver());
		}
		else if (browserName == Browser.FIREFOX) {
			driver.set(new FirefoxDriver());
		}
		else {
			logger.error("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
			System.err.println("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
		}
	}
	
	public BrowserUtility(Browser browserName, boolean isHeadless) {
		logger.info("Launching " + browserName + " browser");
		if (browserName == Browser.CHROME) {
			if (isHeadless) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless=old");
				options.addArguments("--disable-gpu");
				options.addArguments("--window-size=1920,1080");
				driver.set(new ChromeDriver(options));
			}
			else {
				driver.set(new ChromeDriver());
			}
			
		}
		else if (browserName == Browser.EDGE) {
			if (isHeadless) {
				EdgeOptions options = new EdgeOptions();
				options.addArguments("--headless=old");
				options.addArguments("disable-gpu");
				options.addArguments("--window-size=1920,1080");
				driver.set(new EdgeDriver(options));
			}
			else {
				driver.set(new EdgeDriver());
			}
		}
		else if (browserName == Browser.FIREFOX) {
			if (isHeadless) {
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--headless=old");
				options.addArguments("--width=1920");
				options.addArguments("--height=1080");
				driver.set(new FirefoxDriver(options));
			}
			else {
				driver.set(new FirefoxDriver());
			}
		}
		else {
			logger.error("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
			System.err.println("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
		}
	}
	
	public BrowserUtility(String browserName) {
		logger.info("Launching " + browserName + " browser");
		if (browserName.equalsIgnoreCase("chrome")) {
			driver.set(new ChromeDriver());
		}
		else if (browserName.equalsIgnoreCase("edge")) {
			driver.set(new EdgeDriver());
		}
		else if (browserName.equalsIgnoreCase("firefox")) {
			driver.set(new FirefoxDriver());
		}
		else {
			logger.error("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
			System.err.println("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
		}
	}
	
	public BrowserUtility(String browserName, boolean isHeadless) {
		logger.info("Launching " + browserName + " browser");
		if (browserName.equalsIgnoreCase("chrome")) {
			if (isHeadless) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless=old");
				options.addArguments("--window-size=1920,1080");
				driver.set(new ChromeDriver(options));
			}
			else {
				driver.set(new ChromeDriver());
			}
			
		}
		else if (browserName.equalsIgnoreCase("edge")) {
			if (isHeadless) {
				EdgeOptions options = new EdgeOptions();
				options.addArguments("--headless=old");
				options.addArguments("disable-gpu");
				driver.set(new EdgeDriver(options));
			}
			else {
				driver.set(new EdgeDriver());
			}
		}
		else if (browserName.equalsIgnoreCase("firefox")) {
			if (isHeadless) {
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--headless=old");
				options.addArguments("--width=1920");
				options.addArguments("--height=1080");
				driver.set(new FirefoxDriver(options));
			}
			else {
				driver.set(new FirefoxDriver());
			}
		}
		else {
			logger.error("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
			System.err.println("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
		}
	}
	
	public void goToWebsite(String url) {
		logger.info("Visiting the website " + url);
		driver.get().get(url);
		maximizeWindow();
	}
	
	public void maximizeWindow() {
		logger.info("Maximizing the browser window");
		driver.get().manage().window().maximize();
	}
	
	public void clickOn(By locator) {
		logger.info("Finding the element with locator " + locator);
		WebElement element = driver.get().findElement(locator);
		logger.info("Element found and now performing click");
		element.click();
	}
	
	public void enterText(By locator, String textToEnter) {
		logger.info("Finding the element with locator " + locator);
		WebElement element = driver.get().findElement(locator);
		logger.info("Element found and now entering text " + textToEnter);
		element.sendKeys(textToEnter);
	}
	
	public String getVisibleText(By locator) {
		logger.info("Finding the element with locator " + locator);
		WebElement element = driver.get().findElement(locator);
		String visibleText = element.getText();
		logger.info("Element found and now returning text " + visibleText);
		return visibleText;
	}
	
	public String takeScreenshot(String name) {
		TakesScreenshot screenshot = (TakesScreenshot) driver.get();
		File screenshotData = screenshot.getScreenshotAs(OutputType.FILE);
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH-mm-ss");
		String timeStamp = format.format(date);
		String path = "./screenshots/" + name + " " + timeStamp + ".png";
		File screenShotFile = new File(path);
		try {
			FileUtils.copyFile(screenshotData, screenShotFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	}
	
	public void quit() {
		logger.info("Quitting the browser");
		driver.get().quit();
	}
}
