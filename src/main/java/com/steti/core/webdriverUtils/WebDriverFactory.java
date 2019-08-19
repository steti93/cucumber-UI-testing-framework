package com.steti.core.webdriverUtils;

import com.steti.core.dataKeys.PageKeys;
import com.steti.core.utils.ScenarioContext;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class WebDriverFactory {

    @Autowired
    private ScenarioContext scenarioContext;

    protected static WebDriver driver;

    private static final Map<DriverType, Supplier<WebDriver>> driverMap = new HashMap<>();

    private static final Map<DriverType, Supplier<WebDriver>> driverMapLinux = new HashMap<>();

    private static final Supplier<WebDriver> chromeDriverSuplier = () -> {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    };

    private static final Supplier<WebDriver> firefoxDriverSuplier = () -> {
        WebDriverManager.firefoxdriver().setup();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        return new FirefoxDriver();
    };

    private static final Supplier<WebDriver> headLessChromeDriver = () -> {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    };

    private static final Supplier<WebDriver> headLessFirefox = () -> {
        WebDriverManager.firefoxdriver().setup();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        return new FirefoxDriver(firefoxOptions);
    };
    private static final Supplier<WebDriver> internetExplorerDriver = () -> {
        WebDriverManager.iedriver().setup();
        return new InternetExplorerDriver();
    };

    private static final Supplier<WebDriver> chromeDriverSuplierLinux = () -> {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    };

    private static final Supplier<WebDriver> firefoxDriverSuplierLinux = () -> {
        WebDriverManager.firefoxdriver().setup();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        return new FirefoxDriver();
    };

    private static final Supplier<WebDriver> headLessChromeDriverLinux = () -> {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        return new ChromeDriver(chromeOptions);
    };


    private static final Supplier<WebDriver> headLessFirefoxLinux = () -> {
        WebDriverManager.firefoxdriver().setup();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        return new FirefoxDriver(firefoxOptions);
    };

    static {
        driverMap.put(DriverType.CHROME, chromeDriverSuplier);
        driverMap.put(DriverType.FIREFOX, firefoxDriverSuplier);
        driverMap.put(DriverType.CHHEADLESS, headLessChromeDriver);
        driverMap.put(DriverType.IE, internetExplorerDriver);
        driverMap.put(DriverType.FXHEADLESS, headLessFirefox);
        driverMapLinux.put(DriverType.CHROME, chromeDriverSuplierLinux);
        driverMapLinux.put(DriverType.FIREFOX, firefoxDriverSuplierLinux);
        driverMapLinux.put(DriverType.CHHEADLESS, headLessChromeDriverLinux);
        driverMapLinux.put(DriverType.FXHEADLESS, headLessFirefoxLinux);
    }

    private static WebDriver getDriverManager(DriverType driverType) {
        return driverMap.get(driverType).get();
    }

    private static final WebDriver getDriver() {
        String driverType;
        if (driver == null) {
            try {
                driverType = System.getProperty("browser").toUpperCase();
                driver = getDriverManager(DriverType.valueOf(driverType));
            } catch (NullPointerException e) {
                driver = getDriverManager(DriverType.FIREFOX);
            }
        }
        return driver;
    }

    private static WebDriver getDriverManagerLinux(DriverType driverType) {
        return driverMapLinux.get(driverType).get();
    }

    private static final WebDriver getDriverLinux() {
        String driverType;
        if (driver == null) {
            try {
                driverType = System.getProperty("browser").toUpperCase();
                driver = getDriverManagerLinux(DriverType.valueOf(driverType));
            } catch (NullPointerException e) {
                driver = getDriverManagerLinux(DriverType.FIREFOX);
            }
        }
        return driver;
    }

    public WebDriver setUp() {

        try {
            if (System.getProperty("os").equals("linux")) {
                driver = getDriverLinux();
            } else {
                driver = getDriver();
            }
        } catch (NullPointerException e) {
            driver = getDriver();
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Dimension dimension = new Dimension(1500, 1980);
        driver.manage().window().maximize();
        return driver;
    }

    public void closeDriver() {
        driver = (WebDriver) scenarioContext.getData(PageKeys.OPEN_DRIVER);
        driver.quit();
        driver = null;
    }
}
