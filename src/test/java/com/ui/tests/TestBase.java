package com.ui.tests;

import static com.constants.Browser.CHROME;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;

import com.ui.pages.HomePage;
import com.utility.BrowserUtility;
import com.utility.LoggerUtility;

public class TestBase {
	
	protected HomePage homePage;
	Logger logger = LoggerUtility.getLogger(this.getClass());
	
	@BeforeMethod (description = "Load the home page of the website")
	public void setup() {
		logger.info("Load the home page of the website");
		homePage = new HomePage(CHROME); //Because of static import, you can directly write CHROME
	}
	
	public BrowserUtility getInstance() {
		return homePage;
	}
}
