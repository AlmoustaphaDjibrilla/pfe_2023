package com.adi.pfe2023.activity;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.adi.pfe2023.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class LoginActivity extends Activity implements Serializable {

    private final String PATH_USER_DATABASE= "Users";

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    TextView txtCreerCompte, txtPasswordOublie;
    EditText txtMailLogin, txtPasswordLogin;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialisation des différents composants de l'interface graphique
        init();



        /**
         * Click sur le button login pour se connecter
         */
        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mail= txtMailLogin.getText().toString();
                        String password= txtPasswordLogin.getText().toString();

                        loginMethod(mail, password);

                    }
                }
        );

        /**
         * Click sur le lien "mot de passe oublié"
         * pour recevoir un lien de réinitialisation de mot de passe
         */
        txtPasswordOublie.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mail= txtMailLogin.getText().toString();

                        resetPasswordMethod(mail);
                    }
                }
        );
    }


    /**
     * Initialisation des différents composants de l'interface graphique
     */
    private void init(){
        //Initialisation Firebase Auth
        mAuth= FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();

        btnLogin= findViewById(R.id.btnLoginAdmin);
        txtPasswordOublie= findViewById(R.id.txtPasswordOublieAdmin);

        txtMailLogin=findViewById(R.id.txtMailLoginAdmin);
        txtPasswordLogin= findViewById(R.id.txtPasswordLoginAdmin);
    }


    /**
     * Cette méthode permet à
     * un quelconque utilisateur
     * de se connecter au système
     * grâce à son mail et password
     * @param mail
     * @param password
     */
    private void loginMethod(String mail, String password){
        if (mail==null || password==null || mail.equals("") || password.equals("")){
            Toast.makeText(LoginActivity.this, "Veuillez saisir tous les champs", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(mail, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getApplicationContext(), "Authentification réussi", Toast.LENGTH_SHORT).show();
                            firebaseUser= mAuth.getCurrentUser();
                            getUserModelFromFirebase(firebaseUser);
                            ouvrirMainPage();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Données non correspondants", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    /**
     * envoyer un mail pour pouvoir réinitialiser le mot de passe
     * du user ayant le mail spécifié
     * @param mail
     */
    private void resetPasswordMethod(String mail){
        if (mail==null || mail.equals("")){
            Toast.makeText(getApplicationContext(), "Veuillez saisir votre mail...", Toast.LENGTH_SHORT).show();
            txtMailLogin.setError("Erreur");
        }
        else {
            mAuth.sendPasswordResetEmail(mail)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Consultez votre messagerie électronique", Toast.LENGTH_LONG).show();
                            Intent mailClient = new Intent(Intent.ACTION_VIEW);
                            mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail");
                            if (mailClient !=null){
                                startActivity(mailClient);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Echec d'envoi de mail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void ouvrirMainPage(){
        Intent ouvrirMainPage= new Intent(getApplicationContext(), MainPageActivity.class);
        ouvrirMainPage.putExtra("fireBaseUser", firebaseUser);
        startActivity(ouvrirMainPage);
        finish();
    }


    private void getUserModelFromFirebase(FirebaseUser firebaseUser){
        DocumentReference doc=
                firebaseFirestore.collection(PATH_USER_DATABASE)
                        .document(firebaseUser.getUid());

        UserModel userModel=null;


        doc.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.getBoolean("admin")){
                          Toast.makeText(getApplicationContext(), "Administrateur", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "User", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Doc non trouvé", Toast.LENGTH_LONG).show();

                    }
                });
    }
}