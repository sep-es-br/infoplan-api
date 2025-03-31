package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectAccumulatedInvestmentDto {

    private int anoMes;
    private double custoPrevisto;
    private double custoPrevistoAcumulado;
    private double custoRealizado;
    private double custoRealizadoAcumulado;

    
    public StrategicProjectAccumulatedInvestmentDto() {
    }

    public StrategicProjectAccumulatedInvestmentDto(int anoMes, double custoPrevisto, double custoPrevistoAcumulado,
            double custoRealizado, double custoRealizadoAcumulado) {
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
    public double getCustoPrevisto() {
        return custoPrevisto;
    }
    public void setCustoPrevisto(double custoPrevisto) {
        this.custoPrevisto = custoPrevisto;
    }
    public double getCustoPrevistoAcumulado() {
        return custoPrevistoAcumulado;
    }
    public void setCustoPrevistoAcumulado(double custoPrevistoAcumulado) {
        this.custoPrevistoAcumulado = custoPrevistoAcumulado;
    }
    public double getCustoRealizado() {
        return custoRealizado;
    }
    public void setCustoRealizado(double custoRealizado) {
        this.custoRealizado = custoRealizado;
    }
    public double getCustoRealizadoAcumulado() {
        return custoRealizadoAcumulado;
    }
    public void setCustoRealizadoAcumulado(double custoRealizadoAcumulado) {
        this.custoRealizadoAcumulado = custoRealizadoAcumulado;
    }


    
}
