package BDDTest;

import exceptions.InvalidCreditRange;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.util.ReflectionTestUtils;

public class UserAddCreditTest {
    User user = null;
    int amount;
    @Given("Execute AddCredit")
    public void execute_add_credit() {
        user = new User();
    }
    @When("If added amount is {int}")
    public void if_added_amount_is(Integer int1) {
        amount = int1;
    }
    @Then("Throw invalidCreditRange error")
    public void throw_invalid_credit_range_error() {
        Assertions.assertThrows(InvalidCreditRange.class, () -> {
            user.addCredit(amount);
        });
    }
    @Then("Increase the user credit by amount")
    public void increase_the_user_credit_by_amount() throws InvalidCreditRange {
        float initial_credit = (float) ReflectionTestUtils.getField(user, "credit");
        user.addCredit(amount);
        float new_credit = (float) ReflectionTestUtils.getField(user, "credit");
        Assertions.assertEquals(new_credit, initial_credit + amount);
    }
}
