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

import static br.gov.es.infoplan.utils.ApiUtils.joinArray;
import static br.gov.es.infoplan.utils.ApiUtils.parseBigDecimal;

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
        }

        public List<SPOTotalPrevistoDTO> getTotalPrevisto(SPOFiltroDTO dto) {
                return apiUtils.executePentahoQuery("planejamentoOrcamentarioTotalPrevisto", pmoPath,
                                paramsTotalPrevisto(dto),
                                rs -> new SPOTotalPrevistoDTO(
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_PREVISTO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_CONTRATADO),
                                                rs.get(SPOPentahoConfigKey.DT_FIM_EXTRACAO).asText()));
        }

        public List<SPOTotalAutorizadoDTO> getTotalAutorizado(SPOFiltroDTO request) {
                return apiUtils.executePentahoQuery("planejamentoOrcamentarioTotalAutorizado", pmoPath,
                                paramsTotalAutorizado(request),
                                rs -> new SPOTotalAutorizadoDTO(
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_AUTORIZADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_EMPENHADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_LIQUIDADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_PAGO_COM_RAP),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_SEM_RAP)));
        }

        public List<SPOFiltroUosDTO> getListUos() {
                return apiUtils.executePentahoQuery("planejamentoOrcamentarioFiltroUos", pmoPath, new HashMap<>(),
                                rs -> new SPOFiltroUosDTO(
                                                rs.get("cod_uo").asText(),
                                                rs.get("nome_uo").asText()));
        }

        public List<SPOFiltroPosDTO> getListPos(String[] codUos, String ano) {
                Map<String, Object> params = new HashMap<>();
                params.put("parampCodUo", joinArray(codUos));
                params.put("parampAno", ano);

                return apiUtils.executePentahoQuery("planejamentoOrcamentarioFiltroPos", pmoPath, params,
                                rs -> new SPOFiltroPosDTO(
                                                rs.get("cod_po").asText(),
                                                rs.get("nome_po").asText()));
        }

        public List<SPODashboardUoDTO> getListDashboardUo(SPOFiltroDTO filtro) {
                return apiUtils.executePentahoQuery("planejamentoOrcamentarioDashboardUo", pmoPath,
                                paramsTotalAutorizado(filtro),
                                rs -> new SPODashboardUoDTO(
                                                rs.get(SPOPentahoConfigKey.UO).asText(),
                                                rs.get(SPOPentahoConfigKey.SIGLA).asText(),
                                                rs.get(SPOPentahoConfigKey.NOME).asText(),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_PREVISTO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_CONTRATADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_AUTORIZADO)));
        }

        public List<SPODashboardPoDTO> getListDashboardPo(SPOFiltroDTO filtro) {
                return apiUtils.executePentahoQuery("planejamentoOrcamentarioDashboardPo", pmoPath,
                                paramsTotalAutorizado(filtro),
                                rs -> new SPODashboardPoDTO(
                                                rs.get(SPOPentahoConfigKey.UO).asText(),
                                                rs.get(SPOPentahoConfigKey.SIGLA).asText(),
                                                rs.get(SPOPentahoConfigKey.PO).asText(),
                                                rs.get(SPOPentahoConfigKey.NOME_UO).asText(),
                                                rs.get(SPOPentahoConfigKey.NOME_PO).asText(),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_PREVISTO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_CONTRATADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_AUTORIZADO)));
        }

        public List<SPOTotalAutorizadoUoDTO> getTotalAutorizadoUoList(SPOFiltroDTO filtro) {
                List<SPOTotalAutorizadoUoDTO> list = apiUtils.executePentahoQuery(
                                PentahoBiConfigKeys.SPO_TOTAL_AUTORIZADO_UO, pmoPath, paramsTotalAutorizado(filtro),
                                rs -> new SPOTotalAutorizadoUoDTO(
                                                rs.get(SPOPentahoConfigKey.UO).asText(),
                                                rs.get(SPOPentahoConfigKey.SIGLA).asText(),
                                                rs.get(SPOPentahoConfigKey.NOME_UO).asText(),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_EMPENHADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_AUTORIZADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_LIQUIDADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_SEM_RAP),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_PREVISTO)));

                if (list.isEmpty())
                        return List.of(new SPOTotalAutorizadoUoDTO());
                list.forEach(this::calcularPorcentagensUo);
                return list;
        }

        public List<SPOTotalAutorizadoPoDTO> getTotalAutorizadoPoList(SPOFiltroDTO filtro) {
                List<SPOTotalAutorizadoPoDTO> list = apiUtils.executePentahoQuery(
                                PentahoBiConfigKeys.SPO_TOTAL_AUTORIZADO_PO, pmoPath, paramsTotalAutorizado(filtro),
                                rs -> new SPOTotalAutorizadoPoDTO(
                                                rs.get(SPOPentahoConfigKey.UO).asText(),
                                                rs.get(SPOPentahoConfigKey.SIGLA).asText(),
                                                rs.get(SPOPentahoConfigKey.PO).asText(),
                                                rs.get(SPOPentahoConfigKey.NOME).asText(),
                                                rs.get(SPOPentahoConfigKey.NOME_PO).asText(),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_EMPENHADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_AUTORIZADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_LIQUIDADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_SEM_RAP),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_PREVISTO)));

                if (list.isEmpty())
                        return List.of(new SPOTotalAutorizadoPoDTO());
                list.forEach(this::calcularPorcentagensPo);
                return list;
        }

        public List<SPOTotalAnoDTO> getTotalAno(SPOFiltroDTO filtro) {
                return apiUtils.executePentahoQuery(PentahoBiConfigKeys.SPO_TOTAL_ANO, pmoPath,
                                paramsTotalAutorizado(filtro),
                                rs -> new SPOTotalAnoDTO(
                                                rs.get(SPOPentahoConfigKey.ANO).asText(),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_PREVISTO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_CONTRATADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_AUTORIZADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_EMPENHADO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_PAGO),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_PAGO_COM_RAP)));
        }

        public List<SPOTotalAnoSigefes> getTotalAnoSigefes(SPOFiltroDTO filtro) {
                return apiUtils.executePentahoQuery(PentahoBiConfigKeys.SPO_TOTAL_ANO_SIGEFES, pmoPath,
                                paramsTotalAutorizado(filtro),
                                rs -> new SPOTotalAnoSigefes(
                                                rs.get(SPOPentahoConfigKey.ANO).asText(),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_PAGO_COM_RAP),
                                                parseBigDecimal(rs, SPOPentahoConfigKey.VLR_SEM_RAP)));
        }

        private Map<String, Object> paramsTotalPrevisto(SPOFiltroDTO request) {
                Map<String, Object> params = new HashMap<>();
                params.put(PentahoBiConfigParams.PARAMP_ANO, request.getAno());
                params.put(PentahoBiConfigParams.PARAMP_FONTE, joinArray(request.getTipoFonte()));
                params.put(PentahoBiConfigParams.PARAMP_UO, joinArray(request.getUo()));
                params.put(PentahoBiConfigParams.PARAMP_PO, joinArray(request.getPo()));
                params.put(PentahoBiConfigParams.PARAMP_GND, joinArray(request.getGnd()));
                return params;
        }

        private Map<String, Object> paramsTotalAutorizado(SPOFiltroDTO request) {
                Map<String, Object> params = paramsTotalPrevisto(request);
                params.put(PentahoBiConfigParams.PARAMP_MESSES, joinArray(request.getMes()));
                return params;
        }

        private void calcularPorcentagensUo(SPOTotalAutorizadoUoDTO uo) {
                BigDecimal autorizado = uo.getPorcentagemAutorizado();
                uo.setPorcentagemEmpenhado(calcularPorcentagem(autorizado, uo.getPorcentagemEmpenhado()));
                uo.setPorcentagemLiquidado(calcularPorcentagem(autorizado, uo.getPorcentagemLiquidado()));
                uo.setPorcentagemPagoSemRap(calcularPorcentagem(autorizado, uo.getPorcentagemPagoSemRap()));
        }

        private void calcularPorcentagensPo(SPOTotalAutorizadoPoDTO po) {
                BigDecimal autorizado = po.getPorcentagemAutorizado();
                po.setPorcentagemEmpenhado(calcularPorcentagem(autorizado, po.getPorcentagemEmpenhado()));
                po.setPorcentagemLiquidado(calcularPorcentagem(autorizado, po.getPorcentagemLiquidado()));
                po.setPorcentagemPagoSemRap(calcularPorcentagem(autorizado, po.getPorcentagemPagoSemRap()));
        }

        private BigDecimal calcularPorcentagem(BigDecimal autorizado, BigDecimal value) {
                if (value == null || autorizado == null || autorizado.compareTo(BigDecimal.ZERO) == 0) {
                        return BigDecimal.ZERO;
                }
                return value.multiply(new BigDecimal(100))
                                .divide(autorizado, 2, RoundingMode.HALF_UP);
        }
}
