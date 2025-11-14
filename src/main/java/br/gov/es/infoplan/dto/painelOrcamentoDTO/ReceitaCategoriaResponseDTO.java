package br.gov.es.infoplan.dto.painelOrcamentoDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaCategoriaResponseDTO {

    private Long ano;
    private String categoria;
    private BigDecimal receitaLiquida;

}
