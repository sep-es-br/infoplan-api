package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.UsuarioDto;
import br.gov.es.infoplan.service.AutenticacaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Base64;

@CrossOrigin(origins = "${frontend.host}")
@RestController
@RequestMapping("/signin")
@RequiredArgsConstructor
public class AutenticacaoController {

    @Value("${frontend.host}")
    private String frontHost;

    private final AutenticacaoService service;

    @GetMapping("/acesso-cidadao-response")
    public RedirectView acessoCidadaoResponse(String accessToken) {
        String tokenEmBase64 = Base64.getEncoder().encodeToString(accessToken.getBytes());
        return new RedirectView(String.format("%s/token?token=%s", frontHost, tokenEmBase64));
    }

    @GetMapping("/user-info")
    public UsuarioDto montarUsuarioDto(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return service.autenticar( authorization.replace("Bearer ", ""));
    }
}
