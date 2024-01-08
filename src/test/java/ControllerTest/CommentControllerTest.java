package ControllerTest;

import controllers.CommentController;
import exceptions.NotExistentComment;
import model.Comment;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.Baloot;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class CommentControllerTest {
    Map<String,String> map = new HashMap<String,String>();
    int commentId = 1;
    Comment comment = new Comment(commentId, "email1", "username1", 1, "text1");
    Baloot mockBaloot;
    CommentController CC = new CommentController();
    Exception notExistentComment = new NotExistentComment();
    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        mockBaloot = Mockito.mock(Baloot.class);
        CC.setBaloot(mockBaloot);
        map.put("username", "username1");
    }

    @Test
    public void SuccessfullyLikedWhenCommentIdHasFound() throws NotExistentComment {
        when(mockBaloot.getCommentById(isA(int.class))).thenReturn(comment);
        ResponseEntity result = CC.likeComment(String.valueOf(commentId), map);
        assertEquals(result, new ResponseEntity<>("The comment was successfully liked!", HttpStatus.OK));
    }

    @Test
    public void ThrownNotFoundExceptionWhenCommentIdHasNotFoundInLike() throws NotExistentComment {
        when(mockBaloot.getCommentById(isA(int.class))).thenThrow(new NotExistentComment());
        ResponseEntity result = CC.likeComment(String.valueOf(commentId), map);
        assertEquals(result, new ResponseEntity<>(notExistentComment.getMessage(), HttpStatus.NOT_FOUND));
    }

    @Test
    public void SuccessfullyDisLikedWhenCommentIdHasFound() throws NotExistentComment {
        when(mockBaloot.getCommentById(isA(int.class))).thenReturn(comment);
        ResponseEntity result = CC.dislikeComment(String.valueOf(commentId), map);
        assertEquals(result, new ResponseEntity<>("The comment was successfully disliked!", HttpStatus.OK));
    }

    @Test
    public void ThrownNotFoundExceptionWhenCommentIdHasNotFoundInDislike() throws NotExistentComment {
        when(mockBaloot.getCommentById(isA(int.class))).thenThrow(new NotExistentComment());
        ResponseEntity result = CC.dislikeComment(String.valueOf(commentId), map);
        assertEquals(result, new ResponseEntity<>(notExistentComment.getMessage(), HttpStatus.NOT_FOUND));
    }

//    @Test
//    public void AddCommentLikesWhenCommentIdHasFound() throws NotExistentComment {
//        when(mockBaloot.getCommentById(isA(int.class))).thenReturn(comment);
//        CC.likeComment(String.valueOf(commentId), map);
//        assertEquals();
//    }

    @AfterEach
    void TearDown(){

    }

}
