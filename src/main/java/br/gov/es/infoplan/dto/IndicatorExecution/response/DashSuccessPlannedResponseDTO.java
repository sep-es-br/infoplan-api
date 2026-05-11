package br.gov.es.infoplan.dto.IndicatorExecution.response;

import java.math.BigDecimal;

public record DashSuccessPlannedResponseDTO(
        Long year,
        String codGnd,
        String nameGnd,
        BigDecimal budgeted,
        BigDecimal authorized,
        BigDecimal committed,
        BigDecimal liquidated,
        BigDecimal committedBarAuthorized,
        BigDecimal liquidatedBarAuthorized
) {
}
