package com.adi.pfe2023.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.adi.pfe2023.model.FirebaseUtils;
import com.adi.pfe2023.objet.meteo.Meteo;
import com.google.firebase.database.DatabaseReference;

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
        D1 = (CardView)view.findViewById(R.id.d1);
        D2 = (CardView)view.findViewById(R.id.d2);
        D3 = (CardView)view.findViewById(R.id.d3);
        D4 = (CardView)view.findViewById(R.id.d4);
        D5 = (CardView)view.findViewById(R.id.d5);
        D6 = (CardView)view.findViewById(R.id.d6);

        temptxt = view.findViewById(R.id.temperature);
        humtxt = view.findViewById(R.id.humidite);
        lireTemperature(Meteo.getInstance());
        lireHumidite(Meteo.getInstance());

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

    private void lireTemperature(Meteo meteo){
        FirebaseUtils.getValueFromFirebase(meteo.getCheminTemperature(), Long.class, new FirebaseUtils.OnValueReceivedListener<Long>() {
            @Override
            public void onValueReceived(Long value) {
                temptxt.setText(value+" C");
            }
        });
    }

    private void lireHumidite(Meteo meteo){
        FirebaseUtils.getValueFromFirebase(meteo.getCheminHumidite(), Long.class, new FirebaseUtils.OnValueReceivedListener<Long>() {
            @Override
            public void onValueReceived(Long value) {
                humtxt.setText(value+" %");
            }
        });

    }


}