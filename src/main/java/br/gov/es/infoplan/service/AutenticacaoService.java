package br.gov.es.infoplan.service;

import br.gov.es.infoplan.dto.ACUserInfoDto;
import br.gov.es.infoplan.dto.ACUserInfoDtoStringRole;
import br.gov.es.infoplan.dto.UsuarioDto;
import br.gov.es.infoplan.exception.UsuarioSemPermissaoException;
import br.gov.es.infoplan.exception.service.InfoplanServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final Logger logger = LogManager.getLogger(AutenticacaoService.class);
    private final TokenService tokenService;

    public UsuarioDto autenticar(String accessToken) {
        logger.info("Autenticar usuário Infoplan.");
        ACUserInfoDto userInfo = getUserInfo(accessToken);
        String token = tokenService.gerarToken(userInfo);

        return new UsuarioDto(token, userInfo.apelido(), getEmailUserInfo(userInfo), userInfo.role());
    }

    protected ACUserInfoDto getUserInfo(String accessToken) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://acessocidadao.es.gov.br/is/connect/userinfo"))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            ACUserInfoDto userInfoDto;
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body().contains("role\":\"")) {
                userInfoDto = new ACUserInfoDto(new ObjectMapper().readValue(response.body(), ACUserInfoDtoStringRole.class));
            } else {
                userInfoDto = new ObjectMapper().readValue(response.body(), ACUserInfoDto.class);
            }

            if (userInfoDto.role() == null || userInfoDto.role().isEmpty()) {
                throw new UsuarioSemPermissaoException();
            }

            return userInfoDto;
        } catch (InterruptedException | IOException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
        throw new InfoplanServiceException(List.of("Não foi possível identificar um usuário no acesso cidadão com esse token. Faça login novamente!"));
    }

    private static String getEmailUserInfo(ACUserInfoDto userInfo) {
        return userInfo.emailCorporativo() != null ? userInfo.emailCorporativo() : userInfo.email();
    }
}
