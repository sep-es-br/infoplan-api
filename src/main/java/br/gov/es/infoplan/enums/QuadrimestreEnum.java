package br.gov.es.infoplan.enums;

import lombok.Getter;

@Getter
public enum QuadrimestreEnum {
    PRIMEIRO(30.40),
    SEGUNDO(52.87),
    TERCEIRO(65.60);

    private final double marcoReferencia;


    QuadrimestreEnum(double marcoReferencia) {
        this.marcoReferencia = marcoReferencia;
    }

    private static QuadrimestreEnum obterQuadrimestreMes(int mes) {
        return switch (mes) {
            case 1, 2, 3, 4 -> QuadrimestreEnum.PRIMEIRO;
            case 5, 6, 7, 8 -> QuadrimestreEnum.SEGUNDO;
            case 9, 10, 11, 12 -> QuadrimestreEnum.TERCEIRO;
            default -> throw new IllegalArgumentException("Mês inválido");
        };
    }

    public static QuadrimestreEnum obterQuadrimestre(int[] meses) {
        if (meses == null || meses.length == 0) {
            throw new IllegalArgumentException("Nenhum mês informado");
        }

        QuadrimestreEnum quadrimestre = obterQuadrimestreMes(meses[0]);

        for (int mes : meses) {
            if (obterQuadrimestreMes(mes) != quadrimestre) {
                throw new IllegalArgumentException(
                        "Os meses pertencem a quadrimestres diferentes"
                );
            }
        }

        return quadrimestre;
    }


    public static String calcularNotaIGO(double porcentualIGO, QuadrimestreEnum quadrismetre) {
        double marco = quadrismetre.getMarcoReferencia();

        if(porcentualIGO >= marco * 0.95) {
            return "A";
        }

        if(porcentualIGO >= marco * 0.80) {
            return "B";
        }

        return "C";
    }

}
