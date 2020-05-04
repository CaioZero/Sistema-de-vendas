package com.spring.vendas.exception;

public class SenhaInvalidaException extends RuntimeException{

    public SenhaInvalidaException() {
        super("Senha Invalida");
    }    
}
