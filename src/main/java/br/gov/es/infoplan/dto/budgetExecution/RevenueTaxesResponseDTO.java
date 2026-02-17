package br.gov.es.infoplan.dto.budgetExecution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueTaxesResponseDTO {
    private Long year;
    @JsonProperty("patrimonialItemName")
    private String patrimonialItemName;
    private BigDecimal netRevenue;
}
