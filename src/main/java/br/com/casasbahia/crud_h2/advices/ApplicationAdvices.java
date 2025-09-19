package br.com.casasbahia.crud_h2.advices;

import br.com.casasbahia.crud_h2.error.APIErrorResponse;
import br.com.casasbahia.crud_h2.error.IAPIError;
import br.com.casasbahia.crud_h2.error.IAPIErrorFactory;
import br.com.casasbahia.crud_h2.exception.ProductNotFoundException;
import br.com.casasbahia.crud_h2.exception.ProductUnavailableException;
import br.com.casasbahia.crud_h2.exception.RateLimitExceededException;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@AllArgsConstructor
public class ApplicationAdvices {

    private final IAPIErrorFactory errorFactory;

    @ResponseBody
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BadRequestException.class,
            HttpMessageNotReadableException.class,
            DataIntegrityViolationException.class,
            ProductNotFoundException.class,
            RateLimitExceededException.class,
            ProductUnavailableException.class
    })
    public ResponseEntity<IAPIError> handleBadRequestExceptions(Exception ex) {
        APIErrorResponse response = errorFactory.createAPIError(ex);
        return ResponseEntity.status(response.getStatus()).body(response.getError());
    }
}