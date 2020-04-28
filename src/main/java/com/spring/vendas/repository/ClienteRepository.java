package com.spring.vendas.repository;

import java.util.List;

import com.spring.vendas.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
                                          /**Entidade e tipo do ID */
public interface ClienteRepository extends JpaRepository<Cliente,Integer>{

    /**Utilizando um QueryMethod
     * ele esta executando o seguinte comando sql:
     * select c from Cliente c where c.nome like :nome
     */
    List<Cliente> findByNomeLike(String nome);
    
     /**Fazendo a mesma coisa que a funcao de cima
     * so que setando a Query manualmente e colocando um nome de uma funcao do jeito que eu quiser
     * ISSO AQUI EH EM JPQL
     */
    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);
    
        /**Fazendo a mesma coisa que a funcao de cima
     * so que setando a Query manualmente e colocando um nome de uma funcao do jeito que eu quiser
     * ISSO AQUI EH EM SQL
     */
    @Query(value = "select * from cliente c where c.nome like '%:nome%'", nativeQuery = true)
	List<Cliente> encontrarPorNomeSql(@Param("nome") String nome);

    List<Cliente> findByNomeOrId(String nome, Integer id);

    boolean existsByNome(String nome);

    /**Buscando o cliente com seus pedidos 
     * o left join traz os clientes, mesmo tendo ou nao clientes.
     * Caso fosse so join, ele se retornaria clientes que possuem pedidos
    */
    @Query("select c from Cliente c left join fetch c.pedidos p where c.id = :id")
    Cliente findClienteFetchPedidos(@Param ("id") Integer id);
}