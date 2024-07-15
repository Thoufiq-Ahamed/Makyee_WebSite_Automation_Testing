package com.tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;

public class FavoriteFeature { // This Program demonstrates that user can select the property and make it
								// favorite.
	WebDriver driver;
	Actions actions;

	@Test(priority = 1, dataProvider = "loginCredentials")
	public void Login(String Username, String Password) throws InterruptedException { // This Function demonstrates a
																						// login functionality.
		// Navigate to Login page
		driver.navigate().to("https://makyee.com/login-with-email-address.html");

		// Sending Credentials to appropriate text boxes. Credentials get from Data
		// Provider.
		driver.findElement(By.id("UserLogin_email")).sendKeys(Username);
		driver.findElement(By.id("UserLogin_password")).sendKeys(Password);
		Thread.sleep(3000);

		// Click Login / Sign-in
		driver.findElement(By.id("bb")).click();
		Thread.sleep(3000);

		// After logged in, verify whether it redirects to Dashboard Page. If not, it
		// throws an Assertion Error with message.
		String actualURL = driver.getCurrentUrl();
		String expectedURL = "https://makyee.com/dashboard.html";
		if (!actualURL.equalsIgnoreCase(expectedURL)) {
			Reporter.log("Login Unsuccessful !!!");
			Assert.assertTrue(false);
		}
		Reporter.log("Login Successful !!!");
	}

	@DataProvider
	public Object[][] loginCredentials() {
		Object[][] creds = new Object[1][2];
		creds[0][0] = "thoufiqgulf@gmail.com";
		creds[0][1] = "Thoufiq8998@";
		return creds;
	}

	@Test(priority = 2)
	public void OffPlanFavoriteFunction() throws InterruptedException, AWTException {
		/*
		 * Creating Action Class for doing Mouse and Keyboard activities. Here i am
		 * doing that visiting the web site and do the favorite Feature activities which
		 * listed below.
		 */
		driver.navigate().to("https://makyee.com");
		Thread.sleep(2000);

		actions = new Actions(driver);

		/*
		 * Step 1: Click on Search Box and enter Al Barsha as we are going to click the
		 * Favorite Button in one of the properties of Al Barsha.
		 */
		WebElement SearchBox = driver.findElement(By.id("searchInputHeader"));
		actions.moveToElement(SearchBox).click().sendKeys("Al Barsha").build().perform();

		// Step 2: Select from list of option. Here, we are going to select Al Barsha.
		// Not any others like Al Barsha South and so on.
		WebElement propertySelector = driver
				.findElement(By.xpath("//li[@class='w-100 mb-1']/a[@data-value='al-barsha']"));
		actions.moveToElement(propertySelector).click().build().perform();

		// Step 3: Click on search button.
		WebElement SearchButton = driver.findElement(By.xpath("//*[@id=\"respMenu\"]/li[1]/div/div/span[1]/img"));
		actions.moveToElement(SearchButton).click().build().perform();

		/*
		 * Step 4: Here, Favorite Button available only on Off-Plan Properties, not on
		 * Ready Property. So, First, We select one of the Al Barsha's Property which is
		 * Off-Plan, named as "Aqua Dimore". Under Aqua Dimore, we are going to select
		 * "AD-321". After selecting the property and get the property details page, we
		 * are going to click the Favorite.
		 */

		// Clicking Aqua Dimore's Buy button
		driver.findElement(By.xpath("//*[@id=\"listings\"]/section[2]/div/div/div[2]/div/table/tbody/tr[2]/td[6]/a"))
				.click();

		// Clicking AD-321's Buy Button
		driver.findElement(By.xpath("//*[@id=\"listings\"]/section[2]/div/div/div[2]/div/table/tbody/tr[1]/td[8]/a"))
				.click();

		// Clicking Favorite Icon based on the condition whether the element is present
		// or not. If not, it throws an Assertion Error.
		WebElement FavoriteIcon = driver.findElement(By.xpath("//*[@id=\"Paris\"]/div[4]/div[1]"));
		if (FavoriteIcon.isDisplayed())
			FavoriteIcon.click();
		else {
			Reporter.log("Favorite Icon is not present. So, not able to add to Favorite");
			Assert.assertTrue(false);
		}

		// Verify whether the Favorited property is same or not
		Robot robot = new Robot();
		robot.mouseMove(1400, 190);
		Thread.sleep(2000);
		robot.mouseMove(1400, 300);
		Thread.sleep(1000);

		// Clicking on Favorited Properties from Profile Drop Down on the top-right
		// corner.
		WebElement FavoriteProperties = driver.findElement(By.xpath("//*[@id=\"yes_userli\"]/div/div/ul/li[3]/a"));
		actions.moveToElement(FavoriteProperties).click().build().perform();

		// Validate whether the favorited property is same or it contains nothing.
		try {
			WebElement FavoritePropertyName = driver.findElement(By.xpath(
					"//*[@id=\"wrapper\"]/div[3]/div/div/div[1]/div[3]/div/div/div/div/div/div/div[2]/div/div/div/div[2]/div[1]/div[2]/span"));
			String ExpectedFavoritePropertyName = "AD-321";
			if (FavoritePropertyName.isDisplayed()) {
				String ActualFavoritePropertyName = FavoritePropertyName.getText();
				if (ActualFavoritePropertyName.equalsIgnoreCase("@" + ExpectedFavoritePropertyName))
					Reporter.log("The Favorited property is same as we marked. Your Favorite Property is: "+ActualFavoritePropertyName);
			}
		} catch (NoSuchElementException e) {
			Reporter.log(
					"There is no favorited property yet. This is because either you are not mark properties as Favorites till now or you unfavorite all the properties you have !!! ");
		}
	}

