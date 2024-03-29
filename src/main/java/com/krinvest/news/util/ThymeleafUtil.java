package com.krinvest.news.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Component
public class ThymeleafUtil {
    public String addCommas(BigDecimal number) {
        // 콤마를 추가하기 위해 DecimalFormat 객체를 생성합니다.
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        // BigDecimal 값을 문자열로 변환하여 콤마를 추가한 후 반환합니다.
        return decimalFormat.format(number);
    }
}
