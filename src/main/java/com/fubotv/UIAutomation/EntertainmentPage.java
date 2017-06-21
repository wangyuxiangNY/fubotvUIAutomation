package com.fubotv.UIAutomation;

import java.util.List;


import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EntertainmentPage extends Page {
	
	@FindBy(css="div.dual-small-button svg") private WebElement  recordingFlip;
	@FindBy(css="div.modal-wrapper") private WebElement modalWrapper;
	
	@FindBy(css="div.ReactModalPortal")  private WebElement  react;
	@FindBy(css="div.modal-header > h4 > span")  private WebElement modalHeader;
	@FindBy(css="div.modal-footer > button:nth-child(2) > div > span")  private WebElement cancelRecording;
	
	@FindBy(css="div[title='Open Season'] > button") private WebElement  playButton;
	@FindBy(css="a.back")   private WebElement back;
	@FindBy(css="video-container > video") private WebElement video;
	@FindBy(css="play-pause-button-container > play-pause-button[title='Pause']")   private WebElement pause_icon;
	@FindBy(css="play-pause-button-container > play-pause-button[title='Play']")   private WebElement play_icon;
	
	
	public void playChannelThenPause()
	{
		find(playButton).click();
		//verify it is playing by checking the back button
		try {
			find(video);
		}catch(Exception e)
		{
			fail("video is not playing.");
		}
		
		//Hover over playing video and try to pause it.
		hoverThenClick( video,  pause_icon);
		//verify it is playing by checking the back button
		try {
			find(play_icon);
		}catch(Exception e)
		{
			fail("video is not paused.");
		}
	}
	
	public void scheduleRecording()
	{   //Decide  whether or not recording is already scheduled
		if (find(recordingFlip).getAttribute("width").equals("18"))
		{
			recordingFlip.click();
			
			//Verify that growl shows up
			System.out.println(find(modalWrapper).getText());
		}else
		{
	        //recording is already scheduled.Now let's cancel it
			recordingFlip.click();
			
			//Verify that modal dialogue shows up
			if (!find(modalHeader).getText().equals("Cancel Recording"))
				fail("Modal box for cancelling recording is not showing up.");
			else
			{
				cancelRecording.click();
				System.out.println("see react:" + find(react).getText());
			}
			
		}
		
	    
	
	}
	
	public void cancelRecording()
	{
		
	}
	

}
