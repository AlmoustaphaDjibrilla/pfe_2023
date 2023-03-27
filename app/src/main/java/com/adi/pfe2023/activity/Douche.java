package com.adi.pfe2023.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adi.pfe2023.R;
import com.adi.pfe2023.model.FirebaseUtils;
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleDouche;
import com.adi.pfe2023.objet.meteo.Meteo;

public class Douche extends AppCompatActivity {
    private Switch lampe;
    private TextView humC,tempC;
    Meteo meteo = Meteo.getInstance();
    Ampoule A = AmpouleDouche.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douche);
        init();
        lampe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    A.allumer(A);
                }
                else{
                    A.eteindre(A);
                }
            }
        });
    }
    private void init(){
        lampe= findViewById(R.id.switchD);
        humC =findViewById(R.id.humD);
        tempC=findViewById(R.id.tempD);
        FirebaseUtils.getValueFromFirebase(A.getCheminAmpoule(), String.class, new FirebaseUtils.OnValueReceivedListener<String>() {
            @Override
            public void onValueReceived(String value)
            {

                if(value.equals("ON")){
                    lampe.setChecked(true);
                }
                else {
                    lampe.setChecked(false);
                }
            }
        });
        FirebaseUtils.getValueFromFirebase(meteo.getCheminTemperature(), Long.class, new FirebaseUtils.OnValueReceivedListener<Long>() {
            @Override
            public void onValueReceived(Long value) {
                tempC.setText(value+" C");
            }
        });

        FirebaseUtils.getValueFromFirebase(meteo.getCheminHumidite(), Long.class, new FirebaseUtils.OnValueReceivedListener<Long>() {
            @Override
            public void onValueReceived(Long value) {
                humC.setText(value+" %");
            }
        });

    }
}