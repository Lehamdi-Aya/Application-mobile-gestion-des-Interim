package entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String email;
    private String nom;
    private String prenom;
    private String dateDeNaissance;
    private String phone;
    private String pays;

    private String password;



    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public void setPays(String pays) {
        this.pays = pays;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }


    public User(String nom, String prenom, String dateDeNaissance, String phone, String pays, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.phone = phone;
        this.pays = pays;

        this.email = email;  // Initialisation de l'email
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public String getPays() {
        return pays;
    }

    public String getPhone() {
        return phone;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getId() {
        return id;
    }
}
