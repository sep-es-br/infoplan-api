package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectDeliveriesBySelectedDto {
    private int id;
    private String nome;
    private int execucao;
    private int concluida;

    public StrategicProjectDeliveriesBySelectedDto() {
    }

    public StrategicProjectDeliveriesBySelectedDto(int id, String nome, int execucao, int concluida) {
        this.id = id;
        this.nome = nome;
        this.execucao = execucao;
        this.concluida = concluida;
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

    public int getExecucao() {
        return execucao;
    }

    public void setExecucao(int execucao) {
        this.execucao = execucao;
    }

    public int getConcluida() {
        return concluida;
    }

    public void setConcluida(int concluida) {
        this.concluida = concluida;
    }
    
}