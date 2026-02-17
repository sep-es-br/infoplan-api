package br.gov.es.infoplan.dto.budgetExecution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetExecutionRequestDTO {

    private Long year;
    private int[] month;
    private int[] sourceType;

}
