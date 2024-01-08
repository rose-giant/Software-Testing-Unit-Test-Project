package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features= "src/test/java/resource/credit.feature",
        glue = "src/test/java/BDDTest/UserAddCreditTest.java"
)
public class TestRunner {

}
