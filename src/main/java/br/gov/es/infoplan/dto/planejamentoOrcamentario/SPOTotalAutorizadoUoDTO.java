package br.gov.es.infoplan.dto.planejamentoOrcamentario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPOTotalAutorizadoUoDTO {
    private String cod;

    private String sigla;

    @JsonProperty("porcentagem_empenhado")
    private BigDecimal porcentagemEmpenhado;

    @JsonIgnore
    private BigDecimal porcentagemAutorizado;

    @JsonProperty("porcentagem_liquidado")
    private BigDecimal porcentagemLiquidado;

    @JsonProperty("porcentagem_pago_sem_rap")
    private BigDecimal porcentagemPagoSemRap;


    public static BigDecimal calcularPorcentagem(BigDecimal autorizado, BigDecimal value) {
        if(value == null) {
            new NullPointerException("Valor sem encontra nulo.");
        }
        BigDecimal multiplicador = new BigDecimal(100);
        BigDecimal resultado = value.divide(autorizado, 2, RoundingMode.HALF_UP);

        BigDecimal porcentagem = resultado.multiply(multiplicador);
        return porcentagem;
    }

}
