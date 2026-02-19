package br.gov.es.infoplan.dto.budgetExecution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueOriginResponseDTO {
    private Long year;
    private String origin;
    private BigDecimal netRevenue;
}
