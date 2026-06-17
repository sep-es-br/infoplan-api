package br.gov.es.infoplan.dto.painelObras.response;

import java.math.BigDecimal;

public record TotalEntregasOrgaoExeResponseDTO (
        String orgao,
        Long quantidadeEntregas,
        BigDecimal planejado,
        BigDecimal realizado
){
}
