package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPOFiltroDTO {
    private Long ano;
    private int[] tipoFonte;
    private int[] mes;
    private int[] uo;
    private int[] po;
    private int[] gnd;

}
