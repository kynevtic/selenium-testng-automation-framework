package com.ui.dataproviders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.google.gson.Gson;
import com.utility.CSVReaderUtility;
import com.utility.ExcelReaderUtility;

public class LoginDataProvider {
	
	@DataProvider(name = "LoginTestJSONDataProvider")
	public Iterator<Object[]> loginJSONDataProvider() {
		Gson gson = new Gson();
		File file = new File(System.getProperty("user.dir") + "/test-data/logindata.json");
		FileReader fileReader = null;
		
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		TestData data = gson.fromJson(fileReader, TestData.class); // De-serialization
		
		List<Object[]> dataToReturn = new ArrayList<Object[]>();
		for (User user:data.getData()) {
			dataToReturn.add(new Object[] {user});
		}
		
		return dataToReturn.iterator();
	}
	
	@DataProvider(name = "LoginTestCSVDataProvider")
	public Iterator<User> loginCSVDataProvider() {
		return CSVReaderUtility.readCSVFile("logindata.csv"); // Changed File Name
	}
	
	@DataProvider(name = "LoginTestExcelDataProvider")
	public Iterator<User> loginExcelDataProvider() {
		return ExcelReaderUtility.readExcelFile("logindata.xlsx"); // Changed File Name
	}
}