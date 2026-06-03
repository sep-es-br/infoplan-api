package br.gov.es.infoplan.dto.painelObras.response;

import java.math.BigDecimal;

public record TotalEntregasAnoStatusResponseDTO(
        String ano,
        String status,
        BigDecimal planejado,
        BigDecimal realizado
) {
}
