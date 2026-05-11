package br.gov.es.infoplan.config.security;

import br.gov.es.infoplan.exception.mensagens.MensagemErroRest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Value("${spring.security.oauth2.client.registration.acessocidadao.client-id}")
    private String registrationId;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String accept = request.getHeader("Accept");
        boolean isBrowser = accept != null && accept.contains("text/html");

        if (isBrowser) {
            response.sendRedirect(request.getContextPath()
                    + "/oauth2/authorization/" + registrationId);
        } else {
            response.setHeader("Content-Type", "application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(ToStringBuilder.reflectionToString(
                    new MensagemErroRest(HttpStatus.UNAUTHORIZED,
                            "Não autenticado.",
                            List.of("Realize o login para acessar este recurso.")),
                    ToStringStyle.JSON_STYLE));
        }
    }
}
