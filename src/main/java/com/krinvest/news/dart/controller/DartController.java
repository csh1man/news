package com.krinvest.news.dart.controller;


import com.krinvest.news.dart.service.DartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class DartController {

    @Autowired
    private DartService dartService;

    @GetMapping("/dart/gonsi/kospi/high-item")
    public void getGongsiInfo(){

    }
}
