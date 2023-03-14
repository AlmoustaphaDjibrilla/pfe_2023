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
import androidx.annotation.Nullable;

import com.adi.pfe2023.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends Activity {

    final String PATH_USER_DATABASE= "Users";

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    TextView txtEnregistre, txtNom, txtMail, txtPassword1, txtPassword2;
    Button btnSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialiser les elements
        init();

        //Create a new User
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom= txtNom.getText().toString();
                String mail= txtMail.getText().toString();
                String password= txtPassword1.getText().toString();

                createUserWithEmailAndPassword(mail, password, nom);
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

        //Initialisation Firebase Auth
        mAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
    }


    /**
     * Ajouter un nouvel utilisateur
     * @param mail
     * @param password
     */
    private void createUserWithEmailAndPassword(String mail, String password, @Nullable String nom){
        if (mail==null || mail.equals("") || password==null || password.equals("")){
            Toast.makeText(RegisterActivity.this, "Veuillez saisir tous les champs", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(mail, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(RegisterActivity.this, "User crée avec succès", Toast.LENGTH_SHORT).show();

                            FirebaseUser firebaseUser= mAuth.getCurrentUser();
                            UserModel userModel= new UserModel(mail, nom);
                            ajoutUserDataBase(firebaseUser, userModel);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Echec d'ajout!!!", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }


    /**
     * En plus de l'enregistrement
     * ajouter le nouvel user
     * dans une base de données
     * pour faciliter les traffics
     * @param firebaseUser
     * @param userModel
     */
    private void ajoutUserDataBase(FirebaseUser firebaseUser, UserModel userModel){
        DocumentReference documentReference=
                firebaseFirestore.collection(PATH_USER_DATABASE)
                        .document(firebaseUser.getUid());
        userModel.setUid(firebaseUser.getUid());
        documentReference.set(userModel);
    }


}