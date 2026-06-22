package br.gov.es.infoplan.service;

import br.gov.es.infoplan.config.pentahoBi.PentahoBiProperties;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterActionDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterBugataryUnitDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterFullSourceDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterGeneralRequestDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.response.*;
import br.gov.es.infoplan.enums.QuadrimestreEnum;
import br.gov.es.infoplan.utils.ApiUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

import static br.gov.es.infoplan.config.pentahoBi.PentahoBiConfigKeys.*;
import static br.gov.es.infoplan.config.pentahoBi.PentahoBiConfigParams.*;
import static br.gov.es.infoplan.config.spo.SPOPentahoConfigKey.*;

@Service
@Slf4j
public class IndicatorExecutionService {

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private PentahoBiProperties properties;

    private String pmoPath;


    @PostConstruct
    public void init() {
        this.pmoPath = properties.getIndicatorExecution().getPath();
        log.info("PMO Path initialized:: {} ", pmoPath);
    }

    public List<BudgetaryUnitResponseDTO> searchBudgetaryUnit(FilterBugataryUnitDTO request) {
        return apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_UO_BY_YEAR,
                pmoPath,
                params(request),
                rs -> new BudgetaryUnitResponseDTO(
                        rs.get(COD_UO).asText(),
                        rs.get(NOME_UO).asText(),
                        rs.get(SIGLA).asText()
                )
        );
    }


    public List<ActionResponseDTO> searchAction(FilterActionDTO request) {
        return apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_SEARCH_ACTION,
                pmoPath,
                params(request),
                rs -> new ActionResponseDTO(
                        rs.get(COD_ACAO).asText(),
                        rs.get(NOME_ACAO).asText()
                )
        );
    }


    public List<FullSourceResponseDTO> searchFullSource(FilterFullSourceDTO request) {
        return apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_SEARCH_FULL_SOURCE,
                pmoPath,
                params(request),
                rs -> new FullSourceResponseDTO(
                        rs.get(COD_FONTE).asText(),
                        rs.get(NOME_FONTE).asText()
                )
        );
    }


    public WithoutReversationResponseDTO getCardAvailableWithoutReversation(FilterGeneralRequestDTO request) {
        List<WithoutReversationResponseDTO> list = apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_SEM_RESERVA,
                pmoPath,
                params(request),
                rs -> new WithoutReversationResponseDTO(
                        new BigDecimal(
                                rs.get(DISPONIVEL_SEM_RESERVA)
                                        .asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP)
                )
        );

        if (list.isEmpty()) {
            return null;
        }

        WithoutReversationResponseDTO dto = list.get(0);
        return dto;
    }


    public CardSuccessResponseDTO getCardSuccessPlanned(FilterGeneralRequestDTO request) {
        List<CardSuccessResponseDTO> list = apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_SUCESSO,
                pmoPath,
                params(request),
                rs -> new CardSuccessResponseDTO(
                        new BigDecimal(
                                rs.get(SUCCESS_PLANNED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        rs.get("dt_fim_extracao").asText()
                )
        );

        if (list.isEmpty()) {
            return null;
        }

        CardSuccessResponseDTO dto = list.get(0);

        return dto;
    }


    public CardPOLiquidatedResponseDTO getCardPOLiquidated(FilterGeneralRequestDTO request) {
        List<CardPOLiquidatedResponseDTO> list = apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_PO_COM_MAIOR_LIQUIDADO,
                pmoPath,
                params(request),
                rs -> new CardPOLiquidatedResponseDTO(
                        rs.get(COD_PO).asText(),
                        rs.get(NOME_PO).asText(),
                        new BigDecimal(
                                rs.get(LIQUIDATED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP)
                )
        );

        if (list.isEmpty()) {
            return null;
        }

        CardPOLiquidatedResponseDTO dto = list.get(0);
        return dto;
    }

    public CardComparativeResponseDTO getCardComparative(FilterGeneralRequestDTO request) {
        List<CardComparativeResponseDTO> list = apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_COMPARATIVO,
                pmoPath,
                params(request),
                rs -> new CardComparativeResponseDTO(
                        new BigDecimal(
                                rs.get(COMPARATIVE).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP)
                )
        );

        if (list.isEmpty()) {
            return null;
        }

        CardComparativeResponseDTO dto = list.get(0);
        return dto;
    }


    public CardFeasibilityResponseDTO getCardFeasibility(FilterGeneralRequestDTO request) {
        List<CardFeasibilityResponseDTO> list = apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_EXEQUIBILIDADE,
                pmoPath,
                params(request),
                rs -> new CardFeasibilityResponseDTO(
                        new BigDecimal(
                                rs.get(FEASIBILITY).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP)
                )
        );

        if (list.isEmpty()) {
            return null;
        }

        CardFeasibilityResponseDTO dto = list.get(0);

        return dto;
    }


    public CardMissionResponseDTO getCardMission(FilterGeneralRequestDTO request) {
        List<CardMissionResponseDTO> list = apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_MISSAO,
                pmoPath,
                params(request),
                rs -> new CardMissionResponseDTO(
                        new BigDecimal(
                                rs.get(MISSION).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP)
                )
        );

        if (list.isEmpty()) {
            return null;
        }

        CardMissionResponseDTO dto = list.get(0);

        return dto;
    }

    public CardIGOResponseDTO getCardIGO(FilterGeneralRequestDTO request) {

        CardIGOResponseDTO dto = obterIGO(request);

        if (dto == null) {
            return null;
        }

        QuadrimestreEnum quadrimestre = obterQuadrimestreParaCalculoNota(request);

        String nota = QuadrimestreEnum.calcularNotaIGO(
                dto.Igo().doubleValue(),
                quadrimestre
        );

        return new CardIGOResponseDTO(
                dto.Igo(),
                nota
        );
    }

    private CardIGOResponseDTO obterIGO(FilterGeneralRequestDTO request) {

        List<CardIGOResponseDTO> result = apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_IGO,
                pmoPath,
                params(request),
                rs -> new CardIGOResponseDTO(
                        BigDecimal.valueOf(rs.get(IGO).asDouble(2))
                                .setScale(2, RoundingMode.HALF_UP),
                        null
                )
        );

        return result.isEmpty() ? null : result.get(0);
    }

    private QuadrimestreEnum obterQuadrimestreParaCalculoNota(FilterGeneralRequestDTO request) {

        if (!"-1".equals(request.month())) {
            return obterQuadrimestrePorMeses(request.month());
        }

        return isAnoEncerrado(request.year())
                ? QuadrimestreEnum.TERCEIRO
                : obterQuadrimestreAtual();
    }

    private QuadrimestreEnum obterQuadrimestreAtual() {
        return QuadrimestreEnum.obterQuadrimestre(
                new int[]{LocalDate.now().getMonthValue()}
        );
    }

    private QuadrimestreEnum obterQuadrimestrePorMeses(String month) {

        int[] meses = Arrays.stream(month.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        return QuadrimestreEnum.obterQuadrimestre(meses);
    }

    private boolean isAnoEncerrado(String year) {
        if (year == null || year.isEmpty()) {
            return false;
        }

        int maiorAnoInformado = Arrays.stream(year.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(LocalDate.now().getYear());

        return maiorAnoInformado < LocalDate.now().getYear();
    }

    public CardChangeResponseDTO getCardChange(FilterGeneralRequestDTO request) {
        return apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_ALTERACAO,
                pmoPath,
                params(request),
                rs -> new CardChangeResponseDTO(
                        new BigDecimal(
                                rs.get(CHANGE).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP)
                )
        ).get(0);
    }


    public DashAvailabilityUoResponseDTO getDashAvailabilityToUo(FilterGeneralRequestDTO request) {
        List<DashAvailabilityUoResponseDTO> list = apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_DASH_AVAILABILITY_TO_UO,
                pmoPath,
                params(request),
                rs -> new DashAvailabilityUoResponseDTO(
                        new BigDecimal(
                                rs.get(DISPONIVEL).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(DISPONIVEL_SEM_RESERVA).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(DISPONIVEL_COM_RESERVA).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(EMPENHADO_A_LIQUIDAR).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        rs.get(ANO).asLong()
                )
        );

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }


    public List<DashSuccessPlannedResponseDTO> getDashSuccessPlanned(FilterGeneralRequestDTO request) {
        return apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_DASH_SUCCESS_OF_PLANNED,
                pmoPath,
                params(request),
                rs -> new DashSuccessPlannedResponseDTO(
                        rs.get(ANO).asLong(),
                        rs.get(COD_GND).asText(),
                        rs.get(NAME_GND).asText(),
                        new BigDecimal(
                                rs.get(BUDGETED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(AUTHORIZED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(COMMITTED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(LIQUIDATED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(PAID).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(COMMITTED_BAR_AUTHORIZED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(LIQUIDATED_BAR_AUTHORIZED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP)
                )
        );
    }

    public List<DashPlannedBudgetaryResponseDTO> getDashPlannedBudgetary(FilterGeneralRequestDTO request) {
        return apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_DASH_PLANNED_BUDGETARY,
                pmoPath,
                params(request),
                rs -> new DashPlannedBudgetaryResponseDTO(
                        rs.get(ANO).asLong(),
                        rs.get(COD_PO).asText(),
                        rs.get(NOME_PO).asText(),
                        new BigDecimal(
                                rs.get(BUDGETED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(AUTHORIZED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(COMMITTED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(LIQUIDATED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(COMMITTED_BAR_AUTHORIZED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get(LIQUIDATED_BAR_AUTHORIZED).asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP)
                )
        );
    }

    private Map<String, Object> params(FilterBugataryUnitDTO request) {
        Map<String, Object> params = new HashMap<>();

        String year = request.year();

        if (year != null && !year.isEmpty()) {
            params.put(PARAMP_ANO_M, year);
        }

        return params;
    }

    private Map<String, Object> params(FilterActionDTO request) {
        Map<String, Object> params = new HashMap<>();

        String year = request.year();
        String uo = request.codUo();

        if (year != null && !year.isEmpty()) {
            params.put(PARAMP_ANO_M, year);
        }

        params.put(PARAMP_COD_UO, uo);

        return params;
    }

    private Map<String, Object> params(FilterFullSourceDTO request) {
        Map<String, Object> params = new HashMap<>();

        String year = request.year();
        String uo = request.codUo();
        String action = request.codAction();

        if (year != null && !year.isEmpty()) {
            params.put(PARAMP_ANO_M, year);
        }

        params.put(PARAMP_COD_UO, uo);
        params.put(PARAMP_COD_ACAO, action);

        return params;
    }

    private Map<String, Object> params(FilterGeneralRequestDTO request) {
        Map<String, Object> params = new HashMap<>();

        String year = request.year();
        String gnd = request.codGnd();
        String uo = request.codUo();
        String month = request.month();
        String typeSource = request.typeSource();
        String codSource = request.codSource();
        String codAmendment = request.codAmendment();
        String action = request.codAction();

        if (year != null && !year.isEmpty()) {
            params.put(PARAMP_ANO_M, year);
        }

        params.put(PARAMP_COD_UO, uo);
        params.put(PARAMP_COD_ACAO, action);
        params.put(PARAMP_ANO_M, year);
        params.put(PARAMP_COD_EMENDA, codAmendment);
        params.put(PARAMP_COD_FONTE, codSource);
        params.put(PARAMP_TIPO_FONTE, typeSource);
        params.put(PARAMP_COD_GND, gnd);
        params.put(PARAMP_MES, month);
        return params;
    }
}
