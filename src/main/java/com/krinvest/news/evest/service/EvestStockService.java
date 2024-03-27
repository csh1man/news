package com.krinvest.news.evest.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.krinvest.news.evest.dto.T1441Info;
import com.krinvest.news.evest.dto.TokenInfo;
import com.krinvest.news.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EvestStockService {
    private String baseUrl = "https://openapi.ebestsec.co.kr:8080";
    private Map<String, String> key;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    public EvestStockService(){
        this.key = ConfigUtil.getApiKey("evest");
    }
    public String getNewToken(){
        String url = baseUrl + "/oauth2/token";

        String appKey = key.get("access");
        String appSecretKey = key.get("secret");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        String requestBody ="appkey=" + appKey +
                            "&appsecretkey=" + appSecretKey +
                            "&grant_type=client_credentials&scope=oob";

        // HttpEntity 객체 생성
        HttpEntity<String> entity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        /* response json 에서 expires_in 획득하여 config.json에 업로드 */
        TokenInfo tokenInfo = gson.fromJson(responseEntity.getBody(), TokenInfo.class);
        ConfigUtil.updateEvestTokenInfo(tokenInfo);

        /* 업로드된 파일 메모리에 재할당 */
        this.key = ConfigUtil.getApiKey("evest");

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
        httpHeaders.set("authorization", "Bearer " + key.get("token"));
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
