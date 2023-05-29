package br.com.autenticador.autenticador.services;


import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.autenticador.autenticador.models.UserModel;

@Service
public class TokenService {
    public String GenerateToken(UserModel user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("Id", user.getId())
                .withExpiresAt(LocalDateTime.now()
                .plusMinutes(10)
                .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256("Secreto"));
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256("Secreto"))
                .build().verify(token).getSubject();
    }
}
