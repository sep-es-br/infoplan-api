package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectProjectDetailsDto {
  private int contagemPE;
  private Long custoPrevisto;
  private Long custoRealizado;
  private int programaId;
  private String nomePrograma;
  private int orgaoId;
  private String nomeOrgao;
  private int areaId;
  private String nomeArea;
  private int projetoId;
  private String nomeProjeto;
  private String descricaoProjeto;
  private String statusProjeto;
  private String responsavel;
  private String funcaoResponsavel;

  public StrategicProjectProjectDetailsDto() {}

  public StrategicProjectProjectDetailsDto(int contagemPE) {
    this.contagemPE = contagemPE;
  }

  public StrategicProjectProjectDetailsDto(Long custoPrevisto, Long custoRealizado) {
    this.custoPrevisto = custoPrevisto;
    this.custoRealizado = custoRealizado;
  }

  public StrategicProjectProjectDetailsDto(int programaId, String nomePrograma) {
    this.programaId = programaId;
    this.nomePrograma = nomePrograma;
  }

  public StrategicProjectProjectDetailsDto(
    int orgaoId,
    String nomeOrgao,
    int areaId,
    String nomeArea,
    int projetoId,
    String nomeProjeto,
    String descricaoProjeto,
    String statusProjeto,
    String responsavel,
    String funcaoResponsavel
  ) {
    this.orgaoId = orgaoId;
    this.nomeOrgao = nomeOrgao;
    this.areaId = areaId;
    this.nomeArea = nomeArea;
    this.projetoId = projetoId;
    this.nomeProjeto = nomeProjeto;
    this.descricaoProjeto = descricaoProjeto;
    this.statusProjeto = statusProjeto;
    this.responsavel = responsavel;
    this.funcaoResponsavel = funcaoResponsavel;
  }

  public int getContagemPE() {
    return contagemPE;
  }

  public void setContagemPE(int contagemPE) {
    this.contagemPE = contagemPE;
  }

  public Long getCustoPrevisto() {
    return custoPrevisto;
  }

  public void setCustoPrevisto(Long custoPrevisto) {
    this.custoPrevisto = custoPrevisto;
  }

  public Long getCustoRealizado() {
    return custoRealizado;
  }

  public void setCustoRealizado(Long custoRealizado) {
    this.custoRealizado = custoRealizado;
  }

  public int getProgramaId() {
    return programaId;
  }

  public void setProgramaId(int programaId) {
    this.programaId = programaId;
  }

  public String getNomePrograma() {
    return nomePrograma;
  }

  public void setNomePrograma(String nomePrograma) {
    this.nomePrograma = nomePrograma;
  }

  public int getOrgaoId() {
    return orgaoId;
  }

  public void setOrgaoId(int orgaoId) {
    this.orgaoId = orgaoId;
  }

  public String getNomeOrgao() {
    return nomeOrgao;
  }

  public void setNomeOrgao(String nomeOrgao) {
    this.nomeOrgao = nomeOrgao;
  }

  public int getAreaId() {
    return areaId;
  }

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }

  public String getNomeArea() {
    return nomeArea;
  }

  public void setNomeArea(String nomeArea) {
    this.nomeArea = nomeArea;
  }

  public int getProjetoId() {
    return projetoId;
  }

  public void setProjetoId(int projetoId) {
    this.projetoId = projetoId;
  }

  public String getNomeProjeto() {
    return nomeProjeto;
  }

  public void setNomeProjeto(String nomeProjeto) {
    this.nomeProjeto = nomeProjeto;
  }

  public String getDescricaoProjeto() {
    return descricaoProjeto;
  }

  public void setDescricaoProjeto(String descricaoProjeto) {
    this.descricaoProjeto = descricaoProjeto;
  }

  public String getStatusProjeto() {
    return statusProjeto;
  }

  public void setStatusProjeto(String statusProjeto) {
    this.statusProjeto = statusProjeto;
  }

  public String getResponsavel() {
    return responsavel;
  }

  public void setResponsavel(String responsavel) {
    this.responsavel = responsavel;
  }

  public String getFuncaoResponsavel() {
    return funcaoResponsavel;
  }

  public void setFuncaoResponsavel(String funcaoResponsavel) {
    this.funcaoResponsavel = funcaoResponsavel;
  }
}
