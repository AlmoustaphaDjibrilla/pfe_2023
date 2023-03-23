package com.adi.pfe2023.model;

import java.time.LocalDate;

public class UserModel {

    private String Uid;
    private String uriProfil;
    private String telephone;

    public String getUriProfil() {
        return uriProfil;
    }

    public void setUriProfil(String uriProfil) {
        this.uriProfil = uriProfil;
    }

    private String email;
    private String nom;
    private String password;
    private boolean isAdmin;
    private boolean isUser;
    private String dateEnregistrement;


    public UserModel() {
    }

    public UserModel(String email, String nom) {
        this.email = email;
        this.nom = nom;
        this.isAdmin=false;
        this.isUser=true;
        this.dateEnregistrement= LocalDate.now().toString();
    }

    public UserModel(String uid, String email, String nom) {
        Uid = uid;
        this.email = email;
        this.nom = nom;
        this.isAdmin=false;
        this.isUser=true;
        this.dateEnregistrement= LocalDate.now().toString();
    }

    public UserModel(String uid, String email, String nom, String password) {
        Uid = uid;
        this.email = email;
        this.nom = nom;
        this.password = password;
        this.isAdmin=false;
        this.isUser=true;
        this.dateEnregistrement= LocalDate.now().toString();
    }

    public UserModel(String uid,  String telephone, String email, String nom, String password) {
        Uid = uid;
        this.telephone = telephone;
        this.email = email;
        this.nom = nom;
        this.password = password;
        this.isAdmin=false;
        this.isUser=true;
        this.dateEnregistrement= LocalDate.now().toString();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public String getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(String dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
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
