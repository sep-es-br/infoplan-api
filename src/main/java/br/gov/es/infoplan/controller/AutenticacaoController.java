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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Base64;

@Tag(name = "Autenticação")
@CrossOrigin(origins = "${frontend.host}")
@RestController
@RequestMapping("/signin")
@RequiredArgsConstructor
public class AutenticacaoController {

    @Value("${frontend.host}")
    private String frontHost;

    private final AutenticacaoService service;

    @Operation(summary = "Callback do Acesso Cidadão", description = "Endpoint de retorno que processa o token do Acesso Cidadão e redireciona para o frontend")
    @GetMapping("/acesso-cidadao-response")
    public RedirectView acessoCidadaoResponse(String accessToken) {
        String tokenEmBase64 = Base64.getEncoder().encodeToString(accessToken.getBytes());
        return new RedirectView(String.format("%s/token?token=%s", frontHost, tokenEmBase64));
    }

    @Operation(summary = "Obter informações do usuário logado", description = "Retorna os dados do perfil do usuário a partir do token Bearer enviado no header")
    @GetMapping("/user-info")
    public UsuarioDto montarUsuarioDto(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return service.autenticar(authorization.replace("Bearer ", ""));
    }
}
