package com.adi.pfe2023.activity.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adi.pfe2023.R;
import com.adi.pfe2023.model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChercherUser extends AppCompatActivity {
    final String PATH_USER_DATABASE= "Users";

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    CollectionReference collectionUsers;
    FirebaseFirestore firebaseFirestore;
    DocumentReference userDocument;
    UserModel userModel;

    EditText txtMailSearchUser;
    TextView txtResult;
    Button btnSearchUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chercher_user);

        init();

        btnSearchUser.setOnClickListener(
                v->{
                    chercherUser(txtMailSearchUser.getText().toString());
                }
        );


    }

    private void init(){
        txtMailSearchUser= findViewById(R.id.txtMailSearchUser);
        txtResult= findViewById(R.id.txtResult);
        btnSearchUser= findViewById(R.id.btnSearchUser);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        collectionUsers= firebaseFirestore.collection(PATH_USER_DATABASE);
    }

    public void chercherUser(String mail){
        if (mail.equals("") || mail==null){
            txtMailSearchUser.setError("Veuillez saisie le mail");
        }
        else{

            firebaseUser=firebaseAuth.getCurrentUser();
            userDocument= collectionUsers.document(firebaseUser.getUid());
            userDocument.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            userModel= documentSnapshot.toObject(UserModel.class);

                            if (userModel==null)
                                txtResult.setText("Non trouvé");
                            else
                                txtResult.setText(userModel.toString());
                        }
                    });
        }
    }
}