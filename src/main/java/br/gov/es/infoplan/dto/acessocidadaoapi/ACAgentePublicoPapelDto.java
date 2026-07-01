package br.gov.es.infoplan.dto.acessocidadaoapi;

public record ACAgentePublicoPapelDto(
        String Guid,
        String Nome,
        String Tipo,
        String LotacaoGuid,
        String AgentePublicoSub,
        String AgentePublicoNome,
        boolean Prioritario
) {
}
