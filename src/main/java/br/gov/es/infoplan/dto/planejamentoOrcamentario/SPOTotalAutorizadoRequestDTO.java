package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPOTotalAutorizadoRequestDTO {

    private Long[] ano;
    private int[] mes;
    private int[] tipoFonte;
    private int[] gnd;
    private int[] uo;
    private int[] po;
}
