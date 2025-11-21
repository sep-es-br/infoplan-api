package br.gov.es.infoplan.utils;

import br.gov.es.infoplan.service.PentahoBIService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ApiUtils extends PentahoBIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiUtils.class);

//    public <T> List<T> consult(
//            String target,
//            String dataAccessId,
//            String pmoPath,
//            Map<String, Object> params,
//            Function<Map<String, JsonNode>, T> mapper) {
//
//        try {
//            String result = doRequest(buildEndpointUri(pmoPath, target, dataAccessId, params));
//            System.out.println(result);
//
//            List<Map<String, JsonNode>> resultset = extractDataFromResponse(result);
//            return resultset.stream().map(mapper).toList();
//
//        } catch (Exception e) {
//            LOGGER.error("Error during consult: ", e);
//            return List.of();
//        }
//    }

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
//            System.out.println(result);

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
        return super.buildEndpointUri(null,target, dataAccess, params);
    }

    /**
     * Converte um array de qualquer tipo para uma String separada por vírgulas.
     *
     * @param <T> O tipo dos elementos do array.
     * @param array O array a ser unido.
     * @return Uma String com os elementos do array separados por vírgulas.
     */

    public static String unirArrayParaString(int[] array, String delimitador) {
        if (array == null || array.length == 0) {
            return "";
        }
        // Note que usamos Arrays.stream(int[]) e mapToObj
        return Arrays.stream(array)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(delimitador));
    }
}