	@Test(priority = 3)
	public void ReadyFavoriteFunction() throws InterruptedException {
		/*
		 * Creating Action Class for doing Mouse and Keyboard activities. Here i am
		 * doing that visiting the web site and do the favorite Feature activities which
		 * listed below.
		 */
		driver.navigate().to("https://makyee.com");
		Thread.sleep(2000);

		actions = new Actions(driver);

		/*
		 * Step 1: Click on Search Box and enter Al Barsha as we are going to try to
		 * click the Favorite Button in one of the properties of Al Barsha.
		 */
		WebElement SearchBox = driver.findElement(By.id("searchInputHeader"));
		actions.moveToElement(SearchBox).click().sendKeys("Al Barsha").build().perform();

		// Step 2: Select from list of option. Here, we are going to select Al Barsha.
		// Not any others like Al Barsha South and so on.
		WebElement propertySelector = driver
				.findElement(By.xpath("//li[@class='w-100 mb-1']/a[@data-value='al-barsha']"));
		actions.moveToElement(propertySelector).click().build().perform();

		// Step 3: Click on search button.
		WebElement SearchButton = driver.findElement(By.xpath("//*[@id=\"respMenu\"]/li[1]/div/div/span[1]/img"));
		actions.moveToElement(SearchButton).click().build().perform();

		/*
		 * Step 4: Here, Favorite Button is not available on Ready Properties. So, we
		 * are going to check and return the error message. So, First, We select one of
		 * the Al Barsha's Property which is Ready, named as "Al Barsha South". Under Al
		 * Barsha South, we are going to select "Al Barsha South Fourth" ->
		 * "LOCI Residences" -> "LOCI Residences". After selecting the property and get
		 * the property details page, we are going to check whether the favorite button
		 * is not present.
		 */

		// Clicking Al Barsha South's Buy button
		driver.findElement(By.xpath("//*[@id=\"listings\"]/section[2]/div/div/div[2]/div/table/tbody/tr[1]/td[6]/a"))
				.click();

		// Clicking Al Barsha South Fourth's Buy Button
		driver.findElement(By.xpath("//*[@id=\"listings\"]/section[2]/div/div/div[2]/div/table/tbody/tr/td[6]/a"))
				.click();

		// Clicking LOCI Residences's Buy Button
		driver.findElement(By.xpath("//*[@id=\"listings\"]/section[2]/div/div/div[2]/div/table/tbody/tr/td[6]/a"))
				.click();

		// Clicking LOCI Residences Propery's Buy Button
		driver.findElement(By.xpath("//*[@id=\"listings\"]/section[2]/div/div/div[2]/div/table/tbody/tr/td[8]/a"))
				.click();

		// Clicking Favorite Icon based on the condition whether the element is present
		// or not. If not, it throws an Assertion Error.
		try {
			boolean isFavoriteIcon = driver.findElement(By.xpath("//*[@id=\"Paris\"]/div[4]/div[1]")).isDisplayed();
			if (isFavoriteIcon)
				driver.findElement(By.xpath("//*[@id=\"Paris\"]/div[4]/div[1]")).click();
		} catch (NoSuchElementException e) {
			Reporter.log("Favorite Icon is not present in any of the Ready Properties. So, not able to add to Favorite");
			Assert.assertTrue(false, "Favorite Icon is not present. So, not able to add to Favorite");
		}

		/*
		 * Verification of Favorite Page is not needed here because, for Ready
		 * Properties, there is no Favorite Button. So, without this, automatically
		 * there is no properties. If you choose a property as a favorite from Off-Plan,
		 * that only will be shown.
		 */
	}

	@SuppressWarnings("deprecation")
	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://makyee.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterTest
	public void afterTest() throws InterruptedException {
		Thread.sleep(5000);
		driver.quit();
	}

}
