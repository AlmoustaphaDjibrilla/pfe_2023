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
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleCuisine;
import com.adi.pfe2023.objet.ampoule.AmpouleSalon;
import com.adi.pfe2023.objet.meteo.Meteo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FragmentHome extends Fragment {

    final String PATH_TEMPERATURE= "temperature";
    final String PATH_LED_SALON="led_salon";
    final String PATH_LED_CUISINE= "led_cuisine";
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
                v-> allumer(new AmpouleSalon())
        );

        btnEteindreAmpouleSalon.setOnClickListener(
                v-> eteindre(new AmpouleSalon())
        );

        btnAllumerAmpouleCuisine.setOnClickListener(
                v->allumer(new AmpouleCuisine())
        );

        btnEteindreAmpouleCuisine.setOnClickListener(
                v->eteindre(new AmpouleCuisine())
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

    //Classe action
    public class Action {
        private String nom;
        private String action_realise;
        private Date date;

        public Action(){}

        public Action(String nom, String action, Date date){
            this.nom=nom;
            this.action_realise=action;
            this.date=date;
        }
        public String getName() {
            return nom;
        }

        public String getAction_realise() {
            return action_realise;
        }

        public Date getDate(){
            return date;
        }
    }

    public  String getUsermail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        return user.getEmail();

    }
    public void getNomUtilisateur(String email, OnSuccessListener<String> onSuccessListener, OnFailureListener onFailureListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Users"); // nom de la collection

        usersRef.whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String nom = document.getString("nom");
                        onSuccessListener.onSuccess(nom);
                    }
                })
                .addOnFailureListener(onFailureListener);
    }

    //enregistrer dans l'historique
    public void Ajout_historique(String action){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        LocalDate date = LocalDate.now();


        //Ajouter une action dans le firestore
        Map<String, Object> data = new HashMap<>();
        getNomUtilisateur(getUsermail(), new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String nom) {
                // Utilisez la valeur du nom ici
                Log.d(TAG, "Le nom de l'utilisateur est : " + nom);
                data.put("nom",  nom);
                data.put("action_realise", action);
                data.put("date",date.toString());
                db.collection("actions")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "Document ajoute avec ID: " + documentReference.getId() );
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Erreur survenue", e);
                            }
                        });

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Erreur lors de la récupération des documents : ", e);
            }
        });


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
        String action = "A Allumer la "+ cheminAmpoule;
        databaseReference = FirebaseDatabase.getInstance().getReference().child(cheminAmpoule);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = (String) snapshot.getValue();
                if (value!=null) {
                    if (value.equals("OFF")) {
                        databaseReference.setValue("ON");
                        Ajout_historique(action);
                    } else if (value.equals("ON")) {
                        Toast.makeText(getContext(), "La lampe est deja allumée", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
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
        String action = "A Eteint la "+ cheminAmpoule;
        databaseReference = FirebaseDatabase.getInstance().getReference().child(cheminAmpoule);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = (String) snapshot.getValue();
                if (value!=null) {
                    if (value.equals("ON")) {
                        databaseReference.setValue("OFF");
                        Ajout_historique(action);
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

}