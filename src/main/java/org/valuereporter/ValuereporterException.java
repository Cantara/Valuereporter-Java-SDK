package org.valuereporter;

import org.slf4j.helpers.MessageFormatter;

import java.util.UUID;

/**
 * Created by baardl on 18.09.17.
 */
public class ValuereporterException extends RuntimeException {
    private final UUID uuid;
    private Enum<StatusType> statusType = null;


    public ValuereporterException(String message) {
        super(message);
        uuid = UUID.randomUUID();
    }

    public ValuereporterException(String message, Throwable throwable) {
        super(message, throwable);
        this.uuid = UUID.randomUUID();
    }

    public ValuereporterException(String message, Throwable throwable, Object... parameters) {
        this(MessageFormatter.format(message, parameters).getMessage(),throwable);

    }

    public ValuereporterException(String msg, StatusType statusType) {
        this(msg);
        this.statusType = statusType;
    }
    public ValuereporterException(String msg, Throwable t, StatusType statusType) {
        this(msg,t);
        this.statusType = statusType;
    }

    public ValuereporterException(String msg, Exception e, StatusType statusType) {
        this(msg, e);
        this.statusType = statusType;
    }

    @Override
    public String getMessage() {

        String message = super.getMessage() +" MessageId: " + uuid.toString();
        if (getCause() != null) {
            message = message + "\n\tCause: " + getCause().getMessage();
        }
        return message;
    }

    public String getMessageId() {
        return uuid.toString();
    }

    public String getStatusTypeText() {
        String statusTypeText = "";
        if (statusType != null) {
            statusTypeText = statusType.name();
        }
        return statusTypeText;
    }

    public Enum<StatusType> getStatusType() {
        return statusType;
    }
}
