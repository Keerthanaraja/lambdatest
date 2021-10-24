package co.keerthi.selenium.demo;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.TimeUnit;

public class LambdaTest {

	public static void main(String[] args) {
		System.out.println("Lambda test started..");

		try {
			SeleniumTestMainFunction();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Lambda test completed!");

	}

	private static void SeleniumTestMainFunction() throws InterruptedException, Exception {
		String imgPath = "C:\\Users\\Sharnie\\Videos\\";

		System.out.println("Initiate web driver and load URL");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Sharnie\\OneDrive\\Documents\\Sharnie\\chromedriver_win32\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.get("https://www.lambdatest.com/automation-demos");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		// login page
		driver.findElement(By.id("username")).sendKeys("lambda");
		driver.findElement(By.id("password")).sendKeys("lambda123");
		driver.findElement(By.cssSelector(".applynow")).click();

		// form page -- populate user
		driver.findElement(By.id("developer-name")).sendKeys("keerthana.yuva@gmail.com");
		driver.findElement(By.id("populate")).click();
		String alertText = driver.switchTo().alert().getText();
		System.out.println(alertText);
		driver.switchTo().alert().dismiss();
		// assertThat(driver.switchTo().alert().getText(),
		// is("keerthana.yuva@gmail.com"));

		// form fill
		driver.findElement(By.id("3months")).click();
		driver.findElement(By.id("customer-service")).click();
		driver.findElement(By.id("discounts")).click();

		driver.findElement(By.id("preferred-payment")).click();
		{
			WebElement dropdown = driver.findElement(By.id("preferred-payment"));
			dropdown.findElement(By.xpath("//option[. = 'Wallets']")).click();
		}

		driver.findElement(By.id("tried-ecom")).click();
		Thread.sleep(1000);

		// slider
		WebElement sliderElement = driver.findElement(By.xpath("//div[@class='mb-30']"));
		Actions moveSlider = new Actions(driver);
		Action action = moveSlider.dragAndDropBy(sliderElement, 240, 0).build();
		action.perform();

		// comments
		driver.findElement(By.id("comments")).sendKeys("form filled!");

		System.out.println("open page for image download..");
		String fileName = DownloadAndSaveImage(imgPath);
		System.out.println(fileName);

		WebElement addFile = driver.findElement(By.id("file"));
		addFile.sendKeys(imgPath + fileName);
		Thread.sleep(1000);

		// manage image submit alert
		alertText = driver.switchTo().alert().getText();
		System.out.println(alertText);
		driver.switchTo().alert().dismiss();

		driver.findElement(By.id("submit-button")).click();

		driver.quit();
	}

	private static String DownloadAndSaveImage(String imgPath) throws Exception {

		WebDriver driver = new ChromeDriver();
		driver.get("https://www.lambdatest.com/selenium-automation/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		// download jenkins image
		WebElement imgElement = driver.findElement(By.xpath("//img[@title='Jenkins']"));
		String logoSRC = imgElement.getAttribute("src");
		System.out.println(logoSRC);
		String fileName = FilenameUtils.getName(new URL(logoSRC).getPath());
		System.out.println(fileName);
		DownloadFile(logoSRC, (imgPath + fileName));
		Thread.sleep(1000);
		driver.close();
		return fileName;
	}

	private static void DownloadFile(String urlStr, String file) throws IOException {
		URL url = new URL(urlStr);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
	}

}
