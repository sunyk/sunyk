package com.sunyk.springbootjdbc.webflux;

import com.sunyk.springbootjdbc.domian.User;
import com.sunyk.springbootjdbc.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Create by sunyang on 2018/9/24 18:08
 * For me:One handred lines of code every day,make myself stronger.
 */
@Component
public class UserHandler {

    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest){
        System.out.printf("[Thread name: %s ] start saving ...At WebFlux\n", Thread.currentThread().getName());
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        Mono<Boolean> booleanMono = userMono.map(userRepository::save);
        return ServerResponse.ok().body(booleanMono, Boolean.class);
    }
}
