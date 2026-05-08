package br.gov.es.infoplan.dto.IndicatorExecution.response;

import java.math.BigDecimal;

public record DashPlannedBudgetaryResponseDTO(
    Long year,
    String codPo,
    String namePo,
    BigDecimal budgeted,
    BigDecimal authorized,
    BigDecimal committed,
    BigDecimal liquidated,
    BigDecimal committedBarAuthorized,
    BigDecimal liquidatedBarAuthorized
) {
}
