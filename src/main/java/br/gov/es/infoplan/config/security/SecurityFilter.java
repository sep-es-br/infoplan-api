package br.gov.es.infoplan.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }

//    private String recuperarToken(HttpServletRequest request) {
//        var authHeader = request.getHeader("Authorization");
//        if (authHeader == null) return null;
//        return authHeader.replace("Bearer ", "");
//    }

//    private void enviarMensagemErro(List<String> erros, HttpServletResponse response) throws IOException {
//        String mensagem = ToStringBuilder.reflectionToString(new MensagemErroRest(UNAUTHORIZED, "Token Inválido", erros), ToStringStyle.JSON_STYLE);
//        response.setHeader("Content-Type", "application/json");
//        response.setStatus(UNAUTHORIZED.value());
//        response.getWriter().write(mensagem);
//    }
}
