package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.BaseClass;

public class TC002_loginTest extends BaseClass {

    @Test(groups={"Sanity","Master"})
    public void verifyLogin() {
        logger.info("***** Starting TC_002_LoginTest *************");
        
        try {
            // Homepage
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();
            
            // Login Page
            LoginPage lp = new LoginPage(driver);
            
            // Ensure email and password fields are set separately
            lp.setEmail(p.getProperty("email"));  // Use a distinct method for email 
            lp.setPassword(p.getProperty("password"));  // Use a distinct method for password
            lp.clickLogin();
            
            // My Account Page
            MyAccountPage macc = new MyAccountPage(driver);
            boolean targetPage = macc.isMyAccountPageExists();
            
            // Assertion
            Assert.assertTrue(targetPage, "Login failed");
        } catch (Exception e) {
            logger.error("Exception occurred during login test", e);
            Assert.fail("Test case failed due to exception: " + e.getMessage());
        }
        
        logger.info("***** Finished TC_002_LoginTest *************");
    }
}
