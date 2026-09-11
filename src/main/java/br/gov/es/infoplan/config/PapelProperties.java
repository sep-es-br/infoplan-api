package br.gov.es.infoplan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "papel")
public record PapelProperties(
        String sigefes,
        String indicadores,
        String indicadoresOrgaoPrefixo
) {
}
