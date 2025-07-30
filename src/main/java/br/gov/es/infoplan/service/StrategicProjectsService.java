package br.gov.es.infoplan.service;

import br.gov.es.infoplan.dto.strategicProject.StrategicProjectFilter;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectFilterValuesDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectIdAndNameDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectInvestmentSelectedDto;
import br.gov.es.infoplan.dto.strategicProject.ProgramDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectAccumulatedInvestmentDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectByStatusDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectDeliveriesBySelectedDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectMilestonesByPerformaceDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectProgramDetailsDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectProjectDetailsDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectRisksByClassificationDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectTimestampDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectDeliveriesDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectTotaisDto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StrategicProjectsService extends PentahoBIService {
  private final Logger LOGGER = LogManager.getLogger(PentahoBIService.class);

  private final String TODOS = "";

  @Value("${portfolioId}")
  private String portfolioId;

  // TARGETS

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

  @Value("${pentahoBI.pmo.target.deliveriesByStatus}")
  private String targetDeliveriesByStatus;

  @Value("${pentahoBI.pmo.target.deliveriesByPerformace}")
  private String targetDeliveriesByPerformace;

  @Value("${pentahoBI.pmo.target.deliveriesByType}")
  private String targetDeliveriesByType;

  @Value("${pentahoBI.pmo.target.projectByStatus}")
  private String targetProjectByStatus;

  @Value("${pentahoBI.pmo.target.criticalMilestonesForPerformace}")
  private String targetCriticalMilestonesForPerformace;

  @Value("${pentahoBI.pmo.target.risksByClassification}")
  private String targetRisksByClassification;

  @Value("${pentahoBI.pmo.target.accumulatedInvestment}")
  private String targetAccumulatedInvestment;

  @Value("${pentahoBI.pmo.target.investmentByArea}")
  private String targetInvestmentByArea;

  @Value("${pentahoBI.pmo.target.investmentByDelivery}")
  private String targetInvestmentByDelivery;

  @Value("${pentahoBI.pmo.target.investmentByProgram}")
  private String targetInvestmentByProgram;

  @Value("${pentahoBI.pmo.target.investmentByProgramAt}")
  private String targetInvestmentByProgramAt;

  @Value("${pentahoBI.pmo.target.investmentByProject}")
  private String targetInvestmentByProject;

  @Value("${pentahoBI.pmo.target.deliveriesByArea}")
  private String targetDeliveriesByArea;

  @Value("${pentahoBI.pmo.target.deliveriesByProgram}")
  private String targetDeliveriesByProgram;

  @Value("${pentahoBI.pmo.target.deliveriesByProgramAt}")
  private String targetDeliveriesByProgramAt;

  @Value("${pentahoBI.pmo.target.deliveriesByProject}")
  private String targetDeliveriesByProject;

  @Value("${pentahoBI.pmo.target.timestamp}")
  private String targetTimestamp;

  @Value("${pentahoBI.pmo.target.programDetailsContagemPE}")
  private String targetProgramDetailsContagemPE;

  @Value("${pentahoBI.pmo.target.programDetailsCusto}")
  private String targetProgramDetailsCusto;

  @Value("${pentahoBI.pmo.target.programDetailsProjetos}")
  private String targetProgramDetailsProjetos;

  @Value("${pentahoBI.pmo.target.programDetailsResponsavel}")
  private String targetProgramDetailsResponsavel;

  @Value("${pentahoBI.pmo.target.projectDetailsContagemPE}")
  private String targetProjectDetailsContagemPE;

  @Value("${pentahoBI.pmo.target.projectDetailsCusto}")
  private String targetProjectDetailsCusto;

  @Value("${pentahoBI.pmo.target.projectDetailsProgramas}")
  private String targetProjectDetailsPrograma;

  @Value("${pentahoBI.pmo.target.projectDetailsResponsavel}")
  private String targetProjectDetailsResponsavel;

  // DATA ACCESSID

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

  @Value("${pentahoBI.pmo.dataAccessId.deliveriesByStatus}")
  private String dataAccessIdDeliveriesByStatus;

  @Value("${pentahoBI.pmo.dataAccessId.deliveriesByPerformace}")
  private String dataAccessIdDeliveriesByPerformace;

  @Value("${pentahoBI.pmo.dataAccessId.deliveriesByType}")
  private String dataAccessIdDeliveriesByType;

  @Value("${pentahoBI.pmo.dataAccessId.projectByStatus}")
  private String dataAccessIdProjectByStatus;

  @Value("${pentahoBI.pmo.dataAccessId.criticalMilestonesForPerformace}")
  private String dataAccessIdCriticalMilestonesForPerformace;

  @Value("${pentahoBI.pmo.dataAccessId.risksByClassification}")
  private String dataAccessIdRisksByClassification;

  @Value("${pentahoBI.pmo.dataAccessId.accumulatedInvestment}")
  private String dataAccessIdAccumulatedInvestment;

  @Value("${pentahoBI.pmo.dataAccessId.investmentByArea}")
  private String dataAccessIdInvestmentByArea;

  @Value("${pentahoBI.pmo.dataAccessId.investmentByDelivery}")
  private String dataAccessIdInvestmentByDelivery;

  @Value("${pentahoBI.pmo.dataAccessId.investmentByProgram}")
  private String dataAccessIdInvestmentByProgram;

  @Value("${pentahoBI.pmo.dataAccessId.investmentByProgramAt}")
  private String dataAccessIdInvestmentByProgramAt;

  @Value("${pentahoBI.pmo.dataAccessId.investmentByProject}")
  private String dataAccessIdInvestmentByProject;

  @Value("${pentahoBI.pmo.dataAccessId.deliveriesByArea}")
  private String dataAccessIdDeliveriesByArea;

  @Value("${pentahoBI.pmo.dataAccessId.deliveriesByProgram}")
  private String dataAccessIdDeliveriesByProgram;

  @Value("${pentahoBI.pmo.dataAccessId.deliveriesByProgramAt}")
  private String dataAccessIdDeliveriesByProgramAt;

  @Value("${pentahoBI.pmo.dataAccessId.deliveriesByProject}")
  private String dataAccessIdDeliveriesByProject;

  @Value("${pentahoBI.pmo.dataAccessId.timestamp}")
  private String dataAccessIdTimestamp;

  @Value("${pentahoBI.pmo.dataAccessId.programDetailsContagemPE}")
  private String dataAccessIdProgramDetailsContagemPE;

  @Value("${pentahoBI.pmo.dataAccessId.programDetailsCusto}")
  private String dataAccessIdProgramDetailsCusto;

  @Value("${pentahoBI.pmo.dataAccessId.programDetailsProjetos}")
  private String dataAccessIdProgramDetailsProjetos;

  @Value("${pentahoBI.pmo.dataAccessId.programDetailsResponsavel}")
  private String dataAccessIdProgramDetailsResponsavel;

  @Value("${pentahoBI.pmo.dataAccessId.projectDetailsContagemPE}")
  private String dataAccessIdProjectDetailsContagemPE;

  @Value("${pentahoBI.pmo.dataAccessId.projectDetailsCusto}")
  private String dataAccessIdProjectDetailsCusto;

  @Value("${pentahoBI.pmo.dataAccessId.projectDetailsProgramas}")
  private String dataAccessIdProjectDetailsPrograma;

  @Value("${pentahoBI.pmo.dataAccessId.projectDetailsResponsavel}")
  private String dataAccessIdProjectDetailsResponsavel;

  private <T> List<T> consult(
      String target,
      String dataAccessId,
      Map<String, Object> params,
      Function<Map<String, JsonNode>, T> mapper) {
    List<T> retorno = new ArrayList<>();

    try {
      List<Map<String, JsonNode>> resultset = extractDataFromResponse(
          doRequest(buildEndpointUri(target, dataAccessId, params)));

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

  public StrategicProjectFilterValuesDto getAllFilter() {
    StrategicProjectFilterValuesDto dto = new StrategicProjectFilterValuesDto();
    dto.setArea(consultArea());
    dto.setProgramasOriginal(consultProgramaOriginal(TODOS));
    dto.setProgramasTransversal(consultProgramaTransversal(TODOS));
    dto.setProjetos(consultProjeto(TODOS, TODOS));
    dto.setEntregas(consultEntrega(TODOS, TODOS, TODOS));
    dto.setOrgaos(consultOrgao());
    dto.setLocalidades(consultLocalidade());

    return dto;
  }

  public StrategicProjectTimestampDto getTimestamp() {
    try {
      List<StrategicProjectTimestampDto> consulta = consultTimestamp();

      return consulta.isEmpty() ? new StrategicProjectTimestampDto() : consulta.get(0);
    } catch (Exception e) {
      e.printStackTrace();
      return new StrategicProjectTimestampDto();
    }
  }

  public StrategicProjectTotaisDto getTotals(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectTotaisDto> consulta = consultTotals(filter);

      return consulta.isEmpty() ? new StrategicProjectTotaisDto() : consulta.get(0);
    } catch (Exception e) {
      e.printStackTrace();
      return new StrategicProjectTotaisDto();
    }
  }

  public List<StrategicProjectDeliveriesDto> getDeliveriesByStatus(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectDeliveriesDto> consulta = consultDeliveryByStatus(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectDeliveriesDto>();
    }
  }

  public List<StrategicProjectDeliveriesDto> getDeliveriesByPerformace(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectDeliveriesDto> consulta = consultDeliveriesByPerformace(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectDeliveriesDto>();
    }
  }

  public List<StrategicProjectDeliveriesDto> getDeliveriesByType(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectDeliveriesDto> consulta = consultDeliveriesByType(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectDeliveriesDto>();
    }
  }

  public List<StrategicProjectByStatusDto> getProjectByStatus(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectByStatusDto> consulta = consultProjectByStatus(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectByStatusDto>();
    }
  }

  public List<StrategicProjectMilestonesByPerformaceDto> getCriticalMilestonesForPerformace(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectMilestonesByPerformaceDto> consulta = consultCriticalMilestonesForPerformace(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectMilestonesByPerformaceDto>();
    }
  }

  public List<StrategicProjectRisksByClassificationDto> getRisksByClassification(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectRisksByClassificationDto> consulta = consultRisksByClassification(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectRisksByClassificationDto>();
    }
  }

  public List<StrategicProjectAccumulatedInvestmentDto> getAccumulatedInvestment(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectAccumulatedInvestmentDto> consulta = consultAccumulatedInvestment(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectAccumulatedInvestmentDto>();
    }
  }

  public List<StrategicProjectInvestmentSelectedDto> getInvestmentByArea(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectInvestmentSelectedDto> consulta = consultInvestmentByArea(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectInvestmentSelectedDto>();
    }
  }

  public List<StrategicProjectInvestmentSelectedDto> getInvestmentByDelivery(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectInvestmentSelectedDto> consulta = consultInvestmentByDelivery(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectInvestmentSelectedDto>();
    }
  }

  public List<StrategicProjectInvestmentSelectedDto> getInvestmentByProgram(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectInvestmentSelectedDto> consulta = consultInvestmentByProgram(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectInvestmentSelectedDto>();
    }
  }

  public List<StrategicProjectInvestmentSelectedDto> getInvestmentByProgramAt(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectInvestmentSelectedDto> consulta = consultInvestmentByProgramAt(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectInvestmentSelectedDto>();
    }
  }

  public List<StrategicProjectInvestmentSelectedDto> getInvestmentByProject(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectInvestmentSelectedDto> consulta = consultInvestmentByProject(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectInvestmentSelectedDto>();
    }
  }

  public List<StrategicProjectDeliveriesBySelectedDto> getDeliveriesByArea(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectDeliveriesBySelectedDto> consulta = consultDeliveriesByArea(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectDeliveriesBySelectedDto>();
    }
  }

  public List<StrategicProjectDeliveriesBySelectedDto> getDeliveriesByProgram(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectDeliveriesBySelectedDto> consulta = consultDeliveriesByProgram(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectDeliveriesBySelectedDto>();
    }
  }

  public List<StrategicProjectDeliveriesBySelectedDto> getDeliveriesByProgramAt(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectDeliveriesBySelectedDto> consulta = consultDeliveriesByProgramAt(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectDeliveriesBySelectedDto>();
    }
  }

  public List<StrategicProjectDeliveriesBySelectedDto> getDeliveriesByProject(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);

      List<StrategicProjectDeliveriesBySelectedDto> consulta = consultDeliveriesByProject(filter);

      return consulta;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<StrategicProjectDeliveriesBySelectedDto>();
    }
  }

  public StrategicProjectFilterValuesDto getProgramsProjectsDeliveries(String areaId) {
    StrategicProjectFilterValuesDto dto = new StrategicProjectFilterValuesDto();
    dto.setProgramasOriginal(consultProgramaOriginal(areaId));
    dto.setProjetos(consultProjeto(areaId, TODOS));
    dto.setEntregas(consultEntrega(areaId, TODOS, TODOS));

    return dto;
  }

  public StrategicProjectFilterValuesDto getProjectsDeliveries(String areaId, String programId) {
    StrategicProjectFilterValuesDto dto = new StrategicProjectFilterValuesDto();
    dto.setProjetos(consultProjeto(areaId, programId));
    dto.setEntregas(consultEntrega(areaId, programId, TODOS));

    return dto;
  }

  public StrategicProjectFilterValuesDto getDeliveries(String areaId, String programId, String projectId) {
    StrategicProjectFilterValuesDto dto = new StrategicProjectFilterValuesDto();
    dto.setEntregas(consultEntrega(areaId, programId, projectId));

    return dto;
  }

  public StrategicProjectProgramDetailsDto getProgramDetails(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);
      StrategicProjectProgramDetailsDto dto = new StrategicProjectProgramDetailsDto();

      List<StrategicProjectProgramDetailsDto> consultContagemPE = consultProgramDetailsContagemPE(
        filter.getProgramaId().get(0).toString(), filter.getDataInicio(), filter.getDataFim()
      );

      if (!consultContagemPE.isEmpty()) {
        dto.setContagemPE(consultContagemPE.get(0).getContagemPE());
      }

      List<StrategicProjectProgramDetailsDto> consultCusto = consultProgramDetailsCusto(
        filter.getProgramaId().get(0).toString(), filter.getDataInicio(), filter.getDataFim()
      );

      if (!consultCusto.isEmpty()) {
        dto.setCustoPrevisto(consultCusto.get(0).getCustoPrevisto());
        dto.setCustoRealizado(consultCusto.get(0).getCustoRealizado());
      }

      List<StrategicProjectProgramDetailsDto> consultProjetos = consultProgramDetailsProjetos(
        filter.getProgramaId().get(0).toString(), filter.getDataInicio(), filter.getDataFim()
      );

      if (!consultProjetos.isEmpty()) {
        dto.setQtdeProjetos(consultProjetos.get(0).getQtdeProjetos());
      }

      List<StrategicProjectProgramDetailsDto> consultResponsavel = consultProgramDetailsResponsavel(
        filter.getProgramaId().get(0).toString(), filter.getDataInicio(), filter.getDataFim()
      );

      if (!consultResponsavel.isEmpty()) {
        StrategicProjectProgramDetailsDto firstEl = consultResponsavel.get(0);
        dto.setAreaId(firstEl.getAreaId());
        dto.setNomeArea(firstEl.getNomeArea());
        dto.setProgramaId(firstEl.getProgramaId());
        dto.setNomePrograma(firstEl.getNomePrograma());
        dto.setObjetivo(firstEl.getObjetivo());
        dto.setTransversal(firstEl.getTransversal());
        dto.setResponsavel(firstEl.getResponsavel());
      }

      return dto;
    } catch (Exception e) {
      e.printStackTrace();
      return new StrategicProjectProgramDetailsDto();
    }
  }

  public StrategicProjectProjectDetailsDto getProjectDetails(String filterJson) {
    try {
      StrategicProjectFilter filter = new ObjectMapper().readValue(filterJson, StrategicProjectFilter.class);
      StrategicProjectProjectDetailsDto dto = new StrategicProjectProjectDetailsDto();

      List<StrategicProjectProjectDetailsDto> consultContagemPE = consultProjectDetailsContagemPE(
        filter.getProjetoId().get(0).toString(), filter.getDataInicio(), filter.getDataFim()
      );

      if (!consultContagemPE.isEmpty()) {
        dto.setContagemPE(consultContagemPE.get(0).getContagemPE());
      }

      List<StrategicProjectProjectDetailsDto> consultCusto = consultProjectDetailsCusto(
        filter.getProjetoId().get(0).toString(), filter.getDataInicio(), filter.getDataFim()
      );

      if (!consultCusto.isEmpty()) {
        dto.setCustoPrevisto(consultCusto.get(0).getCustoPrevisto());
        dto.setCustoRealizado(consultCusto.get(0).getCustoRealizado());
      }

      List<StrategicProjectProjectDetailsDto> consultPrograma = consultProjectDetailsProgram(
        filter.getProjetoId().get(0).toString(), filter.getDataInicio(), filter.getDataFim()
      );

      if (!consultPrograma.isEmpty()) {
        consultPrograma.forEach((programa) -> {
          if (consultPrograma.indexOf(programa) == 0) {
            dto.setProgramas(programa.getProgramas());
          } else {
            dto.addPrograma(programa.getProgramas().get(0));
          }
        });
      }

      List<StrategicProjectProjectDetailsDto> consultResponsavel = consultProjectDetailsResponsavel(
        filter.getProjetoId().get(0).toString(), filter.getDataInicio(), filter.getDataFim()
      );

      if (!consultResponsavel.isEmpty()) {
        StrategicProjectProjectDetailsDto firstEl = consultResponsavel.get(0);
        dto.setOrgaoId(firstEl.getOrgaoId());
        dto.setNomeOrgao(firstEl.getNomeOrgao());
        dto.setAreaId(firstEl.getAreaId());
        dto.setNomeArea(firstEl.getNomeArea());
        dto.setProjetoId(firstEl.getProjetoId());
        dto.setNomeProjeto(firstEl.getNomeProjeto());
        dto.setDescricaoProjeto(firstEl.getDescricaoProjeto());
        dto.setStatusProjeto(firstEl.getStatusProjeto());
        dto.setResponsavel(firstEl.getResponsavel());
      }

      return dto;
    } catch (Exception e) {
      e.printStackTrace();
      return new StrategicProjectProjectDetailsDto();
    }
  }

  public List<StrategicProjectIdAndNameDto> consultArea() {
    HashMap<String, Object> params = new HashMap<>();
    params.put("parampCodPortfolio", portfolioId);
    return consult(
        targetArea,
        dataAccessIdArea,
        params,
        rs -> new StrategicProjectIdAndNameDto(
            rs.get("id").asInt(),
            rs.get("nome").asText(),
            rs.get("nome_completo").asText()));
  }

  public List<StrategicProjectIdAndNameDto> consultProgramaOriginal(String areaId) {
    HashMap<String, Object> params = new HashMap<>();
    params.put("parampCodPortfolio", portfolioId);
    params.put("parampCodArea", areaId);
    return consult(
        targetProgramaOriginal,
        dataAccessIdPrograma,
        params,
        rs -> new StrategicProjectIdAndNameDto(
            rs.get("id").asInt(),
            rs.get("nome").asText(),
            rs.get("nome_completo").asText()));
  }

  public List<StrategicProjectIdAndNameDto> consultProgramaTransversal(String areaId) {
    HashMap<String, Object> params = new HashMap<>();
    params.put("parampCodPortfolio", portfolioId);
    params.put("parampCodArea", areaId);
    return consult(
        targetProgramaTransversal,
        dataAccessIdPrograma,
        params,
        rs -> new StrategicProjectIdAndNameDto(
            rs.get("id").asInt(),
            rs.get("nome").asText(),
            rs.get("nome_completo").asText()));
  }

  public List<StrategicProjectIdAndNameDto> consultProjeto(String areaId, String programaId) {
    HashMap<String, Object> params = new HashMap<>();
    params.put("parampCodPortfolio", portfolioId);
    params.put("parampCodArea", areaId);
    params.put("parampCodPrograma", programaId);
    return consult(
        targetProjeto,
        dataAccessIdProjeto,
        params,
        rs -> new StrategicProjectIdAndNameDto(
            rs.get("id").asInt(),
            rs.get("nome").asText(),
            rs.get("nome_completo").asText()));
  }

  public List<StrategicProjectIdAndNameDto> consultEntrega(String areaId, String programaId, String projetoId) {
    HashMap<String, Object> params = new HashMap<>();
    params.put("parampCodPortfolio", portfolioId);
    params.put("parampCodArea", areaId);
    params.put("parampCodPrograma", programaId);
    params.put("parampCodProjeto", projetoId);
    return consult(
        targetEntrega,
        dataAccessIdEntrega,
        params,
        rs -> new StrategicProjectIdAndNameDto(
            rs.get("id").asInt(),
            rs.get("nome").asText(),
            rs.get("nome_completo").asText()));
  }

  public List<StrategicProjectIdAndNameDto> consultOrgao() {
    HashMap<String, Object> params = new HashMap<>();
    return consult(
        targetOrgao,
        dataAccessIdOrgao,
        params,
        rs -> new StrategicProjectIdAndNameDto(
            rs.get("id").asInt(),
            rs.get("nome").asText(),
            rs.get("nome_completo").asText()));
  }

  public List<StrategicProjectIdAndNameDto> consultLocalidade() {
    HashMap<String, Object> params = new HashMap<>();
    return consult(targetLocalidade, dataAccessIdLocalidade, params,
        rs -> new StrategicProjectIdAndNameDto(
            rs.get("id").asInt(),
            rs.get("nome").asText(),
            rs.get("tipo").asText(),
            rs.get("microrregiaoId").asInt()));
  }

  public List<StrategicProjectTotaisDto> consultTotals(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetTotais, dataAccessIdTotais, params, rs -> new StrategicProjectTotaisDto(
        rs.get("totalPrevisto").doubleValue(),
        rs.get("totalRealizado").doubleValue(),
        rs.get("totalEntregasPE").asInt(),
        rs.get("qdeProjetos").asInt(),
        rs.get("qdeProgramas").asInt()));
  }

  public List<StrategicProjectDeliveriesDto> consultDeliveryByStatus(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetDeliveriesByStatus, dataAccessIdDeliveriesByStatus, params,
        rs -> new StrategicProjectDeliveriesDto(
            rs.get("portfolioId").asInt(),
            rs.get("nome_portfolio").asText(),
            rs.get("areaId").asInt(),
            rs.get("nome_area").asText(),
            rs.get("programaId").asInt(),
            rs.get("nome_programa").asText(),
            rs.get("projetoId").asInt(),
            rs.get("nome_projeto").asText(),
            rs.get("orgaoId").asInt(),
            rs.get("nome_orgao").asText(),
            rs.get("entregaId").asInt(),
            rs.get("nome_entrega").asText(),
            rs.get("statusId").asInt(),
            rs.get("nome_status").asText(),
            rs.get("contagemPE").asInt(),
            rs.get("cor_status").asText()));
  }

  public List<StrategicProjectDeliveriesDto> consultDeliveriesByPerformace(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetDeliveriesByPerformace, dataAccessIdDeliveriesByPerformace, params,
        rs -> new StrategicProjectDeliveriesDto(
            rs.get("portfolioId").asInt(),
            rs.get("nome_portfolio").asText(),
            rs.get("areaId").asInt(),
            rs.get("nome_area").asText(),
            rs.get("programaId").asInt(),
            rs.get("nome_programa").asText(),
            rs.get("projetoId").asInt(),
            rs.get("nome_projeto").asText(),
            rs.get("orgaoId").asInt(),
            rs.get("nome_orgao").asText(),
            rs.get("entregaId").asInt(),
            rs.get("nome_entrega").asText(),
            rs.get("statusId").asInt(),
            rs.get("nome_status").asText(),
            rs.get("contagemPE").asInt(),
            rs.get("cor_status").asText()));
  }

  public List<StrategicProjectDeliveriesDto> consultDeliveriesByType(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetDeliveriesByType, dataAccessIdDeliveriesByType, params,
        rs -> new StrategicProjectDeliveriesDto(
            rs.get("portfolioId").asInt(),
            rs.get("nome_portfolio").asText(),
            rs.get("areaId").asInt(),
            rs.get("nome_area").asText(),
            rs.get("programaId").asInt(),
            rs.get("nome_programa").asText(),
            rs.get("projetoId").asInt(),
            rs.get("nome_projeto").asText(),
            rs.get("orgaoId").asInt(),
            rs.get("nome_orgao").asText(),
            rs.get("entregaId").asInt(),
            rs.get("nome_entrega").asText(),
            rs.get("statusId").asInt(),
            rs.get("nome_status").asText(),
            rs.get("contagemPE").asInt()));
  }

  public List<StrategicProjectByStatusDto> consultProjectByStatus(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetProjectByStatus, dataAccessIdProjectByStatus, params,
        rs -> new StrategicProjectByStatusDto(
            rs.get("portfolioId").asInt(),
            rs.get("nome_portfolio").asText(),
            rs.get("areaId").asInt(),
            rs.get("nome_area").asText(),
            rs.get("programaId").asInt(),
            rs.get("nome_programa").asText(),
            rs.get("projetoId").asInt(),
            rs.get("nome_projeto").asText(),
            rs.get("orgaoId").asInt(),
            rs.get("nome_orgao").asText(),
            rs.get("statusId").asInt(),
            rs.get("nome_status").asText(),
            rs.get("cor_status").asText()));
  }

  public List<StrategicProjectMilestonesByPerformaceDto> consultCriticalMilestonesForPerformace(
      StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetCriticalMilestonesForPerformace, dataAccessIdCriticalMilestonesForPerformace, params,
        rs -> new StrategicProjectMilestonesByPerformaceDto(
            rs.get("portfolioId").asInt(),
            rs.get("nome_portfolio").asText(),
            rs.get("areaId").asInt(),
            rs.get("nome_area").asText(),
            rs.get("programaId").asInt(),
            rs.get("nome_programa").asText(),
            rs.get("projetoId").asInt(),
            rs.get("nome_projeto").asText(),
            rs.get("orgaoId").asInt(),
            rs.get("nome_orgao").asText(),
            rs.get("mcId").asInt(),
            rs.get("nome_marcocritico").asText(),
            rs.get("statusId").asInt(),
            rs.get("nome_status").asText(),
            rs.get("cor_status").asText()));
  }

  public List<StrategicProjectRisksByClassificationDto> consultRisksByClassification(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetRisksByClassification, dataAccessIdRisksByClassification, params,
        rs -> new StrategicProjectRisksByClassificationDto(
            rs.get("portfolioId").asInt(),
            rs.get("nome_portfolio").asText(),
            rs.get("areaId").asInt(),
            rs.get("nome_area").asText(),
            rs.get("programaId").asInt(),
            rs.get("nome_programa").asText(),
            rs.get("projetoId").asInt(),
            rs.get("nome_projeto").asText(),
            rs.get("riscoId").asInt(),
            rs.get("risco_nome").asText(),
            rs.get("risco_descricao").asText(),
            rs.get("importanciaId").asInt(),
            rs.get("risco_importancia").asText(),
            rs.get("cor_importancia").asText()));
  }

  public List<StrategicProjectAccumulatedInvestmentDto> consultAccumulatedInvestment(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetAccumulatedInvestment, dataAccessIdAccumulatedInvestment, params,
        rs -> new StrategicProjectAccumulatedInvestmentDto(
            rs.get("anoMes").asInt(),
            rs.get("custoPrevisto").doubleValue(),
            rs.get("custoPrevistoAcumulado").doubleValue(),
            rs.get("custoRealizado").doubleValue(),
            rs.get("custoRealizadoAcumulado").doubleValue()));
  }

  public List<StrategicProjectInvestmentSelectedDto> consultInvestmentByArea(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetInvestmentByArea, dataAccessIdInvestmentByArea, params,
        rs -> new StrategicProjectInvestmentSelectedDto(
            rs.get("areaId").asInt(),
            rs.get("nome_area").asText(),
            rs.get("custoPrevisto").doubleValue(),
            rs.get("custoRealizado").doubleValue()));
  }

  public List<StrategicProjectInvestmentSelectedDto> consultInvestmentByDelivery(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetInvestmentByDelivery, dataAccessIdInvestmentByDelivery, params,
        rs -> new StrategicProjectInvestmentSelectedDto(
            rs.get("entregaId").asInt(),
            rs.get("nome_entrega").asText(),
            rs.get("custoPrevisto").doubleValue(),
            rs.get("custoRealizado").doubleValue()));
  }

  public List<StrategicProjectInvestmentSelectedDto> consultInvestmentByProgram(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetInvestmentByProgram, dataAccessIdInvestmentByProgram, params,
        rs -> new StrategicProjectInvestmentSelectedDto(
            rs.get("programaId").asInt(),
            rs.get("nome_programa").asText(),
            rs.get("custoPrevisto").doubleValue(),
            rs.get("custoRealizado").doubleValue()));
  }

  public List<StrategicProjectInvestmentSelectedDto> consultInvestmentByProgramAt(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetInvestmentByProgramAt, dataAccessIdInvestmentByProgramAt, params,
        rs -> new StrategicProjectInvestmentSelectedDto(
            rs.get("programaTransversalId").asInt(),
            rs.get("nome_programa_transversal").asText(),
            rs.get("custoPrevisto").doubleValue(),
            rs.get("custoRealizado").doubleValue()));
  }

  public List<StrategicProjectInvestmentSelectedDto> consultInvestmentByProject(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetInvestmentByProject, dataAccessIdInvestmentByProject, params,
        rs -> new StrategicProjectInvestmentSelectedDto(
            rs.get("projetoId").asInt(),
            rs.get("nome_projeto").asText(),
            rs.get("custoPrevisto").doubleValue(),
            rs.get("custoRealizado").doubleValue()));
  }

  public List<StrategicProjectDeliveriesBySelectedDto> consultDeliveriesByArea(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetDeliveriesByArea, dataAccessIdDeliveriesByArea, params,
        rs -> new StrategicProjectDeliveriesBySelectedDto(
            rs.get("areaId").asInt(),
            rs.get("nome_area").asText(),
            rs.get("execucao").asInt(),
            rs.get("concluida").asInt()));
  }

  public List<StrategicProjectDeliveriesBySelectedDto> consultDeliveriesByProgram(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetDeliveriesByProgram, dataAccessIdDeliveriesByProgram, params,
        rs -> new StrategicProjectDeliveriesBySelectedDto(
            rs.get("programaId").asInt(),
            rs.get("nome_programa").asText(),
            rs.get("execucao").asInt(),
            rs.get("concluida").asInt()));
  }

  public List<StrategicProjectDeliveriesBySelectedDto> consultDeliveriesByProgramAt(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetDeliveriesByProgramAt, dataAccessIdDeliveriesByProgramAt, params,
        rs -> new StrategicProjectDeliveriesBySelectedDto(
            rs.get("programaTransId").asInt(),
            rs.get("nome_programaTrans").asText(),
            rs.get("execucao").asInt(),
            rs.get("concluida").asInt()));
  }

  public List<StrategicProjectDeliveriesBySelectedDto> consultDeliveriesByProject(StrategicProjectFilter filter) {
    Map<String, Object> params = createFilterParams(filter);

    return consult(targetDeliveriesByProject, dataAccessIdDeliveriesByProject, params,
        rs -> new StrategicProjectDeliveriesBySelectedDto(
            rs.get("projetoId").asInt(),
            rs.get("nome_projeto").asText(),
            rs.get("execucao").asInt(),
            rs.get("concluida").asInt()));
  }

  public List<StrategicProjectTimestampDto> consultTimestamp() {
    HashMap<String, Object> params = new HashMap<>();

    return consult(targetTimestamp, dataAccessIdTimestamp, params,
      rs -> new StrategicProjectTimestampDto(
        rs.get("timestamp").asText()
      ));
  }

  public List<StrategicProjectProgramDetailsDto> consultProgramDetailsContagemPE(
    String programId,
    int dataInicio,
    int dataFim
  ) {
    HashMap<String, Object> params = new HashMap<>();

    params.put("paramportfolio", portfolioId);
    params.put("paramprograma", programId);
    params.put("paramde", dataInicio);
    params.put("paramate", dataFim);

    return consult(targetProgramDetailsContagemPE,
      dataAccessIdProgramDetailsContagemPE, params,
      rs -> new StrategicProjectProgramDetailsDto(
        rs.get("contagemPE").asInt()
      ));
  }

  public List<StrategicProjectProgramDetailsDto> consultProgramDetailsCusto(
    String programId,
    int dataInicio,
    int dataFim
  ) {
    HashMap<String, Object> params = new HashMap<>();
    params.put("paramportfolio", portfolioId);
    params.put("paramprograma", programId);
    params.put("paramde", dataInicio);
    params.put("paramate", dataFim);

    return consult(targetProgramDetailsCusto, dataAccessIdProgramDetailsCusto, params,
      rs -> new StrategicProjectProgramDetailsDto(
        rs.get("custoPrevisto").asLong(),
        rs.get("custoRealizado").asLong()
      ));
  }

  public List<StrategicProjectProgramDetailsDto> consultProgramDetailsProjetos(
    String programId,
    int dataInicio,
    int dataFim
  ) {
    HashMap<String, Object> params = new HashMap<>();
    params.put("paramportfolio", portfolioId);
    params.put("paramprograma", programId);
    params.put("paramde", dataInicio);
    params.put("paramate", dataFim);

    return consult(targetProgramDetailsProjetos, dataAccessIdProgramDetailsProjetos, params,
      rs -> new StrategicProjectProgramDetailsDto(
        rs.get("qtdeprojetos").asInt(),
        ""
      ));
  }

  public List<StrategicProjectProgramDetailsDto> consultProgramDetailsResponsavel(
    String programId,
    int dataInicio,
    int dataFim
  ) {
    HashMap<String, Object> params = new HashMap<>();
    params.put("paramportfolio", portfolioId);
    params.put("paramprograma", programId);
    params.put("paramde", dataInicio);
    params.put("paramate", dataFim);

    return consult(targetProgramDetailsResponsavel, dataAccessIdProgramDetailsResponsavel, params,
      rs -> new StrategicProjectProgramDetailsDto(
        rs.get("cod_area").asInt(),
        rs.get("nome_area").asText(),
        rs.get("cod_programa").asInt(),
        rs.get("nome_programa").asText(),
        rs.get("objetivo").asText(),
        rs.get("transversal").asInt(),
        rs.get("responsavel").asText()
      ));
  }

  public List<StrategicProjectProjectDetailsDto> consultProjectDetailsContagemPE(
    String projetoId,
    int dataInicio,
    int dataFim
  ) {
    HashMap<String, Object> params = new HashMap<>();

    params.put("paramportfolio", portfolioId);
    params.put("paramprojeto", projetoId);
    params.put("paramde", dataInicio);
    params.put("paramate", dataFim);

    return consult(targetProjectDetailsContagemPE, dataAccessIdProjectDetailsContagemPE, params,
      rs -> new StrategicProjectProjectDetailsDto(
        rs.get("contagemPE").asInt()
      ));
  }

  public List<StrategicProjectProjectDetailsDto> consultProjectDetailsCusto(
    String projetoId,
    int dataInicio,
    int dataFim
  ) {
    HashMap<String, Object> params = new HashMap<>();
    params.put("paramportfolio", portfolioId);
    params.put("paramprojeto", projetoId);
    params.put("paramde", dataInicio);
    params.put("paramate", dataFim);

    return consult(targetProjectDetailsCusto, dataAccessIdProjectDetailsCusto, params,
      rs -> new StrategicProjectProjectDetailsDto(
        rs.get("custoPrevisto").asLong(),
        rs.get("custoRealizado").asLong()
      ));
  }

  public List<StrategicProjectProjectDetailsDto> consultProjectDetailsProgram(
    String projetoId,
    int dataInicio,
    int dataFim
  ) {
    HashMap<String, Object> params = new HashMap<>();
    params.put("paramportfolio", portfolioId);
    params.put("paramprojeto", projetoId);
    params.put("paramde", dataInicio);
    params.put("paramate", dataFim);

    return consult(targetProjectDetailsPrograma, dataAccessIdProjectDetailsPrograma, params,
    rs -> {
      ProgramDto programa = new ProgramDto(
        rs.get("cod_programa").asInt(),
        rs.get("nome_programa").asText()
      );
      return new StrategicProjectProjectDetailsDto(programa);
    });
  }

  public List<StrategicProjectProjectDetailsDto> consultProjectDetailsResponsavel(
    String projetoId,
    int dataInicio,
    int dataFim
  ) {
    HashMap<String, Object> params = new HashMap<>();
    params.put("paramportfolio", portfolioId);
    params.put("paramprojeto", projetoId);
    params.put("paramde", dataInicio);
    params.put("paramate", dataFim);

    return consult(targetProjectDetailsResponsavel, dataAccessIdProjectDetailsResponsavel, params,
      rs -> new StrategicProjectProjectDetailsDto(
        rs.get("cod_orgao").asInt(),
        rs.get("nome_orgao").asText(),
        rs.get("cod_area").asInt(),
        rs.get("nome_area").asText(),
        rs.get("cod_projeto").asInt(),
        rs.get("nome_projeto").asText(),
        rs.get("descricao").asText(),
        rs.get("status_projeto").asText(),
        rs.get("responsavel").asText()
      ));
  }

  public static Map<String, Object> createFilterParams(StrategicProjectFilter filter) {
    Map<String, Object> params = new HashMap<>();

    params.put("paramportfolio", formatParam(filter.getPortfolioId()));
    params.put("paramarea", formatParam(filter.getAreaId()));
    params.put("paramprograma", formatParam(filter.getProgramaId()));
    params.put("paramprogramat", formatParam(filter.getProgramaTId()));
    params.put("paramprojeto", formatParam(filter.getProjetoId()));
    params.put("paramentrega", formatParam(filter.getEntregaId()));
    params.put("paramorgao", formatParam(filter.getOrgaoId()));
    params.put("paramlocalidade", formatParam(filter.getLocalidadeId()));
    params.put("paramde", filter.getDataInicio());
    params.put("paramate", filter.getDataFim());

    return params;
  }

  private static String formatParam(Object value) {
    if (value == null)
      return "";

    if (value instanceof Collection<?>) {
      return ((Collection<?>) value).stream()
          .map(String::valueOf)
          .collect(Collectors.joining(","));
    }

    return String.valueOf(value);
  }
}
