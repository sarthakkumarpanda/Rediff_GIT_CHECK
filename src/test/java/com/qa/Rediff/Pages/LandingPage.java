package com.qa.Rediff.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {
	
	public WebDriver driver;
	
	
	@FindBy(className = "signin")
	private WebElement signinLink;
	
	
	public LandingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public LoginPage clickOnSignInLink() {
		signinLink.click();
		return new LoginPage(driver);
		
	}

}
