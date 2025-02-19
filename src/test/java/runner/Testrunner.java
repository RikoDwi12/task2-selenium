package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resource/feature", 
    glue = {"stepdefinition"},
    plugin = {"pretty",
              "html:target/cucumber-report.html",
              "json:target/cucumber-report.json",
              "junit:target/cucumber-report.xml"}
)

public class Testrunner {

}
