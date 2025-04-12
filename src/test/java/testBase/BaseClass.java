package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {

	protected WebDriver driver;
	public Logger logger; // Log4j instance
	public Properties p;

	@BeforeClass(groups = { "Sanity", "Regression", "Master" })
	@Parameters({ "os", "browser" })
	public void setup(String os, String br) throws IOException {

		// Load config.properties file
		String configPath = "." + File.separator + "src" + File.separator + "test" + File.separator + "resources"
				+ File.separator + "config.properties";

		try (FileReader file = new FileReader(configPath)) {
			p = new Properties();
			p.load(file);
		}

		logger = LogManager.getLogger(BaseClass.class); // Improved logger initialization

		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// os
			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else {
				System.out.println("No matchinng os");
			}

			// browser
			switch (br.toLowerCase()) {

			case "chrome":
				capabilities.setBrowserName("chrome");
				break;

			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;

			case "firefox":
				capabilities.setBrowserName("firefox");
				break;

			default:
				System.out.println("No matching browser");
				return;
			}
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}

		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
			// Browser selection
			switch (br.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default:
				System.out.println("Invalid browser name..");
				return;

			}
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// getDriver().get("https://tutorialsninja.com/demo/");
		getDriver().get(p.getProperty("appURL")); // reading url from properties file
		getDriver().manage().window().maximize();
	}

	@AfterClass(groups = { "Sanity", "Regression", "Master" })
	public void tearDown() {
		getDriver().quit();
	}

	// Generate random strings
	public String randomeString() {
		String generatedstring = RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
	}

	public String randomeNumber() {
		String generatednumber = RandomStringUtils.randomNumeric(10);
		return generatednumber;
	}

	public String randomeAlphaNumeric() {
		String generatedstring = RandomStringUtils.randomAlphabetic(3);
		String generatednumber = RandomStringUtils.randomNumeric(3);
		return (generatedstring + "@" + generatednumber);

	}

	public String captureScreen(String tname) throws IOException {
		try {
			// Create a timestamp for uniqueness
			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

			// Take Screenshot
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

			// Define screenshot directory
			String screenshotDir = System.getProperty("user.dir") + File.separator + "screenshots";
			File dir = new File(screenshotDir);
			if (!dir.exists()) {
				dir.mkdirs(); // Ensure the directory exists
			}

			// Define the target file path
			String targetFilePath = screenshotDir + File.separator + tname + "_" + timeStamp + ".png";
			File targetFile = new File(targetFilePath);

			// Copy file using Apache Commons IO
			FileUtils.copyFile(sourceFile, targetFile);

			System.out.println("Screenshot saved: " + targetFilePath);
			return targetFilePath;
		} catch (Exception e) {
			System.out.println("Screenshot capture failed: " + e.getMessage());
			return null;
		}

	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
