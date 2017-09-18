package org.valuereporter.http;

import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.valuereporter.ObservedMethod;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static org.testng.Assert.assertNotNull;

/**
 * Created by baardl on 18.09.17.
 */
public class CommandSendObservationsTest {
    private static final Logger log = getLogger(CommandSendObservationsTest.class);

    List<ObservedMethod> observedMethods;
    CommandSendObservations sendObservations;
    @BeforeMethod
    public void setUp() throws Exception {
        observedMethods = new ArrayList<>();
        ObservedMethod method1 = new ObservedMethod("method1", 1505716894519L, 1505716894520L);
        log.trace(method1.toJson());
        observedMethods.add(method1);
        sendObservations = new CommandSendObservations("","","",observedMethods);

    }

    @Test
    public void testBuildJson() throws Exception {
        String json = sendObservations.buildJson(observedMethods);
        log.trace("Json built [{}].", json);
        assertNotNull(json);
        String expectedJson = "[{\"name\": \"method1\",\"startTime\": 1505716894519,\"endTime\": 1505716894520}]";
        JSONAssert.assertEquals(expectedJson,json,true);
    }

}