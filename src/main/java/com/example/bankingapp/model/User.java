package com.example.bankingapp.model;
//import com.example.bankingapp.repository.*;

//package com.example.bankingapp.repository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bankingapp.validation.ValidAccountList;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name="name")
    private String name;

    @ValidAccountList
    //@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Account number must be alphanumeric")
    @ElementCollection
    @Column(name="accounts")
    //@UniqueElements
    private List<@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Account number must be alphanumeric") String> accounts;

    @ElementCollection
    @MapKeyColumn(name = "account")
    @Column(name = "balance")
    //@UniqueElements
    private Map<String, BigDecimal> accountBalances = new HashMap<>();

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }
    public Map<String, BigDecimal> getAccountBalances() {
        return accountBalances;
    }

    public void setAccountBalances(Map<String, BigDecimal> accountBalances) {
        this.accountBalances = accountBalances;
    }

    public BigDecimal getAccountBalance(String account) {
        return accountBalances.getOrDefault(account, BigDecimal.ZERO);
    }

    public void setAccountBalance(String account, BigDecimal balance) {
        accountBalances.put(account, balance);
    }
}
