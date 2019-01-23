package com.steti.jenkins;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@ExtendedCucumberOptions(
        jsonReport = "target/cucumber.json"
)
@CucumberOptions(
        format = {"pretty", "html:target/cucumber", "json:target/cucumber.json"},
        features = {"src/test/resources/features/new-version"},
        glue = {
                "com.steti.hooks",
                "com.steti.stepDef"},
        plugin = {
                "json:target/cucumber.json",},
        tags = {"~@ignore","@smoke_test"}
)
public class RunSmokeVersion {
}
