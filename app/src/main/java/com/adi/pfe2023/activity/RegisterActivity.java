package com.adi.pfe2023.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.adi.pfe2023.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends Activity {

    private FirebaseAuth mAuth;
    TextView txtEnregistre, txtNom, txtMail, txtPassword1, txtPassword2;
    Button btnSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialiser les elements
        init();

        //Initialisation Firebase Auth
        mAuth= FirebaseAuth.getInstance();
        //Create a new User
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom= txtNom.getText().toString();
                String mail= txtMail.getText().toString();
                String password= txtPassword1.getText().toString();

                mAuth.createUserWithEmailAndPassword(mail, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                 Toast.makeText(RegisterActivity.this, "User crée avec succès", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, "Echec d'ajout!!!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }

    private void init(){
        txtEnregistre= findViewById(R.id.txtEnregistre);
        txtNom= findViewById(R.id.txtNom);
        txtMail= findViewById(R.id.txtMail);
        txtPassword1= findViewById(R.id.txtPassword1);
        txtPassword2= findViewById(R.id.txtPassword2);

        btnSave= findViewById(R.id.btnSave);
    }


}