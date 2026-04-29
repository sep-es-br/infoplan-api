package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.planejamentoOrcamentario.*;
import br.gov.es.infoplan.service.PlanejamentoOrcamentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Sistema de Planejamento Orçamentário")
@RestController
@RequestMapping(value = "/planejamentoOrcamentario")
@CrossOrigin(origins = "${frontend.host}")
public class PlanejamentoOrcamentarioController {

    @Autowired
    PlanejamentoOrcamentarioService planejamentoOrcamentarioService;

    @Operation(summary = "Total Previsto", description = "Retorna o valor total previsto no planejamento orçamentário")
    @GetMapping("totalPrevisto")
    public List<SPOTotalPrevistoDTO> getTotalPrevisto(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalPrevistoDTO> totalPrevisto = planejamentoOrcamentarioService
                .getTotalPrevisto(filtro);
        return totalPrevisto;
    }

    @Operation(summary = "Total Autorizado", description = "Retorna o valor total autorizado no planejamento orçamentário")
    @GetMapping("totalAutorizado")
    public List<SPOTotalAutorizadoDTO> getTotalAutorizado(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalAutorizadoDTO> totalPrevisto = planejamentoOrcamentarioService
                .getTotalAutorizado(filtro);
        return totalPrevisto;
    }

    @Operation(summary = "Listar UOs para Filtro", description = "Retorna a lista de Unidades Orçamentárias disponíveis para filtragem")
    @GetMapping("filtroUos")
    public List<SPOFiltroUosDTO> getListUos() {
        List<SPOFiltroUosDTO> list = planejamentoOrcamentarioService.getListUos();
        return list;
    }

    @Operation(summary = "Listar POs para Filtro", description = "Retorna a lista de Programas Orçamentários por ano e UO")
    @GetMapping("filtroPos/{ano}/{codUos}")
    public List<SPOFiltroPosDTO> getListPos(@Validated @PathVariable String[] codUos, @PathVariable String ano) {
        List<SPOFiltroPosDTO> list = planejamentoOrcamentarioService.getListPos(codUos, ano);
        return list;
    }

    @Operation(summary = "Dashboard por UO", description = "Dados consolidados para o dashboard filtrados por Unidade Orçamentária")
    @GetMapping("dashboardUo")
    List<SPODashboardUoDTO> getDashboardUoList(@Validated SPOFiltroDTO filtro) {
        List<SPODashboardUoDTO> listDashboard = planejamentoOrcamentarioService.getListDashboardUo(filtro);
        return listDashboard;
    }

    @Operation(summary = "Dashboard por PO", description = "Dados consolidados para o dashboard filtrados por Programa Orçamentário")
    @GetMapping("dashboardPo")
    List<SPODashboardPoDTO> getDashboardPoList(@Validated SPOFiltroDTO filtro) {
        List<SPODashboardPoDTO> list = planejamentoOrcamentarioService.getListDashboardPo(filtro);
        return list;
    }

    @Operation(summary = "Autorizado por UO", description = "Lista valores autorizados agrupados por Unidade Orçamentária")
    @GetMapping("totalAutorizadoUo")
    List<SPOTotalAutorizadoUoDTO> getTotalAutorizadoUoList(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalAutorizadoUoDTO> list = planejamentoOrcamentarioService.getTotalAutorizadoUoList(filtro);
        return list;
    }

    @Operation(summary = "Autorizado por PO", description = "Lista valores autorizados agrupados por Programa Orçamentário")
    @GetMapping("totalAutorizadoPo")
    List<SPOTotalAutorizadoPoDTO> getTotalAutorizadoPoList(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalAutorizadoPoDTO> list = planejamentoOrcamentarioService.getTotalAutorizadoPoList(filtro);
        return list;
    }

    @Operation(summary = "Total por Ano", description = "Retorna a série histórica de valores por ano dentro do planejamento")
    @GetMapping("totalAno")
    List<SPOTotalAnoDTO> getTotalAno(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalAnoDTO> list = planejamentoOrcamentarioService.getTotalAno(filtro);
        return list;
    }

    @Operation(summary = "Total Ano Sigefes", description = "Busca os valores anuais integrados com o sistema SIGEFES")
    @GetMapping("totalAnoSigefes")
    List<SPOTotalAnoSigefes> getTotalAnoSigefes(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalAnoSigefes> list = planejamentoOrcamentarioService.getTotalAnoSigefes(filtro);
        return list;
    }

}
