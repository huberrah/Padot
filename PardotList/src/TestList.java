import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import org.openqa.selenium.Keys;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.Object;
import java.util.Random;
import org.openqa.selenium.WebElement;

//TODO  Create a Page Object Framework

public class TestList{

//Declare uninstantiated objects
	WebDriver driver;
	DesiredCapabilities capability;
	WebElement we;
	Random randomGenerator = new Random();
	
	
	
//Create date object to set randomized name	
	Date date = new Date();
	SimpleDateFormat simple = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
	String dateTimeStamp = simple.format(date);
	Integer randomInt = randomGenerator.nextInt(100);
	String randomString = randomInt.toString();

//TODO create a randomized string object for email

	private String userName = "pardot.applicant@pardot.com";
	private String password = "Applicant2012";
	private String randomizedName;
	private String randomizedName2;
	private StringBuffer verificationErrors = new StringBuffer();
	
	void fnLogin(){
		driver.findElement(By.id("email_address")).sendKeys(userName);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.name("commit")).click();
	}
	
	void fnCreateList(String randomizedName) {
		
		driver.switchTo().defaultContent();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("mark-tog")));
		driver.findElement(By.id ("mark-tog")).click();
		  
	    Actions action = new Actions(driver);
	    action.moveToElement(driver.findElement(By.linkText("Segmentation"))).perform();
	    
	    //Wait Until List is visible then click it
	    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/list']")));
	    driver.findElement(By.cssSelector("a[href='/list']")).click();
		
	    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add List")));
	    driver.findElement(By.linkText("Add List")).click();
	    
	    driver.switchTo().defaultContent();
	    wait.until(ExpectedConditions.elementToBeClickable(By.id("save_information")));
	    driver.findElement(By.id("name")).sendKeys(randomizedName);
		action.moveToElement(driver.findElement(By.id("save_information")));
	    action.perform();
		driver.findElement(By.id("save_information")).click();
		
	}
	
		
	@Before
	public void setUp() throws Exception {    
	//initialize web driver capabilities
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized", "test-type");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		
	}
	
	@Test
	public void testList() throws Exception{
		//Open Home Page
		System.out.println("Opening Pardot");
		driver.get("https://pi.pardot.com");
		
		//LogIn
		System.out.println("Logging In");
		fnLogin();
		
		System.out.println("Creating List");
		
		//Wait for the Elements & CreateList
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("mark-tog")));
	    
		randomizedName = dateTimeStamp.toString();
		fnCreateList(randomizedName);
	    
	    //Attempt to create the same name again
	    fnCreateList(randomizedName);
	    
	    driver.switchTo().defaultContent();
	    
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.alert")));
	    Assert.assertNotNull(driver.findElement(By.cssSelector("#li_form_update>div.alert.alert-error")));
	   driver.findElement(By.cssSelector("a.btn.btn-default")).click();
	    
	    //Rename the list
	    driver.switchTo().defaultContent();
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(randomizedName)));
	    driver.findElement(By.linkText(randomizedName)).click();
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Edit")));
;	    driver.findElement(By.linkText("Edit")).click();
	    driver.switchTo().defaultContent();
	    wait.until(ExpectedConditions.elementToBeClickable(By.id("save_information")));
	    driver.findElement(By.id("name")).sendKeys(dateTimeStamp);
		
	    Actions action = new Actions(driver);
	    action.moveToElement(driver.findElement(By.id("save_information")));
	    action.perform();
		driver.findElement(By.id("save_information")).click();
	    
		try{
	    Assert.assertNull(driver.findElement(By.cssSelector("#li_form_update>div.alert.alert-error")));
		}
		catch(Exception e){
			verificationErrors.append(e.toString());
		}
	    fnCreateList(randomizedName);
	    
	    try{
	    Assert.assertNotNull(driver.findElement(By.linkText(randomizedName)));
	    }
	    catch(Exception e){
	    	verificationErrors.append(e.toString());
	    }
	    
	    
	    //Create a New Prospect
	    driver.findElement(By.id("pro-tog")).click();;
	    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/prospect']")));
	    action.moveToElement(driver.findElement(By.cssSelector("a[href='/prospect']")));
	    action.perform();
	    driver.findElement(By.linkText("Prospect List")).click();
	    driver.switchTo().defaultContent();
	    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add Prospect")));
	    driver.findElement(By.linkText("Add Prospect")).click();
	    
	    wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
	    driver.findElement(By.id("email")).sendKeys(randomString+"abc"+"@test.com");
	    
	    //create a Select Driver
	    Select select = new Select(driver.findElement(By.id("campaign_id")));
	    select.selectByVisibleText("Allison Tigers");
	    
	    Select select2 = new Select(driver.findElement(By.id("profile_id")));
	    select2.selectByVisibleText("Alan Dawgs 2");
	    driver.findElement(By.name("commit")).click();
	    
	    
	    //add to the newly created list
	    driver.switchTo().defaultContent();
	    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Lists")));
	    driver.findElement(By.linkText("Lists")).click();
	    wait.until(ExpectedConditions.elementToBeClickable(By.name("commit")));
	    driver.findElement(By.cssSelector("*[id^='sel'][id$='_chzn']")).click();
	    
	    driver.findElement(By.cssSelector("div.chzn-search>input[type='text']")).sendKeys(randomizedName);
	    we = driver.findElement(By.cssSelector("div.chzn-search>input[type='text']"));
	    we.sendKeys(Keys.RETURN);
	    Assert.assertNotNull(driver.findElement(By.linkText(randomizedName)));
	    driver.findElement(By.name("commit")).click();;
	    
	    //Verify prospect is on the list
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.alert")));
	    Assert.assertNotNull(driver.findElement(By.cssSelector("div.alert")));
	    
	    //Send the marketing mail
		driver.findElement(By.id ("mark-tog")).click();
		action.moveToElement(driver.findElement(By.linkText("Emails"))).perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/email/draft/edit']")));
	    driver.findElement(By.cssSelector("a[href='/email/draft/edit']")).click();
	    Thread.sleep(5000)
	    ;
	    
	    driver.switchTo().defaultContent();
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
	    driver.findElement(By.id("name")).sendKeys(dateTimeStamp);
	    driver.findElement(By.cssSelector("div.input-prepend[data-placeholder-text='Choose a Campaign']>span.add-on")).click();
	    driver.findElement(By.cssSelector("input.ember-view")).sendKeys("Andy Indians");
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("i.icon-bullhorn")));
	    
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.well.well-small.media.folder-row.clearfix")));
	    driver.findElement(By.cssSelector("div.well.well-small.media.folder-row.clearfix")).click();
	    driver.findElement(By.id("select-asset")).click();
	    
	    driver.switchTo().defaultContent();
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Save")));
	    driver.findElement(By.linkText("Save")).click();;
	    Thread.sleep(5000);
	    
	    //Select Text then Save
	    
	    driver.switchTo().defaultContent();
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.modal")));
	    driver.switchTo().defaultContent();
	    driver.findElement(By.cssSelector("input.input-medium.search-query")).sendKeys("Allison Tigers");
	    driver.findElement(By.cssSelector("div.content > ul.templates > li")).click();
	    driver.findElement(By.id("template_confirm")).click();
	    
	    //Choose Text Only and Save
	    Thread.sleep(5000);
	    driver.findElement(By.cssSelector("a[href='#text']")).click();
	    driver.findElement(By.id("save_footer")).click();
	    Thread.sleep(5000);
	    driver.findElement(By.id("acct-tog")).click();
	    driver.findElement(By.linkText("Sign Out")).click();
	    
	   //driver.quit();
		   
	   }
	
	   
	}

