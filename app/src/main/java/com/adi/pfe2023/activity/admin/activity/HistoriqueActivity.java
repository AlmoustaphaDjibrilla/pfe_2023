package com.adi.pfe2023.activity.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.adi.pfe2023.R;
import com.adi.pfe2023.action.Commande;
import com.adi.pfe2023.adapter.AdapterListCommandes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HistoriqueActivity extends AppCompatActivity {

    final String PATH_COMMANDE= "Commandes";
    ListView listCommandesAdmin;
    ImageView imgQuitter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        init();

        CollectionReference collectionReference=
                FirebaseFirestore.getInstance()
                        .collection(PATH_COMMANDE);

        collectionReference
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Commande> resultatCommandes= new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            resultatCommandes.add(documentSnapshot.toObject(Commande.class));
                        }
                        AdapterListCommandes adapterListCommandes= new AdapterListCommandes(getApplicationContext(), resultatCommandes);
                        listCommandesAdmin.setAdapter(adapterListCommandes);
                    }
                });

        imgQuitter.setOnClickListener(
                v-> finish()
        );
    }

    private void init(){
        listCommandesAdmin= findViewById(R.id.listCommandesAdmin);
        imgQuitter= findViewById(R.id.imgQuitterHistoriqueAdmin);
    }
}