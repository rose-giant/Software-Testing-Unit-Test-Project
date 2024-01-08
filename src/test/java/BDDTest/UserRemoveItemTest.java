package BDDTest;

import exceptions.CommodityIsNotInBuyList;
import exceptions.InsufficientCredit;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Commodity;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserRemoveItemTest {
    User user = null;
    Map<String, Integer> buyList;
    String wantToRemovedId;
    @Given("Execute removeItemFromBuyList")
    public void execute_remove_item_from_buy_list() {
        buyList = new HashMap<>();
        user = new User();
    }
    @When("Current buy list has commodity \" id: {string} , amount: {int} \"")
    public void current_buy_list_has_commodity_id_amount(String id, Integer amount) {
        buyList.put(id, amount);
        user.setBuyList(buyList);
    }
    @When("If wanted commodity id is {string}")
    public void if_wanted_commodity_id_is(String id) {
        wantToRemovedId = id;
    }
    @Then("Throw CommodityIsNotInBuyList error")
    public void throw_commodity_is_not_in_buy_list_error() throws CommodityIsNotInBuyList {
        Assertions.assertThrows(CommodityIsNotInBuyList.class, () -> {
            user.removeItemFromBuyList(new Commodity(wantToRemovedId));
        });
    }
    @Then("Remove the commodity from buy list")
    public void remove_the_commodity_from_buy_list() throws CommodityIsNotInBuyList {
        user.removeItemFromBuyList(new Commodity(wantToRemovedId));
        Map<String, Integer> newBuyList = (Map<String, Integer>) ReflectionTestUtils.getField(user, "buyList");
        assertNull(newBuyList.get(wantToRemovedId));
    }
    @Then("Decrease the amount of commodity in buy list by one")
    public void decrease_the_amount_of_commodity_in_buy_list_by_one() throws CommodityIsNotInBuyList {
        Map<String, Integer> buyList1 = (Map<String, Integer>) ReflectionTestUtils.getField(user, "buyList");
        int amountBeforeRemoving = buyList1.get(wantToRemovedId);
        user.removeItemFromBuyList(new Commodity(wantToRemovedId));
        Map<String, Integer> buyList2 = (Map<String, Integer>) ReflectionTestUtils.getField(user, "buyList");
        assertEquals(amountBeforeRemoving - 1, buyList2.get(wantToRemovedId));
    }

}
