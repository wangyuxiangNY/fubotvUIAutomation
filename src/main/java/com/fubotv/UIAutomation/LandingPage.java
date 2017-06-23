package com.fubotv.UIAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPage extends Page {
	

	 @FindBy(css=".menus") private WebElement menus;
	 @FindBy(css=".list") private WebElement menuOptions;
	 
	 @FindBy(css=".start-free-trial-button") private WebElement freeTrialButton;
	 
	 @FindBy(css="#root > div > div > div > div:nth-child(1) > div:nth-child(2) > div > div:nth-child(1) > a:nth-child(2)")
	 	private WebElement start7DayFreeTrial;
	 
	 //Signup with Google account
	// @FindBy(css="button.social/google")
	 
	 @FindBy(css=" div.box-container button.social\2f google > div > span")
	      private WebElement googleSignupButton;
	 @FindBy(css="#identifierId") private WebElement email;
	 @FindBy(css="#identifierNext" ) private WebElement next;
	 @FindBy(name="password") private WebElement password;
	 @FindBy(css="#passwordNext" ) private WebElement passwordNext;
	 
	 private String mainWindow;  //remember the main window so that later on we can switch back after login.
     
	 
	 public void signUp7DayTrial(String gmail, String password)
	 {
		 start7DayFreeTrial.click();
		
		 googleSignup(gmail, password);
		 
		 driver.switchTo().window(mainWindow);
		 logger.info("Switched to:" + driver.getWindowHandle());
		 // Verify welcome back message
		 logger.info(driver.findElement(By.cssSelector("div.box-container h4")).getText());
		 
		 driver.findElement(By.cssSelector("div.box-container > div > button > div > span")).click();
		 //verify the package plan info are displayed.
		 
	 }
	 
	
	 
	 public void googleSignIn()
	 {
		 driver.findElement(By.cssSelector("div.login-button span")).click();
		 googleSignup(USER_NAME, PASSWORD);
		 
	 }
	 
	 private void googleSignup(String userName, String password)
	 {
		 driver.findElement(By.cssSelector("button[class*='google']")).click();
		 mainWindow = switchWindow();
	     find(driver, By.id("identifierId"), 15).sendKeys(userName);
		 next.click();
		 find(driver, By.name("password"), 15).sendKeys(password);
		 passwordNext.click();
		 //go back to main window after signup is done
		 driver.switchTo().window(mainWindow);
	 }
	 

}
