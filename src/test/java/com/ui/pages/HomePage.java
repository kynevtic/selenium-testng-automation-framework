package com.ui.pages;

import static com.constants.Env.QA;
import static com.utility.PropertiesUtility.readProperty;

import org.openqa.selenium.By;

import com.constants.Browser;
import com.utility.BrowserUtility;

public final class HomePage extends BrowserUtility {
	
	private static final By SIGN_IN_LINK_LOCATOR = By.xpath("//a[contains(text(),'Sign in')]");
	
	public HomePage(Browser browserName) {
		super(browserName);
		goToWebsite(readProperty(QA, "URL"));
	}

	public LoginPage goToLoginPage() { // Page functions -----> Cannot return void
		clickOn(SIGN_IN_LINK_LOCATOR);
		return new LoginPage(getDriver());
	}
}
