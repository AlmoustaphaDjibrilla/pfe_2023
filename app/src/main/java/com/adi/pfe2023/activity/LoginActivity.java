package com.adi.pfe2023.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;

import com.adi.pfe2023.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class LoginActivity extends Activity implements Serializable {

    private final String PATH_USER_DATABASE= "Users";
    private final String PATH_PRESENCE_PERSONNE= "test/presence_personne";

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    TextView txtCreerCompte, txtPasswordOublie;
    EditText txtMailLogin, txtPasswordLogin;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lancerNotification();

        //initialisation des différents composants de l'interface graphique
        init();



        /**
         * Click sur le button login pour se connecter
         */
        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mail= txtMailLogin.getText().toString();
                        String password= txtPasswordLogin.getText().toString();

                        loginMethod(mail, password);

                    }
                }
        );

        /**
         * Click sur le lien "mot de passe oublié"
         * pour recevoir un lien de réinitialisation de mot de passe
         */
        txtPasswordOublie.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mail= txtMailLogin.getText().toString();

                        resetPasswordMethod(mail);
                    }
                }
        );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        lancerNotification();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lancerNotification();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lancerNotification();
    }

    private void notification(){
        NotificationChannel channel = new NotificationChannel(
                "Id Effraction",
                "Alerte effraction",
                NotificationManager.IMPORTANCE_HIGH
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification =
                new Notification.Builder(
                        getApplicationContext(), "Id Effraction"
                )
                        .setContentTitle("Alerte effraction")
                        .setContentText("Attention, présence inconnue detectée")
                        .setSmallIcon(R.drawable.icon_alert)
                        .setAutoCancel(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        NotificationManagerCompat
                .from(this)
                .notify(1, notification.build());
    }

    private void lancerNotification(){
        DatabaseReference reference= FirebaseDatabase.getInstance()
                .getReference(PATH_PRESENCE_PERSONNE);

        reference.addValueEventListener(new ValueEventListener() {
            Long val= 0l;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long presenceValue=(Long) snapshot.getValue();
                val= presenceValue;
                //Nouvelle intruision
                if (presenceValue==1) {
                    notification();
                    vibrationTelephone();
                    sonnerieTelephone();
                    reference.setValue(0L);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Permet de faire vibrer le téléphone
     */
    private void vibrationTelephone(){
        Vibrator vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(3500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    /**
     * Permet de faire sonner le telephone
     */
    private void sonnerieTelephone(){
        Ringtone ringtone= RingtoneManager
                .getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        new CountDownTimer(20000, 1000){

            @Override
            public void onTick(long l) {
                ringtone.play();
            }

            @Override
            public void onFinish() {
                ringtone.stop();
            }
        }.start();
    }

    /**
     * Initialisation des différents composants de l'interface graphique
     */
    private void init(){
        //Initialisation Firebase Auth
        mAuth= FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();

        btnLogin= findViewById(R.id.btnLoginAdmin);
        txtPasswordOublie= findViewById(R.id.txtPasswordOublieAdmin);

        txtMailLogin=findViewById(R.id.txtMailLoginAdmin);
        txtPasswordLogin= findViewById(R.id.txtPasswordLoginAdmin);
    }


    /**
     * Cette méthode permet à
     * un quelconque utilisateur
     * de se connecter au système
     * grâce à son mail et password
     * @param mail
     * @param password
     */
    private void loginMethod(String mail, String password){
        if (mail==null || password==null || mail.equals("") || password.equals("")){
            Toast.makeText(LoginActivity.this, "Veuillez saisir tous les champs", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(mail, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            firebaseUser= mAuth.getCurrentUser();
                            getUserModelFromFirebase(firebaseUser);
                            ouvrirMainPage();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Données non correspondants", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    /**
     * envoyer un mail pour pouvoir réinitialiser le mot de passe
     * du user ayant le mail spécifié
     * @param mail
     */
    private void resetPasswordMethod(String mail){
        if (mail==null || mail.equals("")){
            Toast.makeText(getApplicationContext(), "Veuillez saisir votre mail...", Toast.LENGTH_SHORT).show();
            txtMailLogin.setError("Erreur");
        }
        else {
            mAuth.sendPasswordResetEmail(mail)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Consultez votre messagerie électronique", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getApplicationContext(), "Echec d'envoi de mail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void ouvrirMainPage(){
        Intent ouvrirMainPage= new Intent(getApplicationContext(), MainPageActivity.class);
        ouvrirMainPage.putExtra("fireBaseUser", firebaseUser);
        startActivity(ouvrirMainPage);
        finish();
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
                          Toast.makeText(getApplicationContext(), "Administrateur", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "User", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Doc non trouvé", Toast.LENGTH_LONG).show();

                    }
                });
    }
}