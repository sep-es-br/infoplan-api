package br.gov.es.infoplan.service;

import br.gov.es.infoplan.dto.ACUserInfoDto;
import br.gov.es.infoplan.exception.service.InfoplanServiceException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class TokenService {
    private static final String ISSUER = "SEP Infoplan API";

    @Value("${token.secret}")
    private String secret;

    public String gerarToken(ACUserInfoDto userInfo, String guidOrganizacao) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userInfo.sub())
                    .withClaim("name", userInfo.apelido())
                    .withClaim("email", userInfo.email())
                    .withClaim("roles", userInfo.role() != null ? new ArrayList<>(userInfo.role()) : Collections.emptyList())
                    .withClaim("guidOrganizacao", guidOrganizacao)
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

    public String getGuidOrganizacaoFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        var claim = decodedJWT.getClaim("guidOrganizacao");

        return claim.isNull() ? null : claim.asString();
    }

    public String getNameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        var claim = decodedJWT.getClaim("name");

        return claim.isNull() ? null : claim.asString();
    }

    public String getEmailFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        var claim = decodedJWT.getClaim("email");

        return claim.isNull() ? null : claim.asString();
    }

    private Instant getDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public List<String> getRoleFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("roles").asList(String.class);
    }
}
