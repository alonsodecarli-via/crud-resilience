package br.com.casasbahia.crud_h2.error;

import br.com.casasbahia.crud_h2.exception.ProductNotFoundException;
import br.com.casasbahia.crud_h2.exception.ProductUnavailableException;
import br.com.casasbahia.crud_h2.exception.RateLimitExceededException;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class APIErrorFactory implements IAPIErrorFactory {

    private final Map<Class<? extends Exception>, Function<Exception, APIErrorResponse>> handlers = new HashMap<>();

    public APIErrorFactory() {
        handlers.put(MethodArgumentNotValidException.class,
                ex -> handleValidationException((MethodArgumentNotValidException) ex));

        handlers.put(BadRequestException.class,
                ex -> buildResponse(ex, HttpStatus.BAD_REQUEST));

        handlers.put(HttpMessageNotReadableException.class,
                ex -> buildResponse(ex, HttpStatus.BAD_REQUEST));

        handlers.put(DataIntegrityViolationException.class,
                ex -> buildDataIntegrityViolationResponse((DataIntegrityViolationException) ex));

        handlers.put(ProductNotFoundException.class,
                ex -> buildResponse(ex, HttpStatus.NOT_FOUND));

        handlers.put(RateLimitExceededException.class,
                ex -> buildResponse(ex, HttpStatus.TOO_MANY_REQUESTS));

        handlers.put(ProductUnavailableException.class,
                ex -> buildResponse(ex, HttpStatus.SERVICE_UNAVAILABLE));
    }

    @Override
    public APIErrorResponse createAPIError(Exception ex) {
        return handlers.entrySet().stream()
                .filter(entry -> entry.getKey().isInstance(ex))
                .findFirst()
                .map(entry -> entry.getValue().apply(ex))
                .orElseGet(() -> buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR));
    }


    private APIErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST.value());
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String message = error.getField() + ": " + error.getDefaultMessage();
            apiError.addMessage(message);
        });
        return new APIErrorResponse(HttpStatus.BAD_REQUEST, apiError);
    }

    private APIErrorResponse buildDataIntegrityViolationResponse(DataIntegrityViolationException ex) {
        APIError apiError = new APIError(HttpStatus.CONFLICT.value());

        String rootCauseMessage = Optional.ofNullable(ex.getRootCause())
                .map(Throwable::getMessage)
                .orElse(ex.getMessage());

        if (rootCauseMessage != null && rootCauseMessage.contains("UK_PRODUTOS_NCM")) {
            apiError.addMessage("ncm: Já existe um produto com esse NCM.");
        } else {
            apiError.addMessage("Erro de integridade de dados: " + rootCauseMessage);
        }

        return new APIErrorResponse(HttpStatus.CONFLICT, apiError);
    }


    private APIErrorResponse buildResponse(Exception ex, HttpStatus status) {
        APIError apiError = new APIError(status.value());
        apiError.addMessage(ex.getMessage());
        return new APIErrorResponse(status, apiError);
    }
}
