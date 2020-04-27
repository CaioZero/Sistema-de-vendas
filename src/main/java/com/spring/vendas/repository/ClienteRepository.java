package com.spring.vendas.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.spring.vendas.entity.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository {

    private static String insert = "insert into cliente (nome) values (?)";
    private static String select_all = "select * from cliente";
    private static String update = "update cliente set nome = ? where id = ?";
    private static String delete = "delete from cliente where id = ?";
 

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente salvar(Cliente cliente){
        jdbcTemplate.update(insert, new Object[]{cliente.getNome()});
        return cliente;
    }

    public Cliente atualizar(Cliente cliente){
        jdbcTemplate.update(update, new Object[]{
            cliente.getNome(),
            cliente.getId()
        });
        return cliente;
    }

    public void deletar(Cliente cliente){
        deletar(cliente.getId());
    }

    public void deletar(Integer id){
        jdbcTemplate.update(delete, new Object[]{id});
    }

    public List<Cliente> buscarPorNome(String nome){
        return jdbcTemplate.query(
                select_all.concat(" where nome like ? "),
                new Object[]{"%" + nome + "%"},
                obterClienteMapper());
    }

    public List<Cliente> obterTodos(){
        return jdbcTemplate.query(select_all, obterClienteMapper());
    }

    private RowMapper<Cliente> obterClienteMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                return new Cliente(id, nome);
            }
        };
    }

}