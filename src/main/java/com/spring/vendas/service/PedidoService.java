package com.spring.vendas.service;

import java.util.Optional;

import com.spring.vendas.dto.PedidoDTO;
import com.spring.vendas.entity.Pedido;
import com.spring.vendas.enums.StatusPedido;


public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}