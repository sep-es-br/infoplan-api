package br.gov.es.infoplan.dto.strategicProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class StrategicProjectFilter {
  @JsonProperty("portfolio")
  private int portfolioId = -1;

  @JsonProperty("areaTematica")
  private List<Number> areaId = new ArrayList<Number>(Arrays.asList(-1));

  @JsonProperty("programaOrigem")
  private List<Number> programaId = new ArrayList<Number>(Arrays.asList(-1));;

  @JsonProperty("programaTransversal")
  private List<Number> programaTId = new ArrayList<Number>(Arrays.asList(-1));;

  @JsonProperty("projetos")
  private List<Number> projetoId = new ArrayList<Number>(Arrays.asList(-1));;

  @JsonProperty("entregas")
  private List<Number> entregaId = new ArrayList<Number>(Arrays.asList(-1));;

  @JsonProperty("orgaos")
  private List<Number> orgaoId = new ArrayList<Number>(Arrays.asList(-1));;

  @JsonProperty("localidades")
  private List<Number> localidadeId = new ArrayList<Number>(Arrays.asList(-1));;

  @JsonProperty("dataInicio")
  private int dataInicio;

  @JsonProperty("dataFim")
  private int dataFim;

  public StrategicProjectFilter() {
  }

  public StrategicProjectFilter(
    int portfolioId,
    List<Number> areaId,
    List<Number> programaId,
    List<Number> programaTId,
    List<Number> projetoId,
    List<Number> entregaId,
    List<Number> orgaoId,
    List<Number> localidadeId,
    int dataInicio,
    int dataFim
  ) {
    this.portfolioId = portfolioId;
    this.areaId = areaId;
    this.programaId = programaId;
    this.programaTId = programaTId;
    this.projetoId = projetoId;
    this.entregaId = entregaId;
    this.orgaoId = orgaoId;
    this.localidadeId = localidadeId;
    this.dataInicio = dataInicio;
    this.dataFim = dataFim;
  }

  public int getPortfolioId() {
    return portfolioId;
  }
  public void setPortfolioId(int portfolioId) {
    this.portfolioId = portfolioId;
  }

  public List<Number> getAreaId() {
    return this.areaId;
  }
  public void setAreaId(List<Number> areaId) {
    this.areaId = areaId;
  }

  public List<Number> getProgramaId() {
    return programaId;
  }
  public void setProgramaId(List<Number> programaId) {
    this.programaId = programaId;
  }

  public List<Number> getProgramaTId() {
    return programaTId;
  }
  public void setProgramaTId(List<Number> programaTId) {
    this.programaTId = programaTId;
  }

  public List<Number> getProjetoId() {
    return projetoId;
  }
  public void setProjetoId(List<Number> projetoId) {
    this.projetoId = projetoId;
  }

  public List<Number> getEntregaId() {
    return entregaId;
  }
  public void setEntregaId(List<Number> entregaId) {
    this.entregaId = entregaId;
  }

  public List<Number> getOrgaoId() {
    return orgaoId;
  }
  public void setOrgaoId(List<Number> orgaoId) {
    this.orgaoId = orgaoId;
  }

  public List<Number> getLocalidadeId() {
    return localidadeId;
  }
  public void setLocalidadeId(List<Number> localidadeId) {
    this.localidadeId = localidadeId;
  }

  public int getDataInicio() {
    return dataInicio;
  }
  public void setDataInicio(int dataInicio) {
    this.dataInicio = dataInicio;
  }

  public int getDataFim() {
    return dataFim;
  }
  public void setDataFim(int dataFim) {
    this.dataFim = dataFim;
  }
}
