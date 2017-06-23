package com.fubotv.UIAutomation;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

public class Utils {
	
	public static WebDriver  createWebDriver() 
	{
		return createWebDriver("firefox");
	}
	
	
	public static WebDriver  createWebDriver(String browser) 
	
	{   WebDriver driver;
	
	    if (browser.equalsIgnoreCase("firefox"))
	    {  
	        System.setProperty("webdriver.gecko.driver","C:\\Users\\azurewangyx\\seleniumDownloads\\geckodriver.exe");
	    	FirefoxProfile profile = new FirefoxProfile();
	         profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
	    	profile.setPreference("browser.startup.homepage","about:blank");

	    	profile.setPreference("toolkit.startup.max_resumed_crashes", "-1");

	    	 driver = new FirefoxDriver(profile);
	    	
	    	driver = new FirefoxDriver(profile);
	
	    }else if (browser.equalsIgnoreCase("chrome"))
	    {   
	    	  ChromeDriverManager.getInstance().setup();
	    	  /*
		      ChromeOptions options = new ChromeOptions();
		      options.addArguments("test-type");
		      
		      if (OSDetector().equals("Windows"))
		          options.addArguments("--start-maximized");
		      else
		    	  options.addArguments("--kiosk");
		      driver = new ChromeDriver(options);
		      */
	    	
	    	/*
	    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\azurewangyx\\seleniumDownloads\\" + "chromedriver.exe");
	    	  ChromeOptions options = new ChromeOptions();
	    	  options.addArguments("user-data-dir=C://Users//azurewangyx//AppData//Local//Google//Chrome//User Data");
	    	  //options.addArguments("test-type");
	    	  options.addArguments("user-data-dir=C:\\Users\\azurewangyx\\AppData\\Local\\Google\\Chrome\\User Data");
		    	
	    	  options.addArguments("–start-maximized");
	    	  */
	    	  driver = new ChromeDriver();
	      
	      }else if (browser.equalsIgnoreCase("ie"))
	      {  
	    	  DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
	    	  
	    	  capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
	    	  capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
	    	  
	    	  capabilities.setCapability("unexpectedAlertBehaviour", "accept");
	    	  capabilities.setCapability("ignoreProtectedModeSettings", true);
	    	  capabilities.setCapability("enablePersistentHover", true);

	    	  File file = new File("c:\\Users\\azurewangyx\\seleniumDownloads\\IEDriverServer.exe");
	    	  System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
	    	   driver = new InternetExplorerDriver(capabilities);
	    	
	
	      }else  if (browser.equalsIgnoreCase("edge"))
	      {
	    	  String serverPath = "C:\\Program Files (x86)\\Microsoft Web Driver\\MicrosoftWebDriver.exe";
	    	  System.setProperty("webdriver.edge.driver", serverPath);
	    	
	    	   driver = new EdgeDriver();
	    	  
	      }else
	      {
		      System.out.println("Unknown browser.");
		      return null;
	      }
	      driver.manage().deleteAllCookies();
	     driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	      driver.manage().window().maximize();
	     
	      return driver;
	
	  }
	
	public static void waitForPageToLoad(WebDriver driver) {
	    ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
	
	        public Boolean apply(WebDriver driver) {
	
	          return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	
	        }
	
	      };
	
	    Wait<WebDriver> wait = new WebDriverWait(driver,1000);
	
	      try {
	              wait.until(expectation);
	
	      } catch(Throwable error) {
	
	              System.out.println("Timeout waiting for Page Load Request to complete.");
	      }
	} 
	
	
	public static WebDriver launchBrowser(String url, String browser)
	{   
		WebDriver driver;
		driver = createWebDriver(browser);
		driver.get(url);
		return driver;
	}
	
	
	
	public static int getRandomInt()
	{
		Random randomGenerator = new Random();
	  
	    int randomInt = randomGenerator.nextInt(999999);
	     
	    return randomInt;
	    
	}
	
	
	public static void scrollElementIntoView(WebDriver driver, WebElement element)
	{   
		JavascriptExecutor jse = (JavascriptExecutor)driver; 
		jse.executeScript("arguments[0].scrollIntoView()", element);
		
	}

	public static void scrollToTopOfElement(WebDriver driver,   WebElement element)
	{
		int offset = element.getSize().getHeight() * (-1);
		System.out.println("See element height:" + offset);
		JavascriptExecutor jse = (JavascriptExecutor)driver; 
		jse.executeScript("window.scrollBy(0," + offset + ")", "");
	}

	/**
	 *Utility method to take screenshot upon exception or assertion failures. Screenshot name is generated this way: testcase name + current date in millisecond.
	 * @param  driver  the driver for the current web page
	 * @param  testMethod  the running test method. The screenshot name will spell out testCaseName explicitly so that we know what screenshot is for what test case
	 * @return      
	 */
    public static void takeScreenshot(WebDriver driver, String testCaseName) 
    {      
 	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    			Date date = new Date();
 	       String screenshotName = testCaseName + dateFormat.format(date) + ".png";
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            try{
               FileUtils.copyFile(scrFile, new File(screenshotName));
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("Screenshot: " + screenshotName + " is taken.");
    }
    
}
