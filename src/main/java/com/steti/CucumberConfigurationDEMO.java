package com.steti;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-demo.properties")
@ComponentScan(basePackages = {"com.steti.*"})
@Profile("demo")
public class CucumberConfigurationDEMO {
}
