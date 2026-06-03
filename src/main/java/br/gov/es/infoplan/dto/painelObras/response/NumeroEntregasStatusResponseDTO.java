package br.gov.es.infoplan.dto.painelObras.response;

public record NumeroEntregasStatusResponseDTO(
        String municipio,
        String status,
        Long quantidadeEntregas
) {
}
