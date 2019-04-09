package com.steti.hooks;


import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.steti.core.dataKeys.PageKeys;
import com.steti.core.logger.TestLogHelper;
import com.steti.core.utils.ScenarioContext;
import com.steti.core.webdriverUtils.WebDriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Properties;

public class GlobalStepHooks extends AbstractSpringTest {

    private static Logger logger = LoggerFactory.getLogger(GlobalStepHooks.class);

    public static final String SCREENSHOT_PATH = "target/logs/screenshots/";

    private WebDriver driver;

    @Autowired
    private ScenarioContext scenarioContext;

    @Autowired
    private WebDriverFactory webDriverFactory;

    @Before(order = -3)
    public void loggerConfiguration(Scenario scenario) {
        String scenarioNameFinal = scenario.getName().replaceAll("\\s+", "-");
        scenarioNameFinal = scenarioNameFinal.concat("-" + String.valueOf(LocalTime.now().toSecondOfDay()));
        TestLogHelper.startTestLogging(scenarioNameFinal);
        logger.info("Current testname value is : " + TestLogHelper.getCurrentLogName());
    }

    @Before(order = -2, value = "@qmetry")
    public void setCorrespondingConfigurationForQmetry(Scenario scenario) throws IOException {
        setPropertiesForQmetry("automation.qmetry.testrunname", scenario.getName());
        logger.info(String.format("Qmetry parrameter %s was set with value: %s", "automation.qmetry.testrunname", scenario.getName()));

        setPropertiesForQmetry("automation.qmetry.platform", System.getProperty("browser"));
        logger.info(String.format("Qmetry parrameter %s was set with value: %s", "automation.qmetry.platform", System.getProperty("browser")));

        String valueToBeSet = System.getProperty("qmetry");

        if (valueToBeSet == null) {
            valueToBeSet = "false";
        }

        setPropertiesForQmetry("automation.qmetry.enabled", valueToBeSet);
        logger.info(String.format("Qmetry parrameter %s was set with value: %s", "automation.qmetry.testrunname", valueToBeSet));
    }

    @Before(order = -1, value = "@UI")
    public void setUpDriver(Scenario scenario) {
        logger.info("[" + TestLogHelper.getCurrentLogName() + " ]: - " + "Opening Web driver");
        driver = webDriverFactory.setUp();
        scenarioContext.save(PageKeys.OPEN_DRIVER, driver);
        scenarioContext.save(PageKeys.SCENARIO_NAME, scenario.getName());
    }


    @After(value = "@UI")
    public void closeDriver(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            String imageName = scenario.getName().concat("_" + scenario.getStatus()).concat(String.valueOf(LocalDateTime.now().getNano()));
            Shutterbug.shootPage(driver)
                    .withName(imageName)
                    .save(SCREENSHOT_PATH);
            Path path = Paths.get(SCREENSHOT_PATH.concat(imageName).concat(".png"));
            byte[] screenshot = Files.readAllBytes(path);
            scenario.embed(screenshot, "image/png");
        }
        logger.info("[" + TestLogHelper.getCurrentLogName() + " ]: - " + "Closing Web driver...");
        webDriverFactory.closeDriver();
    }

    public static void setPropertiesForQmetry(String propertyName, String propertyValue) throws IOException {
        FileInputStream in = new FileInputStream("qmetry.properties");
        Properties props = new Properties();
        props.load(in);
        in.close();

        FileOutputStream out = new FileOutputStream("qmetry.properties");
        props.setProperty(propertyName, propertyValue);
        props.store(out, null);
        out.close();
    }

}
