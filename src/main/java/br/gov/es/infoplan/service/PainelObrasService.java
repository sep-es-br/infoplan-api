package br.gov.es.infoplan.service;

import br.gov.es.infoplan.config.pentahoBi.PentahoBiProperties;
import br.gov.es.infoplan.dto.painelObras.request.PainelObrasRequestDTO;
import br.gov.es.infoplan.dto.painelObras.response.*;
import br.gov.es.infoplan.utils.ApiUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.gov.es.infoplan.config.pentahoBi.PentahoBiConfigKeys.*;
import static br.gov.es.infoplan.config.pentahoBi.PentahoBiConfigParams.*;

@Service
@Slf4j
public class PainelObrasService {

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private PentahoBiProperties properties;

    private String pmoPath;

    @PostConstruct
    public void init() {
        this.pmoPath = properties.getPainelObras().getPath();
        log.info("PMO Path initialized:: {} ", pmoPath);
    }


    public List<FiltroOrgaoResponseDTO> filtroListaOrgao() {
       return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_FILTRO_ORGAO,
                pmoPath,
                null,
                rs -> new FiltroOrgaoResponseDTO(
                        rs.get("orgaoId").asLong(),
                        rs.get("nome").asText()
                )
        );
    }

    public List<FiltroMunicipioResponseDTO> filtroListaMunicipio(String orgao) {
        return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_FILTRO_MUNICIPIO,
                pmoPath,
                params(orgao),
                rs -> new FiltroMunicipioResponseDTO(
                        rs.get("id").asLong(),
                        rs.get("nome").asText()
                )
        );
    }


    public List<FiltroStatusResponseDTO> filtroListaStatus(String orgao, String municipio) {
        return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_FILTRO_STATUS,
                pmoPath,
                params(orgao, municipio),
                rs -> new FiltroStatusResponseDTO(
                        rs.get("id").asLong(),
                        rs.get("fase").asText()
                )
        );
    }


    public TotalProgramaResponseDTO totalPrograma(PainelObrasRequestDTO request) {
        List<TotalProgramaResponseDTO> total = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_PROGRMAS,
                pmoPath,
                params(request),
                rs -> new TotalProgramaResponseDTO(
                        rs.get("total_programas").asLong()
                )
        );

        if (total.isEmpty()) {
            return null;
        }

        return total.get(0);
    }


    public TotalProjetosResponseDTO totalProjetos(PainelObrasRequestDTO request) {
        List<Long> total = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_PROJETOS,
                pmoPath,
                params(request),
                rs -> rs.get("total_projetos").asLong()
        );


        if (total.isEmpty()) {
            return null;
        }

        return total.get(0) == null ? new TotalProjetosResponseDTO(0L) : new TotalProjetosResponseDTO(total.get(0));
    }


    public TotalContagemEntregasResponseDTO totalContagemEntrega(PainelObrasRequestDTO request) {
        List<TotalContagemEntregasResponseDTO> contagemEntregas = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_CONTAGEM_ENTREGAS,
                pmoPath,
                params(request),
                rs -> new TotalContagemEntregasResponseDTO(
                        rs.get("total_entregas").asLong()
                )
        );

        if (contagemEntregas == null || contagemEntregas.isEmpty() || contagemEntregas.get(0) == null) {
            return new TotalContagemEntregasResponseDTO(0L);
        }

        return contagemEntregas.get(0);
    }

    public TotalContagemPEResponseDTO totalContagemPE(PainelObrasRequestDTO request) {
        List<TotalContagemPEResponseDTO> contagemPE = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_CONTAGEM_PE,
                pmoPath,
                params(request),
                rs -> new TotalContagemPEResponseDTO(
                        rs.get("contagem_pe").asLong()
                )
        );

        if (contagemPE == null || contagemPE.isEmpty() || contagemPE.get(0) == null) {
            return new TotalContagemPEResponseDTO(0L);
        }

        return contagemPE.get(0);
    }

    public TotalRealizadoResponseDTO totalRealizado(PainelObrasRequestDTO request) {
        List<TotalRealizadoResponseDTO> totalRealizado = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_REALIZADO,
                pmoPath,
                params(request),
                rs -> new TotalRealizadoResponseDTO(
                        new BigDecimal(rs.get("total_realizado").asDouble(2)).setScale(2, BigDecimal.ROUND_HALF_UP)
                )
        );

        if (totalRealizado.isEmpty()) {
            return null;
        }

        return totalRealizado.get(0);
    }

    public TotalPlanejadoResponseDTO totalPlanejado(PainelObrasRequestDTO request) {
        List<TotalPlanejadoResponseDTO> totalPlanejado = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_PLANEJADO,
                pmoPath,
                params(request),
                rs -> new TotalPlanejadoResponseDTO(
                        new BigDecimal(rs.get("total_planejado").asDouble(2)).setScale(2, BigDecimal.ROUND_HALF_UP)
                )
        );

        if (totalPlanejado.isEmpty()) {
            return null;
        }

        return totalPlanejado.get(0);
    }


    public List<QuantidadeStatusResponseDTO> quantidadePorStatus(PainelObrasRequestDTO request) {
        return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_QUANTIDADE_POR_STATUS,
                pmoPath,
                params(request),
                rs -> new QuantidadeStatusResponseDTO(
                        rs.get("quantidade_entregas").asLong(),
                        rs.get("status").asText()
                )
        );
    }


    public List<TotalEntregasAnoStatusResponseDTO> totalEntregasPorAnoEStatus(PainelObrasRequestDTO request) {
        return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_ENTREGAS_POR_ANO_E_STATUS,
                pmoPath,
                params(request),
                rs -> new TotalEntregasAnoStatusResponseDTO(
                        rs.get("ano").asText(),
                        rs.get("status").asText(),
                        ApiUtils.parseBigDecimal(rs, "planejado"),
                        ApiUtils.parseBigDecimal(rs, "realizado")
                )
        );
    }

    public List<TotalEntregasOrgaoResponseDTO> totalEntregasPorOrgao(PainelObrasRequestDTO request) {
       return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_ENTREGAS_ORGAO,
                pmoPath,
                params(request),
                rs -> new TotalEntregasOrgaoResponseDTO(
                        rs.get("orgao").asText(),
                        new BigDecimal(
                                rs.get("planejado").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP),
                        new BigDecimal(
                                rs.get("realizado").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP),
                        rs.get("quantidade_entregas").asLong()
                )
        );
    }

    public List<TotalEntregasOrgaoExeResponseDTO> totalEntregasPorOrgaoExecucao(PainelObrasRequestDTO request) {
        return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_ENTREGAS_ORGAO_EXECEUCAO,
                pmoPath,
                params(request),
                rs -> new TotalEntregasOrgaoExeResponseDTO(
                        rs.get("orgao").asText(),
                        rs.get("quantidade_entregas").asLong(),
                        new BigDecimal(
                                rs.get("planejado").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP),
                        new BigDecimal(
                                rs.get("realizado").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP)
                )
        );

    }

    public List<TotalEntregasMunicipioStatusResponseDTO> totalEntregasPorMunicipioStatus(PainelObrasRequestDTO request) {
        return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_ENTREGAS_MUNICIPIO_STATUS,
                pmoPath,
                params(request),
                rs -> new TotalEntregasMunicipioStatusResponseDTO(
                        rs.get("municipio").asText(),
                        rs.get("status").asText(),
                        new BigDecimal(
                                rs.get("planejado").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP),
                        new BigDecimal(
                                rs.get("realizado").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP)
                )
        );
    }

    public List<NumeroEntregasStatusResponseDTO> totalEntregasPorProjeto(PainelObrasRequestDTO request) {
        return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_NUMERO_ENTREGAS_POR_STATUS,
                pmoPath,
                params(request),
                rs -> new NumeroEntregasStatusResponseDTO(
                        rs.get("municipio").asText(),
                        rs.get("status").asText(),
                        rs.get("quantidade_entregas").asLong()
                )
        );
    }


    public List<QuantidadeMaiorEntregaResponseDTO> quantidadeMaiorEntrega(PainelObrasRequestDTO request) {
        return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_QUANTIDADE_MAIOR_MUNICIPIO_CARTEIRA,
                pmoPath,
                params(request),
                rs -> new QuantidadeMaiorEntregaResponseDTO(
                        rs.get("municipio").asText(),
                        new BigDecimal(
                                rs.get("planejado").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP),
                        rs.get("quantidade_entrega").asLong(),
                        rs.get("nome_maior_entrega").asText(),
                        rs.get("orgao").asText(),
                        rs.get("data").asText(),
                        new BigDecimal(
                                rs.get("total_maior_municipio").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP)
                )
        );
    }

    public List<QuantidadeMaiorPrevistaResponseDTO> quantidadeMaiorPrevista(PainelObrasRequestDTO request) {
       return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_QUANTIDADE_MAIOR_PREVISTA_CARTEIRA,
                pmoPath,
                params(request),
                rs -> new QuantidadeMaiorPrevistaResponseDTO(
                        rs.get("orgao").asText(),
                        new BigDecimal(
                                rs.get("planejado").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP),
                        rs.get("quantidade_entregas").asLong(),
                        rs.get("nome_maior_entrega").asText(),
                        rs.get("municipio").asText(),
                        rs.get("data_conclusao").asText(),
                        new BigDecimal(
                                rs.get("maior_total_orgao").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP)
                )
        );
    }

    public List<TotalEntregaPorMesResponseDTO> totalEntregaPorMes(PainelObrasRequestDTO request) {
        return apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_ENTREGAS_POR_MES_CARTEIRA,
                pmoPath,
                params(request),
                rs -> new TotalEntregaPorMesResponseDTO(
                        rs.get("mes_nome").asText(),
                        new BigDecimal(
                                rs.get("planejado").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP),
                        rs.get("entrega_nome").asText(),
                        rs.get("municipio").asText(),
                        rs.get("quantidade_entregas").asLong(),
                            new BigDecimal(
                                rs.get("maior_valor_no_mes").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP),
                        rs.get("data_conclusao_maior_entrega").asText(),
                        new BigDecimal(
                                rs.get("valor_medio_por_acao").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP)
                )
        );
    }

    public TotalizadorResponseDTO totalTotalizador(PainelObrasRequestDTO request) {
        List<TotalizadorResponseDTO> listTotal = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTALIZADORES,
                pmoPath,
                params(request),
                rs -> new TotalizadorResponseDTO(
                        rs.get("pfId").asLong(),
                        rs.get("qdeEntregas").asLong(),
                        rs.get("qdeProjetos").asLong(),
                        rs.get("qdeProgramas").asLong(),
                        new BigDecimal(
                                rs.get("totalPrevisto").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP),
                        new BigDecimal(
                                rs.get("totalRealizado").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP),
                        new BigDecimal(
                                rs.get("totalProgramado").asDouble(2)
                        ).setScale(2, BigDecimal.ROUND_HALF_UP),
                        rs.get("totalEntregasPE").asLong()
                )
        );


        if(listTotal.isEmpty()) {
            return null;
        }

        return listTotal.get(0);
    }

    private Map<String, Object> params(String orgao) {
        Map<String, Object> params = new HashMap<>();

        params.put(PARAMP_ORGAO, orgao);

        return params;
    }

    private Map<String, Object> params(String orgao, String municipio) {
        Map<String, Object> params = new HashMap<>();

        params.put(PARAMP_ORGAO, orgao);
        params.put(PARAMP_MUNICIPIO, municipio);
        return params;
    }


    private Map<String, Object> params(PainelObrasRequestDTO request) {
        Map<String, Object> params = new HashMap<>();

        params.put(PARAMP_ORGAO, request.orgao());
        params.put(PARAMP_MUNICIPIO, request.municipio());
        params.put(PARAMP_STATUS, request.status());

        return params;
    }
}
