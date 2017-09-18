package org.valuereporter.http;

import com.github.kevinsawicki.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.valuereporter.ImplementedMethod;
import org.valuereporter.util.JsonMapper;

import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class HttpImplementedMethodSender implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(HttpImplementedMethodSender.class);
    private final String reporterHost;
    private final String reporterPort;
    private final String serviceName;
    private static final int STATUS_BAD_REQUEST = 400; //Response.Status.BAD_REQUEST.getStatusCode();
    private static final int STATUS_OK = 200; //Response.Status.OK.getStatusCode();
    private static final int STATUS_FORBIDDEN = 403;
    private String implementedMethodsAsJson;

    public HttpImplementedMethodSender(String reporterHost, String reporterPort, String serviceName, List<ImplementedMethod> implementedMethods) {
        this.reporterHost = reporterHost;
        this.reporterPort = reporterPort;
        this.serviceName = serviceName;
        this.implementedMethodsAsJson = buildJson(implementedMethods);
    }

    private String buildJson(List<ImplementedMethod> implementedMethods)  {
        String json = JsonMapper.toJson(implementedMethods);

        return json;
    }



    @Override
    public void run() {
        String implMethodUrl = "http://"+reporterHost + ":" + reporterPort +"/reporter/observe" + "/implementedmethods/" + serviceName;
        log.info("Connection to ValueReporter on {}" , implMethodUrl);
        log.trace("Forwarding implementedMethods as Json \n{}", implementedMethodsAsJson);
        HttpRequest request = HttpRequest.post(implMethodUrl ).acceptJson().contentType(HttpSender.APPLICATION_JSON).send(implementedMethodsAsJson);
        int statusCode = request.code();
        String responseBody = request.body();
        switch (statusCode) {
            case HttpSender.STATUS_OK:
                log.trace("Updated via http ok. Response is {}", responseBody);
                break;
            case HttpSender.STATUS_FORBIDDEN:
                log.warn("Can not access ValueReporter. The application will function as normally, though Observation statistics will not be stored. URL {}, HttpStatus {}, Response {}, ", implMethodUrl,statusCode, responseBody);
                break;
            default:
                log.error("Error while accessing ValueReporter. The application will function as normally, though Observation statistics will not be stored. URL {}, HttpStatus {},Response from ValueReporter {}",implMethodUrl, statusCode, responseBody);
        }
    }
}
