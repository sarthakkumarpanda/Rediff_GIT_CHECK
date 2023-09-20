package com.qa.Rediff.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	public WebDriver driver;
	
	@FindBy(id = "login1")
	private WebElement usernameTextBox;
	
	@FindBy(id = "password")
	private WebElement passwordTextBox;
	
	@FindBy(name = "proceed")
	private WebElement loginButton;
	
	@FindBy(xpath = "//b[text() = 'Wrong username and password combination.']")
	private WebElement errorMessageUsernamePassword;
	
	@FindBy(xpath = "//b[text() = 'Temporary Issue. Please try again later. [#5002]']")
	private WebElement ambiguousErrorMessage;
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void enterUserName(String usernameText) {
		usernameTextBox.sendKeys(usernameText);
	}
	
	public void enterPassword(String passwordText) {
		passwordTextBox.sendKeys(passwordText);
	}
	
	public AccountPage clickOnLoginButton() {
		loginButton.click();
		return new AccountPage(driver);
	}
	
	public AccountPage navigateToAccountPage(String usernameText , String passwordText ) {
		usernameTextBox.sendKeys(usernameText);
		passwordTextBox.sendKeys(passwordText);
		loginButton.click();
		return new AccountPage(driver);
		
	}
	
	
	public String retrieveErrorUserNamePasswordMessage() {
		String message = errorMessageUsernamePassword.getText();
		return message;
	}
	
	public String retrieveErrorMessageAmbiguous() {
		String message = ambiguousErrorMessage.getText();
		return message;
	}
	

}
