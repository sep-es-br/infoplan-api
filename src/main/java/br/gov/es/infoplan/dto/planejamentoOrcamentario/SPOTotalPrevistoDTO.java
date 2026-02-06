package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SPOTotalPrevistoDTO {
    private BigDecimal previsto;

    private BigDecimal contratado;

    @JsonProperty("times_temp")
    private String timesTemp;
}
