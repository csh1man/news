package com.krinvest.news.evest.controller;

import com.krinvest.news.evest.dto.T1441Info;
import com.krinvest.news.evest.service.EvestStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class EvestStockController {

    @Autowired
    private EvestStockService evestStockService;

    @GetMapping("/evest/issue/token")
    public void issueNewToken(){
        String response = evestStockService.getNewToken();
        System.out.println(response);
    }

    /**
     * 거래량 상위 종목을 가져온다.
     */
    @GetMapping("/evest/stock/high-item")
    public String getHighItem(Model model){
        List<T1441Info> t1441Infos = evestStockService.getHighItem();
        for(T1441Info t1441Info : t1441Infos){
            System.out.println(t1441Info.toString());
        }
        model.addAttribute("highItems", t1441Infos);

        return "stock/highItem";
    }
    @GetMapping("/company")
    public String test(){
        return "stock/test";
    }
}
