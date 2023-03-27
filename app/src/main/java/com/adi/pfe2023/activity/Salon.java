package com.adi.pfe2023.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adi.pfe2023.R;
import com.adi.pfe2023.model.FirebaseUtils;
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleSalon;
import com.adi.pfe2023.objet.meteo.Meteo;

public class Salon extends AppCompatActivity {
    private Switch lampe;
    private TextView humC,tempC;
    Meteo meteo = Meteo.getInstance();
    Ampoule A = AmpouleSalon.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);
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
        lampe= findViewById(R.id.switchS);
        humC =findViewById(R.id.humS);
        tempC=findViewById(R.id.tempS);
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