package com.fubotv.UIAutomation;


import static org.junit.Assert.assertThat;
import java.io.File;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matcher;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ErrorCollectorDecorator extends ErrorCollector{
	
	protected ErrorCollector decoratedErrorCollector;

    public ErrorCollectorDecorator(ErrorCollector decoratedErrorCollector){
       this.decoratedErrorCollector = decoratedErrorCollector;
    }

    public void addError( Throwable error, WebDriver driver,  String screenshotName  )
    {
    	decoratedErrorCollector.addError(error);
    	takeScreenshot(driver, screenshotName);
    }
    
    public <T> void checkThat(final T value, final Matcher<T> matcher,  WebDriver driver, String screenshotName) {
    	checkThat("", value, matcher, driver, screenshotName );
    	
    }

    /**
     * Adds a failure with the given {@code reason}
     * to the table if {@code matcher} does not match {@code value}.
     * Execution continues, but the test will fail at the end if the match fails.
     */
    public <T> void checkThat(final String reason, final T value, final Matcher<T> matcher , WebDriver driver,  String screenshotName) {
        checkSucceeds((new Callable<Object>() {
            public Object call() throws Exception {
                assertThat(reason, value, matcher);
                return value;
            }
        }), driver, screenshotName);
    }
    
    public Object checkSucceeds(Callable<Object> callable, WebDriver driver,  String screenshotName) {
    	 try {
             return callable.call();
         } catch (Throwable e) {
             addError(e, driver, screenshotName);
             return null;
         }
    	
    }
    
    public void takeScreenshot(WebDriver driver, String  name)
    {
    	try {
    		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
           // String filePathRoot = "C:\\_Jenkins\\workspace\\" + jenkinsJobName + "\\target\\surefire-reports\\";
    		String currentPath =  System.getProperty("user.dir");
    		String path = currentPath + "\\target\\surefire-reports\\";
            String fullFilePath = name + ".jpg";
            FileUtils.copyFile(screenshot, new File(fullFilePath));
            System.out.println("Screenshot is taken:" + fullFilePath);
        } catch(Exception ex) {
            System.out.println(ex.toString());
            System.out.println(ex.getMessage());
        }
    }
    
}
