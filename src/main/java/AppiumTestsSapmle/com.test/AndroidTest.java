package com.test;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Predicate;
import com.utils.CommonUtils;

public class AndroidTest {

    public static final String OK_EXPECTED = "ОК - clicked";

    public static final String CANCEL_EXPECTED = "Cancel - clicked";

    private Logger logger = Logger.getLogger(AndroidTest.class);

    private WebDriver driver;

    private static final By BTN_MAPS = By.xpath("//window[1]/button[3]");

    private static final By MAP = By.xpath("//window[1]/UIAMapView[1]");

    @BeforeMethod
    public void setUp() throws Exception {
        logger.info("setUp - started.");

        File app = new File( "app/test2.apk" );
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability( "device", "Android" );
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(CapabilityType.PLATFORM, "Windows");
        capabilities.setCapability(CapabilityType.VERSION, "4.2");
        capabilities.setCapability( "app-package", "com.example.test2" );
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
    public void okButtonTest() {

        logger.info("okButtonTest - started.");
        WebElement btn = driver.findElement(By.name("OK"));
        btn.click();
        WebElement result = driver.findElements(By.className("android.widget.TextView")).get(1);
        Assert.assertEquals(OK_EXPECTED,result.getText());
        logger.info("okButtonTest - finished.");

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10L);
        webDriverWait.until(new Predicate<WebDriver>() {
            public boolean apply(WebDriver webDriver) {
                int size = driver.findElements(By.className("android.widget.Button")).size();
                logger.info("From Predicate: " + size);
                return  size > 1;
            }
        });

    }

    @Test(dependsOnMethods = { "okButtonTest" })
    public void cancelButtonTest() {
        logger.info("cancelButtonTest - started.");
        WebElement btn = driver.findElement(By.name("Cancel"));
        btn.click();
        WebElement result = driver.findElements(By.className("android.widget.TextView")).get(1);
        Assert.assertEquals(CANCEL_EXPECTED,result.getText());
        logger.info("cancelButtonTest - finished.");
    }

    @Test(dependsOnMethods = { "cancelButtonTest" })
    public void testMovement() {
        logger.info("testMovement - started");

        WebElement testGesturesBtn = driver.findElement(BTN_MAPS);
        testGesturesBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(MAP));

        WebElement map = driver.findElement(MAP);

        final int mapX = map.getLocation().getX();
        final int mapY = map.getLocation().getY();
        Dimension size = map.getSize();

        CommonUtils.swipe(driver, mapX + size.width / 3, mapY + size.height / 3, mapX + size.width, mapY + size.height, 2);

        CommonUtils.flick(driver, 0, 0, 2);

        CommonUtils.swipe(driver, mapX, mapY, mapX + size.width, mapY + size.height, 5);

        logger.info("testMovement - finished");
    }
}