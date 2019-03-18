package com.steti.pages.elements;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Slider {


    private JavascriptExecutor js;

    private WebElement element;

    private Actions action;

    public Slider(WebElement element, WebDriver driver) {
        this.element = element;
        this.js = (JavascriptExecutor) driver;
        this.action = new Actions(driver);
    }

    public void moveBy(int percent) {
        action.click(element).build().perform();
        Keys key = percent > 0 ? Keys.ARROW_RIGHT : Keys.ARROW_LEFT;
        percent = Math.abs(percent);
        for (int i = 0; i < percent; i++) {
            action.sendKeys(key).build().perform();
        }
    }

    public String getValue() {
        return (String) js.executeScript("return arguments[0].style.left", element);
    }

}
