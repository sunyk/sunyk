package com.spring.read;

/**
 * created on sunyang 2018/7/9 16:47
 * Are you different!"jia you" for me
 */
public class Readme {

    /**
     * spring 的生命周期
     * 1.实例化BeanFactoryPostProcessor实现类
     * 2.执行BeanFactoryPostProcessor的postProcessBeanFactory方法
     * 3.实例化BeanPostProcessor实现类
     * 4.实例化InstantiationAwareBeanPostProcessorAdapter实现类
     * 5.执行InstantiationAwareBeanPostProcessor的postProcessBeforeInstantiation方法
     * 6.执行Bean的构造器
     * 7.执行InstantiationAwareBeanPostProcessor的postProcessPropertyValues方法
     * 8.为Bean注入属性
     * 9.调用BeanNameAware的setBeanName方法
     * 10.调用BeanFactoryAware的setBeanFactory方法
     * 11.执行BeanPostProcessor的postProcessBeforeInitaialization方法
     * 12.调用InitializingBean的afterPropertiesSet方法
     * 13.调用<bean>的 init-method属性指定的初始化方法
     * 14.执行BeanPostProcessor的postProcessAfterInitialization方法
     * 15.执行InstantiationAwareBeanPostProcessor的postProcessAfterInitialization方法
     * 16.程序执行，业务逻辑调用，容器初始化成功，执行正常调用后，下面销毁容器
     * 17.调用DiposibleBean的destory方法
     * 18.调用<bean>的destroy-method属性指定的初始化方法
     *
     */
}
