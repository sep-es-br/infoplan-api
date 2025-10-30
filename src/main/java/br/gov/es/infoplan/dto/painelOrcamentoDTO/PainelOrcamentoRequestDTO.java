package br.gov.es.infoplan.dto.painelOrcamentoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PainelOrcamentoRequestDTO {

    private Long ano;
    private int[] mes;
    private int[] tipoFonte;

}
