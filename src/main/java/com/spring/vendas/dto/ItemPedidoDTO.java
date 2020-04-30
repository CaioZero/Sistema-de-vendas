package com.spring.vendas.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {
    private Integer produto;
    private Integer quantidade;
}