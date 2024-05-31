package entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Employer {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String companyName;
    public String nationalUniqueNumber;
    public String phoneNumber;
    public String email;
    public String postalAddress;
    public String city;
    public String country;
    public String password; // Nouveau champ pour le mot de passe
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNationalUniqueNumber() {
        return nationalUniqueNumber;
    }

    public void setNationalUniqueNumber(String nationalUniqueNumber) {
        this.nationalUniqueNumber = nationalUniqueNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
