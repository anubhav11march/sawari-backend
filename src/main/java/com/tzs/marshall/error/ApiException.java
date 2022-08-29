package com.tzs.marshall.error;

public class ApiException extends RuntimeException {

    public ApiException() {
        super();
    }

    public ApiException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ApiException(final String message) {
        super(message);
    }

    public ApiException(final Throwable cause) {
        super(cause);
    }
}
