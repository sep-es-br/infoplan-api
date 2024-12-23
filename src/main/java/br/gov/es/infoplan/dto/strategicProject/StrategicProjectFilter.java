package br.gov.es.infoplan.dto.strategicProject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class StrategicProjectFilter {

     @JsonProperty("portfolio")
    private int portfolioId = -1;

    @JsonProperty("areaTematica")
    private int areaId = -1;

    @JsonProperty("programaOrigem")
    private int programaId = -1;

    @JsonProperty("programaTransversal")
    private int programaTId = -1;

    @JsonProperty("projetos")
    private int projetoId = -1;

    @JsonProperty("entregas")
    private int entregaId = -1;

    @JsonProperty("orgaos")
    private int orgaoId = -1;

    @JsonProperty("localidades")
    private int localidadeId = -1;

    @JsonProperty("dataInicio")
    private int dataInicio;

    @JsonProperty("dataFim")
    private int dataFim;

    
    public StrategicProjectFilter() {
    }

    public StrategicProjectFilter(int portfolioId, int areaId, int programaId, int programaTId, int projetoId,
            int entregaId, int orgaoId, int localidadeId, int dataInicio, int dataFim) {
        this.portfolioId = portfolioId;
        this.areaId = areaId;
        this.programaId = programaId;
        this.programaTId = programaTId;
        this.projetoId = projetoId;
        this.entregaId = entregaId;
        this.orgaoId = orgaoId;
        this.localidadeId = localidadeId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
    
    public int getPortfolioId() {
        return portfolioId;
    }
    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }
    public int getAreaId() {
        return areaId;
    }
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
    public int getProgramaId() {
        return programaId;
    }
    public void setProgramaId(int programaId) {
        this.programaId = programaId;
    }
    public int getProgramaTId() {
        return programaTId;
    }
    public void setProgramaTId(int programaTId) {
        this.programaTId = programaTId;
    }
    public int getProjetoId() {
        return projetoId;
    }
    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }
    public int getEntregaId() {
        return entregaId;
    }
    public void setEntregaId(int entregaId) {
        this.entregaId = entregaId;
    }
    public int getOrgaoId() {
        return orgaoId;
    }
    public void setOrgaoId(int orgaoId) {
        this.orgaoId = orgaoId;
    }
    public int getLocalidadeId() {
        return localidadeId;
    }
    public void setLocalidadeId(int localidadeId) {
        this.localidadeId = localidadeId;
    }
    public int getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(int dataInicio) {
        this.dataInicio = dataInicio;
    }
    public int getDataFim() {
        return dataFim;
    }
    public void setDataFim(int dataFim) {
        this.dataFim = dataFim;
    }

    

}
