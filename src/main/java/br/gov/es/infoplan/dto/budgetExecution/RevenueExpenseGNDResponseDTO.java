package br.gov.es.infoplan.dto.budgetExecution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueExpenseGNDResponseDTO {
    private Long year;

    private Long month;

    @JsonProperty("gndName")
    private String gndName;

    @JsonProperty("sourceType")
    private Long sourceType;

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

}
