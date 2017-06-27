package com.fubotv.UIAutomation;

import com.fubotv.UIAutomation.Page;
import com.fubotv.UIAutomation.LandingPage;

import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.Description;
import org.junit.After; 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class TestEntertainment {

	private WebDriver driver;
	private EntertainmentPage entertainPage;
	private LandingPage trialPage;
	private HomePage homePage;
	private Video video;
	
	 String browser = "chrome";
	
	final String URL = "https://www.fubo.tv";
	
	@Rule public TestName testName = new TestName();
	
	@Before
    public void init( )throws Exception
	{  
		driver = Utils.launchBrowser(URL, browser);
	    Page.setDriver(driver);
	    
	   
		trialPage = PageFactory.initElements(driver, LandingPage.class);
	    entertainPage = PageFactory.initElements(driver, EntertainmentPage.class);
	    homePage = PageFactory.initElements(driver, HomePage.class);
	    video = PageFactory.initElements(driver, Video.class);
	    
	    trialPage.googleSignIn();
	    homePage.gotoPage("Entertainment", "home");
	    
	 }
	
	@Test
	public void testHome_liveSeries() throws Exception
	{  
	      entertainPage.verifyHome_liveSeries();
	}
	
	@Test
	public void testScheduleRecording() throws Exception
	{  
	    entertainPage.scheduleRecording();
	}
	
	@Test
	public void testVideoControls_volumeButton() throws Exception
	{  
	    entertainPage.verifyVideoControls_volumnButton();
	}
	
	
	@After
	public  void tearDown()
	{
		if (Page.getErrors().length() > 0)
			 fail(Page.getErrors().toString());
		driver.quit();
	}
	
	@Rule
	public TestWatcher watchman = new TestWatcher() {
	    @Override
	    protected void failed(Throwable e,Description d) {
	       e.printStackTrace();
           Utils.takeScreenshot(driver, testName.getMethodName());
	    }
	};
}
