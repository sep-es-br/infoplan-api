package br.gov.es.infoplan.client;

import br.gov.es.infoplan.dto.acessocidadaoapi.ACAgentePublicoPapelDto;
import br.gov.es.infoplan.dto.acessocidadaoapi.AgentePublicoACDto;
import br.gov.es.infoplan.dto.acessocidadaoapi.SubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "acessoCidadaoWeb")
public interface AcessoCidadaoWebClient {
    @PutMapping("/api/cidadao/{cpf}/pesquisaSub")
    SubResponseDto buscarSubPorCpf(@RequestHeader Map<String, Object> headers, @PathVariable String cpf);

    @GetMapping("/api/agentepublico/{sub}")
    AgentePublicoACDto.AgentePublicoACResponseDto buscarAgentePublicoPorSub(@RequestHeader Map<String, Object> headers,
                                                                            @PathVariable String sub);

    @GetMapping("/api/agentepublico/{sub}/papeis")
    List<ACAgentePublicoPapelDto> buscarPapeisAgentePublicoPorSub(@RequestHeader Map<String, Object> headers, @PathVariable String sub);

    @GetMapping("/api/conjunto/{guid}/gestornovo/papel")
    ACAgentePublicoPapelDto buscarGestorNovoConjuntoPorGuidOrganizacao(@RequestHeader Map<String, Object> headers, @PathVariable String guid);

    @GetMapping("/api/conjunto/{guidUnidadeOrganizacao}/agentesPublicos")
    List<AgentePublicoACDto> buscarAgentesPublicosPorGuidUnidade(@RequestHeader Map<String, Object> headers, @PathVariable String guid);

    @GetMapping("/api/conjunto/{guid}/papeis")
    List<ACAgentePublicoPapelDto> buscarAgentesPublicosPapeisPorGuidUnidade( @RequestHeader Map<String, Object> headers, @PathVariable String guid, //);
                                                                             @RequestParam(value = "operacional", required = false) Boolean operacional );

    @GetMapping("/api/conjunto/{guid}/gestor")
    AgentePublicoACDto.AgentePublicoACResponseDto buscarGestorPorGuidUnidade(@RequestHeader Map<String, Object> headers, @PathVariable String guid);
//
//    @GetMapping("/api/cidadao/{sub}/email")
//    EmailSubResponseDto buscarEmailCorporativoPorSub(@RequestHeader Map<String, Object> headers, @PathVariable String sub);
}
