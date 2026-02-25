package br.gov.es.infoplan.dto.budgetExecution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueExpenseGNDTotalResponseDTO {
    private Long year;

    @JsonProperty("budgetedValue")
    private BigDecimal budgetedValue;

    @JsonProperty("authorizedValue")
    private BigDecimal authorizedValue;

    @JsonProperty("committedValue")
    private BigDecimal committedValue;

    @JsonProperty("liquidatedValue")
    private BigDecimal liquidatedValue;

    @JsonProperty("paidWithRAPValue")
    private BigDecimal paidWithRAPValue;

    @JsonProperty("committedPercentage")
    private BigDecimal committedPercentage;

    @JsonProperty("liquidatedPercentage")
    private BigDecimal liquidatedPercentage;

    @JsonProperty("realizedPercentage")
    private BigDecimal realizedPercentage;

    public RevenueExpenseGNDTotalResponseDTO(Long year, BigDecimal budgetedValue, BigDecimal authorizedValue,
            BigDecimal committedValue, BigDecimal liquidatedValue, BigDecimal paidWithRAPValue) {
        this.year = year;
        this.budgetedValue = budgetedValue;
        this.authorizedValue = authorizedValue;
        this.committedValue = committedValue;
        this.liquidatedValue = liquidatedValue;
        this.paidWithRAPValue = paidWithRAPValue;
    }
}
