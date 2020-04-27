package com.spring.vendas.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Produto {
    private Integer id;
    private String descricao;
    private BigDecimal preco;
}