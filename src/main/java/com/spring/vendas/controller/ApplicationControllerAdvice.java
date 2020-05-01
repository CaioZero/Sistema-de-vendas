package com.spring.vendas.controller;

import com.spring.vendas.exception.PedidoNaoEncontradoException;
import com.spring.vendas.exception.RegraDeNegocioException;
import com.spring.vendas.util.ApiErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    /**Criando um tratador de erros */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraDeNegocioException(RegraDeNegocioException ex){
        String menssagemDeErro = ex.getMessage();
        return new ApiErrors(menssagemDeErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException( PedidoNaoEncontradoException ex ){
        return new ApiErrors(ex.getMessage());
    }
}