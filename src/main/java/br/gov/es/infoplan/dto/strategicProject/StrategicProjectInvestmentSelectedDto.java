package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectInvestmentSelectedDto {

    private int id;
    private String nome;
    private float custoPrevisto;
    private float custoRealizado;

    public StrategicProjectInvestmentSelectedDto() {
    }

    public StrategicProjectInvestmentSelectedDto(int id, String nome, float custoPrevisto, float custoRealizado) {
        this.id = id;
        this.nome = nome;
        this.custoPrevisto = custoPrevisto;
        this.custoRealizado = custoRealizado;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public float getCustoPrevisto() {
        return custoPrevisto;
    }
    public void setCustoPrevisto(float custoPrevisto) {
        this.custoPrevisto = custoPrevisto;
    }
    public float getCustoRealizado() {
        return custoRealizado;
    }
    public void setCustoRealizado(float custoRealizado) {
        this.custoRealizado = custoRealizado;
    }
    
}
