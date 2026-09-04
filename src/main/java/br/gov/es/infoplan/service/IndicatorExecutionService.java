package br.gov.es.infoplan.service;

import br.gov.es.infoplan.config.pentahoBi.PentahoBiProperties;
import br.gov.es.infoplan.dto.IndicatorExecution.request.*;
import br.gov.es.infoplan.dto.IndicatorExecution.response.*;
import br.gov.es.infoplan.dto.UsuarioDto;
import br.gov.es.infoplan.enums.QuadrimestreEnum;
import br.gov.es.infoplan.utils.ApiUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.gov.es.infoplan.config.pentahoBi.PentahoBiConfigKeys.*;
import static br.gov.es.infoplan.config.pentahoBi.PentahoBiConfigParams.*;
import static br.gov.es.infoplan.config.spo.SPOPentahoConfigKey.*;

@Service
@Slf4j
public class IndicatorExecutionService {



    @Value("${papel.indicadores}")
    private String indicadores;

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

    public List<BudgetaryUnitResponseDTO> searchBudgetaryUnit(FilterBugataryUnitDTO request, UsuarioDto usuario) {
        return apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_UO_BY_YEAR,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new BudgetaryUnitResponseDTO(
                        rs.get(COD_UO).asText(),
                        rs.get(NOME_UO).asText(),
                        rs.get(SIGLA).asText()
                )
        );
    }

    public List<ActionResponseDTO> searchAction(FilterActionDTO request, UsuarioDto usuario) {
        return apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_SEARCH_ACTION,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new ActionResponseDTO(
                        rs.get(COD_ACAO).asText(),
                        rs.get(NOME_ACAO).asText()
                )
        );
    }

    public List<FullSourceResponseDTO> searchFullSource(FilterFullSourceDTO request, UsuarioDto usuario) {
        return apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_SEARCH_FULL_SOURCE,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new FullSourceResponseDTO(
                        rs.get(COD_FONTE).asText(),
                        rs.get(NOME_FONTE).asText()
                )
        );
    }

    public WithoutReversationResponseDTO getCardAvailableWithoutReversation(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        return getFirstOrNull(apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_SEM_RESERVA,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new WithoutReversationResponseDTO(
                        obterBigDecimalSeguro(rs.get(DISPONIVEL_SEM_RESERVA).asDouble(2))
                )
        ));
    }

    public CardSuccessResponseDTO getCardSuccessPlanned(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        return getFirstOrNull(apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_SUCESSO,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new CardSuccessResponseDTO(
                        obterBigDecimalSeguro(rs.get(SUCCESS_PLANNED).asDouble(2)),
                        rs.get("dt_fim_extracao").asText()
                )
        ));
    }

    public CardPOLiquidatedResponseDTO getCardPOLiquidated(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        return getFirstOrNull(apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_PO_COM_MAIOR_LIQUIDADO,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new CardPOLiquidatedResponseDTO(
                        rs.get(COD_PO).asText(),
                        rs.get(NOME_PO).asText(),
                        obterBigDecimalSeguro(rs.get(LIQUIDATED).asDouble(2))
                )
        ));
    }

    public CardComparativeResponseDTO getCardComparative(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        return getFirstOrNull(apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_COMPARATIVO,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new CardComparativeResponseDTO(
                        obterBigDecimalSeguro(rs.get(COMPARATIVE).asDouble(2))
                )
        ));
    }

    public CardFeasibilityResponseDTO getCardFeasibility(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        return getFirstOrNull(apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_EXEQUIBILIDADE,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new CardFeasibilityResponseDTO(
                        obterBigDecimalSeguro(rs.get(FEASIBILITY).asDouble(2))
                )
        ));
    }

    public CardMissionResponseDTO getCardMission(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        return getFirstOrNull(apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_MISSAO,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new CardMissionResponseDTO(
                        obterBigDecimalSeguro(rs.get(MISSION).asDouble(2))
                )
        ));
    }

    public CardIGOResponseDTO getCardIGO(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        FilterGeneralRequestDTO requestBlindado = blindarRequest(request, usuario);
        CardIGOResponseDTO dto = obterIGO(requestBlindado);

        if (dto == null) {
            return null;
        }

        QuadrimestreEnum quadrimestre = obterQuadrimestreParaCalculoNota(requestBlindado);
        String nota = QuadrimestreEnum.calcularNotaIGO(dto.Igo().doubleValue(), quadrimestre);

        return new CardIGOResponseDTO(dto.Igo(), nota);
    }

    public CardChangeResponseDTO getCardChange(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        return getFirstOrNull(apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_ALTERACAO,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new CardChangeResponseDTO(
                        obterBigDecimalSeguro(rs.get(CHANGE).asDouble(2))
                )
        ));
    }

    public DashAvailabilityUoResponseDTO getDashAvailabilityToUo(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        return getFirstOrNull(apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_DASH_AVAILABILITY_TO_UO,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new DashAvailabilityUoResponseDTO(
                        obterBigDecimalSeguro(rs.get(DISPONIVEL).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(DISPONIVEL_SEM_RESERVA).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(DISPONIVEL_COM_RESERVA).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(EMPENHADO_A_LIQUIDAR).asDouble(2)),
                        rs.get(ANO).asLong()
                )
        ));
    }

    public List<DashSuccessPlannedResponseDTO> getDashSuccessPlanned(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        return apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_DASH_SUCCESS_OF_PLANNED,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new DashSuccessPlannedResponseDTO(
                        rs.get(ANO).asLong(),
                        rs.get(COD_GND).asText(),
                        rs.get(NAME_GND).asText(),
                        obterBigDecimalSeguro(rs.get(BUDGETED).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(AUTHORIZED).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(COMMITTED).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(LIQUIDATED).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(PAID).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(COMMITTED_BAR_AUTHORIZED).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(LIQUIDATED_BAR_AUTHORIZED).asDouble(2))
                )
        );
    }

    public List<DashPlannedBudgetaryResponseDTO> getDashPlannedBudgetary(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        return apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_DASH_PLANNED_BUDGETARY,
                pmoPath,
                params(blindarRequest(request, usuario)),
                rs -> new DashPlannedBudgetaryResponseDTO(
                        rs.get(ANO).asLong(),
                        rs.get(COD_PO).asText(),
                        rs.get(NOME_PO).asText(),
                        obterBigDecimalSeguro(rs.get(BUDGETED).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(AUTHORIZED).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(COMMITTED).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(LIQUIDATED).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(COMMITTED_BAR_AUTHORIZED).asDouble(2)),
                        obterBigDecimalSeguro(rs.get(LIQUIDATED_BAR_AUTHORIZED).asDouble(2))
                )
        );
    }

    public List<POResponseDTO> searchPO(FilterPODTO request, UsuarioDto usuarioDto) {
        return apiUtils.executePentahoQuery(
                PAINEL_INDICADOR_EXECUCAO,
                pmoPath,
                params(blindarRequest(request, usuarioDto)),
                rs -> new POResponseDTO(
                        rs.get(COD_PO).asText(),
                        rs.get(NOME_PO).asText()
                )
        );
    }

    private List<String> obterTodosOsPapeisDeAcessoTotal() {
        return Stream.of(
                        indicadores
                )
                .filter(Objects::nonNull)
                .flatMap(papel -> Arrays.stream(papel.split(",")))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private String determinarOrgaoDefinitivo(UsuarioDto usuario) {
        if (usuario == null) {
            return "";
        }

        boolean hasAcessoTotal = usuario.role() != null && usuario.role().stream()
                .anyMatch(role -> {
                    String r = role.toUpperCase();
                    return r.contains(indicadores);
                });

        if (hasAcessoTotal) {
            return "-1";
        }

        String sigla = usuario.sigla() != null ? usuario.sigla().trim() : "";

        return sigla;
    }


    private FilterBugataryUnitDTO blindarRequest(FilterBugataryUnitDTO request, UsuarioDto usuario) {
        return new FilterBugataryUnitDTO(request.year(), determinarOrgaoDefinitivo(usuario));
    }

    private FilterActionDTO blindarRequest(FilterActionDTO request, UsuarioDto usuario) {
        return new FilterActionDTO(request.year(), request.codUo(), determinarOrgaoDefinitivo(usuario));
    }

    private FilterPODTO blindarRequest(FilterPODTO request, UsuarioDto usuario) {
        return new FilterPODTO(request.year(), request.codUo(), request.codAction(), determinarOrgaoDefinitivo(usuario));
    }

    private FilterFullSourceDTO blindarRequest(FilterFullSourceDTO request, UsuarioDto usuario) {
        return new FilterFullSourceDTO(request.year(), request.codUo(), request.codAction(), determinarOrgaoDefinitivo(usuario));
    }

    private FilterGeneralRequestDTO blindarRequest(FilterGeneralRequestDTO request, UsuarioDto usuario) {
        return new FilterGeneralRequestDTO(
                request.year(), request.codUo(), request.codAction(), request.month(),
                request.typeSource(), request.codGnd(), request.codSource(), request.codAmendment(),
                determinarOrgaoDefinitivo(usuario), request.codPo()
        );
    }


    private CardIGOResponseDTO obterIGO(FilterGeneralRequestDTO requestBlindado) {
        return getFirstOrNull(apiUtils.executePentahoQuery(
                INDICATOR_EXECUTION_CARD_IGO,
                pmoPath,
                params(requestBlindado),
                rs -> new CardIGOResponseDTO(
                        obterBigDecimalSeguro(rs.get(IGO).asDouble(2)),
                        null
                )
        ));
    }

    private <T> T getFirstOrNull(List<T> list) {
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    private BigDecimal obterBigDecimalSeguro(Double valor) {
        return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP);
    }

    private QuadrimestreEnum obterQuadrimestreParaCalculoNota(FilterGeneralRequestDTO request) {
        if (!"-1".equals(request.month())) {
            return obterQuadrimestrePorMeses(request.month());
        }
        return isAnoEncerrado(request.year()) ? QuadrimestreEnum.TERCEIRO : obterQuadrimestreAtual();
    }

    private QuadrimestreEnum obterQuadrimestreAtual() {
        return QuadrimestreEnum.obterQuadrimestre(new int[]{LocalDate.now().getMonthValue()});
    }

    private QuadrimestreEnum obterQuadrimestrePorMeses(String month) {
        int[] meses = Arrays.stream(month.split(",")).mapToInt(Integer::parseInt).toArray();
        return QuadrimestreEnum.obterQuadrimestre(meses);
    }

    private boolean isAnoEncerrado(String year) {
        if (year == null || year.isEmpty()) return false;

        int maiorAnoInformado = Arrays.stream(year.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(LocalDate.now().getYear());

        return maiorAnoInformado < LocalDate.now().getYear();
    }

    private Map<String, Object> params(FilterBugataryUnitDTO request) {
        Map<String, Object> params = new HashMap<>();
        if (request.year() != null && !request.year().isEmpty()) {
            params.put(PARAMP_ANO_M, request.year());
        }
        params.put(PARAMP_ORGAO, request.orgao());
        return params;
    }

    private Map<String, Object> params(FilterPODTO request) {
        Map<String, Object> params = new HashMap<>();

        if (request.year() != null && !request.year().isEmpty()) {
            params.put(PARAMP_ANO_M, request.year());
        }
        params.put(PARAMP_ACAO, request.codAction());
        params.put(PARAMP_ORGAO, request.orgao());
        params.put(PARAMP_COD_UO, request.codUo());
        return params;
    }

    private Map<String, Object> params(FilterActionDTO request) {
        Map<String, Object> params = new HashMap<>();
        if (request.year() != null && !request.year().isEmpty()) {
            params.put(PARAMP_ANO_M, request.year());
        }
        params.put(PARAMP_COD_UO, request.codUo());
        params.put(PARAMP_ORGAO, request.orgao());
        return params;
    }

    private Map<String, Object> params(FilterFullSourceDTO request) {
        Map<String, Object> params = new HashMap<>();
        if (request.year() != null && !request.year().isEmpty()) {
            params.put(PARAMP_ANO_M, request.year());
        }
        params.put(PARAMP_COD_UO, request.codUo());
        params.put(PARAMP_COD_ACAO, request.codAction());
        params.put(PARAMP_ORGAO, request.orgao());
        return params;
    }

    private Map<String, Object> params(FilterGeneralRequestDTO request) {
        Map<String, Object> params = new HashMap<>();
        if (request.year() != null && !request.year().isEmpty()) {
            params.put(PARAMP_ANO_M, request.year());
        }
        params.put(PARAMP_COD_UO, request.codUo());
        params.put(PARAMP_COD_ACAO, request.codAction());
        params.put(PARAMP_COD_EMENDA, request.codAmendment());
        params.put(PARAMP_COD_FONTE, request.codSource());
        params.put(PARAMP_TIPO_FONTE, request.typeSource());
        params.put(PARAMP_COD_GND, request.codGnd());
        params.put(PARAMP_MES, request.month());
        params.put(PARAMP_ORGAO, request.orgao());
        params.put(PARAMP_COD_PO, request.codPo());
        return params;
    }
}