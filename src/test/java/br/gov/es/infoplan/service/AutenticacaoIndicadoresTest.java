package br.gov.es.infoplan.service;

import br.gov.es.infoplan.client.AcessoCidadaoWebClient;
import br.gov.es.infoplan.dto.ACUserInfoDto;
import br.gov.es.infoplan.dto.acessocidadaoapi.ACAgentePublicoPapelDto;
import br.gov.es.infoplan.dto.organogramawebapi.OrganogramaUnidadeInfoDto;
import br.gov.es.infoplan.dto.organogramawebapi.OrganogramaOrganizacaoInfo;
import br.gov.es.infoplan.exception.UsuarioSemPermissaoException;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Set;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticacaoIndicadoresTest {
    private final TokenService tokens = mock(TokenService.class);
    private final AcessoCidadaoWebClient client = mock(AcessoCidadaoWebClient.class);
    private final OrganogramaService organograma = mock(OrganogramaService.class);
    private AutenticacaoService service;
    private ACUserInfoDto user;

    @BeforeEach
    void setup() {
        service = spy(new AutenticacaoService(tokens, client, null,
                mock(AcessoCidadaoAutorizacaoService.class), null));
        ReflectionTestUtils.setField(service, "organogramaService", organograma);
        ReflectionTestUtils.setField(service, "indicadoresOrgaoPrefixo", "PAINEL_EXEC_ORC_IND_");
        configurarUsuario(Set.of("PAINEL_EXEC_ORC_IND_DPES"));
        when(client.buscarPapeisAgentePublicoPorSub(any(), eq("sub"))).thenReturn(List.of());
    }

    private void configurarUsuario(Set<String> roles) {
        user = new ACUserInfoDto("Usuario", true, true, null, "sub", true,
                "email", null, "sub", roles, null);
        doReturn(user).when(service).getUserInfo("access");
    }

    private void configurarPrioritario() {
        when(client.buscarPapeisAgentePublicoPorSub(any(), eq("sub"))).thenReturn(List.of(
                new ACAgentePublicoPapelDto(null, null, null, "lotacao", null, null, true)));
    }

    @Test
    void extraiSiglaSemPrioritarioEPreservaPapeis() {
        var result = service.autenticar("access");
        assertEquals("DPES", result.sigla());
        assertEquals(user.role(), result.role());
        verify(tokens).gerarToken(user, "DPES");
        verifyNoInteractions(organograma);
    }

    @Test
    void preservaSiglaDoPrioritario() {
        configurarPrioritario();
        when(organograma.listarUnidadeInfoPorLotacaoGuid("lotacao"))
                .thenReturn(new OrganogramaUnidadeInfoDto(null, null, null, "org"));
        when(organograma.listarUnidadeInfoPorOrganizacao("org"))
                .thenReturn(new OrganogramaOrganizacaoInfo(null, null, "SESA", null));
        assertEquals("SESA", service.autenticar("access").sigla());
    }

    @Test
    void usaPapelQuandoOrganogramaNaoRetornaUnidade() {
        configurarPrioritario();
        assertEquals("DPES", service.autenticar("access").sigla());
    }

    @Test
    void usaPapelQuandoOrganogramaRetorna404() {
        configurarPrioritario();
        when(organograma.listarUnidadeInfoPorLotacaoGuid("lotacao"))
                .thenThrow(new FeignException.NotFound("Nao encontrado", requestOrganograma(), null, Map.of()));
        assertEquals("DPES", service.autenticar("access").sigla());
    }

    @Test
    void naoOcultaFalhasDoOrganograma() {
        configurarPrioritario();
        when(organograma.listarUnidadeInfoPorLotacaoGuid("lotacao"))
                .thenThrow(new FeignException.InternalServerError("Falha", requestOrganograma(), null, Map.of()));
        assertThrows(FeignException.InternalServerError.class, () -> service.autenticar("access"));
        verifyNoInteractions(tokens);
    }

    @Test
    void rejeitaOrgaosAmbiguos() {
        configurarUsuario(Set.of("PAINEL_EXEC_ORC_IND_DPES", "PAINEL_EXEC_ORC_IND_SESA"));
        assertThrows(UsuarioSemPermissaoException.class, () -> service.autenticar("access"));
        verifyNoInteractions(tokens);
    }

    @Test
    void naoExtraiSiglaDePapelConvencional() {
        configurarUsuario(Set.of("PAINEL_INDICADORES"));
        assertEquals("", service.autenticar("access").sigla());
    }

    private Request requestOrganograma() {
        return Request.create(Request.HttpMethod.GET, "http://localhost/unidades/lotacao/info",
                Map.of(), (byte[]) null, java.nio.charset.StandardCharsets.UTF_8, null);
    }
}
