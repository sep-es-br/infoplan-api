package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectTotaisDto {
    
    private double totalPrevisto;
    private double totalRealizado;
    private int totalEntregasPE;
    private int qdeProjetos;
    private int qdeProgramas;

    

    public StrategicProjectTotaisDto(double totalPrevisto, double totalRealizado, int totalEntregasPE, int qdeProjetos,
            int qdeProgramas) {
        this.totalPrevisto = totalPrevisto;
        this.totalRealizado = totalRealizado;
        this.totalEntregasPE = totalEntregasPE;
        this.qdeProjetos = qdeProjetos;
        this.qdeProgramas = qdeProgramas;
    }

    public StrategicProjectTotaisDto() {
    }

    public double getTotalPrevisto() {
        return totalPrevisto;
    }
    public void setTotalPrevisto(double totalPrevisto) {
        this.totalPrevisto = totalPrevisto;
    }
    public double getTotalRealizado() {
        return totalRealizado;
    }
    public void setTotalRealizado(double totalRealizado) {
        this.totalRealizado = totalRealizado;
    }
    public int getTotalEntregasPE() {
        return totalEntregasPE;
    }
    public void setTotalEntregasPE(int totalEntregasPE) {
        this.totalEntregasPE = totalEntregasPE;
    }
    public int getQdeProjetos() {
        return qdeProjetos;
    }
    public void setQdeProjetos(int qdeProjetos) {
        this.qdeProjetos = qdeProjetos;
    }
    public int getQdeProgramas() {
        return qdeProgramas;
    }
    public void setQdeProgramas(int qdeProgramas) {
        this.qdeProgramas = qdeProgramas;
    }

}
