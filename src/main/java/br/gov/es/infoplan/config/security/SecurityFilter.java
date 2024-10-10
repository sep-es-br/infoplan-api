package br.gov.es.infoplan.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import br.gov.es.infoplan.exception.mensagens.MensagemErroRest;
import br.gov.es.infoplan.service.TokenService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import com.auth0.jwt.JWT;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    
    @Value("${permissao.capitacao}")
    private String permissaoCapitacao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().endsWith("/user-info")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = recuperarToken(request);
        if (token != null) {
            try {
                String subject = tokenService.validarToken(token);

                List<String> roles = tokenService.getRoleFromToken(token);

                HashMap<String, String> moduloPermissao = new HashMap<>();
                moduloPermissao.put("/capitation", permissaoCapitacao);

                for(Map.Entry<String,String> entry : moduloPermissao.entrySet()){
                    if(request.getRequestURI().contains(entry.getKey()) && 
                        !checarPermissao(entry.getValue(), roles)){
                        enviarMensagemErro(List.of("Este usuario não tem acesso a este módulo (" + entry.getKey() + "). Acesso negado. "), response);
                        return;
                    }
                } 

                
                if (!roles.contains("GESTOR_GLOBAL")) {
                    enviarMensagemErro(
                            List.of("Role inválida. Acesso negado."), response);
                    return;
                }

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roles);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        subject, null, List.of(authority));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException e) {
                var expiresAt = LocalDateTime.ofInstant(JWT.decode(token).getExpiresAt().toInstant(), ZoneOffset.of("-03:00"));
                List<String> erros = new ArrayList<>();
                erros.add("Por favor, faça o login novamente.");
                if (LocalDateTime.now().isAfter((ChronoLocalDateTime<?>) expiresAt))
                    erros.add("Token expirado em " + expiresAt);
                enviarMensagemErro(erros, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean checarPermissao(String permissoes, List<String> roles) {
        for(String permissao : permissoes.split(",")) {
            if(roles.contains(permissao.trim())) return true;
        }
        return false;
    }

   private String recuperarToken(HttpServletRequest request) {
       var authHeader = request.getHeader("Authorization");
       if (authHeader == null) return null;
       return authHeader.replace("Bearer ", "");
   }

   private void enviarMensagemErro(List<String> erros, HttpServletResponse response) throws IOException {
       String mensagem = ToStringBuilder.reflectionToString(new MensagemErroRest(UNAUTHORIZED, "Token Inválido", erros), ToStringStyle.JSON_STYLE);
       response.setHeader("Content-Type", "application/json");
       response.setStatus(UNAUTHORIZED.value());
       response.getWriter().write(mensagem);
   }
}
