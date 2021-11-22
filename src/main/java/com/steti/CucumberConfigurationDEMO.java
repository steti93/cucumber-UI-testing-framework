package com.steti;

import com.steti.core.utils.ScenarioContext;
import com.steti.core.webdriverUtils.WebDriverFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-demo.properties")
@Profile("demo")
public class CucumberConfigurationDEMO {

    @Bean
    public ScenarioContext scenarioContext() {
        return new ScenarioContext();
    }

    @Bean
    public WebDriverFactory webDriverFactory() {
        return new WebDriverFactory();
    }
}
