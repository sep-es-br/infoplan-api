package br.gov.es.infoplan.service;

import br.gov.es.infoplan.config.pentahoBi.PentahoBiProperties;
import br.gov.es.infoplan.dto.execucaoOrcamentariaDTO.*;
import br.gov.es.infoplan.dto.planejamentoOrcamentario.SPOTotalAutorizadoUoDTO;
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
public class ExecucaoOrcamentariaService {

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private PentahoBiProperties properties;

    private String pmoPath;

    @PostConstruct
    public void init() {
        this.pmoPath = properties.getExecucaoOrcamentaria().getPath();
        log.info("PMO Path inicializado: {}", pmoPath);
    }


    public ReceitaTotalResponseDTO getReceitaTotal(ExecucaoOrcamentariaRequestDTO painelOrcamento) {
        List<ReceitaTotalResponseDTO> receitas = consultarReceitaTotal(painelOrcamento);

        if (receitas.isEmpty()) {
            return new ReceitaTotalResponseDTO();
        }

        BigDecimal porcentagemLiquidadaPrevista = calcDivisorPrevistaRealizada(receitas);

        ReceitaTotalResponseDTO principal = receitas.get(0);
        principal.setPorcentagem(porcentagemLiquidadaPrevista);

        return principal;
    }


    private BigDecimal calcDivisorPrevistaRealizada(List<ReceitaTotalResponseDTO> receita) {
        if (receita == null || receita.isEmpty()) {
            return BigDecimal.ZERO;
        }

        ReceitaTotalResponseDTO dados = receita.get(0);
        BigDecimal liquida = dados.getVlr_receita_liquida();
        BigDecimal prevista = dados.getVlr_receita_prevista();

        if (prevista == null || prevista.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        if (liquida == null) liquida = BigDecimal.ZERO;

        return liquida.multiply(new BigDecimal("100"))
                .divide(prevista, 2, RoundingMode.HALF_UP);
    }

//    public ReceitaTotalResponseDTO getReceitaTotal(ExecucaoOrcamentariaRequestDTO painelOrcamento) {
//        List<ReceitaTotalResponseDTO> listDTO = consultarReceitaTotal(painelOrcamento);
//
//        BigDecimal porcentagemLiquidadaPrevista = calcDivisorPrevistaRealizada(listDTO);
//
//        listDTO.stream().map(res -> {
//            res.setPorcentagem(porcentagemLiquidadaPrevista);
//            return res;
//        }).collect(Collectors.toList());
//
//        return listDTO.isEmpty() ? new ReceitaTotalResponseDTO() : listDTO.get(0);
//    }


//    private BigDecimal calcDivisorPrevistaRealizada(List<ReceitaTotalResponseDTO> receita) {
//        var response = receita.get(0);
//
//        BigDecimal receitaLiquida = response.getVlr_receita_liquida();
//        BigDecimal receitaPrevista = response.getVlr_receita_prevista();
//        BigDecimal porcetagem = new BigDecimal("100");
//
//        BigDecimal result = receitaLiquida.divide(receitaPrevista, 2, RoundingMode.HALF_UP);
//        BigDecimal multiply = result.multiply(porcetagem);
//
//        return multiply;
//    }

    public List<ReceitaCategoriaResponseDTO> getReceitaCategoria(Long ano, int[] mes, int[] tipoFonte) {
        ExecucaoOrcamentariaRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);
        List<ReceitaCategoriaResponseDTO> receitaCategoriaList = consultarReceitaCategoria(request);

        if (receitaCategoriaList.isEmpty()) {
            return Collections.emptyList();
        }

        return receitaCategoriaList;
    }

    public List<ReceitaOrigemResponseDTO> getReceitaOrigemList(Long ano, int[] mes, int[] tipoFonte) {
        ExecucaoOrcamentariaRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaOrigemResponseDTO> responseList = consultarRceitaOrigem(request);

        if(responseList.isEmpty()) {
            return Collections.emptyList();
        }

        return responseList;
    }


