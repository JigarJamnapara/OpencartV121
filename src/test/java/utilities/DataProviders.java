package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	// DataProvider for Login Data
	@DataProvider(name = "LoginData")

	public String[][] getData() throws IOException {
		String path = ".\\testData\\Opencart_LoginData.xlsx"; // taking xl file from testData

		ExcelUtility xlutil = new ExcelUtility(path); // creating an object for XLUtility

		int totalRows = xlutil.getRowCount("Sheet1"); // Row count is zero-based
		int totalCols = xlutil.getRowCount("Sheet1", 1);

		if (totalRows <= 0 || totalCols <= 0) {
			throw new IOException("Excel sheet is empty or incorrect row/column count.");
		}

		String logindata[][] = new String[totalRows][totalCols]; // created for two dimension array which can store

		for (int i = 1; i <= totalRows; i++) // 1 //read the data from xl storing in two deminsional array
		{
			for (int j = 0; j < totalCols; j++) // 0 i is rows j is col
			{
				logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j); // 1,0
			}
		}

		return logindata; // returning two dimension array
	}

}
