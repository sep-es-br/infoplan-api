package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectTotaisDto {
    
    private float totalPrevisto;
    private float totalRealizado;
    private int totalEntregasPE;
    private int qdeProjetos;
    private int qdeProgramas;

    

    public StrategicProjectTotaisDto(float totalPrevisto, float totalRealizado, int totalEntregasPE, int qdeProjetos,
            int qdeProgramas) {
        this.totalPrevisto = totalPrevisto;
        this.totalRealizado = totalRealizado;
        this.totalEntregasPE = totalEntregasPE;
        this.qdeProjetos = qdeProjetos;
        this.qdeProgramas = qdeProgramas;
    }

    public StrategicProjectTotaisDto() {
    }

    public float getTotalPrevisto() {
        return totalPrevisto;
    }
    public void setTotalPrevisto(float totalPrevisto) {
        this.totalPrevisto = totalPrevisto;
    }
    public float getTotalRealizado() {
        return totalRealizado;
    }
    public void setTotalRealizado(float totalRealizado) {
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
