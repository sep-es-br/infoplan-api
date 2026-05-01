package br.gov.es.infoplan.utils;

import br.gov.es.infoplan.config.pentahoBi.PentahoBiProperties;
import br.gov.es.infoplan.service.PentahoBIService;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ApiUtils extends PentahoBIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiUtils.class);

    @Autowired
    private PentahoBiProperties properties;

    /**
     * Executes a query on Pentaho BI using a configuration key from properties.
     */
    public <T> List<T> executePentahoQuery(String configKey, String path, Map<String, Object> params,
            Function<Map<String, JsonNode>, T> mapper) {
        String target = properties.getTargetOrThrow(configKey);
        String dataAccessId = properties.getDataAccessIdOrThrow(configKey);
        return this.consult(target, dataAccessId, path, params, mapper);
    }

    /**
     * Safely parses a BigDecimal from a JsonNode result set field.
     */
    public static BigDecimal parseBigDecimal(Map<String, JsonNode> rs, String field) {
        JsonNode node = rs.get(field);
        if (node == null || node.isNull()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return new BigDecimal(node.asDouble(0.0)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Joins a string array into a comma-separated string, filtering nulls.
     */
    public static String joinArray(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        String result = Arrays.stream(array)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(","));

        return result.isEmpty() ? null : result;
    }

    public <T> List<T> consult(
            String target,
            String dataAccessId,
            String pmoPath,
            Map<String, Object> params,
            Function<Map<String, JsonNode>, T> mapper) {

        try {
            String result = doRequest(buildEndpointUri(pmoPath, target, dataAccessId, params));

            // Corrigir as entidades HTML
            result = StringEscapeUtils.unescapeHtml4(result);

            List<Map<String, JsonNode>> resultset = extractDataFromResponse(result);
            return resultset.stream().map(mapper).toList();

        } catch (Exception e) {
            LOGGER.error("Error during consult: ", e);
            return List.of();
        }
    }

    @Override
    protected String buildEndpointUri(String pmoPath, String target, String dataAccess, Map<String, Object> params) {
        return super.buildEndpointUri(pmoPath, target, dataAccess, params);
    }

    @Override
    protected String buildEndpointUri(String target, String dataAccess, Map<String, Object> params) {
        return super.buildEndpointUri(null, target, dataAccess, params);
    }

}
