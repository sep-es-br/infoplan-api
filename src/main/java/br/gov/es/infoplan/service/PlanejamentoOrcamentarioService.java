package br.gov.es.infoplan.service;

import br.gov.es.infoplan.dto.planejamentoOrcamentario.*;
import br.gov.es.infoplan.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanejamentoOrcamentarioService {

    @Autowired
    private ApiUtils apiUtils;

    @Value("${pentahoBI.planejamentoOrcamentario.path}")
    private String pmoPath;

    @Value("${pentahoBI.pmo.target.planejamentoOrcamentarioTotalPrevisto}")
    private String targetplanejamentoOrcamentarioTotalPrevisto;

    @Value("${pentahoBI.pmo.dataAccessId.planejamentoOrcamentarioTotalPrevisto}")
    private String dataAccessIdplanejamentoOrcamentarioTotalPrevisto;

    @Value("${pentahoBI.pmo.target.planejamentoOrcamentarioTotalAutorizado}")
    private String targetplanejamentoOrcamentarioTotalAutorizado;

    @Value("${pentahoBI.pmo.dataAccessId.planejamentoOrcamentarioTotalAutorizado}")
    private String dataAccessIdplanejamentoOrcamentarioTotalAutorizado;

    @Value("${pentahoBI.pmo.target.planejamentoOrcamentarioFiltroUos}")
    private String targetplanejamentoOrcamentarioFiltroUos;

    @Value("${pentahoBI.pmo.dataAccessId.planejamentoOrcamentarioFiltroUos}")
    private String dataAccessIdplanejamentoOrcamentarioFiltroUos;

    @Value("${pentahoBI.pmo.target.planejamentoOrcamentarioFiltroPos}")
    private String targetplanejamentoOrcamentarioFiltroPos;

    @Value("${pentahoBI.pmo.dataAccessId.planejamentoOrcamentarioFiltroPos}")
    private String dataAccessIdplanejamentoOrcamentarioFiltroPos;

    public List<SPOTotalPrevistoDTO> getTotalPrevisto(
            SPOTotalPrevistoRequestDTO dto) {
        List<SPOTotalPrevistoDTO> totalPrevistoList = consultarTotalPrevisto(dto);

        if(totalPrevistoList.isEmpty()) {
            return Collections.emptyList();
        }

        return totalPrevistoList;
    }


    public List<SPOTotalAutorizadoDTO> getTotalAutorizado(
            SPOTotalAutorizadoRequestDTO request) {
        List<SPOTotalAutorizadoDTO> totalAutorizadoList = consultarTotalAutorizado(request);

        if (totalAutorizadoList.isEmpty()) {
            return Collections.emptyList();
        }

        return totalAutorizadoList;
    }

    public List<SPOFiltroUosDTO> getListUos() {
        List<SPOFiltroUosDTO> list = consultarUos();

        if(list.isEmpty()) {
            return Collections.emptyList();
        }
        return list;
    }


    public List<SPOFiltroPosDTO> getListPos(String[] codUos, String ano) {
        List<SPOFiltroPosDTO> list = consultarPos(codUos, ano);

        if(list.isEmpty()) {
            return Collections.emptyList();
        }
        return list;
    }

    private List<SPOFiltroPosDTO> consultarPos(String[] codUos, String ano) {
        String codUosList = Arrays.stream(codUos).collect(Collectors.joining(","));
        HashMap params = new HashMap<>();
        params.put("parampCodUo",codUosList);
        params.put("parampAno", ano);

        return apiUtils.consult(
                targetplanejamentoOrcamentarioFiltroPos,
                dataAccessIdplanejamentoOrcamentarioFiltroPos,
                pmoPath,
                params,
                rs -> new SPOFiltroPosDTO(
                        rs.get("cod_po").asText(),
                        rs.get("nome_po").asText()
                )
        );
    }

    private List<SPOFiltroUosDTO> consultarUos() {

        return apiUtils.consult(
            targetplanejamentoOrcamentarioFiltroUos,
                dataAccessIdplanejamentoOrcamentarioFiltroUos,
                pmoPath,
                new HashMap<>(),
                rs -> new SPOFiltroUosDTO(
                    rs.get("cod_uo").asText(),
                    rs.get("nome_uo").asText()
                )

        );
    }

    private List<SPOTotalAutorizadoDTO> consultarTotalAutorizado(
            SPOTotalAutorizadoRequestDTO request
    ) {
        HashMap<String, Object> params = paramsTotalAutorizado(request);

        return apiUtils.consult(
                targetplanejamentoOrcamentarioTotalAutorizado,
                dataAccessIdplanejamentoOrcamentarioTotalAutorizado,
                pmoPath,
                params,
                rs -> new SPOTotalAutorizadoDTO(
                        new BigDecimal(rs.get("vlr_autorizado").asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_empenhado").asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_liquidado").asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_pago_com_rap").asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_pago_sem_rap").asDouble(0)).setScale(2, RoundingMode.HALF_UP)
                )
        );
    }

    private List<SPOTotalPrevistoDTO> consultarTotalPrevisto(
            SPOTotalPrevistoRequestDTO request
    ) {
        HashMap<String, Object> params = paramsTotalPrevisto(request);

        return apiUtils.consult(
                targetplanejamentoOrcamentarioTotalPrevisto,
                dataAccessIdplanejamentoOrcamentarioTotalPrevisto,
                pmoPath,
                params,
                rs -> new SPOTotalPrevistoDTO(
                        new BigDecimal(rs.get("vlr_previsto").asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_contratado").asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        rs.get("dt_fim_extracao").asText()
                )
        );
    }

    private HashMap<String, Object> paramsTotalPrevisto(SPOTotalPrevistoRequestDTO request) {

        String tipoFontes = Arrays.stream(request.getTipoFonte()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String uo = Arrays.stream(request.getUo()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String gnd = Arrays.stream(request.getGnd()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String po = Arrays.stream(request.getPo()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        HashMap<String, Object> params = new HashMap<>();

        params.put("parampAno", request.getAno());
        params.put("parampFonte",tipoFontes);
        params.put("parampUo", uo);
        params.put("parampGnd", gnd);
        params.put("parampPo", po);

        return params;
    }

    private HashMap<String, Object> paramsTotalAutorizado(SPOTotalAutorizadoRequestDTO request) {

        String meses = Arrays.stream(request.getMes()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String tipoFontes = Arrays.stream(request.getTipoFonte()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String uo = Arrays.stream(request.getUo()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String gnd = Arrays.stream(request.getGnd()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String po = Arrays.stream(request.getPo()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        HashMap<String, Object> params = new HashMap<>();

        params.put("parampAno", request.getAno());
        params.put("parampMes",meses);
        params.put("parampFonte",tipoFontes);
        params.put("parampUo", uo);
        params.put("parampGnd", gnd);
        params.put("parampPo", po);

        return params;
    }
}
