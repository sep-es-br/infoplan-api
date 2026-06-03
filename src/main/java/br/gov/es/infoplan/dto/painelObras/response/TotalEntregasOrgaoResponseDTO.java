package br.gov.es.infoplan.dto.painelObras.response;

import java.math.BigDecimal;

public record TotalEntregasOrgaoResponseDTO(
        String orgao,
        BigDecimal planejado,
        BigDecimal realizado
) {
}
