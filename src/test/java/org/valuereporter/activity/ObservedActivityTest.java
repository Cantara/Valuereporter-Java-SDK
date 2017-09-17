package org.valuereporter.activity;

import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.slf4j.LoggerFactory.getLogger;
import static org.testng.Assert.assertEquals;

/**
 * Created by baardl on 04.03.16.
 */
public class ObservedActivityTest {
    private static final Logger log = getLogger(ObservedActivityTest.class);

    private String activityName;
    private long timestamp;
    private ObservedActivity observedActivity;

    @BeforeMethod
    public void setUp() throws Exception {
        activityName = "testname";
        timestamp = System.currentTimeMillis();
        observedActivity = new ObservedActivity(activityName, timestamp);
        observedActivity.addContextInfo("userid", new String("me"));

    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(activityName, observedActivity.getActivityName());
        assertEquals(timestamp,observedActivity.getStartTime());
        assertEquals("me", observedActivity.getContextInfo().get("userid"));

    }

    @Test
    public void testToJson() throws Exception {
        String expectedJson = "{\"activityName\": \"testname\",\"startTime\": " + timestamp +",\"contextInfo\": {\"userid\": \"me\"}}";
        String actualStr = observedActivity.toJson();
        JSONAssert.assertEquals(expectedJson, actualStr,true);

    }
}