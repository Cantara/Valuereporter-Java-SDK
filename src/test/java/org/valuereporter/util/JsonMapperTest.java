package org.valuereporter.util;

import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;
import static org.testng.Assert.assertNotNull;

/**
 * Created by baardl on 16.09.17.
 */
public class JsonMapperTest {
    private static final Logger log = getLogger(JsonMapperTest.class);
    @BeforeMethod
    public void setUp() throws Exception {
    }

    @Test
    public void testToJson() throws Exception {
    }

    @Test
    public void testToJsonMap() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("string", "String");
        map.put("int",1);
        String json = JsonMapper.toJson(map);
        assertNotNull(json);
        log.trace("Found map: {}",json);
        String expected = "{\"string\": \"String\",\"int\": 1}";
        JSONAssert.assertEquals(expected, json, false);
    }

    @Test
    public void testToJsonMapString() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("string", "String");
        map.put("int","1");
        String json = JsonMapper.toJson(map);
        assertNotNull(json);
        log.trace("Found map: {}",json);
        String expected = "{\"string\": \"String\",\"int\": \"1\"}";
        JSONAssert.assertEquals(expected, json, false);
    }

}