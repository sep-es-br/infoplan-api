package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectAccumulatedInvestmentDto {

    private int anoMes;
    private float custoPrevisto;
    private float custoPrevistoAcumulado;
    private float custoRealizado;
    private float custoRealizadoAcumulado;

    
    public StrategicProjectAccumulatedInvestmentDto() {
    }

    public StrategicProjectAccumulatedInvestmentDto(int anoMes, float custoPrevisto, float custoPrevistoAcumulado,
            float custoRealizado, float custoRealizadoAcumulado) {
        this.anoMes = anoMes;
        this.custoPrevisto = custoPrevisto;
        this.custoPrevistoAcumulado = custoPrevistoAcumulado;
        this.custoRealizado = custoRealizado;
        this.custoRealizadoAcumulado = custoRealizadoAcumulado;
    }
    
    public int getAnoMes() {
        return anoMes;
    }
    public void setAnoMes(int anoMes) {
        this.anoMes = anoMes;
    }
    public float getCustoPrevisto() {
        return custoPrevisto;
    }
    public void setCustoPrevisto(float custoPrevisto) {
        this.custoPrevisto = custoPrevisto;
    }
    public float getCustoPrevistoAcumulado() {
        return custoPrevistoAcumulado;
    }
    public void setCustoPrevistoAcumulado(float custoPrevistoAcumulado) {
        this.custoPrevistoAcumulado = custoPrevistoAcumulado;
    }
    public float getCustoRealizado() {
        return custoRealizado;
    }
    public void setCustoRealizado(float custoRealizado) {
        this.custoRealizado = custoRealizado;
    }
    public float getCustoRealizadoAcumulado() {
        return custoRealizadoAcumulado;
    }
    public void setCustoRealizadoAcumulado(float custoRealizadoAcumulado) {
        this.custoRealizadoAcumulado = custoRealizadoAcumulado;
    }


    
}
