package br.gov.es.infoplan.config.security;

import br.gov.es.infoplan.dto.UsuarioDto;
import br.gov.es.infoplan.exception.mensagens.MensagemErroRest;
import br.gov.es.infoplan.service.AutenticacaoService;
import br.gov.es.infoplan.service.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AutenticacaoService authSrv;

    @Value("${papel.geral}")
    private String papelGeral;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.contains("/user-info") || uri.contains("swagger") || uri.contains("api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = recuperarToken(request);
        if (token != null) {
            try {
                String subject = tokenService.validarToken(token);
                List<String> roles = tokenService.getRoleFromToken(token);
                String siglaLotacao = tokenService.getSiglaFromToken(token);

                if (!checarPermissao(papelGeral, roles)) {
                    for (Map.Entry<String, String> entry : this.authSrv.moduloPermissao.entrySet()) {
                        if (request.getRequestURI().contains(entry.getKey()) &&
                                !checarPermissaoModulo(entry, roles, siglaLotacao)) {
                            enviarMensagemErro(List.of("Este usuario não tem acesso a este módulo (" + entry.getKey()
                                    + "). Acesso negado. "), response, HttpStatus.UNAUTHORIZED);
                            return;
                        }
                    }
                }

                UsuarioDto usuarioPrincipal = new UsuarioDto(
                        token,
                        subject,
                        null,
                        Set.copyOf(roles),
                        siglaLotacao
                );

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        usuarioPrincipal,
                        null,
                        roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                .collect(Collectors.toList()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JWTVerificationException e) {
                var expiresAt = LocalDateTime.ofInstant(JWT.decode(token).getExpiresAt().toInstant(),
                        ZoneOffset.of("-03:00"));
                List<String> erros = new ArrayList<>();
                erros.add("Por favor, faça o login novamente.");
                if (LocalDateTime.now().isAfter((ChronoLocalDateTime<?>) expiresAt))
                    erros.add("Token expirado em " + expiresAt);
                enviarMensagemErro(erros, response, HttpStatus.FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String uri = request.getRequestURI();
//
//        if (uri.contains("/user-info") || uri.contains("swagger") || uri.contains("api-docs")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = recuperarToken(request);
//        if (token != null) {
//            try {
//                String subject = tokenService.validarToken(token);
//
//                List<String> roles = tokenService.getRoleFromToken(token);
//
//                if (!checarPermissao(papelGeral, roles)) {
//                    for (Map.Entry<String, String> entry : this.authSrv.moduloPermissao.entrySet()) {
//                        if (request.getRequestURI().contains(entry.getKey()) &&
//                                !checarPermissao(entry.getValue(), roles)) {
//                            enviarMensagemErro(List.of("Este usuario não tem acesso a este módulo (" + entry.getKey()
//                                    + "). Acesso negado. "), response, HttpStatus.UNAUTHORIZED);
//                            return;
//                        }
//                    }
//                }
//
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        subject, null,
//                        roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                                .collect(Collectors.toList()));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (JWTVerificationException e) {
//                var expiresAt = LocalDateTime.ofInstant(JWT.decode(token).getExpiresAt().toInstant(),
//                        ZoneOffset.of("-03:00"));
//                List<String> erros = new ArrayList<>();
//                erros.add("Por favor, faça o login novamente.");
//                if (LocalDateTime.now().isAfter((ChronoLocalDateTime<?>) expiresAt))
//                    erros.add("Token expirado em " + expiresAt);
//                enviarMensagemErro(erros, response, HttpStatus.FORBIDDEN);
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }

    private boolean checarPermissao(String permissoes, List<String> roles) {
        for (String permissao : permissoes.split(",")) {
            if (roles.contains(permissao.trim()))
                return true;
        }
        return false;
    }

    private boolean possuiSigla(String sigla) {
        return sigla != null && !sigla.isBlank();
    }

    private boolean checarPermissaoModulo(Map.Entry<String, String> modulo, List<String> roles, String sigla) {
        if (checarPermissao(modulo.getValue(), roles)) {
            return true;
        }

        return "/indicador".equals(modulo.getKey())
                && (possuiSigla(sigla) || authSrv.possuiPapelOrgaoIndicadores(roles));
    }

    private String recuperarToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }

    private void enviarMensagemErro(List<String> erros, HttpServletResponse response, HttpStatus status)
            throws IOException {
        String mensagem = ToStringBuilder.reflectionToString(new MensagemErroRest(status, "Token Inválido", erros),
                ToStringStyle.JSON_STYLE);
        response.setHeader("Content-Type", "application/json");
        response.setStatus(status.value());
        response.getWriter().write(mensagem);
    }

    private static final List<String> SWAGGER_WHITELIST = List.of(
            "/v1/api-docs",
            "/v1/api-docs/",
            "/swagger-ui",
            "/swagger-ui/",
            "/swagger-ui.html",
            "/swagger-resources",
            "/swagger-resources/");

    private boolean isSwaggerRequest(String uri) {
        return SWAGGER_WHITELIST.stream().anyMatch(uri::startsWith);
    }
}
