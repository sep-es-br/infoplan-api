package br.gov.es.infoplan.dto.IndicatorExecution.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ActionResponseDTO(
        @JsonProperty("cod_action")
        String codAction,
        @JsonProperty("name_action")
        String nameAction
) {
}
