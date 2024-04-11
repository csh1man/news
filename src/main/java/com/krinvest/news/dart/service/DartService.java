package com.krinvest.news.dart.service;

import com.krinvest.news.util.ConfigUtil;
import com.krinvest.news.util.DataUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DartService {
    private Map<String, String> key;

    public DartService(){
        this.key = ConfigUtil.getApiKey(DataUtil.DART);
    }


}
