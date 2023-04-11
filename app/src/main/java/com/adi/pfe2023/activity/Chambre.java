package com.adi.pfe2023.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adi.pfe2023.R;
import com.adi.pfe2023.model.FirebaseUtils;
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleChambre;
import com.adi.pfe2023.objet.meteo.Meteo;
import com.adi.pfe2023.objet.porte.Porte;
import com.adi.pfe2023.objet.porte.PorteChambre;

public class Chambre extends AppCompatActivity {
    private Switch lampe;
    private SeekBar porte;
    Meteo meteo = Meteo.getInstance();
    Porte porteChambre = PorteChambre.getInstance();
    Ampoule ampouleChambre = AmpouleChambre.getInstance();
    private TextView humC,tempC;


    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_chambre);
        init();
        lampe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ampouleChambre.allumer(ampouleChambre);
                }
                else{
                    ampouleChambre.eteindre(ampouleChambre);
                }
            }
        });

        //Porte
        porte.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void init(){
        lampe= findViewById(R.id.switchCh);
        humC =findViewById(R.id.humC);
        tempC=findViewById(R.id.tempC);
        porte=findViewById(R.id.PC);
        FirebaseUtils.getValueFromFirebase(ampouleChambre.getCheminAmpoule(), String.class, new FirebaseUtils.OnValueReceivedListener<String>() {
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
        FirebaseUtils.getValueFromFirebase(porteChambre.getCheminPorte(), Long.class, new FirebaseUtils.OnValueReceivedListener<Long>() {
            @Override
            public void onValueReceived(Long value) {
                if(Math.toIntExact(value)==90){
                    porte.setProgress(1);
                } else if (Math.toIntExact(value)==0){
                    porte.setProgress(0);
                }
                else{
                    porte.setProgress(2);
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