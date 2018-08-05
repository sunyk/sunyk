package com.sunyk.spring.annotation;

import java.lang.annotation.*;

/**
 * Create by sunyang on 2018/8/3 0:18
 * For me:One handred lines of code every day,make myself stronger.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    String value() default "";
}
