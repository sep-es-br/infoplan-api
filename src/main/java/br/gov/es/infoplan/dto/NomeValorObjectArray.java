package br.gov.es.infoplan.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.gov.es.infoplan.dto.deserializer.NomeValorObjectArrayDeserializer;

@JsonDeserialize(using = NomeValorObjectArrayDeserializer.class)
public record NomeValorObjectArray(
    List<NomeValorObject> list
) {
    
}
