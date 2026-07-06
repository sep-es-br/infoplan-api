package br.gov.es.infoplan.dto.organogramawebapi;

public record OrganogramaOrganizacaoInfo (
    String guid ,
    String razaoSocial,
    String sigla,
    String guidOrganizacaoPai
){
}
