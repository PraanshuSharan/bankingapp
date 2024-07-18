package com.example.bankingapp.service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankingapp.exception.InvalidAmountException;
import com.example.bankingapp.model.User;
import com.example.bankingapp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public List<String> getUserAccounts(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getAccounts();
    }

    //@Autowired
    //private UserRepository userRepository;

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public BigDecimal getAccountBalance(String accountNumber) {
        Optional<User> user = userRepository.findByAccountNumber(accountNumber);
        return user.map(u -> u.getAccountBalances().get(accountNumber)).orElse(null);
    }
    public void transferMoney(String fromAccount, String toAccount, BigDecimal amount) {
        validateAmount(amount);
        User fromUser = userRepository.findByAccountsContaining(fromAccount);
        User toUser = userRepository.findByAccountsContaining(toAccount);

        if (fromUser == null || toUser == null) {
            throw new IllegalArgumentException("Invalid account number");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid Transfer");
        }

        
        BigDecimal fromBalance = fromUser.getAccountBalance(fromAccount);
        BigDecimal toBalance = toUser.getAccountBalance(toAccount);

        if (fromBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromUser.setAccountBalance(fromAccount, fromBalance.subtract(amount));
        toUser.setAccountBalance(toAccount, toBalance.add(amount));

        userRepository.save(fromUser);
        userRepository.save(toUser);
    }
    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new InvalidAmountException("Amount must be provided");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }
        if (!amount.toPlainString().matches("^\\d+(\\.\\d{1,2})?$")) {
            throw new InvalidAmountException("Amount must be a valid number with up to two decimal places");
        }
    }
    

    
    
}
