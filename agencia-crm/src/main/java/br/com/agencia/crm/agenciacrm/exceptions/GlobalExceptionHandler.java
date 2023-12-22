package br.com.agencia.crm.agenciacrm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.agencia.crm.agenciacrm.models.wrapper.ResponseWrapper;

@ControllerAdvice
public class GlobalExceptionHandler<T> {
    
    @ExceptionHandler(ClienteJaCadastradoException.class)
    public ResponseEntity<ResponseWrapper<T>> handleClienteJaCadastradoException(ClienteJaCadastradoException ex) {
        ResponseWrapper<T> response = new ResponseWrapper<T>(null, ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ResponseWrapper<T>> handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex) {
        ResponseWrapper<T> response = new ResponseWrapper<T>(null, ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NaoExistemAlteracoesException.class)
    public ResponseEntity<ResponseWrapper<T>> handleNaoExistemAlteracoesException(NaoExistemAlteracoesException ex) {
        ResponseWrapper<T> response = new ResponseWrapper<T>(null, ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(DependenteException.class)
    public ResponseEntity<ResponseWrapper<T>> handleDependenteJaExisteException(DependenteException ex) {
        ResponseWrapper<T> response = new ResponseWrapper<T>(null, ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
