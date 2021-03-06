package org.valuereporter.activity;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by baardl on 31.07.17.
 */
public class ManualActivitySenderTest {
    private static final Logger log = getLogger(ManualActivitySenderTest.class);

    private static String host = "localhost";
    private static String port = "4901";
    private static String serviceName = "manual-test";

    //Manual testing
    public static void main(String[] args) {

        List<ObservedActivity> observedActivities = new ArrayList<>();
        CommandActivitySender commandSender = new CommandActivitySender(host, port, serviceName, observedActivities);
        log.info("Forward {} activities to http://{}:{} with serviceName {}. \n" +
                "\tTo view visit http://{}:{}/reporter/...TODO", observedActivities.size(), host, port, serviceName,host,port);//TODO correct url
        commandSender.run();
        log.info("Done");

    }

}