    public List<ReceitaImpostosResponseDTO> getReceitaImpostoList(Long ano, int[] mes, int[] tipoFonte) {
        ExecucaoOrcamentariaRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaImpostosResponseDTO> response = consultarReceitaImposto(request);

        if(response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;

    }


    public List<ReceitaICMSResponseDTO> getReceitaICMSList(Long ano, int[] mes, int[] tipoFonte) {
        ExecucaoOrcamentariaRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaICMSResponseDTO> response = consultarReceitaICMS(request);

        if(response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }

    public List<ReceitaParticipacaoResponseDTO> getReceitaParticipacaoList(Long ano, int[] mes, int[] tipoFonte) {
        ExecucaoOrcamentariaRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaParticipacaoResponseDTO> response = consultarReceitaParticipacao(request);

        if(response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }


    public List<ReceitaDespesaGNDResponseDTO> getReceitaDespesaGNDList
            (Long ano, int[] mes, int[] tipoFonte) {

        ExecucaoOrcamentariaRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaDespesaGNDResponseDTO> response = consultarReceitaDespesaGND(request);

        if(response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }


//    public List<ReceitaDespesaGNDTotalResponseDTO> getReceitaDespesaGNDTotalList
//            (Long ano, int[] mes, int[] tipoFonte) {
//        ExecucaoOrcamentariaRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);
//        List<ReceitaDespesaGNDTotalResponseDTO> response = consultarReceitaDespesaGNDTotal(request);
//
//
//
//        if (response == null || response.isEmpty()) {
//            return new ArrayList<>(Arrays.asList(new ReceitaDespesaGNDTotalResponseDTO()));
//        }
//
//        response.stream().map(res -> {
//
//            BigDecimal porcentagemEmpenhada = calcPorcetagemEmpenhada(res);
//            BigDecimal porcentagemLiquidada = calcPorcetagemLiquidada(res);
//
//            res.setPorcentagemEmpenhada(porcentagemEmpenhada);
//            res.setPorcentagemLiquidada(porcentagemLiquidada);
//            return res;
//        }).collect(Collectors.toList());
//
//        if(response.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        return response;
//    }

    public List<ReceitaDespesaGNDTotalResponseDTO> getReceitaDespesaGNDTotalList(Long ano, int[] mes, int[] tipoFonte) {
        ExecucaoOrcamentariaRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);
        List<ReceitaDespesaGNDTotalResponseDTO> response = consultarReceitaDespesaGNDTotal(request);

        if (response == null || response.isEmpty()) {
            return List.of(new ReceitaDespesaGNDTotalResponseDTO());
        }

        response.forEach(res -> {
            res.setPorcentagemEmpenhada(calcularPorcentagem(res.getEmpenhado(), res.getAutorizado()));
            res.setPorcentagemLiquidada(calcularPorcentagem(res.getLiquidado(), res.getAutorizado()));
        });

        return response;
    }


    private BigDecimal calcularPorcentagem(BigDecimal parte, BigDecimal total) {
        if (total == null || total.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal valorParte = (parte == null) ? BigDecimal.ZERO : parte;

        return valorParte.multiply(new BigDecimal("100"))
                .divide(total, 2, RoundingMode.HALF_UP);
    }

//    private BigDecimal calcPorcetagemEmpenhada(ReceitaDespesaGNDTotalResponseDTO receitaDespesasGndTotal) {
//        BigDecimal receitaEmpenhada = receitaDespesasGndTotal.getEmpenhado();
//        BigDecimal receitaAutorizada = receitaDespesasGndTotal.getAutorizado();
//        BigDecimal divisor = new BigDecimal("100");
//
//        BigDecimal division = receitaEmpenhada.divide(receitaAutorizada, 2, RoundingMode.HALF_UP);
//        BigDecimal porcentagem = division.multiply(divisor);
//
//        return porcentagem;
//    }
//
//    private BigDecimal calcPorcetagemLiquidada(ReceitaDespesaGNDTotalResponseDTO receitaDespesasGndTotal) {
//        BigDecimal receitaLiquidadda = receitaDespesasGndTotal.getLiquidado();
//        BigDecimal receitaAutorizada = receitaDespesasGndTotal.getAutorizado();
//        BigDecimal divisor = new BigDecimal("100");
//
//        BigDecimal division = receitaLiquidadda.divide(receitaAutorizada, 2, RoundingMode.HALF_UP);
//        BigDecimal porcentagem = division.multiply(divisor);
//
//        return porcentagem;
//    }


    public List<ReceitaTransferenciaCorrenteResponseDTO> getReceitaTransferenciaCorrente
            (Long ano, int[] mes, int[] tipoFonte) {
        ExecucaoOrcamentariaRequestDTO requestDTO = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaTransferenciaCorrenteResponseDTO> response = consultarReceitaTransferenciaCorrente(requestDTO);

        if(response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }

    private List<ReceitaTransferenciaCorrenteResponseDTO> consultarReceitaTransferenciaCorrente
            (ExecucaoOrcamentariaRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaTransferenciaCorrente");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaTransferenciaCorrente");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs ->
                                new ReceitaTransferenciaCorrenteResponseDTO(
                                        rs.get("ano").asLong(),
                                        rs.get("nome_item_patrimonial").asText(),
                                        new BigDecimal(rs.get("vlr_receita_liquida")
                                                .asDouble(2))
                                                .setScale(2, RoundingMode.HALF_UP)
                ));

    }

    private List<ReceitaDespesaGNDTotalResponseDTO> consultarReceitaDespesaGNDTotal(ExecucaoOrcamentariaRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaDespesaGNDTotal");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaDespesaGNDTotal");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params,
                        rs -> new ReceitaDespesaGNDTotalResponseDTO(
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
                                                                .setScale(2, RoundingMode.HALF_UP)
                ));
    }

    private List<ReceitaDespesaGNDResponseDTO> consultarReceitaDespesaGND(ExecucaoOrcamentariaRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaDespesaGND");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaDespesaGND");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new ReceitaDespesaGNDResponseDTO(
                        rs.get("ano").asLong(),
                        rs.get("mes").asLong(),
                        rs.get("nome_gnd").asText(),
                        rs.get("tipo_fonte").asLong(),
                        new BigDecimal(rs.get("vlr_orcado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_autorizado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_empenhado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_liquidado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_pago_com_rap").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
                ));
    }

