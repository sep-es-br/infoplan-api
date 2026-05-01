package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterActionDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterBugataryUnitDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterFullSourceDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.response.ActionResponseDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.response.BudgetaryUnitResponseDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.response.FullSourceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import br.gov.es.infoplan.service.IndicatorExecutionService;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Indicadores Execução", description = "Consultas de receitas e despesas orçamentárias")
@RestController
@RequestMapping("/indicador")
@CrossOrigin(origins = "${frontend.host}")
@RequiredArgsConstructor
public class IndicatorExecutionController {

    @Autowired
    private IndicatorExecutionService indicatorExecutionService;

    @GetMapping("/buscar-uo")
    public ResponseEntity<List<BudgetaryUnitResponseDTO>> searchUOByYear(
            @Validated @ModelAttribute FilterBugataryUnitDTO request
            ) {
        List<BudgetaryUnitResponseDTO> uoList = indicatorExecutionService.searchBudgetaryUnit(request);
        return ResponseEntity.ok(uoList);
    }

    @GetMapping("/buscar-acao")
    public ResponseEntity<List<ActionResponseDTO>>  searchAction(
            @Validated @ModelAttribute FilterActionDTO request
    ) {
        List<ActionResponseDTO> actionList = indicatorExecutionService.searchAction(request);

        return ResponseEntity.ok(actionList);
    }


    @GetMapping("/buscar-fonte-completa")
    public ResponseEntity<List<FullSourceResponseDTO>>  searchFullSource(
            @Validated @ModelAttribute FilterFullSourceDTO request
    ) {
        List<FullSourceResponseDTO> sourceList = indicatorExecutionService.searchFullSource(request);

        return ResponseEntity.ok(sourceList);
    }


}
