package com.example.interim;
public class Offer {
    private String title;
    private String company;
    private String city;

    public Offer(String title, String company, String city) {
        this.title = title;
        this.company = company;
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
