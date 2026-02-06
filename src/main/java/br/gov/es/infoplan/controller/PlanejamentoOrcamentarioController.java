package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.planejamentoOrcamentario.*;
import br.gov.es.infoplan.service.PlanejamentoOrcamentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/planejamentoOrcamentario")
@CrossOrigin(origins = "${frontend.host}")
public class PlanejamentoOrcamentarioController {

    @Autowired
    PlanejamentoOrcamentarioService planejamentoOrcamentarioService;

    @GetMapping("totalPrevisto")
    public List<SPOTotalPrevistoDTO> getTotalPrevisto(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalPrevistoDTO> totalPrevisto = planejamentoOrcamentarioService
                .getTotalPrevisto(filtro);
        return totalPrevisto;
    }


    @GetMapping("totalAutorizado")
    public List<SPOTotalAutorizadoDTO> getTotalAutorizado(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalAutorizadoDTO> totalPrevisto = planejamentoOrcamentarioService
                .getTotalAutorizado(filtro);
        return totalPrevisto;
    }


    @GetMapping("filtroUos")
    public List<SPOFiltroUosDTO> getListUos() {
        List<SPOFiltroUosDTO> list = planejamentoOrcamentarioService.getListUos();
        return list;
    }

    @GetMapping("filtroPos/{ano}/{codUos}")
    public List<SPOFiltroPosDTO> getListPos(@Validated @PathVariable String[] codUos, @PathVariable String ano) {
        List<SPOFiltroPosDTO> list = planejamentoOrcamentarioService.getListPos(codUos, ano);
        return list;
    }

    @GetMapping("dashboardUo")
    List<SPODashboardUoDTO> getDashboardUoList(@Validated SPOFiltroDTO filtro) {
        List<SPODashboardUoDTO> listDashboard = planejamentoOrcamentarioService.getListDashboardUo(filtro);
        return listDashboard;
    }

    @GetMapping("dashboardPo")
    List<SPODashboardPoDTO> getDashboardPoList(@Validated SPOFiltroDTO filtro) {
        List<SPODashboardPoDTO> list = planejamentoOrcamentarioService.getListDashboardPo(filtro);
        return list;
    }

    @GetMapping("totalAutorizadoUo")
    List<SPOTotalAutorizadoUoDTO> getTotalAutorizadoUoList(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalAutorizadoUoDTO> list = planejamentoOrcamentarioService.getTotalAutorizadoUoList(filtro);
        return list;
    }

    @GetMapping("totalAutorizadoPo")
    List<SPOTotalAutorizadoPoDTO> getTotalAutorizadoPoList(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalAutorizadoPoDTO> list = planejamentoOrcamentarioService.getTotalAutorizadoPoList(filtro);
        return list;
    }

    @GetMapping("totalAno")
    List<SPOTotalAnoDTO> getTotalAno(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalAnoDTO> list = planejamentoOrcamentarioService.getTotalAno(filtro);
        return list;
    }

    @GetMapping("totalAnoSigefes")
    List<SPOTotalAnoSigefes> getTotalAnoSigefes(@Validated SPOFiltroDTO filtro) {
        List<SPOTotalAnoSigefes> list = planejamentoOrcamentarioService.getTotalAnoSigefes(filtro);
        return list;
    }

}
