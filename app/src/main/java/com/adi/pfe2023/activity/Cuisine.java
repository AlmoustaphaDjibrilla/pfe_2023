package com.adi.pfe2023.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.adi.pfe2023.R;
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleCuisine;

public class Cuisine extends AppCompatActivity {
    private Switch lampe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine);
        init();
        init();
        lampe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Ampoule A = AmpouleCuisine.getInstance();
                    A.allumer(A);
                }
                else{
                    Ampoule B = AmpouleCuisine.getInstance();
                    B.eteindre(B);
                }
            }
        });
    }
    private void init(){
        lampe= findViewById(R.id.switchC);
    }

}