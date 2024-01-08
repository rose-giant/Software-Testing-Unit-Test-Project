package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features= "src/test/java/resource/removeItem.feature",
        glue = "src/test/java/BDDTest/UserRemoveItemTest.java"
)
public class TestRunner3 {

}
