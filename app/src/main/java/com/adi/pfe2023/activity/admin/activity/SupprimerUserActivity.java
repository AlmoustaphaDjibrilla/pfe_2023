package com.adi.pfe2023.activity.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adi.pfe2023.R;
import com.adi.pfe2023.model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SupprimerUserActivity extends AppCompatActivity {

    final String PATH_USER_DATABASE= "Users";

    TextView txtMailSupprime, txtMailUserSupprime, txtNomUserSupprime, txtUidUserSupprime, txtDateEnregistrementUserSupprime;
    Button btnSearchUser, btnSupprimerUser;
    ImageView imgQuitter;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_user);

        init();

        imgQuitter.setOnClickListener(
                v->finish()
        );

        dialog= new Dialog(this);

        btnSearchUser.setOnClickListener(
                v->{
                    String mail= txtMailSupprime.getText().toString();

                    CollectionReference collectionReference=
                            FirebaseFirestore.getInstance()
                                    .collection(PATH_USER_DATABASE);

                    collectionReference
                            .whereEqualTo("email", mail)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    ArrayList<UserModel> lesUsers= new ArrayList<>();

                                    for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
                                        UserModel userModel= doc.toObject(UserModel.class);
                                        lesUsers.add(userModel);
                                    }
                                    if (lesUsers.size()>0) {
                                        UserModel userModel = queryDocumentSnapshots.getDocuments().get(0).toObject(UserModel.class);
                                        dialog.setContentView(R.layout.modele_user_supprimer);
                                        composantsDialog();
                                        remplirChamps(userModel);

                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialog.show();

                                        btnSupprimerUser.setOnClickListener(
                                                v -> {
                                                    DocumentReference documentReference =
                                                            FirebaseFirestore.getInstance()
                                                                    .collection(PATH_USER_DATABASE)
                                                                    .document(userModel.getUid());

                                                    documentReference.delete();
                                                    dialog.dismiss();
                                                }
                                        );
                                    }
                                }
                            });
                });
    }


    private void init(){
        txtMailSupprime= findViewById(R.id.txtMailUserSupprime);
        btnSearchUser = findViewById(R.id.btnSearchUserSupprime);
        imgQuitter= findViewById(R.id.imgQuitterSuppressionUser);
    }


    private void composantsDialog(){
        txtMailUserSupprime= dialog.findViewById(R.id.mailUserSupprime);
        txtNomUserSupprime= dialog.findViewById(R.id.nomUserSupprime);
        txtDateEnregistrementUserSupprime= dialog.findViewById(R.id.dateEnregistrementUserSupprime);
        txtUidUserSupprime= dialog.findViewById(R.id.uidUserSupprime);
        btnSupprimerUser= dialog.findViewById(R.id.btnSupprimerUser);
    }

    private void remplirChamps(UserModel userModel){
        if (userModel!=null){
            if (userModel.getUid()!=null){
                txtUidUserSupprime.setText(userModel.getUid());
            }
            if (userModel.getNom()!=null){
                txtNomUserSupprime.setText(userModel.getNom());
            }
            if (userModel.getEmail()!=null){
                txtMailUserSupprime.setText(userModel.getEmail());
            }
            if (userModel.getDateEnregistrement()!=null){
                txtDateEnregistrementUserSupprime.setText(userModel.getDateEnregistrement());
            }
        }
    }
}