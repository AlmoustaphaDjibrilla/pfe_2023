package com.adi.pfe2023.activity.admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adi.pfe2023.R;
import com.adi.pfe2023.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChercherUser extends AppCompatActivity {
    final String PATH_USER_DATABASE= "Users";

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
        txtNomModelUser= dialog.findViewById(R.id.nomUserTrouve);
        txtMailModelUser= dialog.findViewById(R.id.mailUserTrouve);
        txtDateEnregistrementUser= dialog.findViewById(R.id.dateEnregistrementUserTrouve);

        btnSearchUser.setOnClickListener(
                v->{
                    String mail= txtMailSearchUser.getText().toString();
                    String nom= txtNomSearchUser.getText().toString();
                    String dateEnregistrement= txtDateEnregistrementSearchUser.getText().toString();

                    CollectionReference collectionReference=
                            FirebaseFirestore.getInstance()
                                    .collection(PATH_USER_DATABASE);

                    collectionReference
                            .whereEqualTo("email", mail)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                   ArrayList<UserModel> lesUsersRecherche= new ArrayList<>();
                                   UserModel userModel=queryDocumentSnapshots.getDocuments().get(0).toObject(UserModel.class);
                                   dialog.setContentView(R.layout.afficher_user);
                                   composantsDialog();
                                   remplirChamps(userModel);

                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.show();

//                                   for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
//                                       UserModel userModel= documentSnapshot.toObject(UserModel.class);
//                                       Toast.makeText(getApplicationContext(), userModel.toString(), Toast.LENGTH_LONG).show();
//                                       lesUsersRecherche.add(userModel);
//                                   }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_LONG).show();

                                }
                            });
                }
        );
    }

    private void init(){
        txtMailSearchUser= findViewById(R.id.txtMailSearchUser);
        txtNomSearchUser= findViewById(R.id.txtNomSearchUser);
        txtDateEnregistrementSearchUser= findViewById(R.id.txtDateEnregisrementSearchUser);
        btnSearchUser= findViewById(R.id.btnSearchUser);

        txtNomModelUser= findViewById(R.id.nomUserTrouve);
        txtMailModelUser= findViewById(R.id.mailUserTrouve);
        txtDateEnregistrementUser= findViewById(R.id.dateEnregistrementUserTrouve);
    }


    /**
     * Initialiser les composants du dialog qui va
     * afficher les users trouvers
     */
    private void composantsDialog(){
        txtNomModelUser= dialog.findViewById(R.id.nomUserTrouve);
        txtMailModelUser= dialog.findViewById(R.id.mailUserTrouve);
        txtDateEnregistrementUser= dialog.findViewById(R.id.dateEnregistrementUserTrouve);
        txtUidUser= dialog.findViewById(R.id.uidUserTrouve);
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


}