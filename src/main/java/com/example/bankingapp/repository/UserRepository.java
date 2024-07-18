package com.example.bankingapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.bankingapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String name);
    User findByAccountsContaining(String account);
    @Query("SELECT u FROM User u JOIN u.accounts a WHERE a = :accountNumber")
    //User findByAccountNumber(@Param("accountNumber") String accountNumber);
    //Optional<User> findById(Long id);
    Optional<User> findByAccountNumber(String accountNumber);
}