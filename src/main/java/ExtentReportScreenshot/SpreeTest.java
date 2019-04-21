package ExtentReportScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SpreeTest {

	public WebDriver driver;
	public ExtentReports extent;
	public ExtentTest logger;
	
	
	
	
	@BeforeTest
	public void setExtent() {
		
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport.html",true);
		extent.addSystemInfo("Host Name", "Mamun Mac");
		extent.addSystemInfo("User Name", "Shams Automation Labs");
		extent.addSystemInfo("Enviroment","QA");
		
	}
	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}
	
	public static String  getScreenshot(WebDriver driver,String screenshotName) throws IOException {
		
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		
		//after execution you could see a folder "FailedTestsScreenshots"
		//under src folder 
		
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
		
		
	}
	
	
	
	

	@BeforeMethod
	public void setUp() {
     
		System.setProperty("webdriver.chrome.driver", "");
		WebDriverManager.chromedriver().arch64().setup();
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("http://spree.shiftedtech.com/");

	}

	@Test(priority = 0)
	public void spreeTitleTest() {
		logger = extent.startTest("spreeTitleTest");

		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(title, "Spree Demo Site");

	}
	@Test(priority = 1,description="Valid user with valid password")
	public void test1() {
		//report.log("Click login link");
		WebElement loginLink = driver.findElement(By.linkText("Login"));
		loginLink.click();
		//click(By.linkText("Login"));
		
		//report.log("Enter user id");
		WebElement emailTextbox = driver.findElement(By.id("spree_user_email"));
		emailTextbox.sendKeys("shiftqa01@gmail.com");
		
		//report.log("Enter password");
		WebElement passwordTextbox = driver.findElement(By.id("spree_user_password"));
		passwordTextbox.sendKeys("shiftedtech");
		
		//report.log("Click login button");
		WebElement loginButton = driver.findElement(By.name("commit"));
		loginButton.click();
		
		//report.log("Verify login success");
		WebElement successMsg = driver.findElement(By.cssSelector(".alert-success"));
		String msg = successMsg.getText();
		Assert.assertEquals(msg, "Logged in successfully");
	}
	
	@Test(priority = 2,description = "Valid user with invalid password")
	public void test2() {
		WebElement loginLink = driver.findElement(By.linkText("Login"));
		loginLink.click();
		
		WebElement emailTextbox = driver.findElement(By.id("spree_user_email"));
		emailTextbox.sendKeys("shiftqa01@gmail.com");
		
		WebElement passwordTextbox = driver.findElement(By.id("spree_user_password"));
		passwordTextbox.sendKeys("invalid");
		
		WebElement loginButton = driver.findElement(By.name("commit"));
		loginButton.click();
		
		WebElement successMsg = driver.findElement(By.cssSelector(".alert-error"));
		String msg = successMsg.getText();
		Assert.assertEquals(msg, "Invalid email or password.");
	}
	
	@Test(priority = 3,description="Invalid user with valid password")
	public void test3() {
		WebElement loginLink = driver.findElement(By.linkText("Login"));
		loginLink.click();
		
		WebElement emailTextbox = driver.findElement(By.id("spree_user_email"));
		emailTextbox.sendKeys("invalid@gmail.com");
		
		WebElement passwordTextbox = driver.findElement(By.id("spree_user_password"));
		passwordTextbox.sendKeys("shiftedtech");
		
		WebElement loginButton = driver.findElement(By.name("commit"));
		loginButton.click();
		
		WebElement successMsg = driver.findElement(By.cssSelector(".alert-error"));
		String msg = successMsg.getText();
		Assert.assertEquals(msg, "Invalid email or passwordx.");
	}
	
	
	
	
	

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if(result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test case failed is " +result.getName());//to add name is extent report
			logger.log(LogStatus.FAIL, "Test case failed is " +result.getThrowable()); //add error /exception in extent report
			
			String screenshotPath = SpreeTest.getScreenshot(driver, result.getName());
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath)); //to add screenshot in extent report
			//logger.log(LogStatus.FAIL,logger.addScreencast(screenshotPath)); //to add screenshotCast/vedio in extent report
		}
		
		else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
			
		}else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, "Test Case Passed is " + result.getName());
		}
		
		extent.endTest(logger);  //ending test and ends the current test and prepare to create html report
		
		driver.quit();
	}

}
