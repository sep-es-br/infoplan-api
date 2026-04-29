package br.gov.es.infoplan.service;

import br.gov.es.infoplan.config.pentahoBi.PentahoBiConfigKeys;
import br.gov.es.infoplan.config.pentahoBi.PentahoBiConfigParams;
import br.gov.es.infoplan.config.pentahoBi.PentahoBiProperties;
import br.gov.es.infoplan.config.spo.SPOPentahoConfigKey;
import br.gov.es.infoplan.dto.planejamentoOrcamentario.*;
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
public class PlanejamentoOrcamentarioService {

        @Autowired
        private ApiUtils apiUtils;

        @Autowired
        private PentahoBiProperties properties;

        private String pmoPath;

        @PostConstruct
        public void init() {
                this.pmoPath = properties.getPlanejamentoOrcamentario().getPath();
                // log.info("PMO Path inicializado: {}", pmoPath);
        }

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

        public List<SPOTotalAutorizadoUoDTO> getTotalAutorizadoUoList(SPOFiltroDTO filtro) {
                List<SPOTotalAutorizadoUoDTO> list = consultarTotalAutorizadoUo(filtro);

                if (list == null || list.isEmpty()) {
                        return new ArrayList<>(Arrays.asList(new SPOTotalAutorizadoUoDTO()));
                }

                list.forEach(res -> {
                        BigDecimal pctE = SPOTotalAutorizadoUoDTO.calcularPorcentagem(res.getPorcentagemAutorizado(),
                                        res.getPorcentagemEmpenhado());
                        BigDecimal pctL = SPOTotalAutorizadoUoDTO.calcularPorcentagem(res.getPorcentagemAutorizado(),
                                        res.getPorcentagemLiquidado());
                        BigDecimal pctP = SPOTotalAutorizadoUoDTO.calcularPorcentagem(res.getPorcentagemAutorizado(),
                                        res.getPorcentagemPagoSemRap());

                        res.setPorcentagemEmpenhado(pctE);
                        res.setPorcentagemLiquidado(pctL);
                        res.setPorcentagemPagoSemRap(pctP);
                });

                return list;
        }

        public List<SPOTotalAutorizadoPoDTO> getTotalAutorizadoPoList(SPOFiltroDTO filtro) {
                List<SPOTotalAutorizadoPoDTO> list = consultarTotalAutorizadoPo(filtro);

                if (list == null || list.isEmpty()) {
                        return new ArrayList<>(Arrays.asList(new SPOTotalAutorizadoPoDTO()));
                }

                list.forEach(res -> {
                        BigDecimal pctE = SPOTotalAutorizadoUoDTO.calcularPorcentagem(res.getPorcentagemAutorizado(),
                                        res.getPorcentagemEmpenhado());
                        BigDecimal pctL = SPOTotalAutorizadoUoDTO.calcularPorcentagem(res.getPorcentagemAutorizado(),
                                        res.getPorcentagemLiquidado());
                        BigDecimal pctP = SPOTotalAutorizadoUoDTO.calcularPorcentagem(res.getPorcentagemAutorizado(),
                                        res.getPorcentagemPagoSemRap());

                        res.setPorcentagemEmpenhado(pctE);
                        res.setPorcentagemLiquidado(pctL);
                        res.setPorcentagemPagoSemRap(pctP);
                });

                return list;
        }

        public List<SPOTotalAnoDTO> getTotalAno(SPOFiltroDTO filtro) {
                List<SPOTotalAnoDTO> list = consultarTotalAno(filtro);
                return list;
        }

        public List<SPOTotalAnoSigefes> getTotalAnoSigefes(SPOFiltroDTO filtro) {
                List<SPOTotalAnoSigefes> list = consultarTotalAnoSigefes(filtro);
                return list;
        }

