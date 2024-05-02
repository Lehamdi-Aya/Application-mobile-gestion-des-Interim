package com.example.interim;
public class Offre {
    private String titre;
    private String localisation;
    private String description;

    public Offre(String titre, String localisation, String description) {
        this.titre = titre;
        this.localisation = localisation;
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getDescription() {
        return description;
    }
}
