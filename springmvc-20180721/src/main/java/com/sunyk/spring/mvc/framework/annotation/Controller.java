package com.sunyk.spring.mvc.framework.annotation;

import java.lang.annotation.*;

/**
 * Create by sunyang on 2018/7/22 11:15
 * For me:One handred lines of code every day,make myself stronger.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
