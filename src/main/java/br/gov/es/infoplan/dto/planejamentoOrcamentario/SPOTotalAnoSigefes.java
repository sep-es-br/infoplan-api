package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPOTotalAnoSigefes {
    private String ano;

    @JsonProperty("vlr_pago_com_rap")
    private BigDecimal pagoComRap;

    @JsonProperty("vlr_pago_sem_rap")
    private BigDecimal pago;
}
