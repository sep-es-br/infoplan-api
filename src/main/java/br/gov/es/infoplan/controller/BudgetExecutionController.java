package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.budgetExecution.*;
import br.gov.es.infoplan.service.BudgetExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${frontend.host}")
@RestController
@RequestMapping(value = "/budget-execution")
public class BudgetExecutionController {

    @Autowired
    private BudgetExecutionService budgetExecutionService;

    @GetMapping("revenue-total")
    public RevenueTotalResponseDTO getRevenueTotal(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueTotal(requestDTO);
    }

    @GetMapping("revenue-category")
    public List<RevenueCategoryResponseDTO> getRevenueCategoryList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueCategory(requestDTO);
    }

    @GetMapping("revenue-origin")
    public List<RevenueOriginResponseDTO> getRevenueOriginList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueOriginList(requestDTO);
    }

    @GetMapping("revenue-taxes")
    public List<RevenueTaxesResponseDTO> getRevenueTaxesList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueTaxesList(requestDTO);
    }

    @GetMapping("revenue-icms")
    public List<RevenueICMSResponseDTO> getRevenueICMSList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueICMSList(requestDTO);
    }

    @GetMapping("revenue-participation")
    public List<RevenueParticipationResponseDTO> getRevenueParticipationList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueParticipationList(requestDTO);
    }

    @GetMapping("revenue-expense-gnd")
    public List<RevenueExpenseGNDResponseDTO> getRevenueExpenseGNDList(
            @Validated ExecutiveBranchRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueExpenseGNDList(requestDTO);
    }

    @GetMapping("revenue-expense-gnd-total")
    public List<RevenueExpenseGNDTotalResponseDTO> getRevenueExpenseGNDTotalList(
            @Validated ExecutiveBranchRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueExpenseGNDTotalList(requestDTO);
    }

    @GetMapping("revenue-transfer-current")
    public List<RevenueCurrentTransferResponseDTO> getRevenueCurrentTransferList(
            @Validated BudgetExecutionRequestDTO requestDTO) {
        return budgetExecutionService.getRevenueCurrentTransfer(requestDTO);
    }

}
