package automation;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class Automated {

	public static String automatedETrade(String stock, String quantity, String type) throws InterruptedException {
		
		// Chrome Driver details
		System.setProperty("webdriver.chrome.driver","lib/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");						// Comment out to show browser
		WebDriver driver = new ChromeDriver(options);
		int pageLoadTimeout = 20;
		driver.manage().timeouts().implicitlyWait(pageLoadTimeout, TimeUnit.SECONDS);
		
		// Variable declarations
		JavascriptExecutor js = (JavascriptExecutor)driver;
		Scanner input = new Scanner(System.in);
		String username;
		String password;
		String retVal = null;
		
		// Login details
		username = "mcstonge120";
		password = "PPanda2BePopping";
		
		// Open E*Trade and login
		driver.get("https://www.etrade.com");
		WebElement login = driver.findElement(By.xpath("/html/body/header/nav/div[2]/div/span[2]/a[1]"));
		login.click();
		WebElement usernameField = driver.findElement(By.id("user_orig"));
		WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"log-on-form\"]/div[1]/div[2]/div/input"));
		WebElement  submitButton = driver.findElement(By.xpath("//*[@id=\"log-on-form\"]/div[3]/div/div/button"));
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		submitButton.submit();
		
		// Navigate to Stock Page
		WebElement stockPage = driver.findElement(By.xpath("//*[@id=\"new-nav-layout\"]/div[3]/div[3]/div[2]/div/ul/li[1]/a"));
		js.executeScript("arguments[0].click()", stockPage);
		
		// Place Order
		WebElement stockSymbol = driver.findElement(By.xpath("//*[@id=\"symbol\"]"));
		stockSymbol.sendKeys(stock + "\n");
		Thread.sleep(1000);
		WebElement stockQuantity = driver.findElement(By.xpath("//*[@id=\"quantity\"]"));
		stockQuantity.sendKeys(quantity);
		Thread.sleep(1000);
		Select stockAction = new Select(driver.findElement(By.xpath("//*[@id=\"ordertype\"]")));
		
		if(type.equals("buy"))
		{
			stockAction.selectByValue("2");
		}
		else
		{
			stockAction.selectByValue("3");
		}
		
		Thread.sleep(1000);
		Select stockPriceType = new Select(driver.findElement(By.xpath("//*[@id=\"pricetype\"]")));
		stockPriceType.selectByValue("market");
		Thread.sleep(1000);
		Select stockDuration = new Select(driver.findElement(By.xpath("//*[@id=\"term\"]")));
		stockDuration.selectByVisibleText("Good for Day");
		Thread.sleep(1000);
		WebElement stockAllOrNone = driver.findElement(By.xpath("//*[@id=\"etContent\"]/div/div[1]/div/div/div[3]/section/div[1]/div/div/div/article/div/form/div[4]/div[2]/div/label"));
		stockAllOrNone.click();
		
		// Preview Order
		WebElement stockName = driver.findElement(By.xpath("//*[@id=\"symbView\"]/tbody/tr/td[1]"));
		WebElement pricePerStock = driver.findElement(By.xpath("//*[@id=\"snapshotView\"]/div[1]/div[1]/table/tbody/tr[2]/td"));
		WebElement estimatedCommission = driver.findElement(By.xpath("//*[@id=\"etContent\"]/div/div[1]/div/div/div[3]/section/div[1]/div/div/div/article/div/form/div[5]/div/div[2]/span[1]/span[2]"));
		WebElement estimatedTotalCost = driver.findElement(By.xpath("//*[@id=\"etContent\"]/div/div[1]/div/div/div[3]/section/div[1]/div/div/div/article/div/form/div[5]/div/div[2]/span[2]/span[2]"));
		
		retVal = ("Price per Stock of " + stockName.getText() + ": $" + pricePerStock.getText())+'\n'+
		("Units of " + stockName.getText() + " Stock: " + quantity)+'\n'+
		("Estimated Commmission: " + estimatedCommission.getText())+'\n'+
		("Estimated Total: " + estimatedTotalCost.getText());

	    driver.quit();
	    input.close();
	    return retVal;
	}
	
	public static String automatedMarketWatch(String stock) throws InterruptedException
	{
		// Chrome Driver details
		System.setProperty("webdriver.chrome.driver","lib/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");						// Comment out to show browser
		WebDriver driver = new ChromeDriver(options);
		int pageLoadTimeout = 20;
		driver.manage().timeouts().implicitlyWait(pageLoadTimeout, TimeUnit.SECONDS);
		
		// Variable declarations
		JavascriptExecutor js = (JavascriptExecutor)driver;
		Scanner input = new Scanner(System.in);
		String username;
		String password;

		// Login details
		username = "17jafarn@gmail.com";
		password = "sunhacks2018";
		
		// Stock to find
		System.out.println("Insert Stock Symbol: ");
		//String stock = input.nextLine();
		
		// Open MarketWatch and login
		driver.get("https://www.marketwatch.com/");
		WebElement login = driver.findElement(By.xpath("/html/body/header/div[1]/div[1]/div[3]/div/a[2]"));
		js.executeScript("arguments[0].click();", login);
		WebElement usernameField = driver.findElement(By.id("username"));
		WebElement passwordField = driver.findElement(By.id("password"));
		WebElement  submitButton = driver.findElement(By.xpath("//*[@id=\"basic-login\"]/div[1]/form/div/div[6]/div[1]/button"));
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		submitButton.submit();
		
		// Find Stock and return price
	    driver.get("https://www.marketwatch.com/investing/stock/" + stock);
	    WebElement price = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div/div[2]/h3/bg-quote"));
	    
	    driver.close();
	    input.close();
	    
	    return price.getText();
	}

}
