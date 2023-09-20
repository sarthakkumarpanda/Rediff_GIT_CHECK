package com.qa.Rediff.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountPage {
	public WebDriver driver;
	
	@FindBy(className = "rd_logout")
	private WebElement logoutLink;
	
	public AccountPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean displayStatusLogoutLink() {
		boolean status = logoutLink.isDisplayed();
		return status;
	}

}
