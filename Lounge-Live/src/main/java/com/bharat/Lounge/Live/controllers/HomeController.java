package com.bharat.Lounge.Live.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    

    @GetMapping("/login")
    public String getUser(){
        return "login";
    }
    @GetMapping("/success")
    public String success(){
        return "login success";
    }
}
