package com.spring.vendas.controller;

import javax.validation.Valid;

import com.spring.vendas.dto.CredenciaisDTO;
import com.spring.vendas.dto.TokenDTO;
import com.spring.vendas.entity.Usuario;
import com.spring.vendas.exception.SenhaInvalidaException;
import com.spring.vendas.securityjwt.JwtService;
import com.spring.vendas.service.implementation.UserServiceImp;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UserServiceImp userServiceImp;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){

        /**Essa linha eh pra ser salvo no banco de dados uma senha criptografada */
        String senhaCriptografada = encoder.encode(usuario.getSenha());

        /**aqui eh para setar a senha do usuario como a encriptografada */
        usuario.setSenha(senhaCriptografada);
        return userServiceImp.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credentials){
        try {
            Usuario usuario = Usuario.builder()
                                       .login(credentials.getLogin())
                                       .senha(credentials.getSenha()).build();
            UserDetails usuarioAutenticado = userServiceImp.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);

        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());
        }
    }
}