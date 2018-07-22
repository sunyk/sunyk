package com.sunyk.spring.mvc.framework.annotation;

import java.lang.annotation.*;

/**
 * Create by sunyang on 2018/7/22 11:17
 * For me:One handred lines of code every day,make myself stronger.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
