package br.com.casasbahia.crud_h2.error;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class APIError implements IAPIError {
    private final Integer status;
    private final List<String> messages = new ArrayList<>();

    public APIError(Integer status){
        this.status = status;
    }

    public void addMessage(String message){
        messages.add(message);
    }
}