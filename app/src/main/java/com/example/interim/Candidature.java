package com.example.interim;

public class Candidature {
    private String nom;
    public Candidature(String nom, String datedenaissance) {
        this.nom = nom;
        this.datedenaissance = datedenaissance;

    }
    public String getDatedenaissance() {
        return datedenaissance;
    }

    public Candidature(String nom) {
        this.nom = nom;
    }

    public void setDatedenaissance(String datedenaissance) {
        this.datedenaissance = datedenaissance;
    }

    private String datedenaissance;

    public String getNom() {
        return nom;
    }


}
