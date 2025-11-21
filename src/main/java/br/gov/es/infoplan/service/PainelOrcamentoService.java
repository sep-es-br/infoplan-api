package br.gov.es.infoplan.service;

import br.gov.es.infoplan.dto.painelOrcamentoDTO.*;
import br.gov.es.infoplan.utils.ApiUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PainelOrcamentoService {

    private final Logger LOGGER = LogManager.getLogger(PentahoBIService.class);

    @Autowired
    private ApiUtils apiUtils;


    @Value("${pentahoBI.painelOrcamento.path}")
    private String pmoPath;

    @Value("${pentahoBI.pmo.target.painelOrcamentoDashReceitaTotal}")
    private String targetPainelOrcamentoRecitaTotal;

    @Value("${pentahoBI.pmo.dataAccessId.painelOrcamentoDashReceitaTotal}")
    private String dataAccessIdPainelOrcamentoRecitaTotal;

    @Value("${pentahoBI.pmo.target.painelOrcamentoDashReceitaCategoria}")
    private String targetPainelOrcamentoRecitaCategoria;

    @Value("${pentahoBI.pmo.dataAccessId.painelOrcamentoDashReceitaCategoria}")
    private String dataAccessIdPainelOrcamentoCategoria;

    @Value("${pentahoBI.pmo.target.painelOrcamentoDashReceitaOrigem}")
    private String targetpainelOrcamentoDashReceitaOrigem;

    @Value("${pentahoBI.pmo.dataAccessId.painelOrcamentoDashReceitaOrigem}")
    private String dataAccessIdpainelOrcamentoDashReceitaOrigem;

    @Value("${pentahoBI.pmo.dataAccessId.painelOrcamentoDashReceitaImposto}")
    private String dataAccessIdpainelOrcamentoDashReceitaImposto;

    @Value("${pentahoBI.pmo.target.painelOrcamentoDashReceitaImposto}")
    private String targetpainelOrcamentoDashReceitaImposto;

    @Value("${pentahoBI.pmo.dataAccessId.painelOrcamentoDashReceitaICMS}")
    private String dataAccessIdpainelOrcamentoDashReceitaICMS;

    @Value("${pentahoBI.pmo.target.painelOrcamentoDashReceitaICMS}")
    private String targetpainelOrcamentoDashReceitaICMS;

    @Value("${pentahoBI.pmo.dataAccessId.painelOrcamentoDashReceitaParticipacao}")
    private String dataAccessIdpainelOrcamentoDashReceitaParticipacao;

    @Value("${pentahoBI.pmo.target.painelOrcamentoDashReceitaParticipacao}")
    private String targetpainelOrcamentoDashReceitaParticipacao;

    @Value("${pentahoBI.pmo.dataAccessId.painelOrcamentoDashReceitaDespesaGNDTotal}")
    private String dataAccessIdpainelOrcamentoDashReceitaDespesaGNDTotal;

    @Value("${pentahoBI.pmo.target.painelOrcamentoDashReceitaDespesaGNDTotal}")
    private String targetpainelOrcamentoDashReceitaDespesaGNDTotal;

    @Value("${pentahoBI.pmo.dataAccessId.painelOrcamentoDashReceitaDespesaGND}")
    private String dataAccessIdpainelOrcamentoDashReceitaDespesaGND;

    @Value("${pentahoBI.pmo.target.painelOrcamentoDashReceitaDespesaGND}")
    private String targetpainelOrcamentoDashReceitaDespesaGND;

    @Value("${pentahoBI.pmo.dataAccessId.painelOrcamentoDashReceitaTransferenciaCorrente}")
    private String dataAccessIdpainelOrcamentoDashReceitaTransferenciaCorrente;

    @Value("${pentahoBI.pmo.target.painelOrcamentoDashReceitaTransferenciaCorrente}")
    private String targetpainelOrcamentoDashReceitaTransferenciaCorrente;


    public ReceitaTotalResponseDTO getReceitaTotal(PainelOrcamentoRequestDTO painelOrcamento) {
        List<ReceitaTotalResponseDTO> listDTO = consultarReceitaTotal(painelOrcamento);

        BigDecimal porcentagemLiquidadaPrevista = calcDivisorPrevistaRealizada(listDTO);

        listDTO.stream().map(res -> {
            res.setPorcentagem(porcentagemLiquidadaPrevista);
            return res;
        }).collect(Collectors.toList());

        return listDTO.isEmpty() ? new ReceitaTotalResponseDTO() : listDTO.get(0);
    }


    private BigDecimal calcDivisorPrevistaRealizada(List<ReceitaTotalResponseDTO> receita) {
        var response = receita.get(0);

        BigDecimal receitaLiquida = response.getVlr_receita_liquida();
        BigDecimal receitaPrevista = response.getVlr_receita_prevista();
        BigDecimal porcetagem = new BigDecimal("100");

//        System.out.println("receitaLiquida : " + receitaLiquida);
//        System.out.println("receitaPrevista : " + receitaPrevista);
        BigDecimal result = receitaLiquida.divide(receitaPrevista, 2, RoundingMode.HALF_UP);
        BigDecimal multiply = result.multiply(porcetagem);

//        System.out.println("porcentagem realizada: " + multiply);
        return multiply;
    }

    public List<ReceitaCategoriaResponseDTO> getReceitaCategoria(Long ano, int[] mes, int[] tipoFonte) {
        PainelOrcamentoRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);
        List<ReceitaCategoriaResponseDTO> receitaCategoriaList = consultarReceitaCategoria(request);

        if (receitaCategoriaList.isEmpty()) {
            return Collections.emptyList();
        }

        return receitaCategoriaList;
    }

    public List<ReceitaOrigemResponseDTO> getReceitaOrigemList(Long ano, int[] mes, int[] tipoFonte) {
        PainelOrcamentoRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaOrigemResponseDTO> responseList = consultarRceitaOrigem(request);

        if(responseList.isEmpty()) {
            return Collections.emptyList();
        }

        return responseList;
    }


    public List<ReceitaImpostosResponseDTO> getReceitaImpostoList(Long ano, int[] mes, int[] tipoFonte) {
        PainelOrcamentoRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaImpostosResponseDTO> response = consultarReceitaImposto(request);

        if(response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;

    }


    public List<ReceitaICMSResponseDTO> getReceitaICMSList(Long ano, int[] mes, int[] tipoFonte) {
        PainelOrcamentoRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaICMSResponseDTO> response = consultarReceitaICMS(request);

        if(response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }

    public List<ReceitaParticipacaoResponseDTO> getReceitaParticipacaoList(Long ano, int[] mes, int[] tipoFonte) {
        PainelOrcamentoRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaParticipacaoResponseDTO> response = consultarReceitaParticipacao(request);

        if(response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }


    public List<ReceitaDespesaGNDResponseDTO> getReceitaDespesaGNDList
            (Long ano, int[] mes, int[] tipoFonte) {

        PainelOrcamentoRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaDespesaGNDResponseDTO> response = consultarReceitaDespesaGND(request);

        if(response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }


    public List<ReceitaDespesaGNDTotalResponseDTO> getReceitaDespesaGNDTotalList
            (Long ano, int[] mes, int[] tipoFonte) {
        PainelOrcamentoRequestDTO request = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaDespesaGNDTotalResponseDTO> response = consultarReceitaDespesaGNDTotal(request);
        ReceitaDespesaGNDTotalResponseDTO getIndex = response.get(1);
        BigDecimal porcentagemEmpenhada = calcPorcetagemEmpenhada(getIndex);
        BigDecimal porcentagemLiquidada = calcPorcetagemLiquidada(getIndex);
//        System.out.println("porcetagem Liquidada: " + porcentagemLiquidada);
//        System.out.println("porcetagem Empenhada: " + porcentagemEmpenhada);

        response.stream().map(res -> {
            res.setPorcentagemEmpenhada(porcentagemEmpenhada);
            res.setPorcentagemLiquidada(porcentagemLiquidada);
            return res;
        }).collect(Collectors.toList());

        if(response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }


    private BigDecimal calcPorcetagemEmpenhada(ReceitaDespesaGNDTotalResponseDTO receitaDespesasGndTotal) {
        BigDecimal receitaEmpenhada = receitaDespesasGndTotal.getEmpenhado();
        BigDecimal receitaAutorizada = receitaDespesasGndTotal.getAutorizado();
        BigDecimal divisor = new BigDecimal("100");
//        System.out.println("receitaEmpenhada : " + receitaEmpenhada);
//        System.out.println("receitaAutorizada : " + receitaAutorizada);

        BigDecimal division = receitaEmpenhada.divide(receitaAutorizada, 2, RoundingMode.HALF_UP);
        BigDecimal porcentagem = division.multiply(divisor);

        return porcentagem;
    }

    private BigDecimal calcPorcetagemLiquidada(ReceitaDespesaGNDTotalResponseDTO receitaDespesasGndTotal) {
        BigDecimal receitaLiquidadda = receitaDespesasGndTotal.getLiquidado();
        BigDecimal receitaAutorizada = receitaDespesasGndTotal.getAutorizado();
        BigDecimal divisor = new BigDecimal("100");

        BigDecimal division = receitaLiquidadda.divide(receitaAutorizada, 2, RoundingMode.HALF_UP);
//        System.out.println("DIVISÃO : " + division);
        BigDecimal porcentagem = division.multiply(divisor);

        return porcentagem;
    }


    public List<ReceitaTransferenciaCorrenteResponseDTO> getReceitaTransferenciaCorrente
            (Long ano, int[] mes, int[] tipoFonte) {
        PainelOrcamentoRequestDTO requestDTO = convertPaineOrcamentoDto(ano, mes, tipoFonte);

        List<ReceitaTransferenciaCorrenteResponseDTO> response = consultarReceitaTransferenciaCorrente(requestDTO);

        if(response.isEmpty()) {
            return Collections.emptyList();
        }

        return response;
    }

    private List<ReceitaTransferenciaCorrenteResponseDTO> consultarReceitaTransferenciaCorrente
            (PainelOrcamentoRequestDTO request) {
        HashMap<String, Object> params = params(request);
        return apiUtils
                .consult(
                        targetpainelOrcamentoDashReceitaTransferenciaCorrente,
                        dataAccessIdpainelOrcamentoDashReceitaTransferenciaCorrente,
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

    private List<ReceitaDespesaGNDTotalResponseDTO> consultarReceitaDespesaGNDTotal(PainelOrcamentoRequestDTO request) {
        HashMap<String, Object> params = params(request);
        return apiUtils
                .consult(
                        targetpainelOrcamentoDashReceitaDespesaGNDTotal,
                        dataAccessIdpainelOrcamentoDashReceitaDespesaGNDTotal,
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

    private List<ReceitaDespesaGNDResponseDTO> consultarReceitaDespesaGND(PainelOrcamentoRequestDTO request) {
        HashMap<String, Object> params = params(request);
        return apiUtils.consult(targetpainelOrcamentoDashReceitaDespesaGND, dataAccessIdpainelOrcamentoDashReceitaDespesaGND,
                pmoPath, params, rs -> new ReceitaDespesaGNDResponseDTO(
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

    private List<ReceitaParticipacaoResponseDTO> consultarReceitaParticipacao(PainelOrcamentoRequestDTO request) {
        HashMap<String, Object> params = params(request);
        return apiUtils.consult(targetpainelOrcamentoDashReceitaParticipacao, dataAccessIdpainelOrcamentoDashReceitaParticipacao,
                pmoPath, params, rs -> new ReceitaParticipacaoResponseDTO(
                        rs.get("ano").asLong(),
                        rs.get("nome_item_patrimonial").asText(),
                        new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
                ));
    }

    private List<ReceitaICMSResponseDTO> consultarReceitaICMS(PainelOrcamentoRequestDTO request) {

        HashMap<String, Object> params = params(request);
        return apiUtils.consult(targetpainelOrcamentoDashReceitaImposto, dataAccessIdpainelOrcamentoDashReceitaImposto,
                pmoPath, params, rs -> new ReceitaICMSResponseDTO(
                        rs.get("ano").asLong(),
                        rs.get("nome_item_patrimonial").asText(),
                        new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
                ));
    }

    private List<ReceitaImpostosResponseDTO> consultarReceitaImposto(PainelOrcamentoRequestDTO request) {
        HashMap<String, Object> params = params(request);

        return apiUtils.consult(targetpainelOrcamentoDashReceitaImposto, dataAccessIdpainelOrcamentoDashReceitaImposto,
                pmoPath, params, rs -> new ReceitaImpostosResponseDTO(
                        rs.get("ano").asLong(),
                        rs.get("nome_item_patrimonial").asText(),
                        new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
                ));

    }


    private List<ReceitaOrigemResponseDTO> consultarRceitaOrigem(PainelOrcamentoRequestDTO request) {
        HashMap<String, Object> params = params(request);

        return apiUtils.consult(targetpainelOrcamentoDashReceitaOrigem, dataAccessIdpainelOrcamentoDashReceitaOrigem,
                pmoPath, params, rs -> new ReceitaOrigemResponseDTO(
                        rs.get("ano").asLong(),
                        rs.get("origem").asText(),
                        new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
                ));
    }


    private List<ReceitaCategoriaResponseDTO> consultarReceitaCategoria(PainelOrcamentoRequestDTO request) {
        HashMap<String, Object> params = params(request);

        return apiUtils.consult(targetPainelOrcamentoRecitaCategoria, dataAccessIdPainelOrcamentoCategoria, pmoPath,
                params, rs -> new ReceitaCategoriaResponseDTO(
                    rs.get("ano").asLong(),
                    rs.get("categoria").asText(),
                    new BigDecimal(rs.get("vlr_receita_liquida").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
                ));
    }


    public List<ReceitaTotalResponseDTO> consultarReceitaTotal(PainelOrcamentoRequestDTO request) {

        HashMap<String, Object> params = params(request);

        return apiUtils.consult(targetPainelOrcamentoRecitaTotal, dataAccessIdPainelOrcamentoRecitaTotal, pmoPath, params,
                rs -> new ReceitaTotalResponseDTO(
                        rs.get("ano").asInt(),
                        new BigDecimal(
                                rs.get("vlr_receita_prevista").asDouble()

                        ).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(
                                rs.get("vlr_receita_liquida").asDouble(2)
                        ).setScale(2, RoundingMode.HALF_UP)
                ));
    }

    private HashMap<String, Object> params(PainelOrcamentoRequestDTO request) {

        String meses = Arrays.stream(request.getMes()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String tipoFontes = Arrays.stream(request.getTipoFonte()).mapToObj(String::valueOf).collect(Collectors.joining(","));

        HashMap<String, Object> params = new HashMap<>();

        params.put("parampAno", request.getAno());
        params.put("parampMes", meses);
        params.put("parampTipoFonte",tipoFontes);

        return params;
    }

    private PainelOrcamentoRequestDTO convertPaineOrcamentoDto(Long ano, int[] mes, int[] tipoFonte) {
        PainelOrcamentoRequestDTO dto = new PainelOrcamentoRequestDTO(ano, mes, tipoFonte);
        return dto;
    }

}
