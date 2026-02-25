package br.gov.es.infoplan.service;

import br.gov.es.infoplan.config.pentahoBi.PentahoBiProperties;
import br.gov.es.infoplan.dto.budgetExecution.*;
import br.gov.es.infoplan.utils.ApiUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BudgetExecutionService {

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private PentahoBiProperties properties;

    private String pmoPath;

    @PostConstruct
    public void init() {
        this.pmoPath = properties.getExecucaoOrcamentaria().getPath();
        log.info("PMO Path initialized: {}", pmoPath);
    }

    public RevenueTotalResponseDTO getRevenueTotal(BudgetExecutionRequestDTO budgetPanel) {
        List<RevenueTotalResponseDTO> revenues = consultRevenueTotal(budgetPanel);

        if (revenues.isEmpty()) {
            return new RevenueTotalResponseDTO();
        }

        BigDecimal netVsPlannedPercentage = calcNetVsPlannedPercentage(revenues);

        RevenueTotalResponseDTO mainRevenue = revenues.get(0);
        mainRevenue.setPercentage(netVsPlannedPercentage);

        return mainRevenue;
    }

    private BigDecimal calcNetVsPlannedPercentage(List<RevenueTotalResponseDTO> revenue) {
        if (revenue == null || revenue.isEmpty()) {
            return BigDecimal.ZERO;
        }

        RevenueTotalResponseDTO data = revenue.get(0);
        BigDecimal net = data.getNetRevenueValue();
        BigDecimal planned = data.getPlannedRevenueValue();

        if (planned == null || planned.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        if (net == null)
            net = BigDecimal.ZERO;

        return net.multiply(new BigDecimal("100"))
                .divide(planned, 2, RoundingMode.HALF_UP);
    }

    public List<RevenueCategoryResponseDTO> getRevenueCategory(BudgetExecutionRequestDTO requestDTO) {
        List<RevenueCategoryResponseDTO> revenueCategoryList = consultRevenueCategory(requestDTO);

        if (revenueCategoryList.isEmpty()) {
            return Collections.emptyList();
        }

        return revenueCategoryList;
    }

    public List<RevenueOriginResponseDTO> getRevenueOriginList(BudgetExecutionRequestDTO requestDTO) {

        List<RevenueOriginResponseDTO> responseList = consultRevenueOrigin(requestDTO);

        if (responseList.isEmpty()) {
            return Collections.emptyList();
        }

        return responseList;
    }

    public List<RevenueTaxesResponseDTO> getRevenueTaxesList(BudgetExecutionRequestDTO requestDTO) {

        List<RevenueTaxesResponseDTO> response = consultRevenueTaxes(requestDTO);

        if (response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;

    }

    public List<RevenueICMSResponseDTO> getRevenueICMSList(BudgetExecutionRequestDTO requestDTO) {

        List<RevenueICMSResponseDTO> response = consultRevenueICMS(requestDTO);

        if (response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }

    public List<RevenueParticipationResponseDTO> getRevenueParticipationList(BudgetExecutionRequestDTO requestDTO) {

        List<RevenueParticipationResponseDTO> response = consultRevenueParticipation(requestDTO);

        if (response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }

    public List<RevenueExpenseGNDResponseDTO> getRevenueExpenseGNDList(ExecutiveBranchRequestDTO requestDTO) {

        List<RevenueExpenseGNDResponseDTO> response = consultRevenueExpenseGND(requestDTO);

        if (response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }

    public List<RevenueExpenseGNDTotalResponseDTO> getRevenueExpenseGNDTotalList(ExecutiveBranchRequestDTO requestDTO) {
        List<RevenueExpenseGNDTotalResponseDTO> response = consultRevenueExpenseGNDTotal(requestDTO);

        if (response == null || response.isEmpty()) {
            return List.of(new RevenueExpenseGNDTotalResponseDTO());
        }

        response.forEach(res -> {
            res.setCommittedPercentage(calculatePercentage(res.getCommittedValue(), res.getAuthorizedValue()));
            res.setLiquidatedPercentage(calculatePercentage(res.getLiquidatedValue(), res.getAuthorizedValue()));
        });

        return response;
    }

    private BigDecimal calculatePercentage(BigDecimal part, BigDecimal total) {
        if (total == null || total.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal partValue = (part == null) ? BigDecimal.ZERO : part;

        return partValue.multiply(new BigDecimal("100"))
                .divide(total, 2, RoundingMode.HALF_UP);
    }

    public List<RevenueCurrentTransferResponseDTO> getRevenueCurrentTransfer(BudgetExecutionRequestDTO requestDTO) {

        List<RevenueCurrentTransferResponseDTO> response = consultRevenueCurrentTransfer(requestDTO);

        if (response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }

    private List<RevenueCurrentTransferResponseDTO> consultRevenueCurrentTransfer(BudgetExecutionRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaTransferenciaCorrente");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaTransferenciaCorrente");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new RevenueCurrentTransferResponseDTO(
                                rs.get("ano").asLong(),
                                rs.get("nome_item_patrimonial").asText(),
                                new BigDecimal(rs.get("vlr_receita_liquida")
                                        .asDouble(2))
                                        .setScale(2, RoundingMode.HALF_UP)));

    }

    private List<RevenueExpenseGNDTotalResponseDTO> consultRevenueExpenseGNDTotal(ExecutiveBranchRequestDTO request) {
        HashMap<String, Object> params = paramsExecutiveBranch(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaDespesaGNDTotal");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaDespesaGNDTotal");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params,
                        rs -> new RevenueExpenseGNDTotalResponseDTO(
                                rs.get("ano").asLong(),
                                new BigDecimal(rs.get("vlr_orcado").asDouble(2))
                                        .setScale(2, RoundingMode.HALF_UP),
                                new BigDecimal(rs.get("vlr_autorizado").asDouble(2))
                                        .setScale(2, RoundingMode.HALF_UP),
                                new BigDecimal(rs.get("vlr_empenhado").asDouble(2))
                                        .setScale(2, RoundingMode.HALF_UP),
                                new BigDecimal(rs.get("vlr_liquidado").asDouble(2))
                                        .setScale(2, RoundingMode.HALF_UP),
                                new BigDecimal(rs.get("vlr_pago_com_rap")
                                        .asDouble(2))
                                        .setScale(2, RoundingMode.HALF_UP)));
    }

    private List<RevenueExpenseGNDResponseDTO> consultRevenueExpenseGND(ExecutiveBranchRequestDTO request) {
        HashMap<String, Object> params = paramsExecutiveBranch(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaDespesaGND");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaDespesaGND");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new RevenueExpenseGNDResponseDTO(
                                rs.get("ano").asLong(),
                                rs.get("mes").asLong(),
                                rs.get("nome_gnd").asText(),
                                rs.get("tipo_fonte").asLong(),
                                new BigDecimal(rs.get("vlr_orcado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                                new BigDecimal(rs.get("vlr_autorizado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                                new BigDecimal(rs.get("vlr_empenhado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                                new BigDecimal(rs.get("vlr_liquidado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                                new BigDecimal(rs.get("vlr_pago_com_rap").asDouble(2)).setScale(2,
                                        RoundingMode.HALF_UP)));
    }

    private List<RevenueParticipationResponseDTO> consultRevenueParticipation(BudgetExecutionRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaParticipacao");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaParticipacao");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new RevenueParticipationResponseDTO(
                                rs.get("ano").asLong(),
                                rs.get("nome_item_patrimonial").asText(),
                                new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2,
                                        RoundingMode.HALF_UP)));
    }

    private List<RevenueICMSResponseDTO> consultRevenueICMS(BudgetExecutionRequestDTO request) {

        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaICMS");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaICMS");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new RevenueICMSResponseDTO(
                                rs.get("ano").asLong(),
                                rs.get("nome_item_patrimonial").asText(),
                                new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2,
                                        RoundingMode.HALF_UP)));
    }

    private List<RevenueTaxesResponseDTO> consultRevenueTaxes(BudgetExecutionRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaImposto");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaImposto");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new RevenueTaxesResponseDTO(
                                rs.get("ano").asLong(),
                                rs.get("nome_item_patrimonial").asText(),
                                new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2,
                                        RoundingMode.HALF_UP)));

    }

    private List<RevenueOriginResponseDTO> consultRevenueOrigin(BudgetExecutionRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaOrigem");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaOrigem");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new RevenueOriginResponseDTO(
                                rs.get("ano").asLong(),
                                rs.get("origem").asText(),
                                new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2,
                                        RoundingMode.HALF_UP)));
    }

    private List<RevenueCategoryResponseDTO> consultRevenueCategory(BudgetExecutionRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaCategoria");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaCategoria");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new RevenueCategoryResponseDTO(
                                rs.get("ano").asLong(),
                                rs.get("categoria").asText(),
                                new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2,
                                        RoundingMode.HALF_UP)));
    }

    public List<RevenueTotalResponseDTO> consultRevenueTotal(BudgetExecutionRequestDTO request) {

        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaTotal");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaTotal");
        return apiUtils.consult(target, dataAccessId, pmoPath, params,
                rs -> new RevenueTotalResponseDTO(
                        rs.get("ano").asInt(),
                        new BigDecimal(
                                rs.get("vlr_receita_prevista").asDouble()

                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get("vlr_receita_liquida").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                        rs.get("dt_fim_extracao").asText()));
    }

    private HashMap<String, Object> params(BudgetExecutionRequestDTO request) {

        String months = Arrays.stream(request.getMonth()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String sourceTypes = Arrays.stream(request.getSourceType()).mapToObj(String::valueOf)
                .collect(Collectors.joining(","));

        HashMap<String, Object> params = new HashMap<>();

        params.put("parampAno", request.getYear());
        params.put("parampMes", months);
        params.put("parampTipoFonte", sourceTypes);

        return params;
    }

    private HashMap<String, Object> paramsExecutiveBranch(ExecutiveBranchRequestDTO request) {
        String months = Arrays.stream(request.getMonth()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String sourceTypes = Arrays.stream(request.getSourceType()).mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        String branchCodes = Arrays.stream(request.getBranchCode()).mapToObj(String::valueOf)
                .collect(Collectors.joining(","));

        HashMap<String, Object> params = new HashMap<>();

        params.put("parampAno", request.getYear());
        params.put("parampMes", months);
        params.put("parampTipoFonte", sourceTypes);
        params.put("parampCodPoder", branchCodes);

        return params;
    }

}
