package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectProgramDetailsDto {
  private int contagemPE;
  private Long custoPrevisto;
  private Long custoRealizado;
  private int qtdeProjetos;
  private int areaId;
  private String nomeArea;
  private int programaId;
  private String nomePrograma;
  private String objetivo;
  private int transversal;
  private String responsavel;

  public StrategicProjectProgramDetailsDto() {}

  public StrategicProjectProgramDetailsDto(int contagemPE) {
    this.contagemPE = contagemPE;
  }

  public StrategicProjectProgramDetailsDto(Long custoPrevisto, Long custoRealizado) {
    this.custoPrevisto = custoPrevisto;
    this.custoRealizado = custoRealizado;
  }

  public StrategicProjectProgramDetailsDto(int qtdeProjetos, String stringInutil) {
    this.qtdeProjetos = qtdeProjetos;
  }

  public StrategicProjectProgramDetailsDto(
    int areaId,
    String nomeArea,
    int programaId,
    String nomePrograma,
    String objetivo,
    int transversal,
    String responsavel
  ) {
    this.areaId = areaId;
    this.nomeArea = nomeArea;
    this.programaId = programaId;
    this.nomePrograma = nomePrograma;
    this.objetivo = objetivo;
    this.transversal = transversal;
    this.responsavel = responsavel;
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
  public int getQtdeProjetos() {
    return qtdeProjetos;
  }
  public void setQtdeProjetos(int qtdeProjetos) {
    this.qtdeProjetos = qtdeProjetos;
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
  public String getObjetivo() {
    return objetivo;
  }
  public void setObjetivo(String objetivo) {
    this.objetivo = objetivo;
  }
  public int getTransversal() {
    return transversal;
  }
  public void setTransversal(int transversal) {
    this.transversal = transversal;
  }
  public String getResponsavel() {
    return responsavel;
  }
  public void setResponsavel(String responsavel) {
    this.responsavel = responsavel;
  }
}
