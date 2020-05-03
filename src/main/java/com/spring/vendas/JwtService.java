package com.spring.vendas;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.spring.vendas.entity.Usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.signedkey}")
    private String signedKey;

    private String gerarToken(Usuario usuario) {
        long expString = Long.valueOf(expiration);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return Jwts.builder().setSubject(usuario.getLogin()).setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, signedKey).compact();
    }

    /** Claims sao as informacoes de um token */
    public Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(signedKey).parseClaimsJws(token).getBody();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime localDateTime 
                        = expirationDate.toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime();
            return !LocalDateTime.now().isAfter(localDateTime);
        } catch (Exception e) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
        JwtService jwtService = context.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("caio").build();
        String token = jwtService.gerarToken(usuario);
        System.out.println(token);

        boolean isTokenValido =  jwtService.tokenValido(token);
        System.out.println("O token esta valido?: "+isTokenValido);

        System.out.println(jwtService.obterLoginUsuario(token));
    }
}