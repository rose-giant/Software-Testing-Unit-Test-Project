import exceptions.NotInStock;
import io.micrometer.core.instrument.config.validate.Validated;
import lombok.SneakyThrows;
import model.Comment;
import model.Commodity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ModelCommodityTest {

    Commodity commodity = new Commodity();
    String mockId = "mockId";
    String mockName = "mockName";
    String mockProviderId = "mockProviderId";
    int mockPrice = 0;
    ArrayList<String> mockCategories = new ArrayList<>();
    float mockRating = 0;
    int mockInStock = 0;
    String mockImage = "mockImage";
    Map<String, Integer> mockUserRate = new HashMap<>();
    float mockInitRate = 0;

    @BeforeEach
    void beforeEachTestCreateMockCommodity() {
        this.commodity.setId(this.mockId);
        this.commodity.setName(this.mockName);
        this.commodity.setProviderId(this.mockProviderId);
        this.commodity.setPrice(this.mockPrice);
        this.commodity.setCategories(this.mockCategories);
        this.commodity.setRating(this.mockRating);
        this.commodity.setInStock(this.mockInStock);
        this.commodity.setImage(this.mockImage);
        this.commodity.setUserRate(this.mockUserRate);
        this.commodity.setInitRate(this.mockInitRate);
    }

    @Test
    void updateInStockWithInStock0AmountMinus1ThrowsNotInStockException() {

        int testedAmount = -1;
        Exception exception = assertThrows(Exception.class, () -> {
            this.commodity.updateInStock(testedAmount);
        });

        String expectedMessage = "Commodity is not in stock.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateInStockWithInStock100AmountMinus2000ThrowsNotInStockException() {

        int testedAmount = -2000;
        this.commodity.setInStock(100);
        Exception exception = assertThrows(Exception.class, () -> {
            this.commodity.updateInStock(testedAmount);
        });

        String expectedMessage = "Commodity is not in stock.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateInStockWithInStock0Amount0ThrowsNotInStockException() {
        int testedAmount = 0;
        assertDoesNotThrow(() -> {
            this.commodity.updateInStock(testedAmount);
        });
    }

    @Test
    void updateInStockWithInStock0Amount1ThrowsNotInStockException() {
        int testedAmount = 1;
        assertDoesNotThrow(() -> {
            this.commodity.updateInStock(testedAmount);
        });
    }

    @Test
    void updateInStockWithInStock100Amount1ThrowsNotInStockException() {
        this.commodity.setInStock(100);
        int testedAmount = 1;
        assertDoesNotThrow(() -> {
            this.commodity.updateInStock(testedAmount);
        });
    }

    @Test
    void updateInStockUpdatesInStockFieldWithInStock0Amount0() throws NotInStock {
        this.commodity.setInStock(0);
        this.commodity.updateInStock(0);
        int newInStock = this.commodity.getInStock();
        assertEquals(newInStock , 0);
    }

    @Test
    void updateInStockUpdatesInStockFieldWithInStock0Amount1() throws NotInStock {
        this.commodity.updateInStock(1);
        int newInStock = this.commodity.getInStock();
        assertEquals(newInStock , 1);
    }

    @Test
    void updateInStockUpdatesInStockFieldWithInStock100Amount11() throws NotInStock {
        this.commodity.setInStock(100);
        this.commodity.updateInStock(11);
        int newInStock = this.commodity.getInStock();
        assertEquals(newInStock , 111);
    }

    @Test
    void addRatePutsMockName0ScoreInUserRate() {
        this.commodity.addRate(this.mockName, 0);
        Map<String, Integer> userRate = this.commodity.getUserRate();
        assertTrue(userRate.containsKey(this.mockName));
    }

    @Test
    void addRatePuts2MockName0ScoreInUserRate() {
        this.commodity.addRate(this.mockName, 0);
        this.commodity.addRate("mockName2", 0);
        Map<String, Integer> userRate = this.commodity.getUserRate();
        assertTrue(userRate.containsKey("mockName2"));
    }

    @SneakyThrows
    @Test
    void calcRatingSetsRatingHalfWith1UserRateOfMockNameScore1AndInitRate0() {
        this.commodity.setInitRate(0);
        this.commodity.addRate("mockName", 1);
        assertEquals(0.5, this.commodity.getRating());
    }

    @SneakyThrows
    @Test
    void calcRatingSetsRating4With2UserRateOfMockNameScore12AndInitRate0() {
        this.commodity.setInitRate(0);
        this.commodity.addRate("mockName", 7);
        this.commodity.addRate("mockName2", 5);
        assertEquals(4, this.commodity.getRating());
    }

    @SneakyThrows
    @Test
    void calcRatingSetsRating10With2UserRateOfMockNameScore12AndInitRate18() {
        this.commodity.setInitRate(18);
        this.commodity.addRate("mockName", 7);
        this.commodity.addRate("mockName2", 5);
        assertEquals(10, this.commodity.getRating());
    }

    @SneakyThrows
    @Test
    void calcRatingSetsRating10With1UserRateOfMockNameScore353AndInitRate18() {
        this.commodity.setInitRate(18);
        this.commodity.addRate("mockName", 353);
        assertEquals(185.5, this.commodity.getRating());
    }

    @SneakyThrows
    @Test
    void calcRatingSetsRating10With1UserRateOfMockNameScore3503AndInitRate21() {
        this.commodity.setInitRate(21);
        this.commodity.addRate("mockName", 353);
        this.commodity.addRate("mockName2", 3150);
        assertEquals(1174.6666259765625, this.commodity.getRating());
    }
}