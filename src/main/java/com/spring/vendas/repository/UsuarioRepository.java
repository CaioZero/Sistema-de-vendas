package com.spring.vendas.repository;

import java.util.Optional;

import com.spring.vendas.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
    
    Optional<Usuario> findByLogin(String login);
}