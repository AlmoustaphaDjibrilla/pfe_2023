package com.adi.pfe2023.objet.ampoule;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.adi.pfe2023.action.Commande;
import com.adi.pfe2023.model.UserModel;
import com.adi.pfe2023.objet.Composant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;


public abstract class Ampoule extends Composant {

    private String cheminAmpoule;
    private final String detail_allumer_ampoule= "Allumage";
    private final String detail_extinction_ampoule= "Extinction";
    public String ETAT="";
    DatabaseReference databaseReference;

    public String getCheminAmpoule() {
        return cheminAmpoule;
    }

    public void setCheminAmpoule(String cheminAmpoule) {
        this.cheminAmpoule = cheminAmpoule;
    }
    public void setEtat(String e){
        ETAT=e;
    }


    /**
     * Cette fonction allume une ampoule
     * passée en paramètre
     * @param ampoule , Ce parametre est un objet de type Ampoule
     *                pouvant se situer dans n'importe
     *                quel endroit de la maison
     */
    public void allumer(Ampoule ampoule){
        String cheminAmpoule= ampoule.getCheminAmpoule();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(cheminAmpoule);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = (String) snapshot.getValue();
                if (value!=null) {
                    if (value.equals("OFF")) {
                        databaseReference.setValue("ON");
                        enregistrerNouvelleCommande(detail_allumer_ampoule, ampoule);
                        ampoule.setEtat("ON");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * Cette fonction permet d'éteindre
     * une ampoule passée en paramètre
     * @param ampoule, ce paramètre est un objet de type Ampoule
     *                 pouvant se situer dans n'importe
     *                 quel endroit de la maison
     */
    public void eteindre(Ampoule ampoule){
        String cheminAmpoule= ampoule.getCheminAmpoule();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(cheminAmpoule);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = (String) snapshot.getValue();
                if (value!=null) {
                    if (value.equals("ON")) {
                        databaseReference.setValue("OFF");
                        enregistrerNouvelleCommande(detail_extinction_ampoule, ampoule);
                        ampoule.setEtat("OFF");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