    private List<ReceitaParticipacaoResponseDTO> consultarReceitaParticipacao(ExecucaoOrcamentariaRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaParticipacao");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaParticipacao");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new ReceitaParticipacaoResponseDTO(
                        rs.get("ano").asLong(),
                        rs.get("nome_item_patrimonial").asText(),
                        new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
                ));
    }

    private List<ReceitaICMSResponseDTO> consultarReceitaICMS(ExecucaoOrcamentariaRequestDTO request) {

        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaICMS");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaICMS");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new ReceitaICMSResponseDTO(
                        rs.get("ano").asLong(),
                        rs.get("nome_item_patrimonial").asText(),
                        new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
                ));
    }

    private List<ReceitaImpostosResponseDTO> consultarReceitaImposto(ExecucaoOrcamentariaRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaImposto");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaImposto");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new ReceitaImpostosResponseDTO(
                        rs.get("ano").asLong(),
                        rs.get("nome_item_patrimonial").asText(),
                        new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
                ));

    }


    private List<ReceitaOrigemResponseDTO> consultarRceitaOrigem(ExecucaoOrcamentariaRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaOrigem");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaOrigem");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new ReceitaOrigemResponseDTO(
                        rs.get("ano").asLong(),
                        rs.get("origem").asText(),
                        new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
                ));
    }


    private List<ReceitaCategoriaResponseDTO> consultarReceitaCategoria(ExecucaoOrcamentariaRequestDTO request) {
        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaCategoria");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaCategoria");

        return apiUtils
                .consult(
                        target,
                        dataAccessId,
                        pmoPath,
                        params, rs -> new ReceitaCategoriaResponseDTO(
                        rs.get("ano").asLong(),
                        rs.get("categoria").asText(),
                        new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
                ));
    }


    public List<ReceitaTotalResponseDTO> consultarReceitaTotal(ExecucaoOrcamentariaRequestDTO request) {

        HashMap<String, Object> params = params(request);
        String target = properties.getTargetOrThrow("execucaoOrcamentariaDashReceitaTotal");
        String dataAccessId = properties.getDataAccessIdOrThrow("execucaoOrcamentariaDashReceitaTotal");
        return apiUtils.consult(target, dataAccessId, pmoPath, params,
                rs -> new ReceitaTotalResponseDTO(
                        rs.get("ano").asInt(),
                        new BigDecimal(
                                rs.get("vlr_receita_prevista").asDouble()

                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get("vlr_receita_liquida").asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP),
                        rs.get("dt_fim_extracao").asText()
                ));
    }

    private HashMap<String, Object> params(ExecucaoOrcamentariaRequestDTO request) {

        String meses = Arrays.stream(request.getMes()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String tipoFontes = Arrays.stream(request.getTipoFonte()).mapToObj(String::valueOf).collect(Collectors.joining(","));

        HashMap<String, Object> params = new HashMap<>();

        params.put("parampAno", request.getAno());
        params.put("parampMes", meses);
        params.put("parampTipoFonte",tipoFontes);

        return params;
    }

    private ExecucaoOrcamentariaRequestDTO convertPaineOrcamentoDto(Long ano, int[] mes, int[] tipoFonte) {
        ExecucaoOrcamentariaRequestDTO dto = new ExecucaoOrcamentariaRequestDTO(ano, mes, tipoFonte);
        return dto;
    }

}
