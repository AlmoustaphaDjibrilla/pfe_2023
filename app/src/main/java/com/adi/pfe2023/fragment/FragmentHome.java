package com.adi.pfe2023.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.adi.pfe2023.R;
import com.adi.pfe2023.activity.Ampoules;
import com.adi.pfe2023.activity.Chambre;
import com.adi.pfe2023.activity.Cuisine;
import com.adi.pfe2023.activity.Douche;
import com.adi.pfe2023.activity.Porte;
import com.adi.pfe2023.activity.Salon;
import com.adi.pfe2023.objet.meteo.Meteo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.C;

public class FragmentHome extends Fragment{
    private CardView D1,D2,D3,D4,D5,D6;
    private TextView temptxt, humtxt;
    private DatabaseReference databaseReference;

    public FragmentHome (){

    }

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lireTemperatureEtHumidite(Meteo.getInstance());

        D1 = (CardView)view.findViewById(R.id.d1);
        D2 = (CardView)view.findViewById(R.id.d2);
        D3 = (CardView)view.findViewById(R.id.d3);
        D4 = (CardView)view.findViewById(R.id.d4);
        D5 = (CardView)view.findViewById(R.id.d5);
        D6 = (CardView)view.findViewById(R.id.d6);

        temptxt = view.findViewById(R.id.temperature);
        humtxt = view.findViewById(R.id.humidite);

        D1.setOnClickListener(
                v->{
                    startActivity(new Intent(getContext(), Chambre.class));
                }
        );
        D2.setOnClickListener(
                v->{
                    startActivity(new Intent(getContext(), Salon.class));
                }
        );
        D3.setOnClickListener(
                v->{
                    startActivity(new Intent(getContext(), Cuisine.class));
                }
        );
        D4.setOnClickListener(
                v->{
                    startActivity(new Intent(getContext(), Douche.class));
                }
        );
        D5.setOnClickListener(
                v->{
                    startActivity(new Intent(getContext(), Porte.class));
                }
        );
        D6.setOnClickListener(
                v->{
                    startActivity(new Intent(getContext(), Ampoules.class));
                }
        );

        return view;

    }

    private void lireTemperatureEtHumidite(Meteo meteo){
        final String cheminTemperature= meteo.getCheminTemperature();
        final String cheminHumidite= meteo.getCheminHumidite();

        //Se positionner sur l'adresse de la temperature
        databaseReference= FirebaseDatabase.getInstance().getReference().child(cheminTemperature);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(cheminTemperature);
        databaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long value = (Long) snapshot.getValue();
                        if (value!=null) {
                            temptxt.setText(value+" °C");
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
                            humtxt.setText(value.toString()+" %");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                }
        );
    }


}