package com.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ui.dataproviders.User;

public class ExcelReaderUtility {
	public static Iterator<User> readExcelFile(String fileName) {
		File file = new File(System.getProperty("user.dir") + "/test-data/" + fileName);
		XSSFWorkbook workbook = null;
		XSSFSheet sheet;
		List<User> userData = null;
		Iterator<Row> rowIterator;
		Row row;
		Cell emailAddressCell;
		Cell passwordCell;
		User user;
		
		try {
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet("LoginData");
			
			userData = new ArrayList<User>();
			rowIterator = sheet.iterator();
			
			rowIterator.next();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				emailAddressCell = row.getCell(0);
				passwordCell = row.getCell(1);
				user = new User(emailAddressCell.toString(), passwordCell.toString());
				userData.add(user);
			}
			
			workbook.close();
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		} 
		
		return userData.iterator();
	}
}
