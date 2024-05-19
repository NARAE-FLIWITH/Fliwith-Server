package com.narae.fliwith.config.util;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeIdUtil {
    private static final Map<String, String> contentTypeIdMap = new HashMap<>();

    static {
        contentTypeIdMap.put("전체", "0");
        contentTypeIdMap.put("관광지", "12");
        contentTypeIdMap.put("문화시설", "14");
        contentTypeIdMap.put("숙박", "32");
        contentTypeIdMap.put("쇼핑", "38");
        contentTypeIdMap.put("음식점", "39");
    }

    public static String convert(String category) {
        return contentTypeIdMap.get(category);
    }
}
