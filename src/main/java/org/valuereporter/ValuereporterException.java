package org.valuereporter;

import org.slf4j.helpers.MessageFormatter;

import java.util.UUID;

/**
 * Created by baardl on 18.09.17.
 */
public class ValuereporterException extends RuntimeException {
    private final UUID uuid;


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

    @Override
    public String getMessage() {

        String message = super.getMessage() +" MessageId: " + uuid.toString();
        if (getCause() != null) {
            message = message + "\n\tCause: " + getCause().getMessage();
        }
        return message;
    }

    protected String getMessageId() {
        return uuid.toString();
    }
}
