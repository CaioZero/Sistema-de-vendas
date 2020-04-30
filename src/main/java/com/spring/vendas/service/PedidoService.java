package com.spring.vendas.service;

import com.spring.vendas.dto.PedidoDTO;
import com.spring.vendas.entity.Pedido;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
}