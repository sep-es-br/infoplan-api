package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectInvestmentSelectedDto {

    private int id;
    private String nome;
    private double custoPrevisto;
    private double custoRealizado;

    public StrategicProjectInvestmentSelectedDto() {
    }

    public StrategicProjectInvestmentSelectedDto(int id, String nome, double custoPrevisto, double custoRealizado) {
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
    public double getCustoPrevisto() {
        return custoPrevisto;
    }
    public void setCustoPrevisto(double custoPrevisto) {
        this.custoPrevisto = custoPrevisto;
    }
    public double getCustoRealizado() {
        return custoRealizado;
    }
    public void setCustoRealizado(double custoRealizado) {
        this.custoRealizado = custoRealizado;
    }
    
}
