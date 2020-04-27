package com.spring.vendas.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import com.spring.vendas.entity.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository {

    @Autowired
    private EntityManager entityManager;

    /**A anotacao transactional serve para o EntityManager poder utilizar as funcoes
     * Esta anotacao eh OBRIGATORIA
     */

    @Transactional
    public Cliente salvar(Cliente cliente){
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente){
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente){
        if (!entityManager.contains(cliente)) {
           cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

   
    /**Deletar por id */
    @Transactional
    public void deletar(Integer id){
        Cliente cliente =  entityManager.find(Cliente.class, id);
        deletar(cliente);
    }

    /**Transactional que recebe o ReadOnly */
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome){
      String jpql = "select c from Cliente c where c.nome like :nome";

      /**O resultado de uma consulta jpql retorna uma query tipada, por isso ha necessidade de utilizar o typedQuery */
      TypedQuery<Cliente> query =  entityManager.createQuery(jpql, Cliente.class);

      /**Aqui ele esta procurando qualquer coisa que tenha o nome no meio. Por exemplo, se escrever silva, ele vai procurar
       * todos os silvas que ele achar
       */
      query.setParameter("nome","%" + nome + "%");
      return query.getResultList();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Cliente> obterTodos(){
        return entityManager
        .createQuery("from Cliente", Cliente.class)
        .getResultList();
    }
}