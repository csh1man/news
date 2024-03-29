package com.krinvest.news.naver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NaverNewsController {
    @GetMapping("/naver/search")
    public String searchNews(@RequestParam String name){
        return name;
    }
}
