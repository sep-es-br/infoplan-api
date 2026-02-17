package br.gov.es.infoplan.dto.budgetExecution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueCurrentTransferResponseDTO {
    private Long year;
    @JsonProperty("patrimonial_item_name")
    private String patrimonialItemName;
    private BigDecimal netRevenue;

}
