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
public class ObservedMethodTest {
    private static final Logger log = getLogger(ObservedMethodTest.class);
    private ObservedMethod method;
    @BeforeMethod
    public void setUp() throws Exception {
        method = new ObservedMethod("testObservation", 1505563307553L, 1505563307553L);

    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("testObservation", method.getName());
    }

    @Test
    public void testGetStartTime() throws Exception {
        assertEquals(1505563307553L, method.getStartTime());
    }

    @Test
    public void testGetEndTime() throws Exception {
        assertEquals(1505563307553L, method.getEndTime());
    }

    @Test
    public void testToJson() throws Exception {
        String expected = "{\"name\": \"testObservation\",\"startTime\": 1505563307553,\"endTime\": 1505563307553}";
        String json = method.toJson();
        log.trace("Json: {}", json);
        JSONAssert.assertEquals(expected, json, false);
    }

}