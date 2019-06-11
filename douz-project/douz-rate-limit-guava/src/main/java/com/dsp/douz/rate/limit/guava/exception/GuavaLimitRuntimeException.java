package com.dsp.douz.rate.limit.guava.exception;

public class GuavaLimitRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -4189623063764606371L;

    public GuavaLimitRuntimeException() {
        super();
    }

    public GuavaLimitRuntimeException(String message) {
        super(message);
    }

    public GuavaLimitRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuavaLimitRuntimeException(Throwable cause) {
        super(cause);
    }
}
