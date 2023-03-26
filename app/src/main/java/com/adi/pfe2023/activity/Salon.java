package com.adi.pfe2023.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.adi.pfe2023.R;
import com.adi.pfe2023.objet.ampoule.Ampoule;
import com.adi.pfe2023.objet.ampoule.AmpouleSalon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.ExecutionException;

public class Salon extends AppCompatActivity {
    private Switch lampe;
    private String etat_ampoule;
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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(A.getCheminAmpoule());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String etat=(String)snapshot.getValue();
                Log.d("TAG","Etat:"+etat);
                if(etat.equals("ON")){
                    lampe.setChecked(true);
                }
                else {
                    lampe.setChecked(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}