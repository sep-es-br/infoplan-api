package br.gov.es.infoplan.service;

import br.gov.es.infoplan.config.pentahoBi.PentahoBiProperties;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterActionDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterBugataryUnitDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.request.FilterFullSourceDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.response.ActionResponseDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.response.BudgetaryUnitResponseDTO;
import br.gov.es.infoplan.dto.IndicatorExecution.response.FullSourceResponseDTO;
import br.gov.es.infoplan.utils.ApiUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    private Map<String, Object> params(FilterBugataryUnitDTO request) {
        Map<String, Object> params = new HashMap<>();

        String year = request.year();

        if (year != null && !year.isEmpty()) {
            params.put(PARAMP_ANO, year);
        }

        return params;
    }

    private Map<String, Object> params(FilterActionDTO request) {
        Map<String, Object> params = new HashMap<>();

        String year = request.year();
        String uo = request.uo();

        if (year != null && !year.isEmpty()) {
            params.put(PARAMP_ANO, year);
        }

        params.put(PARAMP_COD_UO, uo);

        return params;
    }

    private  Map<String, Object> params(FilterFullSourceDTO request) {
        Map<String, Object> params = new HashMap<>();

        String year = request.year();
        String uo = request.uo();
        String action = request.action();

        if (year != null && !year.isEmpty()) {
            params.put(PARAMP_ANO, year);
        }

        params.put(PARAMP_COD_UO, uo);
        params.put(PARAMP_COD_ACAO, action);

        return params;
    }
}
