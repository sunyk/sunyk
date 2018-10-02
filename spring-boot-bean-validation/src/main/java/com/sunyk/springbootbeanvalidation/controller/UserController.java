package com.sunyk.springbootbeanvalidation.controller;

import com.sunyk.springbootbeanvalidation.domian.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Create by sunyang on 2018/10/2 20:08
 * For me:One handred lines of code every day,make myself stronger.
 */
@RestController
public class UserController {

    @PostMapping("/user/save")
    public void save(@Valid @RequestBody User user, HttpServletResponse response) throws IOException {
        response.getWriter().write(user.toString());
    }
}
