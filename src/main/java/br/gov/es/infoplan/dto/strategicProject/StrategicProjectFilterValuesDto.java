package br.gov.es.infoplan.dto.strategicProject;

import java.util.List;

public class StrategicProjectFilterValuesDto {
    private List<StrategicProjectIdAndNameDto> area;
    private List<StrategicProjectIdAndNameDto> programasOriginal;
    private List<StrategicProjectIdAndNameDto> programasTransversal;
    private List<StrategicProjectIdAndNameDto> projetos;
    private List<StrategicProjectIdAndNameDto> entregas;
    private List<StrategicProjectIdAndNameDto> orgaos;
    private List<StrategicProjectIdAndNameDto> localidades;


    public List<StrategicProjectIdAndNameDto> getArea() {
        return area;
    }

    public void setArea(List<StrategicProjectIdAndNameDto> area) {
        this.area = area;
    }

    public List<StrategicProjectIdAndNameDto> getProgramasOriginal() {
        return programasOriginal;
    }

    public void setProgramasOriginal(List<StrategicProjectIdAndNameDto> programasOriginal) {
        this.programasOriginal = programasOriginal;
    }

    public List<StrategicProjectIdAndNameDto> getProgramasTransversal() {
        return programasTransversal;
    }

    public void setProgramasTransversal(List<StrategicProjectIdAndNameDto> programasTransversal) {
        this.programasTransversal = programasTransversal;
    }

    public List<StrategicProjectIdAndNameDto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<StrategicProjectIdAndNameDto> projetos) {
        this.projetos = projetos;
    }

    public List<StrategicProjectIdAndNameDto> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<StrategicProjectIdAndNameDto> entregas) {
        this.entregas = entregas;
    }

    public List<StrategicProjectIdAndNameDto> getOrgaos() {
        return orgaos;
    }

    public void setOrgaos(List<StrategicProjectIdAndNameDto> orgaos) {
        this.orgaos = orgaos;
    }

    public List<StrategicProjectIdAndNameDto> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<StrategicProjectIdAndNameDto> localidades) {
        this.localidades = localidades;
    }
}

