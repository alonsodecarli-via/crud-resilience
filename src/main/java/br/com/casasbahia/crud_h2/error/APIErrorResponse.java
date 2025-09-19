package br.com.casasbahia.crud_h2.error;

import org.springframework.http.HttpStatus;

public class APIErrorResponse {
    private final HttpStatus status;
    private final IAPIError error;

    public APIErrorResponse(HttpStatus status, IAPIError error) {
        this.status = status;
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public IAPIError getError() {
        return error;
    }
}