package com.sunyk.springbootjdbc.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * Create by sunyang on 2018/9/25 22:57
 * For me:One handred lines of code every day,make myself stronger.
 */
public class VaildCardNumberConstrainValidator implements ConstraintValidator {
    @Override
    public void initialize(Annotation constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
