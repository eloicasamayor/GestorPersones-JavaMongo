package com.example;

public class Adreça {
    private String carrer, poblacio, localitat, provincia;

    public Adreça() {
    }

    public Adreça(String carrer, String poblacio, String localitat, String provincia) {
        this.carrer = carrer;
        this.poblacio = poblacio;
        this.localitat = localitat;
        this.provincia = provincia;
    }

    public String getCarrer() {
        return carrer;
    }

    public void setCarrer(String carrer) {
        this.carrer = carrer;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public String getLocalitat() {
        return localitat;
    }

    public void setLocalitat(String localitat) {
        this.localitat = localitat;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

}
