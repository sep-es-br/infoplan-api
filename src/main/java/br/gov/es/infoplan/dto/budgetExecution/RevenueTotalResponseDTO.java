package br.gov.es.infoplan.dto.budgetExecution;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class RevenueTotalResponseDTO {
    private Integer year;
    private BigDecimal plannedRevenueValue;
    private BigDecimal netRevenueValue;
    private BigDecimal percentage;
    private String timestamp;

    public RevenueTotalResponseDTO(Integer year, BigDecimal plannedRevenueValue, BigDecimal netRevenueValue) {
        this.year = year;
        this.plannedRevenueValue = plannedRevenueValue;
        this.netRevenueValue = netRevenueValue;
    }

    public RevenueTotalResponseDTO(Integer year, BigDecimal plannedRevenueValue, BigDecimal netRevenueValue,
            String timestamp) {
        this.year = year;
        this.plannedRevenueValue = plannedRevenueValue;
        this.netRevenueValue = netRevenueValue;
        this.timestamp = timestamp;
    }
}
