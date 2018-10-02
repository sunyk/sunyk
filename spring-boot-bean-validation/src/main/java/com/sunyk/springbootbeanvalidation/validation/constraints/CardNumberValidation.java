package com.sunyk.springbootbeanvalidation.validation.constraints;

import com.sunyk.springbootbeanvalidation.validation.CardNumberConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Create by sunyang on 2018/10/2 20:14
 * For me:One handred lines of code every day,make myself stronger.
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {CardNumberConstraintValidator.class}
)
public @interface CardNumberValidation {

    String message() default "{com.sunyk.bean.validation.card.number.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
