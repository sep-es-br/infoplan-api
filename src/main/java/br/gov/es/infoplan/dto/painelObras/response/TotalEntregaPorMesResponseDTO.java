package br.gov.es.infoplan.dto.painelObras.response;

import java.math.BigDecimal;
import java.util.Date;

public record TotalEntregaPorMesResponseDTO(
        String mesNome,
        BigDecimal planejado,
        String entregaNome,
        String municipio,
        Long quantidadeEntregas,
        BigDecimal maiorValorNoMes,
        String dataConclusaoMaiorEntrega,
        BigDecimal valorMedioPorAcao
) {
}
