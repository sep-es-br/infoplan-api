package br.gov.es.infoplan.dto.strategicProject;

public class StrategicProjectTimestampDto {
    
    private String timestamp;

    
    public StrategicProjectTimestampDto() {
    }

    public StrategicProjectTimestampDto(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
