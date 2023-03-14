package com.adi.pfe2023.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.adi.pfe2023.R;
import com.adi.pfe2023.activity.RegisterActivity;

public class AdminMainActivity extends AppCompatActivity {
    Button btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        init();

        btnAddUser.setOnClickListener(
                v->{
                    addNewUser();
                }
        );
    }

    private void addNewUser() {
        Intent intentNewUser= new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intentNewUser);
    }


    private void init(){
        btnAddUser= findViewById(R.id.btnAddUser);
    }
}