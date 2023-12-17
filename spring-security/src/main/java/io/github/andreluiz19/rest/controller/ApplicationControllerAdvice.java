package io.github.andreluiz19.rest.controller;

import io.github.andreluiz19.exception.PedidoNotFoundException;
import io.github.andreluiz19.exception.RegraNegocioException;
import io.github.andreluiz19.rest.ApiErrors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApplicationControllerAdvice { // Controla as exceções lançadas pela aplicação

    @ExceptionHandler(RegraNegocioException.class) // Toda vez que o projeto lançar uma exceção desse tipo, vai passar por esse método
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors gandleRegraNegocioException(RegraNegocioException ex) {
        String message = ex.getMessage();
        return new ApiErrors(message);
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors hadlePedidoNotFoundException(PedidoNotFoundException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Exceção lançada pelo bean validation
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiErrors(errors);
    }

}
