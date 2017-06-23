package com.fubotv.UIAutomation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Page {
	
	protected static Logger logger = LoggerFactory.getLogger(Page.class);

	public static WebDriver driver;
	
	 static String browser ="";
	// final String TRIAL_USER_NAME ="fubotvrocks@gmail.com";
	// final String TRIAL_PASSWORD ="fubotv999";
	 final String USER_NAME ="yuxiang.wang.ny@gmail.com";
	 final  String PASSWORD ="Wang1234";
	
	 private static StringBuffer errors = new StringBuffer();  //To hold errors along the way, for late verification
		
	public Page()
	{  
		PageFactory.initElements(driver, this);
		
	}
	
	public Page(WebDriver _driver)
	{   
		driver = _driver;
	}
	
	
	public  void setBrowser(String _browser)
	{
		browser = _browser;
	}
	
	public  String getBrowser()
	{
		return browser;
	}
	
	/**
	 * switch window
	 * @param  url  an absolute URL giving the base location of the image
	 * @param  name the location of the image, relative to the url argument
	 * @return   previous window handeler name
	 */
	public  String  switchWindow()
	{
		//Switch to new tab where the sign-up is
		String winHandleBefore = driver.getWindowHandle();
		logger.info("SEE current window:" + winHandleBefore); 
		//Switch to new window opened
		for(String winHandle : driver.getWindowHandles()){
			logger.info("windows:" + winHandle);
		    driver.switchTo().window(winHandle);
		}	
		
		return winHandleBefore;
	}
	
	public  String getUserName()
	{
		return USER_NAME;
	}
	
	public  String getPassword()
	{
		return PASSWORD;
	}
	
	public static void setDriver(WebDriver _driver)
	{
		driver = _driver;
	}
	
	
	public  WebDriver getDriver()
	{
		return driver;
	}
	
	public  WebElement find(WebElement element)
	{  
		return find(element, 20);
	}
	
	public  WebElement find(WebElement element, int timeoutInSecond)
	{  
		//cancel implicit wait
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond); 
		wait.until(ExpectedConditions.visibilityOf(element));
		
		//restore implicitWait
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		return element;
	}
	
	public static WebElement find(WebDriver driver, By locator)
	{
	      return waitForElementToBeVisible( driver,  locator,  30);
	}
	
	public static WebElement find(WebDriver driver, By locator, int timeoutInSecond)
	{
	      return waitForElementToBeVisible( driver,  locator,  timeoutInSecond);
	}
	
	 /**
	 * Returns web element on the page. If the element is not immediately found, check DOM periodically till it times out 
	 * 
	 * @param  driver  the driver for the current web page
	 * @param  locator  the way to locate this element
	 * @param  timeoutInSecond	Maximum time to wait for the element to be visible										
	 * @return  the web element    
	 */
	public static WebElement waitForElementToBeVisible(WebDriver driver, By locator, int timeoutInSecond)
	{
		//cancel implicit wait
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond); 
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		
		//restore implicitWait
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		return driver.findElement(locator);
	}	
	
	/* hover to the element so that target can show, then click on the target 
	 * @param hoverTo the element that we want to hover to
	 * @param clickTarget target to click on
	 * 
	 */
	public  void hoverThenClick(WebElement hoverTo, WebElement clickTarget)
	{
		Actions actionBuilder = new Actions(driver);
		actionBuilder.moveToElement(hoverTo).moveToElement(clickTarget).click().build().perform();
	}
	
	public static void waitForPageToLoad(WebDriver driver) {

	     ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver driver) {
	          return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	        }
	      };

	     Wait<WebDriver> wait = new WebDriverWait(driver,5*60);   //timeInSeconds, so give it 5 minutes
	      try {
	              wait.until(expectation);
	              logger.info("Page loading is done!");
	      } catch(Throwable error) {
	              logger.info("Timeout waiting for Page Load Request to complete.");
	      }
	 } 
	/* Scroll the hidden element into screen view
	 * @param element  the element that we want to shift into portal view
	 */
   public void scrollIntoView( WebElement element) 
   {
	   ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	  try {
	    Thread.sleep(500);
	  }catch(Exception e)
	  {
		  
	  }
	  
	  scrollToTopOfElement(element);
   }
	
	public  void scrollToTopOfElement(  WebElement element)
	{
		int offset = element.getSize().getHeight() * (-1);
		logger.info("See element height:" + offset);
		JavascriptExecutor jse = (JavascriptExecutor)driver; 
		jse.executeScript("window.scrollBy(0," + offset + ")", "");
	}
	
	
	public void handleError(String msg, String methodName) 
	{
		errors.append(msg);
		try{
		   Utils.takeScreenshot( driver,  methodName);
		}catch(Exception e)
		{
			System.out.println("Exception is thrown taking screenshot.");
		}
	}
	
	public static StringBuffer getErrors()
	{
		return errors;
	}
}