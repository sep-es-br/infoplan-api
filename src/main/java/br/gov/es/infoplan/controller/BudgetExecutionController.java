package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.budgetExecution.*;
import br.gov.es.infoplan.service.BudgetExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Execução Orçamentária")
@CrossOrigin(origins = "${frontend.host}")
@RestController
@RequestMapping(value = "/budget-execution")
public class BudgetExecutionController {

    @Autowired
    private BudgetExecutionService budgetExecutionService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        PropertyEditorSupport arrayCleaner = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.trim().isEmpty()) {
                    setValue(null);
                    return;
                }
                String cleaned = text.replaceAll("[\\[\\]\\s]", "");
                if (cleaned.isEmpty()) {
                    setValue(null);
                    return;
                }
                List<Integer> list = Arrays.stream(cleaned.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                setValue(list);
            }
        };
        binder.registerCustomEditor(List.class, "month", arrayCleaner);
        binder.registerCustomEditor(List.class, "sourceType", arrayCleaner);
    }

    @Operation(summary = "Resumo da Receita Total", description = "Busca o consolidado de receita prevista vs realizada")
    @GetMapping("revenue-total")
    public RevenueTotalResponseDTO getRevenueTotal(
            @Validated @ModelAttribute BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueTotal(requestDTO);
    }

    @Operation(summary = "Lista de Receitas por Categoria", description = "Retorna as receitas agrupadas por categorias econômicas")
    @GetMapping("revenue-category")
    public List<RevenueCategoryResponseDTO> getRevenueCategoryList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueCategory(requestDTO);
    }

    @Operation(summary = "Lista de Receitas por Origem", description = "Retorna as receitas agrupadas por origem de recurso")
    @GetMapping("revenue-origin")
    public List<RevenueOriginResponseDTO> getRevenueOriginList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueOriginList(requestDTO);
    }

    @Operation(summary = "Lista de Receitas por Impostos", description = "Retorna o detalhamento das receitas provenientes de impostos")
    @GetMapping("revenue-taxes")
    public List<RevenueTaxesResponseDTO> getRevenueTaxesList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueTaxesList(requestDTO);
    }

    @Operation(summary = "Detalhamento de Receita ICMS", description = "Busca os valores arrecadados especificamente via ICMS")
    @GetMapping("revenue-icms")
    public List<RevenueICMSResponseDTO> getRevenueICMSList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueICMSList(requestDTO);
    }

    @Operation(summary = "Receitas por Participação", description = "Lista as receitas de participações e transferências obrigatórias")
    @GetMapping("revenue-participation")
    public List<RevenueParticipationResponseDTO> getRevenueParticipationList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueParticipationList(requestDTO);
    }

    @Operation(summary = "Despesas por GND", description = "Lista as despesas do Poder Executivo agrupadas por Grupo de Natureza de Despesa")
    @GetMapping("revenue-expense-gnd")
    public List<RevenueExpenseGNDResponseDTO> getRevenueExpenseGNDList(
            @Validated ExecutiveBranchRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueExpenseGNDList(requestDTO);
    }

    @Operation(summary = "Total de Despesas GND", description = "Retorna o total consolidado das despesas do Poder Executivo")
    @GetMapping("revenue-expense-gnd-total")
    public List<RevenueExpenseGNDTotalResponseDTO> getRevenueExpenseGNDTotalList(
            @Validated ExecutiveBranchRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueExpenseGNDTotalList(requestDTO);
    }

    @Operation(summary = "Transferências Correntes", description = "Lista as transferências correntes recebidas")
    @GetMapping("revenue-transfer-current")
    public List<RevenueCurrentTransferResponseDTO> getRevenueCurrentTransferList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueCurrentTransfer(requestDTO);
    }

}
