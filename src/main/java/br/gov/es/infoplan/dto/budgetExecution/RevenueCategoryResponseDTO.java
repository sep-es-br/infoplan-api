package br.gov.es.infoplan.dto.budgetExecution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueCategoryResponseDTO {

    private Long year;
    private String category;
    private BigDecimal netRevenue;
}
