package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import pageObject.AccountRegistrationPage;
import pageObject.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups={"Regression","Master"})
	public void verify_account_registration() throws InterruptedException {
		logger.info("*************  Starting TC001_AccountRegistrationTest ************");
		try {
			HomePage hp = new HomePage(getDriver());
			hp.clickMyAccount();

			logger.info("Clicked on MyAccount Link");
			hp.clickRegister();
			logger.info("Clicked on Register Link");

			AccountRegistrationPage regpage = new AccountRegistrationPage(getDriver());

			logger.info("Provideing Customer Details");
			Thread.sleep(2000);
			regpage.setFirstName(randomeString().toUpperCase());

			Thread.sleep(2000);
			regpage.setLastName(randomeString().toUpperCase());

			Thread.sleep(2000);
			regpage.setEmail(randomeString() + "@gmail.com"); // randomly generated the

			Thread.sleep(2000);
			regpage.setTelephone(randomeNumber());

			Thread.sleep(2000);
			String password = randomeAlphaNumeric();

			Thread.sleep(2000);
			regpage.setPassword(password);

			Thread.sleep(2000);
			regpage.setConfirmPassword(password);

			Thread.sleep(2000);
			regpage.setPrivacyPolicy();

			Thread.sleep(2000);
			regpage.clickContinue();

			Thread.sleep(2000);
			logger.info("Validation excepted message");
			String confmsg = regpage.getConfirmationMsg();

			Assert.assertEquals(confmsg, "Your Account Has Been Created!");

		} catch (Exception e) {
			logger.error("Test failed...");
			logger.debug("Debug logs.... ");
			Assert.fail();
		}
		logger.info("***** Finished TC001_AccountRegistrationTest ******");
	}
}
