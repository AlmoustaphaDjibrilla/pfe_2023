package com.adi.pfe2023.activity;

public class UserModel {

    private String Uid;
    private String email;
    private String nom;
    private boolean isAdmin;
    private boolean isUser;

    public UserModel() {
    }

    public UserModel(String email, String nom) {
        this.email = email;
        this.nom = nom;
        this.isAdmin=false;
        this.isUser=true;
    }

    public UserModel(String uid, String email, String nom) {
        Uid = uid;
        this.email = email;
        this.nom = nom;
        this.isAdmin=false;
        this.isUser=true;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isUser() {
        return isUser;
    }


    @Override
    public String toString() {
        return "UserModel{" +
                "Uid='" + Uid + '\'' +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", isAdmin=" + isAdmin +
                ", isUser=" + isUser +
                '}';
    }
}
