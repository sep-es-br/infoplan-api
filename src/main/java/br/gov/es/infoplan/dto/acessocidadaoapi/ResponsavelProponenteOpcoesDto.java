package br.gov.es.infoplan.dto.acessocidadaoapi;

public record ResponsavelProponenteOpcoesDto(
        Long id,
        String nome,
        String papelPrioritario,
        String agentePublicoSub,
        boolean gestorOrganizacao
) {
}
