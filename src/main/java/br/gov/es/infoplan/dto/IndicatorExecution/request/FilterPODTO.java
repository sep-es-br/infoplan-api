package br.gov.es.infoplan.dto.IndicatorExecution.request;

public record FilterPODTO(
        String year,
        String codUo,
        String codAction,
        String orgao
        ) {
}
