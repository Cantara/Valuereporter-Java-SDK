package org.valuereporter.util;

import org.valuereporter.Observation;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by baardl on 16.09.17.
 */
public class JsonMapper {

    public static String toJson(List<? extends Observation> observations) {
        String result = "";
        if (observations != null && observations.size() > 0) {
            result = "[";
            result += observations.stream()
                    .map( n -> n.toJson() )
                    .collect( Collectors.joining( "," ) );
            result += "]";
        }
        return result;

    }


    public static String toJson(Map<String,Object> map) {
        String result = "";
        if (map != null && map.keySet() != null) {
            result = "{";
            Set<String> keys = map.keySet();
            for (String key : keys) {
                Object value = map.get(key);
                if (value instanceof String) {
                    result += "\"" + key + "\": \"" + value +"\"";
                } else {
                    result += "\"" + key + "\": " + value;
                }
                result += ",";
            }
            result = removeLastChar(result);
            result += "}";

        }
        return result;

    }

    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }


}
