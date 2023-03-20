package com.adi.pfe2023.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adi.pfe2023.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuUser extends AppCompatActivity {
    DatabaseReference ref;
    Button btnTemp,btnHum;
    TextView tempText,humText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);

        init();
        /*btnTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = FirebaseDatabase.getInstance().getReference().child("test/temperature");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long value = (Long) snapshot.getValue();
                        tempText.setText(value.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });
        */
        btnHum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = FirebaseDatabase.getInstance().getReference().child("test/humidite");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long value = (Long) snapshot.getValue();
                        humText.setText(value.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });


    }

    // allumer led
    private void allumer(String lampe){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("test/leds/"+lampe);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long value = (Long) snapshot.getValue();
                if (value.toString().equals("OFF")){
                    ref.setValue("ON");
                }
                else if(value.toString().equals("OFF")){
                    Toast.makeText(MenuUser.this, "La lampe est deja allumer", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MenuUser.this, "Nous avons rencontre un probleme", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    //Eteindre led
    private void eteindre(String lampe){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("test/leds/"+lampe);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long value = (Long) snapshot.getValue();
                if (value.toString().equals("ON")){
                    ref.setValue("OFF");
                }
                else if(value.toString().equals("OFF")){
                    Toast.makeText(MenuUser.this, "La lampe est deja eteinte", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MenuUser.this, "Nous avons rencontre un probleme", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    // Lire Humidite ou temperature

    private void lireTempHum(String s){
        ref = FirebaseDatabase.getInstance().getReference().child("test/"+s);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long value = (Long) snapshot.getValue();
                if(s.equals("temp")){
                    tempText.setText(value.toString());
                }
                else if(s.equals("hum")){
                    humText.setText(value.toString());
                }
                else{
                    System.out.println("Veuillez verifier le parametre");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void init(){
        btnTemp = findViewById(R.id.btnTemp);
        tempText = findViewById(R.id.temp);
        btnHum = findViewById(R.id.btnHum);
        humText = findViewById(R.id.hum);
    }

}