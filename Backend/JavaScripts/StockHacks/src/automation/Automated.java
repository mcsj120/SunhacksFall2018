package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Automated {

	public static void main(String[] args) {

		WebDriver browser = new ChromeDriver();

	    //Firefox's proxy driver executable is in a folder already
	    //  on the host system's PATH environment variable.
	    browser.get("https://www.google.com");
	    WebElement header = browser.findElement(By.id("gsr"));
	    System.out.println(header.toString());

	    browser.close();        

	}

}
