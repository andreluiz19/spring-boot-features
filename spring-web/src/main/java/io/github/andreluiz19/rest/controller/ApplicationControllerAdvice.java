package io.github.andreluiz19.rest.controller;

import io.github.andreluiz19.exception.PedidoNotFoundException;
import io.github.andreluiz19.exception.RegraNegocioException;
import io.github.andreluiz19.rest.ApiErrors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class) // Toda vez que o projeto lançar uma exceção desse tipo, vai passar por esse método
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors gandleRegraNegocioException(RegraNegocioException ex) {
        String message = ex.getMessage();
        return new ApiErrors(message);
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors pedidoNotFoundException(PedidoNotFoundException ex) {
        return new ApiErrors(ex.getMessage());
    }

}
