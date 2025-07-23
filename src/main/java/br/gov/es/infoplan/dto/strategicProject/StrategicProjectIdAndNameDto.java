package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectIdAndNameDto {
  private int id;

  private String name;

  private String fullName;

  private String tipo;

  private int microregiaoId;

  public StrategicProjectIdAndNameDto(int id, String name) {
    this.id = id;
    this.name = name;
    this.fullName = name;
  }

  public StrategicProjectIdAndNameDto(int id, String name, String fullName) {
    this.id = id;
    this.name = name;
    this.fullName = fullName;
  }

  public StrategicProjectIdAndNameDto(int id, String name, String tipo, int microregiaoId) {
    this.id = id;
    this.name = name;
    this.fullName = name;
    this.tipo = tipo;
    this.microregiaoId = microregiaoId;
  }

  public StrategicProjectIdAndNameDto() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public int getMicroregiaoId() {
    return microregiaoId;
  }

  public void setMicroregiaoId(int microregiaoId) {
    this.microregiaoId = microregiaoId;
  }
}
