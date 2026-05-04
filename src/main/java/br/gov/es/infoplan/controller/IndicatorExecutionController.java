package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterActionDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterBugataryUnitDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterFullSourceDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterGeneralRequestDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.response.*;
import com.nimbusds.oauth2.sdk.SuccessResponse;
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
    public ResponseEntity<List<ActionResponseDTO>> searchAction(
            @Validated @ModelAttribute FilterActionDTO request
    ) {
        List<ActionResponseDTO> actionList = indicatorExecutionService.searchAction(request);

        return ResponseEntity.ok(actionList);
    }


    @GetMapping("/buscar-fonte-completa")
    public ResponseEntity<List<FullSourceResponseDTO>> searchFullSource(
            @Validated @ModelAttribute FilterFullSourceDTO request
    ) {
        List<FullSourceResponseDTO> sourceList = indicatorExecutionService.searchFullSource(request);

        return ResponseEntity.ok(sourceList);
    }

    @GetMapping("/card-totais-disponivel-sem-reserva")
    public ResponseEntity<WithoutReversationResponseDTO> getCardAvailableWithoutReversation(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        WithoutReversationResponseDTO reservation = indicatorExecutionService
                .getCardAvailableWithoutReversation(request);

        return  ResponseEntity.ok(reservation);
    }

    @GetMapping("/card-totais-sucesso-planejado")
    public ResponseEntity<CardSuccessResponseDTO> getCardSuccessPlanned(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardSuccessResponseDTO successPlanned = indicatorExecutionService.getCardSuccessPlanned(request);
        return ResponseEntity.ok(successPlanned);
    }

    @GetMapping("/card-totais-comparativo")
    public ResponseEntity<CardComparativeResponseDTO> getCardComparative(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardComparativeResponseDTO comparative = indicatorExecutionService.getCardComparative(request);

        return ResponseEntity.ok(comparative);
    }

    @GetMapping("/card-totais-po-maior-liquidacao")
    public ResponseEntity<CardPOLiquidatedResponseDTO> getCardPOLiquidated(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardPOLiquidatedResponseDTO poLiquidated = indicatorExecutionService.getCardPOLiquidated(request);
        return ResponseEntity.ok(poLiquidated);
    }

    @GetMapping("/card-totais-exequibilidade")
    public ResponseEntity<CardFeasibilityResponseDTO> getCardFeasibility(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardFeasibilityResponseDTO feasibility = indicatorExecutionService.getCardFeasibility(request);
        return ResponseEntity.ok(feasibility);
    }

    @GetMapping("/card-totais-missao")
    public ResponseEntity<CardMissionResponseDTO> getCardMission(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardMissionResponseDTO mission = indicatorExecutionService.getCardMission(request);
        return ResponseEntity.ok(mission);
    }

    @GetMapping("/card-totais-alteracao")
    public ResponseEntity<CardChangeResponseDTO> getCardChange(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardChangeResponseDTO change = indicatorExecutionService.getCardChange(request);
        return ResponseEntity.ok(change);
    }

    @GetMapping("/card-totais-IGO")
    public ResponseEntity<CardIGOResponseDTO> getCardIGO(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardIGOResponseDTO IGO = indicatorExecutionService.getCardIGO(request);
        return ResponseEntity.ok(IGO);
    }
}
