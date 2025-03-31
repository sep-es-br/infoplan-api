package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectMilestonesByPerformaceDto {

    private int portfolioId;
    private String nomePortfolio;
    private int areaId;
    private String nomeArea;
    private int programaId;
    private String nomePrograma;
    private int projetoId;
    private String nomeProjeto;
    private int orgaoId;
    private String nomeOrgao;
    private int mcId;
    private String nomeMarcoCritico;
    private int statusId;
    private String nomeStatus;
    private String corStatus;

    public StrategicProjectMilestonesByPerformaceDto(){}
    
    public StrategicProjectMilestonesByPerformaceDto(int portfolioId, String nomePortfolio, int areaId,
            String nomeArea, int programaId, String nomePrograma, int projetoId, String nomeProjeto, int orgaoId,
            String nomeOrgao, int mcId, String nomeMarcoCritico, int statusId, String nomeStatus, String corStatus) {
        this.portfolioId = portfolioId;
        this.nomePortfolio = nomePortfolio;
        this.areaId = areaId;
        this.nomeArea = nomeArea;
        this.programaId = programaId;
        this.nomePrograma = nomePrograma;
        this.projetoId = projetoId;
        this.nomeProjeto = nomeProjeto;
        this.orgaoId = orgaoId;
        this.nomeOrgao = nomeOrgao;
        this.mcId = mcId;
        this.nomeMarcoCritico = nomeMarcoCritico;
        this.statusId = statusId;
        this.nomeStatus = nomeStatus;
        this.corStatus = corStatus;
    }

    public int getPortfolioId() {
        return portfolioId;
    }
    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }
    public String getNomePortfolio() {
        return nomePortfolio;
    }
    public void setNomePortfolio(String nomePortfolio) {
        this.nomePortfolio = nomePortfolio;
    }
    public int getAreaId() {
        return areaId;
    }
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
    public String getNomeArea() {
        return nomeArea;
    }
    public void setNomeArea(String nomeArea) {
        this.nomeArea = nomeArea;
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
    public int getProjetoId() {
        return projetoId;
    }
    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }
    public String getNomeProjeto() {
        return nomeProjeto;
    }
    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }
    public int getOrgaoId() {
        return orgaoId;
    }
    public void setOrgaoId(int orgaoId) {
        this.orgaoId = orgaoId;
    }
    public String getNomeOrgao() {
        return nomeOrgao;
    }
    public void setNomeOrgao(String nomeOrgao) {
        this.nomeOrgao = nomeOrgao;
    }
    public int getMcId() {
        return mcId;
    }
    public void setMcId(int mcId) {
        this.mcId = mcId;
    }
    public String getNomeMarcoCritico() {
        return nomeMarcoCritico;
    }
    public void setNomeMarcoCritico(String nomeMarcoCritico) {
        this.nomeMarcoCritico = nomeMarcoCritico;
    }
    public int getStatusId() {
        return statusId;
    }
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
    public String getNomeStatus() {
        return nomeStatus;
    }
    public void setNomeStatus(String nomeStatus) {
        this.nomeStatus = nomeStatus;
    }
    public String getCorStatus() {
        return corStatus;
    }
    public void setCorStatus(String corStatus) {
        this.corStatus = corStatus;
    }

    
    
}
