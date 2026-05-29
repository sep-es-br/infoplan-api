package br.gov.es.infoplan.service;

import br.gov.es.infoplan.config.pentahoBi.PentahoBiProperties;
import br.gov.es.infoplan.dto.painelObras.request.PainelObrasRequestDTO;
import br.gov.es.infoplan.dto.painelObras.response.*;
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
        List<FiltroOrgaoResponseDTO> listOrgao = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_FILTRO_ORGAO,
                pmoPath,
                null,
                rs -> new FiltroOrgaoResponseDTO(
                        rs.get("orgaoId").asLong(),
                        rs.get("nome").asText()
                )
        );

        if(listOrgao.isEmpty()) return null;

        return listOrgao;
    }

    public List<FiltroMunicipioResponseDTO> filtroListaMunicipio(String orgao) {
        List<FiltroMunicipioResponseDTO> listMunicipio = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_FILTRO_MUNICIPIO,
                pmoPath,
                params(orgao),
                rs -> new FiltroMunicipioResponseDTO(
                        rs.get("id").asLong(),
                        rs.get("nome").asText()
                )
        );

        if(listMunicipio.isEmpty()) return null;

        return listMunicipio;
    }


    public List<FiltroStatusResponseDTO> filtroListaStatus(String orgao, String municipio) {
        List<FiltroStatusResponseDTO> listStatus = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_FILTRO_STATUS,
                pmoPath,
                params(orgao, municipio),
                rs -> new FiltroStatusResponseDTO(
                        rs.get("id").asLong(),
                        rs.get("fase").asText()
                )
        );

        if(listStatus.isEmpty()) return null;

        return listStatus;
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

        if(total.isEmpty()) return null;

        return total.get(0);
    }


    public TotalProjetosResponseDTO totalProjetos(PainelObrasRequestDTO request) {
        List<Long> total = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_PROJETOS,
                pmoPath,
                params(request),
                rs -> rs.get("total_projetos").asLong()
        );

        if (total == null || total.isEmpty() || total.get(0) == null) {
            return new TotalProjetosResponseDTO(0L);
        }

        return new TotalProjetosResponseDTO(total.get(0));
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

        if(contagemEntregas == null || contagemEntregas.isEmpty() || contagemEntregas.get(0) == null) {
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

        if(contagemPE == null || contagemPE.isEmpty() || contagemPE.get(0) == null) {
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
                        ApiUtils.parseBigDecimal(rs, "total_realizado")
                )
        );

        if(totalRealizado == null || totalRealizado.isEmpty() || totalRealizado.get(0) == null) {
            return new TotalRealizadoResponseDTO(ApiUtils.parseBigDecimal(new HashMap<>(), "total_realizado"));
        }

        return totalRealizado.get(0);
    }

    public TotalPlanejadoResponseDTO totalPlanejado(PainelObrasRequestDTO request) {
        List<TotalPlanejadoResponseDTO> totalPlanejado = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_TOTAL_PLANEJADO,
                pmoPath,
                params(request),
                rs -> new TotalPlanejadoResponseDTO(
                        ApiUtils.parseBigDecimal(rs, "total_planejado")
                )
        );

        if(totalPlanejado == null || totalPlanejado.isEmpty() || totalPlanejado.get(0) == null) {
            return new TotalPlanejadoResponseDTO(ApiUtils.parseBigDecimal(new HashMap<>(), "total_planejado"));
        }

        return totalPlanejado.get(0);
    }


    public List<QuantidadeStatusResponseDTO> quantidadePorStatus(PainelObrasRequestDTO request) {
        List<QuantidadeStatusResponseDTO> quantidadePorStatus = apiUtils.executePentahoQuery(
                PAINEL_OBRAS_QUANTIDADE_POR_STATUS,
                pmoPath,
                params(request),
                rs -> new QuantidadeStatusResponseDTO(
                        rs.get("quantidade_entregas").asLong(),
                        rs.get("status").asText()
                )
        );

        if(quantidadePorStatus.isEmpty()) return null;

        return quantidadePorStatus;
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
