package br.gov.es.infoplan.dto.IndicatorExecution.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record FilterGeneralRequestDTO(
        @Schema(
                description = "Anos separados por vírgula",
                example = "2025,2026"
        )
        String year,

        @Schema(
                description = "Unidade Orçamentária separados por vírgula",
                example = "27101"
        )
        String codUo,

        @Schema(
                description = "Código da Ação Orçamentária separados por vírgula",
                example = "-1"
        )
        String codAction,

        @Schema(
                description = "Mês separado por vírgula",
                example = "-1"
        )
        String month,

        @Schema(
                description = "Tipo de fonte separado por vírgula",
                example = "-1"
        )
        String typeSource,

        @Schema(
                description = "Código do GND separado por vírgula",
                example = "-1"
        )
        String codGnd,

        @Schema(
                description = "Código da fonte separado por vírgula",
                example = "-1"
        )
        String codSource,

        @Schema(
                description = "Código da emenda seprado por vírgula",
                example = "-1"
        )
        String codAmendment
)
{ }
