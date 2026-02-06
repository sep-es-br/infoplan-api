package br.gov.es.infoplan.dto.execucaoOrcamentariaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalAnoDTO {

    private Long ano;
    private BigDecimal total;
}
