package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectIdAndNameDto {
    
    private int id;
    private String name;

    public StrategicProjectIdAndNameDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public StrategicProjectIdAndNameDto(){
        
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
