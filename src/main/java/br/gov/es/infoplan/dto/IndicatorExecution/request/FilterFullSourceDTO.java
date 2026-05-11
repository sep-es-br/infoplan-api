package br.gov.es.infoplan.dto.IndicatorExecution.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FilterFullSourceDTO(
        @Schema(
                description = "Anos separados por vírgula",
                example = "2025,2026"
        )
        String year,

        @Schema(
                description = "Unidade Orçamentária separados por vírgula",
                example = "27101"
        )
        String uo,

        @Schema(
                description = "Ação Orçamentária separados por vírgula",
                example = "-1"
        )
        String action
) {
}
