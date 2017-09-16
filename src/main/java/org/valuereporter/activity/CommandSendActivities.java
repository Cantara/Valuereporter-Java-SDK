package org.valuereporter.activity;

import com.github.kevinsawicki.http.HttpRequest;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.valuereporter.http.HttpSender;
import org.valuereporter.util.JsonMapper;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class CommandSendActivities extends HystrixCommand<String>  {

    private static final Logger log = getLogger(CommandSendActivities.class);

    private final String prefix;
    private final List<ObservedActivity> observedActivities;
    private final String reporterHost;
    private final String reporterPort;
    private static final int STATUS_OK = 200;
    private static final int STATUS_FORBIDDEN = 403;
    private final String observedActivitiesJson;
    private final int no_of_activities;

    public CommandSendActivities(final String reporterHost, final String reporterPort, final String prefix, final List<ObservedActivity> observedActivities) {
        super(HystrixCommandGroupKey.Factory.asKey("ValueReporterAgent-group"));
        observedActivitiesJson = buildJson(observedActivities);
        no_of_activities = observedActivities.size();
        this.reporterHost = reporterHost;
        this.reporterPort = reporterPort;
        this.prefix = prefix;
        this.observedActivities = observedActivities;
    }

    protected String buildJson(List<ObservedActivity> observedActivities)  {
        String json = JsonMapper.toJson(observedActivities);

        return json;
    }

    @Override
    protected String run() {
//        Client client = ClientBuilder.newClient();
//        String observationUrl = "http://"+reporterHost + ":" + reporterPort +"/reporter/observe/activities";
//        log.info("Connection to ValueReporter on {}" , observationUrl);
//        final WebTarget observationTarget = client.target(observationUrl);
//        WebTarget webResource = observationTarget.path(prefix);
//        log.trace("Forwarding observedActivities as Json \n{}", observedActivitiesJson);
//        Response response = webResource.request(MediaType.APPLICATION_JSON).post(Entity.entity(observedActivitiesJson, MediaType.APPLICATION_JSON));
//        int statusCode = statusCode;

        String observationUrl = "http://"+reporterHost + ":" + reporterPort +"/reporter/observe" + "/activities/" + prefix;
        log.info("Connection to ValueReporter on {} num of activities: {}" , observationUrl,no_of_activities);
        HttpRequest request = HttpRequest.post(observationUrl ).acceptJson().contentType(HttpSender.APPLICATION_JSON).send(observedActivitiesJson);
        int statusCode = request.code();
        String responseBody = request.body();
        switch (statusCode) {
            case HttpSender.STATUS_OK:
                log.trace("Updated via http ok. Response is {}", responseBody);
                break;
            case HttpSender.STATUS_FORBIDDEN:
                log.warn("Can not access ValueReporter. The application will function as normally, though Observation statistics will not be stored. URL {}, HttpStatus {}, Response {}, ", observationUrl,statusCode, responseBody);
                break;
            default:
                log.trace("Retrying access to ValueReporter");
                request = HttpRequest.post(observationUrl ).acceptJson().contentType(HttpSender.APPLICATION_JSON).send(observedActivitiesJson);
                statusCode = request.code();
                responseBody = request.body();
                if (statusCode == HttpSender.STATUS_OK) {
                    log.trace("Retry via http ok. Response is {}", responseBody);
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

    public String getObservedActivitiesJson() {
        return observedActivitiesJson;
    }
}