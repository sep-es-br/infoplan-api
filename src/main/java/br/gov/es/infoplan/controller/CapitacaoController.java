package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.NomeValorObject;
import br.gov.es.infoplan.service.CapitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "${frontend.host}")
@RestController
@RequestMapping("/capitation")
@RequiredArgsConstructor
public class CapitacaoController {

    private final int PROGRAMAS = 1;
    private final int PROJETO = 2;
    private final int MICROREGIAO = 3;
    private final int CIDADES = 4;
    private final int SECRETARIA = 5;
    

    private final CapitationService service;

    @Value("${frontend.host}")
    private String frontHost;

    @GetMapping("/programAmmount2")
    public Double getProgramAmmount(@RequestParam int ano, @RequestParam Map<String, String> filtro) {
        List<Map<String, String>> resultset = service.consultarTodosValores(ano, filtro);
        
        double total = 0;
       for (Map<String,String> registro : resultset) {
            total += Double.parseDouble(registro.get("valor"));
       }  

        //return total;
        
        return service.getProjectTotal();
    }

    @GetMapping("/programAmmount")
    public Double getProgramAmmount() {
        
        return service.getProjectTotal();
    }

    @GetMapping("/projectAmmount2")
    public Double getProjectAmmount(@RequestParam int ano, @RequestParam Map<String, String> filtro) {
        List<Map<String, String>> resultset = service.consultarTodosValores(ano, filtro);
        
        double total = 0;
       for (Map<String,String> registro : resultset) {
            total += Double.parseDouble(registro.get("valor"));
       }
        
        //return total;
        return service.getProjectTotal();
    }

    @GetMapping("/projectAmmount")
    public Double getProjectAmmount() {
        
        return service.getProjectTotal();
    }

    @GetMapping("/valores-estimado2")
    public List<NomeValorObject> getEstimatedValues(@RequestParam String tipo, @RequestParam int ano, @RequestParam Map<String, String> filtro) {
        
        List<Map<String, String>> resultset = service.consultarTodosValores(ano, filtro);
        
        
        switch (tipo) {
            case "microregion":
                return mesclarValoresPor(resultset, "microregiao"); 
            case "city":
                return mesclarValoresPor(resultset, "municipio");
        
            default:
                return List.of();
        }
        
    }

    @GetMapping("/valores-estimado")
    public List<NomeValorObject> getEstimatedValues(@RequestParam String tipo) {
        
        switch (tipo) {
            case "microregion":
                return service.getValoresPorTipo(MICROREGIAO); 
            case "city":
                return service.getValoresPorTipo(CIDADES);
        
            default:
                return List.of();
        }
        
    }

    @GetMapping("/valores-por")
    public List<NomeValorObject> getValuesBy(@RequestParam String tipo) {
        switch (tipo) {
            case "project":
                return service.getValoresPorTipo(PROJETO); 
            case "program":
                return service.getValoresPorTipo(PROGRAMAS);
        
            default:
                return List.of();
        }
        
    }

    @GetMapping("/valores-estimado-secretaria")
    public List<NomeValorObject> getEstimatedValuesSecretary() {
        return service.getValoresPorTipo(SECRETARIA);
        
    }
    
    private List<NomeValorObject> mesclarValoresPor(List<Map<String, String>> resultSet, String campo) {

        HashMap<String, Double> retorno = new HashMap<>();
        
        resultSet.forEach(regResultSet -> {

            Double valorAtual = retorno.get(regResultSet.get(campo));
            valorAtual = valorAtual == null ? 0 : valorAtual;

            retorno.put(regResultSet.get(campo), valorAtual + Double.parseDouble(regResultSet.get("valor")));
        });
        

        return retorno.entrySet().stream().map(reg -> new NomeValorObject(reg.getKey(), reg.getValue())).toList();

    }
    
}
