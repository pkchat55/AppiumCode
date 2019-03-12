package com.amc.paytm;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;


public class MobileRecharge {

	AppiumDriver<MobileElement> driver;
	File appDir = new File("C:\\Users\\User\\udemy\\EndToEndApps\\apps");
	File app = new File(appDir, "Paytm_4.5.0.apk");


	//Appium Server varaibles
	AppiumDriverLocalService appiumServer;
	File nodeLocation= new File ("C:\\Program Files\\nodejs\\node.exe");
	File appiumServerLocation= new File ("C:\\Program Files (x86)\\Appium\\node_modules\\appium\\bin\\appium.js");
	String serverIPorName="127.0.0.1";
	int serverPortNumber=4733;

	@BeforeSuite
	public void startAppiumServer()
	{

		appiumServer=AppiumDriverLocalService.buildService(new AppiumServiceBuilder()

		.usingDriverExecutable(nodeLocation)
		.withAppiumJS(appiumServerLocation)
		.withIPAddress(serverIPorName)
		.usingPort(serverPortNumber));
		
		appiumServer.start();
		if(appiumServer.isRunning())
		System.out.println("Appium Server Started");
		
	}



	@BeforeTest
	public void SetCapability() throws MalformedURLException
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();	

		capabilities.setCapability("deviceName","192.168.0.7:5555");
		//capabilities.setCapability("app", app.getAbsolutePath());	
		capabilities.setCapability("appPackage", "net.one97.paytm");
		capabilities.setCapability("appActivity", ".AJRJarvisSplash");


		driver = new AndroidDriver<MobileElement>(new URL("http://"+serverIPorName+":"+serverPortNumber+"/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}

	@Test
	public void MobileRechange() throws InterruptedException
	{

		if(!driver.findElementById("net.one97.paytm:id/radio_postpaid").isSelected())
			driver.findElementByName("Postpaid").click();

		/*driver.findElementById("net.one97.paytm:id/lyt_edit_no").sendKeys("9018090180");
		//Thread.sleep(4000);
		driver.findElementById("net.one97.paytm:id/lyt_mob_op").click();
		driver.scrollTo("Reliance GSM").click();
		driver.scrollTo("Punjab").click();
		driver.findElementById("net.one97.paytm:id/lyt_edit_amount").sendKeys("100");*/


		MobileElement list=driver.findElementById("android:id/list");
		List<MobileElement> Editbox=list.findElementsByClassName("android.widget.EditText");

		Editbox.get(0).sendKeys("9018090180");
		driver.findElementById("net.one97.paytm:id/lyt_mob_op").click();
		driver.scrollTo("Reliance GSM").click();
		driver.scrollTo("Punjab").click();
		Editbox.get(1).sendKeys("100");



		if(!driver.findElementById("net.one97.paytm:id/radio_fast_forward").isSelected())
			driver.findElementById("net.one97.paytm:id/radio_fast_forward").click();
	}


	
	@AfterSuite
	public void stopAppiumServer()
	{
		appiumServer.stop();
		
		if(!appiumServer.isRunning())
			System.out.println("Appium Server stopped");
		
	}


}
