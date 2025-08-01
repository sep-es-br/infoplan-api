package br.gov.es.infoplan.dto.strategicProject;

public class ProgramDto {
  private int programaId;
  private String nomePrograma;

  public ProgramDto(int programaId, String nomePrograma) {
    this.programaId = programaId;
    this.nomePrograma = nomePrograma;
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

}
