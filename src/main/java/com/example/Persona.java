package com.example;

public class Persona {

    private static int _id;
    private String nom;
    private int edat;
    private String professio;
    private int sou;
    private Adreça adreça;
    private String codiPostal;

    public Persona() {
    }

    public Persona(String nom, int edat, String professio, int sou, Adreça adreça, String codiPostal) {
        this.nom = nom;
        this.edat = edat;
        this.professio = professio;
        this.sou = sou;
        this.adreça = adreça;
        this.codiPostal = codiPostal;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        Persona._id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public String getProfessio() {
        return professio;
    }

    public void setProfessio(String professio) {
        this.professio = professio;
    }

    public int getSou() {
        return sou;
    }

    public void setSou(int sou) {
        this.sou = sou;
    }

    public Adreça getAdreça() {
        return adreça;
    }

    public void setAdreça(Adreça adreça) {
        this.adreça = adreça;
    }

    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }

}
