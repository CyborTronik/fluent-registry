package features;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by stanislav on 10/27/15.
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:src/test/resources/bdd_report/cucumber-report.json"})
public class FeaturesRunner {
}
