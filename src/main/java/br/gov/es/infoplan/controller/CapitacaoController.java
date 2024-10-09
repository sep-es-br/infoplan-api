package br.gov.es.infoplan.controller;

import br.gov.es.infoplan.dto.NomeValorObject;
import br.gov.es.infoplan.dto.UsuarioDto;
import br.gov.es.infoplan.service.AutenticacaoService;
import br.gov.es.infoplan.service.PentahoBIService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Base64;
import java.util.List;

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
    

    private final PentahoBIService pentahoBIService;

    @Value("${frontend.host}")
    private String frontHost;

    @GetMapping("/programAmmount")
    public Double getProgramAmmount() {
        return pentahoBIService.getProgramTotal();
    }

    @GetMapping("/projectAmmount")
    public Double getProjectAmmount() {
        return pentahoBIService.getProjectTotal();
    }

    @GetMapping("/valores-estimado")
    public List<NomeValorObject> getEstimatedValues(@RequestParam String tipo) {
        switch (tipo) {
            case "microregion":
                return pentahoBIService.getValoresPentahoAPI(MICROREGIAO); 
            case "city":
                return pentahoBIService.getValoresPentahoAPI(CIDADES);
        
            default:
                return List.of();
        }
        
    }

    @GetMapping("/valores-por")
    public List<NomeValorObject> getValuesBy(@RequestParam String tipo) {
        switch (tipo) {
            case "project":
                return pentahoBIService.getValoresPentahoAPI(PROJETO); 
            case "program":
                return pentahoBIService.getValoresPentahoAPI(PROGRAMAS);
        
            default:
                return List.of();
        }
        
    }

    @GetMapping("/valores-estimado-secretaria")
    public List<NomeValorObject> getEstimatedValuesSecretary() {
        return pentahoBIService.getValoresPentahoAPI(SECRETARIA);
        
    }
    
}
