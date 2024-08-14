package br.gov.es.infoplan.service;

import br.gov.es.infoplan.exception.service.InfoplanServiceException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TokenService {
    private static final String ISSUER = "SEP Infoplan API";

    @Value("${token.secret}")
    private String secret;

    public String gerarToken() {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withExpiresAt(getDataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new InfoplanServiceException(List.of("Erro ao gerar o token", exception.getMessage()));
        }
    }

    public String validarToken(String token) {
        Algorithm algoritmo = Algorithm.HMAC256(secret);
        return JWT.require(algoritmo)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant getDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
