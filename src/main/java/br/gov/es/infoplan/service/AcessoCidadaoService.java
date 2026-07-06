package br.gov.es.infoplan.service;

import br.gov.es.infoplan.client.AcessoCidadaoUserInfoClient;
import br.gov.es.infoplan.client.AcessoCidadaoWebClient;
import br.gov.es.infoplan.dto.ACUserInfoDto;
import br.gov.es.infoplan.dto.acessocidadaoapi.ACAgentePublicoPapelDto;
import br.gov.es.infoplan.dto.acessocidadaoapi.ResponsavelProponenteOpcoesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcessoCidadaoService {

    @Autowired
    private AcessoCidadaoAutorizacaoService ACAuthService;

    @Autowired
    private AcessoCidadaoWebClient ACWebClient;

    @Autowired
    private AcessoCidadaoUserInfoClient ACUserInfoClient;

    public List<ACAgentePublicoPapelDto> listarPapeisAgentePublicoPorSub(String sub) {
        return buscarPapeisAgentePublicoPorSub(sub);
    }

    private List<ACAgentePublicoPapelDto> buscarPapeisAgentePublicoPorSub(String sub) {
        return ACWebClient.buscarPapeisAgentePublicoPorSub(ACAuthService.getAuthorizationHeader(), sub);
    }

    public ACUserInfoDto buscarInformacoesUsuario(String accessToken) {
        return buscarAcessoCidadaoUserInfo(accessToken);
    }

    private ACUserInfoDto buscarAcessoCidadaoUserInfo(String accessToken) {
        LinkedHashMap<String, Object> userInfoObj = ACUserInfoClient
                .buscarUserInfoAcessoCidadao(ACAuthService.getAccessTokenAuthorizationHeader(accessToken));
        return new ACUserInfoDto(userInfoObj);
    }

    public List<ResponsavelProponenteOpcoesDto> buscarPessoasUnidadePapelPrioritario(String unidadeGuid) {

        List<ResponsavelProponenteOpcoesDto> result = ACWebClient.buscarAgentesPublicosPapeisPorGuidUnidade(
                        ACAuthService.getAuthorizationHeader(), unidadeGuid, true)
                .stream()
                .collect(Collectors.groupingBy(
                        a -> a.AgentePublicoNome()))
                .values()
                .stream()
                .flatMap(listaPorNome -> listaPorNome.stream().anyMatch(a -> Boolean.TRUE.equals(a.Prioritario()))
                        ? listaPorNome.stream().filter(a -> Boolean.TRUE.equals(a.Prioritario()))
                        : listaPorNome.stream().limit(1))
                .map(dto -> new ResponsavelProponenteOpcoesDto(
                        0L,
                        dto.AgentePublicoNome(),
                        dto.Nome().toUpperCase(),
                        dto.AgentePublicoSub(),
                        false))
                .sorted((a, b) -> a.nome().compareToIgnoreCase(b.nome()))
                .collect(Collectors.toList());

        return result;

    }
}
