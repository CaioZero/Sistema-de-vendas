package com.spring.vendas.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {
    String message() default "A lista nao pode ser vazia";

    /**Esses dois metodos abaixo devem ser colados toda vez que utilizar anotacoes customizadas */
    Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}