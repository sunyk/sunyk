package com.sunyk.springbootbeanvalidation.validation;

import com.sunyk.springbootbeanvalidation.validation.constraints.CardNumberValidation;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Create by sunyang on 2018/10/2 22:15
 * For me:One handred lines of code every day,make myself stronger.
 */
public class CardNumberConstraintValidator implements ConstraintValidator<CardNumberValidation, String> {


    @Override
    public void initialize(CardNumberValidation cardNumberValidation) {
        return;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String[] parts  = StringUtils.delimitedListToStringArray(s, "-");
        if (parts.length != 2){
            return false;
        }

        String prefix = parts[0];
        String suffix = parts[1];

        boolean isPrefix = prefix.compareTo("TUHU") == 0;
        boolean isSuffix = suffix.matches("[0-9]{1,}");

        return isPrefix && isSuffix;
    }
}
