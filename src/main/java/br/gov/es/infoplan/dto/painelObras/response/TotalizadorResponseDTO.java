package br.gov.es.infoplan.dto.painelObras.response;

import java.math.BigDecimal;

public record TotalizadorResponseDTO(
        Long portfolio,
        Long quantidadeEntregas,
        Long quantidadeProjetos,
        Long quantidadeProgramas,
        BigDecimal totalPrevisto,
        BigDecimal totalRealizado,
        BigDecimal totalProgramado,
        Long totalEntregasPE
) {
}
