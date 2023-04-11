package com.adi.pfe2023.objet.porte;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.adi.pfe2023.objet.Composant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class Porte extends Composant {
    private String cheminPorte;
    private final String detail_ouvrir= "Ouverture";
    private final String detail_fermer= "Fermeture";
    public long ETAT;
    DatabaseReference databaseReference;

    public String getCheminPorte() {
        return cheminPorte;
    }

    public void setCheminPorte(String cheminPorte) {
        this.cheminPorte = cheminPorte;
    }
    public void setEtat(long e){
        ETAT=e;
    }
    public void ouvrirPorte(Porte porte, long degre){
        String cheminAmpoule= porte.getCheminPorte();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(cheminAmpoule);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long value = (Long) snapshot.getValue();
                if (value!=null) {
                    databaseReference.setValue(degre);
                    enregistrerNouvelleCommande(detail_ouvrir, porte);
                    porte.setEtat(degre);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    public void fermerPorte(Porte porte, long degre){
        String cheminAmpoule= porte.getCheminPorte();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(cheminAmpoule);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long value = (Long) snapshot.getValue();
                if (value!=null) {
                    databaseReference.setValue(degre);
                    enregistrerNouvelleCommande(detail_fermer, porte);
                    porte.setEtat(degre);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
