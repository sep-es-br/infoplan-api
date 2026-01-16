package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPOFiltroDTO {
    private Long ano;
    private String[] tipoFonte;
    private String[] mes;
    private String[] uo;
    private String[] po;
    private String[] gnd;

}
