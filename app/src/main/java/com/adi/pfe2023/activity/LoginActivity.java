package com.adi.pfe2023.activity;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adi.pfe2023.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {

    private FirebaseAuth mAuth;

    TextView txtCreerCompte, txtPasswordOublie;
    EditText txtMailLogin, txtPasswordLogin;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialisation des différents composants de l'interface graphique
        init();

        //Initialisation Firebase Auth
        mAuth= FirebaseAuth.getInstance();

        txtCreerCompte.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent createAccount= new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(createAccount);
                    }
                }
        );
    }


    /**
     * Initialisation des différents composants de l'interface graphique
     */
    private void init(){

        txtCreerCompte= findViewById(R.id.txtCreerCompte);
        btnLogin= findViewById(R.id.btnLogin);
        txtPasswordOublie= findViewById(R.id.txtPasswordOublie);

        txtMailLogin=findViewById(R.id.txtMailLogin);
        txtPasswordLogin= findViewById(R.id.txtPasswordLogin);

    }
}