package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.planejamentoOrcamentario.*;
import br.gov.es.infoplan.service.PlanejamentoOrcamentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/planejamentoOrcamentario")
@CrossOrigin(origins = "${frontend.host}")
public class PlanejamentoOrcamentarioController {

    @Autowired
    PlanejamentoOrcamentarioService planejamentoOrcamentarioService;

    @GetMapping("totalPrevisto")
    public List<SPOTotalPrevistoDTO> getTotalPrevisto(
            @RequestParam Long ano,
            @RequestParam int[] tipoFonte,
            @RequestParam int[] uo,
            @RequestParam int[] po,
            @RequestParam int[] gnd
    ) {

        SPOTotalPrevistoRequestDTO dto = new SPOTotalPrevistoRequestDTO();
        dto.setUo(uo);
        dto.setPo(po);
        dto.setGnd(gnd);
        dto.setAno(ano);
        dto.setTipoFonte(tipoFonte);

        List<SPOTotalPrevistoDTO> totalPrevisto = planejamentoOrcamentarioService
                .getTotalPrevisto(dto);
        return totalPrevisto;
    }


    @GetMapping("totalAutorizado")
    public List<SPOTotalAutorizadoDTO> getTotalPrevisto(
            @RequestParam Long ano,
            @RequestParam int[] tipoFonte,
            @RequestParam int[] mes,
            @RequestParam int[] uo,
            @RequestParam int[] po,
            @RequestParam int[] gnd
    ) {

        SPOTotalAutorizadoRequestDTO dto = new SPOTotalAutorizadoRequestDTO();
        dto.setUo(uo);
        dto.setPo(po);
        dto.setGnd(gnd);
        dto.setAno(ano);
        dto.setMes(mes);
        dto.setTipoFonte(tipoFonte);

        List<SPOTotalAutorizadoDTO> totalPrevisto = planejamentoOrcamentarioService
                .getTotalAutorizado(dto);
        return totalPrevisto;
    }


    @GetMapping("filtroUos")
    public List<SPOFiltroUosDTO> getListUos() {
        List<SPOFiltroUosDTO> list = planejamentoOrcamentarioService.getListUos();
        return list;
    }

    @GetMapping("filtroPos/{ano}/{codUos}")
    public List<SPOFiltroPosDTO> getListPos(@PathVariable String[] codUos, @PathVariable String ano) {
        List<SPOFiltroPosDTO> list = planejamentoOrcamentarioService.getListPos(codUos, ano);
        return list;
    }
}
