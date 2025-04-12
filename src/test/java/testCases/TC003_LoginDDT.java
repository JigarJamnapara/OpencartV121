package testCases;

import org.testng.annotations.Test;

import junit.framework.Assert;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="Datadriven") // getting data provider from different
																				// class
	public void verify_loginDDT(String email, String pwd, String exp) throws InterruptedException {
		
		logger.info("******* starting TC_003_LoginDDT ************ ");
		
		try
		{
		// Homepage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();

		// Login Page
		LoginPage lp = new LoginPage(driver);

		// Ensure email and password fields are set separately
		lp.setEmail(email); // Use a distinct method for email
		lp.setPassword(pwd); // Use a distinct method for password
		lp.clickLogin();

		// My Account Page
		MyAccountPage macc = new MyAccountPage(driver);
		boolean targetPage = macc.isMyAccountPageExists();

		/*
		 * Data is valid - login success - test pass - logout login failed - test fail
		 * 
		 * Data is invalid - login success - test fail - logout login failed - test pass
		 */

		if (exp.equalsIgnoreCase("Valid")) {
			if (targetPage == true) {
				macc.clickLogout();
				Assert.assertTrue(true);

			} else {
				Assert.assertTrue(false);
			}
		}
		if(exp.equalsIgnoreCase("Invalid"))
		{	
			if(targetPage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
			
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		Thread.sleep(3000);
		logger.info("******* Finished TC_003_LoginDDT ************ ");
	}
}
