import exceptions.CommodityIsNotInBuyList;
import exceptions.InsufficientCredit;
import exceptions.InvalidCreditRange;
import model.Commodity;
import model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static User user;
    @BeforeEach
    void Setup(){
        user = new User("TestUsername", "TestPassword",
                "testEmail", "TestBirthDate", "TestAddress");
    }

    @ParameterizedTest
    @ValueSource(floats = {-1, -10.4f, -1000.34f, -55555})
    public void ThrownInvalidCreditRangeExceptionWhenCreditIsNegative(float negVal) {
        assertThrows(InvalidCreditRange.class, () -> {
            user.addCredit(negVal);
        });
    }

    @ParameterizedTest
    @ValueSource(floats = {0, 1, 10.4f, 1000.34f, 55555})
    public void IncreaseCreditWhenAmountIsNotNegative(float amount) throws NoSuchFieldException, InvalidCreditRange {
        float initial_credit = (float) ReflectionTestUtils.getField(user, "credit");
        user.addCredit(amount);
        float new_credit = (float) ReflectionTestUtils.getField(user, "credit");
        assertEquals(new_credit, initial_credit + amount);
    }

    @ParameterizedTest
    @ValueSource(floats = {1, 10.4f, 1000.34f, 55555})
    public void ThrownInsufficientCreditExceptionWhenWithdrawIsMore(float amount) {
        float initial_credit = (float) ReflectionTestUtils.getField(user, "credit");
        Exception exception = assertThrows(InsufficientCredit.class, () -> {
            user.withdrawCredit(initial_credit + amount);
        });
    }

    @ParameterizedTest
    @ValueSource(floats = {0, 1, 10.4f, 1000.34f, 55555})
    public void DecreaseCreditWhenAmountIsLessThanCredit(float amount) throws NoSuchFieldException, InvalidCreditRange, InsufficientCredit {
        ReflectionTestUtils.setField(user, "credit", amount);
        user.withdrawCredit(amount/2);
        float new_credit = (float) ReflectionTestUtils.getField(user, "credit");
        assertEquals(new_credit, amount/2);
    }

    @ParameterizedTest
    @CsvSource(value = {"0, ", "1,id", "55555,1234"})
    public void UpdateQuantityByOneIfCommodityIsAlreadyOnBuyList(String q, String id){
        int quantity = Integer.parseInt(q);
        Map mockBuyList=new HashMap();
        mockBuyList.put(id, quantity);
        ReflectionTestUtils.setField(user, "buyList", mockBuyList );
        user.addBuyItem(new Commodity(id));
        Map<String, Integer> m1 = (Map<String, Integer>) ReflectionTestUtils.getField(user, "buyList");
        int new_quantity = m1.get(id);
        assertEquals(quantity + 1, new_quantity);
    }

    @Test
    public void AddCommodityToBuyListIfIsNotAlreadyOnIt(){
        Map<String, Integer> mockBuyList=new HashMap<>();
        ReflectionTestUtils.setField(user, "buyList", mockBuyList );
        String new_commodity_id = "newId";
        user.addBuyItem(new Commodity(new_commodity_id));
        Map<String, Integer> m1 = (Map<String, Integer>) ReflectionTestUtils.getField(user, "buyList");
        assertEquals(1, m1.get(new_commodity_id));
    }

    @ParameterizedTest
    @CsvSource(value = {"0, ", "1,id", "55555,1234"})
    public void UpdateQuantityIfCommodityIsAlreadyOnPurchasedList(String q, String id){
        int quantity = Integer.parseInt(q);
        int initial_quantity = 0;
        Map<String, Integer> mockPurchasedList = new HashMap();
        mockPurchasedList.put(id, initial_quantity);
        ReflectionTestUtils.setField(user, "purchasedList", mockPurchasedList );
        user.addPurchasedItem(id, quantity);
        Map<String, Integer> m1 = (Map<String, Integer>) ReflectionTestUtils.getField(user, "purchasedList");
        int new_quantity = m1.get(id);
        assertEquals(initial_quantity + quantity, new_quantity);
    }

    @ParameterizedTest
    @CsvSource(value = {"0, ", "1,id", "55555,1234"})
    public void AddCommodityToPurchasedListIfIsNotAlreadyOnIt(String q, String id){
        //Setup
        int quantity = Integer.parseInt(q);
        Map<String, Integer> mockPurchasedList = new HashMap();
        ReflectionTestUtils.setField(user, "purchasedList", mockPurchasedList );
        //Execution
        user.addPurchasedItem(id, quantity);
        //Verification
        Map<String, Integer> m1 = (Map<String, Integer>) ReflectionTestUtils.getField(user, "purchasedList");
        assertEquals(quantity, m1.get(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "id", "1234"})
    public void RemoveCommodityFromBuyListIfQuantityIsOne(String id) throws CommodityIsNotInBuyList {
        //Setup
        Map<String, Integer> mockBuyList = new HashMap();
        mockBuyList.put(id, 1);
        ReflectionTestUtils.setField(user, "buyList", mockBuyList );
        //Execution
        user.removeItemFromBuyList(new Commodity(id));
        //Verification
        Map<String, Integer> m1 = (Map<String, Integer>) ReflectionTestUtils.getField(user, "buyList");
        assertNull(m1.get(id));
    }

    @ParameterizedTest
    @CsvSource(value = {"2, ", "10,id", "55555,1234"})
    public void DecreaseQuantityByOneIfCommoditiesAreMoreThanOneOnBuyList(String q, String id) throws CommodityIsNotInBuyList {
        //Setup
        int quantity = Integer.parseInt(q);
        Map<String, Integer> mockBuyList = new HashMap();
        mockBuyList.put(id, quantity);
        ReflectionTestUtils.setField(user, "buyList", mockBuyList );
        //Execution
        user.removeItemFromBuyList(new Commodity(id));
        //Verification
        Map<String, Integer> m1 = (Map<String, Integer>) ReflectionTestUtils.getField(user, "buyList");
        assertEquals(quantity - 1, m1.get(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "10", "id"})
    public void ThrownCommodityIsNotInBuyListExceptionWhenCommodityNotFound(String id) throws CommodityIsNotInBuyList {
        //Setup
        Map<String, Integer> mockBuyList = new HashMap();
        ReflectionTestUtils.setField(user, "buyList", mockBuyList );
        //Verification
        Exception exception = assertThrows(CommodityIsNotInBuyList.class, () -> {
            user.removeItemFromBuyList(new Commodity(id));
        });
    }

    @AfterEach
    void Teardown(){
        user = null;
    }
}
