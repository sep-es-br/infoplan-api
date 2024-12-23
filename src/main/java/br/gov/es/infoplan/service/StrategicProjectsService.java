package br.gov.es.infoplan.service;

import br.gov.es.infoplan.dto.NomeValorObject;
import br.gov.es.infoplan.dto.capitacao.AllCapitacaoRow;
import br.gov.es.infoplan.dto.capitacao.CapitacaoFilter;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectFilter;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectFilterValuesDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectIdAndNameDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectTotaisDto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StrategicProjectsService extends PentahoBIService {
    
    private final Logger LOGGER = LogManager.getLogger(PentahoBIService.class);

    private  final String TODOS = "todos";

    @Value("${portfolioId}")
    private String portfolioId;

    @Value("${pentahoBI.pmo.path}")
    private String pmoPath;
    
    @Value("${pentahoBI.pmo.target.area}")
    private String targetArea;
    
    @Value("${pentahoBI.pmo.target.programa.original}")
    private String targetProgramaOriginal;
    
    @Value("${pentahoBI.pmo.target.programa.transversal}")
    private String targetProgramaTransversal;
    
    @Value("${pentahoBI.pmo.target.projeto}")
    private String targetProjeto;
    
    @Value("${pentahoBI.pmo.target.entrega}")
    private String targetEntrega;
    
    @Value("${pentahoBI.pmo.target.orgao}")
    private String targetOrgao;
    
    @Value("${pentahoBI.pmo.target.localidade}")
    private String targetLocalidade;

    @Value("${pentahoBI.pmo.target.totais}")
    private String targetTotais;
    
    @Value("${pentahoBI.pmo.dataAccessId.area}")
    private String dataAccessIdArea;
    
    @Value("${pentahoBI.pmo.dataAccessId.programa}")
    private String dataAccessIdPrograma;
    
    @Value("${pentahoBI.pmo.dataAccessId.projeto}")
    private String dataAccessIdProjeto;
    
    @Value("${pentahoBI.pmo.dataAccessId.entrega}")
    private String dataAccessIdEntrega;
    
    @Value("${pentahoBI.pmo.dataAccessId.orgao}")
    private String dataAccessIdOrgao;
    
    @Value("${pentahoBI.pmo.dataAccessId.localidade}")
    private String dataAccessIdLocalidade;

    @Value("${pentahoBI.pmo.dataAccessId.totais}")
    private String dataAccessIdTotais;



    private <T> List<T> consult(String target, String dataAccessId, Map<String, Object> params, Function<Map<String, JsonNode>, T> mapper) {
        List<T> retorno = new ArrayList<>();
        try {
            List<Map<String, JsonNode>> resultset = extractDataFromResponse(doRequest(buildEndpointUri(target, dataAccessId, params)));
            for (Map<String, JsonNode> rs : resultset) {
                T dto = mapper.apply(rs);
                retorno.add(dto);
            }
            return retorno;
        } catch (Exception e) {
            this.LOGGER.error("Error during consult: ", e);
            return List.of();
        }
    }


    @Override
    protected String buildEndpointUri(String target, String dataAccess, Map<String, Object> params) {
        return super.buildEndpointUri(pmoPath, target, dataAccess, params);
    }

    public StrategicProjectFilterValuesDto consultAll() {
        StrategicProjectFilterValuesDto dto = new StrategicProjectFilterValuesDto();        
        dto.setArea(consultArea());
        dto.setProgramasOriginal(consultProgramaOriginal(TODOS));
        dto.setProgramasTransversal(consultProgramaTransversal(TODOS));
        dto.setProjetos(consultProjeto(TODOS,TODOS));
        dto.setEntregas(consultEntrega(TODOS,TODOS,TODOS));
        dto.setOrgaos(consultOrgao());
        dto.setLocalidades(consultLocalidade());
        
        return dto;
    }

    public StrategicProjectTotaisDto consultTotals(String filterJson){

        try {
            StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

            List<StrategicProjectTotaisDto> consulta = consultTotals(
                filter.getPortfolioId(),
                filter.getAreaId(),
                filter.getProgramaId(),
                filter.getProgramaTId(),
                filter.getProjetoId(),
                filter.getEntregaId(),
                filter.getOrgaoId(),
                filter.getLocalidadeId(),
                filter.getDataInicio(),
                filter.getDataFim()
            );

            return consulta.isEmpty() ? new StrategicProjectTotaisDto() : consulta.get(0);

        } catch (Exception e) {
            e.printStackTrace();
            return new StrategicProjectTotaisDto();
        } 


    }

    public StrategicProjectFilterValuesDto consultProgramsProjectsDeliveries(String areaId) {

        StrategicProjectFilterValuesDto dto = new StrategicProjectFilterValuesDto();        
        dto.setProgramasOriginal(consultProgramaOriginal(areaId));
        dto.setProjetos(consultProjeto(areaId,TODOS));
        dto.setEntregas(consultEntrega(areaId,TODOS,TODOS));
        
        return dto;
    }

    public StrategicProjectFilterValuesDto consultProjectsDeliveries(String areaId, String programId) {

        StrategicProjectFilterValuesDto dto = new StrategicProjectFilterValuesDto();        
        dto.setProjetos(consultProjeto(areaId, programId));
        dto.setEntregas(consultEntrega(areaId, programId,TODOS));
        
        return dto;
    }

    public StrategicProjectFilterValuesDto consultDeliveries(String areaId, String programId, String projectId) {

        StrategicProjectFilterValuesDto dto = new StrategicProjectFilterValuesDto();        
        dto.setEntregas(consultEntrega(areaId, programId, projectId));
        
        return dto;
    }



    public List<StrategicProjectIdAndNameDto> consultArea() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("parampCodPortfolio", portfolioId );
        return consult(targetArea, dataAccessIdArea, params, rs -> new StrategicProjectIdAndNameDto(rs.get("id").asInt(), rs.get("nome").asText()));
    }

    public List<StrategicProjectIdAndNameDto> consultProgramaOriginal(String areaId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("parampCodPortfolio", portfolioId);
        params.put("parampCodArea", areaId);
        return consult(targetProgramaOriginal, dataAccessIdPrograma, params, rs -> new StrategicProjectIdAndNameDto(rs.get("id").asInt(), rs.get("nome").asText()));
    }

    public List<StrategicProjectIdAndNameDto> consultProgramaTransversal(String areaId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("parampCodPortfolio", portfolioId);
        params.put("parampCodArea", areaId);
        return consult(targetProgramaTransversal, dataAccessIdPrograma, params, rs -> new StrategicProjectIdAndNameDto(rs.get("id").asInt(), rs.get("nome").asText()));
    }

    public List<StrategicProjectIdAndNameDto> consultProjeto(String areaId, String programaId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("parampCodPortfolio", portfolioId);
        params.put("parampCodArea", areaId);
        params.put("parampCodPrograma", programaId);
        return consult(targetProjeto, dataAccessIdProjeto, params, rs -> new StrategicProjectIdAndNameDto(rs.get("id").asInt(), rs.get("nome").asText()));
    }

    public List<StrategicProjectIdAndNameDto> consultEntrega(String areaId, String programaId, String projetoId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("parampCodPortfolio", portfolioId);
        params.put("parampCodArea", areaId);
        params.put("parampCodPrograma", programaId);
        params.put("parampCodProjeto", projetoId);
        return consult(targetEntrega, dataAccessIdEntrega, params, rs -> new StrategicProjectIdAndNameDto(rs.get("id").asInt(), rs.get("nome").asText()));
    }

    public List<StrategicProjectIdAndNameDto> consultOrgao() {
        HashMap<String, Object> params = new HashMap<>();
        return consult(targetOrgao, dataAccessIdOrgao, params, rs -> new StrategicProjectIdAndNameDto(rs.get("id").asInt(), rs.get("nome").asText()));
    }

    public List<StrategicProjectIdAndNameDto> consultLocalidade() {
        HashMap<String, Object> params = new HashMap<>();
        return consult(targetLocalidade, dataAccessIdLocalidade, params, rs -> new StrategicProjectIdAndNameDto(rs.get("id").asInt(), rs.get("nome").asText()));
    }
    
    public List<StrategicProjectTotaisDto> consultTotals(int portfolioId, int areaId, int programaId, int programaTId, int projetoId, 
                                                            int entregaId, int orgaoId, int localidadeId, int dataInicio, int dataFim){
        HashMap<String, Object> params = new HashMap<>();
        params.put("paramportfolio", portfolioId);
        params.put("paramarea", areaId);
        params.put("paramprograma", programaId);
        params.put("paramprogramat", programaTId);
        params.put("paramprojeto", projetoId);
        params.put("paramentrega", entregaId);
        params.put("paramorgao", orgaoId);
        params.put("paramlocalidade", localidadeId);
        params.put("paramde", dataInicio);
        params.put("paramate", dataFim);

        return consult(targetTotais, dataAccessIdTotais, params, rs -> new StrategicProjectTotaisDto(
            rs.get("totalPrevisto").floatValue(), 
            rs.get("totalRealizado").floatValue(), 
            rs.get("totalEntregasPE").asInt(), 
            rs.get("qdeProjetos").asInt(), 
            rs.get("qdeProgramas").asInt()));
    }
    

}
