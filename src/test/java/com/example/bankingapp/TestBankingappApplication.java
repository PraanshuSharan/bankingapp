package com.example.bankingapp;

import org.springframework.boot.SpringApplication;

public class TestBankingappApplication {

	public static void main(String[] args) {
		SpringApplication.from(BankingappApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
