package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPOFiltroPosDTO {
    @JsonProperty("cod_po")
    private String codPo;

    @JsonProperty("nome_po")
    private String nomePo;
}
