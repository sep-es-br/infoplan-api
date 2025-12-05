package br.gov.es.infoplan.dto.execucaoOrcamentariaDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaTransferenciaCorrenteResponseDTO {
    private Long ano;
    @JsonProperty("nome_item_patrimonial")
    private String nomeItemPatrimonial;
    private BigDecimal receitaLiquida;

}
