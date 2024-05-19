package com.narae.fliwith.config.util;

import java.util.HashMap;
import java.util.Map;

public class AreaCodeUtil {
    private static final Map<String, String> areaCodeMap = new HashMap<>();

    static {
        areaCodeMap.put("서울", "1");
        areaCodeMap.put("경기", "31");
        areaCodeMap.put("인천", "2");
        areaCodeMap.put("강원", "32");
        areaCodeMap.put("대전", "3");
        areaCodeMap.put("세종", "8");
        areaCodeMap.put("전남", "38");
        areaCodeMap.put("전북", "37");
        areaCodeMap.put("충남", "34");
        areaCodeMap.put("충북", "33");
        areaCodeMap.put("광주", "5");
        areaCodeMap.put("경남", "36");
        areaCodeMap.put("경북", "35");
        areaCodeMap.put("대구", "4");
        areaCodeMap.put("부산", "6");
        areaCodeMap.put("제주", "39");
        areaCodeMap.put("울산", "7");
    }

    public static String convert(String korean) {
        return areaCodeMap.get(korean);
    }
}
