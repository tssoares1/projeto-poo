package com.fag.infra.utils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public Map<String, Object> toJson(String apiResponse){
        try {
            Pattern pattern = Pattern.compile("\"(\\w+)\"\\s*:\\s*(\"[^\"]*\"|\\d+|true|false|null)");
            Matcher matcher = pattern.matcher(apiResponse);

            Map<String, Object> jsonData = new HashMap<>();

            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
    
                jsonData.put(key, value.replace("\"", ""));
            }

             return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
