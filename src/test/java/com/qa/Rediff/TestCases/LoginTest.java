package com.qa.Rediff.TestCases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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
		loginpage.enterUserName(email);
		loginpage.enterPassword(password);
		loginpage.clickOnLoginButton();
		accountpage = new AccountPage(driver);
		Assert.assertTrue(accountpage.displayStatusLogoutLink());
	}
	
	@Test(priority=2)
	public void verifyLoginWithValidUsernameInvalidPassword() {
		driver.findElement(By.id("login1")).sendKeys(prop.getProperty("validUsername"));
		driver.findElement(By.id("password")).sendKeys(dataprop.getProperty("invalidPassword"));
		driver.findElement(By.name("proceed")).click();
		String actualWarningMessage = driver.findElement(By.xpath("//b[text() = 'Wrong username and password combination.']")).getText();
		String expectedWarningMessage = dataprop.getProperty("wrongUsernamePasswordComboWarningMessage");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage));
	}
	
	
	@Test(priority=3)
	public void verifyLoginWithInvalidUsernameValidPassword() {
		driver.findElement(By.id("login1")).sendKeys(Utils.emailWithDateTimeStamp());
		driver.findElement(By.id("password")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.name("proceed")).click();
		String actualWarningMessage = driver.findElement(By.xpath("//b[text() = 'Temporary Issue. Please try again later. [#5002]']")).getText();
		String expectedWarningMessage = dataprop.getProperty("tempIssueWarningMessage");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage));
	}
	
	@Test(priority=4)
	public void verifyLoginWithInvalidCredentials() {
		driver.findElement(By.id("login1")).sendKeys(Utils.emailWithDateTimeStamp());
		driver.findElement(By.id("password")).sendKeys(dataprop.getProperty("invalidPassword"));
		driver.findElement(By.name("proceed")).click();
		String actualWarningMessage = driver.findElement(By.xpath("//b[text() = 'Temporary Issue. Please try again later. [#5002]']")).getText();
		String expectedWarningMessage = dataprop.getProperty("tempIssueWarningMessage");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage));
	}
	
	@Test(priority=5)
	public void verifyLoginWithNoCredentials() {
		driver.findElement(By.name("proceed")).click();
		Alert alert = driver.switchTo().alert();
		String actualAlertMessage = alert.getText();
		String expectedAlertMessage = dataprop.getProperty("alertMessage");
		Assert.assertTrue(actualAlertMessage.equals(expectedAlertMessage));;
		alert.accept();
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
