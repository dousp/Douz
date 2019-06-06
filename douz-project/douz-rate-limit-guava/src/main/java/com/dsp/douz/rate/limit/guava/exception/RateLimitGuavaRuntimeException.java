package com.dsp.douz.rate.limit.guava.exception;

public class RateLimitGuavaRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -4189623063764606371L;

    public RateLimitGuavaRuntimeException() {
        super();
    }

    public RateLimitGuavaRuntimeException(String message) {
        super(message);
    }

    public RateLimitGuavaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RateLimitGuavaRuntimeException(Throwable cause) {
        super(cause);
    }
}
