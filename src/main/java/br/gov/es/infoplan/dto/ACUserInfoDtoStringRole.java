package br.gov.es.infoplan.dto;

public record ACUserInfoDtoStringRole(
        String apelido,
        Boolean cpfValidado,
        Boolean verificada,
        String verificacaoTipo,
        String subNovo,
        Boolean agentepublico,
        String email,
        String sub,
        String role,
        String emailCorporativo) {

}
