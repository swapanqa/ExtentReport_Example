package ExtentReportScreenshot;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExplocitlyWait {

	public static void main(String[] args) {
	
		
WebDriver driver = null;
		
		// System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +
		//"/src/main/resources/driver/geckodriver");
		
		 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
				 + "/src/main/resources/driver/chromedriver");
		//WebDriverManager.chromedriver().arch64().setup();
		
		
		
		
		
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
		
		driver.get("https://www.facebook.com");
		clickOn(driver, driver.findElement(By.id("u_0_2")), 20);
		
	}
	
	
	public static void clickOn(WebDriver driver,WebElement locator,int timeOut) {
		
		new WebDriverWait(driver, timeOut).ignoring(StaleElementReferenceException.class)
		.until(ExpectedConditions.elementToBeClickable(locator));
		locator.click();
		
		
		
		
		
	}
	
	
	
	
	
	
	

}
