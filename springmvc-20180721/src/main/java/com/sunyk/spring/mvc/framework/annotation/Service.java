package com.sunyk.spring.mvc.framework.annotation;

import java.lang.annotation.*;

/**
 * Create by sunyang on 2018/7/22 11:16
 * For me:One handred lines of code every day,make myself stronger.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value()default "";
}
