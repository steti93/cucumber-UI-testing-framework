package com.steti.pages.pageObjects.demo;

import com.steti.core.utils.PageObjectUtils;
import com.steti.pages.abstractPage.AbstractPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FacebookHomePage extends AbstractPageObject {

    public FacebookHomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "fb_logo")
    private WebElement pageTitle;

    @FindBy(xpath = "//input[@name = 'firstname']")
    private WebElement firstName;

    @FindBy(xpath = "//input[@name = 'lastname']")
    private WebElement lastName;

    @FindBy(xpath = "//input[@name = 'reg_email__']")
    private WebElement regEmail;

    @FindBy(xpath = "//button[@name = 'websubmit']")
    private WebElement submit;

    @Override
    public boolean isInitialized() {
        return PageObjectUtils.isInitialized(pageTitle);
    }
}
