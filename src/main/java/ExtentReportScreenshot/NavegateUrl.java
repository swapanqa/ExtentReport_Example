package ExtentReportScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NavegateUrl {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		WebDriver driver = null;
		
		// System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +
		//"/src/main/resources/driver/geckodriver");
		
		// System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
			//	 + "/src/main/resources/driver/chromedriver");
		WebDriverManager.chromedriver().arch64().setup();
		
		
		
		
		
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// back and forward simulation
		
		driver.get("https://www.google.com");
		
		//driver.navigate().to("https://www.amazon.com");
		
	/*
	 * 	driver.navigate().back();
	 
		
		Thread.sleep(2000);
		
		driver.navigate().forward();
		
		driver.navigate().back();
		
		driver.navigate().refresh();
   */
		File src = ( (TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		FileUtils.copyFile(src, new File("/Users/mamun79/Documents/Eclipse_Project/ExtentReportScreenshot/src/main/resources/google.png"));
		
		

	}

}
