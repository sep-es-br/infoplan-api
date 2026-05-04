package br.gov.es.infoplan.dto.IndicatorExecution.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record WithoutReversationResponseDTO (
        @JsonProperty("disponivel_sem_reserva")
        BigDecimal availableWithoutReservation
){ }
