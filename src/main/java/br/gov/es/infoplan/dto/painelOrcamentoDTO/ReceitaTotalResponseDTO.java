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
    private BigDecimal porcentagem;

    public ReceitaTotalResponseDTO(Integer ano, BigDecimal vlr_receita_prevista, BigDecimal vlr_receita_liquida) {
        this.ano = ano;
        this.vlr_receita_prevista = vlr_receita_prevista;
        this.vlr_receita_liquida = vlr_receita_liquida;
    }
}
