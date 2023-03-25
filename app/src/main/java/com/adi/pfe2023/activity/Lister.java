package com.adi.pfe2023.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.adi.pfe2023.R;
import com.adi.pfe2023.adapter.AdapterListUser;
import com.adi.pfe2023.model.UserModel;

import java.io.Serializable;
import java.util.ArrayList;

public class Lister extends AppCompatActivity implements Serializable {

    public Lister(){

    }

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister);
        init();

        Intent recevoirDonnes= getIntent();
        ArrayList<UserModel> lesUsers= (ArrayList<UserModel>) recevoirDonnes.getSerializableExtra("lesUsers");

        AdapterListUser adp= new AdapterListUser(getApplicationContext(), lesUsers);
        list.setAdapter(adp);

    }

    private void init(){
        list= findViewById(R.id.listerList);
    }
}