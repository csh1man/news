package com.krinvest.news.evest.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.krinvest.news.evest.dto.T1441Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class EvestStockService {
    private String baseUrl = "https://openapi.ebestsec.co.kr:8080";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    public String getNewToken(){
        String url = baseUrl + "/oauth2/token";
        String appKey = "PSf5ETA5fyNsMd0gWwVyBE21sZeX8PKCuCL3";
        String appSecretKey = "X0VZlHLXYrAmNRQ2sMqE8TdQHy8BSHDA";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        String requestBody ="appkey=" + appKey +
                            "&appsecretkey=" + appSecretKey +
                            "&grant_type=client_credentials&scope=oob";

        // HttpEntity 객체 생성
        HttpEntity<String> entity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return responseEntity.getBody();
    }
    /**
     * 상위종목에 대한 종목 정보를 가져온다.
     */
    public List<T1441Info> getHighItem(){
        String url = baseUrl + "/stock/high-item";
        String trCode = "t1441";

        /* 헤더 정보 설정 */
        HttpHeaders httpHeaders = new HttpHeaders();

        /* content type 설정*/
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAcceptCharset(List.of(StandardCharsets.UTF_8));

        /* api key 및 각종 파라미터 설정*/
        httpHeaders.set("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6Ijk0NjU5NDEwLTg2OWMtNDc3Yi1hNzg1LTI4ZTY1NDJhMjAzMCIsIm5iZiI6MTcxMTUxMDgyNSwiZ3JhbnRfdHlwZSI6IkNsaWVudCIsImlzcyI6InVub2d3IiwiZXhwIjoxNzExNTc2Nzk5LCJpYXQiOjE3MTE1MTA4MjUsImp0aSI6IlBTZjVFVEE1ZnlOc01kMGdXd1Z5QkUyMXNaZVg4UEtDdUNMMyJ9.UG8oNpBbp5ygrAZbm8EuYYNz8s1kITtOiwDjmqmAur2FZDA3fQhJ3FMjqCjDGQI_Fws3NtXLXKfoeYjA8i6j-g");
        httpHeaders.set("tr_cd", trCode);
        httpHeaders.set("tr_cont", "N");

        /* 바디 설정 */
        // JsonObject 생성
        JsonObject jsonObject = new JsonObject();

        // 하위 JsonObject 생성
        JsonObject t1441InBlock = new JsonObject();
        t1441InBlock.addProperty("gubun1", "1");
        t1441InBlock.addProperty("gubun2", "0");
        t1441InBlock.addProperty("gubun3", "0");
        t1441InBlock.addProperty("jc_num", 0);
        t1441InBlock.addProperty("sprice", 0);
        t1441InBlock.addProperty("eprice", 0);
        t1441InBlock.addProperty("volume", 0);
        t1441InBlock.addProperty("idx", 0);
        t1441InBlock.addProperty("jc_num", 0x00004000);

        // 상위 JsonObject에 하위 JsonObject 추가
        jsonObject.add("t1441InBlock", t1441InBlock);

        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(jsonObject), httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        String responseBody = responseEntity.getBody();
        JsonObject responseJson = gson.fromJson(responseBody, JsonObject.class);
        JsonArray t1441Blocks = responseJson.get("t1441OutBlock1").getAsJsonArray();

        List<T1441Info> t1441Infos = new ArrayList<>();
        for(JsonElement element : t1441Blocks){
            t1441Infos.add(new T1441Info(element.getAsJsonObject()));
        }

        return t1441Infos;
    }
}
