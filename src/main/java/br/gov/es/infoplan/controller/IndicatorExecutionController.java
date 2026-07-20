package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.IndicatorExecution.request.*;
import br.gov.es.infoplan.dto.IndicatorExecution.response.*;
import br.gov.es.infoplan.dto.UsuarioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import br.gov.es.infoplan.service.IndicatorExecutionService;

import java.util.List;

@Tag(name = "Indicadores Execução", description = "Consultas de receitas e despesas orçamentárias")
@RestController
@RequestMapping("/indicador")
@CrossOrigin(origins = "${frontend.host}")
@RequiredArgsConstructor
public class IndicatorExecutionController {

    private final IndicatorExecutionService indicatorExecutionService;

    @Operation(summary = "Lista de UO", description = "Busca os valores relacinados a UO")
    @GetMapping("/buscar-uo")
    public ResponseEntity<List<BudgetaryUnitResponseDTO>> searchUOByYear(
            @Validated @ModelAttribute FilterBugataryUnitDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        List<BudgetaryUnitResponseDTO> uoList = indicatorExecutionService.searchBudgetaryUnit(request, usuario);
        return uoList == null || uoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(uoList);
    }

    @Operation(summary = "Lista Ação Orçamentária", description = "Busca os valores relacionados Ações Orçamentária")
    @GetMapping("/buscar-acao")
    public ResponseEntity<List<ActionResponseDTO>> searchAction(
            @Validated @ModelAttribute FilterActionDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        List<ActionResponseDTO> actionList = indicatorExecutionService.searchAction(request, usuario);
        return actionList == null || actionList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(actionList);
    }

    @Operation(summary = "Busca fonte completa", description = "Retorna uma lista de fonte completa")
    @GetMapping("/buscar-fonte-completa")
    public ResponseEntity<List<FullSourceResponseDTO>> searchFullSource(
            @Validated @ModelAttribute FilterFullSourceDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        List<FullSourceResponseDTO> sourceList = indicatorExecutionService.searchFullSource(request, usuario);
        return sourceList == null || sourceList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(sourceList);
    }

    @Operation(summary = "Card total disponível sem reserva", description = "Retornar o total disponível sem reserva do gráfico Disponibilidade por UO")
    @GetMapping("/card-totais-disponivel-sem-reserva")
    public ResponseEntity<WithoutReversationResponseDTO> getCardAvailableWithoutReversation(
            @Validated @ModelAttribute FilterGeneralRequestDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        WithoutReversationResponseDTO reservation = indicatorExecutionService.getCardAvailableWithoutReversation(request, usuario);
        return reservation == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(reservation);
    }

    @Operation(summary = "Card total do sucesso do planejado", description = "Retornar o total das despesas liquidadas em relação ao autorizado")
    @GetMapping("/card-totais-sucesso-planejado")
    public ResponseEntity<CardSuccessResponseDTO> getCardSuccessPlanned(
            @Validated @ModelAttribute FilterGeneralRequestDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        CardSuccessResponseDTO successPlanned = indicatorExecutionService.getCardSuccessPlanned(request, usuario);
        return successPlanned == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(successPlanned);
    }

    @Operation(summary = "Card total do comparativo", description = "Retornar a varição da despesa liqudiada total do exercício")
    @GetMapping("/card-totais-comparativo")
    public ResponseEntity<CardComparativeResponseDTO> getCardComparative(
            @Validated @ModelAttribute FilterGeneralRequestDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        CardComparativeResponseDTO comparative = indicatorExecutionService.getCardComparative(request, usuario);
        return comparative == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(comparative);
    }

    @Operation(summary = "Card total PO com maior liquidação", description = "Retornar a PO e o valor da maior liquidação")
    @GetMapping("/card-totais-po-maior-liquidacao")
    public ResponseEntity<CardPOLiquidatedResponseDTO> getCardPOLiquidated(
            @Validated @ModelAttribute FilterGeneralRequestDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        CardPOLiquidatedResponseDTO poLiquidated = indicatorExecutionService.getCardPOLiquidated(request, usuario);
        return poLiquidated == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(poLiquidated);
    }

