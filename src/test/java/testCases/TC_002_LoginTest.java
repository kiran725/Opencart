package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC_002_LoginTest extends BaseClass {

	@Test(groups = { "Sanity", "Master" })
	public void verify_login() throws InterruptedException {
		logger.info("***** Starting TC002_LoginTest  ****");
		logger.debug("This is a debug log message");

		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on MyAccount Link.. ");

			hp.loginclick();
			logger.info("Clicked on Login Link.. ");

			LoginPage loginpage = new LoginPage(driver);
			loginpage.setEmail(p.getProperty("email"));
			loginpage.setPassword(p.getProperty("password"));
			loginpage.clickLogin();
			logger.info("Login successful.. ");

			MyAccountPage myaccpage = new MyAccountPage(driver);
			boolean result = myaccpage.isMyAccountPageExists();

			Assert.assertTrue(result);
		} catch (Exception e) {
			Assert.fail();
		}

		logger.info("***Finished TC002_LoginTest***");

	}
}

