package com.adi.pfe2023.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.adi.pfe2023.R;
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleChambre;
import com.google.firebase.database.DatabaseReference;

public class Chambre extends AppCompatActivity {
    private Switch lampe;

    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_chambre);
        init();
        lampe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Ampoule A = AmpouleChambre.getInstance();
                    A.allumer(A);
                }
                else{
                    Ampoule B = AmpouleChambre.getInstance();
                    B.eteindre(B);
                }
            }
        });
    }

    private void init(){
        lampe= findViewById(R.id.switchCh);
    }


}