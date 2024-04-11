package com.krinvest.news.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.krinvest.news.ebest.dto.TokenInfo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ConfigUtil {

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String configPath;

    /**
     * 각종 키가 저장되어있는 키값을 메모리에 올리기 위해 메인함수에서 프로젝트 실행하기 전에 반드시 이 함수를 호출하여야한다.
     * @param _configPath 환경설정 파일 경로
     */
    public static void setConfigPath(String _configPath){
        configPath = _configPath;

    }

    /**
     * 환경설정 파일 json 객체 획득
     */
    public static JsonObject getConfigJson(){
        return FileUtil.getLocalJson(configPath);
    }

    /**
     * 특정회사의 api key 정보를 가져온다.
     * @param company 특정 회사 이름
     */
    public static Map<String, String> getApiKey(String company){
        /* 환경설정 파일에서 특정 회사의 필드를 가져온다. */
        JsonObject keyJson = getConfigJson().get(company.toLowerCase()).getAsJsonObject();
        Map<String, String> key = new HashMap<String, String>();

        key.put("access", keyJson.get("access").getAsString());
        if(keyJson.get("secret") != null && !"".equals(keyJson.get("secret").getAsString())){
            key.put("secret", keyJson.get("secret").getAsString());
        }
        if(keyJson.get("token") != null && !"".equals(keyJson.get("token").getAsString())){
            key.put("token", keyJson.get("token").getAsString());
        }
        if(keyJson.get("tokenExpireDate") != null && !"".equals(keyJson.get("tokenExpireDate").getAsString())){
            key.put("tokenExpireDate", keyJson.get("tokenExpireDate").getAsString());
        }

        for(Map.Entry<String, String> entry : key.entrySet()){
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        return key;
    }

    /**
     * 저장해놓은 특정회사의 토큰 만료 시간을 LocalDateTime으로 가져온다.
     * @param company 회사명
     */
    public static LocalDateTime getExpireDateAsLocalDateTime(String company){
        JsonObject config = getConfigJson();
        JsonObject companyJson = config.get(company.toLowerCase()).getAsJsonObject();
        if(companyJson.get("tokenExpireDate") == null){
            return null;
        }
        return TimeUtil.convertToLocalDateTime(companyJson.get("tokenExpireDate").getAsString());
    }

    /**
     * 저장해놓은 특정회사의 토큰 만료 시간을 문자열로 가져온다.
     * @param company 회사명
     */
    public static String getExpireDateAsString(String company){
        JsonObject config = getConfigJson();
        JsonObject companyJson = config.get(company.toLowerCase()).getAsJsonObject();
        if(companyJson.get("tokenExpireDate") == null){
            return null;
        }
        return companyJson.get("tokenExpireDate").getAsString();
    }

    /**
     * 이베스트에서 신규 발급받은 토큰 정보를 환경설정파일에 업데이트 한다.
     * @param evestTokenInfo evestTokenInfo
     */
    public static void updateEvestTokenInfo(TokenInfo evestTokenInfo){
        /* evest json 데이터 획득 */
        JsonObject config = getConfigJson();
        JsonObject evestConfig = config.get("evest").getAsJsonObject();

        /* 신규 토큰 정보 할당 */
        evestConfig.addProperty("token", evestTokenInfo.getAccessToken());
        evestConfig.addProperty("tokenExpireDate", TimeUtil.getExpireTime(true, evestTokenInfo.getExpiresIn()));
        
        /* 파일에 업로드 */
        FileUtil.writeJson(configPath, config);
    }

    public static boolean isKstTimeServer(){
        JsonObject config = getConfigJson();
        return config.get("isKst").getAsBoolean();
    }
}
