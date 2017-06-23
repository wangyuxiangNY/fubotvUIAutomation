package com.fubotv.UIAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class Video extends Page{
	
	@FindBy(css="div.inner-wrapper > div.left-section > a.back") private WebElement back;
	@FindBy(css="div.inner-wrapper > div.left-section span.program") private WebElement playingProgram;
	

	@FindBy(tagName="video-container")  private WebElement videoContainer;
	@FindBy(tagName="video-controls-container")  private WebElement videoControlContainer;
	@FindBy(css="div.inner-wrapper button svg") private WebElement  recordingFlip;
	//@FindBy(css="div.ReactModal__Body--open") private WebElement  growl;
	@FindBy(css="#root > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(4) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > span:nth-child(1)")
                                    private WebElement  growl;
	@FindBy(css="div.modal-wrapper") private WebElement modalWrapper;
	
	@FindBy(css="div.ReactModalPortal")  private WebElement  react;
	@FindBy(css="div.modal-header > h4 > span")  private WebElement modalHeader;
	@FindBy(css="div.modal-footer > button:nth-child(2) > div > span")  private WebElement cancelRecording;
	
	@FindBy(css="div[title='Open Season'] > button") private WebElement  playButton;
	@FindBy(css="video-container > video") private WebElement video;
	@FindBy(css="play-pause-button-container > play-pause-button[title='Pause']")   private WebElement pause_icon;
	@FindBy(css="play-pause-button-container > play-pause-button[title='Play']")   private WebElement play_icon;
	
	//vidoe controls
	@FindBy(tagName="play-pause-button") private WebElement pauseButton;
	
	@FindBy(tagName="volume-button") private WebElement volumeButton;
	@FindBy(tagName="volume-track") private WebElement volumeTrack;
	@FindBy(tagName="volume-head") private WebElement volumeHead;
	@FindBy(tagName="volume-fill")   private WebElement volumeFill;
	
	@FindBy(tagName="settings-button") private WebElement settingsButton;
	@FindBy(tagName="full-screen-button") private WebElement fullScreenButton;
	
	
	/* Verify pauseButton works fine
	 */
	public void  verifyVideoControls()
	{  
		verifyPause();
	}
	
	/* This method does the following:
	 * 1. Hover over the playing video so that the bottom video controls will show
	 * 2. Click on PAUSE BUTTON
	 * 3. Verify that the video is paused by checking button's title attribute
	 * 
	 */
	private void verifyPause()
	{   
		hoverThenClick(find(driver, By.tagName("video")),  pauseButton);
		//verify that video is paused
		 String title = find(pauseButton).getAttribute("title");
		logger.info("SEE BUTTON TITLE:" + title);
		if (!title.equals("Play"))
			handleError("video is not paused.", "verifyPause");
	}
	
	private void hoverOver(WebElement ele)
	{
		Actions action = new Actions(driver);
	    action.moveToElement(ele).build().perform();
	}
	
	
	private void hoverOverVolumeButton()
	{
		Actions action = new Actions(driver);
	    action.moveToElement(video).moveToElement(volumeButton).build().perform();
	}
	
	private void hoverOverVolumeButtonThenDragVolumeHeadBy(int Yoffset)
	{
		Actions action = new Actions(driver);
	    action.moveToElement(video).moveToElement(volumeButton).moveToElement(volumeHead).dragAndDropBy(volumeHead, 0, Yoffset).build().perform();
	}
	
	/* Test case(mute/unmute/change volume) implemented in this method
	 * 1. Play a video
	 * 2. Hover over the playing video so that bottom controls show up
	 * 3. click on Volume button to mute the video, verify the video is indeed muted 
	 * 4. Click on volumn button again to UNMUTE it, verify volume value to make sure that it is indeed UNMUTED
	 * 5. Change volume by dragging volume head, and verify volume is changed
	 * 
	 */
	public void verifyVolume()
	{
		//play();
		hoverOver(find(video));
		String clazz = find(volumeButton).getAttribute("class");
	    logger.info("SEE volume state  before mute:" + clazz);
		//mute 
		hoverThenClick(videoControlContainer, volumeButton);
		clazz = find(volumeButton).getAttribute("class");
	    logger.info("SEE volume state  after mute:" + clazz);
		if (!clazz.contains("mute-volume-state"))
			handleError("video is not muted.", "verifyVolume");
		
		// unmute
		hoverOver(find(video));
		 clazz = find(volumeButton).getAttribute("class");
	    logger.info("SEE volume state  before UNmute:" + clazz);
		hoverThenClick(videoControlContainer, volumeButton);
		clazz = find(volumeButton).getAttribute("class");
	    logger.info("SEE volume state  after UNmute:" + clazz);
		if (!clazz.contains("full-volume-state"))
			handleError("video is not unmuted.", "verifyVolume");
		
		//change volume
		hoverOverVolumeButton();
		String percentage = find(volumeFill).getAttribute("style");
		logger.info("See volume before change:" + percentage);
		hoverOverVolumeButtonThenDragVolumeHeadBy(20);
		String newPercentage = volumeFill.getAttribute("style");
		logger.info("See volume after change:" + newPercentage);
		
		if(percentage.equals(newPercentage))
			handleError("Volume is not changed.", "verifyVolume");
		
	}
	
	public void scheduleRecording()
	{
		find(recordingFlip).click();
			
		//Verify that growl shows up
		String message = find(growl, 20).getText();
		logger.info("see growl:" + message );
		if (message.length()<1)  //sometime got 'we are sorry.This program cannot be recorded at this time.' Not sure this is a bug or expected.
			handleError("Schedule recording is not working.", "scheduleRecording");
    }
	
	
	private void cancelRecording()
	{
		//Suppose that recording is already scheduled.Now let's cancel it
		recordingFlip.click();
		
		//Verify that modal dialogue shows up
		if (!find(modalHeader).getText().equals("Cancel Recording"))
			handleError("Modal box for cancelling recording is not showing up.", "cancelRecording");
		else
		{
			cancelRecording.click();
			logger.info("see react:" + find(react).getText());
		}
	}
	
	public  String getTiteOfPlayingProgram()
	{
		return find(playingProgram).getText();
	}
}
