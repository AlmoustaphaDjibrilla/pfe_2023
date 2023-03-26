package com.adi.pfe2023.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.adi.pfe2023.R;
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleSalon;

public class Salon extends AppCompatActivity {
    private Switch lampe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);
        init();
        lampe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Ampoule A = AmpouleSalon.getInstance();
                    A.allumer(A);
                }
                else{
                    Ampoule B = AmpouleSalon.getInstance();
                    B.eteindre(B);
                }
            }
        });
    }
    private void init(){
        lampe= findViewById(R.id.switchS);
    }

}