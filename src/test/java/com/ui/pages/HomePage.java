package com.ui.pages;

import static com.constants.Env.QA;
import static com.utility.JSONUtility.readJSON;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.constants.Browser;
import com.utility.BrowserUtility;
import com.utility.LoggerUtility;

public final class HomePage extends BrowserUtility {
	
	Logger logger = LoggerUtility.getLogger(this.getClass());
	private static final By SIGN_IN_LINK_LOCATOR = By.xpath("//a[contains(text(),'Sign in')]");
	
	public HomePage(Browser browserName) {
		super(browserName);
//		goToWebsite(readProperty(QA, "URL"));
		goToWebsite(readJSON(QA).getUrl());
	}

	public LoginPage goToLoginPage() { // Page functions -----> Cannot return void
		logger.info("Trying to perform click to go to sign in page");
		clickOn(SIGN_IN_LINK_LOCATOR);
		return new LoginPage(getDriver());
	}
}
