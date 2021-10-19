package com.example.producer.data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AmountValueValidator implements ConstraintValidator<AmountValue, Long> {

   @Override
   public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
      if (value <= 0 || value > 999999999) {
         return false;
      }  else {
         return true;
      }
   }
}
