package com.adi.pfe2023.activity.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.adi.pfe2023.R;
import com.adi.pfe2023.action.Commande;
import com.adi.pfe2023.adapter.AdapterListCommandes;
import com.adi.pfe2023.adapter.AdapterListUser;
import com.adi.pfe2023.model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllUsersActivity extends AppCompatActivity {

    private final String PATH_USER_DATABASE= "Users";

    ListView listUsers;
    ImageView imgQuitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        init();

        CollectionReference collectionReference=
                FirebaseFirestore.getInstance()
                        .collection(PATH_USER_DATABASE);

        collectionReference
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<UserModel> lesUsers= new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            lesUsers.add(documentSnapshot.toObject(UserModel.class));
                        }
                        AdapterListUser adapterListUsers= new AdapterListUser(getApplicationContext(), lesUsers);
                        listUsers.setAdapter(adapterListUsers);
                    }
                });

        imgQuitter.setOnClickListener(
                v-> finish()
        );

    }


    /**
     * Initialisation des composants de l'activite courante
     */
    private void init(){
        listUsers= findViewById(R.id.listUsers);
        imgQuitter= findViewById(R.id.imgQuitterListUsers);
    }
}