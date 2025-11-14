package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.painelOrcamentoDTO.*;
import br.gov.es.infoplan.service.PainelOrcamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${frontend.host}")
@RestController
@RequestMapping(value = "/painelOrcamento")
@RequiredArgsConstructor
public class PainelOrcamentoController {

    @Autowired
    private PainelOrcamentoService painelOrcamentoService;

    @GetMapping("receita-total")
    public ReceitaTotalResponseDTO getReceitaTotal(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {

        PainelOrcamentoRequestDTO dto = new PainelOrcamentoRequestDTO();
        dto.setAno(ano);
        dto.setMes(mes);
        dto.setTipoFonte(tipoFonte);
        ReceitaTotalResponseDTO receita = painelOrcamentoService.getReceitaTotal(dto);
        return receita;
    }

    @GetMapping("receita-categoria")
    public List<ReceitaCategoriaResponseDTO> getReceitaCategoriaList (
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {

        List<ReceitaCategoriaResponseDTO> reponse = painelOrcamentoService.getReceitaCategoria(ano, mes, tipoFonte);
        return reponse;
    }


    @GetMapping("receita-origem")
    public List<ReceitaOrigemResponseDTO> getReceitaOrigemList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaOrigemResponseDTO> responseList = painelOrcamentoService.getReceitaOrigemList(ano, mes, tipoFonte);

        return responseList;
    }

    @GetMapping("receita-impostos")
    public List<ReceitaImpostosResponseDTO> getReceitaImpostoList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaImpostosResponseDTO> responseList = painelOrcamentoService
                .getReceitaImpostoList(ano, mes, tipoFonte);

        return responseList;
    }

    @GetMapping("receita-icms")
    public List<ReceitaICMSResponseDTO> getReceitaICMSList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaICMSResponseDTO> responseList = painelOrcamentoService
                .getReceitaICMSList(ano, mes, tipoFonte);

        return responseList;
    }

    @GetMapping("receita-participacao")
    public List<ReceitaParticipacaoResponseDTO> getReceitaParticipacaoList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaParticipacaoResponseDTO> responseList = painelOrcamentoService
                .getReceitaParticipacaoList(ano, mes, tipoFonte);

        return responseList;
    }

    @GetMapping("receita-despesas-gnd")
    public List<ReceitaDespesaGNDResponseDTO> getReceitaDespesaGNDList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaDespesaGNDResponseDTO> responseList = painelOrcamentoService
                .getReceitaDespesaGNDList(ano, mes, tipoFonte);

        return responseList;
    }



    @GetMapping("receita-despesas-gnd-total")
    public List<ReceitaDespesaGNDTotalResponseDTO> getReceitaDespesaGNDTotalList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaDespesaGNDTotalResponseDTO> responseList = painelOrcamentoService
                .getReceitaDespesaGNDTotalList(ano, mes, tipoFonte);

        return responseList;
    }


    @GetMapping("receita-transferencia-corrente")
    public List<ReceitaTransferenciaCorrenteResponseDTO> getReceitaTransferenciaCorrenteList(
            @RequestParam Long ano,
            @RequestParam int[] mes,
            @RequestParam int[] tipoFonte
    ) {
        List<ReceitaTransferenciaCorrenteResponseDTO> response = painelOrcamentoService
                .getReceitaTransferenciaCorrente(ano, mes, tipoFonte);

        return response;
    }

}