        private List<SPOTotalAnoSigefes> consultarTotalAnoSigefes(SPOFiltroDTO filtro) {
                HashMap<String, Object> params = paramsTotalAutorizado(filtro);

                String target = properties.getTargetOrThrow(PentahoBiConfigKeys.SPO_TOTAL_ANO_SIGEFES);
                String dataAccessId = properties.getDataAccessIdOrThrow(PentahoBiConfigKeys.SPO_TOTAL_ANO_SIGEFES);

                return apiUtils.consult(
                                target,
                                dataAccessId,
                                pmoPath,
                                params,
                                rs -> new SPOTotalAnoSigefes(
                                                rs.get(SPOPentahoConfigKey.ANO).asText(),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PAGO_COM_RAP)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_SEM_RAP)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP)

                                ));
        }

        private List<SPOTotalAnoDTO> consultarTotalAno(SPOFiltroDTO filtro) {
                HashMap<String, Object> params = paramsTotalAutorizado(filtro);

                String target = properties.getTargetOrThrow(PentahoBiConfigKeys.SPO_TOTAL_ANO);
                String dataAccessId = properties.getDataAccessIdOrThrow(PentahoBiConfigKeys.SPO_TOTAL_ANO);

                return apiUtils.consult(
                                target,
                                dataAccessId,
                                pmoPath,
                                params,
                                rs -> new SPOTotalAnoDTO(
                                                rs.get(SPOPentahoConfigKey.ANO).asText(),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PREVISTO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_CONTRATADO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_AUTORIZADO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_EMPENHADO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PAGO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PAGO_COM_RAP)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP)

                                ));
        }

        private List<SPOTotalAutorizadoPoDTO> consultarTotalAutorizadoPo(SPOFiltroDTO filtro) {
                HashMap<String, Object> params = paramsTotalAutorizado(filtro);

                String target = properties.getTargetOrThrow(PentahoBiConfigKeys.SPO_TOTAL_AUTORIZADO_PO);
                String dataAccessId = properties.getDataAccessIdOrThrow(PentahoBiConfigKeys.SPO_TOTAL_AUTORIZADO_PO);

                return apiUtils.consult(
                                target,
                                dataAccessId,
                                pmoPath,
                                params,
                                rs -> new SPOTotalAutorizadoPoDTO(
                                                rs.get(SPOPentahoConfigKey.UO).asText(),
                                                rs.get(SPOPentahoConfigKey.SIGLA).asText(),
                                                rs.get(SPOPentahoConfigKey.PO).asText(),
                                                rs.get(SPOPentahoConfigKey.NOME).asText(),
                                                rs.get(SPOPentahoConfigKey.NOME_PO).asText(),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_EMPENHADO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_AUTORIZADO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_LIQUIDADO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_SEM_RAP)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PREVISTO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP)

                                ));
        }

        private List<SPOTotalAutorizadoUoDTO> consultarTotalAutorizadoUo(SPOFiltroDTO filtro) {
                HashMap<String, Object> params = paramsTotalAutorizado(filtro);

                String target = properties.getTargetOrThrow(PentahoBiConfigKeys.SPO_TOTAL_AUTORIZADO_UO);
                String dataAccessId = properties.getDataAccessIdOrThrow(PentahoBiConfigKeys.SPO_TOTAL_AUTORIZADO_UO);

                return apiUtils.consult(
                                target,
                                dataAccessId,
                                pmoPath,
                                params,
                                rs -> new SPOTotalAutorizadoUoDTO(
                                                rs.get(SPOPentahoConfigKey.UO).asText(),
                                                rs.get(SPOPentahoConfigKey.SIGLA).asText(),
                                                rs.get(SPOPentahoConfigKey.NOME_UO).asText(),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_EMPENHADO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_AUTORIZADO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_LIQUIDADO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_SEM_RAP)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PREVISTO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP)

                                ));
        }

        private List<SPODashboardPoDTO> consultarDashboardPo(SPOFiltroDTO filtro) {
                HashMap<String, Object> params = paramsTotalAutorizado(filtro);
                String target = properties.getTargetOrThrow("planejamentoOrcamentarioDashboardPo");
                String dataAccessId = properties.getDataAccessIdOrThrow("planejamentoOrcamentarioDashboardPo");
                return apiUtils.consult(
                                target,
                                dataAccessId,
                                pmoPath,
                                params,
                                rs -> new SPODashboardPoDTO(
                                                rs.get(SPOPentahoConfigKey.UO).asText(),
                                                rs.get(SPOPentahoConfigKey.SIGLA).asText(),
                                                rs.get(SPOPentahoConfigKey.PO).asText(),
                                                rs.get(SPOPentahoConfigKey.NOME_UO).asText(),
                                                rs.get(SPOPentahoConfigKey.NOME_PO).asText(),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PREVISTO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_CONTRATADO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_AUTORIZADO)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP)));
        }

        private List<SPODashboardUoDTO> consultarDashboardUo(SPOFiltroDTO filtro) {
                HashMap<String, Object> params = paramsTotalAutorizado(filtro);

                String target = properties.getTargetOrThrow("planejamentoOrcamentarioDashboardUo");
                String dataAccessId = properties.getDataAccessIdOrThrow("planejamentoOrcamentarioDashboardUo");
                return apiUtils.consult(
                                target,
                                dataAccessId,
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
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP)));
        }

        private List<SPOFiltroPosDTO> consultarPos(String[] codUos, String ano) {
                String codUosList = Arrays.stream(codUos).collect(Collectors.joining(","));
                HashMap params = new HashMap<>();
                params.put("parampCodUo", codUosList);
                params.put("parampAno", ano);

                String target = properties.getTargetOrThrow("planejamentoOrcamentarioFiltroPos");
                String dataAccessId = properties.getDataAccessIdOrThrow("planejamentoOrcamentarioFiltroPos");
                return apiUtils.consult(
                                target,
                                dataAccessId,
                                pmoPath,
                                params,
                                rs -> new SPOFiltroPosDTO(
                                                rs.get("cod_po").asText(),
                                                rs.get("nome_po").asText()));
        }

        private List<SPOFiltroUosDTO> consultarUos() {
                String target = properties.getTargetOrThrow("planejamentoOrcamentarioFiltroUos");
                String dataAccessId = properties.getDataAccessIdOrThrow("planejamentoOrcamentarioFiltroUos");
                return apiUtils.consult(
                                target,
                                dataAccessId,
                                pmoPath,
                                new HashMap<>(),
                                rs -> new SPOFiltroUosDTO(
                                                rs.get("cod_uo").asText(),
                                                rs.get("nome_uo").asText())

                );
        }

        private List<SPOTotalAutorizadoDTO> consultarTotalAutorizado(SPOFiltroDTO request) {
                HashMap<String, Object> params = paramsTotalAutorizado(request);
                String target = properties.getTargetOrThrow("planejamentoOrcamentarioTotalAutorizado");
                String dataAccessId = properties.getDataAccessIdOrThrow("planejamentoOrcamentarioTotalAutorizado");
                return apiUtils.consult(
                                target,
                                dataAccessId,
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
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_SEM_RAP)
                                                                .asDouble(0)).setScale(2, RoundingMode.HALF_UP)));
        }

        private List<SPOTotalPrevistoDTO> consultarTotalPrevisto(
                        SPOFiltroDTO request) {
                HashMap<String, Object> params = paramsTotalPrevisto(request);
                String target = properties.getTargetOrThrow("planejamentoOrcamentarioTotalPrevisto");
                String dataAccessId = properties.getDataAccessIdOrThrow("planejamentoOrcamentarioTotalPrevisto");
                return apiUtils.consult(
                                target,
                                dataAccessId,
                                pmoPath,
                                params,
                                rs -> new SPOTotalPrevistoDTO(
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_PREVISTO)
                                                                .asDouble(0))
                                                                .setScale(2, RoundingMode.HALF_UP),
                                                new BigDecimal(rs.get(SPOPentahoConfigKey.VLR_CONTRATADO)
                                                                .asDouble(0))
                                                                .setScale(2, RoundingMode.HALF_UP),
                                                rs.get(SPOPentahoConfigKey.DT_FIM_EXTRACAO).asText()));
        }

        public static String joinArray(String[] array) {
                if (array == null || array.length == 0) {
                        return "";
                }
                return Arrays.stream(array)
                                .filter(Objects::nonNull)
                                .collect(Collectors.joining(","));
        }

        private HashMap<String, Object> paramsTotalPrevisto(SPOFiltroDTO request) {
                HashMap<String, Object> params = new HashMap<>();

                params.put(PentahoBiConfigParams.PARAMP_ANO, request.getAno());
                params.put(PentahoBiConfigParams.PARAMP_FONTE, joinArray(request.getTipoFonte()));

                // Correção direta para UO e PO
                params.put(PentahoBiConfigParams.PARAMP_UO, joinArray(request.getUo()));
                params.put(PentahoBiConfigParams.PARAMP_PO, joinArray(request.getPo()));

                params.put(PentahoBiConfigParams.PARAMP_GND, joinArray(request.getGnd()));

                return params;
        }

        private HashMap<String, Object> paramsTotalAutorizado(SPOFiltroDTO request) {
                HashMap<String, Object> params = new HashMap<>();

                params.put(PentahoBiConfigParams.PARAMP_ANO, request.getAno());
                params.put(PentahoBiConfigParams.PARAMP_MESSES, joinArray(request.getMes()));
                params.put(PentahoBiConfigParams.PARAMP_FONTE, joinArray(request.getTipoFonte()));

                // Correção direta para UO e PO
                params.put(PentahoBiConfigParams.PARAMP_UO, joinArray(request.getUo()));
                params.put(PentahoBiConfigParams.PARAMP_PO, joinArray(request.getPo()));

                params.put(PentahoBiConfigParams.PARAMP_GND, joinArray(request.getGnd()));

                return params;
        }
}
