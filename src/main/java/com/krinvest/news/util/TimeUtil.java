package com.krinvest.news.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static String convertToStr(LocalDateTime time){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        return time.format(formatter);
    }

    /**
     * 문자열 타입의 시간을 LocalDateTime 시간으로 변환한다.
     * @param time 문자열 타입의 시간
     */
    public static LocalDateTime convertToLocalDateTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(time, formatter);
    }

    /**
     * 현재 시간을 문자열로 획득하는 함수
     * @param serverIsKst 현재 서버가 Kst 기준인 지 여부
     */
    public static String getCurrentTimeAsString(boolean serverIsKst){
        /* 현재 시간 획득 */
        LocalDateTime now = LocalDateTime.now();

        /* 현재 서버가 UTC 시간 기준이면 9시간을 더해서 반환 */
        if(!serverIsKst){
            now = now.plusHours(9);
        }
        return convertToStr(now);
    }

    /**
     * 현재 시간을 LocalDateTime으로 획득하는 함수
     * @param serverIsKst 현재 서버가 Kst 기준인 지 여부
     */
    public static LocalDateTime getCurrentTimeAsLocalDateTime(boolean serverIsKst){
        /* 현재 시간 획득 */
        LocalDateTime now = LocalDateTime.now();

        /* 현재 서버가 UTC 시간 기준이면 9시간을 더해서 반환 */
        if(!serverIsKst){
            now = now.plusHours(9);
        }
        return  now;
    }
    
    /**
     * 이베스트투자증권용으로 사용되는 값으로서, 이베스트투자증권의 경우 토큰 값의 만료기간을 정수값(초)로 반환한다.
     * 따라서, 현재 시간을 획득하여 해당 초만큼 더한 다음 로컬에 저장해두어야한다.
     * @param serverIsKst  현재 서버가 Kst 기준인 지 확인
     * @param expireIn 만료기간(초)
     */
    public static String getExpireTime(boolean serverIsKst, int expireIn){
        LocalDateTime now = LocalDateTime.now();
        if(!serverIsKst){
            now = now.plusHours(9);
        }

        return convertToStr(now.plusSeconds(expireIn));
    }

    /**
     * 두개의 시간을 비교하는 함수.
     * time1이 time2 보다 더 이후이면 1 반환
     * time1이 time2 보다 더 이전이면 -1 반환
     * time1과 time2가 똑같은시간이면 0 반환
     * @param time1 비교 대상 시간 1
     * @param time2 비교 대상 시간 2
     */
    public static int compareTime(String time1, String time2){
        LocalDateTime localDateTime1 = convertToLocalDateTime(time1);
        LocalDateTime localDateTime2 = convertToLocalDateTime(time2);

        return localDateTime1.compareTo(localDateTime2);
    }
}
