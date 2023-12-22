package br.com.agencia.crm.agenciacrm.models.wrapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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
