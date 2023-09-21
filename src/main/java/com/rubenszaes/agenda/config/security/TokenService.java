package com.rubenszaes.agenda.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.rubenszaes.agenda.domain.model.Usuario;
import com.rubenszaes.agenda.exception.BusinessExcepition;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("123456789");
            return JWT.create()
                    .withIssuer("API Agenda Service")
                    .withSubject(usuario.getUsuario())
                    .withClaim("name", usuario.getNome())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
//            throw new RuntimeException("Erro ao gerar Token JWT", exception);
            throw new BusinessExcepition("Erro ao gerar Token JWT");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}