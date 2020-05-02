package com.spring.vendas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Bean
    public PasswordEncoder passwordEncoder(){
        /**Gera um hash da senha para aumentar seguranca */
        return new BCryptPasswordEncoder();
    }

    /**Traz os objetos que irao fazer autenticacao usuario configura senha, se existe, etc     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .passwordEncoder(passwordEncoder())
            .withUser("Caio")
            .password(passwordEncoder().encode("123"))
            .roles("USER", "ADMIN");
    }
   
    /**Nessa parte, verifica se o usuario autenticado tem permissao para entrar em determinada pagina */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeRequests()
            .antMatchers("/api/clientes/**")
                .hasAnyRole("USER", "ADMIN")
            .antMatchers("/api/pedidos/**")
                .hasAnyRole("USER", "ADMIN")
            .antMatchers("/api/produtos/**")
                .hasRole("ADMIN")
        .and()
            .formLogin();
    ;
    }
   
}