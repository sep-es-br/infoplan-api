package br.gov.es.infoplan.dto.painelObras.response;

import java.math.BigDecimal;

public record QuantidadeMaiorEntregaResponseDTO(
        String municipio,
        BigDecimal planejado,
        Long quantidadeEntrega,
        String nomeMaiorEntrega,
        String orgao,
        String dataConclusao,
        BigDecimal totalMaiorMunicipio
) {
}
