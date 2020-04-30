package com.spring.vendas.util;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public class ApiErrors {
    
    @Getter
    public List<String> errors;

    public ApiErrors(String messageError){
        this.errors = Arrays.asList(messageError);
    }
}