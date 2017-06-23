package com.fubotv.UIAutomation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

//Home page for signed-in users
public class HomePage extends Page{
    // Main category menu
	@FindBy(css="a[href*='/']")
		private WebElement  sports;

	@FindBy(css="a[href*='/entertainment']")
	    private WebElement  entertainment;
	
	@FindBy(css="a[href='/guide']")
		private WebElement  guide;

	@FindBy(css="a[href*='/dvr']")
    	private WebElement  myDVR;

	//CATEGORY BAR(Top-tier menu)
	@FindBy(css="#root > div > div > div > div:nth-child(1) > div:nth-child(3) > div > div:nth-child(1) > div > div:nth-child(2) > div") 
	     private WebElement  categoryBar;
	
	//submenu
	@FindBy(xpath="//*[@id='root']/div/div/div/div[1]/div[3]/div/div[2]/div")   private WebElement  submenuBar;
	
	
	
	public void gotoPage(String category, String menuItem)
	{  
	     switch (category) {
	     	case "Sports":
        	    find(sports).click(); 
                 break;
	         case "Entertainment":
	        	 find(entertainment).click(); 
	             break;
	         case "Guids":
	        	    find(guide).click(); 
	                 break;
		     case "MyDVR":
		        	 find(myDVR).click(); 
		             break;
	         default:
	             throw new IllegalArgumentException("Invalid category: " + category);
	     }
		
		
	    List<WebElement> submenuOptions = find(submenuBar).findElements(By.tagName("a"));
	    for (WebElement submenuOption: submenuOptions)
	    {
	       if(submenuOption.getText().equalsIgnoreCase(menuItem))
	       {   submenuOption.click();
	           break;
	       }
	    }
	    		
	}
	
	
	
	
	
	
}
