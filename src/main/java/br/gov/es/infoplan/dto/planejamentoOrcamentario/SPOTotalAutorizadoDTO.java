package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SPOTotalAutorizadoDTO {
    private BigDecimal autorizado;

    private BigDecimal empenhado;

    private BigDecimal liquidado;

    private BigDecimal pago;

    @JsonProperty("pago_sem_rap")
    private BigDecimal pagoSemRAP;

}
