package com.example.bankingapp;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.example.bankingapp.controller.UserController;
import com.example.bankingapp.model.User;
import com.example.bankingapp.service.UserService;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings({ "deprecation", "null" })
    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        when(userService.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getName());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetUserByIdNotFound() {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setName("Jane Doe");

        when(userService.saveUser(user)).thenReturn(user);

        User createdUser = userController.createUser(user);
        assertEquals("Jane Doe", createdUser.getName());
    }
}
