package com.sunyk.springbootjdbc.valid.constrains;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * Create by sunyang on 2018/9/25 22:49
 * For me:One handred lines of code every day,make myself stronger.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface ValidCardNumber {
}
