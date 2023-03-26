package com.adi.pfe2023.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.adi.pfe2023.R;
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleDouche;

public class Douche extends AppCompatActivity {
    private Switch lampe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douche);
        init();
        lampe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Ampoule A = AmpouleDouche.getInstance();
                    A.allumer(A);
                }
                else{
                    Ampoule B = AmpouleDouche.getInstance();
                    B.eteindre(B);
                }
            }
        });
    }
    private void init(){
        lampe= findViewById(R.id.switchD);
    }
}