package com.krinvest.news.ebest.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.krinvest.news.ebest.dto.T1441Info;
import com.krinvest.news.ebest.dto.T1452Info;
import com.krinvest.news.ebest.dto.TokenInfo;
import com.krinvest.news.util.ConfigUtil;
import com.krinvest.news.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

    public EvestStockService() {
        this.key = ConfigUtil.getApiKey("evest");
        
    }

    private boolean checkAppKey(){
        String appKey = key.get("access");
        String appSecretKey = key.get("secret");
        if(appKey == null || "".equals(appKey) || appSecretKey == null || "".equals(appSecretKey)){
            System.out.println("evest의 API 키 값 셋팅이 올바르지 않습니다. 확인 부탁드립니다.");
            return false;
        }
        return true;
    }
    /**
     * 현재 내가 가지고 있는 토큰의 만료기간이 지났는 지 여부를 확인하는 함수.
     * 반드시, api 호출마다 확인을 해주고 만료되었을 경우, 반드시 재발행해야한다.
     */
    private boolean isTokenExpired(){
        String currentTime = TimeUtil.getCurrentTimeAsString(ConfigUtil.isKstTimeServer());
        String expireTime = ConfigUtil.getExpireDateAsString("evest");
        if(TimeUtil.compareTime(currentTime, expireTime) >= 0){
            return true;
        }

        return false;
    }

    /**
     * 신규 토큰을 발급받는다.
     */
    public String getNewToken(){
        String url = baseUrl + "/oauth2/token";

        /* api key 획득 */
        String appKey = key.get("access");
        String appSecretKey = key.get("secret");

        if(!checkAppKey()){
            return null;
        }
        
        /* 헤더 설정 */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        /* 바디 파라미터 설정 */
        String requestBody ="appkey=" + appKey +
                            "&appsecretkey=" + appSecretKey +
                            "&grant_type=client_credentials&scope=oob";

        /* Request Body를 위한 entity 생성 및 요청 */
        HttpEntity<String> entity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        /* response json 에서 expires_in 획득하여 설정파일에 업로드 */
        TokenInfo tokenInfo = gson.fromJson(responseEntity.getBody(), TokenInfo.class);
        ConfigUtil.updateEvestTokenInfo(tokenInfo);
        System.out.println("신규 토큰 : [" + tokenInfo.getAccessToken()+"]");
        /* 업로드된 파일 메모리에 재할당 */
        this.key = ConfigUtil.getApiKey("evest");

        /* 신규 토큰 반환 */
        return tokenInfo.getAccessToken();
    }

    /**
     * 토큰이 만료되었는 지 확인하고 만료되었을 경우 재발행하여 반환한다.
     */
    private String getToken(){
        boolean isExpired = isTokenExpired();
        if(isExpired){
            return getNewToken();
        }

        return key.get("token");
    }

    /**
     * 등락률 상위 종목을 가져온다.
     */
    public List<T1441Info> getDiffHighItem(){
        String url = baseUrl + "/stock/high-item";
        String trCode = "t1441";

        /* 헤더 정보 설정 */
        HttpHeaders httpHeaders = new HttpHeaders();

        /* content type 설정*/
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAcceptCharset(List.of(StandardCharsets.UTF_8));

        /* api key 및 각종 파라미터 설정*/
        httpHeaders.set("authorization", "Bearer " + getToken());
        httpHeaders.set("tr_cd", trCode);
        httpHeaders.set("tr_cont", "N");

        /* 바디 설정 */
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

        JsonObject jsonObject = new JsonObject();
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

    /**
     * 거래량 상위 종목을 가져온다.
     */
    public List<T1452Info> getVolumeHighItem(){
        String url = baseUrl + "/stock/high-item";
        String trCode = "t1452";

        /* 헤더 정보 설정 */
        HttpHeaders httpHeaders = new HttpHeaders();

        /* content type 설정*/
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAcceptCharset(List.of(StandardCharsets.UTF_8));

        /* api key 및 각종 파라미터 설정*/
        httpHeaders.set("authorization", "Bearer " + getToken());
        httpHeaders.set("tr_cd", trCode);
        httpHeaders.set("tr_cont", "N");

        /* 바디 설정 */
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

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("t1452InBlock", t1441InBlock);

        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(jsonObject), httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        String responseBody = responseEntity.getBody();
        JsonObject responseJson = gson.fromJson(responseBody, JsonObject.class);
        JsonArray t1452Blocks = responseJson.get("t1452OutBlock1").getAsJsonArray();

        List<T1452Info> t1452Infos = new ArrayList<>();
        for(JsonElement element : t1452Blocks){
            t1452Infos.add(new T1452Info(element.getAsJsonObject()));
        }

        return t1452Infos;
    }
}
