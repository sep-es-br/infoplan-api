package br.gov.es.infoplan.dto.execucaoOrcamentariaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecucaoOrcamentariaRequestDTO {

    private Long ano;
    private int[] mes;
    private int[] tipoFonte;

}
