package ControllerTest;

import controllers.CommoditiesController;
import exceptions.NotExistentCommodity;
import exceptions.NotExistentUser;
import model.Comment;
import model.Commodity;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.Baloot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CommodityControllerTest {
    public Baloot mockBaloot;
    public CommoditiesController commoditiesController = new CommoditiesController();
    public Commodity mockCommodity = new Commodity();
    public Comment mockComment = new Comment();


    public Map<String, String> rateInputMap = new HashMap<>();
    public Map<String, String> userCommont = new HashMap<>();
    public Map<String, String> searchCommodityMap = new HashMap<>();

    public ArrayList<Comment> mockCommentList = new ArrayList<>();
    ArrayList<Commodity> mockSuggestedCommodities = new ArrayList<>();
    public ArrayList<Commodity> mockCommodityList = new ArrayList<Commodity>();
    public ArrayList<Commodity> searchResultCommodities = new ArrayList<>();

    public String mockUsername = "mockUsername";
    public String mockPassword = "mockPassword";
    public String mockEmail = "mockEmail";
    public String mockBirthDate = "mockBirthDate";
    public String mockAddress = "mockAddress";

    public User mockUser = new User(mockUsername, mockPassword, mockEmail, mockBirthDate, mockAddress);

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
//    @ParameterizedTest
//    @ValueSource(strings = {"12", "sfsd"})
//    public void getCommodityReturnsNotFoundStatusWithNoneExistentId(String noneExistentId) throws NotExistentCommodity {
//        when(mockBaloot.getCommodityById("sd")).thenReturn(null);
//        assertEquals(new ResponseEntity<>(null, HttpStatus.NOT_FOUND) , commoditiesController.getCommodity("sd"));
//    }

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
//   @Test
//    public void rateCommodityReturnsNotFoundForNoneExistentCommodity() throws NotExistentCommodity {
//        rateInputMap.put("rate", "7");
//        rateInputMap.put("username", "rose");
//
//        when(mockBaloot.getCommodityById("00")).thenReturn(null);
//        ResponseEntity<String> expectedResponse = commoditiesController.rateCommodity("00", rateInputMap);
//
//        assertEquals(new ResponseEntity<>("Commodity not found!", HttpStatus.NOT_FOUND) , expectedResponse);
//   }

   @ParameterizedTest
   @ValueSource(strings = {"111", "123"})
    public void addCommodityCommentAddsMockCommentByMockUser(String mockId) throws NotExistentUser {
        when(mockBaloot.generateCommentId()).thenReturn(Integer.parseInt(mockId));
        when(mockBaloot.getUserById(mockUsername)).thenReturn(mockUser);

        userCommont.put("username", mockUsername);
        userCommont.put("comment", "mock comment text");
        assertEquals(new ResponseEntity<>("comment added successfully!", HttpStatus.OK) ,
                commoditiesController.addCommodityComment(mockId , userCommont));
   }

   //even when comment is null it is posted
    @ParameterizedTest
    @ValueSource(strings = {"111", "123"})
        public void addCommodityCommentAddsCommodityCommentWithNullComment(String mockId) throws NotExistentUser {
        when(mockBaloot.generateCommentId()).thenReturn(Integer.parseInt(mockId));
        when(mockBaloot.getUserById(mockUsername)).thenReturn(mockUser);

        userCommont.put("username", mockUsername);
        assertEquals(new ResponseEntity<>("comment added successfully!", HttpStatus.OK) ,
                commoditiesController.addCommodityComment(mockId , userCommont));
    }

    //the none existent user exception is not caught, instead the error occurs
//    @ParameterizedTest
//    @ValueSource(strings = {"111", "123"})
//    public void addCommodityCommentAddsCommodityCommentWithNullUser(String mockId) throws NotExistentUser {
//        when(mockBaloot.generateCommentId()).thenReturn(Integer.parseInt(mockId));
//        when(mockBaloot.getUserById(mockUsername)).thenReturn(null);
//
//        userCommont.put("username", mockUsername);
//        assertEquals(new ResponseEntity<>("comment added successfully!", HttpStatus.OK) ,
//                commoditiesController.addCommodityComment(mockId , userCommont));
//    }

    @Test
    public void getCommodityCommentReturnsMockCommentList() {
        mockCommentList.add(mockComment);
        when(mockBaloot.getCommentsForCommodity(11)).thenReturn(mockCommentList);
        assertEquals(new ResponseEntity<>(mockCommentList, HttpStatus.OK) ,
                commoditiesController.getCommodityComment("11"));
    }

    @Test
    public void getCommodityCommentReturnsNullCommentList() {
        when(mockBaloot.getCommentsForCommodity(11)).thenReturn(mockCommentList);
        assertEquals(new ResponseEntity<>(mockCommentList, HttpStatus.OK) ,
                commoditiesController.getCommodityComment("11"));
    }

    @Test
    public void searchCommoditiesReturnsEmptyListForEmptySearchOfName() {
        searchCommodityMap.put("searchOption", "name");
        when(mockBaloot.filterCommoditiesByName("")).thenReturn(searchResultCommodities);
        assertEquals(new ResponseEntity<>(searchResultCommodities, HttpStatus.OK)
        , commoditiesController.searchCommodities(searchCommodityMap));
    }

    @Test
    public void searchCommoditiesReturnsEmptyListForEmptySearchOfCategory() {
        searchCommodityMap.put("searchOption", "category");
        when(mockBaloot.filterCommoditiesByName("")).thenReturn(searchResultCommodities);
        assertEquals(new ResponseEntity<>(searchResultCommodities, HttpStatus.OK)
                , commoditiesController.searchCommodities(searchCommodityMap));
    }

    @Test
    public void searchCommoditiesReturnsEmptyListForEmptySearchOfProvider() {
        searchCommodityMap.put("searchOption", "provider");
        when(mockBaloot.filterCommoditiesByName("")).thenReturn(searchResultCommodities);
        assertEquals(new ResponseEntity<>(searchResultCommodities, HttpStatus.OK)
                , commoditiesController.searchCommodities(searchCommodityMap));
    }

    @Test
    public void searchCommoditiesReturnsEmptyListForDummySearchOptionAsDefault() {
        searchCommodityMap.put("searchOption", "dummyOption");
        when(mockBaloot.filterCommoditiesByName("")).thenReturn(searchResultCommodities);
        assertEquals(new ResponseEntity<>(searchResultCommodities, HttpStatus.OK)
                , commoditiesController.searchCommodities(searchCommodityMap));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ramen", "tteoknoki", "mochi", ""})
    public void searchCommoditiesReturnsMockResultAsNameFilterSearchResult(String searchedName) {
        searchCommodityMap.put("searchOption", "name");
        searchCommodityMap.put("searchValue", searchedName);
        when(mockBaloot.filterCommoditiesByName(searchedName)).thenReturn(searchResultCommodities);
        assertEquals(new ResponseEntity<>(searchResultCommodities, HttpStatus.OK) ,
                commoditiesController.searchCommodities(searchCommodityMap));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ramen", "tteoknoki", "mochi", ""})
    public void searchCommoditiesReturnsMockResultAsCategorySearchResult(String searchedCategory) {
        searchCommodityMap.put("searchOption", "category");
        searchCommodityMap.put("searchValue", searchedCategory);
        when(mockBaloot.filterCommoditiesByCategory(searchedCategory)).thenReturn(searchResultCommodities);
        assertEquals(new ResponseEntity<>(searchResultCommodities, HttpStatus.OK) ,
                commoditiesController.searchCommodities(searchCommodityMap));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ramen", "tteoknoki", "mochi", ""})
    public void searchCommoditiesReturnsMockResultAsProviderSearchResult(String searchedProvider) {
        searchCommodityMap.put("searchOption", "provider");
        searchCommodityMap.put("searchValue", searchedProvider);
        when(mockBaloot.filterCommoditiesByProviderName(searchedProvider)).thenReturn(searchResultCommodities);
        assertEquals(new ResponseEntity<>(searchResultCommodities, HttpStatus.OK) ,
                commoditiesController.searchCommodities(searchCommodityMap));
    }

    @Test
    public void getSuggestedCommoditiesReturnsMockSuggestedCommoditiesWithMockCommodity() throws NotExistentCommodity {
        when(mockBaloot.getCommodityById("0")).thenReturn(mockCommodity);
        when(mockBaloot.suggestSimilarCommodities(mockCommodity)).thenReturn(mockSuggestedCommodities);
        assertEquals(new ResponseEntity<>(mockSuggestedCommodities, HttpStatus.OK) ,
                commoditiesController.getSuggestedCommodities("0"));
    }

//    @Test
//    public void getSuggestedCommoditiesThrowsNotExistentCommodityExceptionWithNullCommodity() throws NotExistentCommodity {
//        when(mockBaloot.getCommodityById("0")).thenReturn(null);
//        when(mockBaloot.suggestSimilarCommodities(mockCommodity)).thenReturn(mockSuggestedCommodities);
//        assertEquals(new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND) ,
//                commoditiesController.getSuggestedCommodities("022"));
//    }




















}
