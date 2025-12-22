package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPOFiltroUosDTO {

    @JsonProperty("cod_uo")
    private String codUo;

    @JsonProperty("nome_uo")
    private String nomeUo;
}
