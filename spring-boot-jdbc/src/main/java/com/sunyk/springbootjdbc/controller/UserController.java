package com.sunyk.springbootjdbc.controller;

import com.sunyk.springbootjdbc.domian.User;
import com.sunyk.springbootjdbc.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by sunyang on 2018/9/24 17:52
 * For me:One handred lines of code every day,make myself stronger.
 */
@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/web/mvc/user/save")
    public Boolean save(@RequestBody User user){
        System.out.printf("[Thread name: %s ] start saving  At Controller \n", Thread.currentThread().getName());

        return userRepository.save(user);
    }
}
