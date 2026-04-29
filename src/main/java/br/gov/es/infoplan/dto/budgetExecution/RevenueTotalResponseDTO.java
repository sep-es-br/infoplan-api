package br.gov.es.infoplan.dto.budgetExecution;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class RevenueTotalResponseDTO {
    @Schema(example = "2024")
    private Integer year;
    @Schema(example = "15000000000.00")
    private BigDecimal plannedRevenueValue;
    @Schema(example = "12450800000.50")
    private BigDecimal netRevenueValue;
    @Schema(example = "83.15")
    private BigDecimal percentage;
    @Schema(example = "2024-04-29 14:00:00")
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
