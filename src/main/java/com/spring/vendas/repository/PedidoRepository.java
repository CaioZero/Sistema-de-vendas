package com.spring.vendas.repository;

import java.util.List;
import java.util.Optional;

import com.spring.vendas.entity.Cliente;
import com.spring.vendas.entity.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
    
    /**PEdidos de um cliente */
    List<Pedido> findByCliente(Cliente cliente);

    /**o Itens esta escrito igualmente como ta na entidade pedido */
    @Query(" select p from Pedido p left join fetch p.itens where p.id = :id ")
    Optional<Pedido> findByIdFetchItens(Integer id);
}