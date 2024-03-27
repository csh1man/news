package com.krinvest.news.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    /**
     * 로컬에 존재하는 파일을 byte[] 값으로 읽어온다.
     * @param filePath 대상 파일 경로
     */
    public static byte[] getLocalFile(String filePath){
        byte[] ret = null;
        FileInputStream reader = null;
        try{
            File file = new File(filePath);
            reader = new FileInputStream(file);
            ret = new byte[reader.available()];

            reader.read(ret);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(reader != null) reader.close();
            }catch(IOException ee){
                ee.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 로컬에 존재하는 json 파일을 획득하여 JsonObject로 반환한다.
     * @param filePath 대상 json 파일 경로
     */
    public static JsonObject getLocalJson(String filePath){
        byte[] file = getLocalFile(filePath);
        return new Gson().fromJson(new String(file, StandardCharsets.UTF_8), JsonObject.class);
    }

    /**
     * 특정 경로에 json 객체를 .json 파일로 생성한다.
     * @param path 대상 경로
     * @param json jsonObject
     */
    public static void writeJson(String path, JsonObject json){
        try{
            String jsonString = new Gson().toJson(json);
            FileWriter writer = new FileWriter(path);
            writer.write(jsonString);
            writer.close();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
