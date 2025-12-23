package br.gov.es.infoplan.service;

import br.gov.es.infoplan.config.pentahoBi.PentahoBiConfigParams;
import br.gov.es.infoplan.config.spo.SPOPentahoConfigKey;
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

    @Value("${pentahoBI.pmo.target.planejamentoOrcamentarioDashboardUo}")
    private String targetplanejamentoOrcamentarioDashboardUo;

    @Value("${pentahoBI.pmo.dataAccessId.planejamentoOrcamentarioDashboardUo}")
    private String dataAccessIdplanejamentoOrcamentarioDashboardUo;

    @Value("${pentahoBI.pmo.target.planejamentoOrcamentarioDashboardPo}")
    private String targetplanejamentoOrcamentarioDashboardPo;

    @Value("${pentahoBI.pmo.dataAccessId.planejamentoOrcamentarioDashboardPo}")
    private String dataAccessIdplanejamentoOrcamentarioDashboardPo;

    public List<SPOTotalPrevistoDTO> getTotalPrevisto(
            SPOFiltroDTO dto) {
        List<SPOTotalPrevistoDTO> totalPrevistoList = consultarTotalPrevisto(dto);
        return totalPrevistoList;
    }


    public List<SPOTotalAutorizadoDTO> getTotalAutorizado(
            SPOFiltroDTO request) {
        List<SPOTotalAutorizadoDTO> totalAutorizadoList = consultarTotalAutorizado(request);
        return totalAutorizadoList;
    }

    public List<SPOFiltroUosDTO> getListUos() {
        List<SPOFiltroUosDTO> list = consultarUos();
        return list;
    }


    public List<SPOFiltroPosDTO> getListPos(String[] codUos, String ano) {
        List<SPOFiltroPosDTO> list = consultarPos(codUos, ano);
        return list;
    }


    public List<SPODashboardUoDTO> getListDashboardUo(SPOFiltroDTO filtro) {
        List<SPODashboardUoDTO> listDashboard = consultarDashboardUo(filtro);
        return listDashboard;
    }

    public List<SPODashboardPoDTO> getListDashboardPo(SPOFiltroDTO filtro) {
        List<SPODashboardPoDTO> list = consultarDashboardPo(filtro);
        return list;
    }

    private List<SPODashboardPoDTO> consultarDashboardPo(SPOFiltroDTO filtro) {
        HashMap<String, Object> params = paramsTotalAutorizado(filtro);

        return apiUtils.consult(
                targetplanejamentoOrcamentarioDashboardPo,
                dataAccessIdplanejamentoOrcamentarioDashboardPo,
                pmoPath,
                params,
                rs -> new SPODashboardPoDTO(
                        rs.get(SPOPentahoConfigKey.PO).asText(),
                        rs.get(SPOPentahoConfigKey.SIGLA).asText(),
                        rs.get(SPOPentahoConfigKey.NOME).asText(),
                        new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PREVISTO)
                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_CONTRATADO)
                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_AUTORIZADO)
                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP)
                )
        );
    }

    private List<SPODashboardUoDTO> consultarDashboardUo(SPOFiltroDTO filtro) {
       HashMap<String, Object> params = paramsTotalAutorizado(filtro);

       return apiUtils.consult(
               targetplanejamentoOrcamentarioDashboardUo,
               dataAccessIdplanejamentoOrcamentarioDashboardUo,
               pmoPath,
               params,
               rs -> new SPODashboardUoDTO(
                       rs.get(SPOPentahoConfigKey.UO).asText(),
                       rs.get(SPOPentahoConfigKey.SIGLA).asText(),
                       rs.get(SPOPentahoConfigKey.NOME).asText(),
                       new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PREVISTO)
                               .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                       new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_CONTRATADO)
                               .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                       new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_AUTORIZADO)
                               .asDouble(0)).setScale(2, RoundingMode.HALF_UP)
               )
       );
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

    private List<SPOTotalAutorizadoDTO> consultarTotalAutorizado(SPOFiltroDTO request) {
        HashMap<String, Object> params = paramsTotalAutorizado(request);

        return apiUtils.consult(
                targetplanejamentoOrcamentarioTotalAutorizado,
                dataAccessIdplanejamentoOrcamentarioTotalAutorizado,
                pmoPath,
                params,
                rs -> new SPOTotalAutorizadoDTO(
                        new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_AUTORIZADO)
                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_EMPENHADO)
                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_LIQUIDADO)
                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PAGO_COM_RAP)
                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_SEM_COM_RAP)
                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP)
                )
        );
    }

    private List<SPOTotalPrevistoDTO> consultarTotalPrevisto(
            SPOFiltroDTO request
    ) {
        HashMap<String, Object> params = paramsTotalPrevisto(request);

        return apiUtils.consult(
                targetplanejamentoOrcamentarioTotalPrevisto,
                dataAccessIdplanejamentoOrcamentarioTotalPrevisto,
                pmoPath,
                params,
                rs -> new SPOTotalPrevistoDTO(
                        new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PREVISTO)
                                .asDouble(0))
                                .setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_CONTRATADO)
                                .asDouble(0))
                                .setScale(2, RoundingMode.HALF_UP),
                        rs.get(SPOPentahoConfigKey.DT_FIM_EXTRACAO).asText()
                )
        );
    }

    private HashMap<String, Object> paramsTotalPrevisto(SPOFiltroDTO request) {

        String tipoFontes = Arrays.stream(request.getTipoFonte()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String uo = Arrays.stream(request.getUo()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String gnd = Arrays.stream(request.getGnd()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String po = Arrays.stream(request.getPo()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        HashMap<String, Object> params = new HashMap<>();

        params.put(PentahoBiConfigParams.PARAMP_ANO, request.getAno());
        params.put(PentahoBiConfigParams.PARAMP_FONTE,tipoFontes);
        params.put(PentahoBiConfigParams.PARAMP_UO, uo);
        params.put(PentahoBiConfigParams.PARAMP_GND, gnd);
        params.put(PentahoBiConfigParams.PARAMP_PO, po);

        return params;
    }

    private HashMap<String, Object> paramsTotalAutorizado(SPOFiltroDTO request) {

        String meses = Arrays.stream(request.getMes()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String tipoFontes = Arrays.stream(request.getTipoFonte()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String uo = Arrays.stream(request.getUo()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String gnd = Arrays.stream(request.getGnd()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String po = Arrays.stream(request.getPo()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        HashMap<String, Object> params = new HashMap<>();

        params.put(PentahoBiConfigParams.PARAMP_ANO, request.getAno());
        params.put(PentahoBiConfigParams.PARAMP_MESSES, meses);
        params.put(PentahoBiConfigParams.PARAMP_FONTE, tipoFontes);
        params.put(PentahoBiConfigParams.PARAMP_UO, uo);
        params.put(PentahoBiConfigParams.PARAMP_GND, gnd);
        params.put(PentahoBiConfigParams.PARAMP_PO, po);

        return params;
    }
}
