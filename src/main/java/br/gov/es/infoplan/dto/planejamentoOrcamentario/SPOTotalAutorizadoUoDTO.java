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

    @JsonProperty("nome_uo")
    private String nomeUo;

    @JsonProperty("porcentagem_empenhado")
    private BigDecimal porcentagemEmpenhado;

    @JsonIgnore
    private BigDecimal porcentagemAutorizado;

    @JsonProperty("porcentagem_liquidado")
    private BigDecimal porcentagemLiquidado;

    @JsonProperty("porcentagem_pago_sem_rap")
    private BigDecimal porcentagemPagoSemRap;

    @JsonProperty("vlr_previsto")
    private BigDecimal previsto;

    public static BigDecimal calcularPorcentagem(BigDecimal autorizado, BigDecimal value) {
        if(value == null) {
           throw  new NullPointerException("Valor sem encontra nulo.");
        }

        if (autorizado.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }


        BigDecimal multiplicador = new BigDecimal(100);
        BigDecimal resultado = value.divide(autorizado, 2, RoundingMode.HALF_UP);

        BigDecimal porcentagem = resultado.multiply(multiplicador);
        return porcentagem;
    }

//    public static BigDecimal calcularPorcentagem(BigDecimal autorizado, BigDecimal value) {
//        if (value == null || autorizado == null) {
//            throw new IllegalArgumentException("Os valores não podem ser nulos.");
//        }
//
//        if (autorizado.compareTo(BigDecimal.ZERO) == 0) {
//            return BigDecimal.ZERO;
//        }
//
//        // 2. Lógica otimizada: (Valor * 100) / Autorizado
//        // Usamos o scale no final para garantir a precisão do arredondamento
//        return value.multiply(new BigDecimal("100"))
//                .divide(autorizado, 2, RoundingMode.HALF_UP);
//    }

}
