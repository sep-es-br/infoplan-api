package br.gov.es.infoplan.dto.budgetExecution;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutiveBranchRequestDTO {
    @Schema(description = "Ano de referência", example = "2024")
    private Long year;
    
    @Schema(description = "Meses de referência", type = "array", example = "[1, 2, 3]")
    private List<Integer> month;
    
    @Schema(description = "Tipos de fonte", type = "array", example = "[1, 2]")
    private List<Integer> sourceType;
    
    @Schema(description = "Códigos de poder", type = "array", example = "[1, 2, 3]")
    private List<Integer> branchCode;
}
