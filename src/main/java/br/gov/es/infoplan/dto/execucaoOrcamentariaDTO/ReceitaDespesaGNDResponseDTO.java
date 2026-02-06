package br.gov.es.infoplan.dto.execucaoOrcamentariaDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaDespesaGNDResponseDTO {
    private Long ano;

    private Long mes;

    @JsonProperty("nome_gnd")
    private String nomeGnd;

    @JsonProperty("tipo_fonte")
    private Long tipoFonte;

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

}
