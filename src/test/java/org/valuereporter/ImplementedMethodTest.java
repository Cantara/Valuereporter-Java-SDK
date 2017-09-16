package org.valuereporter;

import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.slf4j.LoggerFactory.getLogger;
import static org.testng.Assert.assertEquals;

/**
 * Created by baardl on 16.09.17.
 */
public class ImplementedMethodTest {
    private static final Logger log = getLogger(ImplementedMethodTest.class);

    private ImplementedMethod method;

    @BeforeMethod
    public void setUp() throws Exception {
        method = new ImplementedMethod("testMethod");
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(method.getName(),"testMethod");
    }

    @Test
    public void testToJson() throws Exception {
        String expected = "{\"name\": \"testMethod\"}";
        String json = method.toJson();
        log.trace("Json: {}", json);
        JSONAssert.assertEquals(expected, json, false);
    }

}