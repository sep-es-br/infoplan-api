package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectRisksByClassificationDto {

    private int portfolioId;
    private String nomePortfolio;
    private int areaId;
    private String nomeArea;
    private int programaId;
    private String nomePrograma;
    private int projetoId;
    private String nomeProjeto;
    private int riscoId;
    private String nomeRisco;
    private String riscoDescricao;
    private int importanciaId;
    private String riscoImportancia;
    private String corImportancia;


    public StrategicProjectRisksByClassificationDto(){}

    public StrategicProjectRisksByClassificationDto(int portfolioId, String nomePortfolio, int areaId, String nomeArea,
            int programaId, String nomePrograma, int projetoId, String nomeProjeto, int riscoId, String nomeRisco,
            String riscoDescricao, int importanciaId, String riscoImportancia, String corImportancia) {
        this.portfolioId = portfolioId;
        this.nomePortfolio = nomePortfolio;
        this.areaId = areaId;
        this.nomeArea = nomeArea;
        this.programaId = programaId;
        this.nomePrograma = nomePrograma;
        this.projetoId = projetoId;
        this.nomeProjeto = nomeProjeto;
        this.riscoId = riscoId;
        this.nomeRisco = nomeRisco;
        this.riscoDescricao = riscoDescricao;
        this.importanciaId = importanciaId;
        this.riscoImportancia = riscoImportancia;
        this.corImportancia = corImportancia;
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
    public int getRiscoId() {
        return riscoId;
    }
    public void setRiscoId(int riscoId) {
        this.riscoId = riscoId;
    }
    public String getNomeRisco() {
        return nomeRisco;
    }
    public void setNomeRisco(String nomeRisco) {
        this.nomeRisco = nomeRisco;
    }
    public String getRiscoDescricao() {
        return riscoDescricao;
    }
    public void setRiscoDescricao(String riscoDescricao) {
        this.riscoDescricao = riscoDescricao;
    }
    public int getImportanciaId() {
        return importanciaId;
    }
    public void setImportanciaId(int importanciaId) {
        this.importanciaId = importanciaId;
    }
    public String getRiscoImportancia() {
        return riscoImportancia;
    }
    public void setRiscoImportancia(String riscoImportancia) {
        this.riscoImportancia = riscoImportancia;
    }
    public String getCorImportancia() {
        return corImportancia;
    }
    public void setCorImportancia(String corImportancia) {
        this.corImportancia = corImportancia;
    }


    
}