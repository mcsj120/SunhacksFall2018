package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Automated {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver","lib/chromedriver.exe");
		WebDriver browser = new ChromeDriver();
		
	    browser.get("https://www.google.com");
	    WebElement header = browser.findElement(By.id("gsr"));
	    System.out.println(header.toString());
	    
	    browser.close();        

	}

}
