package br.gov.es.infoplan.dto.budgetExecution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutiveBranchRequestDTO {
    private Long year;
    private int[] month;
    private int[] sourceType;
    private int[] branchCode;
}
