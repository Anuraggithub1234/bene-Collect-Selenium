//package selenium;
//
//import java.util.List;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//
//public class MakingPayment {
//
//	public static void main(String[] args) throws InterruptedException {
//
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Documents\\externalFiles/chromedriver.exe");
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--remote-allow-origins=*");
//		
//
//		WebDriver driver = new ChromeDriver(options);	
////		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
////		driver.manage().timeouts().pageLoadTimeout(100,TimeUnit.SECONDS);
//		
//		driver.manage().window().maximize();
//
//		driver.get("http://webmail.benepay.io");
//		
//		driver.findElement(By.cssSelector("#rcmloginuser")).sendKeys("anurag@benepay.io");
//		driver.findElement(By.cssSelector("#rcmloginpwd")).sendKeys("ragorana16");
//		driver.findElement(By.cssSelector("#rcmloginsubmit")).click();
//		
//		
//		List<WebElement> mails = driver.findElements(By.xpath("//table[@id='messagelist']/tbody//../preceding-sibling::tr[47]/td/span[3]/a/span"));
//		
//		for(WebElement m : mails) {
//			System.out.println(m.getText());
//			
//			if(m.getText().contains("Request for payment from Test Mid One Ltd")) {
//				Thread.sleep(3000);
//			   m.click();
//			   
//			   Thread.sleep(8000);
//			   
//			WebElement link = driver.findElement(By.xpath("//div[@class='leftcol']/div[2]/div/div/p[3]/a"));
//			 System.out.println(link);
//			
//			
//			}
//		}
//		
//		
//		
//		driver.close();
//
//	}
// 
//}
