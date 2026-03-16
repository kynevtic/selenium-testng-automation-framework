package com.ui.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.constants.Env;
import com.utility.JSONUtility;

public class RetryAnalyzer implements IRetryAnalyzer{
	
//	private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = Integer.parseInt(PropertiesUtility.readProperty(Env.QA, "MAXIMUM_NUMBER_OF_ATTEMPTS"));
	private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = JSONUtility.readJSON(Env.QA).getMAXIMUM_NUMBER_OF_ATTEMPTS();
	private static int currentAttempt = 1;
	
	@Override
	public boolean retry(ITestResult result) {
		if (currentAttempt <= MAXIMUM_NUMBER_OF_ATTEMPTS) {
			currentAttempt++;
			return true;
		}
		return false;
	}
 
}
