package com.spring.vendas.repository;

import java.util.List;

import com.spring.vendas.entity.Cliente;
import com.spring.vendas.entity.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
    
    /**PEdidos de um cliente */
    List<Pedido> findByCliente(Cliente cliente);
}