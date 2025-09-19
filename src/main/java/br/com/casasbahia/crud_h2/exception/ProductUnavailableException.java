package br.com.casasbahia.crud_h2.exception;

public class ProductUnavailableException extends RuntimeException {
    public ProductUnavailableException(Long id, Throwable cause) {
        super("O serviço de produtos está indisponível no momento. ID solicitado: " + id, cause);
    }
}