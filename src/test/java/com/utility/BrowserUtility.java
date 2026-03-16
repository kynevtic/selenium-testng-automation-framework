package com.utility;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.constants.Browser;

public abstract class BrowserUtility {
	
	private WebDriver driver;
	Logger logger = LoggerUtility.getLogger(this.getClass());
	
	public WebDriver getDriver() {
		return driver;
	}

	public BrowserUtility(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public BrowserUtility(Browser browserName) {
		logger.info("Launching " + browserName + " browser");
		if (browserName == Browser.CHROME) {
			driver = new ChromeDriver();
		}
		else if (browserName == Browser.EDGE) {
			driver = new EdgeDriver();
		}
		else if (browserName == Browser.FIREFOX) {
			driver = new FirefoxDriver();
		}
		else {
			logger.error("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
			System.err.println("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
		}
	}
	
	public BrowserUtility(String browserName) {
		logger.info("Launching " + browserName + " browser");
		if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		}
		else if (browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
		else {
			logger.error("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
			System.err.println("Invalid Browser Name...Please selecct [Chrome, Edge or Firefox]");
		}
	}
	
	public void goToWebsite(String url) {
		logger.info("Visiting the website " + url);
		driver.get(url);
		maximizeWindow();
	}
	
	public void maximizeWindow() {
		logger.info("Maximizing the browser window");
		driver.manage().window().maximize();
	}
	
	public void clickOn(By locator) {
		logger.info("Finding the element with locator " + locator);
		WebElement element = driver.findElement(locator);
		logger.info("Element found and now performing click");
		element.click();
	}
	
	public void enterText(By locator, String textToEnter) {
		logger.info("Finding the element with locator " + locator);
		WebElement element = driver.findElement(locator);
		logger.info("Element found and now entering text " + textToEnter);
		element.sendKeys(textToEnter);
	}
	
	public String getVisibleText(By locator) {
		logger.info("Finding the element with locator " + locator);
		WebElement element = driver.findElement(locator);
		String visibleText = element.getText();
		logger.info("Element found and now returning text " + visibleText);
		return visibleText;
	}
}
