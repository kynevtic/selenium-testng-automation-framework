package com.ui.tests;

import static com.constants.Browser.CHROME;
import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ui.dataproviders.User;
import com.ui.pages.HomePage;
import com.utility.LoggerUtility;

@Listeners({com.ui.listeners.TestListener.class})
public class LoginTest {
	
	/*
	 * Test Method Rules
	 * 1. Test Scripts should be small
	 * 2. Test Methods cannot have conditional statements, loops or try-catch [Test Scripts -----> Test Steps]
	 * 3. Reduce the use of Local Variables
	 * 5. Should have at least one Assertion
	 */
	
	HomePage homePage;
	Logger logger = LoggerUtility.getLogger(this.getClass());

	@BeforeMethod (description = "Load the home page of the website")
	public void setup() {
		logger.info("Load the home page of the website");
		homePage = new HomePage(CHROME); //Because of static import, you can directly write CHROME
	}

//	@Test (description = "verifies if the valid user is able to login to the application", groups = {"e2e", "sanity"}, 
//			dataProviderClass = com.ui.dataproviders.LoginDataProvider.class, dataProvider = "LoginTestJSONDataProvider")
//	public void loginTest(User user) {
//		assertEquals(homePage.goToLoginPage().doLoginWith(user.getEmailAddress(), user.getPassword()).getUserName()
//				, "Skill Up");
//	}
//	
//	@Test (description = "verifies if the valid user is able to login to the application", groups = {"e2e", "sanity"}, 
//			dataProviderClass = com.ui.dataproviders.LoginDataProvider.class, dataProvider = "LoginTestCSVDataProvider")
//	public void loginCSVTest(User user) {
//		assertEquals(homePage.goToLoginPage().doLoginWith(user.getEmailAddress(), user.getPassword()).getUserName()
//				, "Skill Up");
//	}
	
	@Test (description = "verifies if the valid user is able to login to the application", groups = {"e2e", "sanity"}, 
			dataProviderClass = com.ui.dataproviders.LoginDataProvider.class, dataProvider = "LoginTestExcelDataProvider",
			retryAnalyzer = com.ui.listeners.RetryAnalyzer.class)
	public void loginExcelTest(User user) {
		assertEquals(homePage.goToLoginPage().doLoginWith(user.getEmailAddress(), user.getPassword()).getUserName()
				, "Skill Up");
	}
}
 