    @Operation(summary = "Card total exequibilidade", description = "")
    @GetMapping("/card-totais-exequibilidade")
    public ResponseEntity<CardFeasibilityResponseDTO> getCardFeasibility(
            @Validated @ModelAttribute FilterGeneralRequestDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        CardFeasibilityResponseDTO feasibility = indicatorExecutionService.getCardFeasibility(request, usuario);
        return feasibility == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(feasibility);
    }

    @Operation(summary = "Card total missão", description = "")
    @GetMapping("/card-totais-missao")
    public ResponseEntity<CardMissionResponseDTO> getCardMission(
            @Validated @ModelAttribute FilterGeneralRequestDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        CardMissionResponseDTO mission = indicatorExecutionService.getCardMission(request, usuario);
        return mission == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(mission);
    }

    @Operation(summary = "Card total alteração", description = "")
    @GetMapping("/card-totais-alteracao")
    public ResponseEntity<CardChangeResponseDTO> getCardChange(
            @Validated @ModelAttribute FilterGeneralRequestDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        CardChangeResponseDTO change = indicatorExecutionService.getCardChange(request, usuario);
        return change == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(change);
    }

    @Operation(summary = "Card total IGO", description = "")
    @GetMapping("/card-totais-IGO")
    public ResponseEntity<CardIGOResponseDTO> getCardIGO(
            @Validated @ModelAttribute FilterGeneralRequestDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        CardIGOResponseDTO IGO = indicatorExecutionService.getCardIGO(request, usuario);
        return IGO == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(IGO);
    }

    @Operation(summary = "Dash disponibilidade por UO", description = "Retorna o detalhamento das despesas do gráfico disponibilidade por UO")
    @GetMapping("/dash/disponibilidade-por-uo")
    public ResponseEntity<DashAvailabilityUoResponseDTO> getDashAvailabilityToUo(
            @Validated @ModelAttribute FilterGeneralRequestDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        DashAvailabilityUoResponseDTO listAvailability = indicatorExecutionService.getDashAvailabilityToUo(request, usuario);
        return listAvailability == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(listAvailability);
    }

    @Operation(summary = "Dash grupo despesas", description = "Retorna o detalhamento das despesas do gráfico comparativo e sucesso do planejado")
    @GetMapping("/dash/grupo-de-despesas")
    public ResponseEntity<List<DashSuccessPlannedResponseDTO>> getDashSuccessPlanned(
            @Validated @ModelAttribute FilterGeneralRequestDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        List<DashSuccessPlannedResponseDTO> list = indicatorExecutionService.getDashSuccessPlanned(request, usuario);
        return list == null || list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @Operation(summary = "Dash plano orçamentário", description = "Retorna o detalhamento de despesas das PO")
    @GetMapping("/dash/plano-orcamentario")
    public ResponseEntity<List<DashPlannedBudgetaryResponseDTO>> getDashPlannedBudgetary(
            @Validated @ModelAttribute FilterGeneralRequestDTO request,
            @AuthenticationPrincipal UsuarioDto usuario
    ) {
        List<DashPlannedBudgetaryResponseDTO> listPlanned = indicatorExecutionService.getDashPlannedBudgetary(request, usuario);
        return listPlanned == null || listPlanned.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listPlanned);
    }

    @Operation(summary = "Lista de PO", description = "Retorna a lista de PO")
    @GetMapping("/buscar-po")
    public ResponseEntity<List<POResponseDTO>> searchPO(
            @Validated @ModelAttribute FilterPODTO request,
            @AuthenticationPrincipal UsuarioDto usuarioDto
            ) {
        List<POResponseDTO> listPO = indicatorExecutionService.searchPO(request, usuarioDto);
        return listPO == null || listPO.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(listPO);
    }


}