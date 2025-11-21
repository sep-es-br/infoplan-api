package br.gov.es.infoplan.dto.painelOrcamentoDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaDespesaGNDTotalResponseDTO {
    private Long ano;

    @JsonProperty("vlr_orcado")
    private BigDecimal orcado;

    @JsonProperty("vlr_autorizado")
    private BigDecimal autorizado;

    @JsonProperty("vlr_empenhado")
    private BigDecimal empenhado;

    @JsonProperty("vlr_liquidado")
    private BigDecimal liquidado;

    @JsonProperty("vlr_pago_com_rap")
    private BigDecimal pagoComRap;

    @JsonProperty("porcentagem_empenhada")
    private BigDecimal porcentagemEmpenhada;

    @JsonProperty("porcentagem_liquidada")
    private BigDecimal porcentagemLiquidada;

    @JsonProperty("porcentagem_realizada")
    private BigDecimal porcentagemRealizada;

    public ReceitaDespesaGNDTotalResponseDTO(Long ano, BigDecimal orcado, BigDecimal autorizado, BigDecimal empenhado, BigDecimal liquidado, BigDecimal pagoComRap) {
        this.ano = ano;
        this.orcado = orcado;
        this.autorizado = autorizado;
        this.empenhado = empenhado;
        this.liquidado = liquidado;
        this.pagoComRap = pagoComRap;
    }
}
