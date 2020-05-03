package com.spring.vendas.controller;

import javax.validation.Valid;

import com.spring.vendas.entity.Usuario;
import com.spring.vendas.service.implementation.UserServiceImp;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UserServiceImp userServiceImp;
    private final PasswordEncoder encoder;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){

        /**Essa linha eh pra ser salvo no banco de dados uma senha criptografada */
        String senhaCriptografada = encoder.encode(usuario.getSenha());

        /**aqui eh para setar a senha do usuario como a encriptografada */
        usuario.setSenha(senhaCriptografada);
        return userServiceImp.salvar(usuario);
    }
}