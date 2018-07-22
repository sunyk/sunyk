package com.sunyk.spirngmvc.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by sunyang on 2018/7/21 17:46
 * For me:One handred lines of code every day,make myself stronger.
 */
@Controller
@RequestMapping("/web")
public class UserController {

    @RequestMapping("/{url}/query.json")
    public void query(
            @RequestParam("name") String name,
            @PathVariable("url")String url,
            HttpServletRequest request, HttpServletResponse response){
        try {
            response.getWriter().println("query:" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/index.json")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        try {
            response.getWriter().println("query: name" );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
