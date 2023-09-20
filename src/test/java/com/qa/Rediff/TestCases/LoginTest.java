package com.qa.Rediff.TestCases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.Rediff.Pages.AccountPage;
import com.qa.Rediff.Pages.LandingPage;
import com.qa.Rediff.Pages.LoginPage;
import com.qa.Rediff.TestBase.TestBase;
import com.qa.Rediff.Utilities.ExcelCode;
import com.qa.Rediff.Utilities.Utils;

public class LoginTest extends TestBase{

	public LoginTest() throws Exception {
		super();
	}

	public WebDriver driver;
	public LoginPage loginpage;
	public LandingPage landingpage;
	public AccountPage accountpage;


	@BeforeMethod
	public void loginSetup() {
		driver = initializeBrowserAndOpenApplication(prop.getProperty("browser"));
		landingpage = new LandingPage(driver);
		loginpage = landingpage.clickOnSignInLink();
	}
	
	@Test(priority=1, dataProvider = "Rediff", dataProviderClass = ExcelCode.class)
	public void verifyLoginWithValidCredentials(String email, String password) {
		accountpage = loginpage.navigateToAccountPage(email, password);
		Assert.assertTrue(accountpage.displayStatusLogoutLink());
	}
	
	@Test(priority=2)
	public void verifyLoginWithValidUsernameInvalidPassword() {
		loginpage.navigateToAccountPage(prop.getProperty("validUsername"), dataprop.getProperty("invalidPassword"));
		Assert.assertTrue(loginpage.retrieveErrorUserNamePasswordMessage().contains(dataprop.getProperty("wrongUsernamePasswordComboWarningMessage")));
	}
	
	
	@Test(priority=3)
	public void verifyLoginWithInvalidUsernameValidPassword() {
		loginpage.navigateToAccountPage(Utils.emailWithDateTimeStamp(), prop.getProperty("validPassword"));
		Assert.assertTrue(loginpage.retrieveErrorMessageAmbiguous().contains(dataprop.getProperty("tempIssueWarningMessage")));
	}
	
	@Test(priority=4)
	public void verifyLoginWithInvalidCredentials() {
		loginpage.navigateToAccountPage(Utils.emailWithDateTimeStamp(), dataprop.getProperty("invalidPassword"));
		Assert.assertTrue(loginpage.retrieveErrorMessageAmbiguous().contains(dataprop.getProperty("tempIssueWarningMessage")));
	}
	
	@Test(priority=5)
	public void verifyLoginWithNoCredentials() {
		loginpage.clickOnLoginButton();
		Alert alert = driver.switchTo().alert();
		Assert.assertTrue(alert.getText().equals(dataprop.getProperty("alertMessage")));
		alert.accept();
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
