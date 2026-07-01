package br.gov.es.infoplan.dto.organogramawebapi;

public record OrganogramaUnidadeInfoDto(
        String guid,
        String nome,
        String sigla,
        String guidOrganizacao
) {
}
