package br.gov.es.infoplan.dto.painelObras.response;

import java.math.BigDecimal;
import java.util.Date;

public record QuantidadeMaiorPrevistaResponseDTO(
        String orgao,
        BigDecimal planejado,
        Long quantidadeEntregas,
        String nomeMaiorEntrega,
        String municipio,
        String dataConclusao,
        BigDecimal totalMaiorOrgao
) {
}
