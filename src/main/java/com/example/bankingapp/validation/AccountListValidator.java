package com.example.bankingapp.validation;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountListValidator implements ConstraintValidator<ValidAccountList, List<String>> {

    @Override
    public void initialize(ValidAccountList constraintAnnotation) {
        // Initialization code, if needed
    }

    @Override
    public boolean isValid(List<String> accounts, ConstraintValidatorContext context) {
        if (accounts == null) {
            return true; // Can be null, use @NotNull for non-null constraint
        }

        String regex = "^[a-zA-Z0-9]+$";
        for (String account : accounts) {
            if (account == null || !account.matches(regex)) {
                return false;
            }
        }
        return true;
    }
}
