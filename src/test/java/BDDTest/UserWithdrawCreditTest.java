package BDDTest;

import exceptions.InsufficientCredit;

import exceptions.InvalidCreditRange;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.util.ReflectionTestUtils;

public class UserWithdrawCreditTest {
    User user = null;
    int withdrawAmount;
    @Given("Execute WithdrawCredit")
    public void execute_withdraw_credit() {
        user = new User();
    }
    @When("User credit is {int}")
    public void user_credit_is(Integer int1) {
        user.setCredit(int1);
    }
    @When("If wanted amount is {int}")
    public void if_wanted_amount_is(Integer int1) {
        withdrawAmount = int1;
    }
    @Then("Throw InsufficientCredit error")
    public void throw_insufficient_credit_error() throws InsufficientCredit {
        Assertions.assertThrows(InsufficientCredit.class, () -> {
            user.withdrawCredit(withdrawAmount);
        });
    }
    @Then("decrease the user credit by amount")
    public void decrease_the_user_credit_by_amount() throws InsufficientCredit {
        float initial_credit = (float) ReflectionTestUtils.getField(user, "credit");
        user.withdrawCredit(withdrawAmount);
        float new_credit = (float) ReflectionTestUtils.getField(user, "credit");
        Assertions.assertEquals(new_credit, initial_credit - withdrawAmount);
    }
}
