package org.valuereporter.activity;

import org.codehaus.jackson.map.ObjectMapper;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static org.testng.Assert.assertNotNull;

/**
 * Created by baardl on 02.03.16.
 */
public class CommandSendActivitiesTest {
    private static final Logger log = getLogger(CommandSendActivitiesTest.class);

    private ObjectMapper objectMapper;
    private CommandSendActivities sendActivities;

    @BeforeMethod
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();



    }

    @Test
    public void testBuildJson() throws Exception {
        List<ObservedActivity> observedActivities = new ArrayList();
        ObservedActivity observedActivity = new ObservedActivity("logon",1456904461955L);
        observedActivity.addContextInfo("userId", "me");
        observedActivity.setServiceName("service1");
        observedActivities.add(observedActivity);
        sendActivities = new CommandSendActivities(null,null,"test",observedActivities);
        String observedActivitiesJson = sendActivities.getObservedActivitiesJson();
        assertNotNull(observedActivitiesJson);
        JSONAssert.assertEquals(expectedSingle,observedActivitiesJson,true);

    }

    public static void main(String[] args) {
        List<ObservedActivity> observedActivities = new ArrayList();
        ObservedActivity userSession = new ObservedActivity("userSession", System.currentTimeMillis());
        userSession.addContextInfo("usersessionfunction","userSessionAccess");
        userSession.addContextInfo("applicationid","100");
        userSession.addContextInfo("userid", "test-only");
        userSession.addContextInfo("applicationtokenid", "to-be-set");
        observedActivities.add(userSession);
        ObservedActivity userLogon = new ObservedActivity("userLogon", System.currentTimeMillis());
        userLogon.addContextInfo("usersessionfunction","userLogon");
        userLogon.addContextInfo("applicationid","100");
        userLogon.addContextInfo("userid", "test-only");
        userLogon.addContextInfo("applicationtokenid", "to-be-set");
        observedActivities.add(userLogon);
        String host = "localhost";
        String port = "4901";
        String serviceName = "initial";
        //Send
        CommandSendActivities sendActivities = new CommandSendActivities(host,port,serviceName,observedActivities);
        //Validate
        String observedActivitiesJson = sendActivities.getObservedActivitiesJson();
        log.info("Received {}", observedActivitiesJson);


    }

    private static final String expectedSingle = "[{\"serviceName\":\"service1\",\"activityName\":\"logon\",\"startTime\":1456904461955,\"contextInfo\":{\"userId\":\"me\"}}]";
}