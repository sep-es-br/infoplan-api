package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.execucaoOrcamentariaDTO.*;
import br.gov.es.infoplan.dto.strategicProject.StrategicProjectTimestampDto;
import br.gov.es.infoplan.service.ExecucaoOrcamentariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
            @Validated ExecucaoOrcamentariaRequestDTO requestDTO
    ) {
        ReceitaTotalResponseDTO receita = execucaoOrcamentariaService.getReceitaTotal(requestDTO);
        return receita;
    }

    @GetMapping("receita-categoria")
    public List<ReceitaCategoriaResponseDTO> getReceitaCategoriaList (
            @Validated ExecucaoOrcamentariaRequestDTO requestDTO
    ) {

        List<ReceitaCategoriaResponseDTO> reponse = execucaoOrcamentariaService.getReceitaCategoria(requestDTO);
        return reponse;
    }


    @GetMapping("receita-origem")
    public List<ReceitaOrigemResponseDTO> getReceitaOrigemList(
            @Validated ExecucaoOrcamentariaRequestDTO requestDTO
    ) {
        List<ReceitaOrigemResponseDTO> responseList = execucaoOrcamentariaService.getReceitaOrigemList(requestDTO);

        return responseList;
    }

    @GetMapping("receita-impostos")
    public List<ReceitaImpostosResponseDTO> getReceitaImpostoList(
            @Validated ExecucaoOrcamentariaRequestDTO requestDTO
    ){
        List<ReceitaImpostosResponseDTO> responseList = execucaoOrcamentariaService
                .getReceitaImpostoList(requestDTO);

        return responseList;
    }

    @GetMapping("receita-icms")
    public List<ReceitaICMSResponseDTO> getReceitaICMSList(
            @Validated ExecucaoOrcamentariaRequestDTO requestDTO
    ) {
        List<ReceitaICMSResponseDTO> responseList = execucaoOrcamentariaService
                .getReceitaICMSList(requestDTO);

        return responseList;
    }

    @GetMapping("receita-participacao")
    public List<ReceitaParticipacaoResponseDTO> getReceitaParticipacaoList(
            @Validated ExecucaoOrcamentariaRequestDTO requestDTO
    ) {
        List<ReceitaParticipacaoResponseDTO> responseList = execucaoOrcamentariaService
                .getReceitaParticipacaoList(requestDTO);

        return responseList;
    }

    @GetMapping("receita-despesas-gnd")
    public List<ReceitaDespesaGNDResponseDTO> getReceitaDespesaGNDList(
            @Validated RequestPoderExecutivoDTO requestDTO
    ) {
        List<ReceitaDespesaGNDResponseDTO> responseList = execucaoOrcamentariaService
                .getReceitaDespesaGNDList(requestDTO);
        return responseList;
    }



    @GetMapping("receita-despesas-gnd-total")
    public List<ReceitaDespesaGNDTotalResponseDTO> getReceitaDespesaGNDTotalList(
            @Validated RequestPoderExecutivoDTO requestDTO
    ) {
        List<ReceitaDespesaGNDTotalResponseDTO> responseList = execucaoOrcamentariaService
                .getReceitaDespesaGNDTotalList(requestDTO);
        return responseList;
    }


    @GetMapping("receita-transferencia-corrente")
    public List<ReceitaTransferenciaCorrenteResponseDTO> getReceitaTransferenciaCorrenteList(
            @Validated ExecucaoOrcamentariaRequestDTO requestDTO
    ) {
        List<ReceitaTransferenciaCorrenteResponseDTO> response = execucaoOrcamentariaService
                .getReceitaTransferenciaCorrente(requestDTO);
        return response;
    }

}
