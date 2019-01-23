package com.steti.pages.abstractPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractPageObject implements AbstractPage {
    protected WebDriver driver;

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public String getPageLink(){
        return null;
    }
}
