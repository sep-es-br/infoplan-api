package br.gov.es.infoplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.es.infoplan.dto.NomeValorObject;
import br.gov.es.infoplan.dto.capitacao.CapitacaoFilter;
import br.gov.es.infoplan.service.CapitationService;
import lombok.RequiredArgsConstructor;


@CrossOrigin(origins = "${frontend.host}")
@RestController
@RequestMapping("/capitation")
@RequiredArgsConstructor
public class CapitacaoController {

    private final CapitationService service;

    @Value("${frontend.host}")
    private String frontHost;

    @GetMapping("/programAmmount")
    public Double getProgramAmmount(@RequestParam String filterJson) {
        
        return service.getProgramTotal(filterJson);
    }

    @GetMapping("/projectAmmount")
    public Double getProjectAmmount(@RequestParam String filterJson) {
        
        return service.getProjectTotal(filterJson);
    }

    @GetMapping("/valores-estimado")
    public List<NomeValorObject> getEstimatedValues(@RequestParam String tipo, @RequestParam String filterJson) {
        
        switch (tipo) {
            case "microregion":
                return service.getAllByMicroregiao(filterJson); 
            case "city":
                return service.getAllByCidade(filterJson);
        
            default:
                return List.of();
        }
        
    }

    @GetMapping("/valores-por")
    public List<NomeValorObject> getValuesBy(@RequestParam String tipo, @RequestParam String filterJson) {
        switch (tipo) {
            case "project":
                return service.getAllByProjeto(filterJson); 
            case "program":
                return service.getAllByPrograma(filterJson);
        
            default:
                return List.of();
        }
        
    }

    @GetMapping("/valores-estimado-secretaria")
    public List<NomeValorObject> getEstimatedValuesSecretary(@RequestParam String filterJson) {
        return service.getAllBySecretaria(filterJson);
        
    }
    
    
}
