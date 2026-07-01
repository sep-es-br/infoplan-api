package br.gov.es.infoplan.dto.organogramawebapi;

public record OrganogramaOrganizacaoInfoEssencialDto(
        String guid,
        String cnpj,
        String sigla,
        String razaoSocial,
        String nomeFantasia
) {
}
