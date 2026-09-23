package br.gov.es.infoplan.service;

import br.gov.es.infoplan.config.PapelProperties;
import br.gov.es.infoplan.config.pentahoBi.PentahoBiProperties;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterActionDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterBugataryUnitDTO;
import br.gov.es.infoplan.dto.UsuarioDto;
import br.gov.es.infoplan.exception.service.InfoplanServiceException;
import br.gov.es.infoplan.utils.ApiUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static br.gov.es.infoplan.config.pentahoBi.PentahoBiConfigKeys.*;
import static br.gov.es.infoplan.config.pentahoBi.PentahoBiConfigParams.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IndicatorExecutionOrganizationTest {
    private ApiUtils apiUtils;
    private IndicatorExecutionService service;

    @BeforeEach
    void setup() {
        apiUtils = mock(ApiUtils.class);
        service = new IndicatorExecutionService(
                new PapelProperties("PAINEL_EXECUCAO_ORCAMENTARIA", "PAINEL_INDICADORES", "PAINEL_EXEC_ORC_IND_")
        );

        PentahoBiProperties properties = new PentahoBiProperties();
        properties.getIndicatorExecution().setPath("/indicadores");
        ReflectionTestUtils.setField(service, "apiUtils", apiUtils);
        ReflectionTestUtils.setField(service, "properties", properties);
        service.init();

        when(apiUtils.executePentahoQuery(anyString(), anyString(), anyMap(), any()))
                .thenAnswer(invocation -> {
                    String configKey = invocation.getArgument(0);
                    if (!PAINEL_INDICADOR_EXECUTION_UO_GUID.equals(configKey)) {
                        if (INDICATOR_EXECUTION_UO_BY_YEAR.equals(configKey)) {
                            Function<Map<String, JsonNode>, Object> mapper = invocation.getArgument(3);
                            return List.of(mapper.apply(Map.of(
                                    "cod_uo", TextNode.valueOf("27101"),
                                    "nome_uo", TextNode.valueOf("Unidade Orçamentária"),
                                    "sigla", TextNode.valueOf("SEP")
                            )));
                        }
                        return List.of();
                    }

                    Function<Map<String, JsonNode>, String> mapper = invocation.getArgument(3);
                    return List.of(mapper.apply(Map.of(COD_ORGAO, TextNode.valueOf("27"))));
                });
    }

    @Test
    void consultaUosPorAnoComCodigoDoOrgaoResolvidoPeloGuid() {
        var unidades = service.searchBudgetaryUnit(
                new FilterBugataryUnitDTO("2026"),
                usuario(Set.of("OUTRO"), "guid-organizacao")
        );

        assertEquals(1, unidades.size());
        assertEquals("27101", unidades.get(0).uo());
        assertEquals("Unidade Orçamentária", unidades.get(0).name());
        assertEquals("SEP", unidades.get(0).acronym());

        verify(apiUtils).executePentahoQuery(
                eq(PAINEL_INDICADOR_EXECUTION_UO_GUID),
                eq("/indicadores"),
                argThat(params -> "guid-organizacao".equals(params.get(PARAMP_ORG_GUID))
                        && "2026".equals(params.get(PARAMP_ANO_M))),
                any()
        );
        verify(apiUtils).executePentahoQuery(
                eq(INDICATOR_EXECUTION_UO_BY_YEAR),
                eq("/indicadores"),
                argThat(params -> "2026".equals(params.get(PARAMP_ANO_M))
                        && "27".equals(params.get(PARAMP_ORGAO))
                        && !params.containsKey(PARAMP_ORG_GUID)),
                any()
        );
    }

    @Test
    void enviaCodigoDoOrgaoNasConsultasAntigas() {
        service.searchAction(
                new FilterActionDTO("2026", "27101", null),
                usuario(Set.of("OUTRO"), "guid-organizacao")
        );

        verify(apiUtils).executePentahoQuery(
                eq(INDICATOR_EXECUTION_SEARCH_ACTION),
                eq("/indicadores"),
                argThat(params -> "27".equals(params.get(PARAMP_ORGAO))
                        && !params.containsKey(PARAMP_ORG_GUID)),
                any()
        );
    }

    @Test
    void acessoTotalUsaTodosOsOrgaosSemConsultarConversao() {
        service.searchBudgetaryUnit(
                new FilterBugataryUnitDTO("2026"),
                usuario(Set.of("PAINEL_EXECUCAO_ORCAMENTARIA"), "guid-organizacao")
        );

        verify(apiUtils, never()).executePentahoQuery(
                eq(PAINEL_INDICADOR_EXECUTION_UO_GUID), anyString(), anyMap(), any()
        );
        verify(apiUtils).executePentahoQuery(
                eq(INDICATOR_EXECUTION_UO_BY_YEAR),
                eq("/indicadores"),
                argThat(params -> "-1".equals(params.get(PARAMP_ORGAO))),
                any()
        );
    }

    @Test
    void reutilizaCodigoDoOrgaoParaMesmoGuidEAno() {
        UsuarioDto usuario = usuario(Set.of("OUTRO"), "guid-organizacao");

        service.searchBudgetaryUnit(new FilterBugataryUnitDTO("2026"), usuario);
        service.searchAction(new FilterActionDTO("2026", "27101", null), usuario);

        verify(apiUtils, times(1)).executePentahoQuery(
                eq(PAINEL_INDICADOR_EXECUTION_UO_GUID), anyString(), anyMap(), any()
        );
    }

    @Test
    void naoExecutaConsultaSeguinteQuandoConversaoNaoRetornaCodigo() {
        reset(apiUtils);
        when(apiUtils.executePentahoQuery(
                eq(PAINEL_INDICADOR_EXECUTION_UO_GUID), anyString(), anyMap(), any()
        )).thenReturn(List.of());

        assertThrows(
                InfoplanServiceException.class,
                () -> service.searchBudgetaryUnit(
                        new FilterBugataryUnitDTO("2026"),
                        usuario(Set.of("OUTRO"), "guid-sem-codigo")
                )
        );

        verify(apiUtils, never()).executePentahoQuery(
                eq(INDICATOR_EXECUTION_UO_BY_YEAR), anyString(), anyMap(), any()
        );
    }

    private UsuarioDto usuario(Set<String> papeis, String guidOrganizacao) {
        return new UsuarioDto("token", "Usuario", "email", papeis, guidOrganizacao);
    }
}
