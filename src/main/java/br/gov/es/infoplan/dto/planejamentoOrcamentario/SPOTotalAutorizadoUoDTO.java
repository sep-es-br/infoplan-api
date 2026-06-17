package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPOTotalAutorizadoUoDTO {
    private String cod;

    private String sigla;

    @JsonProperty("nome_uo")
    private String nomeUo;

    @JsonProperty("porcentagem_empenhado")
    private BigDecimal porcentagemEmpenhado;

    @JsonIgnore
    private BigDecimal porcentagemAutorizado;

    @JsonProperty("porcentagem_liquidado")
    private BigDecimal porcentagemLiquidado;

    @JsonProperty("porcentagem_pago_sem_rap")
    private BigDecimal porcentagemPagoSemRap;

    @JsonProperty("vlr_previsto")
    private BigDecimal previsto;

}
