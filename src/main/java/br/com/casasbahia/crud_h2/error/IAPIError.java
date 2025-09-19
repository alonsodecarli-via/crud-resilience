package br.com.casasbahia.crud_h2.error;

import java.io.Serializable;
import java.util.List;

public interface IAPIError extends Serializable {
    Integer getStatus();
    List<String> getMessages();
}