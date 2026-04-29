package br.gov.es.infoplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.es.infoplan.dto.NomeValorObject;
import br.gov.es.infoplan.service.CapitationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Siscap - Sistema de Captação de Recursos")
@CrossOrigin(origins = "${frontend.host}")
@RestController
@RequestMapping("/capitation")
@RequiredArgsConstructor
public class CapitacaoController {

  private final CapitationService service;

  @Value("${frontend.host}")
  private String frontHost;

  @Operation(summary = "Montante total por Programa", description = "Busca o valor consolidado captado agrupado por programa")
  @GetMapping("/programAmmount")
  public Double getProgramAmmount(@RequestParam String filterJson) {

    return service.getProgramTotal(filterJson);
  }

  @Operation(summary = "Montante total por Projeto", description = "Busca o valor consolidado captado agrupado por projeto")
  @GetMapping("/projectAmmount")
  public Double getProjectAmmount(@RequestParam String filterJson) {

    return service.getProjectTotal(filterJson);
  }

  @Operation(summary = "Valores Estimados por Região/Cidade", description = "Lista os valores estimados filtrando por microregião ou cidade")
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

  @Operation(summary = "Valores Agrupados", description = "Lista valores captados agrupados por projeto ou programa")
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

  @Operation(summary = "Valores Estimados por Secretaria", description = "Busca o consolidado de valores estimados por secretaria/órgão")
  @GetMapping("/valores-estimado-secretaria")
  public List<NomeValorObject> getEstimatedValuesSecretary(@RequestParam String filterJson) {
    return service.getAllBySecretaria(filterJson);

  }

}
