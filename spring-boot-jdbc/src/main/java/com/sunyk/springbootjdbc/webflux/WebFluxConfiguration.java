package com.sunyk.springbootjdbc.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Create by sunyang on 2018/9/24 18:01
 * For me:One handred lines of code every day,make myself stronger.
 */
@Configuration
public class WebFluxConfiguration {

    @Bean
    public RouterFunction<ServerResponse> saveUser(UserHandler userHandler){
        return route(POST("/web/flux/user/save"),userHandler::save);
    }
}
