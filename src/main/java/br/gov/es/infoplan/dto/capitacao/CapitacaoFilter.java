package br.gov.es.infoplan.dto.capitacao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CapitacaoFilter {

    private String ano = "";
    private int idPrograma = -1;
    private int idProjeto = -1;
    private int idMicrorregiao = -1;
    private int idCidade = -1;
    private int idSecretaria = -1;

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public int getIdMicrorregiao() {
        return idMicrorregiao;
    }

    public void setIdMicrorregiao(int idMicrorregiao) {
        this.idMicrorregiao = idMicrorregiao;
    }

    public int getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    public int getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(int idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    
    
}
