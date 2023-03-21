package com.adi.pfe2023.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.adi.pfe2023.R;
import com.adi.pfe2023.activity.RegisterActivity;
import com.adi.pfe2023.activity.admin.activity.ChercherUser;
import com.adi.pfe2023.activity.admin.activity.RechercheUserActivity;

public class AdminMainActivity extends AppCompatActivity {
    CardView cardAddUser, cardSearchUser, cardDeleteUser, cardListAllUsers, cardHistoriqueCommande, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        init();

        cardAddUser.setOnClickListener(
                v->{
                    addNewUser();
                }
        );

        cardSearchUser.setOnClickListener(
                v->{
                    Intent intentChercherUser= new Intent(getApplicationContext(), RechercheUserActivity.class);
                    startActivity(intentChercherUser);
                }
        );

        cardLogout.setOnClickListener(
                v->{
                    finish();
                }
        );
    }

    private void addNewUser() {
        Intent intentNewUser= new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intentNewUser);
    }


    private void init(){
        cardAddUser= findViewById(R.id.cardAddUser);
        cardSearchUser= findViewById(R.id.cardSearchUser);
        cardDeleteUser= findViewById(R.id.cardDeleteUser);
        cardListAllUsers= findViewById(R.id.cardListAllUsers);
        cardHistoriqueCommande= findViewById(R.id.cardHistoriqueCommande);
        cardLogout= findViewById(R.id.cardLogout);
    }
}