package com.krinvest.news.ebest.controller;

import com.krinvest.news.ebest.dto.T1441Info;
import com.krinvest.news.ebest.dto.T1452Info;
import com.krinvest.news.ebest.service.EvestStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 이베스트투자증권의 open api를 이용하여 각종 주식 종목들의 정보를 가져오기 위해 만들어진 컨트롤러.
 * 당일의 등락률 순위 또는 당일의 거래량 순위 등을 가져오는데 사용된다.
 */
@Controller
public class EbestStockController {

    @Autowired
    private EvestStockService evestStockService;

    /**
     * 이베스트투자증권의 경우, open api 신청 시 발급받은, app key와 app secret key를 이용해 oauth 토큰을 발급한다.
     * oauth 토큰 값은 모든 요청에 반드시 들어가야하는 값으로서 반드시 발급받아야하며, 한번 발급받으면 약 18시간동안 유효하다.
     * 따라서, 반드시 각 api 실행 전 현재 키가 만료기간이 지났는 지 여부를 확인하는 절차를 추가해야한다.
     */
    @GetMapping("/evest/issue/token")
    public String issueNewToken(){
        return evestStockService.getNewToken();
    }

    /**
     * 등락률 상위 종목 총 20개를 가져온다.
     */
    @GetMapping("/evest/stock/high-item")
    public String getDiffHighItem(Model model){
        List<T1441Info> t1441Infos = evestStockService.getDiffHighItem();
        model.addAttribute("highItems", t1441Infos);

        return "stock/highItem";
    }

    /**
     * 거래량 상위 종목을 가져온다.
     */
    @GetMapping("/evest/stock/volume-high-item")
    public String getVolumeHighItem(Model model){
        List<T1452Info> t1452Infos = evestStockService.getVolumeHighItem();
        model.addAttribute("volumeHighItems", t1452Infos);

        return "stock/volumeHighItem";
    }

    @GetMapping("/company")
    public String test(){
        return "stock/test";
    }
}
