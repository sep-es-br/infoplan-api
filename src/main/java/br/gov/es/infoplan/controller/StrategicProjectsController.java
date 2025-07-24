package br.gov.es.infoplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.es.infoplan.dto.strategicProject.StrategicProjectFilterValuesDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectInvestmentSelectedDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectDeliveriesDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectAccumulatedInvestmentDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectByStatusDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectDeliveriesBySelectedDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectMilestonesByPerformaceDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectProgramDetailsDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectRisksByClassificationDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectTimestampDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectTotaisDto;
import br.gov.es.infoplan.service.StrategicProjectsService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${frontend.host}")
@RestController
@RequestMapping("/strategicProjects")
@RequiredArgsConstructor
public class StrategicProjectsController {
  private final StrategicProjectsService service;

  @Value("${frontend.host}")
  private String frontHost;

  @GetMapping("/timestamp")
  public StrategicProjectTimestampDto getTimestamp() {
    StrategicProjectTimestampDto strategicProjectDto = service.getTimestamp();
    return strategicProjectDto;
  }

  @GetMapping("/all")
  public StrategicProjectFilterValuesDto getAll() {
    StrategicProjectFilterValuesDto strategicProjectDto = service.getAllFilter();
    return strategicProjectDto;
  }

  @GetMapping("/programsProjectsDeliveries")
  public StrategicProjectFilterValuesDto getProgramsProjectsDeliveries(@RequestParam String areaId) {
    StrategicProjectFilterValuesDto strategicProjectDto = service.getProgramsProjectsDeliveries(areaId);
    return strategicProjectDto;
  }

  @GetMapping("/projectsDeliveries")
  public StrategicProjectFilterValuesDto getProjectsDeliveries(@RequestParam String areaId, String programId) {
    StrategicProjectFilterValuesDto strategicProjectDto = service.getProjectsDeliveries(areaId, programId);
    return strategicProjectDto;
  }

  @GetMapping("/deliveries")
  public StrategicProjectFilterValuesDto getDeliveries(@RequestParam String areaId, String programId, String projectId) {
    StrategicProjectFilterValuesDto strategicProjectDto = service.getDeliveries(areaId, programId, projectId);
    return strategicProjectDto;
  }

  @GetMapping("/totais")
  public StrategicProjectTotaisDto getTotals(@RequestParam String filterJson) {
    return service.getTotals(filterJson);
  }

  @GetMapping("/deliveriesByStatus")
  public List<StrategicProjectDeliveriesDto> getDeliveriesByStatus(@RequestParam String filterJson) {
    return service.getDeliveriesByStatus(filterJson);
  }

  @GetMapping("/deliveriesByPerformace")
  public List<StrategicProjectDeliveriesDto> getDeliveriesByPerformace(@RequestParam String filterJson) {
    return service.getDeliveriesByPerformace(filterJson);
  }

  @GetMapping("/deliveriesByType")
  public List<StrategicProjectDeliveriesDto> getDeliveriesByType(@RequestParam String filterJson) {
    return service.getDeliveriesByType(filterJson);
  }

  @GetMapping("/projectByStatus")
  public List<StrategicProjectByStatusDto> getProjectByStatus(@RequestParam String filterJson) {
    return service.getProjectByStatus(filterJson);
  }

  @GetMapping("/milestones")
  public List<StrategicProjectMilestonesByPerformaceDto> getCriticalMilestonesForPerformace(@RequestParam String filterJson) {
    return service.getCriticalMilestonesForPerformace(filterJson);
  }

  @GetMapping("/risksByClassification")
  public List<StrategicProjectRisksByClassificationDto> getRisksByClassification(@RequestParam String filterJson) {
    return service.getRisksByClassification(filterJson);
  }

  @GetMapping("/accumulatedInvestment")
  public List<StrategicProjectAccumulatedInvestmentDto> getAccumulatedInvestment(@RequestParam String filterJson) {
    return service.getAccumulatedInvestment(filterJson);
  }

  @GetMapping("/investmentByArea")
  public List<StrategicProjectInvestmentSelectedDto> getInvestmentByArea(@RequestParam String filterJson) {
    return service.getInvestmentByArea(filterJson);
  }

  @GetMapping("/investmentByDelivery")
  public List<StrategicProjectInvestmentSelectedDto> getInvestmentByDelivery(@RequestParam String filterJson) {
    return service.getInvestmentByDelivery(filterJson);
  }

  @GetMapping("/investmentByProgram")
  public List<StrategicProjectInvestmentSelectedDto> getInvestmentByProgram(@RequestParam String filterJson) {
    return service.getInvestmentByProgram(filterJson);
  }

  @GetMapping("/investmentByProgramAt")
  public List<StrategicProjectInvestmentSelectedDto> getInvestmentByProgramAt(@RequestParam String filterJson) {
    return service.getInvestmentByProgramAt(filterJson);
  }

  @GetMapping("/investmentByProject")
  public List<StrategicProjectInvestmentSelectedDto> getInvestmentByProject(@RequestParam String filterJson) {
    return service.getInvestmentByProject(filterJson);
  }

  @GetMapping("/deliveriesByArea")
  public List<StrategicProjectDeliveriesBySelectedDto> getDeliveriesByArea(@RequestParam String filterJson) {
    return service.getDeliveriesByArea(filterJson);
  }

  @GetMapping("/deliveriesByProgram")
  public List<StrategicProjectDeliveriesBySelectedDto> getDeliveriesByProgram(@RequestParam String filterJson) {
    return service.getDeliveriesByProgram(filterJson);
  }

  @GetMapping("/deliveriesByProgramAt")
  public List<StrategicProjectDeliveriesBySelectedDto> getDeliveriesByProgramAt(@RequestParam String filterJson) {
    return service.getDeliveriesByProgramAt(filterJson);
  }

  @GetMapping("/deliveriesByProject")
  public List<StrategicProjectDeliveriesBySelectedDto> getDeliveriesByProject(@RequestParam String filterJson) {
    return service.getDeliveriesByProject(filterJson);
  }

  @GetMapping("/programDetails")
  public StrategicProjectProgramDetailsDto getProgramDetails(@RequestParam String filterJson) {
    return service.getProgramDetails(filterJson);
  }
}
