package com.adi.pfe2023.fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adi.pfe2023.R;
import com.adi.pfe2023.action.Commande;
import com.adi.pfe2023.model.UserModel;
import com.adi.pfe2023.objet.Composant;
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleCuisine;
import com.adi.pfe2023.objet.ampoule.AmpouleSalon;
import com.adi.pfe2023.objet.meteo.Meteo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FragmentHome extends Fragment {

    private final String detail_allumer_ampoule= "Allumage";
    private final String detail_extinction_ampoule= "Extinction";
    final String PATH_COMMANDE= "Commandes";
    private final String PATH_USER_DATABASE= "Users";


    private FirebaseUser currentUser;

    DatabaseReference databaseReference;
    Button btnAllumerAmpouleSalon, btnEteindreAmpouleSalon, btnAllumerAmpouleCuisine, btnEteindreAmpouleCuisine;
    TextView tempText, humText;


    public FragmentHome() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.currentUser= FirebaseAuth.getInstance().getCurrentUser();

        View view= inflater.inflate(R.layout.fragment_home, container, false);

        init(view);

        lireTemperatureEtHumidite(new Meteo());

        btnAllumerAmpouleSalon.setOnClickListener(
                v-> {
                    allumer(AmpouleSalon.getInstance());
                }

        );

        btnEteindreAmpouleSalon.setOnClickListener(
                v-> {
                    eteindre(AmpouleSalon.getInstance());
                }
        );

        btnAllumerAmpouleCuisine.setOnClickListener(
                v->allumer(AmpouleCuisine.getInstance())
        );

        btnEteindreAmpouleCuisine.setOnClickListener(
                v->eteindre(AmpouleCuisine.getInstance())
        );


        return view;
    }

    /**
     * Initialiser les differents composants
     * de ce fragment afin de pouvoir les
     * utiliser tout au long de l'exécution
     * de l'application
     * @param view, type View
     *              fourni par le fragment en cours
     */
    private void init(View view){
        tempText = view.findViewById(R.id.temp);
        humText = view.findViewById(R.id.hum);

        btnAllumerAmpouleSalon = view.findViewById(R.id.btnAllumer);
        btnEteindreAmpouleSalon = view.findViewById(R.id.btnEteindre);

        btnAllumerAmpouleCuisine = view.findViewById(R.id.btnAllumerCuisine);
        btnEteindreAmpouleCuisine = view.findViewById(R.id.btnEteindreCuisine);
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
                        Toast.makeText(getContext(), "La lampe est deja allumée", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Nous avons rencontré un probleme", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "La lampe est deja éteinte", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Nous avons rencontré un probleme", Toast.LENGTH_SHORT).show();
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
     *
     * @param meteo pour fournir les infos
     *              telles que la
     *              temperature et l'humidité
     *
     */
    private void lireTemperatureEtHumidite(Meteo meteo){
        final String cheminTemperature= meteo.getCheminTemperature();
        final String cheminHumidite= meteo.getCheminHumidite();

        //Se positionner sur l'adresse de la temperature
        databaseReference= FirebaseDatabase.getInstance().getReference().child(cheminTemperature);
        databaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long value = (Long) snapshot.getValue();
                        if (value!=null) {
                            tempText.setText(value+" °C");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                }
        );

        databaseReference= FirebaseDatabase.getInstance().getReference().child(cheminHumidite);
        databaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long value = (Long) snapshot.getValue();
                        if (value!=null)
                            humText.setText(value.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                }
        );
    }

    private void enregistrerNouvelleCommande(Commande commande){
        DocumentReference documentReference=
                FirebaseFirestore.getInstance()
                        .collection(PATH_COMMANDE)
                        .document();
        documentReference.set(commande);
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