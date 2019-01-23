package com.steti.core.webdriverUtils;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
    protected WebDriver webDriver;

    protected abstract void startService();

    protected abstract void endService();

    protected abstract void createDriver();

    public void quitDriver() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }

    }

    public WebDriver getDriver() {
        if (webDriver == null) {
            startService();
            createDriver();
        }
        return webDriver;
    }
}
