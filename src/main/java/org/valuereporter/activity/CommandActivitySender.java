package org.valuereporter.activity;

import org.slf4j.Logger;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by baardl on 25.07.15.
 */
public class CommandActivitySender implements Runnable {
    private static final Logger log = getLogger(CommandActivitySender.class);
    private final String serviceName;
    private final List<ObservedActivity> observedActivities;
    private final String reporterHost;
    private final String reporterPort;

    public CommandActivitySender(final String reporterHost, final String reporterPort, final String serviceName, final List<ObservedActivity> observedActivities) {
        this.reporterHost = reporterHost;
        this.reporterPort = reporterPort;
        this.serviceName = serviceName;
        List<ObservedActivity> activitiesToSend = observedActivities;
        this.observedActivities = activitiesToSend;
    }

    @Override
    public void run() {

        String commandStatus = new CommandSendActivities(reporterHost,reporterPort, serviceName, observedActivities).execute();
        log.trace("Ran CommandSendObservations. Status {}", commandStatus);

    }
}
