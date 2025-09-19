package br.com.casasbahia.crud_h2.error;

public interface IAPIErrorFactory {
    APIErrorResponse createAPIError(Exception ex);
}