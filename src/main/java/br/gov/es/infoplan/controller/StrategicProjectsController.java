package br.gov.es.infoplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.es.infoplan.dto.strategicProject.StrategicProjectFilterValuesDto;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectIdAndNameDto;
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

    @GetMapping("/all")
    public StrategicProjectFilterValuesDto getAll() {
        StrategicProjectFilterValuesDto strategicProjectDto = service.consultAll();
        return strategicProjectDto;
    }

    @GetMapping("/programsProjectsDeliveries")
    public StrategicProjectFilterValuesDto getProgramsProjectsDeliveries(@RequestParam String areaId) {
        StrategicProjectFilterValuesDto strategicProjectDto = service.consultProgramsProjectsDeliveries(areaId);
        return strategicProjectDto;
    }

    @GetMapping("/projectsDeliveries")
    public StrategicProjectFilterValuesDto getProjectsDeliveries(@RequestParam String areaId, String programId) {
        StrategicProjectFilterValuesDto strategicProjectDto = service.consultProjectsDeliveries(areaId, programId);
        return strategicProjectDto;
    }

    @GetMapping("/deliveries")
    public StrategicProjectFilterValuesDto getDeliveries(@RequestParam String areaId, String programId, String projectId) {
        StrategicProjectFilterValuesDto strategicProjectDto = service.consultDeliveries(areaId, programId, projectId);
        return strategicProjectDto;
    }

    
}
