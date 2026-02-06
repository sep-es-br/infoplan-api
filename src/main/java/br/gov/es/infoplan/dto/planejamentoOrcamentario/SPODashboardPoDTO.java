package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPODashboardPoDTO {
    private String uo;

    private String sigla;

    private String po;

    @JsonProperty("nome_uo")
    private String nomeUo;

    @JsonProperty("nome_po")
    private String nomePo;

    @JsonProperty("vlr_previsto")
    private BigDecimal previsto;

    @JsonProperty("vlr_contratado")
    private BigDecimal contratado;

    @JsonProperty("vlr_autorizado")
    private BigDecimal autorizado;
}
