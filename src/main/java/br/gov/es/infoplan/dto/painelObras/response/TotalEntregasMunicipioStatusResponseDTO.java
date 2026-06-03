package br.gov.es.infoplan.dto.painelObras.response;

import java.math.BigDecimal;

public record TotalEntregasMunicipioStatusResponseDTO(
        String municipio,
        String status,
        BigDecimal planejado,
        BigDecimal realizado
        ) {
}
