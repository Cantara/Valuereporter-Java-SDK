package org.valuereporter.http;

import com.github.kevinsawicki.http.HttpRequest;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.valuereporter.ObservedMethod;
import org.valuereporter.ValuereporterException;
import org.valuereporter.util.JsonMapper;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by totto on 25.06.15.
 */
public class CommandSendObservations extends HystrixCommand<String>  {

    private static final Logger log = getLogger(CommandSendObservations.class);

    private final String serviceName;
    private final String reporterHost;
    private final String reporterPort;
    private final String observedMethodsJson;

    /**
     *
     * @param reporterHost hostname
     * @param reporterPort port
     * @param serviceName common name of the Microservice, or Application
     * @param observedMethods List of observations. May not be null, nor empty.
     * @throws ValuereporterException When observedMethods is null or empty list
     */
    public CommandSendObservations(final String reporterHost, final String reporterPort, final String serviceName, final List<ObservedMethod> observedMethods) {
        super(HystrixCommandGroupKey.Factory.asKey("ValueReporterAgent-group"));
        if (observedMethods == null || observedMethods.size() == 0) {
            throw new ValuereporterException("ObservedMethods can not be Null nor Empty.");
        }
        if (observedMethods != null) {
            log.trace("Build observedMethods json from {} methods.", observedMethods.size());
        }

        observedMethodsJson = buildJson(observedMethods);
        this.reporterHost = reporterHost;
        this.reporterPort = reporterPort;
        this.serviceName = serviceName;
    }

    protected String buildJson(List<ObservedMethod> observedMethods)  {
        String json = JsonMapper.toJson(observedMethods);

        return json;
    }

    @Override
    protected String run() {

        String observationUrl = "http://"+reporterHost + ":" + reporterPort +"/reporter/observe" + "/observedmethods/" + serviceName;
        log.info("Connection to ValueReporter on {}." , observationUrl);
        log.trace("Forward observed methods to {}. Payload [{}].", observationUrl, observedMethodsJson);
        HttpRequest request = HttpRequest.post(observationUrl ).acceptJson().contentType(HttpSender.APPLICATION_JSON).send(observedMethodsJson);
        int statusCode = request.code();
        String responseBody = request.body();

        log.trace("Status {}, body {}", statusCode, responseBody);
        switch (statusCode) {
            case HttpSender.STATUS_OK:
                log.trace("Updated via http ok. Response is {}", responseBody);
                break;
            case HttpSender.STATUS_FORBIDDEN:
                log.warn("Can not access ValueReporter. The application will function as normally, though Observation statistics will not be stored. URL {}, HttpStatus {}, Response {}, ", observationUrl,statusCode, responseBody);
                break;
            default:
                log.trace("Retrying access to ValueReporter");
                request = HttpRequest.post(observationUrl).acceptJson().contentType(HttpSender.APPLICATION_JSON).send(observedMethodsJson);
                if (request.code() == HttpSender.STATUS_OK) {
                    log.trace("Retry via http ok. Response is {}", request.body());
                } else {
                    log.error("Error while accessing ValueReporter. The application will function as normally, though Observation statistics will not be stored. URL {}, HttpStatus {},Response from ValueReporter {}", observationUrl, statusCode, responseBody);
                }
        }
        return "OK";


    }

    @Override
    protected String getFallback() {
        return "FALLBACK";
    }



}