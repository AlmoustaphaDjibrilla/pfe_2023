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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentHome extends Fragment {

    final String PATH_TEMPERATURE= "temperature";
    final String PATH_LED_SALON="led_salon";
    final String PATH_LED_CUISINE= "led_cuisine";

    DatabaseReference ref;
    Button btnTemp, btnHum, btnAllumerAmpouleSalon, btnEteindreAmpouleSalon, btnAllumerAmpouleCuisine, btnEteindreAmpouleCuisine;
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

        View view= inflater.inflate(R.layout.fragment_home, container, false);

        init(view);


        btnHum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = FirebaseDatabase.getInstance().getReference().child("test/humidite");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long value = (Long) snapshot.getValue();
                        humText.setText(value.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });

        btnTemp.setOnClickListener(
                v->{
                    ref = FirebaseDatabase.getInstance().getReference().child("test/temperature");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Long value = (Long) snapshot.getValue();
                            tempText.setText(value.toString()+" °C");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
                }
        );


        btnAllumerAmpouleSalon.setOnClickListener(
                v->{
                    allumer(new AmpouleSalon());
                }
        );

        btnEteindreAmpouleSalon.setOnClickListener(
                v->{
                    eteindre(new AmpouleSalon());
                }
        );

        btnAllumerAmpouleCuisine.setOnClickListener(
                v->{
                    allumer(new AmpouleCuisine());
                }
        );

        btnEteindreAmpouleCuisine.setOnClickListener(
                v->{
                    eteindre(new AmpouleCuisine());
                }
        );


        return view;
    }

    private void init(View view){
        btnTemp = view.findViewById(R.id.btnTemp);
        tempText = view.findViewById(R.id.temp);
        btnHum = view.findViewById(R.id.btnHum);
        humText = view.findViewById(R.id.hum);

        btnAllumerAmpouleSalon = view.findViewById(R.id.btnAllumer);
        btnEteindreAmpouleSalon = view.findViewById(R.id.btnEteindre);

        btnAllumerAmpouleCuisine = view.findViewById(R.id.btnAllumerCuisine);
        btnEteindreAmpouleCuisine = view.findViewById(R.id.btnEteindreCuisine);
    }


    // allumer led
    private void allumer(Ampoule ampoule){
        String cheminAmpoule= ampoule.getCheminAmpoule();
        ref = FirebaseDatabase.getInstance().getReference().child(cheminAmpoule);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = (String) snapshot.getValue();
                if (value.equals("OFF")){
                    ref.setValue("ON");
                }
                else if(value.equals("ON")){
                    Toast.makeText(getContext(), "La lampe est deja allumée", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Nous avons rencontré un probleme", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    //Eteindre led
    private void eteindre(Ampoule ampoule){
        String cheminAmpoule= ampoule.getCheminAmpoule();
        ref = FirebaseDatabase.getInstance().getReference().child(cheminAmpoule);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = (String) snapshot.getValue();
                if (value.equals("ON")){
                    ref.setValue("OFF");
                }
                else if(value.equals("OFF")){
                    Toast.makeText(getContext(), "La lampe est deja éteinte", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Nous avons rencontré un probleme", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    // Lire Humidite ou temperature

    private void lireTempHum(String s){
        ref = FirebaseDatabase.getInstance().getReference().child("test/"+s);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long value = (Long) snapshot.getValue();
                if(s.equals("temp")){
                    tempText.setText(value.toString());
                }
                else if(s.equals("hum")){
                    humText.setText(value.toString());
                }
                else{
                    System.out.println("Veuillez verifier le parametre");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}