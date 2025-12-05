package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.execucaoOrcamentariaDTO.*;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectTimestampDto;
import br.gov.es.infoplan.service.ExecucaoOrcamentariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${frontend.host}")
@RestController
@RequestMapping(value = "/execucaoOrcamentaria")
@RequiredArgsConstructor
public class ExecucaoOrcamentariaController {

    @Autowired
    private ExecucaoOrcamentariaService execucaoOrcamentariaService;

    @GetMapping("receita-total")
    public ReceitaTotalResponseDTO getReceitaTotal(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {

        ExecucaoOrcamentariaRequestDTO dto = new ExecucaoOrcamentariaRequestDTO();
        dto.setAno(ano);
        dto.setMes(mes);
        dto.setTipoFonte(tipoFonte);
        ReceitaTotalResponseDTO receita = execucaoOrcamentariaService.getReceitaTotal(dto);
        return receita;
    }

    @GetMapping("receita-categoria")
    public List<ReceitaCategoriaResponseDTO> getReceitaCategoriaList (
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {

        List<ReceitaCategoriaResponseDTO> reponse = execucaoOrcamentariaService.getReceitaCategoria(ano, mes, tipoFonte);
        return reponse;
    }


    @GetMapping("receita-origem")
    public List<ReceitaOrigemResponseDTO> getReceitaOrigemList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaOrigemResponseDTO> responseList = execucaoOrcamentariaService.getReceitaOrigemList(ano, mes, tipoFonte);

        return responseList;
    }

    @GetMapping("receita-impostos")
    public List<ReceitaImpostosResponseDTO> getReceitaImpostoList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaImpostosResponseDTO> responseList = execucaoOrcamentariaService
                .getReceitaImpostoList(ano, mes, tipoFonte);

        return responseList;
    }

    @GetMapping("receita-icms")
    public List<ReceitaICMSResponseDTO> getReceitaICMSList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaICMSResponseDTO> responseList = execucaoOrcamentariaService
                .getReceitaICMSList(ano, mes, tipoFonte);

        return responseList;
    }

    @GetMapping("receita-participacao")
    public List<ReceitaParticipacaoResponseDTO> getReceitaParticipacaoList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaParticipacaoResponseDTO> responseList = execucaoOrcamentariaService
                .getReceitaParticipacaoList(ano, mes, tipoFonte);

        return responseList;
    }

    @GetMapping("receita-despesas-gnd")
    public List<ReceitaDespesaGNDResponseDTO> getReceitaDespesaGNDList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaDespesaGNDResponseDTO> responseList = execucaoOrcamentariaService
                .getReceitaDespesaGNDList(ano, mes, tipoFonte);

        return responseList;
    }



    @GetMapping("receita-despesas-gnd-total")
    public List<ReceitaDespesaGNDTotalResponseDTO> getReceitaDespesaGNDTotalList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaDespesaGNDTotalResponseDTO> responseList = execucaoOrcamentariaService
                .getReceitaDespesaGNDTotalList(ano, mes, tipoFonte);

        return responseList;
    }


    @GetMapping("receita-transferencia-corrente")
    public List<ReceitaTransferenciaCorrenteResponseDTO> getReceitaTransferenciaCorrenteList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaTransferenciaCorrenteResponseDTO> response = execucaoOrcamentariaService
                .getReceitaTransferenciaCorrente(ano, mes, tipoFonte);

        return response;
    }

}
