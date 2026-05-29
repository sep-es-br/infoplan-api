package br.gov.es.infoplan.dto.painelObras.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record PainelObrasRequestDTO(
        @Schema(description = "O código do Orgão", example = "-1")
        String orgao,

        @Schema(description = "O código do município", example = "-1")
        String municipio,

        @Schema(description = "O código do Status", example = "-1")
        String status
) {
}
