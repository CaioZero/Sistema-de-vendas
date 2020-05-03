package com.spring.vendas.service.implementation;

import javax.transaction.Transactional;

import com.spring.vendas.entity.Usuario;
import com.spring.vendas.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**Essa annotation de transactional significa que estou na camada de servico, ou seja,
     * nao deve ser passado diretamente ao banco e eh necessaria a annotation
     */
    @Transactional
    public Usuario salvar(Usuario usuario){
       return usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // if(!username.equalsIgnoreCase("Caio")){
        //     throw new UsernameNotFoundException("Usuario nao encontrado na base");
        // }

        // return User
        //         .builder()
        //         .username("Caio")
        //         .password(encoder.encode("123"))
        //         .roles("USER", "ADMIN")
        //         .build();
        Usuario usuario = usuarioRepository.findByLogin(username)
            .orElseThrow(()-> new UsernameNotFoundException("Usuario nao encontrado"));

            /**Se o usuario for admin, vai voltar um array com admin e user nos roles
             * caso contrario, so ira retornar um array com user
             */
        String [] roles = usuario.isAdmin() ? new String[]{"ADMIN","USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
    
}