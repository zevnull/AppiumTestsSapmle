package com.utils;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class CommonUtils {

    private static Logger logger = Logger.getLogger(CommonUtils.class);

    static public boolean isElementPresent(WebDriver driver, By by) {

        return driver.findElements(by).size() > 0;

    }

    public static void swipe(WebDriver driver, int startX, int startY, int endX, int endY, int duration) {
        logger.info("Swiping  {" + startX + "," + startY + "} - {" + endX + "," + endY + "}");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Integer> swipeObject = new HashMap<String, Integer>();
        swipeObject.put("startX", startX);
        swipeObject.put("startY", startY);
        swipeObject.put("endX", endX);
        swipeObject.put("endY", endY);
        swipeObject.put("duration", duration);
        js.executeScript("mobile: swipe", swipeObject);
        logger.info("swipe - finished.");
    }


    public static void flick(WebDriver driver, int endX, int endY, int touchCount) {
        logger.info("Flicking to {" + endX + "," + endY + "}");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Integer> flickObject = new HashMap<String, Integer>();
        flickObject.put("endX", endX);
        flickObject.put("endY", endY);
        flickObject.put("touchCount", touchCount);
        js.executeScript("mobile: flick", flickObject);
        logger.info("flick - finished.");

    }
}