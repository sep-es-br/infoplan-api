package br.gov.es.infoplan.dto;

public class NomeValorObject{
    private int id;
    private String name;
    private double ammount = 0; 

    public NomeValorObject() {

    }

    public NomeValorObject(int id, String name, double ammount) {
        this.id = id;
        this.name = name;
        this.ammount = ammount;
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

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public void addAmmount(double ammount) {
        this.ammount += ammount;
    }
}
