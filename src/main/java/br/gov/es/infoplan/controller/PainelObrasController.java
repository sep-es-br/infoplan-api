package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.painelObras.request.PainelObrasRequestDTO;
import br.gov.es.infoplan.dto.painelObras.response.*;
import br.gov.es.infoplan.service.PainelObrasService;
import feign.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Painel de Obras", description = "Endpoints relacionados ao painel de obras")
@RestController
@CrossOrigin(origins = "${frontend.host}")
@RequestMapping("/painel-obras")
public class PainelObrasController {

    @Autowired
    private PainelObrasService painelObrasService;

    @Operation(summary = "Lista de órgãos", description = "Busca os órgãos relacionados às obras")
    @GetMapping("/filtros/orgaos")
    public ResponseEntity<List<FiltroOrgaoResponseDTO>> filtroListaOrgao() {
        List<FiltroOrgaoResponseDTO> list = painelObrasService.filtroListaOrgao();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lista de municípios", description = "Busca os municípios relacionados às obras de um órgão específico")
    @GetMapping("/filtros/municipios/{orgao}")
    public ResponseEntity<List<FiltroMunicipioResponseDTO>> filtroListaMunicipio(@PathVariable String orgao) {
        List<FiltroMunicipioResponseDTO> list = painelObrasService.filtroListaMunicipio(orgao);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lista de status", description = "Busca os status relacionados às obras de um órgão e município específicos")
    @GetMapping("/filtros/status/{orgao}/{municipio}")
    public ResponseEntity<List<FiltroStatusResponseDTO>> filtroListaStatus(
            @PathVariable String orgao,
            @PathVariable String municipio
    ) {
        List<FiltroStatusResponseDTO> list = painelObrasService.filtroListaStatus(orgao, municipio);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Total de obras por programa", description = "Busca o total de obras agrupadas por programa, com base nos filtros aplicados")
    @GetMapping("total-programa")
    public ResponseEntity<TotalProgramaResponseDTO> totalPrograma(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        TotalProgramaResponseDTO total = painelObrasService.totalPrograma(request);
        return ResponseEntity.ok(total);
    }

    @Operation(summary = "Total de obras por projeto", description = "Busca o total de obras agrupadas por projeto, com base nos filtros aplicados")
    @GetMapping("total-projetos")
    public ResponseEntity<TotalProjetosResponseDTO> totalProjetos(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        TotalProjetosResponseDTO totalProjeto = painelObrasService.totalProjetos(request);
        return ResponseEntity.ok(totalProjeto);
    }

    @Operation(summary = "Total de obras por PE", description = "Busca o total de obras agrupadas por PE, com base nos filtros aplicados")
    @GetMapping("total-contagem-pe")
    public ResponseEntity<TotalContagemPEResponseDTO> totalContagemPE(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        TotalContagemPEResponseDTO totalContagemPE = painelObrasService.totalContagemPE(request);
        return ResponseEntity.ok(totalContagemPE);
    }

    @Operation(summary = "Total de obras por entrega", description = "Busca o total de obras agrupadas por entrega, com base nos filtros aplicados")
    @GetMapping("total-contagem-entregas")
    public ResponseEntity<TotalContagemEntregasResponseDTO> totalContagemEntregas(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        TotalContagemEntregasResponseDTO totalContagemEntregas = painelObrasService.totalContagemEntrega(request);
        return ResponseEntity.ok(totalContagemEntregas);
    }

    @Operation(summary = "Total planejado", description = "Busca o total planejado das obras, com base nos filtros aplicados")
    @GetMapping("total-planejado")
    public ResponseEntity<TotalPlanejadoResponseDTO> totalPlanejado(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        TotalPlanejadoResponseDTO totalPlanejado = painelObrasService.totalPlanejado(request);
        return ResponseEntity.ok(totalPlanejado);
    }

    @Operation(summary = "Total realizado", description = "Busca o total realizado das obras, com base nos filtros aplicados")
    @GetMapping("total-realizado")
    public ResponseEntity<TotalRealizadoResponseDTO> totalRealizado(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        TotalRealizadoResponseDTO totalRealizado = painelObrasService.totalRealizado(request);
        return ResponseEntity.ok(totalRealizado);
    }

    @Operation(summary = "Quantidade de obras por status", description = "Busca a quantidade de obras agrupadas por status, com base nos filtros aplicados")
    @GetMapping("quantidade-por-status")
    public ResponseEntity<List<QuantidadeStatusResponseDTO>> quantidadePorStatus(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        List<QuantidadeStatusResponseDTO> quantidadePorStatus = painelObrasService.quantidadePorStatus(request);
        return ResponseEntity.ok(quantidadePorStatus);
    }

    @Operation(summary = "Total de entregas por ano e status", description = "Busca o total de entregas agrupadas por ano e status, com base nos filtros aplicados")
    @GetMapping("total-entregas-por-ano-e-status")
    public ResponseEntity<List<TotalEntregasAnoStatusResponseDTO>> totalEntregasPorAnoEStatus(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        List<TotalEntregasAnoStatusResponseDTO> totalEntregasPorAnoEStatus = painelObrasService.totalEntregasPorAnoEStatus(request);
        return ResponseEntity.ok(totalEntregasPorAnoEStatus);
    }


    @Operation(summary = "Total de entregas por órgão", description = "Busca o total de entregas agrupadas por órgão, com base nos filtros aplicados")
    @GetMapping("total-entregas-por-orgao")
    public ResponseEntity<List<TotalEntregasOrgaoResponseDTO>> totalEntregasPorOrgao(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        List<TotalEntregasOrgaoResponseDTO> totalEntregasPorOrgao = painelObrasService.totalEntregasPorOrgao(request);
        return ResponseEntity.ok(totalEntregasPorOrgao);
    }

    @Operation(summary = "Total de entregas por órgão de execução", description = "Busca o total de entregas agrupadas por órgão de execução, com base nos filtros aplicados")
    @GetMapping("total-entregas-orgao-execucao")
    public ResponseEntity<List<TotalEntregasOrgaoExeResponseDTO>> totalEntregasPorOrgaoExecucao(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        List<TotalEntregasOrgaoExeResponseDTO> totalEntregasPorOrgaoExecucao = painelObrasService.totalEntregasPorOrgaoExecucao(request);
        return ResponseEntity.ok(totalEntregasPorOrgaoExecucao);
    }

    @GetMapping("total-entregas-por-municipio-status")
    @Operation(summary = "Total de entregas por município e status", description = "Busca o total de entregas agrupadas por município e status, com base nos filtros aplicados")
    public ResponseEntity<List<TotalEntregasMunicipioStatusResponseDTO>> totalEntregasPorMunicipioStatus(
            @Validated @ModelAttribute PainelObrasRequestDTO request) {
        List<TotalEntregasMunicipioStatusResponseDTO> totalEntregasPorMunicipioStatus = painelObrasService.totalEntregasPorMunicipioStatus(request);
        return ResponseEntity.ok(totalEntregasPorMunicipioStatus);
    }

    @Operation(summary = "Número de entregas por status", description = "Busca o número de entregas agrupadas por status, com base nos filtros aplicados")
    @GetMapping("numero-entregas-por-status")
    public ResponseEntity<List<NumeroEntregasStatusResponseDTO>> totalEntregasPorProjeto(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        List<NumeroEntregasStatusResponseDTO> totalEntregasPorProjeto = painelObrasService.totalEntregasPorProjeto(request);
        return ResponseEntity.ok(totalEntregasPorProjeto);
    }

    @GetMapping("quantidade-maior-entrega")
    public ResponseEntity<List<QuantidadeMaiorEntregaResponseDTO>> quantidadeMaiorEntrega(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        List<QuantidadeMaiorEntregaResponseDTO> quantidadeMaiorEntrega = painelObrasService.quantidadeMaiorEntrega(request);

        return ResponseEntity.ok(quantidadeMaiorEntrega);
    }

    @GetMapping("quantidade-maior-prevista")
    public ResponseEntity<List<QuantidadeMaiorPrevistaResponseDTO>> quantidadeMaiorPrevista(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        List<QuantidadeMaiorPrevistaResponseDTO> quantidadeMaiorPrevista = painelObrasService.quantidadeMaiorPrevista(request);
        return ResponseEntity.ok(quantidadeMaiorPrevista);
    }

    @GetMapping("total-entrega-por-mes")
    public ResponseEntity<List<TotalEntregaPorMesResponseDTO>> totalEntregaPorMes(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        List<TotalEntregaPorMesResponseDTO> entregaPorMesList = painelObrasService.totalEntregaPorMes(request);
        return ResponseEntity.ok(entregaPorMesList);
    }

    @GetMapping("total-totalizador")
    public ResponseEntity<TotalizadorResponseDTO> totalTotalizador(
            @Validated @ModelAttribute PainelObrasRequestDTO request
    ) {
        TotalizadorResponseDTO totalizador = painelObrasService.totalTotalizador(request);
        return ResponseEntity.ok(totalizador);
    }

}
