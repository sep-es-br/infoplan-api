package br.gov.es.infoplan.config.security;

import br.gov.es.infoplan.service.AutenticacaoService;
import br.gov.es.infoplan.service.TokenService;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SecurityFilterIndicadoresTest {
    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @ParameterizedTest
    @CsvSource({
            "/indicador/test, PAINEL_EXEC_ORC_IND_DPES, DPES, 200",
            "/indicador/test, PAINEL_EXEC_ORC_IND_SESA, -, 200",
            "/indicador/test, PAINEL_INDICADORES, -, 200",
            "/indicador/test, PAINEL_EXECUCAO_ORCAMENTARIA, -, 200",
            "/indicador/test, OUTRO, DPES, 200",
            "/indicador/test, PAINEL_EXEC_ORC_IND_, -, 401",
            "/indicador/test, PAINEL_EXEC_ORC_IND_-1, -, 401",
            "/indicador/test, OUTRO_PAINEL_EXEC_ORC_IND_DPES, -, 401",
            "/indicador/test, OUTRO, -, 401",
            "/capitation/test, PAINEL_EXEC_ORC_IND_DPES, DPES, 401"
    })
    void verificaPermissaoPorModulo(String uri, String role, String sigla, int status) throws Exception {
        TokenService tokens = mock(TokenService.class);
        AutenticacaoService auth = new AutenticacaoService(tokens, null, null, null, null);
        ReflectionTestUtils.setField(auth, "papelIndicadores", "PAINEL_INDICADORES");
        ReflectionTestUtils.setField(auth, "papelSigefes", "PAINEL_EXECUCAO_ORCAMENTARIA");
        ReflectionTestUtils.setField(auth, "papelCapitacao", "PAINEL_CAPTACAO");
        ReflectionTestUtils.setField(auth, "indicadoresOrgaoPrefixo", "PAINEL_EXEC_ORC_IND_");
        auth.init();
        SecurityFilter filter = new SecurityFilter(tokens, auth);
        ReflectionTestUtils.setField(filter, "papelGeral", "GESTOR_GLOBAL");
        when(tokens.validarToken("token")).thenReturn("usuario");
        when(tokens.getRoleFromToken("token")).thenReturn(List.of(role));
        when(tokens.getSiglaFromToken("token")).thenReturn("-".equals(sigla) ? null : sigla);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", uri);
        request.addHeader("Authorization", "Bearer token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilterInternal(request, response, chain);

        assertEquals(status, response.getStatus());
        if (status == 200) {
            verify(chain).doFilter(request, response);
        } else {
            verifyNoInteractions(chain);
        }
    }
}
