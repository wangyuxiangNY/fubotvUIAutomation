package com.fubotv.UIAutomation;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EntertainmentPage extends Page {
	private Video video;
	
	//Live Series
	@FindBy(css="div.slick-list:nth-child(1)") private WebElement liveSerieList;
	@FindBy(css="div.slick-list:nth-child(2)") private WebElement recentlyAiredList;
	@FindBy(css="div.slick-list:nth-child(3)") private WebElement liveMovieList;
	
	
	public EntertainmentPage( )
	{
		this.video = PageFactory.initElements(driver, Video.class);;
	}
	
	public EntertainmentPage(Video video)
	{
		this.video = video;
	}
	
    public Video getVideo()
    {
    	return video;
    }

    /*   Test case: 
     *1. Verify that live serie videos are displayed
     *2. Choose one video randomly(So that each time this test case is run,
     * a different video will be hit by the case), and play it
     */
    public void verifyHome_liveSeries()
    {
        //verify that live series are displayed and user can watch any of them fine.
    	List<WebElement> liveVideos = find(liveSerieList).findElements(By.tagName("button"));
    	int liveVideoTotal = liveVideos.size();
    	logger.info("See total:" + liveVideoTotal);
    	if (liveVideoTotal < 1)
    		handleError("No live video is displayed.", "verifyHome_liveSeries");   // This could be normal if indeed there is no live program at this moment, but agains this code is only for demo purpose.
        
    	//Choose one from it randomly and play it
    	int chosenIndex = generateRandomNum(liveVideoTotal);
    	
    	//Need to scroll item into view if the number is bigger than 4.
    	WebElement chosenItem = liveVideos.get(chosenIndex);
    	scrollIntoView(chosenItem);
    	
    	//remember its title for verification later
    	String css = "div[data-index='" + chosenIndex +"'] div.title";
    	logger.info("see css for title:" + css);
    	String title = liveSerieList.findElement(By.cssSelector(css)).getText();
    	logger.info("Chosen item title:" + title);
    	
    	find(chosenItem).click();
    	//Verify the title of playing vidoe
    	logger.info("playing program:" + video.getTiteOfPlayingProgram());
    	if (!video.getTiteOfPlayingProgram().startsWith(title))  //sometimes palying program contains track info as well, thus so
    		handleError("Playing program is not the one that we choose earlier", "verifyHome_liveSeries" );
    	
    }
    
    public void scheduleRecording()
    {   play();
    	video.scheduleRecording();
    }
    
    public void verifyVideoControls_pauseButton()
    {   
    	play();
    	video.verifyVideoControls();
    }
    
    public void verifyVideoControls_volumnButton()
    {
    	play();
    	video.verifyVolume();
    }
    
    public void verifyHome_recentlyAiredSeries()
    {
    	//To be implemented
    }
    
	private void play()
	{
		//live series
	   find(driver, By.cssSelector("div.slick-list"));
	   List<WebElement> slickList = driver.findElements(By.cssSelector("div.slick-list"));
	   for (WebElement slick: slickList)
	   {
		   List<WebElement>  slickPlayButtons = slick.findElements(By.tagName("button"));
		   //choose one randomly or just play the first one? 
		   slickPlayButtons.get(0).click();
		   break;
	   }
	}
    
    /* Utility method to generate a random integer from 0 to max-1 (Since data-index in fuboTV starts from 0)
     * @param max the maximum number
     * @return  randomly generated number
     */
    private int generateRandomNum(int max)
    {
    	Random rand = new Random();
        int  n = rand.nextInt(max) ;
        logger.info("random number:" + n);
        return n;
    }
}
