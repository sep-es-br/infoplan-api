package br.gov.es.infoplan.dto.IndicatorExecution.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FullSourceResponseDTO(
        @JsonProperty("cod_source")
        String codSource,
        @JsonProperty("name_source")
        String nameSource
) {
}
