package br.gov.es.infoplan.dto.painelOrcamentoDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaImpostosResponseDTO {
    private Long ano;
    @JsonProperty("nome_item_patrimonial")
    private String nomeItemPatrimonial;
    @JsonProperty("vlr_receita_liquida")
    private BigDecimal receitaLiquida;
}
