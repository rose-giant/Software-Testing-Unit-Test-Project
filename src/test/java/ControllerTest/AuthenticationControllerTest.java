package ControllerTest;

import controllers.AuthenticationController;
import exceptions.*;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.Baloot;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {

    Baloot mockBaloot;
    HashMap map;
    ResponseEntity result;
    AuthenticationController AC = new AuthenticationController();
    Exception notExistentUser = new NotExistentUser();
    Exception incorrectPassword = new IncorrectPassword();
    Exception usernameAlreadyTaken = new UsernameAlreadyTaken();

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        mockBaloot = Mockito.mock(Baloot.class);
        AC.setBaloot(mockBaloot);
        map = new HashMap<>();
    }

    @Test
    public void SuccessfullyLoginWhenUsernameAndPasswordAreOk() throws NotExistentUser, IncorrectPassword {
        Mockito.doNothing().when(mockBaloot).login(anyString(), anyString());
        result = AC.login(new HashMap<>());
        assertEquals(new ResponseEntity<>("login successfully!", HttpStatus.OK), result);
    }
    @Test
    public void ThrownNotExistentUserWhenUsernameNotFound() throws NotExistentUser, IncorrectPassword {
        Mockito.doThrow(new NotExistentUser()).when(mockBaloot).login(anyString(), anyString());
        map.put("username", "username1");
        map.put("password", "password1");
        result = AC.login(map);
        assertEquals(new ResponseEntity<>(notExistentUser.getMessage(), HttpStatus.NOT_FOUND), result);
    }
    @Test
    public void ThrownIncorrectPasswordWhenPasswordIsNotCorrect() throws NotExistentUser, IncorrectPassword {
        Mockito.doThrow(new IncorrectPassword()).when(mockBaloot).login(anyString(), anyString());
        result = AC.login(map);
        assertEquals(new ResponseEntity<>(incorrectPassword.getMessage(), HttpStatus.UNAUTHORIZED), result);
    }

    @Test
    public void SuccessfullyLSignupWhenUsernameIsNew() throws UsernameAlreadyTaken {
        Mockito.doNothing().when(mockBaloot).addUser(isA(User.class));
        result = AC.signup(new HashMap<>());
        assertEquals(new ResponseEntity<>("signup successfully!", HttpStatus.OK), result);
    }
    @Test
    public void ThrownUsernameAlreadyTakenWhenUsernameIsAlreadyTaken() throws UsernameAlreadyTaken {
        Mockito.doThrow(new UsernameAlreadyTaken()).when(mockBaloot).addUser(isA(User.class));
        result = AC.signup(new HashMap<>());
        assertEquals(new ResponseEntity<>(usernameAlreadyTaken.getMessage(), HttpStatus.BAD_REQUEST), result);

    }

}
