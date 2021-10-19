package com.example.producer.data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CardNumberValidator implements ConstraintValidator<CardNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        final var regex = "^(?:\\s*)\\d{4}-\\d{4}-\\d{4}-\\d{4}(?:\\s*)$";
        final var pattern = Pattern.compile(regex);
        final var matcher = pattern.matcher(value);
        return matcher.matches();

        }
    }
