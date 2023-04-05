package com.adi.pfe2023.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.adi.pfe2023.R;
import com.adi.pfe2023.model.FirebaseUtils;
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleChambre;
import com.adi.pfe2023.objet.ampoule.AmpouleCuisine;
import com.adi.pfe2023.objet.ampoule.AmpouleDouche;
import com.adi.pfe2023.objet.ampoule.AmpouleSalon;

public class Ampoules extends AppCompatActivity {
    private Switch SwitchCh, SwitchS, SwitchD, SwitchC;
    Ampoule ampouleChambre = AmpouleChambre.getInstance();
    Ampoule ampouleSalon = AmpouleSalon.getInstance();
    Ampoule ampouleDouche = AmpouleDouche.getInstance();
    Ampoule ampouleCuisine = AmpouleCuisine.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ampoules);
        init();
        SwitchCh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        SwitchS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ampouleSalon.allumer(ampouleSalon);
                }
                else{
                    ampouleSalon.eteindre(ampouleSalon);
                }
            }
        });
        SwitchC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ampouleCuisine.allumer(ampouleCuisine);
                }
                else{
                    ampouleCuisine.eteindre(ampouleCuisine);
                }
            }
        });
        SwitchD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ampouleDouche.allumer(ampouleDouche);
                }
                else{
                    ampouleDouche.eteindre(ampouleDouche);
                }
            }
        });
    }

    private void init(){
        SwitchCh = findViewById(R.id.switchCh);
        SwitchS = findViewById(R.id.switchS);
        SwitchC = findViewById(R.id.switchC);
        SwitchD = findViewById(R.id.switchD);

        //ETAT LAMPE CHAMBRE
        FirebaseUtils.getValueFromFirebase(ampouleChambre.getCheminAmpoule(), String.class, new FirebaseUtils.OnValueReceivedListener<String>() {
            @Override
            public void onValueReceived(String value)
            {

                if(value.equals("ON")){
                    SwitchCh.setChecked(true);
                }
                else {
                    SwitchCh.setChecked(false);
                }
            }
        });

        //ETAT LAMPE SALON
        FirebaseUtils.getValueFromFirebase(ampouleSalon.getCheminAmpoule(), String.class, new FirebaseUtils.OnValueReceivedListener<String>() {
            @Override
            public void onValueReceived(String value)
            {

                if(value.equals("ON")){
                    SwitchS.setChecked(true);
                }
                else {
                    SwitchS.setChecked(false);
                }
            }
        });

        //ETAT LAMPE CUISINE
        FirebaseUtils.getValueFromFirebase(ampouleCuisine.getCheminAmpoule(), String.class, new FirebaseUtils.OnValueReceivedListener<String>() {
            @Override
            public void onValueReceived(String value)
            {

                if(value.equals("ON")){
                    SwitchC.setChecked(true);
                }
                else {
                    SwitchC.setChecked(false);
                }
            }
        });

        //ETAT LAMPE DOUCHE
        FirebaseUtils.getValueFromFirebase(ampouleDouche.getCheminAmpoule(), String.class, new FirebaseUtils.OnValueReceivedListener<String>() {
            @Override
            public void onValueReceived(String value)
            {

                if(value.equals("ON")){
                    SwitchD.setChecked(true);
                }
                else {
                    SwitchD.setChecked(false);
                }
            }
        });

    }
}