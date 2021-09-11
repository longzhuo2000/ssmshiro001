package com.zking.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class IndexController {

    @RequestMapping("/go")
    public String goIndex(){
        return "index";
    }
}
