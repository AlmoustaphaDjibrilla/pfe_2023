package com.adi.pfe2023.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.adi.pfe2023.R;
import com.adi.pfe2023.action.Commande;
import com.adi.pfe2023.model.UserModel;
import com.adi.pfe2023.objet.Composant;
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleSalon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Chambre extends AppCompatActivity {
    private final String detail_allumer_ampoule= "Allumage";
    private final String detail_extinction_ampoule= "Extinction";
    final String PATH_COMMANDE= "Commandes";
    private final String PATH_USER_DATABASE= "Users";
    private Switch lampe;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_chambre);
        init();
        lampe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Ampoule A = AmpouleSalon.getInstance();
                    allumer(A);
                }
                else{
                    Ampoule B = AmpouleSalon.getInstance();
                    eteindre(B);
                }
            }
        });
    }

    private void init(){
        lampe= findViewById(R.id.switch1);
    }

    /**
     * Cette fonction allume une ampoule
     * passée en paramètre
     * @param ampoule , Ce parametre est un objet de type Ampoule
     *                pouvant se situer dans n'importe
     *                quel endroit de la maison
     */
    private void allumer(Ampoule ampoule){
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
                    } else if (value.equals("ON")) {
                        Toast.makeText(Chambre.this, "La lampe est deja allumée", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Chambre.this, "Nous avons rencontré un probleme", Toast.LENGTH_SHORT).show();
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
    private void eteindre(Ampoule ampoule){
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
                    } else if (value.equals("OFF")) {
                        Toast.makeText(Chambre.this, "La lampe est deja éteinte", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Chambre.this, "Nous avons rencontré un probleme", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void enregistrerNouvelleCommande(String detailCommande, Composant composant){

        DocumentReference docRef= FirebaseFirestore.getInstance()
                .collection(PATH_USER_DATABASE)
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        docRef.get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            Commande commande= new Commande(composant);
                            UserModel userModel= documentSnapshot.toObject(UserModel.class);
                            commande.setDetail_commande(detailCommande);
                            commande.setUserModel(userModel);
                            commande.setEmailUser(userModel.getEmail());

                            DocumentReference dfReferenceSaveCommande=
                                    FirebaseFirestore.getInstance()
                                            .collection(PATH_COMMANDE)
                                            .document();
                            dfReferenceSaveCommande.set(commande);
                        }
                );
    }

}