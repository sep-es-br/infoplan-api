package br.gov.es.infoplan.config.pentahoBi;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "pentaho-bi.pmo")
@Data
public class PentahoBiProperties {

    private String path;

    private PathConfig execucaoOrcamentaria = new PathConfig();
    private PathConfig planejamentoOrcamentario = new PathConfig();
    private PathConfig strategicProjects = new PathConfig();

    // Configs com target/dataAccessId (suporta múltiplos níveis)
    private Map<String, Object> target = new HashMap<>();
    private Map<String, Object> dataAccessId = new HashMap<>();

    @PostConstruct
    public void validate() {
        if (target.isEmpty() && dataAccessId.isEmpty()) {
            throw new IllegalStateException(
                    "Nenhuma configuração do Pentaho PMO foi encontrada. " +
                            "Configure pelo menos pentaho-bi.pmo.target.* ou pentaho-bi.pmo.path"
            );
        }
    }

    @Data
    public static class PathConfig {
        private String path;

        public String getPathOrThrow(String nome) {
            if (path == null || path.isBlank()) {
                throw new IllegalArgumentException(
                        "Configuração obrigatória ausente: pentaho-bi.pmo." + nome + ".path"
                );
            }
            return path;
        }
    }

    /**
     * Busca target simples (1 nível)
     * Exemplo: getTargetSimple("projeto")
     */
    public String getTargetSimple(String key) {
        Object value = target.get(key);
        return value instanceof String ? (String) value : null;
    }

    /**
     * Busca target aninhado (múltiplos níveis)
     * Exemplo: getTargetNested("programa.original")
     */
    public String getTargetNested(String nestedKey) {
        String[] keys = nestedKey.split("\\.");
        Map<String, Object> current = target;

        for (int i = 0; i < keys.length - 1; i++) {
            Object next = current.get(keys[i]);
            if (next instanceof Map) {
                current = (Map<String, Object>) next;
            } else {
                return null;
            }
        }

        Object value = current.get(keys[keys.length - 1]);
        return value instanceof String ? (String) value : null;
    }

    /**
     * Busca target com validação
     * Suporta tanto chaves simples quanto aninhadas
     */
    public String getTargetOrThrow(String key) {
        String value = getTargetSimple(key);

        if (value == null && key.contains(".")) {
            value = getTargetNested(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    String.format(
                            "Configuração não encontrada: pentaho-bi.pmo.target.%s. " +
                                    "Chaves disponíveis: %s",
                            key,
                            getAllTargetKeys()
                    )
            );
        }

        return value;
    }

    /**
     * Busca dataAccessId simples (1 nível)
     */
    public String getDataAccessIdSimple(String key) {
        Object value = dataAccessId.get(key);
        return value instanceof String ? (String) value : null;
    }

    /**
     * Busca dataAccessId aninhado (múltiplos níveis)
     */
    public String getDataAccessIdNested(String nestedKey) {
        String[] keys = nestedKey.split("\\.");
        Map<String, Object> current = dataAccessId;

        for (int i = 0; i < keys.length - 1; i++) {
            Object next = current.get(keys[i]);
            if (next instanceof Map) {
                current = (Map<String, Object>) next;
            } else {
                return null;
            }
        }

        Object value = current.get(keys[keys.length - 1]);
        return value instanceof String ? (String) value : null;
    }

    /**
     * Busca dataAccessId com validação
     */
    public String getDataAccessIdOrThrow(String key) {
        String value = getDataAccessIdSimple(key);

        if (value == null && key.contains(".")) {
            value = getDataAccessIdNested(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    String.format(
                            "Configuração não encontrada: pentaho-bi.pmo.dataAccessId.%s. " +
                                    "Chaves disponíveis: %s",
                            key,
                            getAllDataAccessIdKeys()
                    )
            );
        }

        return value;
    }

    /**
     * Lista todas as chaves de target (incluindo aninhadas)
     */
    public Set<String> getAllTargetKeys() {
        return flattenKeys(target, "");
    }

    /**
     * Lista todas as chaves de dataAccessId
     */
    public Set<String> getAllDataAccessIdKeys() {
        return flattenKeys(dataAccessId, "");
    }

    /**
     * Verifica se existe config completa (target + dataAccessId)
     */
    public boolean hasConfig(String key) {
        String targetValue = key.contains(".") ?
                getTargetNested(key) : getTargetSimple(key);
        String dataAccessIdValue = key.contains(".") ?
                getDataAccessIdNested(key) : getDataAccessIdSimple(key);

        return targetValue != null && !targetValue.isBlank() &&
                dataAccessIdValue != null && !dataAccessIdValue.isBlank();
    }

    /**
     * Achata map aninhado em chaves com ponto
     */
    private Set<String> flattenKeys(Map<String, Object> map, String prefix) {
        return map.entrySet().stream()
                .flatMap(entry -> {
                    String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();

                    if (entry.getValue() instanceof Map) {
                        return flattenKeys((Map<String, Object>) entry.getValue(), key).stream();
                    } else {
                        return Set.of(key).stream();
                    }
                })
                .collect(Collectors.toSet());
    }
}
