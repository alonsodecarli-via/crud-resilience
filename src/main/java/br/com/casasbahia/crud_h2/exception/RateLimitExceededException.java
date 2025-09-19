package br.com.casasbahia.crud_h2.exception;

public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
