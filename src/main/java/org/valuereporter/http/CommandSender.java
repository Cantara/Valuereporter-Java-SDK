package org.valuereporter.http;

import org.slf4j.Logger;
import org.valuereporter.ObservedMethod;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by baardl on 25.07.15.
 */
public class CommandSender implements Runnable {
    private static final Logger log = getLogger(CommandSender.class);
    private final String serviceName;
    private final List<ObservedMethod> observedMethods;
    private final String reporterHost;
    private final String reporterPort;

    public CommandSender(final String reporterHost, final String reporterPort, final String serviceName, final List<ObservedMethod> observedMethods) {
        this.reporterHost = reporterHost;
        this.reporterPort = reporterPort;
        this.serviceName = serviceName;
        this.observedMethods = observedMethods;
    }

    @Override
    public void run() {

        String commandStatus = new CommandSendObservations(reporterHost,reporterPort, serviceName,observedMethods).execute();
        log.trace("Ran CommandSendObservations. Status {}", commandStatus);

    }
}
