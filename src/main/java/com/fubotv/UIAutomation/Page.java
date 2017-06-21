package com.fubotv.UIAutomation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

public abstract class Page {
	
	//ever-present elements
	
	
	 @FindBy(css=".input") public WebElement searchBox;
	 @FindBy(name="Filter") public WebElement searchFilter;
	   
	
	
	
	final static Logger logger = Logger.getLogger(Page.class);
 	
	public static WebDriver driver;
	
	 static String browser ="";
	 final String TRIAL_USER_NAME ="fubotvrocks@gmail.com";
	 final String TRIAL_PASSWORD ="fubotv999";
	 final String USER_NAME ="yuxiang.wang.ny@gmail.com";
	 final  String PASSWORD ="Wang1234";
	
		
	public Page()
	{  
		PageFactory.initElements(driver, this);
		
	}
	
	public Page(WebDriver _driver)
	{   
		this.driver = _driver;
		PageFactory.initElements(driver, this);
		
	}
	
	
	public boolean  isElementPresent(WebElement element)
	{
		 try{
			  System.out.println("see element:" +  element.getText());
			   return true;
		   }catch(Exception e)
		   {  // e.printStackTrace();
			   return false;
		   }
	}
	
	public  void setBrowser(String _browser)
	{
		browser = _browser;
	}
	
	public  String getBrowser()
	{
		return browser;
	}
	
	
	
	public  String  switchWindow()
	{
		//Switch to new tab where the sign-up is
		String winHandleBefore = driver.getWindowHandle();
		System.out.println("SEE current window:" + winHandleBefore); 
		//Switch to new window opened
		for(String winHandle : driver.getWindowHandles()){
			System.out.println("windows:" + winHandle);
		    driver.switchTo().window(winHandle);
		}	
		
		WaitUtility.waitForPageToLoad(driver);
		
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
	
	
    public static void takeScreenshot(WebDriver driver, String testMethod) throws Exception 
    {      
 	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    			Date date = new Date();
    			//System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
 	       String screenshotName = testMethod + dateFormat.format(date) + ".png";
 	       System.out.println("See screenshotName:" + screenshotName);
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
         //The below method will save the screen shot in d drive with name "screenshot.png"
            FileUtils.copyFile(scrFile, new File(screenshotName));
            System.out.println("Screenshot is taken.");
    }
    
    
    public static void takeRemoteScreenshot(RemoteWebDriver driver, String testMethod) throws Exception
    {
    	takeScreenshot(new Augmenter().augment(driver ), testMethod);
    }
    
    public  WebElement waitForElement( WebElement element, long timeOutInMilliSecond)
	{
    	return waitForElement(driver, element, timeOutInMilliSecond);
	}
    
	//.isDisplayed() doesn't work with iheart elements, This might have something to do ajax elements
	public static WebElement waitForElement(WebDriver driver, WebElement element, long timeOutInMilliSecond)
	{  
		long times = timeOutInMilliSecond / 500 + 1;    
		long count = 0;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		do{
			try{
			   System.out.println(element.getAttribute("outerHTML"));
			  if (element.isEnabled())
			      break;
			}catch(Exception e)
			{  System.out.println("Not there. try again.");
			  // e.printStackTrace();
			   WaitUtility.sleep(500);
			}
			
			count++;
		}while (count< times);
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		return element;
	}
	
	
	public  void hoverThenClick(WebElement hoverTo, WebElement clickTarget)
	{
		Actions actionBuilder = new Actions(driver);
		actionBuilder.moveToElement(hoverTo).click(clickTarget).build().perform();
		 
	}
	
	public  WebElement find(WebElement element)
	{
		return find(element, 20);
	}
	
	public  WebElement find(WebElement element, int timeoutInSecond)
	{  if (driver == null)
		System.out.println("driver is null");
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
	
	
}