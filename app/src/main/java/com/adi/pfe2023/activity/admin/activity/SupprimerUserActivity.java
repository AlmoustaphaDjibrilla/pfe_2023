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
import com.google.firebase.auth.FirebaseAuth;
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

        //Click sur le button Chercher
        //Qui permet de chercher un Utilisateur dans la BD
        //via un Email
        btnSearchUser.setOnClickListener(
                v->{
                    String mail= txtMailSupprime.getText().toString();

                    CollectionReference collectionReference=
                            FirebaseFirestore.getInstance()
                                    .collection(PATH_USER_DATABASE);

                    // Vérifier que l'admin a saisi quelque chose
                    if (mail!=null && !mail.equals("")) {
                        collectionReference
                                .whereEqualTo("email", mail)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        ArrayList<UserModel> lesUsers = new ArrayList<>();

                                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                            UserModel userModel = doc.toObject(UserModel.class);
                                            lesUsers.add(userModel);
                                        }
                                        //Prendre le premier élément de la liste
                                        //au cas où y aurait plusieurs documents
                                        //qui satisfont à la requete donnée
                                        if (lesUsers.size() > 0) {
                                            UserModel userModel = queryDocumentSnapshots.getDocuments().get(0).toObject(UserModel.class);
                                            dialog.setContentView(R.layout.modele_user_supprimer);
                                            composantsDialog();
                                            remplirChamps(userModel);

                                            //Affichage du Dialog sur l'écran de l'utilisateur
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            dialog.show();

                                            //Click sur le button Supprimer
                                            btnSupprimerUser.setOnClickListener(
                                                    v -> {
                                                        //Affecter la connexion actuelle au User qu'on voudrait supprimer
                                                        //Cela permettra de supprimer le User non seulement dans le FireStore
                                                        //mais aussi dans la liste Authentification de Firebase
                                                        FirebaseAuth.getInstance()
                                                                .signInWithEmailAndPassword(userModel.getEmail(), userModel.getPassword())
                                                                .addOnSuccessListener(
                                                                        c->{
                                                                            if (c!=null)
                                                                                c.getUser().delete();
                                                                        }
                                                                );

                                                        DocumentReference documentReference =
                                                                FirebaseFirestore.getInstance()
                                                                        .collection(PATH_USER_DATABASE)
                                                                        .document(userModel.getUid());

                                                        //Suppression du UserModel dans la BD FireStore
                                                        documentReference.delete();
                                                        dialog.dismiss();
                                                    }
                                            );
                                        }
                                    }
                                });
                    }else
                        txtMailSupprime.setError("Veuillez saisir le mail ");
                });

    }


    /**
     * Initialiser les composants
     * de l'activite SupprimerUser
     */
    private void init(){
        txtMailSupprime= findViewById(R.id.txtMailUserSupprime);
        btnSearchUser = findViewById(R.id.btnSearchUserSupprime);
        imgQuitter= findViewById(R.id.imgQuitterSuppressionUser);
    }


    /**
     * Initialiser les composansts graphiques
     * de l'objet de type Dialog qui va s'echapper
     */
    private void composantsDialog(){
        txtMailUserSupprime= dialog.findViewById(R.id.mailUserSupprime);
        txtNomUserSupprime= dialog.findViewById(R.id.nomUserSupprime);
        txtDateEnregistrementUserSupprime= dialog.findViewById(R.id.dateEnregistrementUserSupprime);
        txtUidUserSupprime= dialog.findViewById(R.id.uidUserSupprime);
        btnSupprimerUser= dialog.findViewById(R.id.btnSupprimerUser);
    }


    /**
     * Remplir les composants de
     * l'objet Dialog qui va permettre
     * d'afficher les informations de
     * l'utilisatreur qu'on vourait supprimer
     * @param userModel, qui represente le UserModel
     *                   qu'on voudrait supprimer
     */
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