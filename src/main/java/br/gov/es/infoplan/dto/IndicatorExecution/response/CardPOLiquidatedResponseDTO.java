package br.gov.es.infoplan.dto.IndicatorExecution.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CardPOLiquidatedResponseDTO(
        @JsonProperty("cod_po")
        String codPo,

        @JsonProperty("nome_po")
        String namePo,

        @JsonProperty("liquidado")
        BigDecimal liquidated
) {
}
