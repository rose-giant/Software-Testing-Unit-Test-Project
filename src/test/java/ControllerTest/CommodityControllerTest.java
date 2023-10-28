package ControllerTest;

import controllers.CommentController;
import controllers.CommoditiesController;
import exceptions.NotExistentComment;
import exceptions.NotExistentCommodity;
import model.Commodity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import service.Baloot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CommodityControllerTest {

    public CommoditiesController commoditiesController = new CommoditiesController();
    public Commodity mockCommodity = new Commodity();
    public ArrayList<Commodity> mockCommodityList = new ArrayList<Commodity>();
    public Baloot mockBaloot;
    public HttpStatus mockOKHttpStatus = HttpStatus.valueOf(200);
    public Map<String, String> rateInputMap = new HashMap<>();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockBaloot = Mockito.mock(Baloot.class);
        mockCommodity.setId("00");
        commoditiesController.setBaloot(mockBaloot);

    }

    @Test
    public void getCommoditiesReturnsMockCommodityList() {
        mockCommodityList.add(mockCommodity);
        when(mockBaloot.getCommodities()).thenReturn(mockCommodityList);
        assertEquals(commoditiesController.getCommodities() , new ResponseEntity<>(mockCommodityList, HttpStatus.OK));
    }

    @Test
    public void getCommodityReturnsMockCommodityById() throws NotExistentCommodity {
        when(mockBaloot.getCommodityById("00")).thenReturn(mockCommodity);
        assertEquals(commoditiesController.getCommodity("00") , new ResponseEntity<>(mockCommodity, HttpStatus.OK));
    }

    //issue yaftam
    //when there is no commodity with the id, it does not return null
    @ParameterizedTest
    @ValueSource(strings = {"12", "sfsd"})
    public void getCommodityReturnsNotFoundStatusWithNoneExistentId(String noneExistentId) throws NotExistentCommodity {
        when(mockBaloot.getCommodityById("sd")).thenReturn(null);
        assertEquals(new ResponseEntity<>(null, HttpStatus.NOT_FOUND) , commoditiesController.getCommodity("sd"));
    }

   @Test
    public void rateCommoditySetsRateForMockCommodity() throws NotExistentCommodity {
        when(mockBaloot.getCommodityById("00")).thenReturn(mockCommodity);
        rateInputMap.put("rate", "7");
        rateInputMap.put("username", "rose");
        mockCommodity.addRate("rose", 7);
        assertEquals(new ResponseEntity<>("rate added successfully!", HttpStatus.OK) ,
                commoditiesController.rateCommodity("00", rateInputMap));
   }

   @Test
   public void rateCommodityReturnsBadRequestForNoneIntegerRate() throws NumberFormatException, NotExistentCommodity {
       when(mockBaloot.getCommodityById("00")).thenReturn(mockCommodity);
       rateInputMap.put("username", "rose");
       rateInputMap.put("rate", "I'm not an integer");

       mockCommodity.addRate("rose", 7);

       assertEquals(new ResponseEntity<>("For input string: \""+rateInputMap.get("rate")+"\"", HttpStatus.BAD_REQUEST) ,
               commoditiesController.rateCommodity("00", rateInputMap));
   }

   @Test
   public void rateCommodityReturnsBadRequestForNullRate() throws NotExistentCommodity {
       when(mockBaloot.getCommodityById("00")).thenReturn(mockCommodity);
       rateInputMap.put("username", "rose");
       //Exception numberFormatException = new NumberFormatException();
       assertEquals(new ResponseEntity<>("Cannot parse null string" , HttpStatus.BAD_REQUEST) , commoditiesController.rateCommodity("00", rateInputMap));
   }

   //when commodity does not exist, the exception is not thrown. The error occurs instead
   @Test
    public void rateCommodityReturnsNotFoundForNoneExistentCommodity() throws NotExistentCommodity {
        rateInputMap.put("rate", "7");
        rateInputMap.put("username", "rose");

        when(mockBaloot.getCommodityById("00")).thenReturn(null);
        ResponseEntity<String> expectedResponse = commoditiesController.rateCommodity("00", rateInputMap);

        assertEquals(new ResponseEntity<>("Commodity not found!", HttpStatus.NOT_FOUND) , expectedResponse);
   }

}
