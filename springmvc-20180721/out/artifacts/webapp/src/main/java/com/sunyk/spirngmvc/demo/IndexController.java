package com.sunyk.spirngmvc.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create by sunyang on 2018/7/22 0:20
 * For me:One handred lines of code every day,make myself stronger.
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String hello(){
        System.out.println(11111);
        return "index";
    }
}
