package com.adi.pfe2023.activity.admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adi.pfe2023.R;
import com.adi.pfe2023.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChercherUser extends AppCompatActivity {
    final String PATH_USER_DATABASE= "Users";

    CollectionReference collectionReference;

    ImageView imgQuitter;

    EditText txtMailSearchUser, txtNomSearchUser, txtDateEnregistrementSearchUser;
    Button btnSearchUser;
    Dialog dialog;



    TextView txtNomModelUser, txtMailModelUser, txtDateEnregistrementUser, txtUidUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_user);
        dialog= new Dialog(this);

        init();

        imgQuitter.setOnClickListener(
                v->finish()
        );


        txtNomModelUser= dialog.findViewById(R.id.nomUserSupprime);
        txtMailModelUser= dialog.findViewById(R.id.mailUserSupprime);
        txtDateEnregistrementUser= dialog.findViewById(R.id.dateEnregistrementUserSupprime);

        btnSearchUser.setOnClickListener(
                v->{
                    String mail= txtMailSearchUser.getText().toString();
                    String nom= txtNomSearchUser.getText().toString();
                    String dateEnregistrement= txtDateEnregistrementSearchUser.getText().toString();

                    collectionReference=
                            FirebaseFirestore.getInstance()
                                    .collection(PATH_USER_DATABASE);

                    if (mail!= null && !mail.equals("")){
                        chercherDonnes("email", mail);
                    } else if (nom!=null && !nom.equals("")) {
                        chercherDonnes("nom", nom);
                    }
                    else if(dateEnregistrement!=null && !dateEnregistrement.equals("")){
                        chercherDonnes("dateEnregistrement", dateEnregistrement);
                    }

                }
        );
    }

    private void init(){
        txtMailSearchUser= findViewById(R.id.txtMailSearchUser);
        txtNomSearchUser= findViewById(R.id.txtNomSearchUser);
        txtDateEnregistrementSearchUser= findViewById(R.id.txtDateEnregisrementSearchUser);
        btnSearchUser= findViewById(R.id.btnSearchUser);
        imgQuitter= findViewById(R.id.imgQuitterRechercheUser);
    }


    /**
     * Initialiser les composants du dialog qui va
     * afficher les users trouvers
     */
    private void composantsDialog(){
        txtNomModelUser= dialog.findViewById(R.id.nomUserSupprime);
        txtMailModelUser= dialog.findViewById(R.id.mailUserSupprime);
        txtDateEnregistrementUser= dialog.findViewById(R.id.dateEnregistrementUserSupprime);
        txtUidUser= dialog.findViewById(R.id.uidUserSupprime);
    }

    private void remplirChamps(UserModel userModel){
        if (userModel!=null){
            if (userModel.getNom()!=null){
                txtNomModelUser.setText(userModel.getNom());
            }
            if (userModel.getEmail()!=null){
                txtMailModelUser.setText(userModel.getEmail());
            }
            if (userModel.getDateEnregistrement()!=null){
                txtDateEnregistrementUser.setText(userModel.getDateEnregistrement());
            }
            if (userModel.getUid()!=null){
                txtUidUser.setText(userModel.getUid());
            }
        }
    }

    private void chercherDonnes(String nomField, String value){

        collectionReference
                .whereEqualTo(nomField, value)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<UserModel> lesUsersRecherche= new ArrayList<>();

                        for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
                            UserModel userModel= doc.toObject(UserModel.class);
                            lesUsersRecherche.add(userModel);
                        }

                        if (lesUsersRecherche.size()>0) {
                            UserModel userModel = queryDocumentSnapshots.getDocuments().get(0).toObject(UserModel.class);
                            dialog.setContentView(R.layout.modele_user_recherche);
                            composantsDialog();
                            remplirChamps(userModel);

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_LONG).show();

                    }
                });
    }

}