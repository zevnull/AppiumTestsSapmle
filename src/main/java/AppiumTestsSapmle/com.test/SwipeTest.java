package com.test;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SwipeTest {
    private Logger logger = Logger.getLogger(SwipeTest.class);

    private WebDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {

        logger.info("setUp - started.");

        File app = new File( "aut/ListView.apk" );
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability( "device", "Android" );
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(CapabilityType.PLATFORM, "Windows");
        capabilities.setCapability(CapabilityType.VERSION, "4.2");
        capabilities.setCapability( "app-package", "com.example.listview" );
        capabilities.setCapability( "app-activity", ".MainActivity" );
        driver = new RemoteWebDriver( new URL( "http://127.0.0.1:4723/wd/hub" ), capabilities );

        logger.info("setUp - finished.");

    }

    @AfterMethod
    public void tearDown() throws Exception {
        logger.info("Quit application.");
        driver.quit();
    }

    @Test
    public void swipeTest() {

        logger.info("Swipe listView");
        WebElement end = driver.findElements(By.className("android.widget.TextView")).get(10);
        logger.info("10 : " + end.getLocation().getX() + "," + end.getLocation().getY());

        WebElement start = driver.findElements(By.className("android.widget.TextView")).get(8);
        logger.info("8 : " + start.getLocation().getX() + "," + start.getLocation().getY());

        CommonUtils.swipe(driver, 10, end.getLocation().getY(), 10, start.getLocation().getY(), 4)


    }


}