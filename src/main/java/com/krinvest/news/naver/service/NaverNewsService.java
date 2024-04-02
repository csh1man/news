package com.krinvest.news.naver.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.krinvest.news.util.ConfigUtil;
import com.krinvest.news.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class NaverNewsService {

    private String baseUrl = "https://openapi.naver.com/v1/search/news.json?query=";

    private Map<String, String> key;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    public NaverNewsService(){
        key = ConfigUtil.getApiKey(DataUtil.NAVER);
    }

    public String searchNews(String orgName){
        String url = baseUrl + orgName + " 주가&display=50&start=1&sort=sim";

        /* 헤더 정보 설정 */
        HttpHeaders httpHeaders = new HttpHeaders();

        /* content type 설정*/
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAcceptCharset(List.of(StandardCharsets.UTF_8));

        /* api key 및 각종 파라미터 설정*/
        httpHeaders.set("X-Naver-Client-Id", key.get(DataUtil.ACCESS_STR));
        httpHeaders.set("X-Naver-Client-Secret", key.get(DataUtil.SECRET_STR));

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String response = responseEntity.getBody();

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonArray items = jsonObject.getAsJsonArray("items");

        return items.toString();
    }
}
