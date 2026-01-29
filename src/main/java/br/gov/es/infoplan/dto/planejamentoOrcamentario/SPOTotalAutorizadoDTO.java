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

    @JsonProperty("pago_com_rap")
    private BigDecimal pagoComRap;

    private BigDecimal pago;

}
