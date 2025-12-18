package br.gov.es.infoplan.service;

import br.gov.es.infoplan.dto.planejamentoOrcamentario.SPOTotalPrevistoDTO;
import br.gov.es.infoplan.dto.planejamentoOrcamentario.SPOTotalAutorizadoDTO;
import br.gov.es.infoplan.dto.planejamentoOrcamentario.SPOTotalAutorizadoRequestDTO;
import br.gov.es.infoplan.dto.planejamentoOrcamentario.SPOTotalPrevistoRequestDTO;
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
                        new BigDecimal(rs.get("vlr_autorizado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_empenhado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_liquidado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_pago_com_rap").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_pago_sem_rap").asDouble(2)).setScale(2, RoundingMode.HALF_UP)
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
                        new BigDecimal(rs.get("vlr_previsto").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                        new BigDecimal(rs.get("vlr_contratado").asDouble(2)).setScale(2, RoundingMode.HALF_UP),
                        rs.get("dt_fim_extracao").asText()
                )
        );
    }

    private HashMap<String, Object> paramsTotalPrevisto(SPOTotalPrevistoRequestDTO request) {

        String tipoFontes = Arrays.stream(request.getTipoFonte()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String uo = Arrays.stream(request.getUo()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String gnd = Arrays.stream(request.getGnd()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String po = Arrays.stream(request.getPo()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        String ano = Arrays.stream(request.getAno())
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        HashMap<String, Object> params = new HashMap<>();

        params.put("parampAno", ano);
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
        String ano = Arrays.stream(request.getAno())
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        HashMap<String, Object> params = new HashMap<>();

        params.put("parampAno", ano);
        params.put("parampMes",meses);
        params.put("parampFonte",tipoFontes);
        params.put("parampUo", uo);
        params.put("parampGnd", gnd);
        params.put("parampPo", po);

        return params;
    }

}
