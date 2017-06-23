package com.fubotv.UIAutomation;

import org.openqa.selenium.support.PageFactory;

public class SportsPage extends Page{
	private Video video;
	
	public SportsPage( )
	{
		this.video = PageFactory.initElements(driver, Video.class);;
	}
	
	public SportsPage(Video video)
	{
		this.video = video;
	}
	
    public Video getVideo()
    {
    	return video;
    }
	
    public void watchLiveSport()
    {
    	//to be implemented
    }
    
}
