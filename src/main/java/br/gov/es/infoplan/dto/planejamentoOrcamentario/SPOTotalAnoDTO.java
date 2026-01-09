package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPOTotalAnoDTO {
    private String ano;

    @JsonProperty("vlr_previsto")
    private BigDecimal previsto;

    @JsonProperty("vlr_contratado")
    private BigDecimal contratado;

    @JsonProperty("vlr_autorizado")
    private BigDecimal autorizado;

    @JsonProperty("vlr_empenhado")
    private BigDecimal empenhado;

    @JsonProperty("vlr_pago")
    private BigDecimal pago;

    @JsonProperty("vlr_pago_com_rap")
    private BigDecimal pagoComRap;


}
