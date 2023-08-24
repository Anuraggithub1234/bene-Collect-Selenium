package seleniumCollect;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v113.network.Network;
import org.openqa.selenium.devtools.v113.network.model.Headers;
import org.openqa.selenium.devtools.v113.network.model.RequestId;
import org.openqa.selenium.devtools.v113.network.model.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LaunchBenePay {

	public static void main(String[] args) throws InterruptedException, IOException {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Documents\\externalFiles/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();

		options.addArguments("--remote-allow-origins=*");

		 final ChromeDriver driver = new ChromeDriver(options);

		 
		driver.manage().window().maximize();

		DevTools devtools = driver.getDevTools();
		devtools.createSession();
		devtools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devtools.addListener(Network.responseReceived(), response ->{
			try {
			String responseUrl = response.getResponse().getUrl();
			Headers header = response.getResponse().getHeaders();
			RequestId requestId = response.getRequestId();
			if(responseUrl.contains("v2/uploadpaymentfile") && !header.containsKey("OPTIONS")){
				System.out.println("Url: " + responseUrl);
				Network.GetResponseBodyResponse responseBody= devtools.send(Network.getResponseBody(requestId));
				String body = responseBody.getBody();
				if (body != null) {
				System.out.println("Response body: " + body);
				String urlvalue = body.substring(287, 356);
				System.out.println(urlvalue);
				driver.findElement(By.cssSelector("Body")).sendKeys(Keys.CONTROL + "t");
				driver.get(urlvalue);
			   } else {
		                System.out.println("Error fetching response body: " + body);
		            }
			}
			} catch (Exception e) {
		        System.err.println("An error occurred: " + e.getMessage());
		        e.printStackTrace();
		    }
		});
		
		driver.get("https://uat-collect-dashboard.benepay.io");

		System.out.println("Title Of The Application Is :" + driver.getTitle());

		driver.findElement(By.name("username")).sendKeys("anurag@benepay.io");
		driver.findElement(By.name("password")).sendKeys("Collect@12345");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(12000);

		String currentUrl = driver.getCurrentUrl();

		if (currentUrl.contains("home")) {
			System.out.println("Login Test Is Passed");
		} else {
			WebElement failed = driver.findElement(By.xpath("//div[@role='alert']/div[@class='amplify-alert__body']"));
			System.out.println("Login Test Is Failed : " + failed.getText());
		}

		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//span[@class='MuiTypography-root MuiListItemText-primary"
				+ " MuiTypography-body1' and text()='Upload File']")).click();

		Thread.sleep(8000);

//		 driver.findElement(By.cssSelector("input[value='Refund']")).click();	
//		 Thread.sleep(7000);

//		driver.findElement(By.cssSelector("input[value='payment']")).click();

		driver.findElement(By.cssSelector("#drop-area > form > label > i")).click();
		Runtime.getRuntime().exec("C:\\Users\\User\\Documents\\seleniumdata\\fileuplaod.exe");

		WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(15))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='Toastify']/div/div/div[1]")));
		String toastmsg = toast.getText();
//		System.out.println(toastmsg);

		if (toastmsg.contains("Your file validation is successful")) {

			System.out.println("File Upload Succesfully");

			WebElement row1Data = driver.findElement(By.xpath("//table[@class='MuiTable-root']/tbody/tr[1]"));
			String rowD = row1Data.getText();
			System.out.println(rowD);

		} else {

			System.out.println("File Upload Failed! Errors Below ");

			List<WebElement> error = driver.findElements(By.xpath("//div[@class='error-box mt-4']/table/tbody/tr"));
			for (WebElement err : error) {
				System.out.println(err.getText());
			}
			Thread.sleep(6000);
			
			WebElement merchant = driver.findElement(By.xpath("//p[@class='mt-1 my-3 pl-3  cursor-pointer text-capitalize']"));
			System.out.println("Login Merchant is : " + merchant.getText());

			driver.findElement(By.xpath("//div[@class='error-box mt-4']/button")).click();

		}

		driver.close();

	}

}
