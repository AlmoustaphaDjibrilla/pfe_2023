package com.adi.pfe2023.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adi.pfe2023.R;
import com.adi.pfe2023.action.Commande;
import com.adi.pfe2023.adapter.AdapterListCommandes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CommandesUserActivity extends AppCompatActivity {
    final String PATH_COMMANDE= "Commandes";
    ListView listCommandes;
    ArrayList<Commande> lesCommandes;

    TextView txtMailUser1Commande;
    ImageView imgQuitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes_user);

        init();

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String mail= user.getEmail();

        txtMailUser1Commande.setText(mail);

        CollectionReference collectionReference=
                FirebaseFirestore.getInstance()
                        .collection(PATH_COMMANDE);

        collectionReference.whereEqualTo("emailUser", mail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Commande> resultatCommandes= new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            resultatCommandes.add(documentSnapshot.toObject(Commande.class));
                        }
                        AdapterListCommandes adapterListCommandes= new AdapterListCommandes(getApplicationContext(), resultatCommandes);
                        listCommandes.setAdapter(adapterListCommandes);
                    }
                });

        imgQuitter.setOnClickListener(
                v->finish()
        );

    }

    private void init(){
        listCommandes= findViewById(R.id.listCommandes);
        lesCommandes= new ArrayList<>();
        txtMailUser1Commande= findViewById(R.id.txtMailUser1Commande);
        imgQuitter= findViewById(R.id.imgQuitter);
    }
}