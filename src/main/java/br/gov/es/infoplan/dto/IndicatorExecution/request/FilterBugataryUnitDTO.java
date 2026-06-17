package br.gov.es.infoplan.dto.IndicatorExecution.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FilterBugataryUnitDTO(
        @Schema(
                description = "Anos separados por vírgula",
                example = "2025,2026"
        )
        String year
) { }
