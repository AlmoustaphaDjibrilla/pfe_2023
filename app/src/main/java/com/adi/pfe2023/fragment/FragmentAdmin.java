package com.adi.pfe2023.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adi.pfe2023.R;
import com.adi.pfe2023.activity.admin.AdminMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentAdmin extends Fragment {

    private final String PATH_USER_DATABASE= "Users";

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    Button btnLogin;
    EditText txtMailLoginAdmin, txtPasswordLoginAdmin;
    TextView txtPasswordOublieAdmin;


    public FragmentAdmin() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin, container, false);

        //init
        init(view);

        btnLogin.setOnClickListener(
                v->{
                    String mail= txtMailLoginAdmin.getText().toString();
                    String password= txtPasswordLoginAdmin.getText().toString();

                    loginMethod(mail, password);
        });


        txtPasswordOublieAdmin.setOnClickListener(
                v->{
                    String mail= txtMailLoginAdmin.getText().toString();
                    resetPasswordMethod(mail);
                }
        );



        return view;
    }


    /**
     * Initialisation
     * @param view
     */
    private void init(View view){
        //Initialisation Firebase Auth
        mAuth= FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();

        btnLogin= view.findViewById(R.id.btnLoginAdmin);
        txtMailLoginAdmin= view.findViewById(R.id.txtMailLoginAdmin);
        txtPasswordLoginAdmin= view.findViewById(R.id.txtPasswordLoginAdmin);
        txtPasswordOublieAdmin= view.findViewById(R.id.txtPasswordOublieAdmin);
    }


    /**
     * Cette méthode permet à
     * l'administrateur
     * de se connecter au système
     * grâce à son mail et password
     * @param mail
     * @param password
     */
    private void loginMethod(String mail, String password){
        if (mail==null || password==null || mail.equals("") || password.equals("")){
            Toast.makeText(getContext(), "Veuillez saisir tous les champs", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(mail, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            firebaseUser= mAuth.getCurrentUser();

                            getUserModelFromFirebase(firebaseUser);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Données non correspondants", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }



    private void getUserModelFromFirebase(FirebaseUser firebaseUser){
        DocumentReference doc=
                firebaseFirestore.collection(PATH_USER_DATABASE)
                        .document(firebaseUser.getUid());



        doc.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.getBoolean("admin")){
                            Toast.makeText(getContext(), "Bienvenue à l'interface ADMINISTRATEUR...", Toast.LENGTH_LONG).show();
                            Intent intentAdminMainPage= new Intent(getContext(), AdminMainActivity.class);
                            startActivity(intentAdminMainPage);
                        }
                        else {
                            Toast.makeText(getContext(), "Désolé, vous n'êtes pas un administrateur", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Aucune réponse...", Toast.LENGTH_LONG).show();

                    }
                });
    }


    /**
     * envoyer un mail pour pouvoir réinitialiser le mot de passe
     * du user ayant le mail spécifié
     * @param mail
     */
    private void resetPasswordMethod(String mail){
        if (mail==null || mail.equals("")){
            Toast.makeText(getContext(), "Veuillez saisir votre mail...", Toast.LENGTH_SHORT).show();
            txtMailLoginAdmin.setError("Erreur");
        }
        else {
            mAuth.sendPasswordResetEmail(mail)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Consultez votre messagerie électronique", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getContext(), "Echec d'envoi de mail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}