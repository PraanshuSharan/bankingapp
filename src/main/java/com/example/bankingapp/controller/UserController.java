package com.example.bankingapp.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingapp.dto.TransferRequest;
import com.example.bankingapp.model.User;
import com.example.bankingapp.service.UserService;

//import com.example.bankingapp.model.User;
//import com.example.bankingapp.service.UserService;

@RestController
@RequestMapping("/users")

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /*@GetMapping("/{name}")
    public User getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }*/

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
    /*@GetMapping("/{userId}/accounts")
    public List<String> getUserAccounts(@PathVariable Long userId) {
        return userService.getUserAccounts(userId);
    }*/
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable String accountNumber) {
        BigDecimal balance = userService.getAccountBalance(accountNumber);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
    Optional<User> user = userService.findById(userId);
    if (user.isPresent()) {
        return ResponseEntity.ok(user.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transferMoney(@RequestBody TransferRequest transferRequest) {
    try {
        userService.transferMoney(transferRequest.getFromAccount(), transferRequest.getToAccount(), transferRequest.getAmount());

        // Create a map to hold the response message
        Map<String, String> response = new HashMap<>();
        response.put("message", "Transfer successful");

        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
        // Create a map to hold the error message
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
}
    
    


}
