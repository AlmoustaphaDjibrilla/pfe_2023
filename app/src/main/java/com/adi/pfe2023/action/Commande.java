package com.adi.pfe2023.action;

import com.adi.pfe2023.objet.Composant;
import com.google.firebase.auth.FirebaseUser;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Date;

public class Commande {
    private String emailUser;
    private String composant;
    private String date_commande;

    public Commande(FirebaseUser firebaseUser, Composant composant){
        LocalDate dateInstant= LocalDate.now(Clock.systemDefaultZone());
        this.date_commande= dateInstant.toString();
        this.emailUser= firebaseUser.getEmail();
        this.composant= composant.getNomComposant();
    }

}
