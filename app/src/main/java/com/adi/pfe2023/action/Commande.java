package com.adi.pfe2023.action;

import com.adi.pfe2023.model.UserModel;
import com.adi.pfe2023.objet.Composant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Commande {
    private final String PATH_USER_DATABASE= "Users";
    private final String PATH_COMMANDE= "Commandes";

    private String emailUser;
    private UserModel userModel;
    private String composant;
    private String date_commande;
    private String heure_commande;
    private String detail_commande;

    public Commande() {
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public String getComposant() {
        return composant;
    }

    public void setComposant(String composant) {
        this.composant = composant;
    }

    public String getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(String date_commande) {
        this.date_commande = date_commande;
    }

    public Commande(UserModel userModel, Composant composant){
        LocalDate dateInstant = LocalDate.now();
        this.date_commande= dateInstant.toString();
        this.userModel= userModel;
        if (userModel!=null)
            this.emailUser= userModel.getEmail();
        this.composant= composant.getNomComposant();
        this.heure_commande=LocalTime.now().toString();
    }

    public Commande(Composant composant) {
        this.composant = composant.getNomComposant();
        this.date_commande= LocalDate.now().toString();
        this.heure_commande=LocalTime.now().toString();
    }


    private void getUserModel(FirebaseUser firebaseUser){
        String userId= firebaseUser.getUid();
        if (userId!=null && !userId.equals("")){
            DocumentReference documentReference=
                    FirebaseFirestore.getInstance()
                            .collection(PATH_USER_DATABASE)
                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            documentReference.get()
                    .addOnSuccessListener(
                            documentSnapshot->{
                                UserModel modelUser= documentSnapshot.toObject(UserModel.class);
                                this.setUserModel(modelUser);
                            }
                    );
        }
    }

    public void setUserModel(UserModel modelUser) {
        this.userModel= modelUser;
    }

    public String getDetail_commande() {
        return detail_commande;
    }

    public void setDetail_commande(String detail_commande) {
        this.detail_commande = detail_commande;
    }

    public String getHeure_commande() {
        return heure_commande;
    }

    public void setHeure_commande(String heure_commande) {
        this.heure_commande = heure_commande;
    }
}
