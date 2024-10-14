package br.gov.es.infoplan.service;

import br.gov.es.infoplan.dto.NomeValorObject;
import com.fasterxml.jackson.databind.JsonNode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class CapitationService extends PentahoBIService {
    
    private final Logger LOGGER = LogManager.getLogger(PentahoBIService.class);

    @Value("${pentahoBI.capitation.path}")
    private String CaptationPath;

    @Value("${pentahoBI.capitation.target.dashAll}")
    private String targetDashAll;

    @Value("${pentahoBI.capitation.target.dashPrograms}")
    private String targetDashProgram;

    @Value("${pentahoBI.capitation.target.dashProjects}")
    private String targetDashProject;

    @Value("${pentahoBI.capitation.dataAccessId.dashDatas}")
    private String dataAccessIdDashDatas;

    @Value("${pentahoBI.capitation.dataAccessId.programTotal}")
    private String dataAccessIdProgramTotal;

    @Value("${pentahoBI.capitation.dataAccessId.projectTotal}")
    private String dataAccessIdProjectTotal;

    

    public List<NomeValorObject> getValoresPorTipo(int tipo) {
        HashMap<String, String> params = new HashMap<>();
        params.put("parampTipo", String.valueOf(tipo));
        
        String uri = buildEndpointUri(targetDashAll, dataAccessIdDashDatas, params);

        List<NomeValorObject> result = new ArrayList<NomeValorObject>();

        try {
            
            String response = doRequest(uri);
            
            List<Map<String, JsonNode>> mappedResponse = extractDataFromResponse(response);

            mappedResponse.forEach(register -> {
                String name = register.get("nome").asText("");
                Double value = register.get("valor").asDouble(0);
                result.add(new NomeValorObject(name, value));
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }   

        return result;
    }

    public double getProgramTotal() {

        double value = -1d;

        try {
            String response = doRequest(buildEndpointUri(targetDashProgram, dataAccessIdProgramTotal, null));

            List<Map<String, JsonNode>> mappedResponse = extractDataFromResponse(response);

            value = mappedResponse.get(0).get("valor").asDouble(0);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } 

        return value;
    }

    public double getProjectTotal() {

        double value = -1d;

        try {
            String response = doRequest(buildEndpointUri(targetDashProject, dataAccessIdProjectTotal, null));

            List<Map<String, JsonNode>> mappedResponse = extractDataFromResponse(response);

            value = mappedResponse.get(0).get("valor").asDouble(0) ;
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } 

        return value;
    }

    protected List<Map<String, JsonNode>> filterResult(List<Map<String, JsonNode>> datas, Map<String, String> filters){
        List<Map<String, JsonNode>> result = new ArrayList<>(datas);

        for(Map.Entry<String, String> filter : filters.entrySet()){
            result = result.stream().filter(register -> register.get(filter.getKey()).asText().equals(filter.getValue().toString())).toList();
        }

        return result;
    }

    @Override
    protected String buildEndpointUri(String target, String dataAccess, Map<String, String> params) {
        return super.buildEndpointUri(this.CaptationPath, target, dataAccess, params);
    }

    public List<Map<String, String>> consultarTodosValores(int ano, Map<String, String> filtro) {
        
        HashMap<String, String> params = new HashMap<>();
        params.put("ano", String.valueOf(ano));
        try {
            List<Map<String, JsonNode>> resultset = extractDataFromResponse(doRequest(buildEndpointUri(targetDashAll, dataAccessIdDashDatas, params)));

            resultset = filterResult(resultset, filtro);

            return resultset.stream().map(registro -> {
                
                Map<String, String> retorno = new HashMap<>();
                for(Entry<String, JsonNode> entry : registro.entrySet()) {
                    retorno.put(entry.getKey(), entry.getValue().asText(""));
                }
                return retorno;
            }).toList();
        } catch (Exception e) {
            this.LOGGER.error(e);
            return List.of(new HashMap<String, String>());
        }
    }

}
