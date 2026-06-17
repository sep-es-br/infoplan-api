package br.gov.es.infoplan.dto.IndicatorExecution.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CardMissionResponseDTO(
        @JsonProperty("missao")
        BigDecimal mission
) {
}
