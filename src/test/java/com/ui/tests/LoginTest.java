package com.ui.tests;

import static com.constants.Browser.CHROME;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ui.pages.HomePage;

public class LoginTest {
	
	/*
	 * Test Method Rules
	 * 1. Test Scripts should be small
	 * 2. Test Methods cannot have conditional statements, loops or try-catch [Test Scripts -----> Test Steps]
	 * 3. Reduce the use of Local Variables
	 * 5. Should have at least one Assertion
	 */
	
	HomePage homePage;

	@BeforeMethod (description = "Load the home page of the website")
	public void setup() {
		homePage = new HomePage(CHROME); //Because of static import, you can directly write CHROME
	}

	@Test (description = "verifies if the valid user is able to login to the application", groups = {"e2e", "sanity"})
	public void loginTest() {
		assertEquals(homePage.goToLoginPage().doLoginWith("wayopew108@flosek.com", "password").getUserName()
				, "Skill Up");
	}
}
 