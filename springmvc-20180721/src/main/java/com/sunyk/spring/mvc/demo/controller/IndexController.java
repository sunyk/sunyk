package com.sunyk.spring.mvc.demo.controller;

import com.sunyk.spring.mvc.demo.service.INameService;
import com.sunyk.spring.mvc.demo.service.IService;
import com.sunyk.spring.mvc.framework.annotation.Autowired;
import com.sunyk.spring.mvc.framework.annotation.Controller;
import com.sunyk.spring.mvc.framework.annotation.RequestMapping;
import com.sunyk.spring.mvc.framework.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by sunyang on 2018/7/22 0:20
 * For me:One handred lines of code every day,make myself stronger.
 */
@Controller
@RequestMapping("/web")
public class IndexController {

    @Autowired
    IService service;
    @Autowired("myName")
    INameService nameService;

    @RequestMapping("/index.json")
    public void index(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam(value = "name") String name){
        out(response,"Hello "+ name);
    }

    public void  out( HttpServletResponse response,String str){
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










}

