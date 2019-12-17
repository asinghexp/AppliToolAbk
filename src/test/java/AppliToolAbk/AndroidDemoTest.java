package AppliToolAbk;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.applitools.eyes.LogHandler;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.images.Eyes;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;


public class AndroidDemoTest extends BaseTest {
	protected AndroidDriver<AndroidElement> driver = null;
	
	Eyes eyes = new Eyes();
	
	@BeforeMethod
	@Parameters("deviceQuery")
	public void setUp(@Optional("@os='android'") String deviceQuery) throws Exception{
		init(deviceQuery);
		dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.akbank.android.apps.akbank_direkt");
		dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.akbank.akbankdirekt.ui.prelogin.SplashActivity");
		dc.setCapability("testName", "AndroidDemoTest");
		dc.setCapability("automationName", "UiAutomator2");

		driver = new AndroidDriver<>(new URL(getProperty("url",cloudProperties) + "/wd/hub"), dc);
		
		eyes.setApiKey(getProperty("apiKey" , cloudProperties));
		//eyes.setLogHandler(new StdoutLogHandler());
		
		eyes.setHostOS("Android");
        eyes.setHostApp("My App");

		eyes.open("appName","testName_2");
	}
	
	@Test
	public void test() throws InterruptedException, IOException{
		
		driver.findElement(By.xpath("//*[@text='Devam']")).click();
		EyesCheck("Devam");
		
		driver.findElement(By.xpath("//*[@text='Tamam']")).click();
		EyesCheck("Tamam");
		
		//eyes.checkWindow();
		
		/*driver.findElement(By.xpath("//*[@id='bg_circular' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@text='Döviz\n" + 
				"kurları']]]")).click();*/
		driver.findElement(By.xpath("//*[@id='item2']")).click();
		
		EyesCheck("kurları");
		
		//driver.findElement(By.xpath("//*[@id='bg_circular' and ./parent::*[./parent::*[./parent::*[@id='item2']]]]")).click();
		driver.findElement(By.xpath("//*[@id='currency_type_top_dropdown']")).click();
		
		EyesCheck("Currency");
		
		driver.findElement(By.xpath("(//*[@id='choose_currency_list']/*[@class='android.view.ViewGroup' and ./*[@id='bottom_sheet_currency_icon'] and ./*[@id='bottom_sheet_currency_sell']])[2]")).click();
		driver.findElement(By.xpath("//*[@id='input_before_comma' and ./parent::*[./parent::*[@id='money_amount_top']]]")).sendKeys("34");
		driver.findElement(By.xpath("//*[@id='input_after_comma' and ./parent::*[./parent::*[@id='money_amount_top']]]")).sendKeys("68");
		
		
		driver.findElement(By.xpath("//*[@text='Satış']")).click();
		EyesCheck("Satış");
		
		driver.findElement(By.xpath("//*[@id='currency_type_top_dropdown']")).click();
		EyesCheck("Currency");
		
		driver.findElement(By.xpath("(//*[@id='choose_currency_list']/*[@class='android.view.ViewGroup' and ./*[@id='bottom_sheet_currency_icon'] and ./*[@id='bottom_sheet_currency_sell']])[3]")).click();
		EyesCheck("Currency");
		
		driver.findElement(By.xpath("//*[@id='input_before_comma' and ./parent::*[./parent::*[@id='money_amount_top']]]")).clear();
		driver.findElement(By.xpath("//*[@id='input_after_comma' and ./parent::*[./parent::*[@id='money_amount_top']]]")).clear();
		
		driver.findElement(By.xpath("//*[@id='input_before_comma' and ./parent::*[./parent::*[@id='money_amount_top']]]")).sendKeys("12");
		driver.findElement(By.xpath("//*[@id='input_after_comma' and ./parent::*[./parent::*[@id='money_amount_top']]]")).sendKeys("24");
		driver.findElement(By.xpath("//*[@id='btn_buy_or_sel_currency']")).click();
		EyesCheck("Currency");
		
		
		driver.findElement(By.xpath("//*[@id='input_text' and ./parent::*[./parent::*[@id='customer_number_input']]]")).sendKeys("9321234");
		driver.findElement(By.xpath("//*[@id='input_text' and ./parent::*[./parent::*[@id='password_input']]]")).click();
		EyesCheck("Wrong PWD");
		driver.findElement(By.xpath("//*[@id='input_text' and ./parent::*[./parent::*[@id='password_input']]]")).sendKeys("123456");
		Thread.sleep(10000);
		driver.findElement(By.xpath("//*[@text='TAMAM']")).click();
		EyesCheck("Logged In");
	}

	private void EyesCheck(String tag) throws IOException {
		File imgFile = driver.getScreenshotAs(OutputType.FILE);
		
		FileUtils.copyFile(imgFile, new File("Screenshot.png"));
		
		BufferedImage img = ImageIO.read(new File("Screenshot.png"));
		
        eyes.checkImage(img, tag);
	}
	
	@AfterMethod
	public void tearDown(){
		eyes.close(false);
		driver.closeApp();
		driver.quit();
	}
	
}
