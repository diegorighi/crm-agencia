package br.com.agencia.crm.agenciacrm.models.records.wrapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapper<T> {
    
    private T data;
    private String message; 
    private Boolean success;

    public ResponseWrapper(T data, String message, Boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

}
