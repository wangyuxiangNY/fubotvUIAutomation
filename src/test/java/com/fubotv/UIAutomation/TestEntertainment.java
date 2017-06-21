package com.fubotv.UIAutomation;




import com.fubotv.UIAutomation.Page;
import com.fubotv.UIAutomation.TrialPage;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.rules.ErrorCollector;
import org.junit.rules.TestName;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.experimental.results.PrintableResult;
import org.junit.Ignore; 
import org.junit.Before;
import org.junit.After; 
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;

import static org.junit.experimental.results.PrintableResult.testResult;
import static org.junit.experimental.results.ResultMatchers.hasFailureContaining;
import static org.junit.experimental.results.ResultMatchers.isSuccessful;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.util.Date;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class TestEntertainment {

	private WebDriver driver;
	private EntertainmentPage entertainPage;
	private TrialPage trialPage;
	private HomePage homePage;
	
	 String browser = "chrome";
	//String browser = "firefox";
	//String browser = "edge";
	//String browser = "ie";
	 
	final String URL = "https://www.fubo.tv";
	
	@Rule public TestName name = new TestName();
	
	 @Rule
     public  ErrorCollector errorCollector= new ErrorCollector();
	
	@Before
    public void init( )throws Exception
	{  
		driver = Utils.launchBrowser(URL, browser);
	    Page.setDriver(driver);
		trialPage = PageFactory.initElements(driver, TrialPage.class);
	    entertainPage = PageFactory.initElements(driver, EntertainmentPage.class);
	    homePage = PageFactory.initElements(driver, HomePage.class);
	    

	    trialPage.googleSignIn();
	    homePage.gotoPage("Entertainment", "home");
    }
	
	
	@Test
	public void testPlayChannel() throws Exception
	{  
	    entertainPage.playChannelThenPause();
	}
	
	@Test
	 public void testRecordSchedulingAndCancel() throws Exception
	 {  
	    entertainPage.scheduleRecording();
	 }
	
	
	@After
	public  void tearDown()
	{
		//driver.quit();
		
	}
}
