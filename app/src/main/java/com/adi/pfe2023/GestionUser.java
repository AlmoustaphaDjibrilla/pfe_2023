package com.adi.pfe2023;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.adi.pfe2023.activity.LoginActivity;
import com.adi.pfe2023.activity.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class GestionUser {

    private final String PATH_USER_DATABASE= "Users";

    private FirebaseUser firebaseUser;
    private UserModel userModel;
    private FirebaseFirestore firebaseFirestore;

    public GestionUser(FirebaseUser firebaseUser, UserModel userModel) {
        this.firebaseUser = firebaseUser;
        this.userModel= userModel;
    }

//    public void sendResetPasswordMail(String mail, Context context){
//        if (mail==null || mail.equals("")){
//            Toast.makeText(context, "Veuillez saisir votre mail...", Toast.LENGTH_SHORT).show();
//            //txtMailLogin.setError("Erreur");
//        }
//        else {
//           // mAuth.sendPasswordResetEmail(mail)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Toast.makeText(context, "Consultez votre messagerie électronique", Toast.LENGTH_LONG).show();
//                            Intent mailClient = new Intent(Intent.ACTION_VIEW);
//                            mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail");
//                            if (mailClient !=null){
//                                startActivity(mailClient);
//                            }
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(LoginActivity.this, "Echec d'envoi de mail", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
//    }
}
