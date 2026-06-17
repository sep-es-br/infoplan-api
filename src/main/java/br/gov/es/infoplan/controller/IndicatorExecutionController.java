package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterActionDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterBugataryUnitDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterFullSourceDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterGeneralRequestDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.response.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import br.gov.es.infoplan.service.IndicatorExecutionService;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collections;
import java.util.List;

@Tag(name = "Indicadores Execução", description = "Consultas de receitas e despesas orçamentárias")
@RestController
@RequestMapping("/indicador")
@CrossOrigin(origins = "${frontend.host}")
@RequiredArgsConstructor
public class IndicatorExecutionController {

    @Autowired
    private IndicatorExecutionService indicatorExecutionService;

    @Operation(summary = "Lista de UO", description = "Busca os valores relacinados a UO")
    @GetMapping("/buscar-uo")
    public ResponseEntity<List<BudgetaryUnitResponseDTO>> searchUOByYear(
            @Validated @ModelAttribute FilterBugataryUnitDTO request
    ) {
        List<BudgetaryUnitResponseDTO> uoList = indicatorExecutionService.searchBudgetaryUnit(request);
        return ResponseEntity.ok(uoList);
    }

    @Operation(summary = "Lista Ação Orçamentária", description = "Busca os valores relacionados Ações Orçamentária")
    @GetMapping("/buscar-acao")
    public ResponseEntity<List<ActionResponseDTO>> searchAction(
            @Validated @ModelAttribute FilterActionDTO request
    ) {
        List<ActionResponseDTO> actionList = indicatorExecutionService.searchAction(request);

        return ResponseEntity.ok(actionList);
    }

    @Operation(summary = "Busca fonte completa", description = "Retorna uma lista de fonte completa")
    @GetMapping("/buscar-fonte-completa")
    public ResponseEntity<List<FullSourceResponseDTO>> searchFullSource(
            @Validated @ModelAttribute FilterFullSourceDTO request
    ) {
        List<FullSourceResponseDTO> sourceList = indicatorExecutionService.searchFullSource(request);

        return ResponseEntity.ok(sourceList);
    }

    @Operation(summary = "Card total disponível sem reserva", description = "Retornar o total disponível sem reserva  do gráfico Disponibilidade por UO")
    @GetMapping("/card-totais-disponivel-sem-reserva")
    public ResponseEntity<WithoutReversationResponseDTO> getCardAvailableWithoutReversation(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        WithoutReversationResponseDTO reservation = indicatorExecutionService
                .getCardAvailableWithoutReversation(request);

        return  ResponseEntity.ok(reservation);
    }

    @Operation(summary = "Card total do sucesso do planejado", description = "Retornar o total das despesas liquidadas em relação ao autorizado")
    @GetMapping("/card-totais-sucesso-planejado")
    public ResponseEntity<CardSuccessResponseDTO> getCardSuccessPlanned(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardSuccessResponseDTO successPlanned = indicatorExecutionService.getCardSuccessPlanned(request);
        return ResponseEntity.ok(successPlanned);
    }

    @Operation(summary = "Card total do comparativo", description = "Retornar a varição da despesa liqudiada total do exercício")
    @GetMapping("/card-totais-comparativo")
    public ResponseEntity<CardComparativeResponseDTO> getCardComparative(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardComparativeResponseDTO comparative = indicatorExecutionService.getCardComparative(request);

        return ResponseEntity.ok(comparative);
    }

    @Operation(summary = "Card total PO com maior liquidação", description = "Retornar a PO e o valor da maior liquidação")
    @GetMapping("/card-totais-po-maior-liquidacao")
    public ResponseEntity<CardPOLiquidatedResponseDTO> getCardPOLiquidated(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardPOLiquidatedResponseDTO poLiquidated = indicatorExecutionService.getCardPOLiquidated(request);
        return ResponseEntity.ok(poLiquidated);
    }

    @Operation(summary = "Card total exequibilidade", description = "")
    @GetMapping("/card-totais-exequibilidade")
    public ResponseEntity<CardFeasibilityResponseDTO> getCardFeasibility(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardFeasibilityResponseDTO feasibility = indicatorExecutionService.getCardFeasibility(request);
        return ResponseEntity.ok(feasibility);
    }

    @Operation(summary = "Card total missão", description = "")
    @GetMapping("/card-totais-missao")
    public ResponseEntity<CardMissionResponseDTO> getCardMission(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardMissionResponseDTO mission = indicatorExecutionService.getCardMission(request);
        return ResponseEntity.ok(mission);
    }

    @Operation(summary = "Card total alteração", description = "")
    @GetMapping("/card-totais-alteracao")
    public ResponseEntity<CardChangeResponseDTO> getCardChange(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardChangeResponseDTO change = indicatorExecutionService.getCardChange(request);
        return ResponseEntity.ok(change);
    }

    @Operation(summary = "Card total IGO", description = "")
    @GetMapping("/card-totais-IGO")
    public ResponseEntity<CardIGOResponseDTO> getCardIGO(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        CardIGOResponseDTO IGO = indicatorExecutionService.getCardIGO(request);

        if(IGO == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(IGO);
    }

    @Operation(summary = "Dash disponibilidade por UO", description = "Retona o detalhamento das despesas do gráfico disponibilidade por UO ")
    @GetMapping("/dash/disponibilidade-por-uo")
    public ResponseEntity<DashAvailabilityUoResponseDTO> getDashAvailabilityToUo(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        DashAvailabilityUoResponseDTO listAvailability = indicatorExecutionService.getDashAvailabilityToUo(request);

        if(listAvailability == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listAvailability);
    }

    @Operation(summary = "Dash grupo despesas", description = "Retorna o detalhamento das despesas do gráfico comparativo e sucesso do planejado")
    @GetMapping("/dash/grupo-de-despesas")
    public ResponseEntity<List<DashSuccessPlannedResponseDTO>> getDashSuccessPlanned(
            @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        List<DashSuccessPlannedResponseDTO> list = indicatorExecutionService.getDashSuccessPlanned(request);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Dash plano orçamentário", description = "Retorna o detalhamento de despesas das PO")
    @GetMapping("/dash/plano-orcamentario")
    public ResponseEntity<List<DashPlannedBudgetaryResponseDTO>> getDashPlannedBudgetary(
        @Validated @ModelAttribute FilterGeneralRequestDTO request
    ) {
        List<DashPlannedBudgetaryResponseDTO> listPlanned = indicatorExecutionService.getDashPlannedBudgetary(request);

        return ResponseEntity.ok(listPlanned);
    }
}
