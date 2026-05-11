package br.gov.es.infoplan.dto.IndicatorExecution.response;

import java.math.BigDecimal;

public record DashAvailabilityUoResponseDTO(
        BigDecimal availability,
        BigDecimal availabilityWithoutReservation,
        BigDecimal availabilityWithReservation,
        BigDecimal committedToLiquidating,
        Long year
) {
}
