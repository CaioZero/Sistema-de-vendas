package com.spring.vendas.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.spring.vendas.validation.NotEmptyList;

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
	@NotNull(message = "{campo.codigo-cliente.obrigatorio}")
	private Integer cliente;
	
	@NotNull(message = "{campo.total-pedido.obrigatorio}")
	private BigDecimal total;
	
	/**Essa anotacao foi customizada */
	@NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
    private List<ItemPedidoDTO> items;
}