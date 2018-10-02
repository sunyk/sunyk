package com.sunyk.springbootjdbc.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Create by sunyang on 2018/9/24 19:31
 * For me:One handred lines of code every day,make myself stronger.
 */
@Configuration
public class MultipleDataSourceConfiguration {

    @Bean
    @Primary
    public DataSource masterDataSource(){


        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        DataSource dataSource = dataSourceBuilder
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://127.0.0.1:3306/user")
                .username("root")
                .password("123456")
                .build();
        return dataSource;

    }

    @Bean
    public DataSource slaveDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        DataSource dataSource = dataSourceBuilder
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://127.0.0.1:3306/user")
                .username("root")
                .password("123456")
                .build();
        return dataSource;
    }
}
