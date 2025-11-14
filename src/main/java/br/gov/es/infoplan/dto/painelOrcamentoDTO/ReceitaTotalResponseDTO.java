package br.gov.es.infoplan.dto.painelOrcamentoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaTotalResponseDTO {
    private Integer ano;
    private BigDecimal vlr_receita_prevista;
    private BigDecimal vlr_receita_liquida;

}
