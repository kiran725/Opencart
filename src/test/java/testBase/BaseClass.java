package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.logging.log4j.LogManager;//log4j
//import org.apache.logging.log4j.Logger; //log4j
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public static Logger logger;
	public Properties p;

	@BeforeClass(groups = { "Master", "Regression", "Sanity" })
	@Parameters({ "os", "browser" })
	public void setup(String os, String br) throws IOException {

		// logger = LogManager.getLogger(this.getClass());// Log4j
		FileReader file = new FileReader(".//Configuration//config.properties");
		p = new Properties();
		p.load(file);

		if (br.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (br.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (br.equals("edge")) {
			driver = new EdgeDriver();
		} else if (br.equals("safari")) {
			driver = new SafariDriver();
		}

		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(p.getProperty("appURL")); // getting URL from properties file
		driver.manage().window().maximize();

		logger = Logger.getLogger("opencart");
		PropertyConfigurator.configure("Log4j.properties");

	}

	@AfterClass(groups = { "Master", "Regression", "Sanity" })
	public void tearDown() {
		driver.quit();
	}

	public String randomstring() {
		String genstring = RandomStringUtils.randomAlphabetic(5);
		return genstring;
	}

	public String randomNumber() {
		String gennumber = RandomStringUtils.randomNumeric(10);
		return gennumber;
	}

	public String randomAlphaNumber() {
		String genstring = RandomStringUtils.randomAlphabetic(3);
		String gennumber = RandomStringUtils.randomNumeric(2);
		return (genstring + "@" + gennumber);
	}

	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "//screenshots//" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);

		sourceFile.renameTo(targetFile);

		return targetFilePath;

	}

}

