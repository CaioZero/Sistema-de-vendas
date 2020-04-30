package com.spring.vendas.dto;

import java.math.BigDecimal;
import java.util.List;


/*
{
	"cliente": 1,
	"total": 100,
	"items":[
		{
			"produto":1,
			"quantidade": 10
		}
	]
}
*/

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> items;
}