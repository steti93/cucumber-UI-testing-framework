package com.steti.core.utils;

import org.openqa.selenium.WebElement;

public class PageObjectUtils {

    public static boolean isInitialized(WebElement element) {
        boolean value = false;
        try {
            value = element.isDisplayed();
        } catch (Exception e) {
            System.out.println("Page is not initialized");
        }
        return value;
    }
}
