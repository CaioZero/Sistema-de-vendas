package com.spring.vendas.securityjwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.vendas.service.implementation.UserServiceImp;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserServiceImp userServiceImp;

    public JwtAuthFilter(JwtService jwtService, UserServiceImp userServiceImp) {
        this.jwtService = jwtService;
        this.userServiceImp = userServiceImp;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {

            String authorization  = request.getHeader("Authorization");
            if (authorization!= null && authorization.startsWith("Bearer")) {
                String token = authorization.split(" ")[1];
                boolean isValid = jwtService.tokenValido(token);

                /**Carregando o usuario se o token for valido */
                if (isValid) {
                   String loginUsuario =  jwtService.obterLoginUsuario(token);
                   /**Carregando o usuario se o token for valido */
                  UserDetails usuario = userServiceImp.loadUserByUsername(loginUsuario);
                  UsernamePasswordAuthenticationToken user = 
                            new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                  user.setDetails(new WebAuthenticationDetailsSource()
                                      .buildDetails(request));
                  SecurityContextHolder.getContext().setAuthentication(user);
                }
            }

            filterChain.doFilter(request, response);
    }
    
}